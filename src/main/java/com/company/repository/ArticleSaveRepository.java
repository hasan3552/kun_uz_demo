package com.company.repository;

import com.company.entity.ArticleLikeEntity;
import com.company.entity.SaveArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ArticleSaveRepository extends JpaRepository<SaveArticleEntity, Integer> {


    @Query("FROM ArticleLikeEntity a where  a.article.uuid =:articleId and a.profile.id =:profileId")
    Optional<SaveArticleEntity> findExists(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SaveArticleEntity a where  a.article.uuid =:articleId and a.profile.id =:profileId")
    void delete(String articleId, Integer profileId);



}
