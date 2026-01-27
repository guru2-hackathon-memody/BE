package com.guru2.memody.extractData;

import com.guru2.memody.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("init")
@Slf4j
public class RegionInitRunner implements CommandLineRunner {

    private final RegionService regionService;

    // 지역 적재 로직 실행(VWorld)
    @Override
    public void run(String... args) throws Exception {
        log.info("InitRunner 실행");
        regionService.initRegions();
        log.info("initRunner 종료");
    }
}
