package com.company.controller;

import com.company.dto.article.ArticleLikeCreateDTO;
import com.company.dto.comment.CommentLikeCreateDTO;
import com.company.service.ArticleLikeService;
import com.company.service.CommentLikeService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody CommentLikeCreateDTO dto,
                                  HttpServletRequest request){
        Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.commentLike(dto.getCommentId(), id);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(@RequestBody CommentLikeCreateDTO dto,
                                  HttpServletRequest request){

        Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.commentDisLike(dto.getCommentId(), id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody CommentLikeCreateDTO dto,
                                  HttpServletRequest request){

        Integer id = HttpHeaderUtil.getId(request);
        commentLikeService.removeLike(dto.getCommentId(), id);

        return ResponseEntity.ok().build();
    }
}
