package sn.unchk.librarymanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.mail")
@Getter
@Setter
public class EmailConfigProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
