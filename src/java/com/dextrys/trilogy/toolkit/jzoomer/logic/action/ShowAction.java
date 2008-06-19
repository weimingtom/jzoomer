/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.jface.window.*;
import org.eclipse.swt.widgets.ToolTip;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;

public class ShowAction extends BasicAction
{
	private JZoomerWindow window;

	private ToolTip tooltip;
	
	public ShowAction( JZoomerWindow w )
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
			tooltip.setMessage( getMessage( "action.show.tooltip.hidden" ) );
			tooltip.setVisible( true );
		}
		else
		{
			setText( getMessage( "action.show.text.hidden" ) );
			window.getShell().setVisible( true );
		}
	}

	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip( ToolTip tooltip )
	{
	
		this.tooltip = tooltip;
	}
}
