package com.guru2.memody.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MusicRecordDto {
    Long musicId;
    String content;
    Double latitude;
    Double longitude;
    List<MultipartFile> images;
}
