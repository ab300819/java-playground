package com.netty.demo.http.json.codec;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpJsonResponse {

    private FullHttpResponse response;
    private Object body;

    public HttpJsonResponse(FullHttpResponse response, Object body) {
        this.response = response;
        this.body = body;
    }

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
