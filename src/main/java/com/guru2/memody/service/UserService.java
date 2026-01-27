package com.guru2.memody.service;

import com.guru2.memody.Exception.*;
import com.guru2.memody.config.JwtTokenProvider;
import com.guru2.memody.dto.LoginRequestDto;
import com.guru2.memody.dto.RegionUpdateDto;
import com.guru2.memody.dto.SignUpDto;
import com.guru2.memody.dto.SignUpResponseDto;
import com.guru2.memody.entity.*;
import com.guru2.memody.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ArtistRepository artistRepository;
    private final ArtistPreferenceRepository artistPreferenceRepository;
    private final GenreRepository genreRepository;
    private final GenrePreferenceRepository genrePreferenceRepository;

    // 회원가입
    @Transactional
    public SignUpResponseDto signup(SignUpDto signUpDto) {
        if(userRepository.findUserByEmail(signUpDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        if(userRepository.findUserByName(signUpDto.getName()).isPresent()){
            throw new UserNameAlreadyExistsException();
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(encodedPassword);
        user.setName(signUpDto.getName());
        userRepository.save(user);

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setUserId(user.getUserId());

        return signUpResponseDto;
    }

    // 이메일 중복 확인
    public Boolean checkEmail(String email) {
        Boolean check = userRepository.findUserByEmail(email).isEmpty();
        return check;
    }

    // 닉네임 중복 확인
    public Boolean checkName(String name) {
        Boolean check = userRepository.findUserByName(name).isEmpty();
        return check;
    }

    // 로그인
    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findUserByEmail(loginRequestDto.getEmail()).orElseThrow(UserNotFoundException::new); // 이메일 없을 경우 404

        // 403 오류
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(loginRequestDto.getEmail());
        return token;

    }

    // 온보딩: 지역 설정
    @Transactional
    public String updateRegion(Long userId, RegionUpdateDto region) {
        Region reg = regionRepository.findFirstByFullNameContaining(region.getRegion());
        if(reg == null) {
            throw new RegionWrongException("Region not found");
        }
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UserNotFoundException()
        );

        user.setLocation(reg);

        // 현재 위치로 설정 누를 경우 위도 경도 저장
        if(region.getLatitude() == null && region.getLongitude() == null){

        } else {
            user.setLatitude(region.getLatitude());
            user.setLongitude(region.getLongitude());
            userRepository.save(user);
        }
        return "Patch Region Successful: " + reg.getFullName();

    }

    // 온보딩: 아티스트 선호
    @Transactional
    public String updateArtistPrefer(Long userId, List<String> artist) {
        List<Artist> atst = new ArrayList<>();
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UserNotFoundException()
        );
        artistPreferenceRepository.deleteAllByUser(user);
        for(String s : artist){
            atst.add(artistRepository.findOnboardingArtistByExactName(s).orElse(null));
        }
        for(Artist a : atst){
            ArtistPreference ap = new ArtistPreference();
            ap.setArtist(a);
            ap.setUser(user);
            artistPreferenceRepository.save(ap);
        }

        return "Patch Artist Successful: " + atst.toString();
    }

    // 온보딩: 선호 장르
    @Transactional
    public String updateGenrePrefer(Long userId, List<String> genre) {
        List<Genre> gst = new ArrayList<>();
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UserNotFoundException()
        );
        genrePreferenceRepository.deleteAllByUser(user);
        for(String s : genre){
            gst.add(genreRepository.findGenreByGenreName(s));
        }
        for(Genre g : gst){
            GenrePreference gp = new GenrePreference();
            gp.setGenre(g);
            gp.setUser(user);
            genrePreferenceRepository.save(gp);
        }
        return "Patch Genre Successful: " + gst.toString();
    }
}
