package com.example.StudyDemo;

import com.example.StudyDemo.Member.Grade;
import com.example.StudyDemo.Member.Member;
import com.example.StudyDemo.Member.MemberService;
import com.example.StudyDemo.Member.MemberServiceImpl;
import com.example.StudyDemo.order.Order;
import com.example.StudyDemo.order.OrderService;
import com.example.StudyDemo.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 12000);

        System.out.println("order = " + order);
        System.out.println("order = " + order.calculatePrice());
        System.out.println("discount = " + order.getDiscountPrice());

    }
}
