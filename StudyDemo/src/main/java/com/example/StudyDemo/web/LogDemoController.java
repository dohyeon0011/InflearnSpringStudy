package com.example.StudyDemo.web;

import com.example.StudyDemo.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;    // 가짜 껍데기 프록시 객체 주입, 싱글톤처럼 동작, 실제 요청이 들어오기 전까지 진짜 객체를 생성하지 않고 지연시키기 위한 목적

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();   // getObject()하는 이 시점에 최초 생성, Provider 덕분에 http 요청이 오고 난 후에 빈 생성이 되도록 지연시킬 수 있었음.
        myLogger.setRequestURL(requestURL); // 실제 요청이 들어온 시점에 진짜 객체 주입
        System.out.println("myLogger = " + myLogger.getClass());

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");

        return "OK";
    }

}
