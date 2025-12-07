package com.mtab.mtabapi.repository;

import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(Long category_id);
    Optional<Item> findByNameAndCategory(String name, ItemCategory category);
}
