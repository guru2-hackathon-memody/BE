package com.guru2.memody.controller;

import com.guru2.memody.config.CustomUserDetails;
import com.guru2.memody.dto.LoginRequestDto;
import com.guru2.memody.dto.RegionUpdateDto;
import com.guru2.memody.dto.SignUpDto;
import com.guru2.memody.dto.SignUpResponseDto;
import com.guru2.memody.entity.Region;
import com.guru2.memody.repository.UserRepository;
import com.guru2.memody.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Controller
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpDto signUpDto){
        SignUpResponseDto response = userService.signup(signUpDto);
        return ResponseEntity.ok(response);
    }

    // 이메일 중복 확인
    @GetMapping("/checkemail")
    ResponseEntity<Boolean> check(@RequestParam String email){
        Boolean response = userService.checkEmail(email);
        return ResponseEntity.ok(response);
    }

    // 닉네임 중복 확인
    @GetMapping("/checkname")
    ResponseEntity<Boolean> checkName(@RequestParam String name){
        Boolean response = userService.checkName(name);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }

    // 온보딩: 지역
    @PatchMapping("/region")
    public ResponseEntity<String> updateRegion(@RequestParam Long userId,
                                               @RequestBody RegionUpdateDto region) {
        String response = userService.updateRegion(userId, region);
        return ResponseEntity.ok(response);
    }

    // 온보딩: 가수
    @PatchMapping("/artist")
    public ResponseEntity<String> updateArtistPrefer (@RequestParam Long userId,
                                                      @RequestParam List<String> artist) {
        String response = userService.updateArtistPrefer(userId, artist);
        return ResponseEntity.ok(response);
    }

    // 온보딩: 장르
    @PatchMapping("/genre")
    public ResponseEntity<String> updateGenrePrefer (@RequestParam Long userId,
                                                     @RequestParam List<String> genre) {
        String response = userService.updateGenrePrefer(userId, genre);
        return ResponseEntity.ok(response);
    }

}
