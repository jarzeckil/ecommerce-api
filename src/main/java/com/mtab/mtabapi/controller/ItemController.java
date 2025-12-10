package com.mtab.mtabapi.controller;

import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.repository.ItemCategoryRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @GetMapping
    public List<Item> getItems(@RequestParam(required = false) Long categoryId){
        List<Item> items;
        if(categoryId == null){
            return itemRepository.findAll();
        } else{
            itemCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category doesn't exist"));
            items = itemRepository.findByCategoryId(categoryId);
        }
        return items;
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item doesn't exist."));
    }


}
