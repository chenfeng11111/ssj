package com.example.a526.ssj.notehelper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by 姜益昭 on 2019/5/30.
 * 本地文件的存储
 */
public class NoteUtils {
    private static String allNotePath = "../storefiles";
    //从相册路径拷贝
    //uri图片的相对路径
    //fileName图片
    //函数返回当前文件所在路径
    private static String copyImageFromBlum(Context context,Uri uri,String fileName){
        String originPath = RealPathFromUriUtils.getRealPathFromUri(context,uri);
        //解析文件名称 最后一个/之后
        String picName = originPath.substring(originPath.lastIndexOf("/")+1);
        //当前文件相对路径
        String picPath = allNotePath+File.separator+fileName+ File.separator+picName;
        try {
            //文件输入流
            FileInputStream inputeStream = new FileInputStream(originPath);
            byte []bytes=new byte[1024];
            int len = 0;
            //文件输出流

            FileOutputStream outputStream=new FileOutputStream(picPath);
            while((len=inputeStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            //刷新缓冲区
            outputStream.flush();
            //关闭输入流
            inputeStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.d("ERROR:","复制源文件到目标位置失败");
            return  null;
        }
        return picPath;
    }
    //保存富文本编辑器中内容到本地
    public static void saveFile(Context context, String fileName, String fileContext){
        //主文件夹是否存在 如果不存在 则创建
        File appFilePath=new File(allNotePath);
        if(!appFilePath.exists()){
            appFilePath.mkdirs();
        }
        //创建保存文件的文件夹
        File noteFilePath =new File(allNotePath+"/"+fileName);
        noteFilePath.mkdirs();

        try {
            //jsoup解析HTML文本
            org.jsoup.nodes.Document mDocument = Jsoup.parse(fileContext);
            //找到所有具有src属性的img标签
            Elements imageSrc = mDocument.select("img[src]");
            for (Element element:imageSrc){
                //根据地址获取Uri
                Uri uri = Uri.parse(element.attr("src"));
                //将所有图片文件拷贝到指定文件夹下
                String storePath = copyImageFromBlum(context,uri,fileName);
                //使用新路径替换当前路径
                element.attr("src",storePath);
            }
            /************修改后的文件直接保存到数据库中，不通过文件保存 此处应该调用数据库接口**********/
            //打开文件输出流
         /*   FileOutputStream outputStream=new FileOutputStream(new File(allNotePath+File.separator+fileName+File.separator+fileName+".html"));
            //将替换完成的Docunment写入到文件中
            outputStream.write(mDocument.toString().getBytes());
            outputStream.flush();
            outputStream.close();*/
        } catch (Exception e) {
            Log.d("ERROR","打开文件失败！"+e.toString());
        }
    }
    /***********无用 从数据库中读取**********************/
    /*
    public static String loadFile(Context context,String fileName) {
        String fileOriPath = allNotePath + File.separator + fileName + File.separator + fileName + ".html";
        try {
            //打开html文件读取输入流
            FileInputStream inputStream = new FileInputStream(fileOriPath);
            byte []bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            //将输入流转化为字符串
            return bytes.toString();
        } catch (Exception e) {
            Log.d("ERROR", "读取文件失败!!"+e.toString());
            return "";
        }
    }*/
    public static ArrayList<String> loadAllFiles(Context context){

        //调用数据库接口返回
        return null;
    }
}
