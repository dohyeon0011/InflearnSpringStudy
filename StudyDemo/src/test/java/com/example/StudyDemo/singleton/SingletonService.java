package com.example.StudyDemo.singleton;

public class SingletonService {

    // 자기 자신을 내부에 private를 static으로 클래스 레벨로 올라가서 하나만 존재
    private static final SingletonService instance = new SingletonService();

    // public으로 열어서 인스턴스를 반환하는 메서드
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {
    }
    
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
