package com.example.StudyDemo.discount;

import com.example.StudyDemo.Member.Grade;
import com.example.StudyDemo.Member.Member;
import com.example.StudyDemo.annotation.MainDiscountPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
// @Qualifier("mainDiscountPolicy")    // 빈 이름을 변경하는 것이 아니라 추가 구분자 설정, 못 찾으면 추가로 mainDiscountPolicy라는 이름의 스프링 빈을 찾음
// @Primary 우선순위가 최상위(제일 먼저)로 잡힘, @Qualifier보다 더 자주 씀(@Qualifier는 코드가 지저분해져서), 주로 쓸 때 @Primary를 쓰다 가끔 쓰는 경우에 @Qualifier를 써서 이름을 붙여 가져와 쓰자 하는 경우 쓰임
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {

        if(member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }


}
