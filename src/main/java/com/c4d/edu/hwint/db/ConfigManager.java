/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.db;

import com.c4d.edu.hwint.processors.TagLocationInsightProcessor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROBERT
 */
public class ConfigManager 
{
    public static String getScanInterval() throws SQLException
    {
    ResultSet rs = null;
    Statement stmt = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select scn_ntrvl from edudb.cfgeducatent");
            if(rs.next())
            {
                return rs.getString("scn_ntrvl");
            }

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
        
        return null;
    }
    
    public static String getNumberOfScanToBePresent() throws SQLException
    {
    ResultSet rs = null;
    Statement stmt = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("Select nm_scns_4prsnt from edudb.cfgeducatent");
            if(rs.next())
            {
                return rs.getString("nm_scns_4prsnt");
            }

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
        
        return null;
    }
}
