package sn.unchk.librarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import sn.unchk.librarymanagement.config.TokenPropertiesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ConfigurationPropertiesScan
@EnableConfigurationProperties({TokenPropertiesTests.class})
class LibraryManagementApplicationTests {

    @Test
    void contextLoads() {
    }
}
