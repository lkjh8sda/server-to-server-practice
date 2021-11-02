package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

    //바뀌는 body 서버에서 받기

    private Header header;
    private T responseBody;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header{
        private String responseCode;
    }
}
