package sk.garwan.pecserke.eshop.identity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import sk.garwan.pecserke.eshop.config.WebConfig;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;
import sk.garwan.pecserke.eshop.identity.service.UserService;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(WebConfig.class)
class RegistrationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void register() throws Exception {
        mvc
            .perform(
                post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"registration_user\",\"email\":\"registration_user@example.com\",\"password\":\"registration_user\"}")
            )
            .andDo(log())
            .andExpect(status().isCreated());

        var user = userRepository.findByUsername("registration_user").orElseThrow(IllegalStateException::new);
        assertThat(user.getEmail()).isEqualTo("registration_user@example.com");
        assertThat(user.isAdmin()).isFalse();
        assertThat(passwordEncoder.matches("registration_user", user.getPassword())).isTrue();
    }

    @Test
    @Transactional
    void registerDuplicateUsername() throws Exception {
        userService.createUser("registration_user2", "registration_user_2@example.com", false, "");

        mvc
            .perform(
                post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"registration_user2\",\"email\":\"registration_user2@example.com\",\"password\":\"registration_user2\"}")
            )
            .andDo(log())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void registerDuplicateEmail() throws Exception {
        userService.createUser("registration_user3", "registration_user3@example.com", false, "");

        mvc
            .perform(
                post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"registration_user3\",\"email\":\"registration_user3@example.com\",\"password\":\"registration_user3\"}")
            )
            .andDo(log())
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void registerShortPassword() throws Exception {
        userService.createUser("registration_user4", "registration_user4@example.com", false, "");

        mvc
            .perform(
                post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"registration_user4\",\"email\":\"registration_user4@example.com\",\"password\":\"short\"}")
            )
            .andDo(log())
            .andExpect(status().isBadRequest());
    }
}
