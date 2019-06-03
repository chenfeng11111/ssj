package com.example.a526.ssj.util;

import android.os.Bundle;
import android.util.JsonReader;

import com.example.a526.ssj.entity.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by asus on 2019/5/29.
 */

public class LogUtil {
    private String hostAndPort = "http://10.0.2.2:8080";
    private String loginUrl = "/user/login";
    private String registerUrl = "/user/register";

    public boolean login(String email, String password, Bundle bundle) {
        try {
            URL url = new URL(hostAndPort + loginUrl);
            System.out.println(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
           // conn.connect();

            // 输入数据
            String data = "email=" + email + "&password=" + password;
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(data);
            dos.flush();
            dos.close();

            int code = conn.getResponseCode();
            System.out.println("code:" + code);
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
                bundle.putString("state", jsonObject.getString("state"));
                bundle.putString("message", jsonObject.getString("message"));
                if (jsonObject.has("user")) {
                    JSONObject userObject = new JSONObject(jsonObject.getString("user"));
                    ArrayList<String> userData = new ArrayList<>();
                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setName(userObject.getString("name"));
                    user.setPassword(userObject.getString("password"));
                    user.setEmail(userObject.getString("email"));
                    user.setState(userObject.getInt("state"));
                    userData.add(user.getId().toString());
                    userData.add(user.getName());
                    userData.add(user.getPassword());
                    userData.add(user.getEmail());
                    userData.add(user.getState().toString());
                    bundle.putStringArrayList("user", userData);
                }

            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8")); // 实例化输入流，并获取网页代码
                String s; // 依次循环，至到读的值为空
                StringBuilder sb = new StringBuilder();
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
                reader.close();
                String str = sb.toString();
                System.out.println(str);
                System.out.println(conn.getRequestMethod());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean register(String name, String password, String email, Map<String, Object> map) {
        try {
            URL url = new URL(hostAndPort + loginUrl);
            System.out.println(url.toString());
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
