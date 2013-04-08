/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.util;

/**
 * @author Antonio María
 *
 */
public class NodeImplLDAP implements Node{

    int id;
    String title;
    boolean hasChild;
    boolean childPrinted;
    
    private byte type;
    private String icon;
    
    public int getId() {
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
        return childPrinted;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#setChildPrinted(boolean)
     */
    public void setChildPrinted(boolean childPrinted) {
        this.childPrinted = childPrinted;
        
    }
    
    /**
     * @return Returns the icon.
     */
    public String getIcon() {
        if (type == Constantes.PERSON)
            icon = Constantes.TOKEN_ICON_PERSON_LDAP;
         else if (type == Constantes.GROUP)
            icon = Constantes.TOKEN_ICON_GROUP_LDAP;
         else if (type == Constantes.ORGANIZATIONAL_UNIT)
            icon = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT;
         else
             icon = Constantes.TOKEN_ICON_OTHER;
         
         return icon;
    }
    /**
     * @param icon The icon to set.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * @return Returns the type.
     */
    public byte getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(byte type) {
        this.type = type;
    }
    
    public String toString()
    {
        String s = new String();
        s = id + ":" + title;
        return s;
        
    }
}
