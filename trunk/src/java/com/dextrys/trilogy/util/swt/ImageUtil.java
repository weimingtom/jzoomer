package com.dextrys.trilogy.util.swt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.dextrys.trilogy.util.FileUtil;

/**
 * 
 * @author marquis
 * Modified Date:Jun 16, 2008
 */
public class ImageUtil
{

	public static int SCALE_DEFAULT = BufferedImage.SCALE_DEFAULT;
	public static int SCALE_FAST = BufferedImage.SCALE_FAST;
	public static int SCALE_SMOOTH = BufferedImage.SCALE_SMOOTH;
	public static int SCALE_REPLICATE = BufferedImage.SCALE_REPLICATE;
	public static int SCALE_AREA_AVERAGING = BufferedImage.SCALE_AREA_AVERAGING;
	
	private static ImageRegistry image_registry;

	// private static final String ICON_PATH = "icons/";
	// private static final String ICON_FILETYPE_PATH = "icons/filetype/";
	
	public static void main( String[] args )
	{
		// getImageRegistry( "res/icon", "gif" );
		// System.out.println( getImageRegistry().get( "file" ).getClass().getName() );
	}

	public static final BufferedImage scale( BufferedImage srcImage, int scaleNumber )
	{
		return scale( srcImage, scaleNumber, ImageUtil.SCALE_DEFAULT );
	}
	
	public static final BufferedImage scale( BufferedImage srcImage, int scaleNumber, int scale_type )
	{
		int srcW = srcImage.getWidth();
		int srcH = srcImage.getHeight();

		if( scaleNumber > 0 )
		{
			return scale( srcImage, srcW * scaleNumber, srcW * scaleNumber, scale_type );
		}
		else if( scaleNumber < 0 )
		{
			return scale( srcImage, (int)(srcW / scaleNumber),(int)(srcW / scaleNumber),scale_type );
		}
		else
		{
			return scale( srcImage, srcW, srcW, scale_type );
		}
	}
	
	public static final BufferedImage scale( BufferedImage srcImage, int newW, int newH, int scale_type )
	{
		java.awt.Image scaledImg = srcImage.getScaledInstance( newW, newH, scale_type );
		 // convert Image into BufferdImage 
       BufferedImage   tmp   =   new   BufferedImage( newW, newH, BufferedImage.TYPE_INT_RGB );   

       Graphics2D   graphics2D   =   tmp.createGraphics();   
       //graphics2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
       graphics2D.drawImage( scaledImg,   0,   0,   newW,   newH,   null);
       return tmp;
	}

	// public static final BufferedImage scale( BufferedImage srcImage, int newW, int newH )
	// {
	// int srcW = srcImage.getWidth();
	// int srcH = srcImage.getHeight();
	//
	// //BufferedImage tmp = new BufferedImage( srcW, srcH, srcImage.getType() );
	// BufferedImage tmp = new BufferedImage( srcW, srcH, srcImage.getType() );
	// Graphics g = tmp.getGraphics();
	//
	// g.drawImage( srcImage, ( srcW - newW ) / 2, ( srcH - newH ) / 2, newW, newH, null );
	// //
	// // for (int x = 0; x < newW; x++)
	// // {
	// // g.setClip(x, 0, 1, srcH);
	// // // ���������
	// // g.drawImage(srcImage, x - x * srcW/newW, 0, );
	// //
	// // }
	// //
	// // BufferedImage dst = new BufferedImage( newW, newH, BufferedImage.TYPE_INT_RGB );
	// // g = dst.getGraphics();
	// //
	// // for (int y = 0; y < newH; y++)
	// // {
	// // g.setClip(0, y, newW, 1);
	// // // ���������
	// // g.drawImage(tmp, 0, y - y * srcH/newH, Graphics.LEFT | Graphics.TOP );
	// //
	// // }
	//
	//		return tmp;
	//	}

	private static void executeImageRigister( File folder, String fileType )
	{

		if( image_registry == null )
		{
			image_registry = new ImageRegistry();
		}

		List< File > fileList = FileUtil.getFileList( folder, fileType );

		for( File file : fileList )
		{
			try
			{
				image_registry.put( FileUtil.getFileName( file ), ImageDescriptor.createFromURL( file.toURL() ) );
			}
			catch( MalformedURLException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @author Marquis
	 * @Create Date: 2008-4-2
	 * @param String
	 *            icon_path the path store icons
	 * @param String[]
	 *            type the types of icon
	 * @return imageRegistry which has loaded images
	 */
	public static ImageRegistry getImageRegistry( Display display, String icon_path, String... types )
	{
		if( image_registry == null )
		{
			image_registry = new ImageRegistry( display );

			File folder = new File( icon_path );

			for( String type : types )
			{
				executeImageRigister( folder, type );
			}
		}
		return image_registry;
	}
	
	public static ImageRegistry getImageRegistry()
	{
		if( image_registry == null )
		{
			return null;
		}
		
		return image_registry;
	}
	
	private static final int WM_PRINT = 0x0317;

	private static final int WM_PRINTCLIENT = 0x0318;

	private static final int PRF_CHECKVISIBLE = 0x00000001;

	private static final int PRF_NONCLIENT = 0x00000002;

	private static final int PRF_CLIENT = 0x00000004;

	private static final int PRF_ERASEBKGND = 0x00000008;

	private static final int PRF_CHILDREN = 0x00000010;

	private static final int PRF_OWNED = 0x00000020;
	
	/**
	 * Only for win32 OS!
	 * @author Marquis
	 * @Create Date: 2008-4-20
	 * @param control
	 * @return Image control's capture image
	 */
	public static Image makeShotImage( Control control ) 
	{
		int width = control.getBounds().width;
		int height = control.getBounds().height;
		if (width > 0 && height > 0) 
		{
			Image image = new Image(control.getDisplay(), width, height);
			GC gc = new GC(image);
			OS.SendMessage(control.handle, WM_PRINT, gc.handle, PRF_CLIENT | PRF_ERASEBKGND | PRF_NONCLIENT);
			gc.dispose();
			return image;
		}
		return null;
	}
	
	/**
	 * 
	 * @author Marquis
	 * @Create Date: 2008-4-20
	 * @param control
	 * @return Image control's capture image
	 */
	public static Image getControlImage( Control control )
	{
		Point point = control.getSize();
		GC gc = new GC( control );
		final Image image = new Image( control.getDisplay(), point.x, point.y );
		gc.copyArea( image, 0, 0 );
		gc.dispose();
		
		return image;
	}
	
	public static Image getScaledImage( Image image, int width, int height )
	{
		return new Image( Display.getDefault(), image.getImageData().scaledTo( width, height ) );
	}

	public static Image getScaledImage( Image image, int rate )
	{
		Point zoomSize = new Point( 
				( rate > 0 ) ? image.getBounds().width * rate : image.getBounds().width / -rate, 
				(rate > 0 ) ?  image.getBounds().height * rate : image.getBounds().height / -rate );

		ImageData zoomImageData = (ImageData)image.getImageData().clone();
		return new Image( Display.getDefault(), zoomImageData.scaledTo( zoomSize.x, zoomSize.y ) );

	}
}