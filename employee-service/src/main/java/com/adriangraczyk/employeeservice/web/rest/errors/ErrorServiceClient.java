package com.adriangraczyk.employeeservice.web.rest.errors;

import com.adriangraczyk.employeeservice.service.dto.ErrorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "errorservice")
public interface ErrorServiceClient {

    @RequestMapping(method = RequestMethod.POST, path = "/api/errors")
    void createError(@RequestBody ErrorDTO errorDTO);
}
