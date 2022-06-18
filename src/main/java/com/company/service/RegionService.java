package com.company.service;

import com.company.dto.region.RegionCreateDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.region.RegionGetDTO;
import com.company.dto.type.TypeDTO;
import com.company.entity.RegionEntity;
import com.company.entity.TypesEntity;
import com.company.enums.Language;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;
//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
//    private static Logger log = LoggerFactory.getLogger(RegionService.class);


    public RegionDTO create(RegionCreateDTO dto
//            , MultipartFile file,String designation
    ) {

        isValid(dto);

        RegionEntity regionEntity = new RegionEntity();
        save(dto,regionEntity
//                , file, designation
        );

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

    private void save(RegionCreateDTO dto, RegionEntity regionEntity
 //           , MultipartFile file,String designation
    ){
//
//        HttpHeaders headers = new HttpHeaders();
//
        regionEntity.setNameUz(dto.getNameUz());
        regionEntity.setNameRu(dto.getNameRu());
        regionEntity.setNameEn(dto.getNameEn());
        regionEntity.setKey("region_"+dto.getNameEn());
//      //  regionEntity.setImage();
//
//        String[] desg = designation.split(",");
//        String fileName = file.getOriginalFilename();
//        String filePath = Paths.get(uploadDirectory, fileName).toString();
//        String fileType = file.getContentType();
//        long size = file.getSize();
//        String fileSize = String.valueOf(size);
//      //  Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//
//        log.info("Name: " + dto.getNameEn());
//        log.info("Designation: " + desg[0]);
//        log.info("FileName: " + file.getOriginalFilename());
//        log.info("FileType: " + file.getContentType());
//        log.info("FileSize: " + file.getSize());
//
//        // Save the file locally
//        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
//            stream.write(file.getBytes());
//            stream.close();
//
////            employee.setName(dto.getNameEn());
////            employee.setDesignation(desg[0]);
////            employee.setFileName(fileName);
////            employee.setFilePath(filePath);
////            employee.setFileType(fileType);
////            employee.setFileSize(fileSize);
////          //  employee.setCreatedDate(currentTimestamp);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//

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

    public List<RegionGetDTO> getRegionListByLang(Language language) {

        List<RegionEntity> list = regionRepository.findAllByVisible(Boolean.TRUE);

        List<RegionGetDTO> regionGetDTOS = new ArrayList<>();

        list.forEach(regionEntity -> {
            RegionGetDTO dto = getRegionGetDTO(regionEntity, language);
            regionGetDTOS.add(dto);
        });

        return regionGetDTOS;
    }

    private RegionGetDTO getRegionGetDTO(RegionEntity regionEntity, Language language) {

        RegionGetDTO dto = new RegionGetDTO();

        switch (language){
            case UZ -> dto.setName(regionEntity.getNameUz());
            case RU -> dto.setName(regionEntity.getNameRu());
            case EN -> dto.setName(regionEntity.getNameEn());
        }

        dto.setKey(regionEntity.getKey());

        return dto;
    }

    //*********************** pagenation *********************************

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

        Page<RegionEntity> all = regionRepository.findAll(pageable);

        List<RegionEntity> list = all.getContent();

        List<TypeDTO> dtoList = new LinkedList<>();

        list.forEach(region -> {
            TypeDTO dto = new TypeDTO();
            dto.setId(region.getId());
            dto.setKey(region.getKey());
            dto.setNameUz(region.getNameUz());
            dto.setNameRu(region.getNameRu());
            dto.setNameEn(region.getNameEn());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList,pageable, all.getTotalElements());
    }

}
