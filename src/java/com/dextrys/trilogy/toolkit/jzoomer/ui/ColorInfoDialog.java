package com.dextrys.trilogy.toolkit.jzoomer.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicDialog;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.SourceBundlesReader;

/**
 * 
 * @author marquis Modified Date:Jun 16, 2008
 */
public class ColorInfoDialog extends BasicDialog
{
	private ColorInfoGroup colorInfoGrp;

	public ColorInfoDialog( Shell parent )
	{

		super( parent );
		dialog = new Shell( parent, SWT.TITLE );
		dialog.setLayout( new FillLayout() );
		colorInfoGrp = new ColorInfoGroup( dialog, SWT.NONE );

		dialog.setSize( 100, 120 );
		showDialog( AT_WINDOW_LEFT );
	}



	public void setVisible( boolean visible )
	{

		dialog.setVisible( visible );
	}

	public void setColorInfo( Color color )
	{

		colorInfoGrp.setColorInfo( color );
	}

	public String getColorHtml()
	{

		return colorInfoGrp.getColorHtml();
	}

}