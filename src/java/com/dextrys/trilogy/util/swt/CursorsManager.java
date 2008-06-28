package com.dextrys.trilogy.util.swt;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/*
 * @author Marquis Hou<talent_marquis> Email: marquisx.tz@gmail.com Copyright (C) 2008 Marquis Hou<talent_marquis>
 * All rights reserved. Create Date:2008-6-28
 */
/**
 * @author marquis Modified Date:2008-6-28
 */
public class CursorsManager
{

	/**
	 * Maps IDs to cursors
	 */
	private static HashMap< String, Cursor > stringToCursorMap = new HashMap< String, Cursor >();


	protected static Cursor getCursor( InputStream is )
	{

		Display display = Display.getDefault();
		ImageData data = new ImageData( is );
		if( data.transparentPixel > 0 )
		{
			int hotsPotX = 0;
			int hotsPotY = 0;
			
			searchHotsPot:
			for( int i = 0; i < data.width; i ++ )
			{
				for( int j = 0; j < data.height; j ++ )
				{
					int pixel = data.getPixel( i, j );
					if( pixel != data.transparentPixel )
					{
						hotsPotX = i; 
						hotsPotY = j;
						break searchHotsPot;
					}
				}
			}
				
			return new Cursor( display, data, hotsPotX, hotsPotY );
		}
		
		return new Cursor( display, data, 0, 0 );
	}
	
	public static Cursor getCursor( String path )
	{
		return getCursor( CursorsManager.class, path );
	}
		
	public static Cursor getCursor( Class< ? > clazz, String path )
	{
		String key = clazz.getName() + '|' + path;

		Cursor cursor = stringToCursorMap.get( key );
		if( cursor == null )
		{
			try
			{
				if( path.length() > 0 && path.charAt( 0 ) == '/' )
				{
					String newPath = path.substring( 1, path.length() );
					cursor = getCursor( new BufferedInputStream( clazz.getClassLoader().getResourceAsStream( newPath ) ) );
				}
				else
				{
					cursor = getCursor( new BufferedInputStream( clazz.getClassLoader().getResourceAsStream( path ) ));
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
			
		}
		return cursor;
	}

	/**
	 * Dispose all of the cached cursors
	 */
	public static void disposeCursors()
	{

		for( Iterator< Cursor > iter = stringToCursorMap.values().iterator(); iter.hasNext(); )
			iter.next().dispose();
		stringToCursorMap.clear();
	}
	
	public static void main( String[] args )
	{
		Cursor c = getCursor( CursorsManager.class, "/icons/chalk.gif");
	}

}
