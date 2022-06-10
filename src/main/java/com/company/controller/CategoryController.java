package com.company.controller;

import com.company.dto.CategoryCreateDTO;
import com.company.dto.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt,
                                    @RequestBody CategoryCreateDTO dto) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);

        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody CategoryCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO update = categoryService.update(id, dto);

        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeVisible(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.changeVisible(id);

        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRegion(@RequestHeader("Authorization") String jwt){
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<CategoryDTO> getAllCategory = categoryService.getAll();

        return ResponseEntity.ok(getAllCategory);
    }

}
