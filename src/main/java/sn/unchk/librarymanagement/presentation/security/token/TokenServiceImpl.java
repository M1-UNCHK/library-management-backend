package sn.unchk.librarymanagement.presentation.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static sn.unchk.librarymanagement.constant.GlobalConstant.*;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenProperties tokenProperties;
    private final JwtEncoder jwtEncoder;
    @Override
    public Map<String, String> generateToken(MemberInfo memberInfo, Collection<? extends GrantedAuthority> authorities) {
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer(tokenProperties.issuer())
                .issuedAt(instant)
                .expiresAt(instant.plus(tokenProperties.delay(), ChronoUnit.DAYS))
                .subject(memberInfo.getUsername())
                .claim(SCOPE, memberInfo.getRole())
                .claim(USER_EMAIL, memberInfo.getEmail())
                .build();

        Map<String, String> token = new HashMap<>(0);
        token.put(ACCESS_TOKEN, jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue());
        return token;
    }
}
