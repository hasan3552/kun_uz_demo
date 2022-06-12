package com.company.service;

import com.company.dto.type.TypeCreateDTO;
import com.company.dto.type.TypeDTO;
import com.company.entity.TypesEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public TypeDTO create(TypeCreateDTO dto) {

        isValid(dto);

        TypesEntity typesEntity = new TypesEntity();
        save(dto, typesEntity);

        return getTypeDTO(typesEntity);
    }
    public TypeDTO getTypeDTO(TypesEntity typesEntity){

        System.out.println(typesEntity);
        TypeDTO tyreDTO = new TypeDTO();
        tyreDTO.setCreatedDate(typesEntity.getCreatedDate());
        tyreDTO.setId(typesEntity.getId());
        tyreDTO.setKey(typesEntity.getKey());
        tyreDTO.setStatus(typesEntity.getStatus());
        tyreDTO.setNameEn(typesEntity.getNameEn());
        tyreDTO.setNameRu(typesEntity.getNameRu());
        tyreDTO.setNameUz(typesEntity.getNameUz());
        tyreDTO.setVisible(typesEntity.getVisible());

        return tyreDTO;
    }

    private void isValid(TypeCreateDTO dto) {

        if (dto.getNameUz() == null || dto.getNameUz().isEmpty()){
            throw new BadRequestException("Region name_uz wrong");
        }

        if (dto.getNameRu() == null || dto.getNameRu().isEmpty()){
            throw new BadRequestException("Region name_ru wrong");
        }

        if (dto.getNameEn() == null || dto.getNameEn().isEmpty()){
            throw new BadRequestException("Region name_en wrong");
        }

        Optional<TypesEntity> optional = typeRepository
                .findByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz());

        if (optional.isPresent()) {
            throw new ItemNotFoundException("This region already exist");
        }

    }

    public TypeDTO update(Integer id, TypeCreateDTO dto) {

        isValid(dto);

        Optional<TypesEntity> optional = typeRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("This region id not fount");
        }

        TypesEntity typesEntity = optional.get();
        save(dto, typesEntity);

        return getTypeDTO(typesEntity);
    }

    private void save(TypeCreateDTO dto, TypesEntity typesEntity){

        typesEntity.setNameUz(dto.getNameUz());
        typesEntity.setNameRu(dto.getNameRu());
        typesEntity.setNameEn(dto.getNameEn());
        typesEntity.setKey("article_type_"+dto.getNameEn());

        typeRepository.save(typesEntity);
    }

    public TypeDTO changeVisible(Integer id) {

        Optional<TypesEntity> optional = typeRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Region not fount");
        }

        TypesEntity typesEntity = optional.get();
        typesEntity.setVisible(!typesEntity.getVisible());

        typeRepository.save(typesEntity);

        return getTypeDTO(typesEntity);
    }

    public List<TypeDTO> getAll() {

        Iterable<TypesEntity> iterable = typeRepository.findAll();

        List<TypeDTO> allArticleType = new ArrayList<>();
        iterable.forEach(typesEntity -> {

            TypeDTO articleTypeDTO = getTypeDTO(typesEntity);
            allArticleType.add(articleTypeDTO);
        });

        return allArticleType;
    }

}
