package com.example.a526.ssj.util;

import android.util.JsonReader;

import com.example.a526.ssj.entity.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by asus on 2019/5/29.
 */

public class LogUtil {
    private String hostAndPort = "http://10.120.174.168:8080";
    private String loginUrl = "/login";
    private String registerUrl = "/rigister";

    public boolean login(String email, String password, Map<String, Object> map) {
        try {
            URL url = new URL(hostAndPort + loginUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 输入数据
            String data = "email=" + email + "&password=" + password;
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(data);
            dos.flush();
            dos.close();

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                // 读取数据
                StringBuffer sb = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());
                JSONObject jsonObject = new JSONObject(sb.toString());
                map.put("state", jsonObject.get("state"));
                map.put("message", jsonObject.get("message"));
                if (jsonObject.has("user")) {
                    JSONObject userObject = new JSONObject(jsonObject.getString("user"));
                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setName(userObject.getString("name"));
                    user.setPassword(userObject.getString("password"));
                    user.setEmail(userObject.getString("email"));
                    user.setState(userObject.getInt("state"));
                    map.put("user", user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean register(String name, String password, String email, Map<String, Object> map) {
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 输入数据
            String data = "name=" + name + "&password=" + password + "&email=" + email;
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(data);
            dos.flush();
            dos.close();

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                // 读取数据
                StringBuffer sb = new StringBuffer();
                String readLine;
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());
                JSONObject jsonObject = new JSONObject(sb.toString());
                map.put("state", jsonObject.get("state"));
                map.put("message", jsonObject.get("message"));
                if (jsonObject.has("user")) {
                    JSONObject userObject = new JSONObject(jsonObject.getString("user"));
                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setName(userObject.getString("name"));
                    user.setPassword(userObject.getString("password"));
                    user.setEmail(userObject.getString("email"));
                    user.setState(userObject.getInt("state"));
                    map.put("user", user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
// {"state":"success","message":"登陆成功","user":{"id":1,"name":"user001","password":"123456","email":"123@qq.com","state":1}}
