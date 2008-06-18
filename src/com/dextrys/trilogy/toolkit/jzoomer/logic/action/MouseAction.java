/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.jface.window.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Display;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;

public class MouseAction extends BasicAction implements MouseMoveListener, MouseListener
{
	ApplicationWindow window;
	private int x = 0;
	private int y = 0;

	public MouseAction( ApplicationWindow w )
	{
		this.window = w;
	}

	public void run()
	{

	}

	public void mouseMove( MouseEvent e )
	{

		if ( e.stateMask == SWT.BUTTON1 )
		{
			int mouseX = Display.getDefault().getCursorLocation().x;
			int mouseY = Display.getDefault().getCursorLocation().y;
			window.getShell().setLocation( mouseX - x, mouseY - y);
		}
		
	}

	public void mouseDoubleClick( MouseEvent e ){}

	public void mouseDown( MouseEvent e )
	{
		if ( e.button == 1 )
		{
			int mouseX = Display.getDefault().getCursorLocation().x;
			int mouseY = Display.getDefault().getCursorLocation().y;
			x = mouseX - window.getShell().getLocation().x;
			y = mouseY - window.getShell().getLocation().y;
			//x = e.x;
			//y = e.y;
		}
	}

	public void mouseUp( MouseEvent e ){}

}
