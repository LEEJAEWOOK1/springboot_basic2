package com.ex01.basic.service;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemRepository memRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("auth service username : " +username);
        MemberEntity memberEntity = memRepository
                .findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("로그인 실패"));
        return new CustomUserDetails(
                memberEntity.getId(),
                memberEntity.getUsername(),
                memberEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+memberEntity.getRole()))
        ); //우리가 만든걸로 변경(customUserDetails)
        /*
        return User.builder()
                .username(memberEntity.getUsername())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getRole())
                .build();
         */
    }
}
