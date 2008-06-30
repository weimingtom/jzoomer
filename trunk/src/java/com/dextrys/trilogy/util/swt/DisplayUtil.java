/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.util.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class DisplayUtil
{
	/**
	 * make the control display at the mouse's location
	 * 
	 * @param control
	 */
	public static void showOnAtMouseLocation( Control control )
	{

		int mouseX = Display.getDefault().getCursorLocation().x;
		int mouseY = Display.getDefault().getCursorLocation().y;
		control.setLocation( mouseX - control.getSize().x / 2, mouseY - control.getSize().y / 2 );
	}

	/**
	 * make the control display at the middle of the screen
	 * 
	 * @param control
	 */
	public static void showOnAtScreenMiddle( Control control )
	{

		int screenWidth = Display.getDefault().getPrimaryMonitor().getBounds().width;
		int screenHeight = Display.getDefault().getPrimaryMonitor().getBounds().height;

		control.setLocation( ( screenWidth - control.getSize().x ) / 2, ( screenHeight - control.getSize().y ) / 2 );

	}

	/**
	 * Make the control disploy at the middle of the parent Composite
	 * @author marquis
	 * @Create Date: Jun 17, 2008
	 * @param control
	 */
	public static void setWidgetAtCenter( Control control )
	{
		if( control.getParent() == null )
		{//Top shell
			showOnAtScreenMiddle( control );
			return;
		}

		int parentWidth = control.getParent().getSize().x;
		int parentHeight = control.getParent().getSize().y;

		control.setLocation( ( parentWidth - control.getSize().x ) / 2, ( parentHeight - control.getSize().y ) / 2 );
	}
	
	public static void setTransparent( Control control, int alpha )
	{

		OS.SetWindowLong( control.handle, OS.GWL_EXSTYLE, OS.GetWindowLong( control.handle, OS.GWL_EXSTYLE ) ^ 0x80000 );

		TCHAR lpLibFileName = new TCHAR( 0, "User32.dll", true );
		int hInst = OS.LoadLibrary( lpLibFileName );
		if( hInst != 0 )
		{
			String name = "SetLayeredWindowAttributes\0";
			byte[] lpProcName = new byte[ name.length() ];
			for( int i = 0; i < lpProcName.length; i++ )
			{
				lpProcName[ i ] = ( byte ) name.charAt( i );
			}
			final int fun = OS.GetProcAddress( hInst, lpProcName );
			if( fun != 0 )
			{

				OS.CallWindowProc( fun, control.handle, 0, 200, 2 );
			}
			OS.FreeLibrary( hInst );
		}
	}

	public static Point getMouseLocationInParent( MouseEvent e )
	{
		Control c = (Control)e.getSource();
		if( c.getParent() == null )
		{
			return new Point( e.x, e.y );
		}
		
		return new Point( c.getLocation().x + e.x, c.getLocation().y + e.y );
	}

	public static void main( String args[] )
	{

		// DisplayUtil.setShowOnInTheMiddle();
	}

	/**
	 * 
	 * @param fontData
	 *            the file style in SWT
	 * @return the file style in SWING
	 */
	public static java.awt.Font getAWTFont( FontData fontData )
	{

		String fontName = fontData.getName();
		int fontSize = fontData.getHeight();
		int fontStyle = fontData.getStyle();

		if( fontStyle == SWT.BOLD )
		{
			fontStyle = java.awt.Font.BOLD;
		} else if( fontStyle == SWT.ITALIC )
		{
			fontStyle = java.awt.Font.ITALIC;
		} else if( fontStyle == ( SWT.BOLD | SWT.ITALIC ) )
		{
			fontStyle = java.awt.Font.BOLD + java.awt.Font.ITALIC;
		} else
		{
			fontStyle = java.awt.Font.PLAIN;
		}

		return new java.awt.Font( fontName, fontStyle, fontSize );

	}

	/**
	 * 
	 * @param rgb
	 *            the color style in SWT
	 * @return the color style in AWT
	 */
	public static java.awt.Color getAWTColor( RGB rgb )
	{

		return new java.awt.Color( rgb.red, rgb.green, rgb.blue );
	}

}
