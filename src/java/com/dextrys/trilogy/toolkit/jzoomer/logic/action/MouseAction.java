/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.HotKeysManager;

public class MouseAction extends BasicAction implements MouseMoveListener, MouseListener
{
	JZoomerWindow window;
	
	private Cursor currentCursor;
	private Point location;
	
	private int moveShellHotKey = SWT.None;
	private int moveInParentHotKey = SWT.CTRL;
	
	public MouseAction( JZoomerWindow w )
	{
		this.window = w;
	}

	public void run()
	{

	}

	private void moveShell()
	{
		int mouseX = Display.getDefault().getCursorLocation().x;
		int mouseY = Display.getDefault().getCursorLocation().y;
		window.getShell().setLocation( mouseX - location.x, mouseY - location.y );
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
		int shellX = window.getShell().getLocation().x;
		int shellY = window.getShell().getLocation().y;
		return new Point( mouseX - shellX, mouseY - shellY );
	}

	// ====================Mouse Listener========================
	public void mouseMove( MouseEvent e )
	{

		// when mouse move with left button pressed, implement move-following-mouse moving
		Control c = ( Control ) e.getSource();
		if( e.stateMask == ( moveShellHotKey | SWT.BUTTON1 ) )
		{// move with mouse left button pressed
			//System.out.println( "Mouse move: Left button pressed" );
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
				//c.setCursor( JZoomerConstant.CURSOR_HAND );
				moveShell();
			}else if( e.getSource() instanceof Canvas )
			{
				if( window.getTrackerAction().isChecked() || window.getChalkAction().isChecked() )
				{
					//System.out.println( "MouseAction: Don't move!");					
				}
				else
				{
					moveShell();
				}
			}
			else
			{// other widgets
				//c.setCursor( JZoomerConstant.CURSOR_HAND );
				moveShell();
			}
		} else if( e.stateMask == ( moveInParentHotKey | SWT.BUTTON1 ) ) 
		{// move moveInParentHotKey + mouse left button pressed
			//c.setCursor( JZoomerConstant.CURSOR_HAND );
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
		//currentCursor = c.getCursor();
		if( e.button == 1 && e.stateMask == moveShellHotKey )
		{// press down mouse left button
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
				//c.setCursor( JZoomerConstant.CURSOR_HAND );
				location = getMouseRelativeLocation();
			} else
			{// other widgets
				//c.setCursor( JZoomerConstant.CURSOR_HAND );
				location = getMouseRelativeLocation();
			}
		} else if( e.button == 1 && e.stateMask == moveInParentHotKey )
		{// press down moveInParentHotKey + mouse left button
			if( e.getSource() instanceof Composite && c.getParent() instanceof Shell )
			{// top composite
			} else
			{// other widgets
				//c.setCursor( JZoomerConstant.CURSOR_HAND );
				location = new Point( e.x, e.y );
			}
		}
	}

	public void mouseUp( MouseEvent e )
	{
		//Restore cursor
		Control c = ( Control ) e.getSource();
		//c.setCursor( currentCursor );
		// c.setCursor( CURSOR_ARROW );
	}

	public void mouseDoubleClick( MouseEvent e )
	{

	}
	

	/**
	 * @param moveShellHotKey the moveShellHotKey to set
	 */
	public void setMoveShellHotKey( String keyName )
	{
		//TODO something wrong
		this.moveShellHotKey = HotKeysManager.getHotKeyCode( keyName );
	}

	/**
	 * @param moveInParentHotKey the moveInParentHotKey to set
	 */
	public void setMoveInParentHotKey( String keyName )
	{
		//TODO something wrong
		this.moveInParentHotKey = HotKeysManager.getHotKeyCode( keyName );
	}


}
