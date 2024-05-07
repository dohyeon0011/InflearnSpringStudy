package com.example.StudyDemo.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {  // 프로토타입 빈은 실무에서 거의 사용하지 않음(보통 싱글톤 빈을 사용하고, 새로운 빈이 필요한 경우 새롭게 생성하여 파라미터로 넘겨 해결)
    /*실무에서 자바 표준인 JSR-330 Provider를 사용할 것인지, 아니면 스프링이 제공하는 ObjectProvider 를 사용할 것인지 고민이 될 것이다.
    ObjectProvider는 DL(Dependency Lookup, 필요한 것만 의존관계 주입)을 위한 편의 기능을 많이 제공해주고 스프링 외에 별도의 의존관계 추가가 필요 없기 때문에 편리하다. 만약(정말 그럴일은 거의 없겠지만) 코드를 스프링이 아닌 다른 컨테이너에서도 사용할 수 있어야 한다면 JSR-330 Provider를 사용해야한다.
    스프링을 사용하다 보면 이 기능 뿐만 아니라 다른 기능들도 자바 표준과 스프링이 제공하는 기능이 겹칠때가 많이 있다. 대부분 스프링이 더 다양하고 편리한 기능을 제공해주기 때문에, 특별히 다른 컨테이너를 사용할 일이 없다면, 스프링이 제공하는 기능을 사용하면 된다.*/

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        prototypeBean1.addCount();
        prototypeBean1.getCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        prototypeBean2.getCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean;  // 생성 시점에 주입이 됨.(프로토타입 빈을 사용하지만 생성 후 클라이언트 빈이 내부적으로 관리하기 때문에 싱글톤 타입으로 한 개의 빈만이 존재)

        // 스프링 빈으로 등록한 적이 없지만, 스프링이 자동으로 빈으로 등록시켜줌
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;    // 옛날엔 ObjectFactory를 사용, ObjectFactory(인터페이스) -> ObjectProvider(구현체), Dependency Lookup(DL, 스프링 컨테이너에서 대신 빈을 조회 해주는 역할), 스프링에 의존적임

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;  // Optional하게 가져올 때나, A가 B를 의존하고 B가 A를 의존할 때(의존관계 순환)
        
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();    // getObject()를 호출한 시점에 스프링 컨테이너에서 프로토타입 빈을 찾아서 반환(DL)
            // 자바 라이브러리를 가져오는 것 외에는 간단하여 사용하기 편리하다.(스프링에 의존적이지 않고 자바 표준이므로 다른 컨테이너에서도 사용 가능)
            PrototypeBean prototypeBean = prototypeBeanProvider.get();  // get()을 통해 항상 새로운 프로토타입 빈이 생성, 내부에서 스프링 컨테이너를 통해 해당 빈을 찾아 반환(DL)
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            System.out.println("count = " + count);
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destory() {
            System.out.println("PrototypeBean.destroy");
        }

    }
}
