package com.company.controller;

import com.company.dto.article.ArticleLikeCreateDTO;
import com.company.dto.article.ArticleLikeDTO;
import com.company.service.ArticleLikeService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody ArticleLikeCreateDTO dto,
                                  HttpServletRequest request){
        Integer id = HttpHeaderUtil.getId(request);
        articleLikeService.articleLike(dto.getArticleId(), id);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody ArticleLikeCreateDTO dto,
                                  HttpServletRequest request){

        Integer id = HttpHeaderUtil.getId(request);
        articleLikeService.articleDisLike(dto.getArticleId(), id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody ArticleLikeCreateDTO dto,
                                  HttpServletRequest request){

        Integer id = HttpHeaderUtil.getId(request);
        articleLikeService.removeLike(dto.getArticleId(), id);

        return ResponseEntity.ok().build();
    }
}
