package com.company.controller;

import com.company.dto.comment.CommentCreateDTO;
import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentUpdateDTO;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api("Comment Controller")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Comment Create Method")
    @PostMapping("")
    public ResponseEntity<?> created(
            //@RequestHeader("Authorization") String jwt,
                                     @RequestBody CommentCreateDTO dto) {

      //  Integer profileId = JwtUtil.decode(jwt);
        log.info("Request for comment create dto:{}", dto);
        CommentDTO commentDTO = commentService.create(dto);

        return ResponseEntity.ok(commentDTO);
    }
    @ApiOperation(value = "Comment Update Method")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            //@RequestHeader("Authorization") String jwt,
                                    @RequestBody CommentUpdateDTO dto,
                                    @PathVariable("id") Integer commentId) {
      //  Integer profileId = JwtUtil.decode(jwt);
        log.info("Request for comment update dto:{}", dto);
        CommentDTO update = commentService.update(dto, commentId);

        return ResponseEntity.ok(update);
    }
    @ApiOperation(value = "Comment Deleted Method")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleted(
            //@RequestHeader("Authorization") String jwt,
                                     @PathVariable("id") Integer commentId){

       // Integer profileId = JwtUtil.decode(jwt);
        CommentDTO dto = commentService.changeVisible(commentId);
        log.info("Request for comment deleted dto:{}", dto);
        return ResponseEntity.ok(dto);
    }

}
