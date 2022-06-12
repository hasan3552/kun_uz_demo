package com.company.service;

import com.company.dto.region.RegionCreateDTO;
import com.company.dto.region.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionCreateDTO dto) {

        isValid(dto);

        RegionEntity regionEntity = new RegionEntity();
        save(dto,regionEntity);

        return getRegionDTO(regionEntity);
    }
    public RegionDTO getRegionDTO(RegionEntity regionEntity){

        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setCreatedDate(regionEntity.getCreatedDate());
        regionDTO.setId(regionEntity.getId());
        regionDTO.setKey(regionEntity.getKey());
        regionDTO.setStatus(regionEntity.getStatus());
        regionDTO.setNameEn(regionEntity.getNameEn());
        regionDTO.setNameRu(regionEntity.getNameRu());
        regionDTO.setNameUz(regionEntity.getNameUz());
        regionDTO.setVisible(regionEntity.getVisible());

        return regionDTO;
    }

    private void isValid(RegionCreateDTO dto) {

        if (dto.getNameUz() == null || dto.getNameUz().isEmpty()){
            throw new BadRequestException("Region name_uz wrong");
        }

        if (dto.getNameRu() == null || dto.getNameRu().isEmpty()){
            throw new BadRequestException("Region name_ru wrong");
        }

        if (dto.getNameEn() == null || dto.getNameEn().isEmpty()){
            throw new BadRequestException("Region name_en wrong");
        }

        Optional<RegionEntity> optional = regionRepository
                .findByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz());

        if (optional.isPresent()) {
            throw new ItemNotFoundException("This region already exist");
        }

    }

    public RegionDTO update(Integer id, RegionCreateDTO dto) {

        isValid(dto);

        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("This region id not fount");
        }

        RegionEntity regionEntity = optional.get();
        save(dto,regionEntity);

        return getRegionDTO(regionEntity);
    }

    private void save(RegionCreateDTO dto, RegionEntity regionEntity){

        regionEntity.setNameUz(dto.getNameUz());
        regionEntity.setNameRu(dto.getNameRu());
        regionEntity.setNameEn(dto.getNameEn());
        regionEntity.setKey("region_"+dto.getNameEn());

        regionRepository.save(regionEntity);
    }

    public RegionDTO changeVisible(Integer id) {

        Optional<RegionEntity> optional = regionRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Region not fount");
        }

        RegionEntity regionEntity = optional.get();
        regionEntity.setVisible(!regionEntity.getVisible());

        regionRepository.save(regionEntity);

        return getRegionDTO(regionEntity);
    }

    public List<RegionDTO> getAll() {

        Iterable<RegionEntity> iterable = regionRepository.findAll();

        List<RegionDTO> allRegion = new ArrayList<>();
        iterable.forEach(regionEntity -> {

            RegionDTO regionDTO = getRegionDTO(regionEntity);
            allRegion.add(regionDTO);
        });

        return allRegion;
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Region not found");
        });
    }

}
