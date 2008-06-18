package com.dextrys.trilogy.util.swt;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * Change javax.swing.ImageIcon object to org.eclipse.swt.graphics.Image object.
 * 
 * @author Freshwind
 */
public class ImageConvertor
{
	
	private static ImageData data;
	private static BufferedImage bufferedImage;
	public ImageConvertor()
	{
		super();
	}

	/**
	 * change ImageIcon to BufferedImage
	 * 
	 * @param icon
	 * @return
	 */
	public static BufferedImage getBufferedImage(ImageIcon icon)
	{
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		ImageObserver observer = icon.getImageObserver();
		bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics gc = bufferedImage.createGraphics();
		gc.drawImage(icon.getImage(), 0, 0, observer);
		return bufferedImage;
	}

	/**
	 * change BufferedImage to ImageData
	 * 
	 * @param bufferedImage
	 * @return
	 */
	public static ImageData getImageData( BufferedImage bufferedImage )
	{
		DirectColorModel colorModel = (DirectColorModel) bufferedImage
				.getColorModel();
		PaletteData palette = new PaletteData(colorModel.getRedMask(),
				colorModel.getGreenMask(), colorModel.getBlueMask());
		data = new ImageData(bufferedImage.getWidth(), bufferedImage
				.getHeight(), colorModel.getPixelSize(), palette);
		WritableRaster raster = bufferedImage.getRaster();
		int[] pixelArray = new int[3];
		for (int y = 0; y < data.height; y++)
		{
			for (int x = 0; x < data.width; x++)
			{
				raster.getPixel(x, y, pixelArray);
				int pixel = palette.getPixel(new RGB(pixelArray[0],
						pixelArray[1], pixelArray[2]));
				data.setPixel(x, y, pixel);
			}
		}
		return data;
	}

	public static void main(String[] args)
	{
		ImageIcon icon = new ImageIcon("C:\\temp\\freshwind.jpg");
		bufferedImage = getBufferedImage(icon);
		
		data = getImageData(bufferedImage);

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Image Convertor by Freshwind");
		shell.setLayout(new FillLayout());
		shell.setBackground(new Color(display, new RGB(255, 255, 255)));
		shell.setSize(350, 100);

		Label label = new Label(shell, SWT.NONE);
		// new Image with ImageData which is generated previously.
		Image image = new Image(display, data);
		label.setImage(image);
		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}