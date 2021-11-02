package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {

    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);

        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(
            //HttpEntity<String> httpEntity -> 클라이언트가 뭘 보냈는지 모를 떄 순수하게 엔티티 .getBody()로 찍어보면 디버그 가능
            @RequestBody Req<User> user
            , @PathVariable int userId
            , @PathVariable String userName
            , @RequestHeader("x-authorization") String authorization
            , @RequestHeader("custom-header") String customHeader){
        //log.info("req : {}",httpEntity);
        log.info("authorization : {}, customHeader: {}",authorization,customHeader);
        log.info("userId : {}, userName: {}",userId,userName);
        log.info("client req : {}",user);

        Req<User> response = new Req<>();
        response.setHeader(
                new Req.Header()
        );
        response.setResponseBody(
                user.getResponseBody()
        );

        return response;
    }

}
