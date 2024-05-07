package com.example.StudyDemo.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// ProxyMode : MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해둠, 싱글톤처럼 동작
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)   // value 생략 가능하지만,값이 2개 이상 들어가야할땐 명시해줘야 함
public class MyLogger { // 로그를 출력하기 위한 클래스, HTTP 요청마다 하나 씩 생성(스프링 컨테이너를 요청하는 시점에서)

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString(); // 랜덤 uuid를 생성(전세계적으로 겹치지 않는 유일한 값), 이 uuid로 다른 HTTP 요청과 구별
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }

}
