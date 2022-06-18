package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import com.company.enums.CategoryStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    Optional<CategoryEntity> findByNameEnOrNameRuOrNameUz(String nameEn, String nameRu, String nameUz);

    Optional<CategoryEntity> findByKeyAndStatusAndVisible(String key, CategoryStatus status, Boolean visible);

}
