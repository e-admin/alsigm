package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;
import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
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


public class InsertBatchOutputRegistryAction extends InsertOutputRegistryAction {

	
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
		List<IItem> documentsSinDestino =  new ArrayList<IItem>();
		
		
		String query = "WHERE ID IN ("+StringUtils.join(multibox, ',')+")";
		IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);
        
		for (Iterator<IItem> it = itemcol.iterator(); it.hasNext();) {
        	IItem itemDoc = it.next();
        	//Si el tipo de registro del documento no es de salida o ya está registrado no se tienen en cuenta en la operacion
        	if (StringUtils.equals(itemDoc.getString("TP_REG"), RegisterType.SALIDA) 
        			&& itemDoc.get("NREG") == null
        			&& StringUtils.isNotEmpty(itemDoc.getString("DESTINO"))
        	   ){
        		documentIds.add(String.valueOf(itemDoc.getKeyInt()));
        	}else{
        		if (itemDoc.get("NREG") != null){
        			documentsRegistered.add(itemDoc);
        		}else if(StringUtils.equals(itemDoc.getString("TP_REG"), RegisterType.ENTRADA)){
        			documentsEntrada.add(itemDoc);
        		}else if (StringUtils.isEmpty(itemDoc.getString("DESTINO"))){
        			documentsSinDestino.add(itemDoc);	
        		}
        	}
        }

		if (documentIds.isEmpty()){
        	throw new ISPACInfo("exception.sicres.notDocuments",false);	
        }
		
		request.setAttribute("docIds", StringUtils.join(documentIds.toArray(), ','));

		request.setAttribute("documentsRegistered", documentsRegistered);
		request.setAttribute("documentsEntrada", documentsEntrada);
		request.setAttribute("documentsSinDestino", documentsSinDestino);		
		
		request.setAttribute("numExp", managerAPI.currentState(getStateticket(request)).getNumexp());
		request.setAttribute("action", "insertBatchOutputRegistry.do");
		
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

    	String query = "WHERE ID IN ("+ids+")";
        IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);
		

        List<String> registers_ok = new ArrayList<String>();
        List<String> registers_ko = new ArrayList<String>();
        for (Iterator<IItem> iterator = itemcol.iterator(); iterator.hasNext();) {
			IItem itemDoc = iterator.next();
			try{			
				DocumentInfo[] documentInfo = getDocuments(session, Arrays.asList(new String[]{String.valueOf(itemDoc.getKeyInt())}));
				
				ThirdPerson destiny = new ThirdPerson(itemDoc.getString("DESTINO_ID"), itemDoc.getString("DESTINO"));
				String summary = itemDoc.getString("NOMBRE") + "  -  Ref.Exp.: " + currentState.getNumexp();
				
		        Register register = generateRegister(defaultForm, currentState, documentInfo, destiny, summary);
		        RegisterInfo registerInfo = registerAPI.insertRegister(register);
		        List<IItem> documentList = null;
		        if (registerInfo != null) {
		        	register = registerAPI.readRegister(registerInfo);
		        	
		        	//Se crea el registro en la entidad que contiene el listado de apuntes vinculados con un expediente  
		        	RegisterHelper.insertRegistroES(cct, register, destiny, itemDoc.getString("NUMEXP"), itemDoc.getInt("ID_TRAMITE"));
		        	
		        	registers_ok.add(registerInfo.getRegisterNumber()+ " -> "+ itemDoc.getString("NOMBRE") + " ["+ itemDoc.getString("DESCRIPCION") +"]");
		        	
		        	documentList = new ArrayList<IItem>();
		        	documentList.add(itemDoc);
	
		        	setDocumentsData(session, documentList, registerInfo,false);
		        }else{
		        	registers_ko.add(itemDoc.getString("NOMBRE") + " ["+ itemDoc.getString("DESCRIPCION") +"]" );
		        }
			}catch(Exception e){
				registers_ko.add(itemDoc.getString("NOMBRE") + " ["+ itemDoc.getString("DESCRIPCION") +"]" );
			}
        }
        
        request.setAttribute("REGISTERS_OK", registers_ok);
        request.setAttribute("REGISTERS_KO", registers_ko);
        
		return mapping.findForward("success");	
	}
}
