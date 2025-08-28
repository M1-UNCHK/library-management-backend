package sn.unchk.librarymanagement.presentation.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;
import sn.unchk.librarymanagement.presentation.dto.request.MemberLoginRequest;
import sn.unchk.librarymanagement.presentation.security.token.TokenService;
import sn.unchk.librarymanagement.service.member.MemberService;

import java.util.Map;

@RestController
@Slf4j
public class AuthControllerImpl implements AuthController{
    private final TokenService tokenService;
    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;

    public AuthControllerImpl(TokenService tokenService, MemberService memberService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<Map<String, String>> login(MemberLoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        MemberInfo memberInfo = memberService.retrieveMemberInfo(authentication.getName());

        log.info("authentication::: {} " , authentication);
        Map<String, String> response = tokenService.generateToken(memberInfo, authentication.getAuthorities());

        return ResponseEntity.ok(response);
    }
}
