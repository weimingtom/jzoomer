/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.toolkit.jzoomer.logic.action;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicAction;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.ui.ImageComposite;
import com.dextrys.trilogy.toolkit.jzoomer.ui.JZoomerWindow;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.dextrys.trilogy.util.swt.ImageConvertor;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class ZoomAction extends BasicAction implements MouseWheelListener, ActionListener
{
	private JZoomerWindow window;

	private int zoomType;
	//private int zoomRate;

	private Composite container;
	private Robot robot = JZoomerConstant.ROBOT;
	private BufferedImage bi;
	private ImageData imgData;
	private Image captureImg;
	private int imagScaleType;

	private ImageComposite canvasContainer;
	
	//private static Canvas canvas;
	private static Point zoomSize;

	private static Image currentImage;
	private static Image zoomImage;
	private static ImageData zoomImageData;

	public static final int TYPE_ZOOM_IN = 1;
	public static final int TYPE_ZOOM_OUT = 2;

	public ZoomAction( JZoomerWindow w, int type )
	{

		this.window = w;
		this.container = window.getContainer();
		this.zoomType = type;
		this.imagScaleType = JZoomerConstant.IMAGE_SCALE_TYPE;

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

		//zoomRate = window.getCurrentZoomRate();
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

		//zoomRate = window.getCurrentZoomRate();
		if( e.count > 0 )
		{
			doZoomOut();
		} else
		{
			doZoomIn();
		}
		window.refresh();
	}

	

	private void doZoomCurrentImage( int rate )
	{
		currentImage = window.getCurrentImage();
		zoomImage = ImageUtil.getScaledImage( currentImage, rate );
		zoomSize = new Point( zoomImage.getBounds().width, zoomImage.getBounds().height);
		canvasContainer.setSize( zoomSize.x, zoomSize.y );
		canvasContainer.getBackgroundImage().dispose();
		canvasContainer.setBackgroundImage( zoomImage );
		DisplayUtil.setWidgetAtCenter( canvasContainer );
	}
	
	private void doZoomIn()
	{
		int rate = window.getCurrentZoomRate();
		if( rate <= JZoomerConstant.ZOOM_RATE_MIN )
		{
			//the zoom rate have reached to minimum value
			return;
		} else
		{
			rate--;
			if( rate == 0 || rate == -1 )
			{// skip rate = 0 or rate = -1
				rate = -2;
			}
		}

		if( window.getTimer().isRunning() )
		{// process dynamic image
		} else
		{// TODO process currentImage
			doZoomCurrentImage( rate );
		}
		window.setCurrentZoomRate( rate );
	}
	
	private void doZoomOut()
	{
		int rate = window.getCurrentZoomRate();
		if( rate >= JZoomerConstant.ZOOM_RATE_MAX )
		{
			//the zoom rate have reached to maximum value
			return;

		} else
		{
			rate++;
			if( rate == 0 || rate == -1 )
			{// skip rate = 0 or rate = -1
				rate = 1;
			}
		}

		if( window.getTimer().isRunning() )
		{// process dynamic image
		} else
		{// TODO process currentImage
			doZoomCurrentImage( rate );
		}
		window.setCurrentZoomRate( rate );
	}

	public void actionPerformed( ActionEvent e )
	{

		if( this.container == null )
		{
			container = window.getContainer();
		}
		if( window.getShell().isDisposed() )
		{
			return;
		}

		if( Display.getDefault().isDisposed() )
		{
			return;
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
				if( window.getColorAction().isChecked() )
				{
					Color color = robot.getPixelColor( mouseX, mouseY );
					org.eclipse.swt.graphics.Color c = SWTResourceManager.getColor( color.getRed(), color.getGreen(), color.getBlue() );
					window.getColorInfoDlg().setColorInfo( c );
				}
				
				if( rate == 1 || rate == -1 )
				{
					// do not scale the capture area
				} else
				{
					bi = ImageUtil.scale( bi, currentWidth, currentHeight, imagScaleType );
				}
				imgData = ImageConvertor.getImageData( bi );

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

	/**
	 * @param canvasContainer the canvasContainer to set
	 */
	public void setCanvasContainer( ImageComposite canvasContainer )
	{
	
		this.canvasContainer = canvasContainer;
	}

//	/**
//	 * @param canvas the canvas to set
//	 */
//	public void setCanvas( Canvas canvas )
//	{
//	
//		this.canvas = canvas;
//	}
}
