package anyviewj.interfaces.view;

import anyviewj.debug.source.SourceSource;

/**
 * Class ViewFactory is a singleton that creates the appropriate
 * concrete implementation of a View. It does this based on the
 * arguments passed to one of the <code>create()</code> methods.
 *
 * @author  Nathan Fiedler
 */
public class ViewFactory {
    /** The one instance of this class. */
    private static ViewFactory theInstance;

    static {
        theInstance = new ViewFactory();
    }

    /**
     * This class cannot be instantiated.
     */
    private ViewFactory() {
    } // ViewFactory

    /**
     * Create an instance of a View based on the given source.
     *
     * @param  src  source view data.
     * @return  a new view instance.
     */
    public View create(SourceSource src) {
        View view = null;
        if (src.isByteCode()) {
            //view = new ByteCodeView(src);
        } else {
            String name = src.getName();
            if (name.toLowerCase().endsWith(".java")) {
                // It seems to be a Java source file.
                view = new JavaSourceView(src);
            } else {
                //view = new SourceView(src);
            }
        }
        return view;
    } // create

    /**
     * Returns the instance of this class.
     *
     * @return  a ViewFactory instance.
     */
    public static ViewFactory getInstance() {
        return theInstance;
    } // getInstance
} // ViewFactory
