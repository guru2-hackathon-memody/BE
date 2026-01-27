package com.guru2.memody.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guru2.memody.Exception.UserNotFoundException;
import com.guru2.memody.dto.MusicListResponseDto;
import com.guru2.memody.entity.MusicLike;
import com.guru2.memody.entity.User;
import com.guru2.memody.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ITunesService {

    private final RestTemplate restTemplate = new RestTemplate();

    // iTunes BASE URL
    private static final String APPLE_SEARCH_URL = "https://itunes.apple.com/search"; // String 검색어로 검색할 때 사용
    private static final String APPLE_LOOKUP_URL = "https://itunes.apple.com/lookup"; // iTunesId 값으로 정확한 값을 검색할 때 사용

    // 검색어를 통한 음악 검색
    public String searchTrackWithItunes(String search) {
        String uri = UriComponentsBuilder.fromUriString(APPLE_SEARCH_URL)
                .queryParam("term", search)
                .queryParam("country", "KR")
                .queryParam("media", "music")
                .queryParam("lang", "ko_kr")
                .queryParam("entity", "song")
                .queryParam("limit", 20)
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    // 검색어를 통한 가수 검색
    public String searchArtistWithItunes(String search) {
        String uri = UriComponentsBuilder.fromUriString(APPLE_SEARCH_URL)
                .queryParam("term", search)
                .queryParam("country", "KR")
                .queryParam("media", "music")
                .queryParam("lang", "ko_kr")
                .queryParam("entity", "musicArtist")
                .queryParam("limit", 9)
                .toUriString();
        return restTemplate.getForObject(uri, String.class);
    }

    // 아티스트 id를 통한 가수 검색
    public String lookupArtistImageWithItunes(Long id) {
        String uri = UriComponentsBuilder.fromUriString(APPLE_LOOKUP_URL)
                .queryParam("id", id)
                .queryParam("country", "KR")
                .queryParam("entity", "song")
                .queryParam("attribute", "artistTerm")
                .queryParam("limit", 1)
                .toUriString();

        return restTemplate.getForObject(uri, String.class);
    }


    // iTunes -> spotify 외부 링크 매핑
    public String getSpotifyLinkFromItunes(String itunesUrl) throws JsonProcessingException {
        String encodedUrl = UriUtils.encode(itunesUrl, StandardCharsets.UTF_8);

        String uri = "https://api.song.link/v1-alpha.1/links?url=" + encodedUrl;

        String response = restTemplate.getForObject(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        return root
                .path("linksByPlatform")
                .path("spotify")
                .path("url")
                .asText();
    }

    // 노래 제목 + 가수명으로 음악 검색
    public String searchTrackWithClearInfo(String title, String artist){
        String term = title + " " + artist;

        URI uri = UriComponentsBuilder
                .fromUriString(APPLE_SEARCH_URL)
                .queryParam("term", term)
                .queryParam("media", "music")
                .queryParam("entity", "song")
                .queryParam("limit", 1)
                .queryParam("country", "KR")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }

    // 앨범명 + 가수명으로 음악 검색
    public String searchAlbumWithClearInfo(String title, String artist){
        String term = title + " " + artist;

        URI uri = UriComponentsBuilder
                .fromUriString(APPLE_SEARCH_URL)
                .queryParam("term", term)
                .queryParam("media", "music")
                .queryParam("entity", "album")
                .queryParam("limit", 1)
                .queryParam("country", "KR")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }

    // 앨범 id를 통한 수록곡 검색
    public String lookupTracksByAlbumId(Long id) {
        URI uri = UriComponentsBuilder
                .fromUriString(APPLE_LOOKUP_URL)
                .queryParam("id", id)
                .queryParam("media", "music")
                .queryParam("entity", "song")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }

}
