package com.company.service;

import com.company.dto.CategoryCreateDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.RegionCreateDTO;
import com.company.dto.RegionDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryCreateDTO dto) {

        isValid(dto);

        CategoryEntity categoryEntity = new CategoryEntity();
        save(dto,categoryEntity);

        return getCategoryDTO(categoryEntity);
    }
    private CategoryDTO getCategoryDTO(CategoryEntity categoryEntity){

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCreatedDate(categoryEntity.getCreatedDate());
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setKey(categoryEntity.getKey());
        categoryDTO.setStatus(categoryEntity.getStatus());
        categoryDTO.setNameEn(categoryEntity.getNameEn());
        categoryDTO.setNameRu(categoryEntity.getNameRu());
        categoryDTO.setNameUz(categoryEntity.getNameUz());
        categoryDTO.setVisible(categoryEntity.getVisible());

        return categoryDTO;
    }

    private void isValid(CategoryCreateDTO dto) {

        if (dto.getNameUz() == null || dto.getNameUz().isEmpty()){
            throw new BadRequestException("Region name_uz wrong");
        }

        if (dto.getNameRu() == null || dto.getNameRu().isEmpty()){
            throw new BadRequestException("Region name_ru wrong");
        }

        if (dto.getNameEn() == null || dto.getNameEn().isEmpty()){
            throw new BadRequestException("Region name_en wrong");
        }

        Optional<CategoryEntity> optional = categoryRepository
                .findByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz());

        if (optional.isPresent()) {
            throw new ItemNotFoundException("This region already exist");
        }

    }

    public CategoryDTO update(Integer id, CategoryCreateDTO dto) {

        isValid(dto);

        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("This region id not fount");
        }

        CategoryEntity categoryEntity = optional.get();
        save(dto,categoryEntity);

        return getCategoryDTO(categoryEntity);
    }

    private void save(CategoryCreateDTO dto, CategoryEntity categoryEntity){

        categoryEntity.setNameUz(dto.getNameUz());
        categoryEntity.setNameRu(dto.getNameRu());
        categoryEntity.setNameEn(dto.getNameEn());
        categoryEntity.setKey("category_"+dto.getNameEn());

        categoryRepository.save(categoryEntity);
    }

    public CategoryDTO changeVisible(Integer id) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Region not fount");
        }

        CategoryEntity categoryEntity = optional.get();
        categoryEntity.setVisible(!categoryEntity.getVisible());

        categoryRepository.save(categoryEntity);

        return getCategoryDTO(categoryEntity);
    }

    public List<CategoryDTO> getAll() {

        Iterable<CategoryEntity> iterable = categoryRepository.findAll();

        List<CategoryDTO> allRegion = new ArrayList<>();
        iterable.forEach(categoryEntity -> {

            CategoryDTO categoryDTO = getCategoryDTO(categoryEntity);
            allRegion.add(categoryDTO);
        });

        return allRegion;
    }
}
