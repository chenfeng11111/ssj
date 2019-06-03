package com.example.a526.ssj.notehelper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.a526.ssj.entity.Note;

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

        String uriToString = uri.toString();
        //判断当前路径是否是项目目录 如果是 直接返回
        if((allNotePath+File.separator+fileName).equalsIgnoreCase(uriToString.substring(0,uriToString.lastIndexOf('/'))))
        {
            return uri.toString();
        }
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
    //保存富文本编辑器图片到本地
    public static String savePicAndTransferHtml(Context context, String fileName, String fileContext){
        //主文件夹是否存在 如果不存在 则创建
        File appFilePath=new File(allNotePath);
        if(!appFilePath.exists()){
            appFilePath.mkdirs();
        }
        //创建保存文件的文件夹
        File noteFilePath =new File(allNotePath+"/"+fileName);
        if(!noteFilePath.exists())
        {
            noteFilePath.mkdirs();
        }
        //修改后的Html文档
        String transferHtml = "";
        try {
            //jsoup解析HTML文本
            org.jsoup.nodes.Document mDocument = Jsoup.parse(fileContext);
            //找到所有具有src属性的img标签
            Elements imageSrc = mDocument.select("img[src]");
            for (Element element:imageSrc){
                //根据地址获取Uri
                Uri uri = Uri.parse(element.attr("src"));
                //将所有图片文件拷贝到指定文件夹下
                String storePath = copyImageFromBlum(context,uri,fileContext);
                //使用新路径替换当前路径
                element.attr("src",storePath);
            }
            transferHtml = mDocument.toString();
        } catch (Exception e) {
            Log.d("ERROR","打开文件失败！"+e.toString());
        }
        return transferHtml;
    }
    public static String deleteFile(Context context,String fileName)
    {
        File file = new File(allNotePath+File.separator+fileName);
        //获取文件夹下所有文件
        File[] files = file.listFiles();
        for (File pic:files){
            pic.delete();
        }
        //删除文件夹
        file.delete();
        return "删除成功";
    }
}
