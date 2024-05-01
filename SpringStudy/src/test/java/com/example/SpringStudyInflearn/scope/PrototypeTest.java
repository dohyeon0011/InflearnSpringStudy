package com.example.SpringStudyInflearn.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {    // 클라이언트마다 새로운 빈 인스턴스를 생성하여 반환

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);    // AnnotationConfigApplicationContext()에 지정해주면 지정된 클래스 자체가 컴포넌트 대상 클래스처럼 작동하게 돼서 스프링 빈으로 등록해버림.

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBaen1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close(); // destroy() 실행 되지 않음 -> 프로토타입 스코프 빈은 스프링 컨테이너 생성, 의존관계 주입, 초기화까지만 관여하고 관리 하지 않기 때문에
        prototypeBean1.destroy();   // 종료 메서드를 호출하고 싶으면 직접 해야함.
        prototypeBean2.destroy();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
