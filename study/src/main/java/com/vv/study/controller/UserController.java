package com.vv.study.controller;

import com.vv.study.entity.User;
import com.vv.study.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @GetMapping(value="/users/{id}")
    public Optional<User> findUserById(@PathVariable("id") Integer id){
        try {
            //TODO：执行登录逻辑
            Optional<User> optionalUser=userService.findUserById(id);
            if (optionalUser.isPresent()){
                //TODO：异步写用户日志
                try {
                    User user = optionalUser.get();
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
                    rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));
                    Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(user)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    rabbitTemplate.convertAndSend(message);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //TODO：塞权限数据-资源数据-视野数据
            }else{
//                response=new BaseResponse(StatusCode.Fail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return userService.findUserById(id);
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Integer id,@RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @PostMapping("/users")
    public User insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        Optional<User> user = userService.findUserById(id);
        userService.deleteUser(user.get());
    }
}
