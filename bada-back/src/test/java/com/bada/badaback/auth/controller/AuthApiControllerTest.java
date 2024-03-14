package com.bada.badaback.auth.controller;

import com.bada.badaback.auth.dto.AuthJoinRequestDto;
import com.bada.badaback.auth.dto.AuthSignUpRequestDto;
import com.bada.badaback.auth.dto.LoginResponseDto;
import com.bada.badaback.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.bada.badaback.feature.MemberFixture.SUNKYOUNG;
import static com.bada.badaback.feature.TokenFixture.ACCESS_TOKEN;
import static com.bada.badaback.feature.TokenFixture.REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Auth [Controller Layer] -> AuthApiController 테스트")
class AuthApiControllerTest extends ControllerTest {
    @Nested
    @DisplayName("회원가입(새로운 가족 그룹 생성) API [POST /api/auth/signup]")
    class signup {
        private static final String BASE_URL = "/api/auth/signup";

        @Test
        @DisplayName("회원가입에 성공한다")
        void success() throws Exception {
            // given
            LoginResponseDto loginResponseDto = createLoginResponseDto();
            doReturn(1L)
                    .when(authService)
                    .signup(any(), any(), any(), any(), anyInt(), any(), any());
            doReturn(loginResponseDto)
                    .when(authService)
                    .login(anyLong());

            // when
            final AuthSignUpRequestDto request = createAuthSignUpRequestDto();
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content(convertObjectToJson(request));

            // then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk()
                    );
        }
    }

    @Nested
    @DisplayName("회원가입(기존 가족 그룹 가입) API [POST /api/auth/join]")
    class join {
        private static final String BASE_URL = "/api/auth/join";

        @Test
        @DisplayName("회원가입에 성공한다")
        void success() throws Exception {
            // given
            LoginResponseDto loginResponseDto = createLoginResponseDto();
            doReturn(1L)
                    .when(authService)
                    .signup(any(), any(), any(), any(), anyInt(), any(), any());
            doReturn(loginResponseDto)
                    .when(authService)
                    .login(anyLong());

            // when
            final AuthJoinRequestDto request = createAuthJoinRequestDto();
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .content(convertObjectToJson(request));

            // then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk()
                    );
        }
    }

    private AuthSignUpRequestDto createAuthSignUpRequestDto() {
        return new AuthSignUpRequestDto(SUNKYOUNG.getName(), SUNKYOUNG.getPhone(), SUNKYOUNG.getEmail(), "NAVER",
                SUNKYOUNG.getIsParent(), SUNKYOUNG.getProfileUrl(), SUNKYOUNG.getFamilyCode());
    }

    private AuthJoinRequestDto createAuthJoinRequestDto() {
        return new AuthJoinRequestDto(SUNKYOUNG.getName(), SUNKYOUNG.getPhone(), SUNKYOUNG.getEmail(), "NAVER",
                SUNKYOUNG.getIsParent(), SUNKYOUNG.getProfileUrl(), "인증코드");
    }

    private LoginResponseDto createLoginResponseDto() {
        return new LoginResponseDto(1L, SUNKYOUNG.getName(), ACCESS_TOKEN, REFRESH_TOKEN);
    }
}

