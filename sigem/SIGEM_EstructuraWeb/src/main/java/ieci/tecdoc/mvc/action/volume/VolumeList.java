/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.api.volume.Volumes;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.volume.VolumeDTO;
import ieci.tecdoc.mvc.form.volume.VolumeListForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

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
public class VolumeList extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(VolumeList.class);
    Volumes volumes;
    
    protected ActionForward executeLogic(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        VolumeListForm volumeListForm = (VolumeListForm) form;
        
        
        String order = request.getParameter("order");
        String objTipo = request.getParameter("tipo");
        String objId = request.getParameter("id");
        if (order != null){
            
            changeOrder(order, volumeListForm, entidad);
            
            request.setAttribute("id", volumeListForm.getId());
            request.setAttribute("tipo", volumeListForm.getTipo());
            request.setAttribute("name", volumeListForm.getName());
            return mapping.findForward("success"); 
        }
        
        byte tipo = Byte.parseByte(objTipo);
        int id = Integer.parseInt(objId);
        
        String name = null;
        Repository rep = null;
        ieci.tecdoc.idoc.admin.api.volume.VolumeList vList;
        
        List volList = null;
        if (tipo == Constantes.REPOSITORIES ){
            volList = getVolumeList(id, entidad);
            rep = ObjFactory.createRepository(0);
            rep.load(id, entidad);
            name = rep.getName();
        }
        else{
            vList = ObjFactory.createVolumeList(0);
            vList.load(id, entidad);
            VolumeDTO array[] = getVolumeArray(id, entidad);
            volList = getOrderList(vList,array, entidad);
            
            name = vList.getName();
        }
        
        volumeListForm.setVolumenes(volList);
        
        volumeListForm.setId(objId);
        volumeListForm.setTipo(objTipo);
        volumeListForm.setName(name);
        
        request.setAttribute("id", objId);
        request.setAttribute("tipo", objTipo);
        request.setAttribute("name", name);
        return mapping.findForward("success");
    }

    /**
     * @param order
     * @param volumeListForm
     */
    private void changeOrder(String order, VolumeListForm volumeListForm, String entidad) throws Exception{
        int listId = Integer.parseInt(volumeListForm.getId());
        
        ieci.tecdoc.idoc.admin.api.volume.VolumeList vList = ObjFactory.createVolumeList(0);
        vList.load(listId,entidad);
        StringTokenizer st = new StringTokenizer(order, "/");
        int i = 0;
        List list = volumeListForm.getVolumenes();
        int n = list.size();
        List orderList = new ArrayList(n);
        Map map = new TreeMap();
        while (st.hasMoreTokens())
        {
            int volId = Integer.parseInt(st.nextToken());
            i++;
            vList.setVolumeSortOrder(listId,volId, i, entidad);
            orderList.add(null);
            map.put(new Integer(volId),new Integer(i));
        }
        
        for (i = 0; i < n; i ++){
            VolumeDTO obj = (VolumeDTO) list.get(i);
            int volId = obj.getId();
            int orderPos = ((Integer)(map.get(new Integer(volId)))).intValue();
            orderList.set(orderPos-1, obj);
        }
        volumeListForm.setVolumenes(orderList);
        logger.debug(order);
        //
        
        
    }

    /**
     * @param list
     * @param volList
     */
    private List getOrderList (ieci.tecdoc.idoc.admin.api.volume.VolumeList list, VolumeDTO[] volumeArray, String entidad) throws Exception {
        
        int listId = list.getId();
        int n = volumeArray.length;
        List orderList = new ArrayList(n);
        
        for (int i = n -1 ; i >= 0; i--){
            for (int j = 1; j <= i; j ++ )
            {
                VolumeDTO obj1 = volumeArray[j];
                VolumeDTO obj2 = volumeArray[j-1];
                
                if (compare(obj1,obj2,list, entidad) > 0 ) // si obj1 > obj2
                {
                    VolumeDTO tmp = obj2;
                    volumeArray[j-1] = volumeArray[j];
                    volumeArray[j] = tmp;
                }
            }
        }
        
        for (int i = 0; i < n; i ++)
            orderList.add(volumeArray[i]);
        
        return orderList;
    }
    private int compare (VolumeDTO obj1, VolumeDTO obj2, ieci.tecdoc.idoc.admin.api.volume.VolumeList list, String entidad) throws Exception
    {
        int r = -1;
        int listId = list.getId();
        int volId1 = obj1.getId();
        int volId2 = obj2.getId();
        
        int order1 = list.getVolumeSortOrder(listId,volId1, entidad);
        int order2 = list.getVolumeSortOrder(listId,volId2, entidad);
        
        if (order1 < order2)
            r = 1;
        return r;
        
    }

    /**
     * @param id
     * @return
     */
    private List getVolumeList(int id, String entidad) throws Exception {
        List l = new ArrayList();
        volumes = new Volumes();
        
        volumes.loadByRep(id, entidad);
        int n = volumes.count();
        Volume vol = null;
        
        for (int i = 0; i < n; i ++){
            vol = volumes.getVolume(i);
            int idVol = vol.getId();
            
            String actSize = ServiceCommon.formatNumberToMb(vol.getActSize()); // Pasar de bytes a Mb
            String maxSize = ServiceCommon.formatNumberToMb(vol.getMaxSize()); // Pasar de bytes a Mb
            String name = vol.getName();
            int numFiles = vol.getNumFiles();
            int status = vol.getState();
            String states = getStates(status);
            VolumeDTO obj = new VolumeDTO(idVol, actSize,maxSize,name, numFiles, states);
            l.add(obj);
        }
        
        if (logger.isDebugEnabled())
            logger.debug(l.toString());
        return l;
    }
    public VolumeDTO[] getVolumeArray(int id, String entidad) throws Exception
    {
        volumes = new Volumes();
        volumes.loadByVolumeList(id, entidad);
        int n = volumes.count();
        VolumeDTO array[] = new VolumeDTO[n];
        Volume vol = null;
        for (int i = 0; i < n; i ++){
            vol = volumes.getVolume(i);
            int idVol = vol.getId();
            String actSize = ServiceCommon.formatNumberToMb(vol.getActSize()); // Pasar de bytes a Mb
            String maxSize = ServiceCommon.formatNumberToMb(vol.getMaxSize()); // Pasar de bytes a Mb
            String name = vol.getName();
            int numFiles = vol.getNumFiles();
            int status = vol.getState();
            String states = getStates(status);
            VolumeDTO obj = new VolumeDTO(idVol, actSize,maxSize,name, numFiles, states);
            array[i] = obj;
        }
        return array;
    }

    /**
     * @param status
     * @return
     */
    private String getStates(int status) {
        int i = 0;
        String states = new String();
        
        if ( (status & VolumeDefs.VOL_STAT_NOT_READY) != VolumeDefs.VOL_STAT_NOT_READY ) {
            states = "Disponible";
            }
        else
            states = "No Disponible";
        
        if ( (status & VolumeDefs.VOL_STAT_READONLY) == VolumeDefs.VOL_STAT_READONLY ) {
            states += ", Sólo lectura";
        }
        if ( (status & VolumeDefs.VOL_STAT_FULL ) == VolumeDefs.VOL_STAT_FULL ) {
            states += ", Lleno";            
        }
        
        return states;
    }

}
