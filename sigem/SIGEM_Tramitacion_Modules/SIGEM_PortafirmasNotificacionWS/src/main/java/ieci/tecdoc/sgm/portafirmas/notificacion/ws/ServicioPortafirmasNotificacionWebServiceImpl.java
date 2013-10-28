package ieci.tecdoc.sgm.portafirmas.notificacion.ws;

import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.tram.sign.PortafirmasMinhapProcessSignConnector;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import juntadeandalucia.cice.pfirma.advice.v2_0.AdviceService;
import juntadeandalucia.cice.pfirma.advice.v2_0.PfirmaException;
import juntadeandalucia.cice.pfirma.type.v2.Authentication;
import juntadeandalucia.cice.pfirma.type.v2.Request;
import juntadeandalucia.cice.pfirma.type.v2.Signature;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServicioPortafirmasNotificacionWebServiceImpl
		implements AdviceService {

	private static final Logger logger = Logger.getLogger(ServicioPortafirmasNotificacionWebServiceImpl.class);

	/** Nombre del servicio. */
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_PROCESSING;

	/**
    * Primer firmante
    */
	public static final String SIGN_TYPE_FIRSTSIGNER = "PRIMER FIRMANTE";

	/**
    * Estado aceptado
    */
	public static final String ACEPTED_STATUS = "ACEPTADO";

	/**
    * Permite obtener el servicio de tramitacion
    * @return servicio de tramitacion
    * @throws SOAPException
    * @throws SigemException
    */
	private ServicioTramitacion getServicioTramitacion() throws SOAPException, SigemException {
		StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append("SIGEM.API");
		return LocalizadorServicios.getServicioTramitacion(sbImpl.toString());
	}

	/**
    * Comprueba si una peticion esta finalizada. Una peticion esta finalizada en dos casos:
    *
    * - Si es primer firmante: uno de los firmantes ha firmado
    * - Si es paralela o en cascada: han firmado todos los firmantes
    *
    * @param request peticion a comprobar
    * @return booleano indicando si la peticion esta finalizada
    */
	private boolean checkFinishedRequest(Request request){

		boolean ret = false;

		if (ACEPTED_STATUS.equals(request.getRequestStatus().getValue().toString())){
			ret = true;
		}

//		if ((request.getSignLineList())!=null&&(request.getSignLineList().getValue()!=null)){
//			if (SIGN_TYPE_FIRSTSIGNER.equals(request.getSignType().getValue().value())){
//				for (SignLine signLine:request.getSignLineList().getValue().getSignLine()){
//					Signer signer = signLine.getSignerList().getValue().getSigner().get(0);
//					if ("FIRMADO".equals(signer.getState().getValue().getIdentifier().toString())){
//						ret = true;
//						break;
//					}
//				}
//			} else {
//				ret = true;
//				for (SignLine signLine:request.getSignLineList().getValue().getSignLine()){
//					Signer signer = signLine.getSignerList().getValue().getSigner().get(0);
//					if (!"FIRMADO".equals(signer.getState().getValue().getIdentifier().toString())){
//						ret = false;
//						break;
//					}
//				}
//			}
//		}

		return ret;
	}

	/**
    * {@inheritDoc}
    * @see juntadeandalucia.cice.pfirma.advice.v2_0.AdviceService#updateRequestStatus(juntadeandalucia.cice.pfirma.type.v2.Authentication, juntadeandalucia.cice.pfirma.type.v2.Request, java.util.List)
    */
	public Boolean updateRequestStatus(Authentication authentication,
			Request request, List<Signature> signature) throws PfirmaException {

		if (logger.isInfoEnabled()){
			logger.info("ServicioPortafirmasNotificacionWebServiceImpl.updateRequestStatus - begin - id:"+request.getIdentifier().getValue());
		}

		if (checkFinishedRequest(request)){

			if (logger.isInfoEnabled()){
				logger.info("ServicioPortafirmasNotificacionWebServiceImpl.updateRequestStatus - finished request - id:"+request.getIdentifier().getValue());
			}

			String reference = request.getReference().getValue();
			String [] referenceValues = reference.split(PortafirmasMinhapProcessSignConnector.REFERENCE_SEPARATOR);
			String numExp = referenceValues[0];
			String documentId = referenceValues[1];
			String entidadId = referenceValues[2];

			HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

			try {
				setAuditContext(servletRequest);
				ServicioTramitacion servicioTramitacion = getServicioTramitacion();
				servicioTramitacion.recibirDocumentoFirmado(entidadId, numExp, documentId);
			} catch (Exception e) {
				logger.error(e);
				return Boolean.FALSE;
			}
		}

		if (logger.isInfoEnabled()){
			logger.info("ServicioPortafirmasNotificacionWebServiceImpl.updateRequestStatus - end - id:"+request.getIdentifier().getValue());
		}

		return Boolean.TRUE;
	}

	/**
    * Establecer el contexto de auditoria para la tramitacion de expedientes.
    *
    * @param request
    */
	private void setAuditContext(HttpServletRequest request) {

		// Auditoria
		AuditContext auditContext = new AuditContext();

		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser("PORTAFIRMAS_NOTIFICACION_WS");
		auditContext.setUserId("SYSTEM");

		// Aniadir en el ThreadLocal el objeto AuditContext
		AuditContextHolder.setAuditContext(auditContext);
	}
}
