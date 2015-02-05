package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

public class NodeImplBD implements Node{
	/* (non-Javadoc)
     * @see ieci.tecdoc.mvc.util.Nodo_#getId()
     */
    
    int id;
    String title;
    String codigo;
    boolean hasChild;
    boolean childPrinted;
    int tipo;
    
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
        s = id + ":" + title + ":" + codigo;
        return s;
        
    }

	public int getTipo() {
		// TODO Auto-generated method stub
		return tipo;
	}

	public void setTipo(int tipo) {
		// TODO Auto-generated method stub
		this.tipo = tipo;
	}
	
	public String getCodigo() {
		// TODO Auto-generated method stub
		return codigo;
	}

	public void setCodigo(String codigo) {
		// TODO Auto-generated method stub
		this.codigo = codigo;
	}
}
