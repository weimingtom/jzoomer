/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.jface.window.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ExitAction extends BasicAction
{
	private JZoomerWindow window;

	public ExitAction( JZoomerWindow w )
	{
		window = w;
		setText( getMessage( "action.exit.text" ) );
		setToolTipText( getMessage("action.exit.tooltip") );
		setImageDescriptor(ResourceManager.getImageDescriptor(ExitAction.class, "/icons/close.gif"));
	}

	public void run()
	{
		if( JZoomerConstant.EXIT_CONFIRM == true )
		{
			MessageBox messageBox = new MessageBox( window.getShell(),SWT.YES  | SWT.NO );
			messageBox.setText( getMessage( "action.exit.messageBox.text" ) );
			messageBox.setMessage( getMessage("action.exit.messageBox.info") );
			
			boolean isRunning = window.getTimer().isRunning();
			
			window.getTimer().stop();
			if( SWT.YES == messageBox.open() )
			{
				//dispose all SWT resources
				SWTResourceManager.dispose();
				window.getShell().dispose();
			}
			else
			{
				if( isRunning )
				{
					window.getTimer().start();
				}
			}
		}
		else
		{
			window.getTimer().stop();
			//dispose all SWT resources
			SWTResourceManager.dispose();
			window.getShell().dispose();
		}
			
	}

}
