package com.example.StudyDemo;

import com.example.StudyDemo.Member.MemberRepository;
import com.example.StudyDemo.Member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // 이 어노테이션이 붙은 클래스를 다 찾아서 스프링 빈으로 자동 등록, @Configuration이 붙은 설정 정보도 포함, 만약 지정하지 않으면 ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치
        basePackages = "com.example.StudyDemo.Member", 
        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // Configuration을 받는 클래스는 제외(기존에 만든 AppConfig와 충돌이 나서)
)   // @Component, @Controller, @Service, @Repository, @Configuration이 붙으면 다 스캔 대상
public class AutoAppConfig {    // 프로젝트 시작 루트 위치에 두는 것이 관례적인 방법
    /*@Bean(name = "memoryMemberRepository")  // 수동 빈이 자동 빈을 오버라이딩 해버림, 수동 빈 등록이 우선권을 가짐
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }*/
}
