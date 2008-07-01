/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Event;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ImageComposite;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ChalkAction extends BasicAction implements MouseMoveListener, MouseListener, KeyListener, PaintListener
{
	private JZoomerWindow window;

	// private Canvas canvas;
	private ImageComposite canvasContainer;

	private Image currentImage, zoomImage;

	private GC gc;

	private Point startPoint, endPoint;

	private int chalkSize = JZoomerConstant.CHALK_DEFAULT_SIZE;
	private Color chalkColor = JZoomerConstant.CHALK_DEFAULT_COLOR;

	private ColorDialog colorDialog;

	private RGB rgb;

	// /**
	// * @param canvas
	// * the canvas to set
	// */
	// public void setCanvas( Canvas canvas )
	// {
	//
	// this.canvas = canvas;
	// }

	public ChalkAction( JZoomerWindow w )
	{

		super( AS_RADIO_BUTTON );
		window = w;
		setChecked( true );
		setText( getMessage( "action.chalk.text" ) );
		setToolTipText( getMessage( "action.chalk.tooltip" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( ChalkAction.class, "/icons/chalk.gif" ) );
	}

	private void initColorDialog()
	{

		if( colorDialog == null )
		{
			colorDialog = new ColorDialog( window.getShell() );
			colorDialog.setText( getMessage( "action.chalk.colorDialog.title" ) );
		}
	}

	public void run()
	{

		initColorDialog();

		if( isChecked() )
		{
			canvasContainer.setCursor( JZoomerConstant.CURSOR_CHALK );
			canvasContainer.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
			// canvas.dispose();
			// canvas = new Canvas( canvasContainer, SWT.NONE );
			// canvas.setSize( canvasContainer.getSize() );
			// canvas.setLocation( 0, 0 );
			// canvas.setBackgroundMode( SWT.INHERIT_FORCE );
			// canvas.setVisible( true );

		} else
		{
			canvasContainer.setCursor( JZoomerConstant.CURSOR_CROSS );
			// canvasContainer.setVisible( false );
		}

	}

	private void increaseChalkSize()
	{

		chalkSize++;
		canvasContainer.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
	}

	private void decreaseChalkSize()
	{

		if( chalkSize == 1 )
			return;

		chalkSize--;
		canvasContainer.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
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
		canvasContainer.setToolTipText( getMessage( "action.chalk.canvas.tooltip", "" + chalkSize ) );
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
		// canvas.setSize( zoomSize.x, zoomSize.y );
		canvasContainer.getBackgroundImage().dispose();
		canvasContainer.setBackgroundImage( zoomImage );
		DisplayUtil.setWidgetAtCenter( canvasContainer );
		gc = new GC( canvasContainer );
	}

	private void drawChalkLine( GC gc )
	{

		// TODO need to improve performance
		gc.setForeground( chalkColor );
		gc.setBackground( chalkColor );

		//System.out.println( "ChalkAction: drawChalkLine>>" + startPoint + ":::" + endPoint );
		if( startPoint.x == endPoint.x )
		{
			gc.drawLine( startPoint.x, startPoint.y, endPoint.x, endPoint.y );
		} else
		{
			gc.fillPolygon( new int[]
			{
					startPoint.x, startPoint.y, 
					endPoint.x, endPoint.y, 
					endPoint.x, endPoint.y + chalkSize, 
					startPoint.x, startPoint.y + chalkSize
			} );
		}
	}

	public void paintControl( PaintEvent e )
	{

		//Image image = canvasContainer.getBackgroundImage();
		//gc = new GC( image );
		drawChalkLine( e.gc );
		//e.gc.drawImage( image, 0, 0 );
		// drawChalkLine( e );
		//gc.dispose();
		//e.gc.dispose();
	}

	public void mouseMove( MouseEvent e )
	{

		if( isChecked() )
		{
			if( e.stateMask == SWT.BUTTON1 )
			{
				//System.out.println( "ChalkAction:" + e.x + "," + e.y );
				endPoint = new Point( e.x, e.y );
				drawChalkLine(gc);
				//canvasContainer.redraw();
				// canvasContainer.redraw(
				// startPoint.x - Math.abs( startPoint.x - endPoint.x ), startPoint.y - Math.abs(
				// startPoint.y-endPoint.y ),
				// 4 * Math.abs( startPoint.x - endPoint.x ), 4 * Math.abs( startPoint.y-endPoint.y
				// ),
				//						true );
				
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
				currentImage = canvasContainer.getBackgroundImage();
				gc = new GC( canvasContainer );
			    
				startPoint = new Point( e.x, e.y );
				System.out.println( "ChalkAction: button1 pressed >>" + startPoint );
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
				System.out.println( "ChalkAction: button1 & shift released" );
				endPoint = new Point( e.x, e.y );
				drawChalkLine(gc);
				// canvasContainer.redraw(
				// startPoint.x, startPoint.y,
				// Math.abs( startPoint.x - endPoint.x ), Math.abs( startPoint.y-endPoint.y ),
				//						true );
			}
			
			gc.dispose();
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

	/**
	 * @param canvasContainer
	 *            the canvasContainer to set
	 */
	public void setCanvasContainer( ImageComposite canvasContainer )
	{

		this.canvasContainer = canvasContainer;
	}

}
