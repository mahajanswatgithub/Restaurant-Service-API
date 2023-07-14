package com.example.RestaurantManagementServiceAPI.controller;

import com.example.RestaurantManagementServiceAPI.model.FoodItem;
import com.example.RestaurantManagementServiceAPI.model.User;
import com.example.RestaurantManagementServiceAPI.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodItemController {
    @Autowired
    FoodItemService foodItemService;
    @PostMapping("foodItem/{userId}")
    public FoodItem createFoodItem(@RequestBody FoodItem foodItem,@RequestParam Integer userId) {
        return foodItemService.createFoodItem(foodItem, userId);
    }

    @GetMapping("foodItem")
    List<FoodItem> getAllFoodItems()
    {
        return foodItemService.getAllFoodItems();
    }
}
