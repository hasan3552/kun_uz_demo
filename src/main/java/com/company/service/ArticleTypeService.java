package com.company.service;

import com.company.dto.tag.TagDTO;
import com.company.dto.type.TypeDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTagEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleTypeRepository;
import com.company.repository.TagRepository;
import com.company.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeRepository typeRepository;

    public void create(ArticleEntity article, List<Integer> typeList){

        typeList.forEach(integer -> {
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setArticle(article);
            Optional<TypesEntity> optional = typeRepository.findById(integer);
            if (optional.isEmpty()){
                throw new ItemNotFoundException("Type not found");
            }
            articleTypeEntity.setTypes(optional.get());
            articleTypeRepository.save(articleTypeEntity);
        });
    }

    public List<TypeDTO> getTypeByArticle(ArticleEntity entity) {

        List<ArticleTypeEntity> list = articleTypeRepository.findAllByArticle(entity);

        List<TypeDTO> typeDTOList = new ArrayList<>();

        list.forEach(articleTypeEntity -> {
           // TagDTO tagDTO = tagService.getTagDTO(articleTagEntity.getTag());
            TypeDTO typeDTO = typeService.getTypeDTO(articleTypeEntity.getTypes());
            typeDTOList.add(typeDTO);
        });

        return typeDTOList;

    }
}
