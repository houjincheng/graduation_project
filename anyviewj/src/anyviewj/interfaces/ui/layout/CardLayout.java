package anyviewj.interfaces.ui.layout;

import java.awt.*;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
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

public class CardLayout implements LayoutManager2,
                                   Serializable {

    private static final long serialVersionUID = -5328196481005933333L;

    /**
     * Creates a new card layout with gaps of size zero.
     */
    public CardLayout() {

    }

    /**
     * Adds the specified component to this card layout's internal
     * table of names. The object specified by <code>constraints</code>
     * must be a string. The card layout stores this string as a key-value
     * pair that can be used for random access to a particular card.
     * By calling the <code>show</code> method, an application can
     * display the component with the specified name.
     * @param     comp          the component to be added.
     * @param     constraints   a tag that identifies a particular
     *                                        card in the layout.
     * @see       java.awt.CardLayout#show(java.awt.Container, java.lang.String)
     * @exception  IllegalArgumentException  if the constraint is not a string.
     */
    @Override
	public void addLayoutComponent(Component comp, Object constraints) {
        Container container = comp.getParent();
        if(container!=null){
            if(container.getComponentCount()==1) comp.setVisible(true);
            else comp.setVisible(false);
        }
    }

    /**
     * @deprecated   replaced by
     *      <code>addLayoutComponent(Component, Object)</code>.
     */
    @Deprecated
	@Override
	public void addLayoutComponent(String name, Component comp) {
        this.addLayoutComponent(comp,name);
    }

    /**
     * Removes the specified component from the layout.
     * @param   comp   the component to be removed.
     * @see     java.awt.Container#remove(java.awt.Component)
     * @see     java.awt.Container#removeAll()
     */
    @Override
	public void removeLayoutComponent(Component comp) {
    }

    /**
     * Determines the preferred size of the container argument using
     * this card layout.
     * @param   parent the parent container in which to do the layout
     * @return  the preferred dimensions to lay out the subcomponents
     *                of the specified container
     * @see     java.awt.Container#getPreferredSize
     * @see     java.awt.CardLayout#minimumLayoutSize
     */
    @Override
	public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;

            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                Dimension d = comp.getPreferredSize();
                if (d.width > w) {
                    w = d.width;
                }
                if (d.height > h) {
                    h = d.height;
                }
            }
            return new Dimension(insets.left + insets.right + w ,
                                 insets.top + insets.bottom + h );
        }
    }

    /**
     * Calculates the minimum size for the specified panel.
     * @param     parent the parent container in which to do the layout
     * @return    the minimum dimensions required to lay out the
     *                subcomponents of the specified container
     * @see       java.awt.Container#doLayout
     * @see       java.awt.CardLayout#preferredLayoutSize
     */
    @Override
	public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int w = 0;
            int h = 0;

            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                Dimension d = comp.getMinimumSize();
                if (d.width > w) {
                    w = d.width;
                }
                if (d.height > h) {
                    h = d.height;
                }
            }
            return new Dimension(insets.left + insets.right + w ,
                                 insets.top + insets.bottom + h);
        }
    }

    /**
     * Returns the maximum dimensions for this layout given the components
     * in the specified target container.
     * @param target the component which needs to be laid out
     * @see Container
     * @see #minimumLayoutSize
     * @see #preferredLayoutSize
     */
    @Override
	public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    @Override
	public float getLayoutAlignmentX(Container parent) {
        return 0.5f;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    @Override
	public float getLayoutAlignmentY(Container parent) {
        return 0.5f;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    @Override
	public void invalidateLayout(Container target) {
    }

    /**
     * Lays out the specified container using this card layout.
     * <p>
     * Each component in the <code>parent</code> container is reshaped
     * to be the size of the container, minus space for surrounding
     * insets, horizontal gaps, and vertical gaps.
     *
     * @param     parent the parent container in which to do the layout
     * @see       java.awt.Container#doLayout
     */
    @Override
	public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            Component comp = null;
            boolean currentFound = false;

            for (int i = 0 ; i < ncomponents ; i++) {
                comp = parent.getComponent(i);
                comp.setBounds(insets.left,  insets.top,
                               parent.getWidth() - (insets.left + insets.right),
                               parent.getHeight() - (insets.top + insets.bottom));
                if (comp.isVisible()) {
                    currentFound = true;
                }
            }

            if (!currentFound && ncomponents > 0) {
                parent.getComponent(0).setVisible(true);
            }
        }
    }

    /**
     * Make sure that the Container really has a CardLayout installed.
     * Otherwise havoc can ensue!
     */
    void checkLayout(Container parent) {
        if (parent.getLayout() != this) {
            throw new IllegalArgumentException("wrong parent for CardLayout");
        }
    }

    /**
     * Flips to the first card of the container.
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#last
     */
    public void first(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    break;
                }
            }
            if (ncomponents > 0) {
                parent.getComponent(0).setVisible(true);
                parent.validate();
            }
        }
    }

    /**
     * Flips to the next card of the specified container. If the
     * currently visible card is the last one, this method flips to the
     * first card in the layout.
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#previous
     */
    public void next(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    int currentCard = (i + 1) % ncomponents;
                    comp = parent.getComponent(currentCard);
                    comp.setVisible(true);
                    parent.validate();
                    return;
                }
            }
            showDefaultComponent(parent);
        }
    }

    /**
     * Flips to the previous card of the specified container. If the
     * currently visible card is the first one, this method flips to the
     * last card in the layout.
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#next
     */
    public void previous(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    int currentCard = ((i > 0) ? i-1 : ncomponents-1);
                    comp = parent.getComponent(currentCard);
                    comp.setVisible(true);
                    parent.validate();
                    return;
                }
            }
            showDefaultComponent(parent);
        }
    }

    void showDefaultComponent(Container parent) {
        if (parent.getComponentCount() > 0) {
            parent.getComponent(0).setVisible(true);
            parent.validate();
        }
    }

    /**
     * Flips to the last card of the container.
     * @param     parent   the parent container in which to do the layout
     * @see       java.awt.CardLayout#first
     */
    public void last(Container parent) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            int ncomponents = parent.getComponentCount();
            for (int i = 0 ; i < ncomponents ; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    break;
                }
            }
            if (ncomponents > 0) {
                parent.getComponent(ncomponents - 1).setVisible(true);
                parent.validate();
            }
        }
    }

    /**
     * Flips to the component that was added to this layout with the
     * specified <code>name</code>, using <code>addLayoutComponent</code>.
     * If no such component exists, then nothing happens.
     * @param     parent   the parent container in which to do the layout
     * @param     name     the component name
     * @see       java.awt.CardLayout#addLayoutComponent(java.awt.Component, java.lang.Object)
     */
    public void show(Container parent, Component comp) {
        if(comp==null) return;
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            if(comp.getParent()!=parent) return;
            for(int i = parent.getComponentCount()-1; i>=0; i--){
                Component c = parent.getComponent(i);
                if(c != comp) c.setVisible(false);
                else
                    comp.setVisible(true);
            }
            parent.validate();
        }
    }

    /**
     * Flips to the component that was added to this layout with the
     * specified <code>name</code>, using <code>addLayoutComponent</code>.
     * If no such component exists, then nothing happens.
     * @param     parent   the parent container in which to do the layout
     * @param     index     the component name
     * @see       java.awt.CardLayout#addLayoutComponent(java.awt.Component, java.lang.Object)
     */
    public void show(Container parent, int index) {
        synchronized (parent.getTreeLock()) {
            checkLayout(parent);
            if(index < 0 || index >= parent.getComponentCount()) return;
            for(int i=parent.getComponentCount()-1;i>=0;i--){
                Component c = parent.getComponent(i);
                if(i != index) c.setVisible(false);
                else
                    c.setVisible(true);
            }
            parent.validate();
        }
    }


    /**
     * Returns a string representation of the state of this card layout.
     * @return    a string representation of this card layout.
     */
    @Override
	public String toString() {
        return getClass().getName();
    }

    /**
     * Reads serializable fields from stream.
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
    }

    /**
     * Writes serializable fields to stream.
     */
    private void writeObject(ObjectOutputStream s)
        throws IOException
    {
    }
}
