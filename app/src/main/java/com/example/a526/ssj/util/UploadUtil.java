package com.example.a526.ssj.util;

import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 姜益昭 on 2019/6/3.
 */

public class UploadUtil {
    public static String host = "http://10.120.174.168:8080";

    public static String uploadImg(String filename, byte[] data) throws IOException {
        URL url = new URL(host + "/file/upload/image");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // 设置连接
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
        conn.setRequestProperty("Content-Type", "multipart/form-data;file="+ filename);

        // 建立连接
        conn.connect();

        // 传输数据
        OutputStream out = new DataOutputStream(conn.getOutputStream());
//        DataInputStream in = new DataInputStream(new FileInputStream(uri));
        out.write(data);
        out.flush();
        out.close();
//        int bytes = 0;
//        byte[] bufferOut = new byte[2048];
//        while ((bytes = in.read(bufferOut)) != -1) {
//            out.write(bufferOut, 0, bytes);
//        }
//        in.close();

        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            StringBuffer sb=new StringBuffer();
            String readLine;
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine())!=null){
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            System.out.println(sb.toString());
            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                String preFilename = jsonObject.getString("filename");
                String path = jsonObject.getString("path");
                return path;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean uploadArticle(Note note)
    {

        try {
            URL url = new URL(host + "/file/upload/article");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 传输数据
            String data = "title=" + note.getTitle() +
                    "content=" + note.getContent() +
                    "userid=" + note.getUserId() +
                    "isupload=" + note.getUpload() +
                    "isshared=" + note.getShare() +
                    "savetime" + note.getSaveTime().toString() +
                    "version" + note.getVersion() +
                    "code" + note.getCode();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK)
            {
                // 读取数据
                StringBuffer sb=new StringBuffer();
                String readLine;
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());
                JSONObject jsonObject = new JSONObject(sb.toString());
                if(jsonObject.getString("state").equals("success") && jsonObject.getString("message").equals("上传成功"))
                {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateArticle(Note note)
    {

        try {
            URL url = new URL(host + "/file/upload/article/update");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 传输数据
            String data = "title=" + note.getTitle() +
                    "content=" + note.getContent() +
                    "userid=" + note.getUserId() +
                    "isupload=" + note.getUpload() +
                    "isshared=" + note.getShare() +
                    "savetime" + note.getSaveTime().toString() +
                    "version" + note.getVersion() +
                    "code" + note.getCode();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK)
            {
                // 读取数据
                StringBuffer sb=new StringBuffer();
                String readLine;
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());
                JSONObject jsonObject = new JSONObject(sb.toString());
                if(jsonObject.getString("state").equals("success") && jsonObject.getString("message").equals("更新成功"))
                {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Note> downloadArticle(Integer userid)
    {
        try {
            URL url = new URL(host + "/file/upload/article/download");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置连接
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

            // 传输数据
            String data = "userid=" + userid;
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK)
            {
                // 读取数据
                StringBuffer sb=new StringBuffer();
                String readLine;
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                System.out.println(sb.toString());
                JSONArray jsonArray = new JSONArray(sb.toString());
                List<Note> noteList = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject articleJSON = jsonArray.getJSONObject(i);
                    Note note = new Note();
                    note.setTitle(articleJSON.getString("title"));
                    note.setContent(articleJSON.getString("content"));
                    note.setUserId(articleJSON.getInt("userid"));
                    note.setUpload(articleJSON.getString("isupload").equals("1"));
                    note.setShare(articleJSON.getString("isshared").equals("1"));
                    note.setSaveTime(new Date(articleJSON.getString("savetime")));
                    note.setVersion(articleJSON.getInt("version"));
                    note.setCode(articleJSON.getString("code"));
                    noteList.add(note);
                }
                return noteList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
* title
* conent
* userid
* isupload
* isshared
* savetime
* version
* code
* */