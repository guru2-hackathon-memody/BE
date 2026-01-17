package com.guru2.memody.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordPinResponseDto {
    private Long recordId;
    private String thumbnailUrl;
    private Double latitude;
    private Double longitude;
}
