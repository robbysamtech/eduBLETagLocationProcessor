/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ROBERT
 */
public class SWAttendanceInfo {

    private String studentId = null;
    private String gradeId = null;
    private Date sweepTime = null;
    private String roomId = null;
    private String periodId = null;
    private String coursePeriodId = null;
    private String teacherId = null;

   
    private String markingPeriodId = null;
    private ArrayList<String> courseList = null;
    private ArrayList<String> coursePeriodList = null;    
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public Date getSweepTime() {
        return sweepTime;
    }

    public void setSweepTime(Date sweepTime) {
        this.sweepTime = sweepTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getCoursePeriodId() {
        return coursePeriodId;
    }

    public void setCoursePeriodId(String coursePeriodId) {
        this.coursePeriodId = coursePeriodId;
    }

    public ArrayList<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<String> courseList) {
        this.courseList = courseList;
    }

    public ArrayList<String> getCoursePeriodList() {
        return coursePeriodList;
    }

    public void setCoursePeriodList(ArrayList<String> coursePeriodList) {
        this.coursePeriodList = coursePeriodList;
    }
    


    @Override
    public int hashCode()
    {
        
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.roomId);
        hash = 41 * hash + Objects.hashCode(this.studentId);
        return hash;
    }
    
    @Override
     public boolean equals(Object inComing) {
        // This does not take into account the sweeptime.
        if(inComing == null || (!(inComing instanceof SWAttendanceInfo)))
            return false;
        
        SWAttendanceInfo inComingSWAttendanceInfo = (SWAttendanceInfo) inComing;
        String thisValue = this.getStudentId()+":"+this.getRoomId();
        String inComingValue = inComingSWAttendanceInfo.getStudentId()+":"+inComingSWAttendanceInfo.getRoomId();

        if(thisValue.equalsIgnoreCase(inComingValue))
            return true;
        
        return false;
    }
    
      public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getMarkingPeriodId() {
        return markingPeriodId;
    }

    public void setMarkingPeriodId(String markingPeriodId) {
        this.markingPeriodId = markingPeriodId;
    }
     
}
