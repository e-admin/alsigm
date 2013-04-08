package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sign.ResultadoValidacionCertificado;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchSignForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BatchSignAction extends BaseDispatchAction {

	private IState enterState(HttpServletRequest request, 
							  SessionAPI session, 
							  HttpServletResponse response) throws ISPACException {
		
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		// Se cambia el estado de tramitación
		Map params = request.getParameterMap();
		//Al estar en un 'DispacthAction', cambiamos de estado si no nos encontramos ya en el estado de BATCHSIGNDOCUMENT  
		IState state = managerAPI.currentState(getStateticket(request));
		if (state.getState() != ManagerState.BATCHSIGNLIST)
			state = managerAPI.enterState(getStateticket(request),ManagerState.BATCHSIGNLIST, params);
		storeStateticket(state, response);
		
		return state;
	}
	
	private void setMenu(ClientContext cct, 
						 IState state, 
						 HttpServletRequest request) throws ISPACException {
		
		request.setAttribute("menus", MenuFactory.getSignedDocumentListMenu(cct, state, getResources(request)));		
	}
	
	public ActionForward calculateDocumentsHash(ActionMapping mapping, 
												ActionForm form, 
												HttpServletRequest request, 
												HttpServletResponse response, 
												SessionAPI session) throws Exception {
		
		//Activamos nuevo estado
		IState state = enterState(request, session, response);
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		ISignAPI signAPI = session.getAPI().getSignAPI();
		
		BatchSignForm batchSignForm = ((BatchSignForm)form);
		//en 'ids' se recogen los identificados de los pasos de circuitos de firma instanciados que se han seleccionado
		String[] ids = batchSignForm.getMultibox();

		//Se calcula el hash de los documentos a firmar
		IItem itemDoc;
		IItem itemStep;
		String hash;
		SignDocument signDocument;
		String[] hashs = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			itemStep = signAPI.getStepInstancedCircuit(Integer.parseInt((String)ids[i]));
			itemDoc = entitiesAPI.getDocument(itemStep.getInt("ID_DOCUMENTO"));
			signDocument = new SignDocument(itemDoc);
			hash = signAPI.generateHashCode(signDocument);
			
			hashs[i] = hash;
		}
		batchSignForm.setHashs(hashs);
		
		setMenu(session.getClientContext(), state, request);
		
		return mapping.findForward("batchSign");	
	}	
	
	public ActionForward batchSign(ActionMapping mapping, 
								   ActionForm form, 
								   HttpServletRequest request, 
								   HttpServletResponse response, 
								   SessionAPI session) throws Exception {
		
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		ISignAPI signAPI = session.getAPI().getSignAPI();
		
		IState state = enterState(request, session, response);		

		BatchSignForm batchSignForm = (BatchSignForm) form;
		
		// Identificadores de los pasos de circuito de firma asociados a los documentos a firmar
		String[] stepIds = batchSignForm.getMultibox();
		if (stepIds == null) {
			
			return mapping.findForward("refresh");
		}
		
		// Firmas del hash de los documentos
		String[] signs = batchSignForm.getSigns().split("!");
		
		if (((signs.length == 1) &&  (StringUtils.isEmpty(signs[0]))) || 
			(signs.length != stepIds.length)) {
			
			throw new ISPACInfo(getResources(request).getMessage("error.message.numero.firmas.incorrecta"));
		}
		//TODO : BORRAR O VERFIFICAR--> Pdt para cuando se añada el codigo de cotejo
		/*Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
        String entityId = sesionBO.getIdEntidad();*/
		
		try {
			
			//Se comprubea la validez del certificado
			ResultadoValidacionCertificado resultado = signAPI.validateCertificate(batchSignForm.getSignCertificate());
			if (resultado.getResultadoValidacion().equals(ResultadoValidacionCertificado.VALIDACION_ERROR)){
				throw new ISPACException("El certificado utilizado no es válido. "+ resultado.getMensajeValidacion()); 
			}			
			
			// Firmar los documentos asociados a los pasos de firma
			List signDocuments = signAPI.batchSignSteps(stepIds, signs, batchSignForm.getSignCertificate());
			String autor_cert=signAPI.getFirmanteFromCertificado(batchSignForm.getSignCertificate());
			
			// Estados de firma para incluir el sustituto en la lista enviada a la vista
			IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_TBL_008, "");
			Map mapSignStates = CollectionBean.getBeanMap(itemcol, "VALOR");
			
			// Establecer el estado de firma para cada documento
			List ltSignsDocuments = new ArrayList();
			
			Iterator it = signDocuments.iterator();
			while (it.hasNext()) {
				
				SignDocument signDocument = (SignDocument) it.next();
				//TODO: BORRAR O VERIFICAR  signDocument.setEntityId(entityId);
				IItem document = signDocument.getItemDoc();
				// Incluir el sustituto del estado de firma
				ItemBean itemBean = new ItemBean(document);
				itemBean.setProperty("AUTOR_CERT", autor_cert);
				itemBean.setProperty("SUSTITUTO_ESTADOFIRMA", ( (ItemBean) mapSignStates.get(document.getString("ESTADOFIRMA"))).getProperty("SUSTITUTO"));
				ltSignsDocuments.add(itemBean);
			}
			
			// Lista de documentos firmados para la vista
			request.setAttribute(ActionsConstants.SIGN_DOCUMENT_LIST, ltSignsDocuments);
			
			batchSignForm.clean();
			
		    ///////////////////////////////////////////////
		    // Formateador
			CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/documentsignedlistformatter.xml"));
			request.setAttribute(ActionsConstants.FORMATTER, formatter);
			
			setMenu(session.getClientContext(), state, request);
			
			return mapping.findForward("success");
			
		} catch (Exception e) {
			logger.error("Error en la firma de documentos en un circuito de firma", e);
			throw new ISPACInfo(getResources(request).getMessage("sign.message.error.failure") + " " + e.getMessage(), false);
		}
	}
	
}