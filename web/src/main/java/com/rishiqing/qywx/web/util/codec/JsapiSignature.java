package com.rishiqing.qywx.web.util.codec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;

public class JsapiSignature {
    public static String getSignature(String url, String nonceStr, Long timestamp, String ticket) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String plainTex = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        System.out.println(plainTex);
        String signature = "";

        MessageDigest e = MessageDigest.getInstance("SHA-1");
        e.reset();
        e.update(plainTex.getBytes("UTF-8"));
        signature = byteToHex(e.digest());
        System.out.println("----signature----" + signature);
        return signature;
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] result = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = result[var4];
            formatter.format("%02x", new Object[]{Byte.valueOf(b)});
        }

        String var6 = formatter.toString();
        formatter.close();
        return var6;
    }

    public static void main(String[] args) {
        String url = "http://qywx.etoutiao.cn/rsqdevapp";
        String nonce = "xxxxDDDDTTTTuuuu";
        Long timestamp = new Date().getTime()/1000;
        String ticket = "ppppPPPPKKKKLLLL";

        try {
            String sig = JsapiSignature.getSignature(url, nonce, timestamp, ticket);
            System.out.println(sig);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
