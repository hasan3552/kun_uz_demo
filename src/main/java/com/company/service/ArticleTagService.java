package com.company.service;

import com.company.dto.tag.TagDTO;
import com.company.entity.*;
import com.company.repository.ArticleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleTagService {

    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private TagService tagService;

    public void create(ArticleEntity article, List<String> tagList) {

        tagList.forEach(tagName -> {
            ArticleTagEntity articleTagEntity = new ArticleTagEntity();
            articleTagEntity.setArticle(article);
            articleTagEntity.setTag(tagService.createdIfNotExist(tagName));
            articleTagRepository.save(articleTagEntity);
        });
    }

    public List<TagDTO> getTagListByArticle(ArticleEntity article) {

        List<ArticleTagEntity> list = articleTagRepository.findAllByArticle(article);

        List<TagDTO> tagDTOList = new ArrayList<>();
        list.forEach(articleTagEntity -> {
            TagDTO tagDTO = tagService.getTagDTO(articleTagEntity.getTag());
            tagDTOList.add(tagDTO);
        });

        return tagDTOList;
    }
}
