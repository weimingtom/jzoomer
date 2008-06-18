/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */

package com.dextrys.trilogy.toolkit.jzoomer.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicDialog;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.FileUtil;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.swtdesigner.SWTResourceManager;

public class AboutDialog extends BasicDialog
{
	private Text aboutText;
	private Text logText;
	private String title;
	private String version;
	private String author;
	private String email1,email2;
	private String msn;
	private String logStr;
	
	public AboutDialog( Shell parent )
	{
		super( parent );
		title = getMessage( "dialog.about.title" );
		author = getMessage( "dialog.about.author" );
		email1 = getMessage( "dialog.about.email1" );
		email2 = getMessage( "dialog.about.email2" );
		msn = getMessage( "dialog.about.msn" );
		
		version = JZoomerConstant.VERSION;
		try
		{
			logStr = FileUtil.loadSimpleTextFile( new File( "log/log.txt" ) );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}

	public void open()
	{

		Shell parent = getParent();
		final Shell dialog = new Shell( parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		dialog.setImage(SWTResourceManager.getImage(AboutDialog.class, "/icons/magnifier.png"));
		dialog.setSize( 321, 279 );
		dialog.setText( title + " " + version );
		
		Button okBtn = new Button( dialog, SWT.NONE );
		okBtn.setImage(SWTResourceManager.getImage(AboutDialog.class, "/icons/magnifier.png"));
		okBtn.setToolTipText( getMessage( "dialog.about.button.ok" ) );
		okBtn.setBounds( 230, 10, 74, 74 );
		okBtn.addSelectionListener( new SelectionAdapter()
		{
			public void widgetSelected( SelectionEvent e )
			{
				dialog.dispose();
			}
		} );

		aboutText = new Text(dialog, SWT.MULTI | SWT.READ_ONLY);
		aboutText.setBounds(10, 23, 214, 61);
		aboutText.setText( author + "\n" + email1 + "\n" + email2 + "\n" + msn );
		
		final Group logGroup = new Group( dialog, SWT.NONE );
		logGroup.setText( getMessage( "dialog.about.group.title" ) );
		logGroup.setBounds( 10, 90, 294, 146 );

		logText = new Text(logGroup, SWT.V_SCROLL | SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		logText.setEditable(false);
		logText.setDoubleClickEnabled(false);
		logText.setBounds(10, 15, 274, 121);
		logText.setText( logStr );
		
		DisplayUtil.showOnAtScreenMiddle( dialog );

		
		dialog.open();
		Display display = parent.getDisplay();
		while( !dialog.isDisposed() )
		{
			if( !display.readAndDispatch() )
				display.sleep();
		}
	}
}