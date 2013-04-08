/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.volume.StateListDTO;
import ieci.tecdoc.mvc.form.volume.RepositoryForm;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class RepositoryProperties extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(RepositoryProperties.class);    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        RepositoryForm repositoryForm= (RepositoryForm) form;
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);
        
        StateListDTO stateListDTO = new StateListDTO();
        stateListDTO.setStates(getRepositoryAtts());
        
        request.setAttribute("statesRepositoryList", stateListDTO );
        int type = loadData (repositoryForm, id, entidad);
        request.setAttribute("type",new Integer(type));
        
        return mapping.findForward("success");
    }
    
    public int loadData(RepositoryForm repositoryForm, int id, String entidad) throws Exception
    {
        Repository rep = ObjFactory.createRepository(0);
        rep.load(id, entidad);
        int type = rep.getType();
        if (VolumeDefs.REP_TYPE_FTP == type )
            repositoryForm.setType("FTP");
        else if (VolumeDefs.REP_TYPE_PFS == type)
            repositoryForm.setType("PFS");
        else if (VolumeDefs.REP_TYPE_DB == type)
        	repositoryForm.setType("DB");
        else if (VolumeDefs.REP_TYPE_DB == type)
        	repositoryForm.setType("CENTERA");
        
        repositoryForm.setName(rep.getName());
        repositoryForm.setId(id);
        repositoryForm.setDescription(rep.getRemarks());
        
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        Date creationDate = rep.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        int creatorId = rep.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = getUserNameById(creatorId, entidad);
        
        Date updateDate = rep.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = rep.getUpdaterId();
        String updaterName ="";
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = getUserNameById(updaterId, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        repositoryForm.setCreationDate(fechaCreacion);
        repositoryForm.setCreatorName(creatorName);
        repositoryForm.setUpdateDate(fechaModificacion);
        repositoryForm.setUpdaterName(updaterName);
        

        // Detalle
        if (VolumeDefs.REP_TYPE_FTP == type || VolumeDefs.REP_TYPE_PFS == type)
        	repositoryForm.setPath(rep.getPath());

        int status = rep.getState();
        String states[] = repositoryForm.getStates();
        int i = 0;
        if ( (status & VolumeDefs.REP_STAT_OFFLINE) != VolumeDefs.REP_STAT_OFFLINE ) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_OFFLINE);
        }
        if ( (status & VolumeDefs.REP_STAT_READONLY) == VolumeDefs.REP_STAT_READONLY ) {
            states[i++] = String.valueOf(VolumeDefs.REP_STAT_READONLY);
        }
        return type;
        
    }
    public String getUserNameById(int id, String entidad) throws Exception
    {
        User u = ObjFactory.createUser();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }    

}
