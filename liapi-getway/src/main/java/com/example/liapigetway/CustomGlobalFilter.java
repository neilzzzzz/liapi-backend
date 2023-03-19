package com.example.liapigetway;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
   private static final List<String> IP_WHITE_LIST= Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        //1.用户发送请求到api网关

        //2.请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
         log.info("请求id"+request.getId());
        log.info("请求路径"+request.getPath());
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        //3.黑白名单()
        if(!IP_WHITE_LIST.contains(remoteAddress)){
           response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //4.用户鉴权（ak,ck是否合法）
        //从请求头里面获取签名和ak
        HttpHeaders headers = request.getHeaders();
        String accesskey = headers.getFirst("accesskey");
        String secretkey = headers.getFirst("secretKey");
        String sign = headers.getFirst("sign");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        if(!accesskey.equals("jinze")){
            throw new RuntimeException("无权限");
        }
        //超时
        Long currentTime = System.currentTimeMillis()/1000;
        Long FIVE_MINEUTES=60*5L;
        if(currentTime-Long.parseLong(timestamp) >= FIVE_MINEUTES){
             throw new RuntimeException("超时");
        }
        //5.请求的模拟接口是否合法
        //6.调用接口(重试 切换接口 接口降级) 有问题不应该写在这里
        //7.响应日志 调用成功+1 调用失败返回错误码



        return handleResponse(exchange,chain);
    }
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            //从交换机里面拿到对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if(statusCode == HttpStatus.OK){
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            //拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往字符串里面写数据
                            //拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                //8.调用成功 to do:接口调用次数+1
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
//                                log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                log.info("响应结果"+data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            //9.调用失败，返回规范错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关异常处理" + e);
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
