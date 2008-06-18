package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;

/**
 * 
 * @author marquis Modified Date:Jun 17, 2008
 */
public class ToggleMonitorAction extends BasicAction implements MouseTrackListener, MouseMoveListener, MenuListener
{
	private JZoomerWindow window;
	private boolean isStoped;

	public ToggleMonitorAction( JZoomerWindow w )
	{

		window = w;
		isStoped = false;
		setText( getMessage( "action.togglemonitor.text.stop" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( AboutAction.class, "/icons/pause.gif" ) );
	}

	public void run()
	{

		if( window.getTimer().isRunning() )
		{
			setText( getMessage( "action.togglemonitor.text.start" ) );
			setImageDescriptor( ResourceManager.getImageDescriptor( AboutAction.class, "/icons/start.gif" ) );
			window.toggleMonitor( false );
			isStoped = true;
		} else
		{
			setText( getMessage( "action.togglemonitor.text.stop" ) );
			setImageDescriptor( ResourceManager.getImageDescriptor( AboutAction.class, "/icons/pause.gif" ) );
			window.toggleMonitor( true );
			isStoped = false;
		}
	}

	public void mouseEnter( MouseEvent e )
	{

		if( window.getTimer().isRunning() )
		{
			System.out.println( "mouse enter toggle off" );
			window.toggleMonitor( false );
			setEnabled( false );
		}
	}

	public void mouseExit( MouseEvent e )
	{

		Point currentMousePoint = null;
		Point compositeSize = null;
		Control c = ( Control ) e.getSource();
		
		if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
		{// composite size
			compositeSize = c.getSize();
			currentMousePoint = new Point( e.x, e.y );
		}else
		{//Canvas
			compositeSize = c.getParent().getSize();
			currentMousePoint = new Point( c.getLocation().x + e.x, c.getLocation().y + e.y );
		}
		
		if( currentMousePoint.x > 0 && currentMousePoint.y > 0 && currentMousePoint.x < compositeSize.x && currentMousePoint.y < compositeSize.y )
		{// mouse move in the composite
			System.out.println( "mouse are move in the composite" );
			return;
		}
		
		if( isStoped == true )
		{// do nothing
		} else
		{// start minitor
			window.toggleMonitor( true );
		}
		setEnabled( true );
	}

	public void mouseMove( MouseEvent e )
	{

		if( window.getTimer().isRunning() )
		{
			System.out.println( "force toggle off" );
			window.toggleMonitor( false );
		}
	}

	public void mouseHover( MouseEvent e )
	{

	}

	public void menuHidden( MenuEvent e )
	{

		System.out.println( "hidden" );
		// window.toggleMinitor( true );
	}

	public void menuShown( MenuEvent e )
	{

		System.out.println( "show" );
		// window.toggleMinitor( false );
	}

}
