package sn.unchk.librarymanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.security.RsaKeyProperties;
import sn.unchk.librarymanagement.presentation.security.token.TokenProperties;
import sn.unchk.librarymanagement.service.member.MemberService;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties({RsaKeyProperties.class, TokenProperties.class})
@Slf4j
public class LibraryManagementApplication {
    public static final String ADMIN_USERNAME = "diack";
    public static final String ADMIN_PASSWORD = "diack@123";
    public static final String ADMIN_EMAIL = "m.diackk@gmail.com";
    public static final String READER_USERNAME = "beni";
    public static final String READER_PASSWORD = "beni@123";
    public static final String READER_EMAIL = "djongnabeb@gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

 //   @Bean
    CommandLineRunner runner(MemberService memberService) {
        return args -> {
            if (memberService.retrieveAllMembers().isEmpty()) {
                saveMembers(memberService);
                log.info("MEMBERS SAVED SUCCESSFUL");
            }
        };
    }

    private void saveMembers(MemberService memberService) {
        MemberRequest admin = MemberRequest.builder()
                .firstname("Mouhamad")
                .lastname("DIACK")
                .username(ADMIN_USERNAME)
                .email(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .address("Dakar")
                .phoneNumber("780010101")
                .role(MemberRole.ADMIN)
                .build();

        memberService.createAdmin(admin);

        MemberRequest reader = MemberRequest.builder()
                .firstname("Djongnabe")
                .lastname("Beni")
                .username(READER_USERNAME)
                .email(READER_EMAIL)
                .password(READER_PASSWORD)
                .address("Dakar")
                .phoneNumber("780010102")
                .role(MemberRole.READER)
                .build();

        memberService.createReader(reader);
    }


}
