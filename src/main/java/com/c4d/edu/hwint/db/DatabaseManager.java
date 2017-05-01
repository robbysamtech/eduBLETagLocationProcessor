/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ROBERT
 */
public class DatabaseManager {
    
    public static String userName = "root";
    public static String password = "";
    public static String server = "localhost";
    public static String port = "3306";
    public static String database = "school";

    private static Connection conn = null;
    
    public static synchronized Connection getConnection() throws SQLException
    {
        if(conn != null)
        {
            return conn;
        }
        
        String connURL = "jdbc:mysql://"+server+":"+port+"/"+database;
        conn = DriverManager.getConnection(connURL, userName, password);
        System.out.println("CONNECTION SUCCESS");
        return conn;
    }
    
}
