package com.company.controller;

import com.company.service.ArticleSaveService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "Article Save Profile Controller")
@RestController
@RequestMapping("/article_save")
public class ArticleSaveController {

    @Autowired
    private ArticleSaveService articleSaveService;

    @ApiOperation(value = "Article Save User", notes = "Article save user by users")
    @PostMapping("/{id}")
    public ResponseEntity<?> like(@PathVariable("id") String articleId,
                                  HttpServletRequest request) {

        Integer profileId = HttpHeaderUtil.getId(request);
        articleSaveService.articleSave(articleId, profileId);
        log.info("article save by user profileId; {}, articleId :{}", profileId,articleId);

        return ResponseEntity.ok("success");
    }


    @ApiOperation(value = "Article Saved remove User", notes = "Article saved remove user by users")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") String articleId,
                                    HttpServletRequest request) {

        Integer profileId = HttpHeaderUtil.getId(request);
        articleSaveService.removeSave(articleId, profileId);
        log.info("article save by user profileId; {}, articleId :{}", profileId,articleId);
        return ResponseEntity.ok().build();
    }
}
