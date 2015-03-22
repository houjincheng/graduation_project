package anyviewj.interfaces.view;


import anyviewj.debug.source.SourceSource;
import anyviewj.debug.source.event.ContextListener;
import anyviewj.lang.ClassDefinition;
import com.bluemarsh.jswat.parser.java.lexer.LexerException;
import com.bluemarsh.jswat.parser.java.node.Token;
import com.bluemarsh.jswat.parser.java.parser.ParserException;
//import com.bluemarsh.jswat.view.BreakpointTooltipProducer;
//import com.bluemarsh.jswat.ui.UIAdapter;
import anyviewj.util.SkipList;
import anyviewj.util.Defaults;
import com.sun.jdi.Location;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.BadLocationException;

/**
 * Defines the JavaSourceView class which extends SourceView and displays
 * Java source code.
 *
 * @author  ltt
 */
public class JavaSourceView extends SourceView implements ContextListener {
    /** Draw layer responsible for colorizing Java code. */
    private JavaDrawLayer javaDrawLayer;
    /** Draw layer that highlights the line with an error. */
    //private ErrorDrawLayer errorDrawLayer;
    /** Tooltip producer for detailed breakpoint information. */
    //private BreakpointTooltipProducer bpTooltipProducer;
    /** Tooltip producer for showing parser error message.  */
    //private MessageTooltipProducer msgTooltipProducer;
    /** Classlines of the parsed source file. */
    private List classDefinitions;
    /** List of method definitions. */
    private List methodDefinitions;

    /**
     * Creates a JavaSourceView object.
     *
     * @param  src  source object to be displayed.
     */
    public JavaSourceView(SourceSource src) {
    	
        super(src);
        System.out.println( "public JavaSourceView(SourceSource src)" );
        // Set up the text component with our enhancements.
        javaDrawLayer = new JavaDrawLayer();
        textComponent.addDrawLayer(javaDrawLayer);
        
        //errorDrawLayer = new ErrorDrawLayer();
        //textComponent.addDrawLayer(errorDrawLayer);
        //bpTooltipProducer = new BreakpointTooltipProducer();
        //textComponent.addTooltipProducer(bpTooltipProducer);
        //msgTooltipProducer = new MessageTooltipProducer();
        //textComponent.addTooltipProducer(msgTooltipProducer);

        // Remove the generic popup menu.
        //textComponent.removeMouseListener(popupMenu);

        // Create and add our Java popup menu.
        //popupMenu = new JavaSourceViewPopup(src);
        //textComponent.addMouseListener(popupMenu);
    } // JavaSourceView

    /**
     * Check if the Location is in the same source file as the file we
     * are displaying.
     *
     * @param  location  Location to check.
     * @return  True if breakpoint source name same as our filename.
     */
    @Override
	protected boolean matches(Location location) {
        if (classDefinitions == null) {
            // Fall back to the default behavior.
            return super.matches(location);
        } else {
            Iterator iter = classDefinitions.iterator();
            String clazz = location.declaringType().name();
            while (iter.hasNext()) {
                ClassDefinition cd = (ClassDefinition) iter.next();
                if (cd.getClassName().equals(clazz)) {
                    return true;
                }
            }
            return false;
        }
    } // matches

    /**
     * Reads the class definition information from the source file using
     * the JavaParser class. Give this information to the text component
     * and row header popup menus.
     */
    protected void parseClassDefs() {
        // Read in the class definitions from the source file.
        CharArrayReader car = new CharArrayReader(viewContent);
        JavaParser parser = new JavaParser(car);
        try {
            classDefinitions = null;
            methodDefinitions = null;
            parser.parse();
            // Parser closes the reader in all cases.
            classDefinitions = parser.getClassLines();
            methodDefinitions = parser.getMethodLines();
            //if (logger.isLoggable(Level.INFO)) {
            //    logger.info("parsed Java source in " + viewTitle);
            //}
        } catch (ParserException pe) {
            // Highlight the line with the error.
            Token token = pe.getToken();
            // Translate absolute to relative numbering.
            int badline = token.getLine() - 1;
            int badpos = token.getPos();
            try {
                int p0 = getLineStartOffset(badline);
                int p1 = getLineEndOffset(badline);
                //errorDrawLayer.setHighlight(p0, p1);
            } catch (BadLocationException ble) {
                // This is highly unlikely.
                //owningSession.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_ERROR, ble.toString());
            }
            // Show the parser error message as a tooltip.
            //msgTooltipProducer.setMessage(pe.getMessage(), badline + 1);

        } catch (LexerException le) {
            // This will happen a lot with unsupported language features.
            //owningSession.getUIAdapter().showMessage(
            //    UIAdapter.MESSAGE_NOTICE, le.toString());
        } catch (Exception e) {
            //owningSession.getUIAdapter().showMessage(
            //    UIAdapter.MESSAGE_ERROR, e.toString());
        }
        breakpointDrawLayer.setClassDefinitions(classDefinitions);
        //bpTooltipProducer.setClassDefinitions(classDefinitions);
        //JavaSourceViewPopup popup = (JavaSourceViewPopup) popupMenu;
        //popup.setClassDefinitions(classDefinitions, methodDefinitions);
    } // parseClassDefs

    /**
     * Code to be executed after the refresh() method has completed.
     */
    @Override
	protected void refreshPost() {
        // Parse first so the breakpoints can use the information.
        boolean parse = preferences.getBoolean("parseView",
                                               Defaults.VIEW_PARSE);
        if (parse) {
            parseClassDefs();
        }

        // Now call the superclass so the draw layers get the class
        // definitions they need.
        super.refreshPost();
    } // refreshPost

    /**
     * Called to update this view's preferences, either when this object
     * is constructed or when the preferences change.
     */
    @Override
	protected void setPreferences() {
        super.setPreferences();
        // Notify the colorizers of possible changes.
        try {
            JavaDrawLayer.updateColors(preferences);
        } catch (NumberFormatException nfe) {
            //owningSession.getUIAdapter().showMessage(UIAdapter.MESSAGE_ERROR,
            //                                         nfe.toString());
        }
        // Short method descriptor option may have changed.
        //JavaSourceViewPopup popup = (JavaSourceViewPopup) popupMenu;
        //if (popup != null) {
        //    popup.setClassDefinitions(classDefinitions, methodDefinitions);
        //}
    } // setPreferences

    /**
     * Set the content of the text component using the text defined by
     * <code>viewContent</code>. This implementation applies styles to
     * the Java source to colorize it syntactically.
     */
    @Override
	protected void setTextContent() {
        boolean colorize = preferences.getBoolean("colorizeView",
                                                  Defaults.VIEW_COLORIZE);

        SourceContent doc = new SourceContent(viewContent);

        if (colorize) {
            // Colorize the source.
            CharArrayReader car = new CharArrayReader(viewContent);
            JavaScanner scanner = new JavaScanner(car, doc);
            SkipList tokenList = null;
            try {
                tokenList = scanner.scan();
            } catch (IOException ioe) {
                //owningSession.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_WARNING, ioe.toString());
            } catch (LexerException le) {
                // This will happen a lot with unsupported language features.
                //owningSession.getUIAdapter().showMessage(
                //    UIAdapter.MESSAGE_NOTICE, le.toString());
            }
            // Scanner closes the reader.
            // Give the JavaDrawLayer the token information.
            javaDrawLayer.setTokens(tokenList);
        } else {
            // Indicate that we have no tokens and the layer
            // should be inactive.
            javaDrawLayer.setTokens(null);
        }

        // Do this when we're all done, so it forces a repaint.
        textComponent.setContent(doc);
    } // setTextContent
} // JavaSourceView
