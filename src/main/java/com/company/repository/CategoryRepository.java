package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    Optional<CategoryEntity> findByNameEnOrNameRuOrNameUz(String nameEn, String nameRu, String nameUz);

}
