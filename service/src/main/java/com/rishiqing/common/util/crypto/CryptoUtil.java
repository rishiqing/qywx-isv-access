package com.rishiqing.common.util.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 22:14
 */
public class CryptoUtil {
    private static String KEY = "Me2-d&ydnqIhdoeY.fcqQd9bgPo$#>uu";  // 32 bytes
    private static String INIT_VECTOR = "1Rd)<?efP%dwnvD*";  // 16 bytes

    public static String encrypt(String value){
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String encoded){
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encoded));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String str = new Date().getTime() + "--wxec002534a59ea2e7--002";
        System.out.println("origin string: " + str);

        String encoded = encrypt(str);
        System.out.println("encoded is: " + encoded + ", length is " + encoded.length());

        try {
            String encodedUrl = URLEncoder.encode(encoded, "UTF-8");
            System.out.println("after encoded url: " + encodedUrl);

            String decodedUrl = URLDecoder.decode(encoded, "UTF-8");
            System.out.println("after decoded url: " + decodedUrl);

            System.out.println("decoded is: " + decrypt(decodedUrl));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
