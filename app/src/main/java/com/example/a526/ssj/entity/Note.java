package com.example.a526.ssj.entity;

import java.util.Date;

/**
 * Created by chenfeng on 2019/5/28.
 */

public class Note {
    Integer id;//文件ID
    String content;//文件内容
    Boolean isUpload;//是否上传到服务器
    Boolean isShare;//文件是否分享
    Date saveTime;//文件创建时间
    Integer userId;//上传者
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
}
