/*
 * @author talent_marquis<甜菜侯爵> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<甜菜侯爵>
 * All rights reserved.
 */
package com.dextrys.trilogy.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class SourceBundlesReader
{
	private ResourceBundle bundle;

	public SourceBundlesReader()
	{

	}
	
	public SourceBundlesReader(String bundleName )
	{
		setBundle( bundleName );
	}
	
	public SourceBundlesReader(String bundleName, Locale locale )
	{
		setBundle( bundleName, locale );
	}

	
	public void setBundle( String bundleName, Locale locale )
	{
		bundle = ResourceBundle.getBundle( bundleName, locale );
	}
	
	public void setBundle( String bundleName )
	{
		bundle = ResourceBundle.getBundle( bundleName );
	}
	
	public String getProperty( String key )
	{

		return bundle.getString( key );
	}

	public int getIntegerProperty( String key )
	{

		return Integer.valueOf( bundle.getString( key ) );
	}
	
	public boolean getBooleanProperty( String key )
	{
		return Boolean.valueOf( bundle.getString( key ) );
	}
	
	public double getDoubleProperty( String key )
	{
		return Double.valueOf( bundle.getString( key ) );
	}

	public String getProperty( String key, String... args )
	{

		String str = bundle.getString( key );

		if( args.length == 0 )
		{
			return str;
		}
		for( int i = 0; i < args.length; i++ )
		{
			str = str.replace( "${" + i + "}", args[ i ] );
		}
		return str;
	}
}
