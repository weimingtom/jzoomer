/*
 * @author talent_marquis<��˺��>
 * Email: talent_marquis@163.com
 * Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.util;

public class StringUtil
{

	/**
	 * 
	 * @author Marquis
	 * @Create Date: 2008-3-29
	 * @param str
	 * @return
	 */
	public static boolean isStringEmpty( String str )
	{
		if( "".equals( str ) || str == null )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @author Marquis
	 * @Create Date: 2008-3-29
	 * @param str
	 * @return
	 */
	public static String deNull( String str )
	{
		return str == null? "": str;
	}
	
	/**
	 * 
	 * @param objectArray
	 * @return stringArray
	 */
	public static String[] convertObjectArrayToStringArray( Object[] objectArray )
	{
		String[] stringArray = new String[ objectArray.length ];
		int i = 0;
		for( Object o : objectArray )
		{
			stringArray[ i ] = o.toString();
			i++;
		}
		// Arrays.sort( stringArray );
		return stringArray;
	}
}
