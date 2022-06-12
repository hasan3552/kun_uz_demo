package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {

    List<ArticleEntity> findAllByStatusAndVisible(ArticleStatus status, Boolean visible);

    Optional<ArticleEntity> findByStatusAndUuid(ArticleStatus status, String uuid);
}
