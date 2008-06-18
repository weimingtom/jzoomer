package com.dextrys.trilogy.toolkit.jzoomer.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicComposite;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author marquis Modified Date:Jun 17, 2008
 */
public class ColorInfoGroup extends BasicComposite
{
	private Label htmlLb;
	private Label colorLb;
	private Label blueColorLb;
	private Label greenColorLb;
	private Label redColorLb;
	private Label blueNumberLb;
	private Label greenNumberLb;
	private Label redNumberLb;

	public ColorInfoGroup( Composite parent, int style )
	{

		super( parent, style );

		redNumberLb = new Label( this, SWT.CENTER );
		redNumberLb.setBounds( 5, 15, 32, 13 );
		redNumberLb.setToolTipText( getMessage( "window.colorGroup.tooltip.red" ) );

		greenNumberLb = new Label( this, SWT.CENTER );
		greenNumberLb.setBounds( 5, 30, 32, 13 );
		greenNumberLb.setToolTipText( getMessage( "window.colorGroup.tooltip.green" ) );

		blueNumberLb = new Label( this, SWT.CENTER );
		blueNumberLb.setBounds( 5, 45, 32, 13 );
		blueNumberLb.setToolTipText( getMessage( "window.colorGroup.tooltip.blue" ) );

		redColorLb = new Label( this, SWT.NONE );
		redColorLb.setBackground( Display.getCurrent().getSystemColor(SWT.COLOR_RED) );
		redColorLb.setBounds( 40, 15, 40, 10 );
		redColorLb.setText( "" );

		greenColorLb = new Label( this, SWT.NONE );
		greenColorLb.setBackground( Display.getCurrent().getSystemColor(SWT.COLOR_GREEN) );
		greenColorLb.setBounds( 40, 30, 40, 10 );
		greenColorLb.setText( "" );

		blueColorLb = new Label( this, SWT.NONE );
		blueColorLb.setBackground( Display.getCurrent().getSystemColor(SWT.COLOR_BLUE) );
		blueColorLb.setBounds( 40, 45, 40, 10 );
		blueColorLb.setText( "" );

		colorLb = new Label( this, SWT.CENTER | SWT.BORDER );
		colorLb.setBounds( 15, 64, 17, 16 );
		colorLb.setToolTipText( "" );
		colorLb.setBackground( SWTResourceManager.getColor( 128, 128, 128 ) );
		colorLb.setToolTipText( getMessage( "window.colorGroup.tooltip.color" ) );

		htmlLb = new Label( this, SWT.NONE );
		htmlLb.setBounds( 35, 64, 50, 15 );
		htmlLb.setText( "#808080" );
		htmlLb.setToolTipText( getMessage( "window.colorGroup.tooltip.html" ) );

		// TODO alpha 150
		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		Point size = new Point( 90, 110 );
		setSize( size );
	}

	public void setColorInfo( Color color )
	{

		redNumberLb.setText( "" + color.getRed() );
		greenNumberLb.setText( "" + color.getGreen() );
		blueNumberLb.setText( "" + color.getBlue() );

		redColorLb.setSize( color.getRed() / 6, 10 );
		greenColorLb.setSize( color.getGreen() / 6, 10 );
		blueColorLb.setSize( color.getBlue() / 6, 10 );

		htmlLb.setText( getColorHtml( color ) );
		colorLb.setBackground( SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() ) );
	}

	public String getColorHtml()
	{

		return htmlLb.getText();
	}

	private String getColorHtml( Color color )
	{

		String redHex = Integer.toHexString( color.getRed() ).toUpperCase();
		String greenHex = Integer.toHexString( color.getGreen() ).toUpperCase();
		String blueHex = Integer.toHexString( color.getBlue() ).toUpperCase();

		if( redHex.length() == 1 )
			redHex = 0 + redHex;
		if( greenHex.length() == 1 )
			greenHex = 0 + greenHex;
		if( blueHex.length() == 1 )
			blueHex = 0 + blueHex;

		return "#" + redHex + greenHex + blueHex;
	}

}
