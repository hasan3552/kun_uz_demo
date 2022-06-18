package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.SaveArticleEntity;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleSaveService {

    @Autowired
    private ArticleSaveRepository articleSaveRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public void articleSave(String articleId, Integer pId) {
        Optional<SaveArticleEntity> optional = articleSaveRepository.findExists(articleId, pId);
        if (optional.isPresent()) {
            SaveArticleEntity like = optional.get();
            articleSaveRepository.save(like);
            return;
        }
        boolean articleExists = articleRepository.existsById(articleId);
        if (!articleExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        SaveArticleEntity saveArticle = new SaveArticleEntity();
        saveArticle.setArticle(new ArticleEntity(articleId));
        saveArticle.setProfile(new ProfileEntity(pId));
        //like.setStatus(status);
        articleSaveRepository.save(saveArticle);
    }

    public void removeSave(String articleId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleSaveRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleSaveRepository.delete(articleLikeEntity);
        });*/
        articleSaveRepository.delete(articleId, pId);
    }
}
