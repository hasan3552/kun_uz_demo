package com.company.repository;


import com.company.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends PagingAndSortingRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByNameEnOrNameRuOrNameUz(String nameEN, String nameRu, String nameUz);

    List<RegionEntity> findAllByVisible(Boolean visible);

}
