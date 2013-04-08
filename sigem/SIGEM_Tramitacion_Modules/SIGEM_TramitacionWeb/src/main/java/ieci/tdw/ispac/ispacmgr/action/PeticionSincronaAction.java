package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.intermediacion.services.Constantes;
import ieci.tdw.ispac.ispacmgr.intermediacion.services.PeticionSincronaDatosGenericos;
import ieci.tdw.ispac.ispacmgr.intermediacion.services.RespuestaRecubrimientoWS;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class PeticionSincronaAction extends BaseDispatchAction {
	
	private static final Logger log = Logger.getLogger(PeticionSincronaAction.class);

	public ActionForward defaultExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		String codigoCertificado = request.getParameter("codigoCertificado");
		String nombreCertificado = request.getParameter("nombreCertificado");
		request.setAttribute("codigoCertificado", codigoCertificado);
		request.setAttribute("nombreCertificado", nombreCertificado);
		
		if (log.isDebugEnabled()){
			log.debug("Seleccioando el servicio '("+codigoCertificado+")" + nombreCertificado + "' para realizar la consulta al servicio de Intermediacion");
		}
		
		return mapping.findForward("form");
	}


	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		EntityForm entityForm = (EntityForm) form;
		
		String codigoCertificado = entityForm.getProperty("codigoCertificado");
		String nombreCertificado = entityForm.getProperty("nombreCertificado");
		String finalidad = entityForm.getProperty("finalidad");
		String nifTitular = entityForm.getProperty("nifTitular");
		String consentimiento = entityForm.getProperty("consentimiento");
		
		PeticionSincronaDatosGenericos clienteRecubrimiento = new PeticionSincronaDatosGenericos();
		clienteRecubrimiento.setCodigoServicio(codigoCertificado);
		clienteRecubrimiento.setNombreServicio(nombreCertificado);
		clienteRecubrimiento.setFinalidad(finalidad);
		clienteRecubrimiento.setNifTitular(nifTitular);
		clienteRecubrimiento.setConsentimiento(consentimiento);
		
		IState state = ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext()).currentState(getStateticket(request));
		String numExp = state.getNumexp();
		clienteRecubrimiento.setNumExp(numExp);
		
		//Obtencion de codigo y nombre del procedimiento desde el que se realiza la consulta 		
		IProcedureAPI procedureAPI = session.getClientContext().getAPI().getProcedureAPI();
		IItem procedure = procedureAPI.getProcedureById(state.getPcdId());
		clienteRecubrimiento.setCodigoProcedimiento(procedure.getString("CTPROCEDIMIENTOS:COD_PCD"));
		clienteRecubrimiento.setNombreProcedimiento(procedure.getString("CTPROCEDIMIENTOS:NOMBRE"));
		
		//Departamento al que pertenece el usuario conectado que realiza la consulta
		clienteRecubrimiento.setUnidadTramitadora(session.getClientContext().getUser().getOrgUnit().getName());
		
		
		String nifFuncionario = (String)request.getSession().getAttribute(Constantes.NIF_CERTIFICADO_SELECCIONADO);
		String nameFuncionario  = (String)request.getSession().getAttribute(Constantes.NOMBRE_CERTIFICADO_SELECCIONADO);
		
		clienteRecubrimiento.setNifFuncionario(nifFuncionario);
		clienteRecubrimiento.setNameFuncionario(nameFuncionario);

		String idSolicitante = ConfigurationMgr.getVarGlobal(session.getClientContext(), Constantes.INTERMEDIACION_ID_SOLICITANTE);
		String nameSolicitante = ConfigurationMgr.getVarGlobal(session.getClientContext(), Constantes.INTERMEDIACION_NOMBRE_SOLICITANTE);
		
		if (StringUtils.isEmpty(idSolicitante) || StringUtils.isEmpty(nameSolicitante)){
			throw new ISPACException(getResources(request).getMessage("exception.organismo.datos"));
		}
			
				
		clienteRecubrimiento.setIdSolicitante(idSolicitante);
		clienteRecubrimiento.setNameSolicitante(nameSolicitante);
		
		RespuestaRecubrimientoWS res =  clienteRecubrimiento.invoke(session, request);
		request.setAttribute("RespuestaRecubrimientoWS_CodigoEstado", res.getCodigoEstado());
		request.setAttribute("RespuestaRecubrimientoWS_LiteralError", res.getLiteralError());
		
		if(StringUtils.equals(RespuestaRecubrimientoWS.CODIGO_ESTADO_OK, res.getCodigoEstado())){
			storeDocument(session, request, res, codigoCertificado, nombreCertificado);
		}		

		return mapping.findForward("success");
	}


	private void storeDocument(SessionAPI session, HttpServletRequest request, RespuestaRecubrimientoWS res, String codigoCertificado, String nombreCertificado) throws ISPACException {
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));
		IGenDocAPI gendocAPI = cct.getAPI().getGenDocAPI();
		
		Object connectorSession = null;
		try {
			byte pdf[] = res.getPdf();
			if (pdf == null){
				pdf = new sun.misc.BASE64Decoder().decodeBuffer(res.getPdfBase64());
			}
			
			//Guarda el documento obtenido en el tramite desde el que se realiza la invocacion
			int taskId = currentState.getTaskId();
			IItem newdoc = gendocAPI.createTaskDocument(taskId, 0);
			String rutaFileName = FileTemporaryManager.getInstance().getFileTemporaryPath() +  "/" + FileTemporaryManager.getInstance().newFileName(".pdf");
			FileOutputStream  fos = new FileOutputStream(rutaFileName);
			fos.write(pdf);
			fos.close();
			fos.flush();
			
			File file = new File(rutaFileName);
			
			FileInputStream in = new FileInputStream(file);
			int docId = newdoc.getInt("ID");
			connectorSession = gendocAPI.createConnectorSession();
			IItem entityDoc = gendocAPI.attachTaskInputStream(connectorSession, taskId, docId, in, (int)file.length(), "application/pdf", nombreCertificado);
			entityDoc.set("NOMBRE", codigoCertificado+".pdf");
			entityDoc.set("EXTENSION", "pdf");
			entityDoc.store(cct);
			file.delete();		
			
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}finally {
			if (connectorSession != null) {
				gendocAPI.closeConnectorSession(connectorSession);
			}
    	}			
		
	}
}
