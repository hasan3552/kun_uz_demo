package com.company.controller;

import com.company.dto.ArticleTypeCreateDTO;
import com.company.dto.ArticleTypeDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleTypeService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt,
                                    @RequestBody ArticleTypeCreateDTO dto) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ArticleTypeDTO articleTypeDTO = articleTypeService.create(dto);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody ArticleTypeCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ArticleTypeDTO update = articleTypeService.update(id, dto);

        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeVisible(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        ArticleTypeDTO articleTypeDTO = articleTypeService.changeVisible(id);

        return ResponseEntity.ok(articleTypeDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRegion(@RequestHeader("Authorization") String jwt){
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<ArticleTypeDTO> getAllArticleType = articleTypeService.getAll();

        return ResponseEntity.ok(getAllArticleType);
    }

}
