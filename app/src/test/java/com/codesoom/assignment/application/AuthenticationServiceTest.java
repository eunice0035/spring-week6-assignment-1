package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.InvalidAccessTokenException;
import com.codesoom.assignment.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthenticationServiceTest {
    private static final String SECRET = "12345678901234567890123456789012";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final String INVALID_TOKEN = VALID_TOKEN + "WRONG";
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp(){
        JwtUtil jwtUtil = new JwtUtil(SECRET);
        authenticationService = new AuthenticationService(jwtUtil);
    }

    @Test
    void login(){
        String accessToken = authenticationService.login();
        assertThat(accessToken).isEqualTo(VALID_TOKEN);
    }

    @Test
    void parseTokenwithValidToken(){
        Long userId = authenticationService.parseToken(VALID_TOKEN);
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void parseTokenwithInvalidToken() {
        assertThatThrownBy(() -> authenticationService.parseToken(INVALID_TOKEN))
                .isInstanceOf(InvalidAccessTokenException.class);
    }

    @Test
    void parseTokenWithBlankToken() {
        assertThatThrownBy(
                () -> authenticationService.parseToken(null)
        ).isInstanceOf(InvalidAccessTokenException.class);

        assertThatThrownBy(
                () -> authenticationService.parseToken("")
        ).isInstanceOf(InvalidAccessTokenException.class);

    }
}
