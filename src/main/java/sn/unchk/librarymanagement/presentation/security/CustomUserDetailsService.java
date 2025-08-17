package sn.unchk.librarymanagement.presentation.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.presentation.exceptions.UnauthorizedException;
import sn.unchk.librarymanagement.repository.MemberRepository;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("Loading user:::: {}", username);
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

            if (!member.isActive())
                throw new DisabledException("Please activate your account before access to this resource.");

            Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()));

            return new User(username, member.getPassword(), authorities);

        } catch (NotFoundException | UnauthorizedException ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        }
    }
}
