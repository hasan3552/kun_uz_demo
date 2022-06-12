package com.company.controller;

import com.company.dto.comment.CommentCreateDTO;
import com.company.dto.comment.CommentDTO;
import com.company.dto.comment.CommentUpdateDTO;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> created(@RequestHeader("Authorization") String jwt,
                                     @RequestBody CommentCreateDTO dto) {

        Integer profileId = JwtUtil.decode(jwt);
        CommentDTO commentDTO = commentService.create(dto, profileId);

        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody CommentUpdateDTO dto,
                                    @PathVariable("id") Integer commentId) {

        Integer profileId = JwtUtil.decode(jwt);
        CommentDTO update = commentService.update(dto, commentId, profileId);

        return ResponseEntity.ok(update);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleted(@RequestHeader("Authorization") String jwt,
                                     @PathVariable("id") Integer commentId){

        Integer profileId = JwtUtil.decode(jwt);
        CommentDTO dto = commentService.changeVisible(profileId, commentId);

        return ResponseEntity.ok(dto);
    }


}
