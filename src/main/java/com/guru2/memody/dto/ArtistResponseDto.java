package com.guru2.memody.dto;

import com.guru2.memody.entity.Artist;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArtistResponseDto {
    private Long artistId;
    private String artistName;
    private String imageUrl;
}
