package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacmgr.intermediacion.services.Constantes;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Node;

import sun.misc.BASE64Encoder;
import es.ieci.scsp.verifdata.model.dao.Servicio;
import es.ieci.scsp.verifdata.services.ClienteLigero;
import es.ieci.scsp.verifdata.services.ClienteLigeroServiceLocator;

public class ObtenerServiciosIntermediacionAction extends BaseAction {


    
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		
		//----------------------------------------------------------------------------------------------
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));
		IState currentState = managerAPI.currentState(getStateticket(request));
        //----------------------------------------------------------------------------------------------
		
		try{
			X509Certificate certificate = null;
			try{
				String attributeName = "javax.servlet.request.X509Certificate";
				Object obj = request.getAttribute(attributeName);
		      	if (obj instanceof java.security.cert.X509Certificate[]) {
		        	java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
					certificate = certArray[0];
				}
				if (obj instanceof java.security.cert.X509Certificate) {
		        	certificate = (X509Certificate) obj;
				}
		   	}catch(Exception e){
		    	logger.error(e.getMessage());
		   		throw new ISPACInfo(getResources(request).getMessage("exception.certificado.usuario"), true);
		   	}
			
			if (certificate == null){
				logger.error("Error al obtener el certificado de usuario");
		   		throw new ISPACInfo(getResources(request).getMessage("exception.certificado.usuario"), true);
			}
			
			//Validacion del certificado
			ServicioCriptoValidacion servicioCriptoValidacion = LocalizadorServicios.getServicioCriptoValidacion();
			BASE64Encoder encoder = new BASE64Encoder();
	
			String psB64Certificate = encoder.encodeBuffer(certificate.getEncoded());
	
	
			ResultadoValidacion resultado = servicioCriptoValidacion.validateCertificate(psB64Certificate);
			if (ResultadoValidacion.VALIDACION_ERROR.equals(resultado.getResultadoValidacion())) {
		   		throw new ISPACInfo(getResources(request).getMessage("exception.certificado.usuario.invalido"), true);
			}
	
			InfoCertificado infoCertificado = resultado.getCertificado();
	
		   	//Certificado valido
			String nif = infoCertificado.getNif();
	   		String nombre = infoCertificado.getName(); 
	   		
			request.getSession().setAttribute(Constantes.NIF_CERTIFICADO_SELECCIONADO, nif);
			request.getSession().setAttribute(Constantes.NOMBRE_CERTIFICADO_SELECCIONADO, nombre);
	   		
			
	   		IItem ctProcedure = entitiesAPI.getEntity(SpacEntities.SPAC_CT_PROCEDIMIENTOS, state.getPcdId());
			String pcdAdm = ctProcedure.getString("COD_PCD");
			
			//Se obtiene el codigo de procedimiento del Cliente Ligero asociado al expediente actual
			String codPcdSvd = getCodigoProcedimientoSvd(getResources(request), cct, pcdAdm);			
			
			
	
			//Se obtienen los servicios que sólo tienen datos genéricos
			String[] srvDatosGenericos = getServiciosDatosGenericos(getResources(request), cct);
			
			//Se obtienen los servicios disponibles en el Cliente Ligero para el usuario conectado
			Servicio[] servicios = getServicios(nif, codPcdSvd);
			
			if(servicios != null){
				String clienteLigeroWebUrl = ISPACConfiguration.getInstance().get(Constantes.CLIENTE_LIGERO_WEB_URL);
				request.setAttribute(Constantes.CLIENTE_LIGERO_WEB_URL_PARAM, clienteLigeroWebUrl);
				List<ItemBean> list = getListaServicios(servicios, srvDatosGenericos, nif, nombre);
				request.setAttribute("ValueList", list);
				 
				 int taskId = currentState.getTaskId();
				 request.setAttribute("taskId", taskId+"");
				 request.setAttribute("numexp", state.getNumexp()); 
				// Obtiene el decorador
				CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
				BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/serviciosVerificacionDatosformatter.xml"));
				request.setAttribute("Formatter", formatter);
				return mapping.findForward("success");
				
			}
			else{
				throw new ISPACInfo(getResources(request).getMessage("exception.permisos.servicios"), true);
	//			ISPACInfo informacion=new ISPACInfo("El usuario no tiene permisos para ver ningún servicio. Contacte con el administrador.", "",false);
	//			request.getSession().setAttribute("infoAlert", informacion);
	//			IState currentstate = managerAPI.currentState(getStateticket(request));
	//			return NextActivity.refresh(request, mapping, currentstate);
			}
		}catch(ISPACInfo e){
			request.setAttribute("error", e.getMessage());
			return mapping.findForward("error");
		}
	}
		
	
	private List<ItemBean> getListaServicios(Servicio[] servicios, String[] serviciosDatosGenercios, String cif, String nombre) throws ISPACException {

		List<ItemBean> list = new ArrayList<ItemBean>(); 	
		for(int i = 0; i < servicios.length; i++){
			
			//logg.warn("Procedimientos "+vListaServicios[i]);
			Servicio servicio = servicios[i];
			ItemBean itemB = new ItemBean();

			
			itemB.setProperty("SERVICIO", servicio.getCodcertificado());
			itemB.setProperty("NOMBRE", servicio.getDescripcion());
			itemB.setProperty("EMISOR", servicio.getCoreEmisorCertificado());
			itemB.setProperty("DNI", cif);
			itemB.setProperty("NOMBREDNI", nombre);
			
			if (ArrayUtils.contains(serviciosDatosGenercios, servicio.getCodcertificado())){
				itemB.setProperty("DATOSESPECIFICOS", "NO");
			}else{
				itemB.setProperty("DATOSESPECIFICOS", "SI");
			}
			
			list.add(itemB);
		}
		return list;
	}
	
	

	private String[] getServiciosDatosGenericos(MessageResources messageResources, ClientContext cct) throws ISPACException {
		String xml = ConfigurationMgr.getVarGlobal(cct, Constantes.SERVICIOS_SVD_DATOS_GENERICOS);

		if (StringUtils.isEmpty(xml)){
			//throw new ISPACInfo("No se ha encontrado la definición de la variable '"+Constantes.SERVICIOS_SVD_DATOS_GENERICOS+"', que indica los servicios de intermediación que sólo requieren datos genéricos", false);
			throw new ISPACInfo(messageResources.getMessage("exception.servicios.genericos.definicion", new Object[] {Constantes.SERVICIOS_SVD_DATOS_GENERICOS}), false);
		}
		XmlFacade xmlFacade = new XmlFacade(xml);
		List<String> list = xmlFacade.getList("/servicios/servicio");
		return list.toArray(new String[0]);
	}


	private String getCodigoProcedimientoSvd(MessageResources messageResources, ClientContext cct, String pcdAdm) throws ISPACException {
		String xml = ConfigurationMgr.getVarGlobal(cct, Constantes.RELACION_PCDS_SVD);
		
		if (StringUtils.isEmpty(xml)){
			//throw new ISPACInfo("No se ha encontrado la definición de la variable '"+Constantes.RELACION_PCDS_SVD+"', que indica el procedimiento sobre el que realizar la consulta de servicios disponibles al Cliente Ligero de la Plataforma de Intermediación", false);
			throw new ISPACInfo(messageResources.getMessage("exception.pcds.relacion.definicion", new Object[] {Constantes.RELACION_PCDS_SVD}), false);
		}
		
		XmlFacade xmlFacade = new XmlFacade(xml);
		String xpath = "/procedimientos/procedimiento[@pcd_adm='"+pcdAdm+"']";
		String pcdClienteLigero = null;
		Node node = xmlFacade.getSingleNode(xpath);
		if (node != null){
			pcdClienteLigero = xmlFacade.getAttributeValue(node, "pcd_cliente_ligero");
		}
		
		if (StringUtils.isEmpty(pcdClienteLigero)){
			throw new ISPACInfo(messageResources.getMessage("exception.pcd.cliente.igero.novinculado", new Object[] {pcdAdm}), false);
		}
		return pcdClienteLigero;
	}

	private Servicio[] getServicios(String nifFuncionario, String codigoProcedimientoCL) throws ISPACException{
		try{
			String clienteLigeroWS_URL = ISPACConfiguration.getInstance().get(Constantes.CLIENTE_LIGERO_SERVICE_WS_URL);
			ClienteLigeroServiceLocator locator = new ClienteLigeroServiceLocator();
			locator.setClienteLigeroEndpointAddress(clienteLigeroWS_URL);
			ClienteLigero clienteLigero = locator.getClienteLigero();
			
			Servicio[] servicios = clienteLigero.consultaProcedimientoByNIF(nifFuncionario, codigoProcedimientoCL);
		
			return servicios;
		}catch(ServiceException e){
			logger.error(e.getMessage());
			throw new ISPACException("Error al consultar los servicios disponibles al Cliente Ligero del Servicio de Intermediacion. "+e.getMessage());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
			throw new ISPACException("Error al consultar los servicios disponibles al Cliente Ligero del Servicio de Intermediacion. "+e.getMessage());
		}
	}
	
}
