/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.jface.window.*;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;

public class ShowAction extends BasicAction
{
	ApplicationWindow window;

	public ShowAction( ApplicationWindow w )
	{
		window = w;
		setText( getMessage( "action.show.text.hidden" ) );
		//setImageDescriptor( ImageDescriptor.createFromURL( JZoomerConstant.newURL( "classpath:icons/close.gif" ) ) );
	}

	public void run()
	{
		if( window.getShell().isVisible() )
		{
			setText( getMessage( "action.show.text.show" ) );
			window.getShell().setVisible( false );
		}
		else
		{
			setText( getMessage( "action.show.text.hidden" ) );
			window.getShell().setVisible( true );
		}
	}
}
