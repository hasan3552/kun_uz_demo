package com.company.controller;

import com.company.dto.JwtDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.VerificationDTO;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login", notes = "Method for login", nickname = "Some nick name")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.info("Request for login dto: {}", dto);
        ProfileDTO profileDTO = authService.login(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Registration", notes = "Method for registration", nickname = "Some nick name")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto) {
        log.info("Request for registration dto: {}", dto);
        String profileDTO = authService.registration(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Verification Sms", notes = "Method for verification sms")
    @PostMapping("/verification")
    public ResponseEntity<String> verification(@RequestBody VerificationDTO dto) {
        log.info("Request for verification sms dto: {}", dto);
        String response = authService.verification(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Verification email", notes = "Method for verification email", nickname = "Some nick name")
    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        JwtDTO jwtDTO = JwtUtil.decodeJwtDTO(jwt);
        log.info("Request for verification email JWTdto: {}",jwtDTO );
        ResponseInfoDTO responseInfoDTO = authService.emailVerification(jwtDTO.getId());

        return ResponseEntity.ok(responseInfoDTO);
    }

    @ApiOperation(value = "Resend phone", notes = "Method for resend")
    @PostMapping("/resend/{phone}")
    public ResponseEntity<?> resend(@ApiParam(value = "phone", required = true, example = "99891234567890")
                                        @PathVariable String phone) {
        ResponseInfoDTO response = authService.resendSms(phone);
        log.info("Request for resend phone sms by phone: {}", phone);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Resend email", notes = "Method for resend email")
    @GetMapping("/resend/email/{jwt}")
    public ResponseEntity<?> resendEmail(@PathVariable("jwt") String jwt) {
        JwtDTO jwtDTO = JwtUtil.decodeJwtDTO(jwt);
        ResponseInfoDTO response = authService.resendEmail(jwtDTO.getId());
        log.info("Request for resend phone email by dto: {}", jwtDTO);
   //     ResponseInfoDTO responseInfoDTO = authService.resendEmail(id);
        return ResponseEntity.ok(response);
    }
}
