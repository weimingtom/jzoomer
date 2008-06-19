package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Test extends Shell
{
	private Text text;
	public static void main( String args[] )
	{

		try
		{
			Display display = Display.getDefault();
			Test shell = new Test( display, SWT.SHELL_TRIM );
			shell.open();
			shell.layout();
			while( !shell.isDisposed() )
			{
				if( !display.readAndDispatch() )
					display.sleep();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public Test( Display display, int style )
	{

		super( display, style );
		createContents();
	}

	protected void createContents()
	{
		
		setText( "SWT Application" );
		setSize( 500, 375 );

		text = new Text(this, SWT.BORDER);
		text.setBounds(113, 87, 80, 25);

		final Canvas canvas = new Canvas(this, SWT.NONE);
		canvas.setBounds(129, 167, 64, 64);

		final Group group = new Group(this, SWT.NONE);
		group.setText("adfasdfa");
		group.setBounds(274, 42, 100, 100);

		final Button button = new Button(group, SWT.NONE);
		button.setText("button");
		button.setBounds(31, 67, 44, 23);
		
		
		OS.SetWindowLong( this.handle, OS.GWL_EXSTYLE, OS.GetWindowLong( this.handle, OS.GWL_EXSTYLE ) ^ 0x80000 );

		TCHAR lpLibFileName = new TCHAR( 0, "User32.dll", true );
		int hInst = OS.LoadLibrary( lpLibFileName );
		if( hInst != 0 )
		{
			String name = "SetLayeredWindowAttributes\0";
			byte[] lpProcName = new byte[ name.length() ];
			for( int i = 0; i < lpProcName.length; i++ )
			{
				lpProcName[ i ] = ( byte ) name.charAt( i );
			}
			final int fun = OS.GetProcAddress( hInst, lpProcName );
			if( fun != 0 )
			{

				OS.CallWindowProc( fun, this.handle, 0, 200, 2 );
			}
			OS.FreeLibrary( hInst );
		}
	}

	protected void checkSubclass()
	{

	}
}
