/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.pojo;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ROBERT
 */
public class HWTagRoomInfo 
{
    private String recordId = null;
    private String roomId = null;
    private String tagId = null;
    private Date sweepTime = null;
    private String rsi = null;
    private Date uploadTime = null;
    private Date dataReadTime = null;
    
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Date getSweepTime() {
        return sweepTime;
    }

    public void setSweepTime(Date sweepTime) {
        this.sweepTime = sweepTime;
    }

    public String getRsi() {
        return rsi;
    }

    public void setRsi(String rsi) {
        this.rsi = rsi;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getDataReadTime() {
        return dataReadTime;
    }

    public void setDataReadTime(Date dataReadTime) {
        this.dataReadTime = dataReadTime;
    }
 
//    public String toString()
//    {
//     return recordId + " : " + roomId + " : " + tagId + " : " + sweepTime + " : " + rsi + " : " + uploadTime+ " : " +dataReadTime;
//    }
    
    @Override
    public int hashCode()
    {
        
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.roomId);
        hash = 41 * hash + Objects.hashCode(this.tagId);
        return hash;
    }

    public boolean equals(Object inComing) {
        // This does not take into account the sweeptime.
        if(inComing == null || (!(inComing instanceof HWTagRoomInfo)))
            return false;
        
        HWTagRoomInfo inComingHWTagRoomInfo = (HWTagRoomInfo) inComing;
        String thisValue = this.getTagId()+":"+this.getRoomId();
        String inComingValue = inComingHWTagRoomInfo.getTagId()+":"+inComingHWTagRoomInfo.getRoomId();

        if(thisValue.equalsIgnoreCase(inComingValue))
            return true;
        
        return false;
    }
}
