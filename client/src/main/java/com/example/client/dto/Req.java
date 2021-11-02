package com.example.client.dto;

public class Req<T> {

//헤더는 고정값으로 들어오는데 바디는 계속 바뀌는 경우 디자인
/*
    {
        "header" : {
            "response_code" : "OK"
        },
        "responseBody" : {
            "book" : "spring boot",          "name" : "lee"
            "page" : 1024          ->        "age" : 24
        }
    }
 */

    private Header header;
    //계속 변하는 body는 제네릭 타입 T 으로 받으면 됨
    private T responseBody;


    //header는 그대로 받고
    public static class Header{
        private String responseCode;

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "Req{" +
                "header=" + header +
                ", responseBody=" + responseBody +
                '}';
    }
}
