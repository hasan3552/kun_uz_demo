package com.company.repository;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleShortDTO;
import com.company.dto.article.ArticleShortInfoByCategory;
import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.TypesEntity;
import com.company.enums.ArticleStatus;
import com.company.enums.CategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {

    List<ArticleEntity> findAllByStatusAndVisible(ArticleStatus status, Boolean visible);

    Optional<ArticleEntity> findByStatusAndUuid(ArticleStatus status, String uuid);

    @Query(value = "SELECT article.* " +
            " FROM article " +
            " Where  status =:status " +
            " and visible " +
            " order by public_date " +
            " limit :limit",
            nativeQuery = true)
    List<ArticleEntity> findTopLimitByStatus(@Param("status") String status, @Param("limit") Integer limit);


    @Query(value = "SELECT  art.* " +
            " FROM article_type as a " +
            " inner join article as art on art.uuid = a.article_id " +
            " inner join types as t on t.id = a.type_id " +
            " Where  t.key =:key  " +
            " order by art.public_date " +
            " limit :limit",
            nativeQuery = true)
    List<ArticleEntity> findTopLimitByArticleNative(@Param("key") String key, @Param("limit") Integer limit);

    List<ArticleEntity> findAllByCategoryAndStatusAndVisibleOrderByCreatedDate
            (CategoryEntity category, ArticleStatus status, Boolean visible);

    @Query("SELECT new ArticleEntity(art.uuid,art.title,art.description,art.publicDate) " +
            " from ArticleEntity as art " +
            " Where art.visible = true and art.status = 'PUBLISHED' and art.uuid not in (:idList) " +
            " order by art.publicDate ")
    Page<ArticleEntity> findLast8NotIn(@Param("idList") List<String> idList, Pageable pageable);

    @Query(value = "select * from article order by view_count DESC limit :limit", nativeQuery = true)
    List<ArticleEntity> mostRead(@Param("limit") int limit);

//    @Query(value = "SELECT  art.* " +
//            " FROM article_type as a " +
//            " inner join article as art on art.uuid = a.article_id " +
//            " inner join types as t on t.id = a.type_id " +
//            " Where  t.key =:key  " +
//            " order by art.public_date " +
//            " limit :limit", nativeQuery = true)
//    List<ArticleEntity> mostRead(@Param("limit") int limit,
//                                 @Param("regionId") Integer regionId);

    @Query(value = "SELECT  art.uuid as id ,art.title as title, art.description as description," +
            "art.public_date as publishDate " +
            " FROM article_type as a " +
            " inner join article as art on art.uuid = a.article_id " +
            " inner join types as t on t.id = a.type_id " +
            " inner join region as r on r.id=art.region_id " +
            " Where  t.key =:typeKey and  r.key=:regionKey and  art.visible = true and " +
            " art.status = 'PUBLISHED' and art.visible" +
            " order by art.public_date " +
            " limit 5",
            nativeQuery = true)
    List<ArticleShortInfoByCategory> getLast5ArticleByTypesAndByRegionKey
            (@Param("typeKey") String typeKey, @Param("regionKey") String regionKey);

    // 9
    @Query(value = "SELECT  art.uuid as id ,art.title as title, art.description as description," +
            "art.public_date as publishDate " +
            " FROM article as art " +
            " inner join article_type as a on a.article_id = art.uuid " +
            " inner join types as t on t.id = a.type_id " +
            " where  t.key =:key and art.visible = true and art.status = 'PUBLISHED' and art.uuid not in (:id) " +
            " order by art.public_date " +
            " limit 5 ",
            nativeQuery = true)
    List<ArticleShortDTO> getLast4ArticleByType(@Param("key") String key, @Param("id") String id);

    @Query(value = "SELECT article.* " +
            " FROM article " +
            " Where  status =:status " +
            " and category_id =:categoryId" +
            " and visible " +
            " order by public_date " +
            " limit :limit",
            nativeQuery = true)
    List<ArticleEntity> findTopLimitByCategory(@Param("status") String status, @Param("limit") Integer limit
            , @Param("categoryId") Integer categoryId);

}
