/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;
import ieci.tecdoc.idoc.admin.api.volume.VolumeLists;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.common.ListDTO;
import ieci.tecdoc.mvc.dto.volume.StateListDTO;
import ieci.tecdoc.mvc.form.volume.VolumeForm;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

/**
 * @author Antonio María
 *
 */
public class VolumeEdit extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(VolumeEdit.class);
    HttpSession session;
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String entidad=SessionHelper.getEntidad(request);
    	VolumeForm volumeForm = (VolumeForm) form;
        
        String submitted = request.getParameter("submitted");
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);

        session = request.getSession(false);
        
        ListDTO listDTO = new ListDTO();
        listDTO.setList(getVolumeListList(entidad));
        request.setAttribute("listasCollection", listDTO );
        
        
        StateListDTO stateListDTO = new StateListDTO();
        stateListDTO.setStates(getStatesVolumeList());
        request.setAttribute("statesVolumeList", stateListDTO );
        
        if (submitted == null )
        {
            loadData(volumeForm, id, entidad);
        }
        else
        {
            saveDate(volumeForm, entidad);
        }
        return mapping.findForward("success");
    }

    /**
     * @param volumeForm
     */
    
    public List getVolumeListList(String entidad) throws Exception // obtener la lista de listas de volumenes
    {
        List lista = new ArrayList();
        VolumeLists volumeLists = new VolumeLists();

        volumeLists.load(entidad);
        int n = volumeLists.count();
        for (int i = 0; i < n; i ++ )
        {
            ieci.tecdoc.idoc.admin.api.volume.VolumeList obj = volumeLists.getVolumeList(i);
            LabelValueBean tmp= new LabelValueBean(obj.getName(), String.valueOf(obj.getId()));
            lista.add(tmp);
        }
        return lista;
        
    }

    private void saveDate(VolumeForm volumeForm, String entidad) throws Exception{
        int id = volumeForm.getId();
        Volume volume = ObjFactory.createVolume(0,0, entidad);
        volume.load(id, entidad);
        volume.setName(volumeForm.getName());
        String maxSize = volumeForm.getMaxSize();
        volume.setMaxSize(formatNumberToByte(maxSize));
        volume.setRemarks(volumeForm.getDescription());
        String states [] = volumeForm.getStates();
        String s = new String();
        for (int i = 0; i < states.length; i ++ )
            s += states[i];
        int status = 0;

        if (s.indexOf(String.valueOf(VolumeDefs.VOL_STAT_NOT_READY)) == -1)
            status += VolumeDefs.VOL_STAT_NOT_READY;
        
        if (s.indexOf( String.valueOf(VolumeDefs.VOL_STAT_FULL)) != -1)
            status +=  VolumeDefs.VOL_STAT_FULL;
        
        if (s.indexOf(String.valueOf(VolumeDefs.VOL_STAT_READONLY)) != -1)
            status += VolumeDefs.VOL_STAT_READONLY;
        
        asociarAListas(volumeForm.getListAsociated(), id, entidad);
        volume.setState(status);
        volume.store(entidad);
        
        
        
    }

    public void asociarAListas (String listasSeleccionadas[],int idVol, String entidad) throws Exception
    {
        ieci.tecdoc.idoc.admin.api.volume.VolumeList l = ObjFactory.createVolumeList(Defs.NULL_ID);
        
        String [] listasAsignadas = (String [])session.getAttribute("listasAsignadas"); 
        
		String [] listasParaAsignar =  diferencia (listasSeleccionadas, listasAsignadas );
		String [] listasParaEliminar = diferencia (listasAsignadas , listasSeleccionadas);
		
		for (int i = 0; i < listasParaAsignar.length; i ++)
		{
		    int idLista = Integer.parseInt(listasParaAsignar[i]);
		    l.load(idLista, entidad);
		    l.add(idVol, entidad);
		    l.store(entidad);
		}
		for (int i = 0; i < listasParaEliminar.length; i ++)
		{
		    int idLista = Integer.parseInt(listasParaEliminar[i]);
		    l.load(idLista, entidad);
		    l.deleteVolume(idVol, entidad);
		    l.store(entidad);
		}
		
		
		//session.removeAttribute("listasAsignadas");
    }
    /**
     * @param id
     * @param volumeForm
     */
    private void loadData(VolumeForm volumeForm, int id, String entidad) throws Exception{
        Volume volume = ObjFactory.createVolume(0,0, entidad);
        volume.load(id, entidad);
        int idRep = volume.getRepId();
        volumeForm.setId(id);
        volumeForm.setName(volume.getName());
        volumeForm.setPath(volume.getPath());
        String maxSize = volume.getMaxSize();
        
        volumeForm.setMaxSize(formatNumberToMb(maxSize));
        volumeForm.setDescription(volume.getRemarks());
        int state = volume.getState();
        
        Repository rep = ObjFactory.createRepository(0);
        rep.load(idRep, entidad);
        String repositoryName = rep.getName();
        volumeForm.setRepositoryName(repositoryName);
        
        volumeForm.setDescription(volume.getRemarks());
        int status = volume.getState();
        String states [] = volumeForm.getStates();
        int i = 0;
        
        if ( (status & VolumeDefs.VOL_STAT_NOT_READY) != VolumeDefs.VOL_STAT_NOT_READY ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_NOT_READY);
            }
        if ( (status & VolumeDefs.VOL_STAT_FULL ) == VolumeDefs.VOL_STAT_FULL ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_FULL);
        }
        if ( (status & VolumeDefs.VOL_STAT_READONLY) == VolumeDefs.VOL_STAT_READONLY ) {
            states[i++] = String.valueOf(VolumeDefs.VOL_STAT_READONLY);
        }
        String listAsociated[] = getListAsociated(id, entidad);
        session.setAttribute("listasAsignadas", listAsociated);
        volumeForm.setListAsociated(listAsociated);
        
    }
    public String [] getListAsociated(int idVol, String entidad) throws Exception
    {
        
        Volume volume = ObjFactory.createVolume(0,Defs.NULL_ID, entidad);
        ArrayList l = new ArrayList();
        
        volume.loadListIds(idVol, l, entidad);
        String ids[]= new String[l.size()];
        Iterator it = l.iterator();
        int i = 0;
        while (it.hasNext())
        {
            ids[i++] = it.next().toString();
        }
        
        return ids;
    }
    
    
    private String [] diferencia(String[] v1, String[] v2) {
        TreeSet resul = new TreeSet();
        int n = v1.length;
        int i;
        for (i = 0; i < n; i ++)
        {
            String s = v1[i];
            if (s!= null && !busca (s, v2))
                resul.add(s);
            
        }
        int nresul = resul.size();
        String r[] = new String[nresul];
        Iterator it = resul.iterator();
        i = 0; 
        while (it.hasNext())
        {
            r[i] = (String) it.next();
            i++;
        }
               
        return r;
    }
    public boolean busca(String obj, String[] vector)
    {
        boolean enc = false;
        int n = vector.length;
        int i = 0; 
        while (i < n  && !enc)
        {
            if (vector[i] != null && vector[i].equals(obj))
                enc = true;
            i++;
        }
        return enc;
    }
    private String formatNumberToMb(String n) {
        
        double num = Double.parseDouble(n);
	    double tamKb = num * Math.pow(2,-20);
	    
	    String pattern = "###,###.##";
        DecimalFormat formatter = new DecimalFormat(pattern);
        String formatString = formatter.format(tamKb);
        String parseString = null; 
	    try {
            parseString = formatter.parse(formatString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseString;
    }    
    private String formatNumberToByte(String n) {
        
        double num = Double.parseDouble(n);
	    double tamKb = num * Math.pow(2,20);
	    
	    String pattern = "#.##";
        DecimalFormat formatter = new DecimalFormat(pattern);
        String formatString = formatter.format(tamKb);
	    String parseString = null; 
	    try {
            parseString = formatter.parse(formatString).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseString;
    }
}
