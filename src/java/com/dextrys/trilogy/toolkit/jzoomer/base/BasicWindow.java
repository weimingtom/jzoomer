package com.dextrys.trilogy.toolkit.jzoomer.base;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.SourceBundlesReader;
import com.dextrys.trilogy.util.swt.DisplayUtil;

/**
 * 
 * @author marquis Modified Date:Jun 16, 2008
 */
public class BasicWindow extends ApplicationWindow
{
	private static SourceBundlesReader reader;

	public static final Cursor CURSOR_HAND = JZoomerConstant.CURSOR_HAND;
	public static final Cursor CURSOR_CROSS = JZoomerConstant.CURSOR_CROSS;
	public static final Cursor CURSOR_ARROW = JZoomerConstant.CURSOR_ARROW;

	public BasicWindow()
	{
		super( null );
		if( JZoomerConstant.LOCALE != null )
		{
			reader = new SourceBundlesReader( JZoomerConstant.WINDOW_MESSAGE_BUNDLE_NAME, JZoomerConstant.LOCALE );
		} else
		{
			reader = new SourceBundlesReader( JZoomerConstant.WINDOW_MESSAGE_BUNDLE_NAME );
		}
	}

	// ====================Message Reader========================
	protected String getMessage( String key )
	{

		return reader.getProperty( key );
	}

	protected String getMessage( String key, String... args )
	{

		return reader.getProperty( key, args );
	}

	protected int getIntegerMessage( String key )
	{

		return reader.getIntegerProperty( key );
	}

	protected boolean getBooleanMessage( String key )
	{

		return reader.getBooleanProperty( key );
	}

	protected double getDoubleMessage( String key )
	{

		return reader.getDoubleProperty( key );
	}

	// ====================Display========================
	protected void setShowAtScreenCenter()
	{

		DisplayUtil.showOnAtScreenMiddle( getShell() );
	}

	protected void setShowAtMouseLocation()
	{

		DisplayUtil.showOnAtMouseLocation( getShell() );
	}

	protected void setWidgetAtComponiteCenter( Control control )
	{

		DisplayUtil.setWidgetAtCenter( control );
	}

}
