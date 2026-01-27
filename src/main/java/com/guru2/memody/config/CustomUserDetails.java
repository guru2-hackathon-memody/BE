package com.guru2.memody.config;

import com.guru2.memody.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/// 사용자 정보 구현체(Adapter)
///  DB에 저장된 커스텀 User 엔티티를 UserDetails 형태로 변환
/// 인증 과정에서 사용
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 계정 권한 목록 리턴(모두 ROLE_USER)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_USER");
    }


    ///  아래 메서드들은 계정 만료 등의 여부를 반환합니다.
    /// 이번 프로젝트에서는 별도의 만료 로직을 두지 않으므로 모두 true를 반환합니다.
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}


