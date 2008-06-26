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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tracker;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class TrackerAction extends BasicAction implements MouseMoveListener, MouseListener, ControlListener// ,
// Listener
{
	private JZoomerWindow window;
	private Tracker tracker;
	private Point point;
	private Canvas canvas;

	private DefaultToolTip tooltip;

	/**
	 * @param canvas
	 *            the canvas to set
	 */
	public void setCanvas( Canvas canvas )
	{

		this.canvas = canvas;
		if( tooltip == null )
		{
			tooltip = new DefaultToolTip( canvas );
			tooltip.deactivate();
			tooltip.setHideOnMouseDown( true );
		}
	}

	public TrackerAction( JZoomerWindow w )
	{

		super( AS_CHECK_BOX );
		window = w;
		setChecked( true );
		setText( getMessage( "action.tracker.text" ) );
		// setToolTipText( getMessage("action.exit.tooltip") );
		setImageDescriptor( ResourceManager.getImageDescriptor( TrackerAction.class, "/icons/measure.png" ) );
	}

	public void run()
	{

	}

	private Point getTooltipLocation( Point point )
	{

		return new Point( point.x + 1, point.y + 1 );
	}

	private int getRealValue( int scaledValue, int zoomRate )
	{
		int realValue;
		if( zoomRate == 0 )
			realValue = 0;
		if( zoomRate == 1 )
		{
			realValue = scaledValue;
		}
		/*
		 * For example: 
		 * if the scaledValue is 8, zoomRate is 8, then the real value is ( 8 - 1 )/8 + 1 = 1 
		 * if the scaledValue is 9, zoomRate is 8, then the real value is ( 9 - 1 )/8 + 1 = 2
		 * if the scaledValue is 4, zoomRate is -4, then the real value is -( 4 * (-4) = 16
		 */
		realValue = ( zoomRate > 0 ) ? ( ( scaledValue - 1 ) / zoomRate + 1 ) : ( -( scaledValue * zoomRate ) );
		
		return realValue;
	}

	/**
	 * @param tracker
	 *            the tracker to set
	 */
	public void setTracker( Tracker tracker )
	{

		this.tracker = tracker;
	}

	public void controlMoved( ControlEvent e )
	{

		// TODO Auto-generated method stub

	}

	public void controlResized( ControlEvent e )
	{
		
		if( isChecked() )
		{
			int rate = window.getCurrentZoomRate();
			Rectangle rectangle = tracker.getRectangles()[ 0 ];
			Point realSize = new Point( getRealValue( rectangle.width, rate ), getRealValue( rectangle.height, rate ) );
			// Point p = new Point( point.x + tracker.getRectangles()[ 0 ].width, point.y +
			// tracker.getRectangles()[ 0 ].height );
			tooltip.setText( getMessage( 
					"action.tracker.canvas.tooltip", 
					"" + rectangle.width, "" + rectangle.height, // scaled width and height
					"" + realSize.x, "" + realSize.y // real width and height
			) );
			tooltip.show( point );
		}
		
		
	}

	public void mouseMove( MouseEvent e )
	{

		// System.out.println( "canvas mouse move" );
		//TODO should have better solution!
		Control c = (Control)e.getSource();
		c.setCursor( window.CURSOR_CROSS );
		
		tooltip.hide();

	}

	public void mouseDoubleClick( MouseEvent e )
	{

		// TODO Auto-generated method stub

	}

	public void mouseDown( MouseEvent e )
	{

		if( isChecked() )
		{
			if( e.button == 1 && e.stateMask == SWT.None )
			{
				point = new Point( e.x, e.y );
				// System.out.println( point );
				tracker.setRectangles( new Rectangle[]
				{
					new Rectangle( point.x, point.y, 0, 0 )
				} );
				// tracker.setStippled( true );
				tracker.open();
			}
		}

	}

	public void mouseUp( MouseEvent e )
	{

		System.out.println( "tracker mouse up" );
		// if( tracker != null )
		// {
		// tracker.close();
		// }
		// tooltip.hide();
	}

}
