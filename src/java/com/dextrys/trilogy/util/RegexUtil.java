/*
 * @author talent_marquis<��˺��>
 * Email: talent_marquis@163.com
 * Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.util;

import java.util.regex.*;

public class RegexUtil
{
	public static final String REGEX_IS_EMAIL = "^[a-zA-Z0-9][a-zA-Z0-9_.]*@\\w+([.][a-zA-Z]+)+$";
	public static final String REGEX_IS_CHINESE_MOBILE_PHONE_NUMBER = "^(86)*0*1[3,5]\\d{9}$";
	
	
	public static final String REGEX_EMAIL_INFO = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String REGEX_CHINESE_MOBILE_PHONE_NUMBER_INFO = "(86)*0*1[3,5]\\d{9}"; 
	
	
	
	public static void main(String args[])
	{
		//�������
		String testData = "23@asd.org|555@dsa.cn#afdad43876@aa.aa.aafqpowier, a@asdfa.cn,asdfawqe/,xcnvq;qtqpr";
		
		String someDateFile[] = getEmailInfo(testData);
		for(String str : someDateFile)
		{
			println(str);
		}
		
		String[] test =
			{	"asd@asd.", "asd@asd.cn", "asd@asd.asd.", "asd@asd.asd.cn" };
		for( String str : test )
		{
			println(str+":"+isEmail(str));
		}
		
		String testData2 = "086013693567997aspdfa;sldfkjqw;ekjr13701365050asdfsfqw0erwer15341230901324e512341241234341234";
		for( String str : getChineseMobilePhoneNumber( testData2 ))
		{
			println( str );
		}
		
		String[] test2 = 
			{ "13693567997", "136935679978", "13693567997a" };
		for( String str: test2)
		{
			println( str + ":"+ isChinseMobilePhoneNumber( str ));
		}
		
		
	}
	
	public static boolean isChinseMobilePhoneNumber( String data )
	{
		return isMatchRegex( data, REGEX_IS_CHINESE_MOBILE_PHONE_NUMBER );
	}
	
	/**
	 * @return boolean whether the String is a email address
	 */
	public static boolean isEmail( String data )
	{
		return isMatchRegex( data, REGEX_IS_EMAIL );
	}
	
	/**
	 * 
	 * @param data String data including chinese mobile phone numbers
	 * @return a String array filled with phone numbers
	 */
	public static String[] getChineseMobilePhoneNumber( String data )
	{
		return getMatchInfo( data, REGEX_CHINESE_MOBILE_PHONE_NUMBER_INFO );
	}
	
	/**
	 * 
	 * @param data String dada including email infos
	 * @return a String array filled with email address
	 */
	public static String[] getEmailInfo(String data)
	{		
		return getMatchInfo( data,REGEX_EMAIL_INFO );
		// //��ȡEmail�����������ʽ
		// String regexStr = REGEX_EMAIL_INFO;
		// //����������ʽģ��
		// Pattern p = Pattern.compile(regexStr);
		// //����Email�ֶ�ƥ����,���ѯ�ַ�DataΪ�����
		// Matcher emailMatcher = p.matcher(data);
		// //��Email��ַ�洢��StringBuffer���͵�emailTemp��
		// StringBuffer emailTemp = new StringBuffer();
		//		
		// while( emailMatcher.find() )
		// {
		// emailTemp.append( emailMatcher.group() + ",") ;
		// }
		//		
		// //��emailTemp�е�email��ַת����string����emailAddress��
		// return emailTemp.toString().split(",");
	}

	
	/**
	 * 
	 * @param data the String testing match the regex
	 * @param regex the String regex
	 * @return the String array with strings match regex
	 */
	private static String[] getMatchInfo( String data, String regex )
	{
		Pattern p = Pattern.compile( regex );
		Matcher matcher = p.matcher( data );
		StringBuffer strBuffer = new StringBuffer();
		while( matcher.find() )
		{
			strBuffer.append( matcher.group() + "," );
		}
		
		return strBuffer.toString().split( "," );
	}
	
	/**
	 * 
	 * @param data the String testing match the regex
	 * @param regex	 the String regex
	 * @return whether the string match the regex
	 */
	private static boolean isMatchRegex( String data, String regex )
	{
		Pattern p = Pattern.compile( regex );
		Matcher matcher = p.matcher( data );
		if( matcher.find() )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void println(Object o)
	{
		System.out.println(o);
	}
}
