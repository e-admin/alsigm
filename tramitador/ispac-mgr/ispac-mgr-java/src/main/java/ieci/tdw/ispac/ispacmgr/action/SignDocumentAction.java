package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.sign.ResultadoValidacionCertificado;
import ieci.tdw.ispac.ispaclib.sign.SignCircuitFilter;
import ieci.tdw.ispac.ispaclib.sign.SignDocument;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.ProcessSignProperties;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.SignForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SignDocumentAction extends BaseDispatchAction {

	private IState enterState(HttpServletRequest request, SessionAPI session, HttpServletResponse response) throws ISPACException{
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		// Se cambia el estado de tramitación
		Map params = request.getParameterMap();
		//Al estar en un 'DispacthAction', cambiamos de estado si no nos encontramos ya en el estado de SIGNDOCUMENT
		IState state = managerAPI.currentState(getStateticket(request));
		if (state.getState() != ManagerState.SIGNDOCUMENT)
			state = managerAPI.enterState(getStateticket(request),ManagerState.SIGNDOCUMENT, params);

		storeStateticket(state, response);
		return state;
	}

	public ActionForward selectOption(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {
		//Activamos nuevo estado
		enterState(request, session, response);
		return mapping.findForward("selectOption");
	}

	//===================
	//Firmar ahora

	public ActionForward initSign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		ISignAPI signAPI = session.getAPI().getSignAPI();
		IState state = enterState(request, session, response);

		// Obtener el documento para calcular el HASH
		IItem itemDoc = entitiesAPI.getDocument(state.getEntityRegId());

		// Comprobar si para el documento ya se ha iniciado un circuito de firma
		if (SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA.equals(itemDoc.get("ESTADOFIRMA"))) {
			throw new ISPACInfo("exception.signProcess.inUse.document", true);
		}

		SignDocument signDocument = new SignDocument(itemDoc);
		String hash = signAPI.generateHashCode(signDocument);

		request.setAttribute(ActionsConstants.HASH_CODE, hash);

		return mapping.findForward("sign");
	}

	public ActionForward sign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {

		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		ISignAPI signAPI = session.getAPI().getSignAPI();

		String forward = null;

		IState state = enterState(request, session, response);
		SignForm signForm = (SignForm) form;
		/**TODO Sesion sesionBO = AutenticacionBackOffice.obtenerDatos(request);
        String entityId = sesionBO.getIdEntidad();
        */
		if (signForm.getDocumentId() != null) {

			forward = "refresh";
		}
		else {
			String sign = signForm.getSign();
			if (StringUtils.isEmpty(sign)) {

				forward = "failure";
			}
			else {
				int documentId = state.getEntityRegId();

				IItem itemDoc = entitiesAPI.getDocument(documentId);
				SignDocument signDocument = new SignDocument(itemDoc);
				//TODO   signDocument.setEntityId(entityId);
				signDocument.setIdPcd(state.getPcdId());
				signDocument.setNumExp(state.getNumexp());
				signDocument.addSign(sign);
				String hash = signForm.getHash();
		        String signCertificate = signForm.getSignCertificate();
		        signDocument.setHash(hash);
		        signDocument.addCertificate(signCertificate);
		        try{
			        if(StringUtils.isNotEmpty(signCertificate) && !signCertificate.equalsIgnoreCase("undefined")){
			    		//Se comprubea la validez del certificado
			    		ResultadoValidacionCertificado resultado = signAPI.validateCertificate(signCertificate);
			    		if (resultado.getResultadoValidacion().equals(ResultadoValidacionCertificado.VALIDACION_ERROR)){
			    			throw new ISPACException("El certificado utilizado no es válido. "+ resultado.getMensajeValidacion()); 
			    		}
			        	signAPI.sign(signDocument, true);
			        } else{
			        	forward="failure";
			        }
		        } catch(ISPACInfo e) {
		        	request.setAttribute("typeInvalid", "true");
		        	forward="failure";
		        } catch(ISPACException e) {
		        	request.setAttribute("SIGN_ERROR", e.getMessage());
		        	forward="failure";
		        }

		        if(!StringUtils.equalsIgnoreCase("failure", forward)){
				signForm.setDocumentId(String.valueOf(documentId));
				ItemBean item=new ItemBean(itemDoc);
				item.setProperty("AUTOR_CERT", signAPI.getFirmanteFromCertificado(signCertificate));
				List list = new ArrayList();
				list.add(item);
				request.setAttribute(ActionsConstants.SIGN_DOCUMENT_LIST, list);

			    ///////////////////////////////////////////////
			    // Formateador
				CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
				BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/documentsignedformatter.xml"));
				request.setAttribute(ActionsConstants.FORMATTER, formatter);
				signForm.setDocumentId(null);
				forward = "success";
		        }
			}
		}

		return mapping.findForward(forward);
	}

	 public static X509Certificate getX509Certificate(HttpServletRequest request, String serialNumber)
	    {
	        String attributeName = "javax.servlet.request.X509Certificate";
	        Object obj = request.getAttribute(attributeName);
	        X509Certificate certificate = null;
	        if(obj instanceof X509Certificate[])
	        {
	            X509Certificate certArray[] = (X509Certificate[])obj;
	            for(int i = 0; i < certArray.length; i++)
	                if(certArray[i].getSerialNumber().toString().equals(serialNumber))
	                    return certArray[i];

	            return null;
	        }
	        if(obj instanceof X509Certificate)
	        {
	            certificate = (X509Certificate)obj;
	            if(certificate.getSerialNumber().toString().equals(serialNumber))
	                return certificate;
	            else
	                return null;
	        } else
	        {
	            return null;
	        }
	    }

	//===================
	//Circuito de firmar
	public ActionForward selectSignCircuit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {
		ISignAPI signAPI = session.getAPI().getSignAPI();
		IState state = enterState(request, session, response);

		// Filtro obtener los circuitos de firma genéricos definidos en el sistema y los específicos del procedimiento del expediente
		SignCircuitFilter filter = new SignCircuitFilter();
		filter.setPcdId(state.getPcdId());
		filter.setIdSistema(ProcessSignConnectorFactory.getInstance().getProcessSignConnector().getIdSystem());
		filter.setDefaultPortafirmas(ProcessSignConnectorFactory.getInstance().isDefaultConnector());

		// Obtenemos los circuitos para el procedimiento
		IItemCollection itemcol = signAPI.getCircuits(filter);
		List list = CollectionBean.getBeanList(itemcol);
		request.setAttribute(ActionsConstants.SIGN_CIRCUIT_LIST, list);

		return mapping.findForward("selectCircuit");
	}
	public ActionForward setPropertiesSignCircuit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {
		ISignAPI signAPI = session.getAPI().getSignAPI();
		//Activamos nuevo estado
		IState state = enterState(request, session, response);
		SignForm formulario= (SignForm) form;
		formulario.resetProperties();
		//Obtenemos identificador del circuito de firma a iniciar
		int circuitId = Integer.parseInt(request.getParameter(ActionsConstants.SIGN_CIRCUIT_ID));
		request.setAttribute(ActionsConstants.SIGN_CIRCUIT_ID, circuitId);
		return mapping.findForward("showProperties");
	}

	public ActionForward initSignCircuit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws Exception {
		ISignAPI signAPI = session.getAPI().getSignAPI();
		//Activamos nuevo estado
		IState state = enterState(request, session, response);
		//Obtenemos identificador del circuito de firma a iniciar
		int circuitId = Integer.parseInt(request.getParameter(ActionsConstants.SIGN_CIRCUIT_ID));

		//En el estado actual 'SignDocumentState' en el campo entityRegId estara almacenado el identificador del documento a firmar
		int documentId = state.getEntityRegId();
		SignForm formulario= (SignForm) form;
		ProcessSignProperties properties =new ProcessSignProperties(formulario.getSubject(),
																	formulario.getFstart(),
																	formulario.getFexpiration(),
																	formulario.getContent(),
																	formulario.getLevelOfImportance());
		signAPI.initCircuitPortafirmas(circuitId, documentId, properties);
		return mapping.findForward("success");
	}

	public ActionForward getStateDocumentInProcessSign(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		// Obtenemos el estado de un documentos enviado a un portafirmas que no
		// sea el de por defecto.
		ISignAPI signAPI = session.getAPI().getSignAPI();
		// Activamos nuevo estado
		IState state = enterState(request, session, response);
		String estado = signAPI.getStateDocumentInPortafirmas(state
				.getEntityRegId());
		throw new ISPACInfo(getResources(request).getMessage(
				"state.document.portafirmas")
				+ ":" + estado);

	}
}