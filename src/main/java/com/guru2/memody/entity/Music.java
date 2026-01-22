package com.guru2.memody.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false, unique = true)
    private Long itunesId;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String spotifyUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String appleMusicUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String thumbnailUrl;
}
