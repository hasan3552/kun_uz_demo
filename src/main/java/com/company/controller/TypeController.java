package com.company.controller;

import com.company.dto.type.TypeCreateDTO;
import com.company.dto.type.TypeDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.service.TypeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private TypeService typeService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt,
                                    @RequestBody TypeCreateDTO dto) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.create(dto);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody TypeCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO update = typeService.update(id, dto);

        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeVisible(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        TypeDTO articleTypeDTO = typeService.changeVisible(id);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRegion(@RequestHeader("Authorization") String jwt){
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<TypeDTO> getAllArticleType = typeService.getAll();

        return ResponseEntity.ok(getAllArticleType);
    }

}
