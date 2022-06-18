package com.company.controller;

import com.company.service.ArticleSaveService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article_save")
public class ArticleSaveController {

    @Autowired
    private ArticleSaveService articleSaveService;

    @PostMapping("/{id}")
    public ResponseEntity<?> like(@PathVariable("id") String profileId,
                                  HttpServletRequest request) {

        Integer id = HttpHeaderUtil.getId(request);
        articleSaveService.articleSave(profileId, id);

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") String profileId,
                                    HttpServletRequest request) {

        Integer id = HttpHeaderUtil.getId(request);
        articleSaveService.removeSave(profileId, id);

        return ResponseEntity.ok().build();
    }
}
