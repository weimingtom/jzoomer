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
public class BasicWindow extends ApplicationWindow implements MouseMoveListener, MouseListener
{
	private static SourceBundlesReader reader;
	//private int locationX = 0;
	//private int locationY = 0;
	private Point location;			//Mouse Relative Location
	private Cursor currentCursor;

	public static final Cursor CURSOR_HAND = new Cursor( Display.getCurrent(), SWT.CURSOR_HAND );
	public static final Cursor CURSOR_CROSS = new Cursor( Display.getCurrent(), SWT.CURSOR_CROSS );
	public static final Cursor CURSOR_ARROW = new Cursor( Display.getCurrent(), SWT.CURSOR_ARROW );

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

	private void moveShell()
	{
		int mouseX = Display.getDefault().getCursorLocation().x;
		int mouseY = Display.getDefault().getCursorLocation().y;
		getShell().setLocation( mouseX - location.x, mouseY - location.y );
	}

	private void moveInParent( MouseEvent e )
	{
		Control c = ( Control ) e.getSource();
		int mouseX = e.x + c.getLocation().x;
		int mouseY = e.y + c.getLocation().y;
		c.setLocation( mouseX - location.x, mouseY - location.y );
	}

	private Point getMouseRelativeLocation()
	{

		int mouseX = Display.getDefault().getCursorLocation().x;
		int mouseY = Display.getDefault().getCursorLocation().y;
		int shellX = getShell().getLocation().x;
		int shellY = getShell().getLocation().y;
		return new Point( mouseX - shellX, mouseY - shellY );
	}

	// ====================Mouse Listener========================
	public void mouseMove( MouseEvent e )
	{

		// when mouse move with left button pressed, implement move-following-mouse moving
		Control c = ( Control ) e.getSource();
		if( e.stateMask == SWT.BUTTON1 )
		{// move with mouse left button pressed
			//System.out.println( "Mouse move: Left button pressed" );
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
				c.setCursor( CURSOR_HAND );
				moveShell();

			} else
			{// other widgets
				c.setCursor( CURSOR_HAND );
				moveShell();
			}
		} else if( e.stateMask == ( SWT.CTRL | SWT.BUTTON1 ) ) 
		{// move ctrl + mouse left button pressed
			c.setCursor( CURSOR_HAND );
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
			} else
			{// other widgets
				moveInParent( e );
			}
		}
	}

	public void mouseDown( MouseEvent e )
	{
		Control c = ( Control ) e.getSource();
		currentCursor = c.getCursor();
		if( e.button == 1 && e.stateMask == SWT.None )
		{// press down mouse left button
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
				c.setCursor( CURSOR_HAND );
				location = getMouseRelativeLocation();
			} else
			{// other widgets
				c.setCursor( CURSOR_HAND );
				location = getMouseRelativeLocation();
			}
		} else if( e.button == 1 && e.stateMask == SWT.CTRL )
		{// press down ctrl + mouse left button
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
			} else
			{// other widgets
				c.setCursor( CURSOR_HAND );
				location = new Point( e.x, e.y );
			}
		}
	}

	public void mouseUp( MouseEvent e )
	{
		//Restore cursor
		Control c = ( Control ) e.getSource();
		c.setCursor( currentCursor );
		// c.setCursor( CURSOR_ARROW );
	}

	public void mouseDoubleClick( MouseEvent e )
	{

	}

}
