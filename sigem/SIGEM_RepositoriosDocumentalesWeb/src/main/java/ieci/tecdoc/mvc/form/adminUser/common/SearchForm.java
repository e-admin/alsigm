/*
 * Created on 05-abr-2005
 *
 */
package ieci.tecdoc.mvc.form.adminUser.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class SearchForm extends ActionForm{
    String clave;
    String valor;
    
    String campoBusqueda = null; /* Bien por nombre, o uid,.. */
    
    byte tipoBusqueda = -1; /* Bien grupos, dtos o personas */
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        clave=
        valor =
        campoBusqueda = "";
        tipoBusqueda= -1;
    }
    
    

    /**
     * @return Returns the campoBusqueda.
     */
    public String getCampoBusqueda() {
        return campoBusqueda;
    }
    /**
     * @param campoBusqueda The campoBusqueda to set.
     */
    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }
    /**
     * @return Returns the clave.
     */
    public String getClave() {
        return clave;
    }
    /**
     * @param clave The clave to set.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    /**
     * @return Returns the tipoBusqueda.
     */
    public byte getTipoBusqueda() {
        return tipoBusqueda;
    }
    /**
     * @param tipoBusqueda The tipoBusqueda to set.
     */
    public void setTipoBusqueda(byte tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }
    /**
     * @return Returns the valor.
     */
    public String getValor() {
        return valor;
    }
    /**
     * @param valor The valor to set.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
