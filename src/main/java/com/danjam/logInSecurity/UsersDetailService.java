package com.danjam.logInSecurity;

import com.danjam.users.Users;
import com.danjam.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsersDetailService implements UserDetailsService {
    private final UsersRepository userRepository;

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            System.out.println("loadUserByUsername email: " + email);
            Users user = userRepository.findByEmail(email);
            // https://velog.io/@hellozin/Spring-Security-Form-Login-%EA%B0%84%EB%8B%A8-%EC%82%AC%EC%9A%A9-%EC%84%A4%EB%AA%85%EC%84%9C-f2jzojj8bj
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            if (user.getRole().equals("ROLE_ADMIN")) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new UserDetail(user);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Not Found " + email);
        }
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            return userDetail.getUsername();
        }
        throw new UsernameNotFoundException("Not Found");
    }
}