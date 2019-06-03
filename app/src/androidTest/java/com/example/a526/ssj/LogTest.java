package com.example.a526.ssj;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.example.a526.ssj.entity.User;
import com.example.a526.ssj.util.LogUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LogTest {

    @Test
    public void helloServer()
    {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8080" + "");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 输入数据
//            String data = "email=" + email + "&password=" + password;
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            dos.writeBytes(data);
//            dos.flush();
//            dos.close();
            int code = conn.getResponseCode();
            System.out.println(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logIn() throws Exception {
        assertEquals(4, 2 + 2);
        LogUtil logUtil = new LogUtil();
        User user = new User();
        user.setEmail("123@qq.com");
        user.setName("user123");
        user.setPassword("123456");
        user.setState(1);
        Bundle bundle = new Bundle();
        logUtil.login(user.getEmail(), user.getPassword(), bundle);
        System.out.println("//todo");
    }

    @Test
    public void register() throws Exception {
        assertEquals(4, 2 + 2);
        LogUtil logUtil = new LogUtil();
        User user = new User();
        user.setEmail("123@qq.com");
        user.setName("user123");
        user.setPassword("123456");
        user.setState(1);
        HashMap<String, Object> map = new HashMap<>();
        logUtil.register(user.getName(), user.getPassword(), user.getEmail(), map);
        User serverUser = (User) map.get("user");
        System.out.println(serverUser);
    }
}
