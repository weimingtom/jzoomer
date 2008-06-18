/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.ImageConvertor;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.ResourceManager;

public class ZoomAction extends BasicAction implements MouseWheelListener, ActionListener
{
	private JZoomerWindow window;

	private int zoomType;
	private int zoomRate;

	// private Canvas canvas;
	private Composite container;
	private Robot robot;
	private BufferedImage bi;
	private ImageData imgData;
	private Image captureImg;
	private int imagScaleType;

	public static final int TYPE_ZOOM_IN = 1;
	public static final int TYPE_ZOOM_OUT = 2;

	public ZoomAction( JZoomerWindow w, int type )
	{

		this.window = w;
		// this.canvas = window.getCanvas();
		this.container = window.getContainer();
		this.zoomType = type;
		this.imagScaleType = JZoomerConstant.IMAGE_SCALE_TYPE;
		try
		{
			this.robot = new Robot();
		}
		catch( AWTException e )
		{
			e.printStackTrace();
		}
		switch( type )
		{
			case TYPE_ZOOM_IN:
				setText( getMessage( "action.zoom.text.zoomin" ) );
				setImageDescriptor( ResourceManager.getImageDescriptor( ZoomAction.class, "/icons/zoomin.png" ) );
				break;

			case TYPE_ZOOM_OUT:
				setText( getMessage( "action.zoom.text.zoomout" ) );
				setImageDescriptor( ResourceManager.getImageDescriptor( ZoomAction.class, "/icons/zoomout.png" ) );
				break;
		}
	}

	public void run()
	{

		zoomRate = window.getCurrentZoomRate();
		if( zoomType == TYPE_ZOOM_IN )
		{
			doZoomIn();

		} else if( zoomType == TYPE_ZOOM_OUT )
		{
			doZoomOut();
		}
		window.refresh();
	}

	public void mouseScrolled( MouseEvent e )
	{

		zoomRate = window.getCurrentZoomRate();
		if( e.count > 0 )
		{
			doZoomOut();
		} else
		{
			doZoomIn();
		}
		window.refresh();
	}

	private void doZoomIn()
	{

		if( zoomRate > JZoomerConstant.ZOOM_RATE_MIN )
		{
			zoomRate--;
			if( zoomRate == 0 || zoomRate == -1 )
			{// skip rate = 0 or rate = -1
				zoomRate = -2;
			}
			window.setCurrentZoomRate( zoomRate );
		}
	}

	private void doZoomOut()
	{

		if( zoomRate < JZoomerConstant.ZOOM_RATE_MAX )
		{
			zoomRate++;
			if( zoomRate == 0 || zoomRate == -1 )
			{// skip rate = 0 or rate = -1
				zoomRate = 1;
			}
			window.setCurrentZoomRate( zoomRate );
		}
	}

	public void actionPerformed( ActionEvent e )
	{

		if( this.container == null )
		{
			container = window.getContainer();
		}

		Display.getDefault().syncExec( new Runnable()
		{
			public void run()
			{

				int mouseX = MouseInfo.getPointerInfo().getLocation().x;
				int mouseY = MouseInfo.getPointerInfo().getLocation().y;
				int currentWidth = window.getContainer().getSize().x;
				int currentHeight = window.getContainer().getSize().y;
				int rate = window.getCurrentZoomRate();
				int captureWidth = ( rate > 0 ) ? ( int ) ( currentWidth / rate ) : ( int ) ( -currentWidth * rate );
				int captureHeight = ( rate > 0 ) ? ( int ) ( currentHeight / rate ) : ( int ) ( -currentHeight * rate );
				Rectangle sampleRectangle = new Rectangle( mouseX - ( captureWidth / 2 ),
						mouseY - ( captureHeight / 2 ), captureWidth, captureHeight );

				
				bi = robot.createScreenCapture( sampleRectangle );
				if( rate == 1 || rate == -1 )
				{
					// do not scale the capture area
				} else
				{
					bi = ImageUtil.scale( bi, currentWidth, currentHeight, imagScaleType );
				}
				imgData = ImageConvertor.getImageData( bi );

				// mouseLocationLb.setText( "(" + mouseX + "," + mouseY + ")" );

				// 动态颜色信息显示
				// redNumberLb.setText( "" + color.getRed() );
				// greenNumberLb.setText( "" + color.getGreen() );
				// blueNumberLb.setText( "" + color.getBlue() );

				// 动态颜色长度显示
				// redColorLb.setSize( color.getRed()/6, 10 );
				// greenColorLb.setSize( color.getGreen()/6, 10 );
				// blueColorLb.setSize( color.getBlue()/6, 10 );

				// 动态HTML代码显示
				// htmlLb.setText( getColorHtml( color ) );
				// colorLb.setBackground( SWTResourceManager.getColor(
				// color.getRed(),color.getGreen(),color.getBlue() ) );

				// 捕捉鼠标附近100*100象素大小范围的图像
				if( captureImg != null )
				{
					captureImg.dispose();
				}
				captureImg = new Image( null, imgData );
				if( container != null )
				{
					// GC gc = new GC(container);
					// gc.drawImage( captureImg, 0, 0 );
					// gc.dispose();
					container.setBackgroundImage( captureImg );
				}
			}
		} );
	}
}
