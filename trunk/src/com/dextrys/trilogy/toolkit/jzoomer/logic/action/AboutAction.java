/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.AboutDialog;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;


public class AboutAction extends BasicAction
{
	private JZoomerWindow window;
	private AboutDialog aboutDlg;

	public AboutAction( JZoomerWindow w )
	{

		this.window = w;

		setText( getMessage( "action.about.text" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( AboutAction.class, "/icons/about.gif" ) );

	}

	public void run()
	{
		boolean isRunning = window.getTimer().isRunning();
		aboutDlg = new AboutDialog(window.getShell());
		
		if( isRunning == true )
		{
			window.getTimer().stop();
			aboutDlg.open();
			window.getTimer().start();
		}else
		{
			aboutDlg.open();
		}
	}
}
