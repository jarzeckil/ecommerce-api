package com.mtab.mtabapi.service;

import com.mtab.mtabapi.dto.OrderItemRequest;
import com.mtab.mtabapi.dto.OrderRequest;
import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.Order;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import com.mtab.mtabapi.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    ItemRepository itemRepository; // Atrapa
    @Mock
    CustomerRepository customerRepository; // Atrapa
    @Mock
    OrderRepository orderRepository; // Atrapa
    @InjectMocks
    OrderService orderService; // Testowana klasa

    @Test
    void shouldThrowExceptionWhenItemNotFound() {
        // GIVEN
        OrderRequest request = new OrderRequest(1L, List.of(new OrderItemRequest(1L, 1)));
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));

        // WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(request));

        // THEN
        assertEquals("Item not found!", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // GIVEN
        OrderRequest request = new OrderRequest(1L, List.of(new OrderItemRequest(1L, 1)));
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        // WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(request));

        // THEN
        assertEquals("Customer not found!", exception.getMessage());
    }

    @Test
    void shouldPlaceOrderSuccessfully() {
        // GIVEN
        OrderRequest request = new OrderRequest(1L, List.of(new OrderItemRequest(10L, 1)));

        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);

        Item mockItem = new Item();
        mockItem.setId(10L);
        mockItem.setPrice(150.00); // Cena katalogowa

        Order savedOrder = new Order();
        savedOrder.setId(123L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
        when(itemRepository.findById(10L)).thenReturn(Optional.of(mockItem));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // WHEN
        Long resultId = orderService.placeOrder(request);

        // THEN
        assertEquals(123L, resultId);

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}