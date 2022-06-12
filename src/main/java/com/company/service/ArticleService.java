package com.company.service;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.category.CategoryDTO;
import com.company.dto.region.RegionDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ProfileService profileService;

    public ArticleDTO create(ArticleCreateDTO dto, Integer profileId) {

        ArticleEntity entity = new ArticleEntity();
        getArticleEntity(entity, dto, profileId);

        articleRepository.save(entity);

        articleTypeService.create(entity, dto.getTypesList());

        articleTagService.create(entity, dto.getTagList());

        return getDTO(entity);

    }

    private ArticleEntity getArticleEntity(ArticleEntity entity, ArticleCreateDTO dto, Integer profileId) {

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegionId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        entity.setCategory(category);

        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(profileId);
        entity.setModerator(moderator);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        return entity;
    }

    public ArticleDTO getDTO(ArticleEntity entity) {

        ArticleDTO dto = new ArticleDTO();

        dto.setUuid(entity.getUuid());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setPublicDate(entity.getPublicDate());
        dto.setShareCount(entity.getShareCount());
        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());
        dto.setViewCount(entity.getViewCount());
        dto.setVisible(entity.getVisible());

        dto.setCategoryDTO(categoryService.getCategoryDTO(entity.getCategory()));

        dto.setRegionDTO(regionService.getRegionDTO(entity.getRegion()));

        dto.setModeratorDTO(profileService.getProfileDTOById(entity.getModerator().getId()));

        if (entity.getPublisher() != null) {
            dto.setPublisherDTO(profileService.getProfileDTOById(entity.getPublisher().getId()));
        }

        dto.setTagDTOList(articleTagService.getTagListByArticle(entity));

        dto.setTypeDTOList(articleTypeService.getTypeByArticle(entity));

        return dto;
    }


    public ArticleDTO getArticleDTOById(String uuid) {

        Optional<ArticleEntity> optional = articleRepository.findById(uuid);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Article not fount");
        }
        ArticleEntity articleEntity = optional.get();
        if (!articleEntity.getVisible()) {
            throw new ItemNotFoundException("Article not fount");
        }

        if (articleEntity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            throw new ItemNotFoundException("Article not fount");
        }

        return getDTO(articleEntity);
    }

    public List<ArticleDTO> getArticleDTOList() {

        Iterable<ArticleEntity> all = articleRepository.findAll();
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        all.forEach(articleEntity -> {
            articleDTOList.add(getDTO(articleEntity));
        });

        return articleDTOList;
    }

    public List<ArticleDTO> getArticleDTOListForUsers() {


        Iterable<ArticleEntity> all = articleRepository
                .findAllByStatusAndVisible(ArticleStatus.PUBLISHED, true);
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        all.forEach(articleEntity -> {
            articleDTOList.add(getDTO(articleEntity));
        });

        return articleDTOList;
    }

    public ArticleDTO changeVisible(String uuid) {

        Optional<ArticleEntity> optional = articleRepository
                .findByStatusAndUuid(ArticleStatus.PUBLISHED, uuid);

        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Article not fount");
        }

        ArticleEntity articleEntity = optional.get();
        articleEntity.setVisible(!articleEntity.getVisible());

        return getDTO(articleEntity);
    }

    public ArticleDTO update(ArticleCreateDTO dto, String uuid, Integer profileId) {

        Optional<ArticleEntity> optional = articleRepository.findById(uuid);

        if (optional.isEmpty()){
            throw new ItemNotFoundException("Article not fount");
        }

        ArticleEntity entity = optional.get();

        getArticleEntity(entity,dto,profileId);

        return getDTO(entity);
    }

    public ArticleEntity get(String articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> {
            throw new ItemNotFoundException("Article Not found");
        });
    }
}
