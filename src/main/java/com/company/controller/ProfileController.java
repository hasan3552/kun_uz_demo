package com.company.controller;

import com.company.dto.profile.ProfileFilterDTO;
import com.company.dto.attach.AttachDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileShortDTO;
import com.company.enums.ProfileRole;
import com.company.service.AttachService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(value = "Profile Controller")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @ApiOperation(value = "profile create by admin")
    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto
    //                                         @RequestHeader("Authorization") String jwt
    ) {
    //    Integer pId = JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.create(dto);
        log.info("Request profile create by admin dto:{}", dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "profile update by admin")
    @PutMapping("/adm/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id,
                                             @RequestBody ProfileDTO dto
                                           //  @RequestHeader("Authorization") String jwt
    ) {
   //     JwtUtil.decode(jwt, ProfileRole.ADMIN);
        profileService.update(id, dto);
        log.info("Request profile update by admin dto:{}", dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "profile update by profile")
    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> detailUpdate(@RequestBody ProfileDTO dto
                                              //     @RequestHeader("Authorization") String jwt
    ) {
    //    Integer pId = JwtUtil.decode(jwt);
        profileService.update(dto);
        log.info("Request profile update by profile dto:{}", dto);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "profile get by admin")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getProfile(
            //@RequestHeader("Authorization") String jwt,
                                        @PathVariable("id") Integer id){

       // JwtUtil.decode(jwt,ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.getProfileDTOById(id);
        log.info("Request profile get by admin profileId:{}", id);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "profile list by admin")
    @GetMapping("/adm")
    public ResponseEntity<?> getProfileList(
    //        @RequestHeader("Authorization") String jwt
    ){

      //  JwtUtil.decode(jwt,ProfileRole.ADMIN);
        List<ProfileDTO> profileDTOS = profileService.getAllProfileDTOById();
        log.info("Request profile list by admin ");
        return ResponseEntity.ok(profileDTOS);
    }
    @ApiOperation(value = "profile change visible by admin")
    @DeleteMapping("/adm")
    public ResponseEntity<?> changeVisible(
            //@RequestHeader("Authorization") String jwt,
                                           @RequestParam("id") Integer profileId){

      //  JwtUtil.decode(jwt,ProfileRole.ADMIN);
        ProfileDTO profileDTO = profileService.changeVisible(profileId);
        log.info("Request profile change visible by admin profileId:{}", profileId);
        return ResponseEntity.ok(profileDTO);

    }
    @ApiOperation(value = "profile photo upload by profile")
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file
    //                                HttpServletRequest request
    ) {

     //   Integer profileId = HttpHeaderUtil.getId(request);
        AttachDTO dto = attachService.saveToProfile(file);
        log.info("Request profile photo upload by profile dto:{} ", dto);
        return ResponseEntity.ok().body(dto);
    }
    @ApiOperation(value = "profile filter by admin")
    @PostMapping("/adm/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto
    //                                HttpServletRequest request
    ) {
        log.info("Request profile filter by admin " );
     //   HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        List<ProfileShortDTO> response = profileService.filter(dto);
        return ResponseEntity.ok().body(response);
    }
    // filter**********
}
