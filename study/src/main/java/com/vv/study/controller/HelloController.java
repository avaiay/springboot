package com.vv.study.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/rabbitmq")
public class HelloController {

    @RequestMapping(value = "sayHello", method = RequestMethod.GET)
    public String sayHello(String name){
        return "Hello " + name;
    }
}
