package com.example.RestaurantManagementServiceAPI.repository;

import com.example.RestaurantManagementServiceAPI.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface IFoodItemRepo extends JpaRepository<FoodItem,Integer>{
}
