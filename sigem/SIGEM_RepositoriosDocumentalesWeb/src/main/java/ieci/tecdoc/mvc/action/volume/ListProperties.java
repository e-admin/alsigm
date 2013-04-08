/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.volume.ListForm;
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
public class ListProperties extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(ListProperties.class);    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
    	String entidad=SessionHelper.getEntidad(request);
    	
        ListForm listForm= (ListForm) form;
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);
                
        loadData (listForm, id, entidad);
        
        
        return mapping.findForward("success");
    }
    
    public void loadData(ListForm listForm, int id, String entidad) throws Exception
    {
        ieci.tecdoc.idoc.admin.api.volume.VolumeList volumeList = ObjFactory.createVolumeList(0);
        volumeList.load(id, entidad);
        
        listForm.setName(volumeList.getName());
        listForm.setId(id);
        listForm.setDescription(volumeList.getRemarks());
        
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        Date creationDate = volumeList.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        int creatorId = volumeList.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = getUserNameById(creatorId, entidad);
        
        Date updateDate = volumeList.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = volumeList.getUpdaterId();
        String updaterName ="";
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = getUserNameById(updaterId, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        listForm.setCreationDate(fechaCreacion);
        listForm.setCreatorName(creatorName);
        listForm.setUpdateDate(fechaModificacion);
        listForm.setUpdaterName(updaterName);
        

        // Detalle
        //repositoryForm.setPath(rep.getPath());

        /* int status = rep.getState();

        String states[] = volumeForm.getStates();
        int i = 0;
        if ( (status & VolumeDefs.VOL_STAT_FULL ) == VolumeDefs.VOL_STAT_FULL ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_FULL);
        }
        if ( (status & VolumeDefs.VOL_STAT_NOT_READY) != VolumeDefs.VOL_STAT_NOT_READY ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_NOT_READY);
            }
        if ( (status & VolumeDefs.VOL_STAT_READONLY) == VolumeDefs.VOL_STAT_READONLY ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_READONLY);
        }        
        */
        
        
    }
    public String getUserNameById(int id, String entidad) throws Exception
    {
        User u = ObjFactory.createUser();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }    

}
