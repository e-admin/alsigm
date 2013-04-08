/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;

/**
 * @author Antonio María
 *
 */
public class NodeImplLDAP implements Node{

	public static String NOT_VALID_FOR_SELECTION = "0";
	public static String VALID_FOR_SELECTION = "1";
	
    int id;
    String title;
    String dn;
    String validForSelection;
    boolean hasChild;
    boolean childPrinted;
    
    private int tipo;
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
        if (tipo == LdapConstants.PERSON)
			icon = Constantes.TOKEN_ICON_PERSON_LDAP;
		else if (tipo == LdapConstants.GROUP)
			icon = Constantes.TOKEN_ICON_GROUP_LDAP;
		else if (tipo == LdapConstants.ORGANIZATIONAL_UNIT)
			icon = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT_LDAP;
		else if (tipo == LdapConstants.DEPARTMENT)
			icon = Constantes.TOKEN_ICON_GROUP_LDAP;
		else
			icon = Constantes.TOKEN_ICON_OTHER;

		return icon;
    }
    /**
	 * @param icon
	 *            The icon to set.
	 */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * @return Returns the tipo.
     */
    public int getTipo() {
        return tipo;
    }
    /**
     * @param type The tipo to set.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
	public String getCodigo() {
		return dn;
	}

	public void setCodigo(String codigo) {
		this.dn = codigo;
	}
	
    public String getValidForSelection() {
		return validForSelection;
	}

	public void setValidForSelection(String validForSelection) {
		this.validForSelection = validForSelection;
	}

	public String toString()
    {
        String s = new String();
        s = id + ":" + title;
        return s;
        
    }
}
