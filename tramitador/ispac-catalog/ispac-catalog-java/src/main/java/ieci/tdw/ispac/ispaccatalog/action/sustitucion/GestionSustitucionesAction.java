package ieci.tdw.ispac.ispaccatalog.action.sustitucion;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.frmsustitutions.SustitutionsForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionSustitucionesAction extends BaseDispatchAction {
	
	public ActionForward addFechaSustitucion(ActionMapping mapping, 
											 ActionForm form,
											 HttpServletRequest request, 
											 HttpServletResponse response,
											 SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

	  	String uid = request.getParameter("uid");
	  	if (uid != null) {
	  		request.setAttribute("uid", uid);
	  	}
	  	String uidGroup = request.getParameter("uidGroup");
	  	if (uidGroup != null) {
	  		request.setAttribute("uidGroup", uidGroup);
	  	}

		SustitutionsForm frm = (SustitutionsForm) form;
		
		 // Validar el formulario
		frm.setEntityAppName("AddFechaSutitucion");
        ActionMessages errors = frm.validate(mapping, request);
        if (errors.isEmpty()) {
        	
        	Date fechaInicio = DateUtil.getDate(frm.getProperty("FECHA_INICIO"));
	        Date fechaFin = DateUtil.getDate(frm.getProperty("FECHA_FIN"));
	        
	        try {
				if (DateUtil.compare(fechaInicio, fechaFin) == 1) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("fecha.incorrecta"));
				}
	        } catch (Exception e) {}
			
			if (errors.isEmpty()) {
				
				ISecurityAPI security = session.getAPI().getSecurityAPI();
				
				String descripcion = frm.getProperty("DESCRIPCION");
				String uidSustituido = frm.getProperty("UID_SUSTITUIDO");
				String sustituteIds = frm.getProperty("UIDS_SUSTITUTOS");
				if ((StringUtils.isNotEmpty(uidSustituido)) &&
					(StringUtils.isNotEmpty(sustituteIds))) {
					
					String[] uidSustitutos = sustituteIds.split(",");
					if (uidSustitutos.length > 0) {
						
						// Generar las sustituciones para el período establecido
						security.addSustituciones(uidSustituido, uidSustitutos, fechaInicio, fechaFin, descripcion);
					}
				}
				else {
					// Modificación del período de sustitución
					String idFechSustitucion = frm.getProperty("ID_FECH_SUSTITUCION");
					if (StringUtils.isNotEmpty(idFechSustitucion)) {
						
						security.modifyFechSustitucion(Integer.parseInt(idFechSustitucion), fechaInicio, fechaFin, descripcion);
					}
				}
				
				return mapping.findForward("closeFrame");
	        }
        }
    	saveErrors(request, errors);
    	
    	return mapping.findForward("success_viewAddFecha");
	}
	
	public ActionForward viewFechaSustitucion(ActionMapping mapping, 
			 								  ActionForm form,
			 								  HttpServletRequest request, 
			 								  HttpServletResponse response,
			 								  SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_READ,
				ISecurityAPI.FUNC_PERM_EDIT });

		IInvesflowAPI invesflowapi= session.getAPI();
		IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
		
	  	String uid = request.getParameter("uid");
	  	request.setAttribute("uid", uid);
	  	String uidGroup = request.getParameter("uidGroup");
	  	if (uidGroup != null) {
	  		request.setAttribute("uidGroup", uidGroup);
	  	}
		
		String idFechSustitucion = request.getParameter("regId");
		IItem fechSustitucion = respAPI.getFechSustitucion(idFechSustitucion);
		
		SustitutionsForm frm = (SustitutionsForm) form;
		
		frm.setProperty("ID_FECH_SUSTITUCION", idFechSustitucion);
    	frm.setProperty("FECHA_INICIO", fechSustitucion.getString("FECHA_INICIO"));
        frm.setProperty("FECHA_FIN", fechSustitucion.getString("FECHA_FIN"));
        frm.setProperty("DESCRIPCION", fechSustitucion.getString("DESCRIPCION"));

		return mapping.findForward("success_viewAddFecha");
	}
	
	public ActionForward showAddFechaSustitucion(ActionMapping mapping, 
												 ActionForm form,
												 HttpServletRequest request, 
												 HttpServletResponse response,
												 SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_READ,
				ISecurityAPI.FUNC_PERM_EDIT });

		SustitutionsForm frm = (SustitutionsForm) form;
		frm.setProperty("UID_SUSTITUIDO", request.getParameter("uid"));
		
		return mapping.findForward("success_viewAddFecha");
	}
	
	private ActionForward getForwardVerPropiedades(HttpServletRequest request, 
												   ActionMapping mapping) {
		
        ActionForward frwd = mapping.findForward("vista_sustitucion");
		String path = frwd.getPath();
		String uid =  request.getParameter("uid");
		String view =  request.getParameter("view");
		String uidGroup =  request.getParameter("uidGroup");
		String block =  request.getParameter("block");
		path = path +"?uid="+uid;
		path = path +"&view="+view;
		if (uidGroup!=null)
			path = path +"&uidGroup="+uidGroup;
		path = path +"&block=" + block;
		ActionForward retFrd = new ActionForward();
		retFrd.setPath(path);
		retFrd.setRedirect(true);
		return retFrd;		
	}
	
	public ActionForward showSustitutesHistoric(ActionMapping mapping, 
												ActionForm form,
												HttpServletRequest request, 
												HttpServletResponse response,
												SessionAPI session)throws ISPACException{
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_READ,
				ISecurityAPI.FUNC_PERM_EDIT });

		IInvesflowAPI invesflowapi= session.getAPI();
		IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
		
	  	String captionkey = request.getParameter("captionkey");
	  	request.setAttribute("captionkey", captionkey);

	  	// Extraccion de parametros: uid del supervisor, tipo de supervision
		// y tipo de vista (organizacion o grupos)
	  	String uid = request.getParameter("uid");
	  	request.setAttribute ("uid", uid);
	  	String view = request.getParameter("view");
	  	if (view == null) {
	  		view = "organization";
	  	}
	  	
	  	request.setAttribute("view", view);
	    IItemCollection itemCollSustitutes = respAPI.getSustitutesHistoricals(uid);
        request.setAttribute("SUSTITUTOS_LIST", CollectionBean.getBeanList(itemCollSustitutes));
        
        if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_PERM_EDIT)) {
        	setFormatter(request, "SustitutoFormatter", "/formatters/supervision/substitutionformatter.xml");
        } else {
        	setFormatter(request, "SustitutoFormatter", "/formatters/supervision/substitutionreadonlyformatter.xml");
        }
        
	  	request.setAttribute("action", request.getServletPath().substring(1, request.getServletPath().length()));
	 
      	return mapping.findForward("ver_historicos");
		
	}
	public ActionForward showSustitutes(ActionMapping mapping, 
										ActionForm form,
										HttpServletRequest request, 
										HttpServletResponse response,
										SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_READ,
				ISecurityAPI.FUNC_PERM_EDIT });

		IResponsible directoryResp = null;
		IInvesflowAPI invesflowapi= session.getAPI();
		IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
		
	  	String captionkey = request.getParameter("captionkey");
	  	request.setAttribute("captionkey", captionkey);

	  	// Extraccion de parametros: uid del supervisor, tipo de supervision
		// y tipo de vista (organizacion o grupos)
	  	String uid = request.getParameter("uid");
	  	request.setAttribute ("uid", uid);
	  	String dirUid = request.getParameter ("dirUid");
	  	request.setAttribute ("dirUid", dirUid);
	  	String view = request.getParameter("view");
	  	if (view == null) {
	  		view = "organization";
	  	}
	  	request.setAttribute("view", view);
	  	String uidGroup = request.getParameter("uidGroup");
	  	if (StringUtils.isNotEmpty(uidGroup)) {
	  		request.setAttribute("uidGroup", uidGroup);
	  	}
	  	String regId = request.getParameter("regId");
	  	if (StringUtils.isNotEmpty(regId)) {
	  		request.setAttribute("regId", regId);
	  	}
	
	  	// Formateador para sustitutos (el mismo que supervision)
	    CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
	    BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
	    request.setAttribute("SupervisionFormatter", formatter);
	
	  	// Enviamos directorios: organizacion o grupos:la vista x defecto es
	  	// la de organizacion
	    String supervDirectoryView = request.getParameter("supervDirView");
	  	if (supervDirectoryView == null || supervDirectoryView.equals("organization")) {
	  		
	  		// Calculamos responsable del directorio
	    	if (dirUid == null)
	    		directoryResp = respAPI.getRootResp();
	    	else
	    		directoryResp = respAPI.getResp(dirUid);
	    	
	  		// Ancestros
		  	IItemCollection ancestors = respAPI.getAncestors(directoryResp.getUID());
		  	List ancestorsList = CollectionBean.getBeanList(ancestors);
		  	request.setAttribute("supervAncestors", ancestorsList);
		  	
		  	// Usuarios y departamentos
		  	IItemCollection users = directoryResp.getRespUsers();
		  	IItemCollection orgUnits = directoryResp.getRespOrgUnits();
		  	List usersList = CollectionBean.getBeanList(users);
		  	List orgUnitsList = CollectionBean.getBeanList(orgUnits);
		  	Collections.sort(usersList, Responsible.getRespComparator());
		  	Collections.sort(orgUnitsList, Responsible.getRespComparator());
		  	
		  	// No seleccionables los ya seleccionados (al añadir sustitutos)
		  	if (StringUtils.isNotEmpty(regId)) {
		  		
		  		IItemCollection fm_responsibles = respAPI.getSubstitutes(Integer.parseInt(regId));
		  		List lfm_responsibles = CollectionBean.getBeanList(fm_responsibles);
		  		
		  		setResponsiblesAlreadySelected(usersList, lfm_responsibles);
		  		setResponsiblesAlreadySelected(orgUnitsList, lfm_responsibles);
		  	}
			  	
		  	// Eliminar de la lista el propio usuario que va ser sustituido
		  	eliminarSustituido(usersList, uid);
		  	
		  	// No seleccionable el departamento
		  	setResponsibleAlreadySelected(orgUnitsList, uid);
	
		  	request.setAttribute("supervUsers", usersList);
		  	request.setAttribute("supervOrgunits", orgUnitsList);
		  	request.setAttribute("titleNavigator", "catalog.supervision.titleNavigator.organization");
	  	}
	  	else if (supervDirectoryView.equals("groups")) {
	  		
	    	if (dirUid == null) {
	    		
	    		// Todos los grupos
	      		IItemCollection groups = respAPI.getAllGroups();
	      		List groupsList = CollectionBean.getBeanList(groups);
	      		Collections.sort(groupsList, Responsible.getRespComparator());
	      		
			  	// Eliminar de la lista de sustitutos el grupo que va a ser sustituido
	      		eliminarSustituido(groupsList, uid);
	      		
			  	// No seleccionables los ya seleccionados (al añadir sustitutos)
			  	if (StringUtils.isNotEmpty(regId)) {
			  		
			  		IItemCollection fm_responsibles = respAPI.getSubstitutes(Integer.parseInt(regId));
			  		List lfm_responsibles = CollectionBean.getBeanList(fm_responsibles);
			  		
			  		setResponsiblesAlreadySelected(groupsList, lfm_responsibles);
			  	}
			  	
	      		request.setAttribute("supervGroups", groupsList);
	    	}
	    	else {
	    		// Usuarios de un grupo
	    		IResponsible group = respAPI.getResp(dirUid);
	    		IItemCollection users = group.getRespUsers();
	    		List usersList = CollectionBean.getBeanList(users);
	    		Collections.sort(usersList, Responsible.getRespComparator());
	    		
		  		// Eliminar de la lista de sustitutos el usuario que va ser sustituido
			  	eliminarSustituido(usersList, uid);
			  	
			  	// No seleccionables los ya seleccionados (al añadir sustitutos)
			  	if (StringUtils.isNotEmpty(regId)) {
			  		
			  		IItemCollection fm_responsibles = respAPI.getSubstitutes(Integer.parseInt(regId));
			  		List lfm_responsibles = CollectionBean.getBeanList(fm_responsibles);
			  		
			  		setResponsiblesAlreadySelected(usersList, lfm_responsibles);
			  	}
	
	    	  	request.setAttribute("supervUsers", usersList);
	        	request.setAttribute("groupName", group.getName());
	    	}
	    	request.setAttribute("titleNavigator", "catalog.supervision.titleNavigator.groups");
	  	}
	  	
	  	request.setAttribute("action", request.getServletPath().substring(1, request.getServletPath().length()));
	  	
	  	return mapping.findForward("success_showSustitutos");
	
	}
	
	private void eliminarSustituido(List list, 
									String uid) throws ISPACException {

		ListIterator iter = list.listIterator();
		while (iter.hasNext())
		{
			ItemBean item = (ItemBean) iter.next();
			if (item.getString("UID").equals(uid))
			{
				list.remove(item);
				break;
			}			
		}
	}
	
	private void setResponsiblesAlreadySelected(List list, 
												List filter) throws ISPACException {

		ListIterator filterIter = filter.listIterator();
		
		while (filterIter.hasNext())
		{
			ItemBean filterItem = (ItemBean) filterIter.next();
			String filterUID = filterItem.getString("UID");
			
			setResponsibleAlreadySelected(list, filterUID);
		}
	}

	private void setResponsibleAlreadySelected(List list, 
				   							   String filterUID) throws ISPACException {
	
		ListIterator listIter = list.listIterator();
		while (listIter.hasNext())
		{
			ItemBean item = (ItemBean) listIter.next();
			if (item.getString("UID").equals(filterUID))
			{
				item.setProperty("SELECTED", Boolean.TRUE);
				break;
			}
		}
	}
	
	public ActionForward addSubstitutes(ActionMapping mapping, 
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

		SustitutionsForm sustitutionsForm = (SustitutionsForm) form;
		
		String uid = request.getParameter("uid");
		
		String sustituteIds = ""; 
		if(request.getParameterValues("seleccion")!=null){
			sustitutionsForm.setUids(request.getParameterValues("seleccion"));
		}
		String[] uids = sustitutionsForm.getUids();
        for (int i = 0; i < uids.length; i++) {
            if (i == 0) {
            	sustituteIds = uids[i];
            }
            else {
            	sustituteIds += "," + uids[i];
            }
        }
        
        // Añadir sustitutos a un período ya existente
        String idFechSustitucion = request.getParameter("regId");
        if (StringUtils.isNotBlank(idFechSustitucion )) {
        	
        	ISecurityAPI securityAPI = session.getAPI().getSecurityAPI();
        	
			String[] uidSustitutos = sustituteIds.split(",");
			if (uidSustitutos.length > 0) {
        	
				securityAPI.addSustituciones(Integer.parseInt(idFechSustitucion), uid, uidSustitutos);
			}
			
        	return mapping.findForward("closeFrame");
        }
        else {
        	// Establecer el período de sustitución
        	sustitutionsForm.setProperty("UID_SUSTITUIDO", uid);
        	sustitutionsForm.setProperty("UIDS_SUSTITUTOS", sustituteIds);
        	 
        	return mapping.findForward("success_viewAddFecha");
        }
		
        /*
		// String supervDirView = request.getParameter("supervDirView");
		// String dirUid = request.getParameter("dirUid");
        
        IInvesflowAPI invesflowapi = session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();

        // Obtenemos la otra lista de supervision
        IItemCollection responsibles = respAPI.getSustitutes(uid);

        List lResponsibles = CollectionBean.getBeanList(responsibles);
        Collections.sort(lResponsibles, Responsible.getRespComparator());

        //String params = "&uid=" + uid + "&supervDirView=" + supervDirView;
        //if (StringUtils.isNotEmpty(dirUid)) {
        //	params += "&dirUid=" + dirUid;
        //}

        ISecurityAPI api =  invesflowapi.getSecurityAPI();
        for (int i = 0; i < uids.length; i++)
            api.addSustituto(uid, uids[i]);
        
      	// Parámetros para volver
        //String view = request.getParameter("view");
        //params += "&view=" + view;
      	//String uidGroup = request.getParameter("uidGroup");
      	//if(StringUtils.isNotEmpty(uidGroup)) {
      	//	params += "&uidGroup=" + uidGroup;
      	//}

      	return mapping.findForward("closeFrame");
        //ActionForward ret  = new ActionForward();
        //ret.setPath("gestionSustituciones.do?method=showSustitutes" + params);
        //ret.setRedirect(true);
        //return ret;
        */
	}

	private void deleteSubstitute(  ActionForm form,SessionAPI session ) throws ISPACException{
		
		SustitutionsForm deleteSupervisedsForm = (SustitutionsForm) form;
		String[] multibox = deleteSupervisedsForm.getMultibox();
		
		if (multibox != null && multibox.length > 0 ) {
			
			ISecurityAPI securityAPI = session.getAPI().getSecurityAPI();
			
			securityAPI.deleteSustituciones(multibox);
		}
		
	}
	
	public ActionForward deleteSubstituteHistoric(ActionMapping mapping, 
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response,
			  SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

		deleteSubstitute(form, session);
		return showSustitutesHistoric(mapping, form, request, response, session);
		
	}
	
	public ActionForward deleteSubstitute(ActionMapping mapping, 
										  ActionForm form,
										  HttpServletRequest request,
										  HttpServletResponse response,
										  SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

		deleteSubstitute(form, session);
		ActionForward ret = getForwardVerPropiedades(request, mapping);
		return ret;
	}
	
	/*
	public ActionForward deleteFechSubstitutions(ActionMapping mapping, 
												 ActionForm form,
												 HttpServletRequest request, HttpServletResponse response,
												 SessionAPI session) throws ISPACException {
		
		SustitutionsForm deleteSupervisedsForm = (SustitutionsForm) form;
		String[] multibox = deleteSupervisedsForm.getMultibox();
		
		if (multibox != null && multibox.length > 0 ) {
			
			ISecurityAPI securityAPI = session.getAPI().getSecurityAPI();
			securityAPI.deleteFechasSustitucion(multibox);
		}
		
		ActionForward ret = getForwardVerPropiedades(request, mapping);
		return ret;
	}
	*/
	
}