package ieci.tecdoc.mvc.dto.adminUser.bd;
/*
 * Created on 01-abr-2005
 *
 */

/**
 * @author Antonio María
 *
 */
public class GroupDTO {
    int id;
    String nombre;
    boolean isDisabled;
    
    public GroupDTO(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    
    public boolean getIsDisabled() {
        return isDisabled;
    }
    /**
     * @param isDisabled The isDisabled to set.
     */
    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String toString()
    {
        StringBuffer s = new StringBuffer();
        s.append("Id: " + getId() );
        s.append( "Name: " + getNombre());
        s.append("isDisabled: " + getIsDisabled());
        return s.toString();
        
        
    }
}
