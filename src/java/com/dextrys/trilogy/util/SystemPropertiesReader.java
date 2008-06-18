/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.eclipse.swt.graphics.Color;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.swtdesigner.SWTResourceManager;

public class SystemPropertiesReader
{
	public static final String SYSTEM_FILE = "conf/system.properties";
	private static Properties prop = new Properties();
	
	public SystemPropertiesReader()
	{
		this( SYSTEM_FILE );
	}
	
	public SystemPropertiesReader( String filePath )
	{
		loadProperty( filePath );
	}
	
	public static Properties loadProperty( String filePath )
	{
		FileInputStream in;
		try
		{
			in = new FileInputStream( new File( filePath ) );
			prop.load( in );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		return prop;
	}
	
	public static void saveProperty()
	{
		saveProperty( SYSTEM_FILE );
	}
	
	public static void saveProperty( String filePath )
	{
		FileOutputStream out;
		try
		{
			out = new FileOutputStream( new File( filePath ) );
			prop.store(out, "");
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	public static String getProperty( String key )
	{
		return prop.getProperty( key );
	}
	
	public static Integer getIntegerProperty( String key )
	{
		String temp = prop.getProperty( key ) ;
		return Integer.valueOf( prop.getProperty( key ) );
	}
	
	public static boolean getBooleanProperty( String key )
	{
		return Boolean.valueOf( prop.getProperty( key ) );
	}
	
	public static Color getColorProperty( String key )
	{
		String[] rgbStr = getProperty( key ).split( "," );
		
		int r = Integer.valueOf( rgbStr[0] );
		int g = Integer.valueOf( rgbStr[1] );
		int b = Integer.valueOf( rgbStr[2] );
		
		return SWTResourceManager.getColor( r, g, b );
	}
	
	public static void setProperty( String key, String value )
	{
		prop.setProperty( key, value );
	}
	
	public static void main( String args[])
	{
		//setProperty( "aaa", "bbb");
		//saveProperty();
		SystemPropertiesReader reader = new SystemPropertiesReader();
		String a = reader.getProperty( "locale" );
		System.out.println( "".equals( a ));
	}

}
