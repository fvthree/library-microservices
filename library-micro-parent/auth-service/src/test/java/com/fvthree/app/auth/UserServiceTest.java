package com.fvthree.app.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static com.fvthree.app.auth.UserTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static Logger logger = LogManager.getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();;

    @InjectMocks
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .username(USER_USERNAME)
                .email(UserTestConstants.USER_EMAIL)
                .password(UserTestConstants.USER_PASSWORD)
                .build();
    }

    @DisplayName("JUnit for register")
    @Test
    public void givenMap_whenRegister_thenReturnUserObj() {

        User resultingUser = user;

        Map<String, String> request = Map.of("username",USER_USERNAME,"email",USER_EMAIL,"password",USER_PASSWORD);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.register(request);

        Assertions.assertThat(createdUser).isNotNull();
        Assertions.assertThat(resultingUser.getUsername()).isEqualTo(USER_USERNAME);
        Assertions.assertThat(resultingUser.getEmail()).isEqualTo(UserTestConstants.USER_EMAIL);
        Assertions.assertThat(resultingUser.getPassword()).isEqualTo(UserTestConstants.USER_PASSWORD);
    }

    @DisplayName("JUnit for login")
    @Test
    public void givenMap_whenLogin_thenReturnAccessToken() {
        var request = Map.of("username",USER_USERNAME,"password", USER_PASSWORD, "email", USER_EMAIL);

        JwtAccessToken jwtAccessToken = JwtAccessToken.builder()
                .accessToken("f09f9f02f2ff")
                .refreshToken("f9f928f82rff4")
                .build();

        when(encoder.encode(any(CharSequence.class))).thenReturn(USER_PASSWORD);
        when(userRepository.findByUsernameOrEmailAndPassword(any(String.class), any(String.class), any(String.class)))
                .thenReturn(Optional.ofNullable(user));
        when(jwtTokenUtil.generateToken(any(Authentication.class))).thenReturn(jwtAccessToken.getAccessToken());

        JwtAccessToken loggedInUser = userService.login(request);

        Assertions.assertThat(loggedInUser).isNotNull();
        Assertions.assertThat(loggedInUser.getAccessToken()).isEqualTo(jwtAccessToken.getAccessToken());

        logger.info(loggedInUser);

    }



}
