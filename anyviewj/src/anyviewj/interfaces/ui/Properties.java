package anyviewj.interfaces.ui;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Properties {
    /** The resource bundle contained in this object. */
    private static ResourceBundle resourceBundle;

    static {
        // Retrieve the resource bundle for this package.
        resourceBundle = ResourceBundle.getBundle(
            Properties.class.getName());
    }

    /**
     * Retrieves an object from the localized resource bundle. In most
     * cases this is an image.
     *
     * @param  key  key name of the resource to find.
     * @return  URL pointing to the object resource, or null if it
     *          was not found.
     */
    public static URL getResource(String key) {
        try {
            String name = resourceBundle.getString(key);
            return Properties.class.getResource(name);
        } catch (MissingResourceException mre) {
            return null;
        }
    }

    /**
     * Retrieves the String resource from this bundle. If the key was
     * not found, this method returns a string that clearly indicates
     * that it was missing.
     *
     * @param  key  name of String resource to retrieve.
     * @return  named resource value.
     */
    public static String getString(String key) {
        return getString(key, false);
    }

    /**
     * Retrieves the String resource from this bundle. If the key was
     * not found and <code>missingNull</code> is false, this method
     * returns a string that clearly indicates that it was missing;
     * otherwise it returns null.
     *
     * @param  key          name of String resource to retrieve.
     * @param  missingNull  if true, return null when resource is missing.
     * @return  named resource value.
     */
    public static String getString(String key, boolean missingNull) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException mre) {
            return missingNull ? null : "MISSING STRING: " + key;
        }
    }
    
	/**
	 * 取配置项内容 <br>
	 * 
	 * @param key:String 关键字 <br>
	 * 
	 * @return String 值;若无，则返回null <br>
	 */
	public static String getProperty(String key) {
		String value = null;
		value = resourceBundle.getString(key);
		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value;
		}
	}

}
