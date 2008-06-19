package com.dextrys.trilogy.toolkit.jzoomer.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import com.dextrys.trilogy.toolkit.jzoomer.base.BasicWindow;
import com.dextrys.trilogy.toolkit.jzoomer.common.JZoomerConstant;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.AboutAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.AlwaysOnTopAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ColorAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ExitAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.MouseAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ShowAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ToggleMonitorAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ZoomAction;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.SWTResourceManager;
import javax.swing.Timer;

/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2008 talent_marquis<��˺��>
 * All rights reserved. Create Date:Jun 14, 2008
 */
public class JZoomerWindow extends BasicWindow implements Listener
{
	private Tray tray;

	private ExitAction exitAction;
	private ShowAction showAction;
	// private AlwaysOnTopAction alwaysOnTopAction;
	// private MouseAction mouseAction;
	private ZoomAction zoomInAction;
	private ZoomAction zoomOutAction;
	private ToggleMonitorAction toggleMonitorAction;
	private AboutAction aboutAction;
	private ColorAction colorAction;

	private int currentWidth = JZoomerConstant.WINDOW_INIT_WIDTH;
	private int currentHeight = JZoomerConstant.WINDOW_INIT_HEIGHT;
	private int currentZoomRate = JZoomerConstant.ZOOM_RATE_DEFAULT;
	private int capture_per_millisecond = JZoomerConstant.CAPTURE_PER_MILLISECOND;

	private Color backgroundColor = JZoomerConstant.BACKGROUND_COLOR_DEFAULT;

	private TrayItem trayItem;
	private Canvas canvas;
	private ColorInfoGroup colorInfoGroup;
	private Composite container;
	private Timer timer;
	private Image currentImage;
	// private GC gc;

	private ToolTip tooltip;

	/**
	 * Create the application window
	 */
	public JZoomerWindow()
	{

		super();
		setAlwaysOnTop( JZoomerConstant.ALWAYS_ON_TOP );
		createActions();
		addMenuBar();
		// addToolBar( SWT.FLAT | SWT.WRAP );
		// addStatusLine();
	}

	public void create()
	{

		super.create();

		// getShell().getMenuBar().addMenuListener( toggleMonitorAction );
		timer = new Timer( capture_per_millisecond, zoomInAction );
		timer.start();
		
		setShowAtScreenCenter();
	}
	/**
	 * Initialize contents of the tray
	 */
	private void initTray()
	{

		tray = Display.getDefault().getSystemTray();
		trayItem = new TrayItem( tray, SWT.NONE );
		trayItem.setImage( SWTResourceManager.getImage( JZoomerWindow.class, "/icons/magnifier.png" ) );
		trayItem.setToolTipText( getMessage( "window.tray.tooltip", getCurrentZoomRate() + "" ) );
		trayItem.setToolTip( tooltip );
		final Menu menu = createTrayMenuManager().createContextMenu( getShell() );
		trayItem.addMenuDetectListener( new MenuDetectListener()
		{
			public void menuDetected( MenuDetectEvent e )
			{

				menu.setVisible( true );
			}
		} );
	}
	/**
	 * Create contents of the application window
	 * 
	 * @param parent
	 */
	protected Control createContents( Composite parent )
	{

		container = new Composite( parent, SWT.NONE );
		container.setBackground( backgroundColor );
		container.setBackgroundMode( SWT.INHERIT_DEFAULT );
		// container.setLayout( new FillLayout() );
		// canvas = new Canvas( container, SWT.NONE );

		container.setMenu( createPopupMenuManager().createContextMenu( container ) );

		// implement mouse drag
		container.addMouseMoveListener( this );
		container.addMouseListener( this );
		// when mouse move in or move, stop monitor
		container.addMouseMoveListener( toggleMonitorAction );
		container.addMouseTrackListener( toggleMonitorAction );

		container.addMouseWheelListener( zoomInAction );
		container.setToolTipText( getMessage( "window.canvas.tooltip", getCurrentZoomRate() + "" ) );

		container.addControlListener( new ControlAdapter()
		{
			public void controlResized( ControlEvent e )
			{

				if( getTimer().isRunning() == false )
				{
					setWidgetAtComponiteCenter( canvas );
				}
			}

		} );

		// getShell().getMenuBar().addMenuListener( toggleMonitorAction );
		// tracker = new Tracker( getShell().getDisplay(), SWT.RESIZE );
		tracker = new Tracker( container, SWT.RESIZE );
		// tracker.setStippled( true );

		// container.addListener( SWT.MouseDown, this );
		// container.addListener( SWT.MouseUp, this );
		// container.addListener( SWT.MouseMove, this );

		colorInfoGroup = new ColorInfoGroup( container, SWT.NONE );
		colorInfoGroup.setVisible( false );
		// implement mouse drag
		colorInfoGroup.addMouseMoveListener( this );
		colorInfoGroup.addMouseListener( this );

		canvas = new Canvas( container, SWT.NONE );
		canvas.setVisible( false );
		canvas.setMenu( createPopupMenuManager().createContextMenu( container ) );

		// implement mouse drag
		canvas.addMouseMoveListener( this );
		canvas.addMouseListener( this );
		// when mouse move in or move, stop monitor
		canvas.addMouseMoveListener( toggleMonitorAction );
		canvas.addMouseTrackListener( toggleMonitorAction );
		// implement color pick-up
		canvas.addMouseListener( colorAction );
		// canvas.addKeyListener( colorAction );
		canvas.addMouseMoveListener( colorAction );

		tooltip = new ToolTip( getShell(), SWT.BALLOON | SWT.ICON_INFORMATION );
		tooltip.setText( getMessage( "window.tooltip.title" ) );
		tooltip.setAutoHide( true );
		initTray();
		
		colorAction.setCanvas( canvas );
		colorAction.setColorInfoGroup( colorInfoGroup );
		colorAction.setTooltip( tooltip );
		showAction.setTooltip( tooltip );

		return container;
	}

	private Tracker tracker;
	private Point point;
	public void handleEvent( Event event )
	{

		switch( event.type )
		{
			case SWT.MouseDown:
				point = new Point( event.x, event.y );
				// System.out.println( point );
				tracker.setRectangles( new Rectangle[]
				{
					new Rectangle( point.x, point.y, 0, 0 )
				} );
				// tracker.setStippled( true );

				tracker.open();
				break;
			case SWT.MouseMove:
				if( point == null )
				{
					return;
				}
				// System.out.println(point);
			case SWT.MouseUp:
				System.out.println( point );
				if( tracker != null )
				{
					tracker.close();
				}
				point = null;
				break;
		}
	}

	public void toggleMonitor( boolean flag )
	{

		if( flag == true )
		{
			System.out.println( "invoke toggle on " );
			// Convert capture Image from canvas backgroundImage to container backgroundImage
			canvas.setVisible( false );
			zoomInAction.setEnabled( true );
			zoomOutAction.setEnabled( true );
			// hidden colorInfo panel
			colorAction.setChecked( false );
			colorAction.run();
			timer.start();
			// TODO zoom currentImage in future
		} else
		{
			System.out.println( "invoke toggle off" );
			// Convert capture Image from cantainer backgroundImage to canvas backgroundImage
			if( container.getBackgroundImage() != null )
			{
				currentImage = new Image( Display.getDefault(), container.getBackgroundImage(), SWT.IMAGE_COPY );
			}
			canvas.setBackgroundImage( currentImage );
			canvas.setSize( container.getSize() );
			canvas.setLocation( 0, 0 );
			canvas.setVisible( true );
			// show colorInfo panel
			colorAction.setChecked( true );
			colorAction.run();
			// TODO zoom currentImage in future
			zoomInAction.setEnabled( false );
			zoomOutAction.setEnabled( false );

			// container.setImageDisplayMode( ImageComposite.CENTRE );
			// currentImage = ImageUtil.getControlImage( container );
			timer.stop();
			container.setBackgroundImage( null );
		}
	}

	/**
	 * Create the actions
	 */
	private void createActions()
	{

		showAction = new ShowAction( this );
		exitAction = new ExitAction( this );
		// alwaysOnTopAction = new AlwaysOnTopAction( this );
		// mouseAction = new MouseAction( this );
		zoomInAction = new ZoomAction( this, ZoomAction.TYPE_ZOOM_IN );
		zoomOutAction = new ZoomAction( this, ZoomAction.TYPE_ZOOM_OUT );
		toggleMonitorAction = new ToggleMonitorAction( this );
		aboutAction = new AboutAction( this );
		colorAction = new ColorAction( this );
	}

	/**
	 * Create the tray menu manager
	 * 
	 * @return the tray menu manager
	 */
	protected MenuManager createTrayMenuManager()
	{

		MenuManager menuManager = new MenuManager( "menu" );
		menuManager.add( aboutAction );
		menuManager.add( new Separator() );
		menuManager.add( colorAction );
		menuManager.add( new Separator() );
		menuManager.add( zoomOutAction );
		menuManager.add( zoomInAction );
		menuManager.add( new Separator() );
		menuManager.add( toggleMonitorAction );
		menuManager.add( showAction );
		menuManager.add( new Separator() );
		menuManager.add( exitAction );
		return menuManager;
	}
	/**
	 * create the popup menu manager
	 * 
	 * @return the popup menu manager
	 */
	protected MenuManager createPopupMenuManager()
	{

		MenuManager menuManager = new MenuManager( "menu" );
		menuManager.add( aboutAction );
		menuManager.add( new Separator() );
		menuManager.add( colorAction );
		menuManager.add( new Separator() );
		menuManager.add( zoomOutAction );
		menuManager.add( zoomInAction );
		menuManager.add( new Separator() );
		menuManager.add( toggleMonitorAction );
		menuManager.add( showAction );
		// menuManager.add( alwaysOnTopAction );
		menuManager.add( new Separator() );
		menuManager.add( exitAction );
		return menuManager;
	}

	/**
	 * Create the menu manager
	 * 
	 * @return the menu manager
	 */
	protected MenuManager createMenuManager()
	{

		MenuManager menuManager = new MenuManager( "menu" );

		MenuManager fileMenuManager = new MenuManager( getMessage( "window.menu.file" ) );
		fileMenuManager.add( showAction );
		fileMenuManager.add( new Separator() );
		fileMenuManager.add( exitAction );

		MenuManager operationMenuManager = new MenuManager( getMessage( "window.menu.operation" ) );
		operationMenuManager.add( colorAction );
		operationMenuManager.add( new Separator() );
		operationMenuManager.add( zoomOutAction );
		operationMenuManager.add( zoomInAction );
		operationMenuManager.add( new Separator() );
		operationMenuManager.add( toggleMonitorAction );

		MenuManager settingMenuManager = new MenuManager( getMessage( "window.menu.setting" ) );
		MenuManager languageMenuManager = new MenuManager( getMessage( "window.menu.language" ) );
		// TODO implement the function of switch language
		// languageMenuManager.add( exitAction );

		settingMenuManager.add( languageMenuManager );
		// TODO add settingAction
		// settingMenuManager.add( settingAction );

		MenuManager helpMenuManager = new MenuManager( getMessage( "window.menu.help" ) );
		helpMenuManager.add( aboutAction );

		menuManager.add( fileMenuManager );
		menuManager.add( operationMenuManager );
		menuManager.add( settingMenuManager );
		menuManager.add( helpMenuManager );

		return menuManager;
	}

	/**
	 * Create the status line manager
	 * 
	 * @return the status line manager
	 */
	protected StatusLineManager createStatusLineManager()
	{

		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage( null, "" );
		return statusLineManager;
	}

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main( String args[] )
	{

		try
		{
			JZoomerWindow window = new JZoomerWindow();
			window.setBlockOnOpen( true );
			window.open();
			Display.getCurrent().dispose();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell
	 * 
	 * @param newShell
	 */
	protected void configureShell( Shell newShell )
	{

		super.configureShell( newShell );
		newShell.setText( getMessage( "window.title" ) + " " + JZoomerConstant.VERSION );
		newShell.setImage( SWTResourceManager.getImage( JZoomerWindow.class, "/icons/magnifier.png" ) );
		newShell.setSize( currentWidth, currentHeight );
		newShell.setMinimumSize( JZoomerConstant.WINDOW_MIN_WIDTH, JZoomerConstant.WINDOW_MIN_HEIGHT );
	}

	/**
	 * When press the close button on the frame, hidden it into the tray
	 */
	public boolean close()
	{

		showAction.run();
		return false;
	}

	// TODO There are some problems in it
	public void setAlwaysOnTop( boolean flag )
	{

		if( flag == true )
		{
			setShellStyle( SWT.ON_TOP | SWT.RESIZE | SWT.CLOSE );
		} else
		{
			setShellStyle( SWT.RESIZE | SWT.CLOSE );
			// setShellStyle( SWT.RESIZE | SWT.CLOSE);
		}
	}

	/**
	 * Return the initial size of the window
	 */
	protected Point getInitialSize()
	{

		return new Point( currentWidth, currentHeight );
	}

	/**
	 * @return the currentZoomRate
	 */
	public int getCurrentZoomRate()
	{

		return currentZoomRate;
	}

	/**
	 * @param currentZoomRate
	 *            the currentZoomRate to set
	 */
	public void setCurrentZoomRate( int currentZoomRate )
	{

		this.currentZoomRate = currentZoomRate;
	}

	/**
	 * @return the canvas
	 */
	public Canvas getCanvas()
	{

		return canvas;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer()
	{

		return timer;
	}

	public void refresh()
	{

		trayItem.setToolTipText( getMessage( "window.tray.tooltip", getCurrentZoomRate() + "" ) );
		container.setToolTipText( getMessage( "window.canvas.tooltip", getCurrentZoomRate() + "" ) );
	}

	/**
	 * @return the container
	 */
	public Composite getContainer()
	{

		return container;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor()
	{

		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor( Color backgroundColor )
	{

		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the tracker
	 */
	public Tracker getTracker()
	{

		return tracker;
	}

	/**
	 * @return the toolTip
	 */
	public ToolTip getToolTip()
	{

		return tooltip;
	}

}
