package com.guru2.memody.service;

import com.guru2.memody.config.JwtTokenProvider;
import com.guru2.memody.dto.LoginRequestDto;
import com.guru2.memody.dto.SignUpDto;
import com.guru2.memody.entity.User;
import com.guru2.memody.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void signup(SignUpDto signUpDto) {
        if(userRepository.findUserByEmail(signUpDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(encodedPassword);
        user.setName(signUpDto.getName());
        userRepository.save(user);
    }

    public String login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(loginRequestDto.getEmail());
        return token;

    }


}
