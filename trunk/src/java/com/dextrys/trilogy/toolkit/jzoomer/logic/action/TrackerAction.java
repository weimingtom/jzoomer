/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tracker;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class TrackerAction extends BasicAction implements Listener
{
	private JZoomerWindow window;
	private Tracker tracker;
	private Point point;

	public TrackerAction( JZoomerWindow w )
	{
		window = w;
		//setText( getMessage( "action.exit.text" ) );
		//setToolTipText( getMessage("action.exit.tooltip") );
		//setImageDescriptor(ResourceManager.getImageDescriptor(TrackerAction.class, "/icons/close.gif"));
	}

	public void run()
	{
		//TODO implement tracker function
			
	}
	

	public void handleEvent( Event event )
	{

		switch( event.type )
		{
			case SWT.MouseDown:
				point = new Point( event.x, event.y );
				// System.out.println( point );
				tracker.setRectangles( new Rectangle[]
				{
					new Rectangle( point.x, point.y, 0, 0 )
				} );
				// tracker.setStippled( true );

				tracker.open();
				break;
			case SWT.MouseMove:
				if( point == null )
				{
					return;
				}
				// System.out.println(point);
			case SWT.MouseUp:
				System.out.println( point );
				if( tracker != null )
				{
					tracker.close();
				}
				point = null;
				break;
		}
	}

}
