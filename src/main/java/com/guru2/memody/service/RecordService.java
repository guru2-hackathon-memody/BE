package com.guru2.memody.service;

import com.guru2.memody.Exception.RecordNotFoundException;
import com.guru2.memody.Exception.UserNotFoundException;
import com.guru2.memody.dto.RecordDetailDto;
import com.guru2.memody.dto.RecordPinResponseDto;
import com.guru2.memody.entity.User;
import com.guru2.memody.entity.Record;
import com.guru2.memody.repository.RecordRepository;
import com.guru2.memody.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    public List<RecordPinResponseDto> getMeInMap(Long userId) {
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                UserNotFoundException::new
        );

        List<Record> records = recordRepository.findAllByUser(user);
        List<RecordPinResponseDto> recordPinResponseDtos = new ArrayList<>();
        for (Record record : records) {
            RecordPinResponseDto recordPinResponseDto = new RecordPinResponseDto();
            recordPinResponseDto.setRecordId(record.getRecordId());
            recordPinResponseDto.setThumbnailUrl(record.getRecordMusic().getThumbnailUrl());
            recordPinResponseDto.setLatitude(record.getLatitude());
            recordPinResponseDto.setLongitude(record.getLongitude());
            recordPinResponseDtos.add(recordPinResponseDto);
        }

        return recordPinResponseDtos;
    }

    public List<RecordPinResponseDto> getOtherInMap(Long userId) {
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                UserNotFoundException::new
        );

        List<Record> records = recordRepository.findAllByUserNot(user);
        List<RecordPinResponseDto> recordPinResponseDtos = new ArrayList<>();
        for (Record record : records) {
            RecordPinResponseDto recordPinResponseDto = new RecordPinResponseDto();
            recordPinResponseDto.setRecordId(record.getRecordId());
            recordPinResponseDto.setThumbnailUrl(record.getRecordMusic().getThumbnailUrl());
            recordPinResponseDto.setLatitude(record.getLatitude());
            recordPinResponseDto.setLongitude(record.getLongitude());
            recordPinResponseDtos.add(recordPinResponseDto);
        }

        return recordPinResponseDtos;
    }

    public RecordDetailDto getRecordDetail(Long recordId) {
        Record record = recordRepository.findById(recordId).orElseThrow(
                RecordNotFoundException::new
        );

        RecordDetailDto recordDetailDto = new RecordDetailDto();
        recordDetailDto.setTitle(record.getRecordMusic().getTitle());
        recordDetailDto.setArtist(record.getRecordMusic().getArtist());
        recordDetailDto.setContent(record.getText());
        recordDetailDto.setThumbnail(record.getRecordMusic().getThumbnailUrl());
        recordDetailDto.setSpotifyUrl(record.getRecordMusic().getSpotifyUrl());
        recordDetailDto.setITunesUrl(record.getRecordMusic().getAppleMusicUrl());
        recordDetailDto.setCreationDate(record.getRecordTime());
        return recordDetailDto;
    }
}
