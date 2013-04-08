/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.util;

/**
 * @author Antonio María
 *
 */
public class NodoBasic {

    private int  id;
    private String title;
    private boolean hijosMostrados;
    private boolean tieneHijos;
    
    
    public NodoBasic(String title, int id)
    {
        this.title = new String (title);
        this.id =id;
    }
    /**
     * @return Returns the hijosMostrados.
     */
    public boolean isHijosMostrados() {
        return hijosMostrados;
    }
    /**
     * @param hijosMostrados The hijosMostrados to set.
     */
    public void setHijosMostrados(boolean hijosMostrados) {
        this.hijosMostrados = hijosMostrados;
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
     * @return Returns the nombreDepto.
     */
    public String getNombreDepto() {
        return title;
    }
    /**
     * @param nombreDepto The nombreDepto to set.
     */
    public void setNombreDepto(String title) {
        this.title = title;
    }
    /**
     * @return Returns the tieneHijos.
     */
    public boolean isTieneHijos() {
        return tieneHijos;
    }
    /**
     * @param tieneHijos The tieneHijos to set.
     */
    public void setTieneHijos(boolean tieneHijos) {
        this.tieneHijos = tieneHijos;
    }
    
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
