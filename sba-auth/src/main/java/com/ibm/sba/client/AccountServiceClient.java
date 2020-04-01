package com.ibm.sba.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sba-account")
public interface AccountServiceClient {
    @GetMapping(value = "/account/api/v1/findUser")
    ResponseEntity<Object> getUser(@RequestParam("userName") String userName);
}
