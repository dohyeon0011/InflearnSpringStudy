package com.example.StudyDemo.autowired;

import com.example.StudyDemo.Member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);


    }

    static class TestBean {

        @Autowired(required = false)    // false로 없으면 아예 호출이 안됨    
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {      // 없으면 null로 들어옴
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {  // Spring Bean이 없으면 Empty라는 옵션을 줌. 값이 있으면 옵션 안에 값이 감싸짐
            System.out.println("noBean1 = " + noBean3);
        }
    }


}
