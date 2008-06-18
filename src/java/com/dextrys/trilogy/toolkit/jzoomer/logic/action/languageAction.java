/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;

public class languageAction extends BasicAction
{
	private JZoomerWindow window;

	public languageAction( JZoomerWindow w )
	{
		window = w;
		setText( getMessage( "action.exit.text" ) );
		setToolTipText( getMessage("action.exit.tooltip") );
		setImageDescriptor(ResourceManager.getImageDescriptor(languageAction.class, "/icons/close.gif"));
	}

	public void run()
	{
		MessageBox messageBox = new MessageBox( window.getShell(),SWT.YES  | SWT.NO );
		messageBox.setText( getMessage( "action.exit.messageBox.text" ) );
		messageBox.setMessage( getMessage("action.exit.messageBox.info") );
		window.getTimer().stop();
		if( SWT.YES == messageBox.open() )
		{
			window.getShell().dispose();
		}
		else
		{
			window.getTimer().start();
		}
	}

}
