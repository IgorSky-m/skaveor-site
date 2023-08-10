package com.skachko.shop.auth.service.entities.user.client.api;

import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.dto.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "ACCOUNT-SERVICE", url = "http://localhost:9019")
public interface IUserFeignClient {


    @PostMapping("/users")
    CustomUser create(@RequestBody CustomUser user);


    @GetMapping("/users/{id}")
    CustomUser getOne(@PathVariable UUID id);


    @PutMapping("/users/{id}/dt_update/{version}")
    CustomUser update(@RequestBody CustomUser newUser, @PathVariable UUID id, @PathVariable Date version);

    @PutMapping("/users/{id}/dt_update/{version}/roles")
    CustomUser updateRoles(@PathVariable UUID id, @PathVariable Date version, @RequestBody Set<EUserRole> newRoles);

    @PutMapping("/users/{id}/dt_update/{version}/deactivate")
    CustomUser deactivate(@PathVariable UUID id, @PathVariable Date version);

    @DeleteMapping("/users/{id}/dt_update/{version}/activate")
    CustomUser activate(@PathVariable UUID id, @PathVariable Date version);

    @PostMapping("/users/exist")
    CustomUser getByEmailAndPassword(@RequestBody UserRequest request);

    @GetMapping("/users/exist")
    ResponseEntity<Boolean> isEmailExist(@RequestParam String email);


}



