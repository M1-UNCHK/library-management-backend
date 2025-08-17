package sn.unchk.librarymanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sn.unchk.librarymanagement.config.TokenPropertiesTests;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ActiveProfiles("dev")
@Rollback
public class ControllerBaseTest {
    protected final ObjectMapper objectMapper;

    protected final MockMvc mockMvc;

    protected final TokenPropertiesTests tokenPropertiesTests;

    @Test
    void contextLoads() {
    }

    protected <T> String convert(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
