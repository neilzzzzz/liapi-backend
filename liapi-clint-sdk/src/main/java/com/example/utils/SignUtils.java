package com.example.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {
    /**
     *生成签名(已加密)
     * @param body       用户参数
     * @param secretkey  用户密钥
     * @return
     */
    public static String getSign(String body, String secretkey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String testStr = body +"."+secretkey;
        // 5393554e94bf0eb6436f240a4fd71282
        String digestHex = md5.digestHex(testStr);
        return digestHex;


    }
}
