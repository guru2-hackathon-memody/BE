package com.guru2.memody.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "record_image")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecordImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordImageId;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    private String imageUrl;
}
