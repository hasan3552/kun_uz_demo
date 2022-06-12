package com.company.entity;

import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;
}
