/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.volume;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.volume.ListForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class ListNew extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(ListNew.class);    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        ListForm listForm= (ListForm) form;
        HttpSession session = request.getSession();
        String submitted = request.getParameter("submitted");
        String objId = request.getParameter("id");
       // int id = Integer.parseInt(objId);
        int id = 0;
        if (submitted == null){
            //loadData (listForm, id, session);
            return mapping.findForward("ok");
        }
        else{
            ActionMessages messages = new ActionMessages();
            ActionMessage msg = new ActionMessage("message.list.new");
        	messages.add(Globals.MESSAGES_KEY, msg);
            saveMessages(request, messages);
            
            saveData (listForm, id, session, entidad);
            request.setAttribute("tipo", String.valueOf(Constantes.LIST));
            return mapping.findForward("success");
        }
        
        
        
    }
    
    /**
     * @param listForm
     * @param id
     */
    private void saveData(ListForm listForm, int id, HttpSession session, String entidad) throws Exception{
        // TODO Auto-generated method stub
        
        ieci.tecdoc.idoc.admin.api.volume.VolumeList volumeList ;
	    
        volumeList = ObjFactory.createVolumeList(0);
        //volumeList.load(id, true);
        volumeList.setName(listForm.getName());
        volumeList.setRemarks(listForm.getDescription());
        volumeList.store(entidad);
    }

    public void loadData(ListForm listForm, int id , HttpSession session, String entidad) throws Exception
    {
        ieci.tecdoc.idoc.admin.api.volume.VolumeList volumeList = ObjFactory.createVolumeList(0);
        volumeList.load(id, entidad);
        
        listForm.setName(volumeList.getName());
        listForm.setDescription(volumeList.getRemarks());
        session.setAttribute("objList",volumeList);
        
    }
}
