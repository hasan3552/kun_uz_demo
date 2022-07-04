package com.company.controller;

import com.company.dto.type.TypeCreateDTO;
import com.company.dto.type.TypeDTO;
import com.company.dto.type.TypeGetDTO;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.service.TypeService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(value = "Type Controller")
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private TypeService typeService;

    @ApiOperation(value = "Type create method")
    @PostMapping("/adm")
    public ResponseEntity<?> create(
            //HttpServletRequest request,
                                    @RequestBody TypeCreateDTO dto) {

        //  JwtUtil.decode(jwt, ProfileRole.ADMIN);
       // HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.create(dto);
        log.info("Request for type create dto:{}", dto);
        return ResponseEntity.ok(articleTypeDTO);
    }

    @ApiOperation(value = "Type update method")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(
            //@RequestHeader("Authorization") String jwt,
                                    @RequestBody TypeCreateDTO dto,
                                    @PathVariable("id") Integer id) {
    //    JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO update = typeService.update(id, dto);
        log.info("Request for type update dto:{}", dto);
        return ResponseEntity.ok(update);

    }


    @ApiOperation(value = "Type deleted method")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(
            //@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
     //   JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.changeVisible(id);
        log.info("Request for type deleted typeId:{}", id);
        return ResponseEntity.ok(articleTypeDTO);
    }


    @ApiOperation(value = "All type by language method for admin")
    @GetMapping("/adm/list/{lang}")
    public ResponseEntity<?> getAllType(
            //@RequestHeader("Authorization") String jwt,
                                          @PathVariable("lang") Language language) {
       // JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);
        log.info("Request for type list by admin ");
        return ResponseEntity.ok(getAllArticleType);
    }
// GENERAL

    @ApiOperation(value = "All type by language method for general")
    @GetMapping("/open/{lang}")
    public ResponseEntity<?> getAllTypes(@PathVariable(value = "lang") Language language) {
        log.info("Request for type list by general language: {} ", language);
        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);
        return ResponseEntity.ok(getAllArticleType);
    }

//    @GetMapping("")
//    public ResponseEntity<?> getAllRegion2(@RequestParam(value = "lang", defaultValue = "UZ") Language language){
////        JwtUtil.decode(jwt, ProfileRole.ADMIN);
//        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);
//
//        return ResponseEntity.ok(getAllArticleType);
//    }

    @ApiOperation(value = "All type by language method for general")
    @GetMapping("/open")
    public ResponseEntity<?> getAllRegion3(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                           Language language) {
//        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);
        log.info("Request for type list by general language: {} ", language);
        return ResponseEntity.ok(getAllArticleType);
    }


    @ApiOperation(value = "All type by language method for pagination")
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getAllRegionPagination(//@RequestHeader("Authorization") String jwt,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language,
                                                    @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size) {
     //   JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl pagination = typeService.pagination(page, size);
        log.info("Request for type pagination by admin page:{}, size:{}, language: {} ", page, size, language);
        return ResponseEntity.ok(pagination);
    }


}
