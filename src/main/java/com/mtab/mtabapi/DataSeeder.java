package com.mtab.mtabapi;

import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.ItemCategory;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemCategoryRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        log.info("🌱 SEEDING DATABASE (upsert mode)...");

        ItemCategory supplements = itemCategoryRepository.findByName("Supplements")
                .orElseGet(() -> itemCategoryRepository.save(new ItemCategory("Supplements")));

        ItemCategory clothes = itemCategoryRepository.findByName("Clothes")
                .orElseGet(() -> itemCategoryRepository.save(new ItemCategory("Clothes")));

        ItemCategory accessories = itemCategoryRepository.findByName("Accessories")
                .orElseGet(() -> itemCategoryRepository.save(new ItemCategory("Accessories")));

        upsertItem("Creatine", 15.99, supplements);
        upsertItem("Whey Protein", 10.99, supplements);
        upsertItem("Lifting Straps", 8.49, accessories);
        upsertItem("Compression Shirt", 21.19, clothes);

        upsertCustomer(new Customer("Adam", "Mickiewicz", "adammickiewicz@example.com"));
        upsertCustomer(new Customer("Juliusz", "Słowacki", "juliuszslowacki@example.com"));
        upsertCustomer(new Customer("George", "Lucas", "georgie123lucas@example.com"));

        log.info("DATABASE SEEDED (upsert complete)!");
    }

    private void upsertItem(String name, double price, ItemCategory category) {
        Optional<Item> existing = itemRepository.findByNameAndCategory(name, category);
        if (existing.isPresent()) {
            log.info("Item exists: " + name + " (category: " + category.getName() + ") - skipping");
        } else {
            Item saved = itemRepository.save(new Item(name, price, category));
            log.info("Inserted item: " + saved.getName() + " (id=" + saved.getId() + ")");
        }
    }

    private void upsertCustomer(Customer customer) {
        Optional<Customer> existing = customerRepository.findByEmailAddress(customer.getEmailAddress());
        if (existing.isPresent()) {
            log.info("Customer exists: " + customer.getEmailAddress() + " - skipping");
        } else {
            Customer saved = customerRepository.save(customer);
            log.info("Inserted customer: " + saved.getEmailAddress() + " (id=" + saved.getId() + ")");
        }
    }
}
