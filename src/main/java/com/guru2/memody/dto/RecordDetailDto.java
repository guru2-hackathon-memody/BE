package com.guru2.memody.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecordDetailDto {
    private String title;
    private String artist;
    private String content;
    private LocalDateTime creationDate;
    private String thumbnail;
    private String spotifyUrl;
    private String iTunesUrl;
}
