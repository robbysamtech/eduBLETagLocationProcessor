/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c4d.edu.hwint.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ROBERT
 */
public class DateUtils 
{
    public static String getFormattedDate(String pattern, Date inTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(pattern);
        return sdf.format(inTime);
    }
}
