package com.example.temi.service;

import com.example.temi.pojo.InfoVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StringService {
    private String val = "hidden value";

    public String getVal(String str) {
        return val+ str;
    }
    public String getVal(){
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    //db를 사용하지 않는 데이터 저장 예시========================

    public StringService() {//Constructor
        this.map = new HashMap<String, InfoVo>();
    }

    HashMap<String, InfoVo> map;
    public void createInfo(String name,String val){
        map.put(name,new InfoVo(name,val));//name으로 객체 내용이 검색가능하다.
        //여기서 InfoVo는 Struct와 비슷한 역할을 한다.
    }
}
