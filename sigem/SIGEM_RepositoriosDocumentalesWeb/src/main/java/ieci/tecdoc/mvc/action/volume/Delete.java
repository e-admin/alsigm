/*
 * Created on 21-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * @author Antonio María
 *  
 */
public class Delete extends BaseAction {

    /*
     * (non-Javadoc)
     * 
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    int id;

    protected ActionForward executeLogic(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	    	
    	String entidad=SessionHelper.getEntidad(request);

        DynaActionForm dynaActionForm = (DynaActionForm) form;

        id = Integer.parseInt(dynaActionForm.get("id").toString());
        byte type = Byte.parseByte(dynaActionForm.get("type").toString());

        switch (type) {
        case Constantes.LIST: {
            deleteList(entidad);
            postGlobalMessage("message.list.delete", request);
            break;
        }
        case Constantes.VOLUME: {
            deleteVolume(entidad);
            postGlobalMessage("message.volume.delete", request);
            break;
        }
        case Constantes.REPOSITORIES: {
            deleteRepositories(entidad);
            postGlobalMessage("message.repository.delete", request);
            break;
        	}
        }
        request.setAttribute("tipo", String.valueOf(type));
        return mapping.findForward("success");
    }

    /**
     * @param id
     */
    private void deleteRepositories(String entidad) throws Exception {
        Repository repository = ObjFactory.createRepository(0);
        repository.load(id, entidad);
        repository.delete(entidad);
        repository.store(entidad);
    }

    /**
     * @param id
     */
    private void deleteVolume(String entidad) throws Exception {
        Volume volume = ObjFactory.createVolume(0, Defs.NULL_ID, entidad);
        volume.load(id, entidad);
        volume.delete(entidad);
        volume.store(entidad);
    }

    /**
     * @param id
     */
    private void deleteList(String entidad) throws Exception {
        ieci.tecdoc.idoc.admin.api.volume.VolumeList list = ObjFactory
                .createVolumeList(0);
        list.load(id, entidad);
        list.delete(entidad);
        list.store(entidad);
    }

}