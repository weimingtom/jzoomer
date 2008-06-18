package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;

/**
 * 鼠标移动事件, 用于实现拖拽窗口动作.
 * 
 * @author zheng
 */
public class MouseMoveListener implements Listener
{

	private Shell shell;
	private Point point = null;

	public void handleEvent( Event event )
	{

		Tracker tracker = null;
		switch( event.type )
		{
			case SWT.MouseDown:
				point = new Point( event.x, event.y );
				break;
			case SWT.MouseMove:
				if( point == null )
				{
					return;
				}
				tracker = new Tracker( shell.getDisplay(), SWT.NONE );
				tracker.setRectangles( new Rectangle[]
				{
					shell.getBounds()
				} );
				tracker.setStippled( true );
				tracker.open();
				// 一定要让它通过, 不然会出现可怕的错误.
			case SWT.MouseUp:
				if( tracker != null )
				{
					shell.setBounds( tracker.getRectangles()[ 0 ] );
					tracker.close();
				}
				point = null;
				break;
			case SWT.MouseDoubleClick:
				if( shell.getMaximized() )
				{
					shell.setMaximized( false );
					// support.updateSkin( SHELL_MAX );
				} else
				{
					shell.setMaximized( true );
					// support.updateSkin( SHELL_REM );
				}
				break;
		}
	}
}