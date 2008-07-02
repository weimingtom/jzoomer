package com.dextrys.trilogy.toolkit.jzoomer.base;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.util.SourceBundlesReader;
/**
 * 
 * @author marquis
 * Modified Date:Jun 16, 2008
 */
public class BasicDialog extends Dialog
{
	private static SourceBundlesReader reader;
	protected Shell dialog;
	
	public static final int AT_WINDOW_LEFT = 0;
	public static final int AT_WINDOW_TOP = 1;
	public static final int AT_WINDOW_RIGHT = 2;
	public static final int AT_WINDOW_BOTTOM = 3;

	public BasicDialog(Shell parent)
	{
		super(parent);
		if( JZoomerConstant.LOCALE != null )
		{
			reader = new SourceBundlesReader( JZoomerConstant.DIALOG_MESSAGE_BUNDLE_NAME, JZoomerConstant.LOCALE );
		}
		else
		{
			reader = new SourceBundlesReader( JZoomerConstant.DIALOG_MESSAGE_BUNDLE_NAME );
		}
	}
	
	public void showDialog( int style )
	{
		Point dialogSize = dialog.getSize();
		Point parentSize = getParent().getSize();
		Point parentLocation = getParent().getLocation();
		switch( style )
		{
			case AT_WINDOW_TOP:
				dialog.setLocation( parentLocation.x, parentLocation.y - dialogSize.y );
				break;
			case AT_WINDOW_BOTTOM:
				dialog.setLocation( parentLocation.x - dialogSize.x, parentLocation.y );
				break;
			case AT_WINDOW_LEFT:
				dialog.setLocation( parentLocation.x - dialogSize.x, parentLocation.y );
				break;
			case AT_WINDOW_RIGHT:
				dialog.setLocation( parentLocation.x, parentLocation.y + parentSize.y );
				break;
		}
	}
	
	public void open()
	{

		Shell parent = getParent();

		dialog.open();
		Display display = parent.getDisplay();
		while( !dialog.isDisposed() )
		{
			if( !display.readAndDispatch() )
				display.sleep();
		}
	}
	
//	protected void showDialogAtWindowLeft( Shell parent, Shell dialog )
//	{
//		Point dialogSize = dialog.getSize();
//		Point parentLocation = parent.getLocation();
//		
//		dialog.setLocation( parentLocation.x - dialogSize.x, parentLocation.y );
//	}

	//====================Message Reader========================
	public String getMessage( String key )
	{

		return reader.getProperty( key );
	}

	public String getMessage( String key, String... args )
	{

		return reader.getProperty( key, args );
	}

	public int getIntegerMessage( String key )
	{

		return reader.getIntegerProperty( key );
	}
	
	public boolean getBooleanMessage( String key )
	{
		return reader.getBooleanProperty( key );
	}
	
	protected double getDoubleMessage( String key )
	{
		return reader.getDoubleProperty( key );
	}
	
}