/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.processors;

import com.c4d.edu.hwint.db.DatabaseManager;
import com.c4d.edu.hwint.pojo.HWTagRoomInfo;
import com.c4d.edu.hwint.util.DateUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROBERT
 */
public class TagLocationInsightProcessor extends Thread {
    
    private Map<HWTagRoomInfo, Integer> attendanceMap = null;
    public void run()
    {
        //while(true)
        //{
            
           makeInsights(); 
            
        //}
    }
    
    private synchronized void makeInsights()
    {
        attendanceMap = new HashMap<HWTagRoomInfo, Integer>();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select * from edudb.attdmp4mhw where datareadtime is null");
            process(rs);  
            System.out.println(attendanceMap);
            }catch (SQLException e) 
            {
                e.printStackTrace();
              Logger.getLogger(TagLocationInsightProcessor.class.getName()).log(Level.SEVERE, null, e);
            }
            finally
            {
                try{
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                Logger.getLogger(TagLocationInsightProcessor.class.getName()).log(Level.SEVERE, null, e);
                }

            }
    }
    
    private void process(ResultSet rs) throws SQLException
    {
        while(rs.next())
        {
            HWTagRoomInfo tagRoomInfo = getRowData(rs);
            //isStudentAndClassCorrectAsPerSchedule(tagRoomInfo);
            String studentId = getStudentWithTag(tagRoomInfo.getTagId());
            String gradeId = getGradeForStudent(studentId);
            Date sweepTime = tagRoomInfo.getSweepTime();
            String roomId = getRoomWithTag(tagRoomInfo.getRoomId());
            ArrayList<String> courseList = getAllCoursesForGrade(gradeId);
            
            if(courseList==null)
            {
                System.out.println("Course List was null");
                continue;
            }
            
            ArrayList<String> coursePeriodList = getAllCoursePeriods(courseList);
            
            if(courseList==null)
            {
                System.out.println("Course List was null");
                continue;
            }
            else
            {
                for(String coursePeriod  : coursePeriodList)
                {
                 if(doesSweepTimeMatchesWithRoomAndScheduledCourse(coursePeriod, sweepTime, roomId))
                 {
                     if(attendanceMap.get(tagRoomInfo) == null)
                     {
                      attendanceMap.put(tagRoomInfo, 1);
                     }
                     else
                     {
                      attendanceMap.put(tagRoomInfo,((Integer) attendanceMap.get(tagRoomInfo))+1);
                     }
                  System.out.println("Matches "+studentId+ " and "+sweepTime +" for classroom "+roomId);
                 }
                 else
                 {
                  System.out.println("Does not match "+studentId+ " and "+sweepTime +" for classroom "+roomId);
                 }

                }
            }
            //updateDateReadForTagLocationInfo(tagRoomInfo.getRecordId(), currentTime);
        }
    }
    
    private boolean doesSweepTimeMatchesWithRoomAndScheduledCourse(String coursePeriod, Date sweepTime, String location) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        System.out.println("sweeptime "+sweepTime);
        String query = "Select  does_attendance from edudb.course_period_var where room_id='"+location+"' and course_period_id = '"+coursePeriod+"' and '"+DateUtils.getFormattedDate("HH:mm:ss", sweepTime)+"' between edudb.course_period_var.start_time and edudb.course_period_var.end_time";
        System.out.println(query);
        rs = stmt.executeQuery(query);
            while(rs.next())
            {
                String doesAttendance = rs.getString(1);
                if(doesAttendance.equalsIgnoreCase("Y"))
                {
                    return true;
                }
                
            }

        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(rs!=null) rs.close();   
         if(stmt!=null) stmt.close();
        }
        return false;
    }
    
    private HWTagRoomInfo getRowData(ResultSet rs) throws SQLException
    {
        HWTagRoomInfo tagRoomInfo = new HWTagRoomInfo();
        tagRoomInfo.setRecordId(rs.getString("id"));
        tagRoomInfo.setRoomId(rs.getString("classname"));
        tagRoomInfo.setTagId(rs.getString("tagname"));
        tagRoomInfo.setRsi(rs.getString("rsi"));
        tagRoomInfo.setSweepTime(rs.getTime("scantime"));
        System.out.println(tagRoomInfo);
        return tagRoomInfo;
    }
    
    private String getStudentWithTag(String tagId) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        String query = "Select student_id from edudb.students where custom_1='"+tagId+"'";
        rs = stmt.executeQuery(query);
            if(rs.next())
            {
                String studentId = rs.getString(1);
                System.out.println(studentId);
                return studentId;
            }
        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(rs!=null) rs.close();   
         if(stmt!=null) stmt.close();
        }
        System.out.println("Returning null at getStudentIdForTagId");
        return null;
    }

    private String getRoomWithTag(String locationId) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        String query = "Select room_id from edudb.rooms where tag='"+locationId+"'";
        rs = stmt.executeQuery(query);
            if(rs.next())
            {
                String roomId = rs.getString(1);
                System.out.println(roomId);
                return roomId;
            }
        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(rs!=null) rs.close();   
         if(stmt!=null) stmt.close();
        }
        System.out.println("Returning null at getRoomWithTag");
        return null;
    }
     
    private String getGradeForStudent(String studentId) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        String query = "Select grade_id from edudb.student_enrollment where student_id='"+studentId+"'";
        rs = stmt.executeQuery(query);
            if(rs.next())
            {
                String gradeId = rs.getString(1);
                System.out.println(gradeId);
                return gradeId;
            }
        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(rs!=null) rs.close();   
         if(stmt!=null) stmt.close();
        }
        System.out.println("Returning null at getGradeIdForStudentId");
        return null;
    }
    
    private ArrayList<String> getAllCoursesForGrade(String gradeId) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList al = new ArrayList<String>();
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        String query = "Select course_id from edudb.courses where grade_level='"+gradeId+"'";
        rs = stmt.executeQuery(query);
            while(rs.next())
            {
                al.add(rs.getString(1));
            }
        
        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(rs!=null) rs.close();   
         if(stmt!=null) stmt.close();
        }
        
        if(al.size()==0) System.out.println("Returning null at getGradeIdForStudentId");
        
        return al.size() > 0? al : null;
    }

    private ArrayList<String> getAllCoursePeriods(ArrayList<String> courseList) throws SQLException
    {
        ArrayList<String> coursePeriodList = new ArrayList<String>();
        
        for(String courseId : courseList)
        {
           Statement stmt = null;
           ResultSet rs = null;
           try{
           stmt = DatabaseManager.getConnection().createStatement();
           String query = "Select course_period_id from edudb.course_periods where course_id='"+courseId+"'";
           rs = stmt.executeQuery(query);
            if(rs.next())
            {
                String coursePeriodId = rs.getString(1);
                coursePeriodList.add(coursePeriodId);

            }
           }catch(SQLException e)
           {
            throw e;
           }
           finally
           {
            if(rs!=null) rs.close();   
            if(stmt!=null) stmt.close();
           }
        }
        
     return coursePeriodList.size()==0 ? null:coursePeriodList;
    }
    
    private boolean doesSweepTimeMatchWithLocation(ArrayList<String> courseList) throws SQLException
    {
    
        return false;
    }
    private boolean isStudentAndClassCorrectAsPerSchedule(HWTagRoomInfo tagLocationInfo) throws SQLException
    {
        String tagId = tagLocationInfo.getTagId();
        String location = tagLocationInfo.getRoomId();
        Date sweepTime = tagLocationInfo.getSweepTime();
        
        String studentId = getStudentWithTag(tagId);
        String gradeId = getGradeForStudent(studentId);
        
        //1. get Student id
        //Select student_id into varStudentId from edudb.students where custom_1=studentTagId;
        
        //2. get Room id
        //Select room_id into varRoomId from edudb.rooms where tag = locationTagId;
        
        
        //3. get the grade from student_enrollment for the student.
        
        //4. get all the course_id for that grade_level from courses.
        
        //5. get all the course_period_ids from the course_periods for that courseid.
        
        //6. use all the course_period_ids to know if at this time, the room that is there matches with the incoming room;
        
        //5. use the course_id to get all the courseperiods wihin that 
        
        //Select course_period_id, period_id, does_attendance into varCoursePeriodId, varPeriodId, varDoesAttendance from edudb.course_period_var where room_id=varRoomId  and sweepTime between edudb.course_period_var.start_time and edudb.course_period_var.end_time;
        
//        Statement stmt = null;
//        try{
//        stmt = DatabaseManager.getConnection().createStatement();
//        String insertQuery = "";//Update edudb.attdmp4mhw set datareadtime='"+formattedDate+"' where id = "+rowId;
//        stmt.executeUpdate(insertQuery);
//        }catch(SQLException e)
//        {
//         throw e;
//        }
//        finally
//        {
//         if(stmt!=null) stmt.close();
//        }

        return false;
    }
    
  //  private void getClassRoomOnScheduleForStudent
    
    private void updateDateReadForTagLocationInfo(String rowId, Date currentTime) throws SQLException
    {
        String formattedDate = DateUtils.getFormattedDate("YYYY-MM-dd HH:mm:ss", currentTime);
        Statement stmt = null;
        try{
        stmt = DatabaseManager.getConnection().createStatement();
        String insertStatement = "Update edudb.attdmp4mhw set datareadtime='"+formattedDate+"' where id = "+rowId;
        stmt.executeUpdate(insertStatement);
        }catch(SQLException e)
        {
         throw e;
        }
        finally
        {
         if(stmt!=null) stmt.close();
        }
    }
}
