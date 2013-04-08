package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.frmsupervision.AddSupervisedsForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddSupervisedsAction extends BaseAction
{
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

        AddSupervisedsForm addSupervisedsForm = (AddSupervisedsForm) form;
        if(request.getParameterValues("seleccion")!=null){
        	addSupervisedsForm.setUids(request.getParameterValues("seleccion"));
		}
        String[] uids = addSupervisedsForm.getUids();
        String uid = request.getParameter("uid");
      	String captionkey = request.getParameter("captionkey");
        String kindOfSuperv = request.getParameter("kindOfSuperv");
        String supervDirView = request.getParameter("supervDirView");
        String dirUid = request.getParameter("dirUid");
        
        IInvesflowAPI invesflowapi = session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();

        // Obtenemos la otra lista de supervision
        IItemCollection responsibles = null;
        int iKindOfSuperv = Integer.parseInt(kindOfSuperv);
        if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE)
            //responsibles = resp.getTotalModeSuperviseds();
            responsibles = respAPI.getTotalModeSuperviseds(uid);
        else if (iKindOfSuperv == ISecurityAPI.SUPERV_TOTALMODE)
            //responsibles = resp.getFollowedModeSuperviseds();
            responsibles = respAPI.getFollowedModeSuperviseds(uid);

        List lResponsibles = CollectionBean.getBeanList(responsibles);
        Collections.sort(lResponsibles, Responsible.getRespComparator());

        // Comprobamos q los uids no se encuentren en la otra lista de
        // supervision
        String alreadySupervised = checkResponsiblesAlreadySupervised(uids,
                lResponsibles);
        if (alreadySupervised != null)
        {
            String params = "?uid=" + uid + "&captionkey=" + captionkey +"&kindOfSuperv=" + kindOfSuperv + "&supervDirView=" + supervDirView;
            if (StringUtils.isNotEmpty(dirUid)) {
            	params += "&dirUid=" + dirUid;
            }
            params += "&alreadysupervised=" + alreadySupervised;
//          Parámetros para volver
            String view = request.getParameter("view");
            params += "&view=" + view;
          	String uidGroup = request.getParameter("uidGroup");
          	if(StringUtils.isNotEmpty(uidGroup)) {
          		params += "&uidGroup=" + uidGroup;
          	}

            ActionForward af = mapping
                    .findForward("showResponsiblesForSupervision");
            return new ActionForward(af.getName(), af.getPath() + params, true);
            
        } else
        {
            ISecurityAPI api =  invesflowapi.getSecurityAPI();
            if(uids.length>0){
            	 List listaResponsables=new ArrayList();
              	//Buscamos los responsables actualmente asociados 
            	 listaResponsables=api.getSuperviseds(uid, Integer.parseInt(kindOfSuperv));
            int i, j;
            for (i = 0; i < uids.length; i++) {
            	boolean encontrado=false;
            	for(j=0; j<listaResponsables.size() && !encontrado;j++){
            		if(StringUtils.equalsIgnoreCase(uids[i], ((ItemBean)listaResponsables.get(j)).getString("UID_SUPERVISADO"))){
            			encontrado=true;
            		}
            	}
            	if(!encontrado){
                        api.addSupervised(uid, uids[i], Integer.parseInt(kindOfSuperv));
            	}
            }
            }
            
        }

        //cerrar la pagina 
        ActionForward af = mapping.findForward("closeFrame");
        return af;
    }

    private String checkResponsiblesAlreadySupervised(String[] list, List filter)
            throws ISPACException
    {
        ListIterator filterIter = filter.listIterator();
        while (filterIter.hasNext())
        {
            ItemBean filterItem = (ItemBean) filterIter.next();
            String filterUID = filterItem.getString("UID");
            for (int i = 0; i < list.length; i++)
            {
                if (filterUID.equals(list[i]))
                    return list[i];
            }
        }
        return null;
    }
}