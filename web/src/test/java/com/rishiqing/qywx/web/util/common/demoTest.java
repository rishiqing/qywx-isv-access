package com.rishiqing.qywx.web.util.common;

import com.rishiqing.qywx.service.common.crypto.CryptoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-09-30 22:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:web-test-spring-context.xml")
public class demoTest {

    @Autowired
    private CryptoUtil cryptoUtil;

    @Test
    public void demoTest(){

        try {
//            String token = "pf0d6MSbwSHFjj51WIYYlllw8NQdZ31H3wA7iNhn5U5c%2Fr9LQXVu1NN7ICJ%2B18nF";
//            String token = "zfcY1/nzKfGYVlF0HLfPsFisUq4Km6jmP+rNPfKCv9uncwRx5wD2esFRvJSzqF9U";
            String token = "5vkVeEiUhAZc3cziRmv_JYTvNmokpUL_GfI-nyclMCDwkyIFGOnqxvmIjC_ZX7VM";
//            System.out.println("encoded1 is: " + URLEncoder.encode("a+b", "UTF-8"));
//            System.out.println("encoded2 is: " + URLEncoder.encode("a b", "UTF-8"));
//            System.out.println("decoded1 is: " + URLDecoder.decode("a b", "UTF-8"));
//            System.out.println("decoded2 is: " + URLDecoder.decode("a%2Bb", "UTF-8"));
//            System.out.println("decoded3 is: " + URLDecoder.decode("a+b", "UTF-8"));
//            String orgString = URLDecoder.decode(token, "UTF-8");
//            System.out.println("decode function is: " + orgString + ", length is " + orgString.length());
            String outerId = "aaaaa--bbbb--" + new Date().getTime();
            String outerIdEncoded = cryptoUtil.encrypt(outerId);
            System.out.println("encoded is: " + outerIdEncoded);

            String decoded = cryptoUtil.decrypt(token);
            System.out.println("decoded is: " + decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
