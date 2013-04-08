/*
 * Created on 11-ago-2005
 *
 */
package ieci.tdw.ispac.ispaclib.bean;



import ieci.tdw.ispac.api.ISessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * Interfaz de implementacion obligfada para los action de gestion de arboles
 *
 */
public interface NodeSelectionHandlerAction {

    /**
     * Metodo que devuelve el action a ejecutar en caso de que se dese ver un
     * nodo
     * 
     * @param mappings
     * @param form
     * @param request
     * @param response
     * @return @throws
     *         Exception
     */
    public ActionForward verNodo(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, ISessionAPI sessionApi) throws Exception;

    /**
     * Metodo que retorna el path del action encargado de ejecutar la accion verNodo
     * @return
     */
    public String getHandlerPath();

}