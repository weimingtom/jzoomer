package com.dextrys.trilogy.util.swt;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.SWT;

/*
 * @author Marquis Hou<talent_marquis>
 * Email: marquisx.tz@gmail.com
 * Copyright (C) 2008 Marquis Hou<talent_marquis>
 * All rights reserved.
 * Create Date:Jun 28, 2008
 */
public class HotKeysManager
{
	private static Map< String, Integer > keyCodes;
	
	public static int getHotKeyCode( String keyName )
	{
		initKeyCodes();
		return keyCodes.get( keyName.toUpperCase() );
	}
	/**
	 * Initializes the internal key code table.
	 */
	private static final void initKeyCodes() {
		keyCodes = new HashMap(40);

		keyCodes.put( "ALT", new Integer( SWT.ALT ) );//$NON-NLS-1$
		keyCodes.put( "CTRL", new Integer( SWT.CTRL ) );//$NON-NLS-1$
		keyCodes.put( "COMMAND", new Integer( SWT.COMMAND ) );//$NON-NLS-1$
		keyCodes.put( "SHIFT", new Integer( SWT.SHIFT ) );//$NON-NLS-1$
		
		keyCodes.put("BACKSPACE", new Integer(8)); //$NON-NLS-1$
		keyCodes.put("TAB", new Integer(9)); //$NON-NLS-1$
		keyCodes.put("RETURN", new Integer(13)); //$NON-NLS-1$
		keyCodes.put("ENTER", new Integer(13)); //$NON-NLS-1$
		keyCodes.put("ESCAPE", new Integer(27)); //$NON-NLS-1$
		keyCodes.put("ESC", new Integer(27)); //$NON-NLS-1$
		keyCodes.put("DELETE", new Integer(127)); //$NON-NLS-1$

		keyCodes.put("SPACE", new Integer(' ')); //$NON-NLS-1$
		keyCodes.put("ARROW_UP", new Integer(SWT.ARROW_UP)); //$NON-NLS-1$
		keyCodes.put("ARROW_DOWN", new Integer(SWT.ARROW_DOWN)); //$NON-NLS-1$
		keyCodes.put("ARROW_LEFT", new Integer(SWT.ARROW_LEFT)); //$NON-NLS-1$
		keyCodes.put("ARROW_RIGHT", new Integer(SWT.ARROW_RIGHT)); //$NON-NLS-1$
		keyCodes.put("PAGE_UP", new Integer(SWT.PAGE_UP)); //$NON-NLS-1$
		keyCodes.put("PAGE_DOWN", new Integer(SWT.PAGE_DOWN)); //$NON-NLS-1$
		keyCodes.put("HOME", new Integer(SWT.HOME)); //$NON-NLS-1$
		keyCodes.put("END", new Integer(SWT.END)); //$NON-NLS-1$
		keyCodes.put("INSERT", new Integer(SWT.INSERT)); //$NON-NLS-1$
		keyCodes.put("F1", new Integer(SWT.F1)); //$NON-NLS-1$
		keyCodes.put("F2", new Integer(SWT.F2)); //$NON-NLS-1$
		keyCodes.put("F3", new Integer(SWT.F3)); //$NON-NLS-1$
		keyCodes.put("F4", new Integer(SWT.F4)); //$NON-NLS-1$
		keyCodes.put("F5", new Integer(SWT.F5)); //$NON-NLS-1$
		keyCodes.put("F6", new Integer(SWT.F6)); //$NON-NLS-1$
		keyCodes.put("F7", new Integer(SWT.F7)); //$NON-NLS-1$
		keyCodes.put("F8", new Integer(SWT.F8)); //$NON-NLS-1$
		keyCodes.put("F9", new Integer(SWT.F9)); //$NON-NLS-1$
		keyCodes.put("F10", new Integer(SWT.F10)); //$NON-NLS-1$
		keyCodes.put("F11", new Integer(SWT.F11)); //$NON-NLS-1$
		keyCodes.put("F12", new Integer(SWT.F12)); //$NON-NLS-1$
	}
}
