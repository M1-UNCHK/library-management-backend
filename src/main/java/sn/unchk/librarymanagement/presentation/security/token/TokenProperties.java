package sn.unchk.librarymanagement.presentation.security.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.token")
public record TokenProperties(
        String issuer,
        int delay

) {
}
