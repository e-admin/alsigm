package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class InsertGroupedOutputRegistryAction extends InsertOutputRegistryAction {

	
	public ActionForward defaultExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IRegisterAPI registerAPI = invesFlowAPI.getRegisterAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		
		// Comprobar si el conector con SICRES está configurado
		if (!registerAPI.existConnector()) {
			throw new ISPACInfo("exception.sicres.notConfigured",false);
		}
		
		// Comprobar si el usuario tiene permisos para registrar de salida
		if (!registerAPI.canRegister(RegisterType.SALIDA)) {
			throw new ISPACInfo("exception.sicres.outputRegisterNotAllowed",false);
		}
		
		// Nombre del campo que contiene los campos de entrada
		EntityForm defaultForm = (EntityForm) form;
		String[] multibox = defaultForm.getMultibox();
		
		
		List<String> documentIds = new ArrayList<String>();

		List<IItem> documentsRegistered = new ArrayList<IItem>();
		List<IItem> documentsEntrada =  new ArrayList<IItem>();
		
		String query = "WHERE ID IN ("+StringUtils.join(multibox, ',')+")";
		IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);
        
		for (Iterator<IItem> it = itemcol.iterator(); it.hasNext();) {
        	IItem itemDoc = it.next();
        	//Si el tipo de registro del documento no es de salida o ya está registrado no se tienen en cuenta en la operacion
        	if (StringUtils.equals(itemDoc.getString("TP_REG"), RegisterType.SALIDA) && itemDoc.get("NREG") == null){
        		documentIds.add(String.valueOf(itemDoc.getKeyInt()));
        	}else{
        		if (itemDoc.get("NREG") != null){
        			documentsRegistered.add(itemDoc);
        		}else if(StringUtils.equals(itemDoc.getString("TP_REG"), RegisterType.ENTRADA)){
        			documentsEntrada.add(itemDoc);
        		}        		
        	}
        }

		if (documentIds.isEmpty()){
        	throw new ISPACInfo("exception.sicres.notDocuments",false);	
        }
		
		request.setAttribute("docIds", StringUtils.join(documentIds.toArray(), ','));
		request.setAttribute("documentsRegistered", documentsRegistered);
		request.setAttribute("documentsEntrada", documentsEntrada);
		request.setAttribute("numExp", managerAPI.currentState(getStateticket(request)).getNumexp());
        	        
		return mapping.findForward("form");
	}
	

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		
		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
   	    IState currentState = managerAPI.currentState(getStateticket(request));
   	    
  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IRegisterAPI registerAPI = invesFlowAPI.getRegisterAPI();
		
		// Comprobar si el conector con SICRES está configurado
		if (!registerAPI.existConnector()) {
			throw new ISPACInfo("exception.sicres.notConfigured",false);
		}
		
		// Comprobar si el usuario tiene permisos para registrar de salida
		if (!registerAPI.canRegister(RegisterType.SALIDA)) {
			throw new ISPACInfo("exception.sicres.notDocuments",false);
		}
		
		EntityForm defaultForm = (EntityForm) form;
		
		// Validar la información del formulario
		ActionMessages errors = defaultForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("form");
		}		
		
		String ids = defaultForm.getProperty("multibox");

		if (StringUtils.isEmpty(ids)){
        	throw new ISPACInfo("exception.sicres.notDocuments",false);	
        }
        
        DocumentInfo[] documentInfo = getDocuments(session, Arrays.asList(StringUtils.split(ids, ',')));
        ThirdPerson destiny = null;

        String idTercero = defaultForm.getProperty("DESTINO_ID");
        if (StringUtils.equals(idTercero, "0")){
        	IItem itemExp = entitiesAPI.getExpedient(currentState.getNumexp());
        	destiny = new ThirdPerson(itemExp.getString("IDTITULAR"), itemExp.getString("IDENTIDADTITULAR"));
        }else{
        	IItem itemTercero = entitiesAPI.getEntity(SpacEntities.SPAC_DT_INTERVINIENTES, Integer.parseInt(idTercero));
        	destiny = new ThirdPerson(itemTercero.getString("ID_EXT"), itemTercero.getString("NOMBRE"));
        }
        
    	String query = "WHERE ID IN ("+ids+")";
        IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);
        // DESTINO
        String[] documentNames = new String[itemcol.toList().size()]; 
        int i = 0;
        for (Iterator<IItem> iterator = itemcol.iterator(); iterator.hasNext(); i++) {
			IItem itemDoc = iterator.next();
			documentNames[i] =itemDoc.getString("NOMBRE");
		}
        
        String summary = StringUtils.join(documentNames, ", ") + "  -  Ref.Exp.: " + currentState.getNumexp();
    	// Generar el registro de salida
        Register register = generateRegister(defaultForm, currentState, documentInfo, destiny, summary);
        RegisterInfo registerInfo = registerAPI.insertRegister(register);
        
        if (registerInfo != null) {
        	
        	setDocumentsData(session, itemcol.toList(), registerInfo,true);
        	
        	register = registerAPI.readRegister(registerInfo);
        	
        	//Se crea el registro en la entidad que contiene el listado de apuntes vinculados con un expediente  
        	RegisterHelper.insertRegistroES(cct, register, destiny, currentState.getNumexp(), currentState.getTaskId());
        	
            // Establecer los datos para la vista
            viewRegister(request, register);
            request.setAttribute("NUMERO_REGISTRO", registerInfo.getRegisterNumber());      		
        } else {
            request.setAttribute(ERROR_VAR, ERROR_REGISTRO);
        }
        
        
		return mapping.findForward("success");
	}
}