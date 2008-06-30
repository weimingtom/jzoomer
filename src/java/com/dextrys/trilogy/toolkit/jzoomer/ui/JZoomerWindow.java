package com.dextrys.trilogy.toolkit.jzoomer.ui;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
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
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ChalkAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ColorAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ExitAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.MouseAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ShowAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ToggleMonitorAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.TrackerAction;
import com.dextrys.trilogy.toolkit.jzoomer.logic.action.ZoomAction;
import com.dextrys.trilogy.util.swt.DisplayUtil;
import com.dextrys.trilogy.util.swt.ImageConvertor;
import com.dextrys.trilogy.util.swt.ImageUtil;
import com.swtdesigner.SWTResourceManager;
import javax.swing.Timer;

/*
 * @author talent_marquis<��˺��> Email: talent_marquis@163.com Copyright (C) 2008 talent_marquis<��˺��>
 * All rights reserved. Create Date:Jun 14, 2008
 */
public class JZoomerWindow extends BasicWindow
{

	private ExitAction exitAction;
	private ShowAction showAction;
	// private AlwaysOnTopAction alwaysOnTopAction;
	private MouseAction mouseAction;
	private ZoomAction zoomInAction;
	private ZoomAction zoomOutAction;
	private ToggleMonitorAction toggleMonitorAction;
	private AboutAction aboutAction;
	private ColorAction colorAction;
	private TrackerAction trackerAction;
	private ChalkAction chalkAction;

	private int currentWidth = JZoomerConstant.WINDOW_INIT_WIDTH;
	private int currentHeight = JZoomerConstant.WINDOW_INIT_HEIGHT;
	private int currentZoomRate = JZoomerConstant.ZOOM_RATE_DEFAULT;
	private int capture_per_millisecond = JZoomerConstant.CAPTURE_PER_MILLISECOND;

	private Color backgroundColor = JZoomerConstant.BACKGROUND_COLOR_DEFAULT;

	private Tracker tracker;

	private Tray tray;
	private TrayItem trayItem;
	private Canvas canvas;
	private ColorInfoGroup colorInfoGroup;
	private Composite container;
	private Timer timer;
	private Image currentImage;

	private Robot robot = JZoomerConstant.ROBOT;
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
			} 
		);
		
		trayItem.addSelectionListener( new SelectionAdapter() 
			{
				public void widgetSelected( SelectionEvent e )
				{
					//System.out.println( "trayItem selected");
					showAction.run();
				}
				
			}
		);
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
		container.addMouseMoveListener( mouseAction );
		container.addMouseListener( mouseAction );
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

		colorInfoGroup = new ColorInfoGroup( container, SWT.NONE );
		//setTransparent( colorInfoGroup, 125 );
		colorInfoGroup.setVisible( false );
		// implement mouse drag
		colorInfoGroup.addMouseMoveListener( mouseAction );
		colorInfoGroup.addMouseListener( mouseAction );

		canvas = new Canvas( container, SWT.NONE );
		canvas.setVisible( false );
		canvas.setMenu( createPopupMenuManager().createContextMenu( container ) );
		canvas.setCursor( CURSOR_CROSS );
		canvas.setFocus();

		// implement mouse drag
		canvas.addMouseMoveListener( mouseAction );
		canvas.addMouseListener( mouseAction );
		// implement chalk
		canvas.addMouseListener( chalkAction );
		canvas.addMouseMoveListener( chalkAction );
		canvas.addKeyListener( chalkAction );
		// implement measure
		canvas.addMouseListener( trackerAction );
		canvas.addMouseMoveListener( trackerAction );
		// implement color pick-up
		canvas.addMouseListener( colorAction );
		// canvas.addKeyListener( colorAction );
		canvas.addMouseMoveListener( colorAction );
		// when mouse move in or move, stop monitor
		canvas.addMouseMoveListener( toggleMonitorAction );
		canvas.addMouseTrackListener( toggleMonitorAction );
		

		tooltip = new ToolTip( getShell(), SWT.BALLOON | SWT.ICON_INFORMATION );
		tooltip.setText( getMessage( "window.tooltip.title" ) );
		tooltip.setAutoHide( true );

		// tracker = new Tracker( getShell().getDisplay(), SWT.RESIZE );
		tracker = new Tracker( canvas, SWT.RESIZE );
		tracker.addControlListener( trackerAction );
		// tracker.setStippled( true );
		
		initTray();

		chalkAction.setCanvas( canvas );
		chalkAction.run();
		
		zoomInAction.setCanvas( canvas );
		zoomOutAction.setCanvas( canvas );
		
		colorAction.setCanvas( canvas );
		colorAction.setColorInfoGroup( colorInfoGroup );
		colorAction.setTooltip( tooltip );
		
		showAction.setTooltip( tooltip );
				
		trackerAction.setCanvas( canvas );
		trackerAction.setTracker( tracker );

		return container;
	}

	public void toggleMonitor( boolean flag )
	{

		if( flag == true )
		{
			System.out.println( "invoke toggle on " );
			// Convert capture Image from canvas backgroundImage to container backgroundImage
			// container.setBackgroundImage( canvas.getBackgroundImage() );
			canvas.setVisible( false );
			//colorAction.setChecked( false );
			//colorAction.run();
			chalkAction.setEnabled( false );
			timer.start();
			if( canvas.getBackgroundImage() != null )
			{
				canvas.setBackgroundImage( null );
			}
			if( currentImage != null )
			{
				currentImage = null;
			}

		} else
		{
			System.out.println( "invoke toggle off" );
			container.setBackground( backgroundColor );
			// capture current image and put it into canvas
			Point mouseLocation = Display.getDefault().getCursorLocation();
			int currentWidth = container.getSize().x;
			int currentHeight = container.getSize().y;

			Rectangle sampleRectangle = new Rectangle( mouseLocation.x - ( currentWidth / 2 ), mouseLocation.y
					- ( currentHeight / 2 ), currentWidth, currentHeight );

			// this.getShell().setVisible( false );
			BufferedImage bi = robot.createScreenCapture( sampleRectangle );
			// this.getShell().setVisible( true );

			timer.stop();
			if( container.getBackgroundImage() != null )
			{
				container.getBackgroundImage().dispose();
				container.setBackgroundImage( null );
				container.update();
			}

			currentImage = new Image( Display.getDefault(), ImageConvertor.getImageData( bi ) );

			Image zoomImage = ImageUtil.getScaledImage( currentImage, currentZoomRate );
			canvas.setSize( zoomImage.getBounds().width, zoomImage.getBounds().height );
			canvas.setBackgroundImage( zoomImage );
			DisplayUtil.setWidgetAtCenter( canvas );
			canvas.setVisible( true );
			
			chalkAction.setEnabled( true );
			// show colorInfo panel
			//colorAction.setChecked( true );
			//colorAction.run();
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
		mouseAction = new MouseAction( this );
		zoomInAction = new ZoomAction( this, ZoomAction.TYPE_ZOOM_IN );
		zoomOutAction = new ZoomAction( this, ZoomAction.TYPE_ZOOM_OUT );
		toggleMonitorAction = new ToggleMonitorAction( this );
		aboutAction = new AboutAction( this );
		colorAction = new ColorAction( this );
		trackerAction = new TrackerAction( this );
		chalkAction = new ChalkAction( this );
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
		menuManager.add( chalkAction );
		menuManager.add( trackerAction );
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
		menuManager.add( chalkAction );
		menuManager.add( trackerAction );
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
		operationMenuManager.add( chalkAction );
		operationMenuManager.add( trackerAction );
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
			window.getShell().dispose();
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

	/**
	 * @return the currentImage
	 */
	public Image getCurrentImage()
	{

		return currentImage;
	}

	/**
	 * @return the tray
	 */
	public Tray getTray()
	{
	
		return tray;
	}

	/**
	 * @return the trackerAction
	 */
	public TrackerAction getTrackerAction()
	{
	
		return trackerAction;
	}

	/**
	 * @return the chalkAction
	 */
	public ChalkAction getChalkAction()
	{
	
		return chalkAction;
	}

}
