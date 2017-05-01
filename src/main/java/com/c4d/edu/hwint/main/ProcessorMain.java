/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.main;

import com.c4d.edu.hwint.processors.TagLocationInsightProcessor;
import java.sql.SQLException;

/**
 *
 * @author ROBERT
 */
public class ProcessorMain 
{
    public static void main(String arg[]) throws SQLException
    {
        TagLocationInsightProcessor tlip = new TagLocationInsightProcessor();
        tlip.start();
    }
}
