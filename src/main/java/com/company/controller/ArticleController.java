package com.company.controller;

import com.company.dto.article.ArticleChangeStatusDTO;
import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleShortDTO;
import com.company.dto.type.TypeDTO;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.service.ArticleService;
import com.company.service.ProfileService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(HttpServletRequest request,
            //@RequestHeader("Authorization") String jwt,
                                 @RequestBody ArticleCreateDTO dto){

//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, profileId);

        return ResponseEntity.ok(articleDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getArticleById(@RequestParam("id") String uuid){
        ArticleDTO articleDTO = articleService.getArticleDTOById(uuid);

        return ResponseEntity.ok(articleDTO);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getArticleListForAdministration(//@RequestHeader("Authorization") String jwt
                                                             HttpServletRequest request){
       // Integer profileId = JwtUtil.decode(jwt);
        Integer profileId = HttpHeaderUtil.getId(request);

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

    @DeleteMapping("/adm")
    public ResponseEntity<?> changeStatus(@RequestParam("id") String uuid,
                                          HttpServletRequest request){

        HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO dto = articleService.changeVisible(uuid);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/adm")
    public ResponseEntity<?> update(//@RequestHeader("Authorization") String jwt,
                                    HttpServletRequest request,
                                    @RequestBody ArticleCreateDTO dto,
                                    @RequestParam("id") String uuid){
//        Integer profileId = JwtUtil.decode(jwt, ProfileRole.MODERATOR);

        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);

        ArticleDTO update = articleService.update(dto, uuid, profileId);

        return ResponseEntity.ok(update);
    }

    @PutMapping("/adm/change_status")
    public ResponseEntity<?> changeStatus(HttpServletRequest request,
                                          @RequestBody ArticleChangeStatusDTO dto,
                                          @RequestParam("id") String uuid){

        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.SUPER_MODERATOR);

        ArticleDTO update = articleService.changeStatus(dto, uuid, profileId);

        return ResponseEntity.ok(update);

    }

    @GetMapping("/public")
    public ResponseEntity<?> getArticleByType(@RequestParam("key") String key,
                                              @RequestParam("limit") Integer limit){

        List<ArticleDTO> dtos = articleService.getArticleDTOListByType(key, limit);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/public/last")
    public ResponseEntity<?> getLastArticleList(@RequestParam("size") Integer size){

        List<ArticleShortDTO> dtos = articleService.getArticleDTOListLast(size);

        return ResponseEntity.ok(dtos);
    }


}
