package com.mtab.mtabapi.controller;

import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public List<Item> getItems(@RequestParam(required = false) Long categoryId){
        List<Item> items;
        if(categoryId == null){
            return itemRepository.findAll();
        } else{
            items = itemRepository.findByCategoryId(categoryId);
        }
        return items;
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item ID not found."));
    }


}
