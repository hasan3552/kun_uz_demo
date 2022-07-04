package com.company.controller;

import com.company.dto.region.RegionCreateDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.region.RegionGetDTO;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Api(value = "Region Controller")
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "Region create method")
    @PostMapping("/adm")
    public ResponseEntity<?> create(
            //@RequestHeader("Authorization") String jwt,
            @RequestBody RegionCreateDTO dto
//            ,                       @RequestParam("file") MultipartFile file,
//                                    @RequestParam("designation") String designation
    ) {
        //       JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.create(dto
//                , file, designation
        );

        log.info("Request for region create dto:{}", dto);
        return ResponseEntity.ok(regionDTO);
    }

    @ApiOperation(value = "Region update method")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(
            //@RequestHeader("Authorization") String jwt,
            @RequestBody RegionCreateDTO dto,
            @PathVariable("id") Integer id) {
        //    JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO update = regionService.update(id, dto);
        log.info("Request for region update dto{}, id{}", dto, id);
        return ResponseEntity.ok(update);

    }

    @ApiOperation(value = "Region change visible method")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(
            //@RequestHeader("Authorization") String jwt,
            @PathVariable("id") Integer id) {

        //    JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.changeVisible(id);
        log.info("Request for region deleted regionId: {}", id);
        return ResponseEntity.ok(regionDTO);
    }

    @ApiOperation(value = "Region list method")
    @GetMapping("/adm/list")
    public ResponseEntity<?> getAllRegion(
            //        @RequestHeader("Authorization") String jwt
    ) {
        //   JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<RegionDTO> getAllRegion = regionService.getAll();
        log.info("Request for region list by admin");
        return ResponseEntity.ok(getAllRegion);
    }


    @ApiOperation(value = "Region list by language method")
    @GetMapping("")
    public ResponseEntity<?> getListByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                           Language language) {

        List<RegionGetDTO> list = regionService.getRegionListByLang(language);
        log.info("Request for region list by language :{}", language);
        return ResponseEntity.ok(list);
    }

//     13. Get Article list by Region Key (Pagination)
//    ArticleShortInfo

    @ApiOperation(value = "Region pagination method")
    @GetMapping("/pagination")
    public ResponseEntity<?> getAllRegionPagination(//@RequestHeader("Authorization") String jwt,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language,
                                                    @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size) {
        //    JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl pagination = regionService.pagination(page, size);
        log.info("Request for region pagination size:{}, page:{}, language:{}", size, page, language);
        return ResponseEntity.ok(pagination);
    }

}
