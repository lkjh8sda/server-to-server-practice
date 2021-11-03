package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {

    //네이버 지역검색 api https://openapi.naver.com
    // ?query=%EA%B0%88%EB%B9%84%EC%A7%91
    // &display=10
    // &start=1
    // &sort=random
    @GetMapping("/naver")
    public String naver(){

        String query = "중국집";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",query)
                .queryParam("display",10)
                .queryParam("start",1)
                .queryParam("sort","random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}",uri);

        RestTemplate restTemplate = new RestTemplate();

        //들어갈거 없으면 void
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","I93NrM1cr4oKyomK2C3P")
                .header("X-Naver-Client-Secret","CLlk0qXN2F")
                .build();

        ResponseEntity<String> result
                = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

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
