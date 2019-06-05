package com.example.a526.ssj.notehelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.a526.ssj.entity.GlobalVariable;
import com.example.a526.ssj.entity.Note;
import com.example.a526.ssj.util.UploadUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by 姜益昭 on 2019/5/30.
 * 调用客户端网络接口 ，将文件上传的服务器
 */

public class NoteHelper {
    //传入文件所在文件夹路径 将文件上传到服务器端
    public static String uploadFileToServer(Note note,int status){
        System.out.println(note.getContent());
        System.out.println("NoteHelper processing...");
        //打开路径下的html文件
        String htmlDocument = note.getContent();
        try {
            //使用jsoup解析HTML文件
            Document jsoupDocument = Jsoup.parse(htmlDocument);
            Elements imageSrc = jsoupDocument.select("img[src]");

            for (Element element : imageSrc){
                //获取图片路径
                String picUri = element.attr("src");
                String picPath = picUri.substring(picUri.indexOf(':')+3);
                //获取图片名称
                String picName = picUri.substring(picUri.lastIndexOf('/')+1);
                System.out.println("图片名称："+picName);
                System.out.println("图片路径:"+picPath);
                //打开文件输入流
                FileInputStream picInputStream = new FileInputStream(picPath);
                byte[] bytes = new byte[picInputStream.available()];
                picInputStream.read(bytes);
                //调用网络接口向服务器上传图片文件 获取返回的服务器路径
                String serverPicPath = UploadUtil.uploadImg(picName,bytes);
                System.out.println("网络路径:"+serverPicPath);
                if("".equalsIgnoreCase(serverPicPath))
                {
                    return "上传文件失败！！！";
                }
                /******************************此处应该调用服务器接口**********************************/
                //将Document中的src替换为服务器路径
                element.attr("src",serverPicPath);
            }
            //所有标签替换完成后，将Document转化为字节流 发送到服务器端
            String serverHtml = jsoupDocument.toString();
            note.setContent(serverHtml);
            System.out.println(note);
            //此处调用网络接口将HTML文本发送到服务器
            /***********************************************/
            UploadUtil.uploadArticle(note);
        } catch (Exception e) {
            Log.d("ERROR:上传文件","打开文件失败");
            return "打开文件失败";
        }
        return "上传成功";
    }
    public static String syncFileToLocal()
    {
        //获取本地文件存储路径
        String allNotePath = GlobalVariable.getFileStorePath().toString();
        List<Note> remoteNoteList = UploadUtil.downloadArticle(GlobalVariable.getCurrentUserId());
        for(Note note:remoteNoteList)
        {
            System.out.println("服务器文本："+note.getContent());
            //保存文件到本地
            int status = GlobalVariable.getNoteDatabaseHolder().needToUpdate(note);
            if(status != 0)
            {
                //获取文件名
                String fileName = note.getTitle();
                File file = new File(allNotePath + File.separator + fileName);
                if(!file.exists())
                {
                    file.mkdirs();
                }
                //解析图片文件
                Document mDoucument = Jsoup.parse(note.getContent());
                Elements elements = mDoucument.select("img[src]");
                for(Element element:elements)
                {
                    String remotePicPath = element.attr("src");
                    String localPicPath = copyPicFormServer(remotePicPath,fileName);
                    System.out.println("服务器图片:"+remotePicPath);
                    System.out.println("下载图片路径:"+localPicPath);
                    if("".equalsIgnoreCase(localPicPath)){
                        return "下载图片文件失败"+ remotePicPath;
                    }
                    element.attr("src",localPicPath);
                }
                //解析完成后，存储到数据库
                note.setContent(mDoucument.toString());
                System.out.println("解析文本:"+mDoucument.toString());
                if(1 == status)
                {
                    GlobalVariable.getNoteDatabaseHolder().insertNote(note);
                }
                else if(2 == status)
                {
                    GlobalVariable.getNoteDatabaseHolder().updateNote(note);
                }
            }
        }
        return "成功";
    }
    private static String copyPicFormServer(String uri,String fileName)
    {
        System.out.println("从服务器下载图片");
        //获取所有文件路径
        String allNotePath = GlobalVariable.getFileStorePath().toString();
        //读取图片文件名称
        String picRemoteName = uri.substring(uri.lastIndexOf('/')+1);
        //图片保存路径
        String picLocalPath = allNotePath + File.separator+fileName+File.separator+picRemoteName;
        //读取网络输入流
        String result = UploadUtil.downloadImage(uri,picLocalPath);
        if("success".equalsIgnoreCase(result))
        {
            return "file:///"+picLocalPath;
        }
        else
        {
           return result;
        }
    }
}
