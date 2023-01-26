package com.skachko.account.service.entities.user.controller;

import com.skachko.account.service.entities.user.api.EUserRole;
import com.skachko.account.service.entities.user.dto.CustomUser;
import com.skachko.account.service.entities.user.dto.UserRequest;
import com.skachko.account.service.entities.user.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;


    @PostMapping
    public CustomUser create(@RequestBody CustomUser user) {
        return service.save(user);
    }


    @GetMapping("/{id}")
    public CustomUser getOne(@PathVariable UUID id) {
        return service.getOne(id);
    }

    @GetMapping
    public List<CustomUser> getAll() {
        return service.findAll();
    }

    @GetMapping("/page")
    public Page<CustomUser> getAll(
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "dtCreate") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        return service.getPage(size, page, field, direction);
    }


    @PutMapping("/{id}/dt_update/{version}")
    public CustomUser update(@RequestBody CustomUser newUser, @PathVariable UUID id, @PathVariable Date version) {
        return service.update(id, newUser, version);
    }

    @PutMapping("/{id}/dt_update/{version}/roles")
    public CustomUser updateRoles(@PathVariable UUID id, @PathVariable Date version, @RequestBody Set<EUserRole> newRoles) {
        return service.updateRoles(id, version, newRoles);
    }

    @PutMapping("/{id}/dt_update/{version}/deactivate")
    public CustomUser deactivate(@PathVariable UUID id, @PathVariable Date version) {
        return service.deactivate(id, version);
    }


    @PutMapping("/{id}/dt_update/{version}/activate")
    public CustomUser activate(@PathVariable UUID id, @PathVariable Date version) {
        return service.activate(id, version);
    }

    @PostMapping("/exist")
    public CustomUser getByEmailAndPassword(@RequestBody UserRequest request) {
        return service.getByRequest(request);
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> isEmailExist(@RequestParam String email) {
        return ResponseEntity.ok(service.isEmailExist(email));
    }
}
