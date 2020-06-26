package com.example.temi.controller;

import com.example.temi.service.ServersideFireBaseService;
import com.example.temi.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class APIRestController {
    @Autowired
    private StringService service;
    @Autowired
    private ServersideFireBaseService service2;

    @GetMapping("/{val}")
    public String getRest(@PathVariable("val")String value1){
        return service.getVal(value1);
    }
    @PostMapping("/post")
    public void getPost(@RequestParam("value1") String str1){
        service.setVal(str1);
    }

    @GetMapping("/firebase")
    public String sendFireBaseMsg(@RequestParam(value = "title", required = false, defaultValue = "empty title") String title,
                                  @RequestParam(value = "content",required = false,defaultValue = "empty content") String content){
        try {
            return service2.sendNotification(title, content, "topic");
        }catch (Exception e){// 메시지 송신을 실패할 경우
            return "fail to send";
        }
    }
}
