package com.example.temi.controller;

import com.example.temi.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    StringService service;

    @GetMapping("/t")
    public String testPage(){
        return "test";
    }
    @GetMapping("/mapping")
    public String testMapping(Model model){
        String value = "value to send";
        model.addAttribute("text",value);
        model.addAttribute("text1",value+"1");
        return "mapping";
    }
    @GetMapping("/mapping2")
    public String testMapping2(Model model){
        String value = service.getVal();
        model.addAttribute("text",value);
        model.addAttribute("text1",service.getVal("1"));
        return "mapping";
    }

    @GetMapping("/param")
    public String getparam(@RequestParam("value1") String str1,
                           @RequestParam(value = "value2", required = false, defaultValue = "nodata") String str2,
                           Model m){
        m.addAttribute("text",str1);
        m.addAttribute("text1",str2);
        return "mapping";
    }

    @GetMapping("/path/{value3}")
    public String getPath(@PathVariable("value3")String value1,Model m){
        m.addAttribute("text",value1);
        m.addAttribute("text1", service.getVal(" "+value1));
        return "mapping";
    }

}
