package com.company.controller;

import com.company.dto.region.RegionCreateDTO;
import com.company.dto.region.RegionDTO;
import com.company.dto.region.RegionGetDTO;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader("Authorization") String jwt,
                                    @RequestBody RegionCreateDTO dto
//            ,                       @RequestParam("file") MultipartFile file,
//                                    @RequestParam("designation") String designation
    ) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.create(dto
//                , file, designation
        );

        return ResponseEntity.ok(regionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String jwt,
                                    @RequestBody RegionCreateDTO dto,
                                    @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO update = regionService.update(id, dto);

        return ResponseEntity.ok(update);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> changeVisible(@RequestHeader("Authorization") String jwt,
                                           @PathVariable("id") Integer id) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        RegionDTO regionDTO = regionService.changeVisible(id);

        return ResponseEntity.ok(regionDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRegion(@RequestHeader("Authorization") String jwt) {
        JwtUtil.decode(jwt, ProfileRole.ADMIN);
        List<RegionDTO> getAllRegion = regionService.getAll();

        return ResponseEntity.ok(getAllRegion);
    }

    @GetMapping("")
    public ResponseEntity<?> getListByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language){

        List<RegionGetDTO> list = regionService.getRegionListByLang(language);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getAllRegionPagination(//@RequestHeader("Authorization") String jwt,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language language,
                                                    @RequestParam(value = "page") Integer page,
                                                    @RequestParam(value = "size") Integer size){
    //    JwtUtil.decode(jwt, ProfileRole.ADMIN);
        PageImpl pagination = regionService.pagination(page, size);

        return ResponseEntity.ok(pagination);
    }

}
