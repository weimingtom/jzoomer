/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import java.awt.MouseInfo;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tracker;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ChalkAction extends BasicAction implements MouseMoveListener, MouseListener, KeyListener
{
	private JZoomerWindow window;

	private Canvas canvas;

	private Image currentImage, zoomImage;

	private GC gc;

	private Point startPoint;

	private int chalkSize = JZoomerConstant.CHALK_DEFAULT_SIZE;
	private Color chalkColor = JZoomerConstant.CHALK_DEFAULT_COLOR;

	private ColorDialog colorDialog;

	private RGB rgb;

	/**
	 * @param canvas
	 *            the canvas to set
	 */
	public void setCanvas( Canvas canvas )
	{

		this.canvas = canvas;
	}

	public ChalkAction( JZoomerWindow w )
	{

		super( AS_RADIO_BUTTON );
		window = w;
		setChecked( true );
		setText( getMessage( "action.chalk.text" ) );
		setToolTipText( getMessage( "action.chalk.tooltip" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( ChalkAction.class, "/icons/chalk.gif" ) );
	}

	public void run()
	{

		if( colorDialog == null )
		{
			colorDialog = new ColorDialog( window.getShell() );
			colorDialog.setText( getMessage( "action.chalk.colorDialog.title" ) );
		}

		if( isChecked() )
		{
			canvas.setCursor( JZoomerConstant.CURSOR_CHALK );
			canvas.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
		} else
		{
			canvas.setCursor( JZoomerConstant.CURSOR_CROSS );
		}

	}

	private void increaseChalkSize()
	{

		chalkSize++;
		canvas.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
	}

	private void decreaseChalkSize()
	{

		if( chalkSize == 1 )
			return;

		chalkSize--;
		canvas.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
	}

	private void chooseChalkColor()
	{

		colorDialog.setRGB( chalkColor.getRGB() );

		rgb = colorDialog.open();
		if( rgb != null )
		{
			chalkColor = SWTResourceManager.getColor( rgb );
		}
	}

	private void restoreChalkSize()
	{

		chalkSize = JZoomerConstant.CHALK_DEFAULT_SIZE;
		canvas.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
	}

	private void eraseChalk()
	{
		if( zoomImage != null )
		{
			zoomImage.dispose();
		}
		
		currentImage = window.getCurrentImage();
		zoomImage = ImageUtil.getScaledImage( currentImage, window.getCurrentZoomRate() );
		Point zoomSize = new Point( zoomImage.getBounds().width, zoomImage.getBounds().height );
		canvas.setSize( zoomSize.x, zoomSize.y );
		canvas.getBackgroundImage().dispose();
		canvas.setBackgroundImage( zoomImage );
		DisplayUtil.setWidgetAtCenter( canvas );
	}

	
	private void drawChalkLine( MouseEvent e )
	{
		//TODO need to improve performance
		gc.setForeground( chalkColor );
		gc.setBackground( chalkColor );
		
		if( startPoint.x == e.x )
		{
			gc.drawLine( startPoint.x, startPoint.y, e.x, e.y );
		}
		else
		{
			gc.fillPolygon( new int[]{ 
				startPoint.x, startPoint.y, 
				e.x, e.y, 
				e.x, e.y + chalkSize, 
				startPoint.x, startPoint.y + chalkSize 
			});
		}
	}
	
	public void mouseMove( MouseEvent e )
	{

		if( isChecked() )
		{
			if( e.stateMask == SWT.BUTTON1 )
			{
				// System.out.println( "ChalkAction:" + e.x + "," + e.y )

				drawChalkLine(e);

				startPoint = new Point( e.x, e.y );
			}
		}
	}

	public void mouseDoubleClick( MouseEvent e )
	{

	}

	public void mouseDown( MouseEvent e )
	{

		if( isChecked() )
		{
			if( e.button == 1 )
			{
				//System.out.println( "ChalkAction: button1 pressed" );
				currentImage = canvas.getBackgroundImage();
				gc = new GC( canvas );
				startPoint = new Point( e.x, e.y );
			}
		}

	}

	public void mouseUp( MouseEvent e )
	{

		if( isChecked() )
		{
			System.out.println( e.stateMask );
			if( e.stateMask == ( SWT.SHIFT | SWT.BUTTON1 ) )
			{
				//System.out.println( "ChalkAction: button1 & shift released" );
				drawChalkLine( e );
			}
			
			
			if( gc != null )
			{
				gc.dispose();
			}
		}
	}

	public void keyReleased( KeyEvent e )
	{

	}

	public void keyPressed( KeyEvent e )
	{

		if( isChecked() )
		{
			switch( e.character )
			{
				// increase chalk size
				case '=':
				case '+':
					increaseChalkSize();
					break;
				// decrease chalk size
				case '-':
				case '_':
					decreaseChalkSize();
					break;
				// restore chalk size
				case 'd':
				case 'D':
					restoreChalkSize();
					break;
				// erase chalk mark
				case 'e':
				case 'E':
					eraseChalk();
					break;
				// choose chalk color
				case 'c':
				case 'C':
					chooseChalkColor();
					break;
			}

		}

	}

}
