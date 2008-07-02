package com.dextrys.trilogy.toolkit.jzoomer.base;
import org.eclipse.swt.widgets.Dialog;
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