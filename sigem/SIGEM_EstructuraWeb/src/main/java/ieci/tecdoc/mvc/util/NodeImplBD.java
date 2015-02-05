/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.util;

/**
 * @author Antonio María
 *
 */
public class NodeImplBD implements Node{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#getId()
     */
    
    int id;
    String title;
    boolean hasChild;
    boolean childPrinted;
    
    public int getId() {
        // TODO Auto-generated method stub
        return id;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#setId(int)
     */
    public void setId(int id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#getTitle()
     */
    public String getTitle() {
        // TODO Auto-generated method stub
        return title;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#setTitle(java.lang.String)
     */
    public void setTitle(String title) {
        this.title = title;
        
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#getHasChild()
     */
    public boolean getHasChild() {
        // TODO Auto-generated method stub
        return hasChild;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#setHasChild(boolean)
     */
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#getChildPrinted()
     */
    public boolean getChildPrinted() {
        // TODO Auto-generated method stub
        return childPrinted;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#setChildPrinted(boolean)
     */
    public void setChildPrinted(boolean childPrinted) {
        this.childPrinted = childPrinted;
        
    }
    
    public String toString()
    {
        String s = new String();
        s = id + ":" + title;
        return s;
        
    }

}
