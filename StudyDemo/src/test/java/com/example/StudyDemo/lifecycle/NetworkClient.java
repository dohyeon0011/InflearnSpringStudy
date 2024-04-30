package com.example.StudyDemo.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {    // InitializingBean, DisposableBean : 스프링 전용 인터페이스라 해당 코드가 스프링 전용 인터페이스에 의존한다., 메서드 이름 변경 x, 인터페이스를 사용하는 초기화, 종료 방법은 초기에나 사용하고 이젠 거의 사용 안함

    private String url; // 접속 해야 할 서버 url

    public NetworkClient() {
        System.out.println("생성자 호출 , url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

    // 어노테이션을 사용하는 걸 스프링에서 권장하고 있음
    @PostConstruct  // 편리하다, 컴포넌트 스캔과 잘 어울린다, 스프링이 아닌 다른 컨테이너에서도 동작함, 유일한 단점은 외부 라이브러리에는 적용 불가 -> 외부 라이브러리를 초기화, 종료 해야 하면 @Bean의 기능(initMethod, destroyMethod)을 사용해야 한다.
    public void init() throws Exception { // 의존 관계 주입이 끝나면 호출
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy // 어노테이션을 사용하는 걸 스프링에서 권장하고 있음
    public void close() throws Exception {    // 빈이 종료될 때
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
