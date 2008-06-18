/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Robot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ColorInfoGroup;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ColorAction extends BasicAction implements MouseListener, MouseMoveListener// ,
																						// KeyListener
{
	private static final String Control = null;
	private JZoomerWindow window;
	private Canvas canvas;
	private Robot robot;
	private int r, g, b;
	private boolean isHotKeyPressed, isStopped;;
	private ColorInfoGroup colorInfoGroup;

	private static Clipboard clipboard = new Clipboard( Display.getCurrent() );
	private static TextTransfer text_transfer = TextTransfer.getInstance();
	
	public ColorAction( JZoomerWindow w )
	{

		super( AS_CHECK_BOX );
		window = w;
		try
		{
			robot = new Robot();
		}
		catch( AWTException e )
		{
			e.printStackTrace();
		}
		setChecked( false );
		setText( getMessage( "action.color.text" ) );
		setToolTipText( getMessage( "action.color.tooltip" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( ColorAction.class, "/icons/colorpicker.png" ) );
	}

	public void run()
	{

		if( isChecked() )
		{
			canvas.setCursor( JZoomerWindow.CURSOR_CROSS );
			canvas.setToolTipText( getMessage( "action.color.canvas.tooltip" ) );
			toggleColorPick( true );
		} else
		{
			canvas.setCursor( JZoomerWindow.CURSOR_ARROW );
			canvas.setToolTipText( "" );
			toggleColorPick( false );
		}
	}

	private void pickupColor()
	{

		System.out.println( "pick up color" );
		Color color = getCurrentMouseLocationColor();
		colorInfoGroup.setColorInfo( SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() ) );
		//TODO save color info into clip
		String cHtml = getColorHtml();
		
		clipboard.setContents( new Object[]{ cHtml }, new Transfer[]{ text_transfer } );
		
	}
	
	public String getColorHtml()
	{
		return colorInfoGroup.getColorHtml();
	}

	private Color getCurrentMouseLocationColor()
	{

		int mouseX = MouseInfo.getPointerInfo().getLocation().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y;

		return robot.getPixelColor( mouseX, mouseY );
	}

	private void showColorInfo()
	{

		System.out.println( "show color info" );
		if( isStopped ) return;
		Color color = getCurrentMouseLocationColor();
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();

		colorInfoGroup.setColorInfo( SWTResourceManager.getColor( r, g, b ) );
	}

	public void mouseDoubleClick( MouseEvent e )
	{

		// pickup color immediately
		pickupColor();
	}
	public void mouseUp( MouseEvent e )
	{
		//toggle stop/start show color info
		isStopped = !isStopped;
	}

	public void mouseDown( MouseEvent e )
	{

		// if( e.stateMask == ( SWT.BUTTON1 | 'c')||e.stateMask == ( SWT.BUTTON1 | 'C' ) )
		// {
		// pickupColor( e );
		// }
		
	}

	public void mouseMove( MouseEvent e )
	{

		// System.out.println( e.stateMask );
		if( isHotKeyPressed )
		{
			showColorInfo();
		}

	}

	public void keyPressed( KeyEvent e )
	{

		// if( e.keyCode == 'c' || e.keyCode == 'C' )
		// {
		// System.out.println( "c pressed" );
		// canvas.setCursor( JZoomerWindow.CURSOR_CROSS );
		// toggleColorPick( true );
		// }
	}

	private void toggleColorPick( boolean flag )
	{

		isHotKeyPressed = flag;
		isStopped = !flag;
		colorInfoGroup.setVisible( flag );
	}

	public void keyReleased( KeyEvent e )
	{

		// if( e.keyCode == 'c' || e.keyCode == 'C' )
		// {
		// System.out.println( "c released" );
		// canvas.setCursor( JZoomerWindow.CURSOR_ARROW );
		// toggleColorPick( false );
		// }
	}

	/**
	 * @param canvas
	 *            the canvas to set
	 */
	public void setCanvas( Canvas canvas )
	{

		this.canvas = canvas;
	}

	public void setColorInfoGroup( ColorInfoGroup colorInfoGroup )
	{

		this.colorInfoGroup = colorInfoGroup;
	}
}
