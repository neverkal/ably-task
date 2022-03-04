package com.ably.task.member.api.controller;

import com.ably.task.member.api.dto.request.*;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.entity.type.AuthType;
import com.ably.task.member.api.entity.type.OtpAuthType;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.repository.OtpAuthRepositorySupport;
import com.ably.task.member.api.service.AuthService;
import com.ably.task.member.config.security.WebSecurityConfig;
import com.ably.task.member.config.security.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({WebSecurityConfig.class, JwtTokenProvider.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AuthService authService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private OtpAuthRepositorySupport otpAuthRepositorySupport;

    ObjectMapper mapper = new ObjectMapper();
    Optional<Member> member = Optional.ofNullable(Member.builder()
            .email("hyeokmin22@gmail.com")
            .id(1L)
            .nickName("과녁")
            .personName("권혁민")
            .phoneNumber("01039599686")
            .password("1234")
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

    @Test
    public void memberLoginTest() throws Exception {
        given(authService.memberLogin(any(AuthLoginReq.class))).willReturn(true);
        given(memberRepository.findByEmail(any(String.class))).willReturn(member);
        given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(true);

        AuthLoginReq userLoginReq = new AuthLoginReq();

        userLoginReq.setEmail("hyeokmin22@gmail.com");
        userLoginReq.setPassword("12341!Asdf");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userLoginReq)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void memberJoinTest() throws Exception {
        given(authService.memberJoin(any(AuthRegisterReq.class))).willReturn(3L);
        given(otpAuthRepositorySupport.otpAuthPhoneNumber(
                any(String.class), any(LocalDateTime.class), any(AuthType.class)
        )).willReturn(OtpAuthType.SUCCESS);

        AuthRegisterReq userRegisterReq = new AuthRegisterReq();

        userRegisterReq.setEmail("hyeokmin22@gmail.com");
        userRegisterReq.setPassword("1234!gurals");
        userRegisterReq.setPersonName("권혁민");
        userRegisterReq.setOtpAuthId(1);
        userRegisterReq.setNickName("과녁");
        userRegisterReq.setPhoneNumber("01039599686");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/join")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRegisterReq)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void otpSendTest() throws Exception {
        AuthOtpSendReq authOtpSendReq = new AuthOtpSendReq();

        authOtpSendReq.setAuthType(AuthType.JOIN);
        authOtpSendReq.setPhoneNumber("01039599686");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/otp")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authOtpSendReq)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void otpCheckTest() throws Exception {
        AuthOtpReq authOtpReq = new AuthOtpReq();

        authOtpReq.setOtpAuthId(1L);
        authOtpReq.setOtpNumber("1234");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/auth/otp")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authOtpReq)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void passwordResetTest() throws Exception {
        MemberPasswordResetReq memberPasswordResetReq = new MemberPasswordResetReq();

        memberPasswordResetReq.setPassword("1234!gslsld");
        memberPasswordResetReq.setAuthOtpId(3L);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/auth/password")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberPasswordResetReq)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}