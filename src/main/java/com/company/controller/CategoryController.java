package com.company.controller;

import com.company.dto.JwtDTO;
import com.company.dto.category.CategoryCreateDTO;
import com.company.dto.category.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api("Category Controller")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Category Create", notes = "Method for category create")
    @PostMapping("/adm")
    public ResponseEntity<?> create(
            //HttpServletRequest request,
                                    @RequestBody CategoryCreateDTO dto) {
        log.info("Request for category created dto: {}", dto);
       // HttpHeaderUtil.getId(request, ProfileRole.ADMIN);

        // JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);

        return ResponseEntity.ok(categoryDTO);
    }
    @ApiOperation(value = "Category UPdate", notes = "Method for category update")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(
            //@RequestHeader("Authorization") String jwt,
                                    @RequestBody CategoryCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        //JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO update = categoryService.update(id, dto);
        log.info("Request for category update UpdateDto: {}, categoryId: {}", dto, id);
        return ResponseEntity.ok(update);

    }
    @ApiOperation(value = "Category Change Status", notes = "Method for category change status")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(
            //@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
      //  JwtUtil.decode(jwt, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.changeVisible(id);
        log.info("Request for category deleted id: {}", id);
        return ResponseEntity.ok(categoryDTO);
    }
    @ApiOperation(value = "Category All list", notes = "Method for category list ")
    @GetMapping("/adm/list")
    public ResponseEntity<?> getAllCategory(
           // @RequestHeader("Authorization") String jwt
    ) {
     //   JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<CategoryDTO> getAllCategory = categoryService.getAll();
        log.info("Request for category list ");
        return ResponseEntity.ok(getAllCategory);
    }

}
