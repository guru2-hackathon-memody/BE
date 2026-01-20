package com.guru2.memody.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MusicDetailDto {
    private String thumbnailUrl;
    private String title;
    private String artistName;
    private String appleMusicUrl;
    private String spotifyMusicUrl;
    private Boolean liked;
    List<RecordListDto> record;
}
