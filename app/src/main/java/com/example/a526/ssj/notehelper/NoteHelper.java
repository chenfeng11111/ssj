package com.example.a526.ssj.notehelper;

import android.util.Log;

import com.example.a526.ssj.entity.Note;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by 姜益昭 on 2019/5/30.
 * 调用客户端网络接口 ，将文件上传的服务器
 */

public class NoteHelper {
    private static  String allNotePath = "../storefiles";
    //传入文件所在文件夹路径 将文件上传到服务器端
    public static void uploadFileToServer(Note note,int status){
        //打开路径下的html文件
        String htmlDocument = note.getContent();
        try {
            //使用jsoup解析HTML文件
            Document jsoupDocument = Jsoup.parse(htmlDocument);
            Elements imageSrc = jsoupDocument.select("img[src]");

            for (Element element : imageSrc){
                //获取图片路径
                String picUri = element.attr("src");
                //打开文件输入流
                FileInputStream picInputStream = new FileInputStream(picUri);
                byte []picStream =new byte[picInputStream.available()];
                //读取图片流到数组中
                picInputStream.read(picStream);
                //调用网络接口向服务器上传图片文件 获取返回的服务器路径
                String serverPicPath = "";/******************************此处应该调用服务器接口**********************************/
                //将Document中的src替换为服务器路径
                element.attr("src",serverPicPath);
            }
            //所有标签替换完成后，将Document转化为字节流 发送到服务器端
            String serverHtml = jsoupDocument.toString();
            note.setContent(serverHtml);
            //此处调用网络接口将HTML文本发送到服务器
            /***********************************************/

        } catch (Exception e) {
            Log.d("ERROR:上传文件","打开文件失败");
        }
    }
}
