package com.dextrys.trilogy.toolkit.jzoomer.common;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import org.eclipse.swt.graphics.Color;
import com.dextrys.trilogy.util.SystemPropertiesReader;

/*
 * @author talent_marquis<甜菜侯爵> Email: talent_marquis@163.com Copyright (C) 2008 talent_marquis<甜菜侯爵>
 * All rights reserved. Create Date:Jun 14, 2008
 */
public class JZoomerConstant
{
	public static final String SYSTEM_FILE = "conf/system.properties";
	private static SystemPropertiesReader reader = new SystemPropertiesReader(SYSTEM_FILE);
	
	public static final String ACTION_MESSAGE_BUNDLE_NAME = "language.action_message";
	public static final String WINDOW_MESSAGE_BUNDLE_NAME = "language.window_message";
	public static final String DIALOG_MESSAGE_BUNDLE_NAME = "language.dialog_message";
	
	public static Robot ROBOT = getRobot();
	
	
	public static final String VERSION = reader.getProperty( "version" );							// v1.01
	public static final String AUTHOR = reader.getProperty( "author" );								// me
	
	public static final int ZOOM_RATE_MIN = reader.getIntegerProperty( "zoom.rate.min" ); 			// 1
	public static final int ZOOM_RATE_MAX = reader.getIntegerProperty( "zoom.rate.max" ); 			// 8
	public static final int ZOOM_RATE_DEFAULT = reader.getIntegerProperty( "zoom.rate.default" ); 	// 2
	public static final int WINDOW_INIT_WIDTH = reader.getIntegerProperty( "init.width" );			// 300
	public static final int WINDOW_INIT_HEIGHT = reader.getIntegerProperty( "init.height" ); 		// 200
	public static final int WINDOW_MIN_WIDTH = reader.getIntegerProperty( "min.width" );			// 200
	public static final int WINDOW_MIN_HEIGHT = reader.getIntegerProperty( "min.height" );			// 150
	public static final int IMAGE_SCALE_TYPE = reader.getIntegerProperty( "image.scale.type" );		// 1
	
	public static final Color BACKGROUND_COLOR_DEFAULT = reader.getColorProperty( "background.color" );
	
	public static final Locale LOCALE = ( "".equals( reader.getProperty( "locale" ) ) ) ? null : new Locale( reader.getProperty( "locale" ) );
	
	public static final int CAPTURE_PER_MILLISECOND = reader.getIntegerProperty( "capture.millisecond" );	// 50

	public static final boolean ALWAYS_ON_TOP = reader.getBooleanProperty( "on_top" );			// true
	public static final boolean EXIT_CONFIRM = reader.getBooleanProperty( "exit.confirm" );		// false

	public static Robot getRobot()
	{
		try
		{
			return new Robot();
		}
		catch( AWTException e )
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static URL newURL( String url_name )
	{
		try
		{
			return new URL( url_name );
		}
		catch( MalformedURLException e )
		{
			throw new RuntimeException( "Malformed URL " + url_name, e );
		}
	}
	
}
