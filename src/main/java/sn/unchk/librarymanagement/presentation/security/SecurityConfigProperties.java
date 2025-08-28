package sn.unchk.librarymanagement.presentation.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@ConfigurationProperties("security")
@Getter @Setter
public class SecurityConfigProperties {
    private UrlProperties url;
    private CorsProperties cors;
    private RsaKeyProperties rsa;

    @Getter @Setter
    public static class UrlProperties {
        private String authorized;
    }

    @Getter @Setter
    public static class CorsProperties {
        private List<String> allowedOrigins;
        private List<String> allowedMethods;
    }

    @Getter @Setter
    public static class RsaKeyProperties {
        private RSAPublicKey publicKey;
        private RSAPrivateKey privateKey;
    }
}
