/*********************************************************************
 *
 *      Copyright (C) 2003-2005 Nathan Fiedler
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
 * $Id: ViewException.java 1813 2005-07-17 05:55:46Z nfiedler $
 *
 ********************************************************************/

package anyviewj.interfaces.view;

/**
 * ViewException is thrown when there was a problem with a view
 * operation.
 *
 * @author  Nathan Fiedler
 */
public class ViewException extends Exception {
    /** silence the compiler warnings */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a ViewException with no message.
     */
    public ViewException() {
        super();
    } // ViewException

    /**
     * Constructs a ViewException with the given message.
     *
     * @param  msg  message.
     */
    public ViewException(String msg) {
        super(msg);
    } // ViewException

    /**
     * Constructs a ViewException with the given message and cause.
     *
     * @param  msg    message.
     * @param  cause  the real cause of the problem.
     */
    public ViewException(String msg, Throwable cause) {
        super(msg, cause);
    } // ViewException

    /**
     * Constructs a ViewException with the given cause.
     *
     * @param  cause  the real cause of the problem.
     */
    public ViewException(Throwable cause) {
        super(cause);
    } // ViewException
} // ViewException
