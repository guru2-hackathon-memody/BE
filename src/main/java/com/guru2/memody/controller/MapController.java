package com.guru2.memody.controller;

import com.guru2.memody.config.CustomUserDetails;
import com.guru2.memody.dto.RecordDetailDto;
import com.guru2.memody.dto.RecordPinResponseDto;
import com.guru2.memody.repository.MusicRepository;
import com.guru2.memody.repository.RecordRepository;
import com.guru2.memody.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
@Controller
public class MapController {
    private final RecordService recordService;

    @GetMapping("/me")
    public ResponseEntity<List<RecordPinResponseDto>> getMeInMap(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        List<RecordPinResponseDto> response = recordService.getMeInMap(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other")
    public ResponseEntity<List<RecordPinResponseDto>> getOtherInMap(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        List<RecordPinResponseDto> response = recordService.getOtherInMap(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordDetailDto> getRecordDetail(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long recordId) {
        Long userId = user.getUserId();
        RecordDetailDto recordDetailDto = recordService.getRecordDetail(userId, recordId);
        return ResponseEntity.ok(recordDetailDto);
    }
}
