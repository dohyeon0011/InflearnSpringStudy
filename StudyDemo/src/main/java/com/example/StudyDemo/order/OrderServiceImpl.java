package com.example.StudyDemo.order;

import com.example.StudyDemo.Member.Member;
import com.example.StudyDemo.Member.MemberRepository;
import com.example.StudyDemo.Member.MemoryMemberRepository;
import com.example.StudyDemo.annotation.MainDiscountPolicy;
import com.example.StudyDemo.discount.DiscountPolicy;
import com.example.StudyDemo.discount.FixDiscountPolicy;
import com.example.StudyDemo.discount.RateDiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
// @RequiredArgsConstructor
@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository; // 필드 주입을 하게 되면 setter를 생성해줘야 하고 외부 변경 불가, 스프링에 의존하지 않고 테스트하는데에도 안좋음, DI 프레임워크가 없으면 아무것도 할 수 없음.
    private final DiscountPolicy discountPolicy; // 스프링 설정 목적으로 하는 Test에선 용이, 생성자 주입을 사용하면 final 사용 가능

//    @Autowired  // 일반 메서드 주입, 보통 생성자에서 주입을 다 하기 때문에 잘 사용 안함, 스프링 빈인 클래스에서만 작동됨
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Autowired    // @RequiredArgsConstructor가 자동으로 만들어줌
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) { // @Qualifier("mainDiscountPolicy"), 생성자 자동주입 방식
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
