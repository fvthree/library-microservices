package com.fvthree.app.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.fvthree.app.auth.UserTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .email(USER_EMAIL)
                .username(USER_USERNAME)
                .password(USER_PASSWORD)
                .build();
        userRepository.save(user);
    }

    @DisplayName("JUnit for find By Username then return the User Object")
    @Test
    public void givenUsername_whenFindByUsername_thenReturnUserObject() {

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));

        user = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User not found"));

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getUsername()).isEqualTo(USER_USERNAME);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
    }

    @DisplayName("JUnit for find By Username And Password Then Return the User Object")
    @Test
    public void givenUsername_whenFindByUsernameOrEmailAndPassword_thenReturnUserObject() {
        when(userRepository.findByUsernameOrEmailAndPassword(any(String.class), any(String.class), any(String.class))).thenReturn(Optional.ofNullable(user));


        user = userRepository.findByUsernameOrEmailAndPassword(user.getUsername(), user.getUsername(), user.getPassword())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User not found"));

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getUsername()).isEqualTo(USER_USERNAME);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
    }


    @DisplayName("JUnit for find By Email And Password Then Return the User Object")
    @Test
    public void givenEmail_whenFindByUsernameOrEmailAndPassword_thenReturnUserObject() {

        when(userRepository.findByUsernameOrEmailAndPassword(any(String.class), any(String.class), any(String.class))).thenReturn(Optional.ofNullable(user));

        user = userRepository.findByUsernameOrEmailAndPassword(user.getEmail(), user.getEmail(), user.getPassword())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User not found"));

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getUsername()).isEqualTo(USER_USERNAME);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
    }
}
