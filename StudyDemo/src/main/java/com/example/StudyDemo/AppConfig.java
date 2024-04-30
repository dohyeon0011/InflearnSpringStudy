package com.example.StudyDemo;

import com.example.StudyDemo.Member.MemberRepository;
import com.example.StudyDemo.Member.MemberService;
import com.example.StudyDemo.Member.MemberServiceImpl;
import com.example.StudyDemo.Member.MemoryMemberRepository;
import com.example.StudyDemo.discount.DiscountPolicy;
import com.example.StudyDemo.discount.FixDiscountPolicy;
import com.example.StudyDemo.discount.RateDiscountPolicy;
import com.example.StudyDemo.order.OrderService;
import com.example.StudyDemo.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 스프링 설정 클래스로 인식하고 @Bean 어노테이션으로 직접 빈을 정의 가능
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy()); // @Autowired 필드 주입하려고
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
