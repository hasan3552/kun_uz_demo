package com.company.entity;

import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String emile;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private Boolean visible = Boolean.TRUE;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status = ProfileStatus.ACTIVE;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;


    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

}