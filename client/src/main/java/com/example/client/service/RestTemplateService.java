package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Service
public class RestTemplateService {
    //http://localhost/api/server/hello 호출해서 응답 받아올것
    //response
    public UserResponse hello(){
        //주소 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name", "lee")
                .queryParam("age", 28)
                .encode()
                .build()
                .toUri();

        //queryParam을 통해 쿼리 파라미터 입력 가능
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }

    public UserResponse post(){
        //http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100,"lee")
                .toUri();
        System.out.println(uri);

        //post는 http body를 보내야함
        // 그러나 object만 보내도 object mapper가 json으로 바꿔서
        // rest template이 http body에 json으로 넣어준다.
        UserRequest req = new UserRequest();
        req.setName("leee");
        req.setAge(30);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri,req, UserResponse.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }

    public UserResponse exchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100,"lee")
                .toUri();
        System.out.println(uri);

        //post는 http body를 보내야함
        // 그러나 object만 보내도 object mapper가 json으로 바꿔서
        // rest template이 http body에 json으로 넣어준다.
        UserRequest req = new UserRequest();
        req.setName("leee");
        req.setAge(30);


        //헤더 내용도 같이 보내기
        RequestEntity<UserRequest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","abcd")
                .header("custom-header","ffffff")
                .body(req);



        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity,UserResponse.class);
        return response.getBody();
    }

    public Req<UserResponse> genericExchange(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100,"lee")
                .toUri();
        System.out.println(uri);

        UserRequest userRequest = new UserRequest();
        userRequest.setName("leee");
        userRequest.setAge(30);

        Req<UserRequest> req = new Req<UserRequest>();
        req.setHeader(
            new Req.Header()
        );
        req.setResponseBody(
                //req에 대한 body의 내용은 userRequest
            userRequest
        );


        //헤더 내용도 같이 보내기
        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","abcd")
                .header("custom-header","ffffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();     //generic에는 class 못붙인다 Req<UserResponse>.class 불가능
        ResponseEntity<Req<UserResponse>> response
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>(){});
        return response.getBody();
    }

}
