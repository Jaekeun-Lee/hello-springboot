package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {


    //이렇게 new키워드로 생성하면 MemberService에서 사용하는 MemoryMemberRepository와 다른 인스턴스가 된다.
    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    // 테스트코드명은 실제 코드에 포함되지 않기 때문에 한글로 가독성 좋게 만들어도 된다.
    @Test
    void 회원가입() {

        //given
        Member member = new Member();
        member.setName("Jack");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("Jack");
        Member member2 = new Member();
        member2.setName("Jack");

        //when
        memberService.join(member1);

    /* //방법 1 try-catch

        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

    */

        //방법2
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
    }

    @Test
    void findOne() {
    }
}