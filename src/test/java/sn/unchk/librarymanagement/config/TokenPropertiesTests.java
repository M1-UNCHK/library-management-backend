package sn.unchk.librarymanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;

@ConfigurationProperties("security.token.test")
@ActiveProfiles("dev")
public record TokenPropertiesTests(String header, String value) {
}
