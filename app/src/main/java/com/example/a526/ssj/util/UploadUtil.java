package com.example.a526.ssj.util;

import android.os.Bundle;
import android.util.Log;

import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 姜益昭 on 2019/6/3.
 */

public class UploadUtil {
    public static String host = "http://10.0.2.2:8080";

    public static String uploadImg(String filename, byte[] data) throws IOException {
        System.out.println("UploadImg processing.............");
        URL url = new URL(host + "/file/upload/image");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置连接
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        String boundary = "---------------------------823928434";
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        // 建立连接
        httpURLConnection.connect();

        // 传输数据
        String end = "\r\n";
        String twoHyphens = "--";
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeBytes(twoHyphens + boundary + end);
        dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "\"" + end);
        dos.writeBytes(end);
        dos.write(data);
        dos.writeBytes(end);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
        dos.flush();

        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            StringBuffer sb=new StringBuffer();
            String readLine;
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine())!=null){
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            System.out.println(sb.toString());
            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                String preFilename = jsonObject.getString("filename");
                String path = jsonObject.getString("path");
                return host+path;
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
        ArrayList<Note> noteList=new ArrayList<>();
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
                noteList = new ArrayList<>();
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
        return noteList;
    }

    public static ArrayList<Note> square()
    {
        ArrayList<Note> noteList = new ArrayList<>();
        try {
            URL url = new URL(host + "/file/upload/article/square");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 设置连接
//            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
            conn.setRequestProperty("Charset", "UTF-8");

            // 建立连接
            conn.connect();

//            // 传输数据
//            String data = "userid=" + userid;
//            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//            out.writeBytes(data);
//            out.flush();
//            out.close();

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
                System.out.println(jsonObject.get("articlelist"));
                JSONArray jsonArray = new JSONArray(jsonObject.getString("articlelist"));
                System.out.println(jsonArray.toString());
                 noteList = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject articleJSON = jsonArray.getJSONObject(i);
                    Note note = new Note();
                    note.setTitle(articleJSON.getString("title"));
                    note.setContent(articleJSON.getString("content"));
                    note.setUserId(articleJSON.getInt("userid"));
                    note.setUpload(articleJSON.getString("isupload").equals("1"));
                    note.setShare(articleJSON.getString("isShared").equals("1"));
                    note.setSaveTime(new Date(articleJSON.getString("savetime")));
                    note.setVersion(articleJSON.getInt("version"));
                    note.setCode(articleJSON.getString("code"));
                    noteList.add(note);
                    System.out.println(note);
                }
                return noteList;
            }
        } catch (Exception e) {
            Log.d("exception:", "square-----------" );
            e.printStackTrace();
        }
        Note note = new Note();
        note.setContent("test");
        note.setShare(true);
        note.setUpload(true);
        note.setVersion(1);
        note.setSaveTime(new Date(System.currentTimeMillis()));
        note.setUserId(1);
        note.setCode("this is test");
        note.setTitle("title");
        noteList.add(note);
        Log.d("square", "doing------------" + noteList.size());
        return noteList;
    }

    public static byte[]  downloadImage(String path)
    {
        try
        {
            URL url = new URL( path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();
                byte[] data = new byte[is.available()];
                is.read(data);
                is.close();
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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