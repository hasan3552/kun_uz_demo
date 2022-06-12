package com.company.entity;

import com.company.enums.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "types")
@NoArgsConstructor
public class TypesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "name_uz")
    private String nameUz;

    @Column(nullable = false, name = "name_ru")
    private String nameRu;

    @Column(nullable = false, name = "name_en")
    private String nameEn;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CategoryStatus status = CategoryStatus.ACTIVE;

    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;

    public TypesEntity(Integer id) {
        this.id = id;
    }
}
