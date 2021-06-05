package com.yqh.eurekaconsumer;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @Auther：yqh
 * @Date：2021/5/31
 * @Description：创建
 * @Version：1.0
 */

public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        System.out.println("拦截到");
        System.out.println(httpRequest.getURI());

        ClientHttpResponse execute = clientHttpRequestExecution.execute(httpRequest, bytes);
//        System.out.println(execute.getHeaders());
        return execute;
    }
}
