package com.example.RestaurantManagementServiceAPI.service;

import com.example.RestaurantManagementServiceAPI.model.Order;
import com.example.RestaurantManagementServiceAPI.model.User;
import com.example.RestaurantManagementServiceAPI.model.UserType;
import com.example.RestaurantManagementServiceAPI.repository.IOrderRepo;
import com.example.RestaurantManagementServiceAPI.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    IOrderRepo iOrderRepo;
    @Autowired
    IUserRepo iUserRepo;

    public Order generateFoodOrder(Order order, Integer userId) {
        User user = iUserRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newEmail = user.getUserEmail();
        UserType type = user.getUserType();
        if(!type.equals(UserType.Normal_User) && !newEmail.endsWith(("@gmail.com"))){
            throw new IllegalArgumentException("Only Normal User can generate food Order");
        }

        // Create the food item
        return iOrderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return iOrderRepo.findAll();
    }
}
