package com.company.service;

import com.company.dto.type.TypeCreateDTO;
import com.company.dto.type.TypeDTO;
import com.company.dto.type.TypeGetDTO;
import com.company.entity.TypesEntity;
import com.company.enums.Language;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public List<TypeGetDTO> getAll(Language language) {

        Iterable<TypesEntity> iterable = typeRepository.findAll();

        List<TypeGetDTO> allArticleType = new ArrayList<>();
        iterable.forEach(typesEntity -> {

            TypeGetDTO articleTypeDTO = getTypeGetDTO(typesEntity, language);
            allArticleType.add(articleTypeDTO);
        });

        return allArticleType;
    }

    private TypeGetDTO getTypeGetDTO(TypesEntity typesEntity, Language language) {

        TypeGetDTO dto = new TypeGetDTO();

        switch (language) {
            case RU:
                dto.setName(typesEntity.getNameRu());
                break;
            case EN:
                dto.setName(typesEntity.getNameEn());
                break;
            case UZ:
                dto.setName(typesEntity.getNameUz());
                break;
            default:
                dto.setName(typesEntity.getNameUz());
                break;
        }

        dto.setKey(typesEntity.getKey());
        return dto;
    }



    ///88888888888888*****************************************88888888888888888888888888888
    public PageImpl pagination(int page, int size) {
        // page = 1
    /*    Iterable<TypesEntity> all = typesRepository.pagination(size, size * (page - 1));
        long totalAmount = typesRepository.countAllBy();*/
//        long totalAmount = all.getTotalElements();
//        int totalPages = all.getTotalPages();

//        TypesPaginationDTO paginationDTO = new TypesPaginationDTO(totalAmount, dtoList);
//        return paginationDTO;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TypesEntity> all = typeRepository.findAll(pageable);

        List<TypesEntity> list = all.getContent();

        List<TypeDTO> dtoList = new LinkedList<>();

        list.forEach(typesEntity -> {
            TypeDTO dto = new TypeDTO();
            dto.setId(typesEntity.getId());
            dto.setKey(typesEntity.getKey());
            dto.setNameUz(typesEntity.getNameUz());
            dto.setNameRu(typesEntity.getNameRu());
            dto.setNameEn(typesEntity.getNameEn());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList,pageable, all.getTotalElements());
    }

}
