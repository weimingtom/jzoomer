package com.dextrys.trilogy.toolkit.jzoomer.common;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import com.dextrys.trilogy.util.SystemPropertiesReader;
import com.dextrys.trilogy.util.swt.CursorsManager;

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
	
	public static final Cursor CURSOR_CHALK = CursorsManager.getCursor( "/icons/chalk.gif" );
	public static final Cursor CURSOR_COLORPICKER = CursorsManager.getCursor( "/icons/colorpicker.gif" );
	public static final Cursor CURSOR_HAND = new Cursor( Display.getCurrent(), SWT.CURSOR_HAND );
	public static final Cursor CURSOR_CROSS = new Cursor( Display.getCurrent(), SWT.CURSOR_CROSS );
	public static final Cursor CURSOR_ARROW = new Cursor( Display.getCurrent(), SWT.CURSOR_ARROW );
	
	public static final String VERSION = reader.getProperty( "version" );							// v1.01
	public static final String AUTHOR = reader.getProperty( "author" );								// me
	//init window
	public static final int ZOOM_RATE_MIN = reader.getIntegerProperty( "zoom.rate.min" ); 			// 1
	public static final int ZOOM_RATE_MAX = reader.getIntegerProperty( "zoom.rate.max" ); 			// 8
	public static final int ZOOM_RATE_DEFAULT = reader.getIntegerProperty( "zoom.rate.default" ); 	// 2
	public static final int WINDOW_INIT_WIDTH = reader.getIntegerProperty( "init.width" );			// 300
	public static final int WINDOW_INIT_HEIGHT = reader.getIntegerProperty( "init.height" ); 		// 200
	public static final int WINDOW_MIN_WIDTH = reader.getIntegerProperty( "min.width" );			// 200
	public static final int WINDOW_MIN_HEIGHT = reader.getIntegerProperty( "min.height" );			// 150
	public static final int IMAGE_SCALE_TYPE = reader.getIntegerProperty( "image.scale.type" );		// 1

	public static final Color BACKGROUND_COLOR_DEFAULT = reader.getColorProperty( "background.color" );
	//chalk
	public static final int CHALK_DEFAULT_SIZE = reader.getIntegerProperty( "chalk.default.size" );		//1
	public static final Color CHALK_DEFAULT_COLOR = reader.getColorProperty( "chalk.default.color" );	//255,0,0
	
	public static final Locale LOCALE = ( "".equals( reader.getProperty( "locale" ) ) ) ? null : new Locale( reader.getProperty( "locale" ) );
	
	public static final int CAPTURE_PER_MILLISECOND = reader.getIntegerProperty( "capture.millisecond" );	// 50

	public static final boolean ALWAYS_ON_TOP = reader.getBooleanProperty( "on_top" );					// true
	public static final boolean EXIT_CONFIRM = reader.getBooleanProperty( "exit.confirm" );				// false
	public static final boolean TRAY_MESSAGE_SHOW = reader.getBooleanProperty( "tray.message.show" );	//true
	

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
