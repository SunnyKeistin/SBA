package com.ibm.sba.client;

import com.ibm.sba.model.MentorFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "fsd-account")
public interface AccountServiceClient {
    @GetMapping(value = "/account/api/v1/getUsersByIds")
    ResponseEntity<Object> getUsersByIds(@RequestParam(value = "userIds", required = false) String userIdsString);
}
