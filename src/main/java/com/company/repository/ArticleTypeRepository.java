package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {

    List<ArticleTypeEntity> findAllByArticle(ArticleEntity entity);
}
