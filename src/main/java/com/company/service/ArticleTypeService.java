package com.company.service;

import com.company.dto.ArticleTypeCreateDTO;
import com.company.dto.ArticleTypeDTO;
import com.company.entity.ArticleTypeEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeCreateDTO dto) {

        isValid(dto);

        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
        save(dto,articleTypeEntity);

        return getArticleTypeDTO(articleTypeEntity);
    }
    private ArticleTypeDTO getArticleTypeDTO(ArticleTypeEntity articleTypeEntity){

        ArticleTypeDTO articleTypeDTO = new ArticleTypeDTO();
        articleTypeDTO.setCreatedDate(articleTypeEntity.getCreatedDate());
        articleTypeDTO.setId(articleTypeEntity.getId());
        articleTypeDTO.setKey(articleTypeEntity.getKey());
        articleTypeDTO.setStatus(articleTypeEntity.getStatus());
        articleTypeDTO.setNameEn(articleTypeEntity.getNameEn());
        articleTypeDTO.setNameRu(articleTypeEntity.getNameRu());
        articleTypeDTO.setNameUz(articleTypeEntity.getNameUz());
        articleTypeDTO.setVisible(articleTypeEntity.getVisible());

        return articleTypeDTO;
    }

    private void isValid(ArticleTypeCreateDTO dto) {

        if (dto.getNameUz() == null || dto.getNameUz().isEmpty()){
            throw new BadRequestException("Region name_uz wrong");
        }

        if (dto.getNameRu() == null || dto.getNameRu().isEmpty()){
            throw new BadRequestException("Region name_ru wrong");
        }

        if (dto.getNameEn() == null || dto.getNameEn().isEmpty()){
            throw new BadRequestException("Region name_en wrong");
        }

        Optional<ArticleTypeEntity> optional = articleTypeRepository
                .findByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz());

        if (optional.isPresent()) {
            throw new ItemNotFoundException("This region already exist");
        }

    }

    public ArticleTypeDTO update(Integer id, ArticleTypeCreateDTO dto) {

        isValid(dto);

        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("This region id not fount");
        }

        ArticleTypeEntity articleTypeEntity = optional.get();
        save(dto,articleTypeEntity);

        return getArticleTypeDTO(articleTypeEntity);
    }

    private void save(ArticleTypeCreateDTO dto, ArticleTypeEntity articleTypeEntity){

        articleTypeEntity.setNameUz(dto.getNameUz());
        articleTypeEntity.setNameRu(dto.getNameRu());
        articleTypeEntity.setNameEn(dto.getNameEn());
        articleTypeEntity.setKey("article_type_"+dto.getNameEn());

        articleTypeRepository.save(articleTypeEntity);
    }

    public ArticleTypeDTO changeVisible(Integer id) {

        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Region not fount");
        }

        ArticleTypeEntity articleTypeEntity = optional.get();
        articleTypeEntity.setVisible(!articleTypeEntity.getVisible());

        articleTypeRepository.save(articleTypeEntity);

        return getArticleTypeDTO(articleTypeEntity);
    }

    public List<ArticleTypeDTO> getAll() {

        Iterable<ArticleTypeEntity> iterable = articleTypeRepository.findAll();

        List<ArticleTypeDTO> allArticleType = new ArrayList<>();
        iterable.forEach(articleTypeEntity -> {

            ArticleTypeDTO articleTypeDTO = getArticleTypeDTO(articleTypeEntity);
            allArticleType.add(articleTypeDTO);
        });

        return allArticleType;
    }
}
