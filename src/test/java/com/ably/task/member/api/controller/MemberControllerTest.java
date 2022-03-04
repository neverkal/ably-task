package com.ably.task.member.api.controller;

import com.ably.task.member.api.dto.response.MemberInfoRes;
import com.ably.task.member.api.entity.Member;
import com.ably.task.member.api.repository.MemberRepository;
import com.ably.task.member.api.service.MemberService;
import com.ably.task.member.config.security.WebSecurityConfig;
import com.ably.task.member.config.security.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import({WebSecurityConfig.class, JwtTokenProvider.class})
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

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
    @WithMockUser(roles="USER")
    public void memberInfoTest() throws Exception {
        MemberInfoRes memberInfoRes = MemberInfoRes.builder()
                .email("hyeokmin22@gmail.com")
                .nickName("과녁")
                .personName("권혁민")
                .phoneNumber("01039599686")
                .build();

//        given(memberService.joinMemberInfo()).willReturn(memberInfoRes);
        given(memberRepository.findByEmail(any(String.class))).willReturn(member);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/members")
                        .contentType(APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", "token")
                        .content("test"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}