package com.company.service;

import com.company.dto.tag.TagDTO;
import com.company.entity.TagEntity;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagEntity created(String name){

        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(name.toUpperCase());

        tagRepository.save(tagEntity);

        return tagEntity;
    }

    public TagEntity createdIfNotExist(String tagName) {

        Optional<TagEntity> optional = tagRepository.findByName(tagName.toUpperCase());
        return optional.orElseGet(() -> created(tagName));

    }

    public TagDTO getTagDTO(TagEntity entity) {

        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(entity.getId());
        tagDTO.setCreatedDate(entity.getCreatedDate());
        tagDTO.setVisible(entity.getVisible());
        tagDTO.setName(entity.getName());
        tagDTO.setStatus(entity.getStatus());

        return tagDTO;
    }
}
