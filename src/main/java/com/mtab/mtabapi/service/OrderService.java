package com.mtab.mtabapi.service;

import com.mtab.mtabapi.dto.OrderItemRequest;
import com.mtab.mtabapi.dto.OrderRequest;
import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.ItemOrder;
import com.mtab.mtabapi.entity.Order;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import com.mtab.mtabapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long placeOrder(OrderRequest orderRequest) {

        Long customerId = orderRequest.getCustomerId();
        log.info("Adding order for customer {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found!"));


        Order order = new Order();
        order.setCustomer(customer);


        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            Item currItem = itemRepository.findById(orderItemRequest.getItemId()).
                    orElseThrow(() -> new IllegalArgumentException("Item not found!"));

            ItemOrder currItemOrder = new ItemOrder();
            currItemOrder.setItem(currItem);
            currItemOrder.setQuantity(orderItemRequest.getQuantity());
            currItemOrder.setPrice(currItem.getPrice());
            currItemOrder.setOrder(order);
            order.getItemOrders().add(currItemOrder);
        }

        Order savedOrder = orderRepository.save(order);

        log.info("Order with id {} saved", savedOrder.getId());

        return savedOrder.getId();
    }
}
