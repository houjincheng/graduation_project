package anyviewj.util;

import java.util.prefs.Preferences;


public class Names {
    /** Our Preferences node. */
    private static Preferences preferences;

    static {
        preferences = Preferences.userRoot().node("com/bluemarsh/jswat/util");
    }

    /**
     * None shall instantiate us.
     */
    private Names() {
    } // Names

    /**
     * Determine if the given string is a valid Java identifier.
     *
     * @param  s  string to validate.
     * @return  true if string is a valid Java identifier.
     */
    public static boolean isJavaIdentifier(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        // First character of identifier is a special case.
        if (!Character.isJavaIdentifierStart(s.charAt(0))) {
            return false;
        }
        // Now check all other characters of the identifier.
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                return false;
            }
        }
        return true;
    } // isJavaIdentifier

    /**
     * Determine if the given string is a valid method identifier.
     *
     * @param  s  string to validate.
     * @return  true if string is a valid method identifier.
     */
    public static boolean isMethodIdentifier(String s) {
        return isJavaIdentifier(s) || s.equals("<init>")
            || s.equals("<clinit>");
    } // isMethodIdentifier

    /**
     * Returns just the name of the class, without the package name.
     *
     * @param  cname  Name of class, possibly fully-qualified.
     * @return  Just the class name, or null if cname is null.
     */
    public static String justTheName(String cname) {
        if (cname == null) {
            return null;
        }

        if (preferences.getBoolean("shortClassNames",
                                   Defaults.SHORT_CLASS_NAMES)) {
            int i = cname.lastIndexOf('.');
            if (i > 0) {
                return cname.substring(i + 1);
            }
        }
        return cname;
    } // justTheName
} // Names
