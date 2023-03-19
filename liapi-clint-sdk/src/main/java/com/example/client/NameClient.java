package com.example.client;



import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.model.User;
import com.example.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

public class NameClient {
    private String accesskey;
    private String secretkey;
    public NameClient(String accesskey, String secretKey){
        this.accesskey = accesskey;
        this.secretkey = secretKey;
    }

    private Map<String,String> getHeaderMap(String body){
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("accessKey","zhangsan");
        headerMap.put("secretKey","abcedg");
        headerMap.put("nonce", RandomUtil.randomNumbers(100));
        headerMap.put("body",body);
        headerMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        headerMap.put("sign", SignUtils.getSign(body,secretkey));
        return headerMap;
    }



    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String username = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        return "get请求的结果" + username;
    }

    public String getNameByPost(User user) {
        //转成json串
        String json = JSONUtil.toJsonStr(user);
        //返回响应
         HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/json")
                 .addHeaders(getHeaderMap(json))
                 .body(json)
                .execute();
        return "状态码为"+httpResponse.getStatus()+"并且"+"响应体为"+ httpResponse.body();
    }




}
