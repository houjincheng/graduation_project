

/*********************************************************************
 *
 *      Copyright (C) 1999-2003 Nathan Fiedler
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * PROJECT:     JSwat
 * MODULE:      JSwat Actions
 * FILE:        JSwatAction.java
 *
 * AUTHOR:      Nathan Fiedler
 *
 * REVISION HISTORY:
 *      Name    Date            Description
 *      ----    ----            -----------
 *      nf      02/22/99        Initial version
 *      nf      10/07/01        Moved methods to SessionFrameMapper
 *      nf      04/17/02        Removed JSwat instance.
 *
 * $Id: JSwatAction.java 821 2003-01-26 20:31:17Z nfiedler $
 *
 ********************************************************************/
package anyviewj.interfaces.actions;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import anyviewj.debug.session.Session;
import anyviewj.debug.session.SessionFrameMapper;
import anyviewj.interfaces.ui.UIAdapter;

/**
 * Base action class which all other actions subclass. Provides some
 * utility functions needed by nearly all of the actions in JSwat.
 *
 * @author  Nathan Fiedler
 */
public abstract class JSwatAction extends AbstractAction {

    /**
     * Creates a new JSwatAction command with the given
     * action command string.
     *
     * @param  name  action command string
     */
    public JSwatAction(String name) {
        super(name);
    } // JSwatAction

    /**
     * Display an error message in a dialog.
     *
     * @param  o    Object with which to find the parent frame.
     *              Could be a subclass of EventObject or Component.
     * @param  msg  error message to be displayed.
     */
    public static void displayError(Object o, String msg) {
        Session session = getSession(o);
        UIAdapter adapter = session.getUIAdapter();
        adapter.showMessage(UIAdapter.MESSAGE_ERROR, msg);
    } // displayError

    /**
     * Find the hosting frame for this object. Often used
     * when displaying dialogs which require a host frame.
     *
     * @param  o  Object with which to find the parent frame.
     *            Could be a subclass of EventObject or Component.
     * @return  hosting frame or null if none.
     */
    public static Frame getFrame(Object o) {
        // Use the SessionFrameMapper to get the Session for the object.
        return SessionFrameMapper.getOwningFrame(o);
    } // getFrame

    /**
     * Finds the Session that is associated with the window
     * that contains the component that is the source of the
     * given object.
     *
     * @param  o  Object with which to find the parent frame.
     *            Could be a subclass of EventObject or Component.
     * @return  Session instance, or null if error.
     */
    public static Session getSession(Object o) {
        // Use the SessionFrameMapper to get the Session for the event.
        return SessionFrameMapper.getSessionForFrame(getFrame(o));
    } // getSession
    
    public static List<File> getFileFromSd(File path, String fileDot) {
        List<File> fileList = new ArrayList<File>();
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.endsWith(fileDot)) {
                    fileList.add(file);
                }
            } else {
                fileList.addAll(getFileFromSd(file, ".class"));
            }
        }
        return fileList;
    }
    
    
} // JSwatAction
