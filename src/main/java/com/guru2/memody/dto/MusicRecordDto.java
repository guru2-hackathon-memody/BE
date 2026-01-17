package com.guru2.memody.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicRecordDto {
    Long musicId;
    String content;
    Double latitude;
    Double longitude;
}
