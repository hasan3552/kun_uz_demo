package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> findByArticleAndProfile(ArticleEntity article, ProfileEntity profile);

    @Query("FROM ArticleLikeEntity a where  a.article.uuid =:articleId and a.profile.id =:profileId")
    Optional<ArticleLikeEntity> findExists(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArticleLikeEntity a where  a.article.uuid =:articleId and a.profile.id =:profileId")
    void delete(String articleId, Integer profileId);


//    @Transactional
//    @Modifying
    @Query(value = "\n" +
            "select cast(sum(case when status = 'LIKE' then 1 else 0 end) as int) as like_count, " +
            "       cast(sum(case when status = 'DISLIKE' then 1 else 0 end) as int) as dislike_count " +
            "from article_like " +
            "where article_like.article_id =:articleId", nativeQuery = true)
    Map<String, Integer> countLikeDislike(@Param("articleId") String articleId);


}

