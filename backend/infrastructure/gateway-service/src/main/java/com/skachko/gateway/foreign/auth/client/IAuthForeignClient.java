package com.skachko.gateway.foreign.auth.client;

import com.skachko.gateway.foreign.auth.ValidateTokenResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "AUTH-SERVICE", url = "http://localhost:9010")
public interface IAuthForeignClient {

    @GetMapping(value = "/auth/token/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ValidateTokenResult validateToken(@RequestParam(name = "token") String token);
}
