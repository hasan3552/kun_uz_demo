package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.EmailService;
import com.company.service.SmsService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api("Email Controller")
@RestController
@RequestMapping("/email")
public class EmailController {

//    sms Controller
//    sms Pagination (ADMIN)
//    sms limit (1 minut 4 ta)

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "Email Pagination Method")
    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size,
                                        HttpServletRequest request){
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PageImpl pagination = emailService.pagination(page, size);

        log.info("Request for email pagination");
        return ResponseEntity.ok(pagination);
    }


}
