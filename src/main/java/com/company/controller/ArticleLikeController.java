package com.company.controller;

import com.company.dto.article.ArticleLikeCreateDTO;
import com.company.dto.article.ArticleLikeDTO;
import com.company.service.ArticleLikeService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "Article Like Controller")
@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    //     16. Article LikeCreate (ANY)
//        (article_id)
    @ApiOperation(value = "Article Like", notes = "Article like by users")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody ArticleLikeCreateDTO dto
                                 // HttpServletRequest request
    ) {
        log.info("Request for article like :{}", dto.getArticleId());
     //   Integer id = HttpHeaderUtil.getId(request);

        articleLikeService.articleLike(dto.getArticleId());

        return ResponseEntity.ok("success");
    }

    //    17. Article DisLikeCreate (ANY)
//            (article_id)
    @ApiOperation(value = "Article Dis Like", notes = "Article dis like by users")
    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody ArticleLikeCreateDTO dto
                                  //   HttpServletRequest request
    ) {

        log.info("Request for dislike article :{}",dto.getArticleId());
     //   Integer id = HttpHeaderUtil.getId(request);
        articleLikeService.articleDisLike(dto.getArticleId());

        return ResponseEntity.ok().build();
    }

    //      18. Article Like Remove (ANY)
//        (article_id)
    @ApiOperation(value = "Article Like Remove", notes = "Article like remove by users")
    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody ArticleLikeCreateDTO dto
                                 //   HttpServletRequest request
    ) {

        log.info("Request for remove article like:{}",dto.getArticleId());
    //    Integer id = HttpHeaderUtil.getId(request);
        articleLikeService.removeLike(dto.getArticleId());

        return ResponseEntity.ok().build();
    }
}
