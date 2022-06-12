package com.company.repository;

import com.company.entity.TypesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<TypesEntity,Integer> {
    Optional<TypesEntity> findByNameEnOrNameRuOrNameUz(String nameEn, String nameRu, String nameUz);

}
