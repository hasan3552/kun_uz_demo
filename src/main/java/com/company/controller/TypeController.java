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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private TypeService typeService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody TypeCreateDTO dto) {

      //  JwtUtil.decode(jwt, ProfileRole.ADMIN);
        HttpHeaderUtil.getId(request,ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.create(dto);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody TypeCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO update = typeService.update(id, dto);

        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.changeVisible(id);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @GetMapping("/adm/list/{lang}")
    public ResponseEntity<?> getAllRegion(@RequestHeader("Authorization") String jwt,
                                          @PathVariable("lang") Language language){
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);

        return ResponseEntity.ok(getAllArticleType);
    }
// GENERAL
    @GetMapping("/{lang}")
    public ResponseEntity<?> getAllRegion(@PathVariable(value = "lang") Language language){

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

    @GetMapping("")
    public ResponseEntity<?> getAllRegion3(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                               Language language){
//        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypeGetDTO> getAllArticleType = typeService.getAll(language);

        return ResponseEntity.ok(getAllArticleType);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getAllRegionPagination(@RequestHeader("Authorization") String jwt,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language,
                                                    @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size){
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl pagination = typeService.pagination(page, size);

        return ResponseEntity.ok(pagination);
    }



}
