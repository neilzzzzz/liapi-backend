package com.example.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.example.model.User;
import com.example.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 名称 API
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/user")
    public String getUserName(@RequestParam String name){
        return "GET用户的名字是" +name;
    }
    @PostMapping("/json")
    public String postUserName(@RequestBody User user, HttpServletRequest request){
        //从请求头里面获取密钥
        String accesskey = request.getHeader("accesskey");
        String secretkey = request.getHeader("secretKey");
        String sign = request.getHeader("sign");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String body = request.getHeader("body");
        //1校验
          //1.1时间戳的校验
          //1.2nonce校验
          //1.3密钥校验
        if(!sign.equals(SignUtils.getSign(body,"abcedg"))){
            throw new RuntimeException("无权限");
        };
        return "POST用户的名字是" +user.getUsername();
    }
}
