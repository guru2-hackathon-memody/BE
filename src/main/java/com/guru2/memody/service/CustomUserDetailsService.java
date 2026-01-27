package com.guru2.memody.service;

import com.guru2.memody.config.CustomUserDetails;
import com.guru2.memody.entity.User;
import com.guru2.memody.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

///  사용자 정보 로드 서비스
/// 로그인 요청 시, DB에서 유저 정보를 조회하여 Spring Security에 전달하는 역할을 합니다.
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인 시 입력된 이메일로 유저 정보를 찾습니다.
    // Spring Security의 AuthenticationManager가 내부적으로 이 메서드를 호출합니다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + email));

        return new CustomUserDetails(user);
    }

}

