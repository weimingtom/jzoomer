/*
 * @author talent_marquis<甜菜侯爵> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<甜菜侯爵>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.base;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.SourceBundlesReader;
import com.dextrys.trilogy.util.swt.DisplayUtil;

public class BasicAction extends Action
{
	private static SourceBundlesReader reader;
	
	protected static final int SCREEN_WIDTH = Display.getDefault().getPrimaryMonitor().getBounds().width;
	protected static final int SCREEN_HEIGHT = Display.getDefault().getPrimaryMonitor().getBounds().height;
	
	public BasicAction( int style )
	{
		super(null, style);
	}
	
	public BasicAction()
	{

		super();
		if( JZoomerConstant.LOCALE != null )
		{
			reader = new SourceBundlesReader( JZoomerConstant.ACTION_MESSAGE_BUNDLE_NAME, JZoomerConstant.LOCALE );
		}
		else
		{
			reader = new SourceBundlesReader( JZoomerConstant.ACTION_MESSAGE_BUNDLE_NAME );
		}
	}

	public String getMessage( String key )
	{

		return reader.getProperty( key );
	}

	public String getMessage( String key, String... args )
	{

		return reader.getProperty( key, args );
	}

	public int getIntegerMessage( String key )
	{

		return reader.getIntegerProperty( key );
	}
	
	public boolean getBooleanMessage( String key )
	{
		return reader.getBooleanProperty( key );
	}
	
	protected double getDoubleMessage( String key )
	{
		return reader.getDoubleProperty( key );
	}
	
	
	protected Point getMouseLocationInParent( MouseEvent e )
	{
		return DisplayUtil.getMouseLocationInParent( e );
	}
	
}
