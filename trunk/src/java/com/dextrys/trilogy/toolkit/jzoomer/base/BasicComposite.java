package com.dextrys.trilogy.toolkit.jzoomer.base;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.SourceBundlesReader;
import com.swtdesigner.SWTResourceManager;

/*
 * @author Marquis Hou<talent_marquis>
 * Email: marquisx.tz@gmail.com
 * Copyright (C) 2008 Marquis Hou<talent_marquis>
 * All rights reserved.
 * Create Date:Jun 17, 2008
 */
public class BasicComposite extends Composite implements MouseMoveListener, MouseListener
{
	private static SourceBundlesReader reader;
	private int locationX = 0;
	private int locationY = 0;
	private Cursor currentCursor;

	public BasicComposite( Composite parent, int style )
	{

		super( parent, style );
		
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
	
	// ====================Mouse Listener========================
	public void mouseMove( MouseEvent e )
	{

		// when mouse move with left button pressed, implement move-following-mouse moving
		int mouseX;
		int mouseY;

		Control c = ( Control ) e.getSource();
		if( e.stateMask == SWT.BUTTON1 && e.getSource() instanceof Composite && c.getParent() instanceof Shell )
		{
			c.setCursor( BasicWindow.CURSOR_HAND );
			mouseX = Display.getDefault().getCursorLocation().x;
			mouseY = Display.getDefault().getCursorLocation().y;
			getShell().setLocation( mouseX - locationX, mouseY - locationY );
		} else if( e.stateMask == ( SWT.CTRL | SWT.BUTTON1 ) && !( c.getParent() instanceof Shell ) )
		{
			c.setCursor( BasicWindow.CURSOR_HAND );
			mouseX = e.x + c.getLocation().x;
			mouseY = e.y + c.getLocation().y;
			System.out.println( "mouse move: e (x,y)" + "(" + e.x + "," + e.y + ")" );
			System.out.println( "mouse move: canvas (x,y)" + "(" + c.getLocation().x + "," + c.getLocation().y + ")" );
			c.setLocation( mouseX - locationX, mouseY - locationY );
		}

	}
	public void mouseDown( MouseEvent e )
	{

		if( e.button == 1 )
		{// press down mouse left button
			int mouseX;
			int mouseY;
			Control c = ( Control ) e.getSource();
			currentCursor = c.getCursor();
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{
				mouseX = Display.getDefault().getCursorLocation().x;
				mouseY = Display.getDefault().getCursorLocation().y;
				locationX = mouseX - getShell().getLocation().x;
				locationY = mouseY - getShell().getLocation().y;
				System.out.println( "composite (x,y)" + "(" + locationX + "," + locationY + ")" );

			} else
			{
				locationX = e.x;
				locationY = e.y;
				// System.out.println( "mouse down: e (x,y)" + "(" + e.x + "," + e.y + ")" );
				// System.out.println( "mouse down:canvas (x,y)" + "(" + locationX + "," + locationY
				// + ")" );
			}
		}
	}

	public void mouseUp( MouseEvent e )
	{

		if( e.button == 1 )
		{// mouse left button up
			Control c = ( Control ) e.getSource();
			c.setCursor( currentCursor );
		}
	}

	public void mouseDoubleClick( MouseEvent e )
	{

	}

	protected void checkSubclass()
	{
		//do nothing
	}
}
