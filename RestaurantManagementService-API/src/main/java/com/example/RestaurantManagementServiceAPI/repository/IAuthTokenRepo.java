package com.example.RestaurantManagementServiceAPI.repository;

import com.example.RestaurantManagementServiceAPI.model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Integer> {
}
