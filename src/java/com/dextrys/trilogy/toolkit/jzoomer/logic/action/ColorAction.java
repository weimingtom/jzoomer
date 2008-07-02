/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Robot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolTip;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicDialog;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ColorInfoDialog;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ColorInfoGroup;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ImageComposite;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ColorAction extends BasicAction implements MouseListener, MouseMoveListener// ,
// KeyListener
{
	private JZoomerWindow window;
	private ColorInfoDialog colorInfoDlg;
	//private Canvas canvas;
	private ImageComposite canvasContainer;
	private Robot robot;
	private int r, g, b;
	private boolean isHotKeyPressed, isStopped;;
	//private ColorInfoGroup colorInfoGroup;
	private ToolTip tooltip;

	private static Clipboard clipboard = new Clipboard( Display.getCurrent() );
	private static TextTransfer text_transfer = TextTransfer.getInstance();

	public ColorAction( JZoomerWindow w )
	{

		super( AS_RADIO_BUTTON );
		window = w;
		try
		{
			robot = new Robot();
		}
		catch( AWTException e )
		{
			e.printStackTrace();
		}
		setChecked( false );
		setText( getMessage( "action.color.text" ) );
		setToolTipText( getMessage( "action.color.tooltip" ) );
		setImageDescriptor( ResourceManager.getImageDescriptor( ColorAction.class, "/icons/colorpicker.png" ) );
	}

	private void initColorInfoDialog()
	{
		colorInfoDlg = new ColorInfoDialog(window.getShell());
	}
	
	public void run()
	{
		if( colorInfoDlg == null )
		{
			initColorInfoDialog();
		}

		if( isChecked() )
		{
			canvasContainer.setCursor( JZoomerConstant.CURSOR_COLORPICKER );
			canvasContainer.setToolTipText( getMessage( "action.color.canvas.tooltip" ) );
			toggleColorPick( true );
			window.getShell().setActive();
		} else
		{
			canvasContainer.setCursor( JZoomerWindow.CURSOR_CROSS );
			canvasContainer.setToolTipText( "" );
			toggleColorPick( false );
		}
	}

	private void pickupColor()
	{

		// System.out.println( "pick up color" );
		Color color = getCurrentMouseLocationColor();
		colorInfoDlg.setColorInfo( SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() ) );
		//colorInfoGroup.setColorInfo( SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() ) );
		String cHtml = getColorHtml();

		// save color info into clipboard
		clipboard.setContents( new Object[]
		{
			cHtml
		}, new Transfer[]
		{
			text_transfer
		} );

	}

	public String getColorHtml()
	{
		return colorInfoDlg.getColorHtml();
		//return colorInfoGroup.getColorHtml();
	}

	private Color getCurrentMouseLocationColor()
	{

		int mouseX = MouseInfo.getPointerInfo().getLocation().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y;

		return robot.getPixelColor( mouseX, mouseY );
	}

	private void showColorInfo()
	{

		// System.out.println( "show color info status:" + isStopped );
		if( isStopped )
			return;
		Color color = getCurrentMouseLocationColor();
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();

		//colorInfoGroup.setColorInfo( SWTResourceManager.getColor( r, g, b ) );
		
		colorInfoDlg.setColorInfo( SWTResourceManager.getColor( r, g, b ) );
	}

	public void mouseDoubleClick( MouseEvent e )
	{

		if( isChecked() )
		{
			// pickup color immediately
			pickupColor();
			// stop pickup color dynamically
			isStopped = false;
			if( JZoomerConstant.TRAY_MESSAGE_SHOW )
			{// show color info in tray tooltip
				tooltip.setMessage( getMessage( "action.color.tooltip.clip", getColorHtml() ) );
				tooltip.setVisible( true );
			}
		}
	}
	public void mouseUp( MouseEvent e )
	{

		// toggle stop/start show color info
		// isStopped = !isStopped;
	}

	public void mouseDown( MouseEvent e )
	{

		if( isChecked() )
		{
			isStopped = !isStopped;
			// if( e.stateMask == ( SWT.BUTTON1 | 'c')||e.stateMask == ( SWT.BUTTON1 | 'C' ) )
			// {
			// pickupColor( e );
			// }
		}

	}

	public void mouseMove( MouseEvent e )
	{

		if( isChecked() )
		{
			// System.out.println( e.stateMask );
			if( isHotKeyPressed )
			{
				showColorInfo();
			}
		}

	}
	public void keyPressed( KeyEvent e )
	{

		if( isChecked() )
		{
			// if( e.keyCode == 'c' || e.keyCode == 'C' )
			// {
			// System.out.println( "c pressed" );
			// canvas.setCursor( JZoomerWindow.CURSOR_CROSS );
			// toggleColorPick( true );
			// }
		}
	}

	private void toggleColorPick( boolean flag )
	{

		isHotKeyPressed = flag;
		isStopped = !flag;
		//colorInfoGroup.setVisible( flag );
		colorInfoDlg.showDialog( BasicDialog.AT_WINDOW_LEFT );
		colorInfoDlg.setVisible( flag );
	}

	public void keyReleased( KeyEvent e )
	{

		if( isChecked() )
		{
			// if( e.keyCode == 'c' || e.keyCode == 'C' )
			// {
			// System.out.println( "c released" );
			// canvas.setCursor( JZoomerWindow.CURSOR_ARROW );
			// toggleColorPick( false );
			// }
		}
	}

	/**
	 * @param canvas
	 *            the canvas to set
	 */
//	public void setCanvas( Canvas canvas )
//	{
//
//		this.canvas = canvas;
//	}

	// public void setColorInfoGroup( ColorInfoGroup colorInfoGroup )
	// {
	//
	// this.colorInfoGroup = colorInfoGroup;
	//	}

	/**
	 * @param tooltip
	 *            the tooltip to set
	 */
	public void setTooltip( ToolTip tooltip )
	{

		this.tooltip = tooltip;
	}

	/**
	 * @param canvasContainer the canvasContainer to set
	 */
	public void setCanvasContainer( ImageComposite canvasContainer )
	{
	
		this.canvasContainer = canvasContainer;
	}

	/**
	 * @param colorInfoDlg the colorInfoDlg to set
	 */
	public void setColorInfoDlg( ColorInfoDialog colorInfoDlg )
	{
	
		this.colorInfoDlg = colorInfoDlg;
	}
}
