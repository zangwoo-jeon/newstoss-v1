package com.newstoss.portfolio.application;

import com.newstoss.member.adapter.in.web.dto.requestDTO.AddressDTO;
import com.newstoss.member.adapter.in.web.dto.requestDTO.SignupRequestDTO;
import com.newstoss.member.adapter.out.persistence.JpaMemberRepository;
import com.newstoss.member.application.in.command.SignupService;
import com.newstoss.member.application.in.command.WithdrawService;
import com.newstoss.member.domain.Member;
import com.newstoss.portfolio.adapter.outbound.persistence.repository.JpaPortfolioRepository;
import com.newstoss.portfolio.entity.Portfolio;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PortfolioServiceTest {
    @Autowired
    SignupService signupService;
    @Autowired
    JpaPortfolioRepository repository;
    @Autowired
    PortfolioService service;
    @Autowired
    WithdrawService withdrawService;
    @Autowired
    EntityManager em;
    @Autowired
    JpaMemberRepository memberRepository;

    @Test
    public void CreateTestPortfolio(){
        //given
        AddressDTO addressDTO = new AddressDTO("222","@222","2222");
        SignupRequestDTO signupRequestDTO = new SignupRequestDTO("1","1","1","1","1",null,addressDTO);
        Member exec = signupService.exec(signupRequestDTO);

        //when
        Portfolio portfolio = repository.findByMemberId(exec.getMemberId()).get();
        assertThat(portfolio).isNotNull();

    }

    @Test
    public void find() {
        List<Member> members = memberRepository.findByName("1");
        Member member = members.get(0);

        Portfolio portfolio = repository.findByMemberId(member.getMemberId()).get();
        assertThat(portfolio).isNotNull();
    }
}