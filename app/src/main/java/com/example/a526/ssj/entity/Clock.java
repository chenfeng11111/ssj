package com.example.a526.ssj.entity;

import java.util.Date;

/**
 * Created by 10902 on 2019/6/2.
 */

public class Clock {
    int Id;
    Date time;
    int relatedNoteId;

    public int getId() {
        return Id;
    }

    public Date getTime() {
        return time;
    }

    public int getRelatedNoteId() {
        return relatedNoteId;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setRelatedNoteId(int relatedNoteId) {
        this.relatedNoteId = relatedNoteId;
    }
}
