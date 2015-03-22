package anyviewj.interfaces.resource.defaults;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import anyviewj.interfaces.resource.IconResource;

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
public class DefaultIconResource extends IconResource {
    private ImageIcon[] icons = new ImageIcon[INDEXES_TOTAL_COUNT];

    public DefaultIconResource() {
        loadIcons();
    }

    /**
     * getIcon
     *
     * @param index int
     * @return Icon
     * @todo Implement this anyviewj.resources.IconResource method
     */
    @Override
	public Icon getIcon(int index) {
        return icons[index];
    }

    private void loadIcons(){
        icons[TABPANE_COMBINE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/tabs/combine.gif"));
        icons[TABPANE_DEPART] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/tabs/depart.gif"));
        icons[TABPANE_CLOSE_WITHOUT_SAVE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/tabs/closenosave.gif"));
        icons[TABPANE_CLOSE_WITH_SAVE] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/tabs/closewantsave.gif"));
        icons[TABPANE_READONLY ] = new ImageIcon(anyviewj.interfaces.resource.ResourceManager.class.
                                            getResource("/anyviewj/interfaces/resources/tabs/closereadonly.gif"));
    }
}
