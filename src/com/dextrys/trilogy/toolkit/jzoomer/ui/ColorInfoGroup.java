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
import org.eclipse.swt.widgets.Text;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicComposite;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author marquis Modified Date:Jun 17, 2008
 */
public class ColorInfoGroup extends BasicComposite
{
	private Label colorLb;
	private Label blueColorLb;
	private Label greenColorLb;
	private Label redColorLb;
	private Text htmlText, redNumberText, greenNumberText, blueNumberText;

	public ColorInfoGroup( Composite parent, int style )
	{

		super( parent, style );

		redNumberText = new Text( this, SWT.CENTER );
		redNumberText.setBounds( 5, 15, 32, 13 );
		redNumberText.setToolTipText( getMessage( "window.colorGroup.tooltip.red" ) );

		greenNumberText = new Text( this, SWT.CENTER );
		greenNumberText.setBounds( 5, 30, 32, 13 );
		greenNumberText.setToolTipText( getMessage( "window.colorGroup.tooltip.green" ) );

		blueNumberText = new Text( this, SWT.CENTER );
		blueNumberText.setBounds( 5, 45, 32, 13 );
		blueNumberText.setToolTipText( getMessage( "window.colorGroup.tooltip.blue" ) );

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
		colorLb.setBounds( 10, 64, 20, 15 );
		colorLb.setToolTipText( "" );
		colorLb.setBackground( SWTResourceManager.getColor( 128, 128, 128 ) );
		colorLb.setToolTipText( getMessage( "window.colorGroup.tooltip.color" ) );

		htmlText = new Text( this, SWT.NONE );
		htmlText.setBounds( 35, 64, 50, 15 );
		htmlText.setText( "#808080" );
		htmlText.setToolTipText( getMessage( "window.colorGroup.tooltip.html" ) );

		// TODO alpha 150
		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		setSize(95,100);

	}

	public void setColorInfo( Color color )
	{

		redNumberText.setText( "" + color.getRed() );
		greenNumberText.setText( "" + color.getGreen() );
		blueNumberText.setText( "" + color.getBlue() );

		redColorLb.setSize( color.getRed() / 6, 12 );
		greenColorLb.setSize( color.getGreen() / 6, 12 );
		blueColorLb.setSize( color.getBlue() / 6, 12 );

		htmlText.setText( getColorHtml( color ) );
		colorLb.setBackground( SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() ) );
	}

	public String getColorHtml()
	{

		return htmlText.getText();
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
