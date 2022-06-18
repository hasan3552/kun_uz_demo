package com.company.repository;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    List<ArticleEntity> findAllByStatusAndVisible(ArticleStatus status, Boolean visible);

    Optional<ArticleEntity> findByStatusAndUuid(ArticleStatus status, String uuid);

    @Query(value = "SELECT article.* " +
            " FROM article " +
            " Where  status =:status " +
            " and visible " +
            " order by public_date " +
            " limit :limit",
            nativeQuery = true)
    List<ArticleEntity> findTopLimitByStatus(@Param("status") String status,  @Param("limit") Integer limit);


    @Query(value = "SELECT  art.* " +
            " FROM article_type as a " +
            " inner join article as art on art.uuid = a.article_id " +
            " inner join types as t on t.id = a.type_id " +
            " Where  t.key =:key  " +
            " order by art.public_date " +
            " limit :limit",
            nativeQuery = true)
    List<ArticleEntity> findTopLimitByArticleNative(@Param("key") String key, @Param("limit") Integer limit);

}
