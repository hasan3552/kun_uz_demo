package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.service.ArticleService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt,
                                 @RequestBody ArticleCreateDTO dto){

        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, profileId);

        return ResponseEntity.ok(articleDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable("id") String uuid){
        ArticleDTO articleDTO = articleService.getArticleDTOById(uuid);

        return ResponseEntity.ok(articleDTO);
    }


    @GetMapping("/special/list")
    public ResponseEntity<?> getArticleListForAdministration(@RequestHeader("Authorization") String jwt){
        Integer profileId = JwtUtil.decode(jwt);

        if (profileService.get(profileId).getRole().equals(ProfileRole.USER)){
            throw new BadRequestException("No access");
        }

        List<ArticleDTO> dtos = articleService.getArticleDTOList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/detail/list")
    public ResponseEntity<?> getArticleList(){

        List<ArticleDTO> dtos = articleService.getArticleDTOListForUsers();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String uuid,
                                     @RequestHeader("Authorization") String jwt){

        Integer profileId = JwtUtil.decode(jwt);
        if (profileService.get(profileId).getRole().equals(ProfileRole.USER)){
            throw new BadRequestException("No access");
        }

        ArticleDTO dto = articleService.changeVisible(uuid);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody ArticleCreateDTO dto,
                                    @PathVariable("id") String uuid){
        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);

        ArticleDTO update = articleService.update(dto, uuid, profileId);

        return ResponseEntity.ok(update);
    }
}
