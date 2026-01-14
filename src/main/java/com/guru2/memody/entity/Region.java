package com.guru2.memody.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @Column(length = 10)
    private Long code;   // 행정동 코드

    @Column(length = 30, nullable = false)
    private String name;
}

