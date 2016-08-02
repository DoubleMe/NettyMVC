package com.chm.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author wwggzz
 * 
 */
public class StringUtil {

	public static final Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");//中文字符
	
	/**
	 * 获取可能包含中文字符的字符串集的数据库长度
	 * 
	 * @param str
	 */
	public static int getDBLenFromContainCH(String str){
		 int addlen = 0;//需要增加的长度
		 Matcher m = p.matcher(str);
		 while(m.find()){//有中文
			 addlen++;
		 }
		 return str.length()+addlen;
	}
	
	/**
	 * 日期格式化函數
	 * 
	 * @param date
	 *            : 格式化前的字符串,長度必須為8,且是YYYYMMDD格式; ?? 如果為null,或空串或只有空格的字符串,返回空串;
	 *            ?? 如果長度是不為8的字符串,返回空串;
	 * @return 格式為: YYYY/MM/DD 的字符串;
	 */
	public static String formatDate(String date) {
		if (date == null) {
			return "";
		}
		date = date.trim();
		if (date.equals("&nbsp;")) {
			return "";
		}
		if (date.length() == 0 || date.length() != 8) {
			return "";
		}
		date = date.substring(0, 4) + "/" + date.substring(4, 6) + "/"
				+ date.substring(6, 8);
		return date;
	}

	/**
	 * 获取当前日期格式化函数
	 * 
	 * @param dateFormat
	 *            传入需要输出的日期格式 ；如果為null,或空串或只有空格的字符串，则默认为yyyyMMddHHmmss格式
	 * @return 格式為: 输入的日期格式 的字符串; 默认为 yyyyMMddHHmmss
	 */
	public static String getNow(String dateFormat) {
		if (isBlank(dateFormat)) {
			dateFormat = "yyyyMMddHHmmss";
		}
		SimpleDateFormat dateformat = new SimpleDateFormat(dateFormat);
		return dateformat.format(System.currentTimeMillis());
	}

	/**
	 * 日期格式化函數
	 * 
	 * @param date
	 *            : 格式化前的字符串,長度必須為10,且是YYYY/MM/DD格式; ??
	 *            如果為null,或空串或只有空格的字符串,返回空串; ?? 如果長度是不為8的字符串,返回空串;
	 * @return 格式為: YYYYMMDD 的字符串;
	 */
	public static String formatDateToStr(String date) {
		if (date == null) {
			return "";
		}
		date = date.trim();
		if (date.equals("&nbsp;")) {
			return "";
		}
		if (date.length() == 0 || date.length() != 10) {
			return "";
		}
		date = date.substring(0, 4) + date.substring(5, 7)
				+ date.substring(8, 10);
		return date;
	}

	/**
	 * 字符转double
	 * 
	 * @param str
	 * @return double
	 */
	public static double parseDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0d;
		}
	}
	
	/**
	 * 将double精确到后小数点后几位
	 * @param number 需要精确的数值
	 * @param pattern 精度，用"#.##"表示
	 * @return String
	 */
	public static String parseDouble(double number,String pattern){
		
		DecimalFormat df=new DecimalFormat(pattern);
		return df.format(number);
	}

	/**
	 * 对象转换为double
	 * 
	 * @param str
	 * @return double
	 */
	public static double parseDouble(Object obj) {
		return parseDouble(toString(obj));
	}

	/**
	 * 字符转long Description: Date:2012-11-3
	 * 
	 * @param str
	 * @return long
	 */
	public static long parseLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * 字符转int Description: Date:2012-11-3
	 * 
	 * @param str
	 * @return int
	 */
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int parseInt(Object obj) {
		return parseInt(toString(obj));
	}

	/**
	 * 判斷字符串是否為空,空格为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().equals("");
	}
	
	/**
	 * 判斷字符串是否為空，空格不为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlankWithoutTrim(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 判斷字符串是否不为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj){
		return !isBlank(obj);
	}
	
	/**
	 * 判斷英數字
	 * 
	 * @param val
	 * @return boolean
	 */
	public static boolean isAlpha(String val) {
		if (val == null) {
			return false;
		}
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);

			if ((ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z')
					&& (ch < '0' || ch > '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判斷是否為大寫英文字母
	 * 
	 * @param val
	 * @return boolean
	 */
	public static boolean isCapsEnglish(String val) {
		if (val == null) {
			return false;
		}
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);

			if (ch < 'A' || ch > 'Z') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串 是否是数字
	 * @param val
	 * @return boolean
	 */
	public static boolean isNumber(String val) {
		if (val == null) {
			return false;
		}
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (ch < '0' || ch > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param val
	 * @return boolean
	 */
	public static boolean isNumberOrCapsEnglish(String val) {
		if (val == null) {
			return false;
		}
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);

			if ((ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param object
	 * @return boolean
	 */
	public static boolean isBlank(Object object) {
		if (object == null) {
			return true;
		}
		return isBlank(object.toString());
	}

	/**
	 * 取得字符串指定位置的字符
	 * 
	 * @param str
	 * @param num
	 * @return String
	 */
	public static String getStrChar(String str, int num) {
		if (str == null || num > str.length()) {
			return "";
		}
		return str.substring(num - 1, num);
	}

	/**
	 * input 1---52 ,return 'A'----'Z'
	 * 
	 * @param i
	 * @return String
	 */
	public static String convertintTochar(int i) {
		if (i <= 26) {
			return ((char) (64 + i)) + "";
		} else if (i <= 52) {
			return "A" + ((char) (64 + i - 26));
		} else if (i > 52) {
			return "B" + ((char) (64 + i - 52));
		} else {
			return "";
		}
	}

	/**
	 * 返回对象字符串，对于出错情况返回空字符串
	 * 
	 * @param obj
	 * @return String
	 */
	public static String toString(Object obj) {
		try {
			return obj == null ? "" : obj.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 转换为Long类型数组
	 * 
	 * @param strs
	 *            字符串数组
	 * @return Long[] Long数据
	 */
	public static Long[] toLongArray(String[] strs) {
		Long[] rs = new Long[strs.length];
		for (int i = 0; i < strs.length; i++) {
			rs[i] = Long.valueOf(strs[i]);
		}
		return rs;
	}

	/**
	 * Description:拆分字符串，功能同java自带的split方法 Date:2013-3-25
	 * 
	 * @param str
	 *            //输入字符串
	 * @param key
	 *            //分割字符
	 * @param num
	 *            //拆分个数
	 * @return String[]
	 */
	public static String[] split(String str, String key, int num) {
		String[] strs = new String[num];
		for (int i = 0; i < num - 1; i++) {
			strs[i] = str.substring(0, str.indexOf(key));
			str = str.substring(str.indexOf(key) + 1);
		}
		strs[num - 1] = str;
		return strs;
	}

	/**
	 * 将数据库字段名转换成javabean属性名(不区分大小写)
	 * 
	 * @param columnName
	 *            字段名
	 * @return String javabean属性名
	 */
	public static String columnNameToBeanPropertyLowerCase(String columnName) {

		String returnStr = columnName.replaceAll("_", "").toLowerCase(
				Locale.getDefault());
		return returnStr;
	}

	/**
	 * 将“aaa,bbb,ccc”转为“'aaa','bbb','ccc'”
	 * 
	 * @param str
	 * @return String
	 */
	public static String addSeperatorOfString(String str) {
		if(isBlank(str)){
			return "''";
		}
		String returnStr = "'" + str.replaceAll(",", "','") + "'";
		return returnStr;
	}
	
	
	public static String addSeperatorOfString2(String str) {
		if(isBlank(str)){
			return "''";
		}
		return "'" + str+ "'";
	}
	/**
	 * 
	 * 按照String的split方法分割之后，判断分割后的字符数组内是否包含s。</br>
	 * 例如：isContainsAfterSplit("10000:10001",":","1000")返回false</br>
	 * 而isContainsAfterSplit("10000:10001",":","10000")返回true</br>
	 * 
	 * @param str 源字符串
	 * @param regex 分割符
	 * @param s 判断是否包含该字符
	 * @return 判断结果boolean值
	 * @author jc
	 */
	public static boolean isContainsAfterSplit(String str,String regex,CharSequence s){
		String[] strs=str.split(regex);
		Boolean f=false;
		for (String st : strs) {
			if(s.equals(st)){
				f=true;
				break;
			}
		}
		return f;
	}

	/**
	 * 字符串数组中是否含有指定字符串
	 * @param sources 数组
	 * @param desc 指定字符串
	 * @return boolean
	 */
	public static boolean isContains(String[] sources, String desc) {
		if (sources == null || desc == null) {
			return false;
		}
		for (String src : sources) {
			if (desc.equals(src)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 字符串是否含有指定字符串
	 * @param sources 字符串元数据
	 * @param desc 指定字符串
	 * @return boolean
	 */
	public static boolean comprise(String sources, String desc) {
		if(isBlank(desc) || isBlank(sources)){
			return false;
		}
		return sources.contains(desc);
	}
}
