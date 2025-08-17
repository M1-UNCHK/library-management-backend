package sn.unchk.librarymanagement.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Objects.nonNull(authentication) && authentication.isAuthenticated()
                ? Optional.of(authentication.getName())
                : Optional.empty();
    }
}
