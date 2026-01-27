package com.guru2.memody.service;

import com.guru2.memody.extractData.VWorldClient;
import com.guru2.memody.dto.RegionFullName;
import com.guru2.memody.entity.Region;
import com.guru2.memody.repository.RegionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final VWorldClient vWorldClient;

    // 지역명 데이터 적재
    @Transactional
    public void initRegions() {
        List<RegionFullName> regionFN = vWorldClient.getRegions();

        for (RegionFullName regionFullName : regionFN) {
            Region region = Region.builder()
                    .code(regionFullName.getCode())
                    .fullName(regionFullName.getName())
                    .build();
            regionRepository.save(region);
        }
    }

    // 지역명 검색
    public ResponseEntity<List<String>> searchRegions(@RequestParam String region) {
        List<Region> regions = regionRepository.findAllByFullNameContaining(region);
        List<String> regionNames = new ArrayList<>();

        // 검색어가 없는 경우
        if (regions.isEmpty()) {
            return new ResponseEntity<>(regionNames, HttpStatus.OK);
        }

        for (Region reg : regions) {
            regionNames.add(reg.getFullName());
        }
        return new ResponseEntity<>(regionNames, HttpStatus.OK);
    }

    // 위경도를 넣으면 지역명을 반환하는 함수, 현재 사용 X
    public ResponseEntity<String> getRegion(@RequestParam Double lat, @RequestParam Double lon) {
        String response = vWorldClient.getRegionName(lat, lon);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
