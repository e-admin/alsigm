/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.form.volume;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author Antonio María
 *
 */
public class VolumeListForm extends ActionForm{
    List volumenes;
    String id;
    String tipo;
    String name;
    

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the tipo.
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * @param tipo The tipo to set.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @return Returns the volumenes.
     */
    public List getVolumenes() {
        return volumenes;
    }
    /**
     * @param volumenes The volumenes to set.
     */
    public void setVolumenes(List volumenes) {
        this.volumenes = volumenes;
    }
}
