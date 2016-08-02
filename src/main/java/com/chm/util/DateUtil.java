package com.chm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 日期处理的工具类
 * 
 * @author chm
 */
public class DateUtil {
	private static final String FORMAT_TO_SECONDS_LINE = "yyyy-MM-dd hh:mm:ss";
	private static final String FORMAT_TO_SECONDS_SPRIT = "yyyy/MM/dd hh:mm:ss";
	private static final String FORMAT_TO_DAY_LINE = "yyyy-MM-dd";
	private static final String FORMAT_TO_DAY_SPRIT = "yyyy/MM/dd";
	
	/**
	 * 将字符串转换为java.util.Date
	 * @param dateValue
	 * @return
	 */
	public static Date getDateFromStr(String dateValue){
		
		return getDateFromStr(dateValue,FORMAT_TO_SECONDS_LINE);
	}
	
	/**
	 * 将字符串转换为java.util.Date
	 * @param dateValue
	 * @return
	 */
	public static Date getDateFromStr(String dateValue,String formatStr){
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * java.util.Date 转换为字符串
	 * @param date 日期类
	 * @param formatStr 转化格式
	 * @return
	 */
	public static String getStrFormDate(Date date,String formatStr){
		
		return new SimpleDateFormat(formatStr).format(date);
	}
	/**
	 * 使用默认格式
	 * @param date
	 * @return
	 */
	public static String getStrFormDate(Date date){
		
		return new SimpleDateFormat(FORMAT_TO_SECONDS_LINE).format(date);
	}
	
	/** 
	 * @Description: 
	 * @param args 
	 */
	public static void main(String[] args) {
		Date date = getDateFromStr("2010/10/11 10:00:02",FORMAT_TO_SECONDS_SPRIT);
		System.out.println(getStrFormDate(date));
	}
}
