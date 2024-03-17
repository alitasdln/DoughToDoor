package com.doughtodoor.ordermanagement.client;


import com.doughtodoor.ordermanagement.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management", url = "${user-management.url}")
@Component
public interface UserClient {

    @GetMapping("/users/{userId}")
    UserDTO getUserDetails(@PathVariable("userId") Long userId);
}