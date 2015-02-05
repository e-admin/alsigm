/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.volume.StateListDTO;
import ieci.tecdoc.mvc.form.volume.VolumeForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

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
public class VolumeProperties extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(VolumeProperties.class);    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        VolumeForm volumeForm = (VolumeForm) form;
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);
        
        StateListDTO stateListDTO = new StateListDTO();
        stateListDTO.setStates(getStatesVolumeList());
        request.setAttribute("statesVolumeList", stateListDTO );
        
        boolean isRepBDType = loadData (volumeForm, id, entidad);
        request.setAttribute("isRepBDType", new Boolean(isRepBDType));
        
        return mapping.findForward("success");
    }
    
    public boolean loadData(VolumeForm volumeForm, int id, String entidad) throws Exception
    {
        Volume volume = ObjFactory.createVolume(0,0, entidad);
        volume.load(id, entidad);
        int idRep = volume.getRepId();
        volumeForm.setName(volume.getName());
        Repository rep = ObjFactory.createRepository(0);
        rep.load(idRep, entidad);
        String repositoryName = rep.getName();
        volumeForm.setRepositoryName(repositoryName);
        volumeForm.setId(id);
        volumeForm.setDescription(volume.getRemarks());
        
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        
        Date creationDate = volume.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        int creatorId = volume.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = getUserNameById(creatorId, entidad);
        
        Date updateDate = volume.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = volume.getUpdaterId();
        String updaterName ="";
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = getUserNameById(updaterId, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        volumeForm.setCreationDate(fechaCreacion);
        volumeForm.setCreatorName(creatorName);
        volumeForm.setUpdateDate(fechaModificacion);
        volumeForm.setUpdaterName(updaterName);
        

        // Detalle
        /*
        String path = volume.getPath();
        String maxSize = volume.getMaxSize();
        String actSize = volume.getActSize();
        int numFiles = volume.getNumFiles();
        
        volumeForm.setPath(path);
        volumeForm.setMaxSize(maxSize);
        volumeForm.setActSize(actSize);
        volumeForm.setNumFiles(numFiles);
        */
        String path = volume.getPath();
        boolean is = false;
        if ( volume.getRepType() == VolumeDefs.REP_TYPE_DB ){
        	StringTokenizer tok = new StringTokenizer(path,",");
        	path = tok.nextToken();
        	is = true;
        }
        volumeForm.setPath(path);
        String actSize = ServiceCommon.formatNumberToMb(volume.getActSize()); // Pasar de bytes a Mb
        String maxSize = ServiceCommon.formatNumberToMb(volume.getMaxSize()); // Pasar de bytes a Mb
        volumeForm.setMaxSize(maxSize);
        volumeForm.setActSize(actSize);
        
        // volumeForm.setMaxSize(volume.getMaxSize()); antes 31/03/2003
        // volumeForm.setActSize(volume.getActSize());
        volumeForm.setNumFiles(volume.getNumFiles());

        
        int status = volume.getState();

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
        return is;
    }
    public String getUserNameById(int id, String entidad) throws Exception
    {
        User u = ObjFactory.createUser();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }    

}
