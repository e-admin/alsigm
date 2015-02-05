package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

public interface Node {
    
	// Necesario para el tipo de organizaciones
	public int getTipo();
    public void setTipo(int tipo);
	
    public int getId();
    public void setId(int id);
    
    public String getTitle();
    public void setTitle(String title);
    
    public boolean getHasChild();
    public void setHasChild(boolean hasChild);
    
    public boolean getChildPrinted();
    public void setChildPrinted(boolean childPrinted);
    
    public String getCodigo();
    public void setCodigo(String codigo);
    
    public String toString();

}