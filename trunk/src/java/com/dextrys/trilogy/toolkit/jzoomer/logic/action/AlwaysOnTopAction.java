/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;

public class AlwaysOnTopAction extends BasicAction
{
	JZoomerWindow window;

	public AlwaysOnTopAction( JZoomerWindow w )
	{
		super( AS_CHECK_BOX );
		window = w;
		setText( getMessage( "action.alwaysOnTop.text" ) );
		setToolTipText( getMessage("action.alwaysOnTop.tooltip") );
		setChecked( true );
	}

	public void run()
	{
		window.setAlwaysOnTop( this.isChecked() );
	}
}
