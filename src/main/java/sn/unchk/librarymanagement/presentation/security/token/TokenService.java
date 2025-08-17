package sn.unchk.librarymanagement.presentation.security.token;

import org.springframework.security.core.GrantedAuthority;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;

import java.util.Collection;
import java.util.Map;

public interface TokenService {
    Map<String, String> generateToken(MemberInfo userInfo, Collection<? extends GrantedAuthority> authorities);
}
