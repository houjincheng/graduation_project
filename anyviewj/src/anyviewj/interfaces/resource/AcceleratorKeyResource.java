package anyviewj.interfaces.resource;

import javax.swing.KeyStroke;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007 gdut 1627</p>
 *
 * <p>Company: gdut 1627</p>
 *
 * @author cyf
 * @version 1.0
 */
public abstract class AcceleratorKeyResource {
    /**
     * 这里的索引值参考ActionResource
     * @param index int
     * @return String
     */
    public abstract KeyStroke getAcceleratorKey(int index);

    /**
     *
     * @param index int
     * @return String
     */
    public abstract String getAcceleratorKeyName(int index);
}
