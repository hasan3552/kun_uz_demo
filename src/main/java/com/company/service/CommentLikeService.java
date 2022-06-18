package com.company.service;

import com.company.dto.article.ArticleLikeDTO;
import com.company.dto.comment.CommentLikeDTO;
import com.company.entity.*;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentLikeRepository;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;

    public void commentLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.LIKE);
    }

    public void commentDisLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(Integer commentId, Integer pId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean commentExists = commentRepository.existsById(commentId);
        if (!commentExists) {
            throw new ItemNotFoundException("Comment NotFound");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment(new CommentEntity(commentId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        commentLikeRepository.save(like);
    }

    public void removeLike(Integer commentId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = commentLikeRepository.findExists(commentId, pId);
        optional.ifPresent(commentLikeEntity -> {
            commentLikeRepository.delete(commentLikeEntity);
        });*/
        commentLikeRepository.delete(commentId, pId);
    }
}
