/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.form.adminUser.bd;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

/**
 * @author Antonio María
 *
 */
public class UsersListForm extends ActionForm{

    int id;
    String nombre;
    int tipo;
    private Collection usersList;
    int managerId;
    

    
    /**
     * @return Returns the managerId.
     */
    public boolean getHayUsuarios()
    {
        boolean hayUsuarios = false;
        int n = usersList.size();
        if (n > 0 )
            hayUsuarios = true;
        return hayUsuarios;
    }
    public int getManagerId() {
        return managerId;
    }
    /**
     * @param managerId The managerId to set.
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
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
    /**
     * @return Returns the tipo.
     */
    public int getTipo() {
        return tipo;
    }
    /**
     * @param tipo The tipo to set.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    /**
     * @return Returns the usersList.
     */
    public Collection getUsersList() {
        return usersList;
    }
    /**
     * @param usersList The usersList to set.
     */
    public void setUsersList(Collection usersList) {
        this.usersList = usersList;
    }
}
