package anyviewj.interfaces.view;

import anyviewj.debug.source.SourceSource;
import java.io.IOException;
import javax.swing.JComponent;

/**
 * View defines the API expected of source and bytecode viewers in JSwat.
 *
 * @author  ltt
 */
public interface View {

    /**
     * Look for the given string in this view. Uses the view's current
     * text selection as the starting point. Will wrap around if the
     * string was not found after the current selection.
     *
     * @param  query       string to look for.
     * @param  ignoreCase  true to ignore case.
     * @return  true if string was found somewhere, false if string
     *          does not exist in this view.
     */
    boolean findString(String query, boolean ignoreCase);

    /**
     * Returns the long version of title of this view. This may be a
     * file name and path, a fully-qualified class name, or whatever is
     * appropriate for the type of view.
     *
     * @return  long view title.
     */
    String getLongTitle();

    /**
     * Returns the title of this view. This may be a file name, a class
     * name, or whatever is appropriate for the type of view.
     *
     * @return  view title.
     */
    String getTitle();

    /**
     * Returns a reference to the UI component which can be added to the
     * user interface component tree.
     *
     * @return  interface component.
     */
    JComponent getUI();

    /**
     * Read the source data and display the contents in the view, as
     * appropriate for the concrete view implementation.
     *
     * @param  src   source for the view to read from.
     * @param  line  line to make visible.
     * @throws  IOException
     *          if an I/O error occurs in reading the data.
     */
    void refresh(SourceSource src, int line) throws IOException;

    /**
     * Scrolls the view to the given line, if possible. Any value less
     * than one is ignored.
     *
     * @param  line  line to scroll to (1-based).
     */
    void scrollToLine(int line);
} // View
