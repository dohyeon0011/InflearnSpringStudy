package com.example.StudyDemo.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // 스프링 컨테이너 종료
    }

    @Configuration
    static class LifeCycleConfig {

        // 스프링 빈이 스프링 코드에 의존x, 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화,종료 메서드를 적용할 수 있다.
        @Bean(initMethod = "init", destroyMethod = "") // destroyMethod는 기본 값이 "(infrred)"(추론)으로 등록되어 있어 외부 라이브러리에서 대부분 종료 메서드의 이름으로 사용하는 "close", "shutdown"라는 이름의 메서드를 자동으로 호출해줘서 ""처럼 비워놔도 잘 동작함
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
