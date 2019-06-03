package com.example.a526.ssj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenfeng on 2019/5/28.
 */

public class Note implements Serializable{
    Integer id;//文件ID
    String title;//文件标题
    String content;//文件内容
    Boolean isUpload;//是否上传到服务器
    Boolean isShare;//文件是否分享
    Date saveTime;//文件创建时间
    Integer userId;//上传者
    String code;//笔记的唯一标识符，生成规则是userID+savetime，由数据库工具自动生成
    int version;//笔记的版本，由数据库工具自动生成

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }

    public Boolean getShare() {
        return isShare;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setShare(Boolean share) {

        isShare = share;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
