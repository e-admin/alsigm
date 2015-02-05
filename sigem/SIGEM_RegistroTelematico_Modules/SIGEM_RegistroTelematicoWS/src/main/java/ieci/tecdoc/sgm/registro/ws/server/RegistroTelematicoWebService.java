package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.utils.InetUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.SOAPException;

import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RegistroTelematicoWebService {

	private static final Logger logger = Logger.getLogger(RegistroTelematicoWebService.class);

	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_EREGISTRY;

	/**
	 * Establecer el contexto de auditoría para la tramitación de expedientes.
	 * La ip del cliente que hace la llamada al WS.
	 *
	 */
	protected void setAuditContext() {

		try {
			// Auditoría
			AuditContext auditContext = new AuditContext();

			auditContext.setUserHost("REMOTE WS CLIENT");
			auditContext.setUserIP( getUserIP());
			auditContext.setUser("REGISTRO_TELEMATICO_WS");
			auditContext.setUserId("SYSTEM");

			// Añadir en el ThreadLocal el objeto AuditContext
			AuditContextHolder.setAuditContext(auditContext);
		} catch (Exception e) {
			logger.error("Error al intentar establecer el contexto para la auditoría",e);
		}
	}

	/**
	 * Obtener la IP para la auditoría.
	 *
	 * @return IP remota o en caso de no obtenerla, la IP local.
	 */
	protected String getUserIP() {

		String userIP = "";

		MessageContext messageContext = MessageContext.getCurrentContext();
		if (messageContext != null) {
			userIP = (String) messageContext.getProperty(Constants.MC_REMOTE_ADDR);
		}

		if (StringUtils.isBlank(userIP)) {
			try {
				userIP = InetUtils.getLocalHostAddress();
			} catch (Exception e) {
				logger.debug("Error al obtener la IP de LocalHost", e);
			}
		}

		return userIP;
	}

	private ServicioRegistroTelematico getServicioRegistroTelematico() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioRegistroTelematico();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioRegistroTelematico(sbImpl.toString());
		}
	}

	public StringB64 crearPeticionRegistro (String sessionId, RegistroPeticion requestInfo, String idiom, String organismo, String numeroExpediente, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().crearPeticionRegistro(sessionId, getRegistroPeticionServicio(requestInfo), idiom, organismo, numeroExpediente, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error en creacion de peticion de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en creacion de peticion de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado en creacion de peticion de registro.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}


	public RetornoCadena establecerDocumentosSubsanacion (String sessionId, Documentos procedureDocuments,
			PeticionDocumentos requestDocuments, Entidad entidad) {
		try {
			setAuditContext();
			RetornoCadena retornoCadena = new RetornoCadena();
			retornoCadena.setCadena(getServicioRegistroTelematico().establecerDocumentosSubsanacion (sessionId,
						getDocumentosServicio(procedureDocuments), getPeticionDocumentosServicio(requestDocuments), entidad));
			return (RetornoCadena)ServiciosUtils.completeReturnOK(retornoCadena);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al establecer documentos en subsanacion.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al establecer documentos en subsanacion.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al establecer documentos en subsanacion.", e);
			return (RetornoCadena)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoCadena()));
		}
	}


	public StringB64 registrar(String sessionId, StringB64 registryRequest,
				String additionalInfo, String idiom, String oficina, String plantilla, String certificado, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().registrar (sessionId, getStringB64Servicio(registryRequest),
							additionalInfo, idiom, oficina, plantilla, certificado, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al registrar la peticion.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}

	public StringB64 registrarConJustificante(String sessionId, StringB64 registryRequest,
			String additionalInfo, String idiom, String oficina, StringB64 plantilla, String certificado, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().registrar (sessionId, getStringB64Servicio(registryRequest),
							additionalInfo, idiom, oficina, getStringB64Servicio(plantilla), certificado, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al registrar la peticion.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}

	public StringB64 registrarTelematicoAndIniciarExpediente(String sessionId, StringB64 registryRequest, String additionalInfo,
			String idiom, String oficina, String plantilla, String certificado, String tramiteId, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().registrarTelematicoAndIniciarExpediente(sessionId, getStringB64Servicio(registryRequest),
							additionalInfo, idiom, oficina, plantilla, certificado, tramiteId, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al registrar la peticion.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}

	public StringB64 registrarTelematicoConJustificanteAndIniciarExpediente(String sessionId, StringB64 registryRequest, String additionalInfo,
			String idiom, String oficina, StringB64 plantilla, String certificado, String tramiteId, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().registrarTelematicoAndIniciarExpediente(sessionId, getStringB64Servicio(registryRequest),
							additionalInfo, idiom, oficina, getStringB64Servicio(plantilla), certificado, tramiteId, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al registrar la peticion.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al registrar la peticion.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}

	public RetornoServicio deshacerRegistro (String sessionId, String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			getServicioRegistroTelematico().deshacerRegistro(sessionId, registryNumber, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (RegistroTelematicoException e) {
			logger.error("Error al deshacer un registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al deshacer un registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al deshacer un registro.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoCadena obtenerNumeroRegistro (Entidad entidad) {
		try {
			setAuditContext();
			RetornoCadena retornoCadena = new RetornoCadena();
			retornoCadena.setCadena(getServicioRegistroTelematico().obtenerNumeroRegistro(entidad));
			return (RetornoCadena)ServiciosUtils.completeReturnOK(retornoCadena);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener número de registro.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener número de registro.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error al obtener número de registro.", e);
			return (RetornoCadena)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoCadena()));
		}
	}


	public RetornoServicio eliminarDocumentosTemporales (String sessionId, String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			getServicioRegistroTelematico().eliminarDocumentosTemporales(sessionId, registryNumber, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (RegistroTelematicoException e) {
			logger.error("Error al eliminar documentos temporales.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar documentos temporales.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error al eliminar documentos temporales.", e);
			return ServiciosUtils.createReturnError();
		}
	}


	public Registros query (String sessionId, RegistroConsulta query, Entidad entidad) {
		try {
			setAuditContext();
			Registros registros = getRegistrosWS(
					getServicioRegistroTelematico().query (sessionId, getRegistroConsultaServicio(query), entidad)
						);
			return (Registros)ServiciosUtils.completeReturnOK(registros);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al realizar consulta.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al realizar consulta.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al realizar consulta.", e);
			return (Registros)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Registros()));
		}
	}

	public Registros obtenerRegistrosParaMostrar(RegistroConsulta query, Entidad entidad) {
		try {
			setAuditContext();
			Registros registros = getRegistrosWS(
					getServicioRegistroTelematico().obtenerRegistrosParaMostrar(getRegistroConsultaServicio(query), entidad)
						);
			return (Registros)ServiciosUtils.completeReturnOK(registros);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener registros a mostrar.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener registros a mostrar.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener registros a mostrar.", e);
			return (Registros)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Registros()));
		}
	}

	public Registro obtenerRegistro(String sessionId, String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			Registro registro = getRegistroWS(
					getServicioRegistroTelematico().obtenerRegistro(sessionId, registryNumber, entidad)
						);
			return (Registro)ServiciosUtils.completeReturnOK(registro);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener registro.", e);
			return (Registro)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registro()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener registro.", e);
			return (Registro)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registro()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener registro.", e);
			return (Registro)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Registro()));
		}
	}


	public ContenedorDocumentos obtenerDatosDocumentosRegistro (String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			ContenedorDocumentos contenedorDocumentos = getContenedorDocumentosWS(
					getServicioRegistroTelematico().obtenerDatosDocumentosRegistro(registryNumber, entidad)
						);
			return (ContenedorDocumentos)ServiciosUtils.completeReturnOK(contenedorDocumentos);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener datos de documentos.", e);
			return (ContenedorDocumentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ContenedorDocumentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener datos de documentos.", e);
			return (ContenedorDocumentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ContenedorDocumentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener datos de documentos.", e);
			return (ContenedorDocumentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ContenedorDocumentos()));
		}
	}


	public StringB64 obtenerJustificanteRegistro(String sessionId, String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().obtenerJustificanteRegistro(sessionId, registryNumber, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener justificante de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener justificante de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener justificante de registro.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}


	public StringB64 obtenerPeticionRegistro (String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().obtenerPeticionRegistro (registryNumber, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener peticion de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener peticion de registro.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener peticion de registro.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}

	public Registros obtenerRegistrosConsolidados(Entidad entidad) {
		try {
			setAuditContext();
			Registros registros = getRegistrosWS(
					getServicioRegistroTelematico().obtenerRegistrosConsolidados (entidad)
						);
			return (Registros)ServiciosUtils.completeReturnOK(registros);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener registros consolidados.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener registros consolidados.", e);
			return (Registros)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Registros()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener registros consolidados.", e);
			return (Registros)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Registros()));
		}
	}


	public RetornoLogico tieneDocumentos (String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			RetornoLogico retornoLogico = getRetornoLogicoWS(
					getServicioRegistroTelematico().tieneDocumentos (registryNumber, entidad)
						);
			return (RetornoLogico)ServiciosUtils.completeReturnOK(retornoLogico);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al comprobar si hay documentos asociados al registro.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al comprobar si hay documentos asociados al registro.", e);
			return (RetornoLogico)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoLogico()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al comprobar si hay documentos asociados al registro.", e);
			return (RetornoLogico)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoLogico()));
		}
	}


	public ByteArrayB64 obtenerContenidoDocumento (String sessionId, String registryNumber, String code, Entidad entidad) {
		try {
			setAuditContext();
			ByteArrayB64 byteArrayB64 = getByteArrayB64WS(
					getServicioRegistroTelematico().obtenerContenidoDocumento (sessionId, registryNumber, code, entidad)
						);
			return (ByteArrayB64)ServiciosUtils.completeReturnOK(byteArrayB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener contenido de documento.", e);
			return (ByteArrayB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ByteArrayB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener contenido de documento.", e);
			return (ByteArrayB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ByteArrayB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener contenido de documento.", e);
			return (ByteArrayB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ByteArrayB64()));
		}
	}


	public StringB64 obtenerDocumento (String registryNumber, String code, Entidad entidad) {
		try {
			setAuditContext();
			StringB64 stringB64 = getStringB64WS(
					getServicioRegistroTelematico().obtenerDocumento (registryNumber, code, entidad)
						);
			return (StringB64)ServiciosUtils.completeReturnOK(stringB64);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener contenido de documento.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener contenido de documento.", e);
			return (StringB64)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new StringB64()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener contenido de documento.", e);
			return (StringB64)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new StringB64()));
		}
	}


	public RetornoServicio establecerEstadoRegistro (String registryNumber, String status, Entidad entidad) {
		try {
			setAuditContext();
			getServicioRegistroTelematico().establecerEstadoRegistro(registryNumber, new Integer(status).intValue(), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (RegistroTelematicoException e) {
			logger.error("Error al establecer el estado del registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al establecer el estado del registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error al establecer el estado del registro.", e);
			return ServiciosUtils.createReturnError();
		}
	}


	public RegistroDocumentos obtenerDocumentosRegistro(String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			RegistroDocumentos registroDocumentos = getRegistroDocumentosWS(
					getServicioRegistroTelematico().obtenerDocumentosRegistro (registryNumber, entidad)
						);
			return (RegistroDocumentos)ServiciosUtils.completeReturnOK(registroDocumentos);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener documentos de registro.", e);
			return (RegistroDocumentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documentos de registro.", e);
			return (RegistroDocumentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documentos de registro.", e);
			return (RegistroDocumentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RegistroDocumentos()));
		}
	}

	public RegistroDocumento obtenerDocumentoRegistro (String sessionId, String registryNumber, String code, Entidad entidad) {
		try {
			setAuditContext();
			RegistroDocumento registroDocumento = getRegistroDocumentoWS(
					getServicioRegistroTelematico().obtenerDocumentoRegistro (sessionId, registryNumber, code, entidad)
						);
			return (RegistroDocumento)ServiciosUtils.completeReturnOK(registroDocumento);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al obtener documento de registro.", e);
			return (RegistroDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener documento de registro.", e);
			return (RegistroDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener documento de registro.", e);
			return (RegistroDocumento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RegistroDocumento()));
		}
	}


	public RegistroDocumento insertarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) {
		try {
			setAuditContext();
			RegistroDocumento registroDocumento = getRegistroDocumentoWS(
					getServicioRegistroTelematico().insertarDocumentoRegistro (getRegistroDocumentoServicio(registryDocument), entidad)
						);
			return (RegistroDocumento)ServiciosUtils.completeReturnOK(registroDocumento);
		} catch (RegistroTelematicoException e) {
			logger.error("Error al insertar documento de registro.", e);
			return (RegistroDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al insertar documento de registro.", e);
			return (RegistroDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RegistroDocumento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al insertar documento de registro.", e);
			return (RegistroDocumento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RegistroDocumento()));
		}
	}


	public RetornoServicio actualizarDocumentoRegistro (RegistroDocumento registryDocument, Entidad entidad) {
		try {
				setAuditContext();
				getServicioRegistroTelematico().actualizarDocumentoRegistro (getRegistroDocumentoServicio(registryDocument), entidad);
				return ServiciosUtils.createReturnOK();
		} catch (RegistroTelematicoException e) {
			logger.error("Error al actualizar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		}
	}


	public RetornoServicio eliminarDocumentoRegistro (String registryNumber, Entidad entidad) {
		try {
			setAuditContext();
			getServicioRegistroTelematico().eliminarDocumentoRegistro (registryNumber, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (RegistroTelematicoException e) {
			logger.error("Error al eliminar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar documento de registro.", e);
			return ServiciosUtils.createReturnError();
		}
	}


	private StringB64 getStringB64WS(byte[] poStr){
		if(poStr == null){
			return null;
		}
		StringB64 oStr = new StringB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oStr.setStringB64(encoder.encode(poStr));
		return oStr;
	}


	private byte[] getStringB64Servicio(StringB64 poStr){
		if(poStr == null){
			return null;
		}

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] oStr = decoder.decodeBuffer(poStr.getStringB64());
			return oStr;
		}catch(Exception e){
			return null;
		}
	}


	private RetornoLogico getRetornoLogicoWS(boolean poRetornoLogico){
		RetornoLogico oRetornoLogico = new RetornoLogico ();
		if (poRetornoLogico)
			oRetornoLogico.setValor(ConstantesServicios.LABEL_TRUE);
		else oRetornoLogico.setValor(ConstantesServicios.LABEL_FALSE);
		return oRetornoLogico;
	}


	private ByteArrayB64 getByteArrayB64WS(ByteArrayOutputStream poByteArray){
		if(poByteArray == null){
			return null;
		}
		ByteArrayB64 oByteArray = new ByteArrayB64();
		BASE64Encoder encoder = new BASE64Encoder();
		oByteArray.setByteArrayB64(encoder.encode(poByteArray.toByteArray()));
		return oByteArray;
	}


	private ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion getRegistroPeticionServicio(RegistroPeticion poRegistroPeticion){
		ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion oRegistroPeticion = new ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion();
		if(poRegistroPeticion == null){
			return oRegistroPeticion;
		}

		oRegistroPeticion.setAddressee(poRegistroPeticion.getAddressee());
		oRegistroPeticion.setDocuments(getPeticionDocumentosServicio(poRegistroPeticion.getDocuments()));
		oRegistroPeticion.setEmail(poRegistroPeticion.getEmail());
		oRegistroPeticion.setFolderId(poRegistroPeticion.getFolderId());
		oRegistroPeticion.setProcedureId(poRegistroPeticion.getProcedureId());
		oRegistroPeticion.setSenderIdType(poRegistroPeticion.getSenderIdType());
		oRegistroPeticion.setSpecificData(poRegistroPeticion.getSpecificData());

		return oRegistroPeticion;
	}

	private ieci.tecdoc.sgm.core.services.catalogo.Documentos getDocumentosServicio(Documentos poDocumentos){
		if (poDocumentos == null)
			return null;

		ieci.tecdoc.sgm.core.services.catalogo.Documentos oDocumentos = new ieci.tecdoc.sgm.core.services.catalogo.Documentos();
		DocumentoExtendido[] poDocumentosArray = poDocumentos.getDocumentos();
		for(int i=0; i<poDocumentosArray.length; i++)
			oDocumentos.add(getDocumentoExtendidoServicio(poDocumentosArray[i]));

		return oDocumentos;
	}


	private ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido getDocumentoExtendidoServicio(DocumentoExtendido poDocumentoExtendido){
		ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido oDocumentoExtendido = new ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido();
		if(poDocumentoExtendido == null){
			return oDocumentoExtendido;
		}

		oDocumentoExtendido.setCode(poDocumentoExtendido.getCode());
		oDocumentoExtendido.setDescription(poDocumentoExtendido.getDescription());
		oDocumentoExtendido.setExtension(poDocumentoExtendido.getExtension());
		oDocumentoExtendido.setId(poDocumentoExtendido.getId());
		oDocumentoExtendido.setMandatory(new Boolean(poDocumentoExtendido.isMandatory()).booleanValue());
		oDocumentoExtendido.setSignatureHook(poDocumentoExtendido.getSignatureHook());
		oDocumentoExtendido.setValidationHook(poDocumentoExtendido.getValidationHook());

		return oDocumentoExtendido;
	}


	private ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos getPeticionDocumentosServicio(PeticionDocumentos poPeticionDocumentos){
		if (poPeticionDocumentos == null || poPeticionDocumentos.getPeticionDocumentos()==null)
			return null;

		ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos oPeticionDocumentos = new ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos();
		PeticionDocumento[] poPeticionDocumentosArray = poPeticionDocumentos.getPeticionDocumentos();
		for(int i=0; i<poPeticionDocumentosArray.length; i++)
			oPeticionDocumentos.add(getPeticionDocumentoServicio(poPeticionDocumentosArray[i]));

		return oPeticionDocumentos;
	}


	private ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento getPeticionDocumentoServicio(PeticionDocumento poPeticionDocumento){
		ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento oPeticionDocumento = new ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento();
		if(poPeticionDocumento == null){
			return oPeticionDocumento;
		}

		oPeticionDocumento.setCode(poPeticionDocumento.getCode());
		oPeticionDocumento.setExtension(poPeticionDocumento.getExtension());
		oPeticionDocumento.setFileName(poPeticionDocumento.getFileName());
		oPeticionDocumento.setLocation(poPeticionDocumento.getLocation());
		oPeticionDocumento.setFileContent(getStringB64Servicio(poPeticionDocumento.getFileContent()));
		return oPeticionDocumento;
	}


	private ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta getRegistroConsultaServicio(RegistroConsulta poRegistroConsulta){
		ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta oRegistroConsulta = new ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta();
		if(poRegistroConsulta == null){
			return oRegistroConsulta;
		}

		oRegistroConsulta.setAddressee(poRegistroConsulta.getAddressee());
		oRegistroConsulta.setFirstRegistryDate(poRegistroConsulta.getFirstRegistryDate());
		oRegistroConsulta.setFolderId(poRegistroConsulta.getFolderId());
		oRegistroConsulta.setLastRegistryDate(poRegistroConsulta.getLastRegistryDate());
		oRegistroConsulta.setOprRegistryNumber(new Integer(poRegistroConsulta.getOprRegistryNumber()).intValue());
		oRegistroConsulta.setRegistryNumber(poRegistroConsulta.getRegistryNumber());
		oRegistroConsulta.setSenderId(poRegistroConsulta.getSenderId());
		oRegistroConsulta.setStatus(new Integer(poRegistroConsulta.getStatus()).intValue());
		oRegistroConsulta.setSubject(poRegistroConsulta.getSubject());
		oRegistroConsulta.setSubtype(poRegistroConsulta.getSubtype());
		oRegistroConsulta.setTopic(poRegistroConsulta.getTopic());
		oRegistroConsulta.setType(poRegistroConsulta.getType());
		oRegistroConsulta.setFirstEffectiveDate(poRegistroConsulta.getFirstEffectiveDate());
		oRegistroConsulta.setLastEffectiveDate(poRegistroConsulta.getLastEffectiveDate());
		return oRegistroConsulta;
	}


	private Registro getRegistroWS(ieci.tecdoc.sgm.core.services.telematico.Registro poRegistro){
		Registro oRegistro = new Registro();
		if(poRegistro == null){
			return oRegistro;
		}


		oRegistro.setAddressee(poRegistro.getAddressee());

		if (poRegistro.getAdditionalInfo() != null) {

			BASE64Encoder encoder = new BASE64Encoder();
			oRegistro.setAdittionalInfo(encoder.encode(poRegistro.getAdditionalInfo()));
		}

		oRegistro.setEMail(poRegistro.getEMail());
		oRegistro.setName(poRegistro.getName());
		oRegistro.setNumeroExpediente(poRegistro.getNumeroExpediente());
		oRegistro.setOficina(poRegistro.getOficina());
		oRegistro.setRegistryDate(DateTimeUtil.getDateTime(poRegistro.getRegistryDate(), ConstantesServicios.DATE_PATTERN));
		oRegistro.setEffectiveDate(DateTimeUtil.getDateTime(poRegistro.getEffectiveDate(), ConstantesServicios.DATE_PATTERN));
		oRegistro.setRegistryNumber(poRegistro.getRegistryNumber());
		oRegistro.setRepresentedId(poRegistro.getRepresentedId());
		oRegistro.setRepresentedName(poRegistro.getRepresentedName());
		oRegistro.setSenderId(poRegistro.getSenderId());
		oRegistro.setSenderIdType(""+poRegistro.getSenderIdType());
		oRegistro.setStatus(""+poRegistro.getStatus());
		//oRegistro.setSurName(poRegistro.getSurName());
		oRegistro.setTopic(poRegistro.getTopic());

		return oRegistro;
	}


	private Registros getRegistrosWS(ieci.tecdoc.sgm.core.services.telematico.Registros poRegistros) {
		if (poRegistros == null)
			return null;

		Registros oRegistros = new Registros();
		Registro[] oRegistrosArray = new Registro[poRegistros.count()];
		for(int i=0; i<poRegistros.count(); i++)
			oRegistrosArray[i] = getRegistroWS((ieci.tecdoc.sgm.core.services.telematico.Registro)poRegistros.get(i));
		oRegistros.setRegistros(oRegistrosArray);

		return oRegistros;
	}


	private ContenedorDocumento getContenedorDocumentoWS(ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento poContenedorDocumento){
		ContenedorDocumento oContenedorDocumento = new ContenedorDocumento();
		if(poContenedorDocumento == null){
			return oContenedorDocumento;
		}

		if (poContenedorDocumento.getContent() != null){

			BASE64Encoder encoder = new BASE64Encoder();
			oContenedorDocumento.setContent(encoder.encode(poContenedorDocumento.getContent()));
		}

		oContenedorDocumento.setContentSize(""+poContenedorDocumento.getContentSize());
		oContenedorDocumento.setExtension(poContenedorDocumento.getExtension());
		oContenedorDocumento.setGuid(poContenedorDocumento.getGuid());
		oContenedorDocumento.setHash(poContenedorDocumento.getHash());
		oContenedorDocumento.setTimestamp(DateTimeUtil.getDateTime(poContenedorDocumento.getTimestamp(), ConstantesServicios.DATE_PATTERN));

		return oContenedorDocumento;
	}


	private ContenedorDocumentos getContenedorDocumentosWS(ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos poContenedorDocumentos) {
		if (poContenedorDocumentos == null)
			return null;

		ContenedorDocumentos oContenedorDocumentos = new ContenedorDocumentos();
		ContenedorDocumento[] oContenedorDocumentosArray = new ContenedorDocumento[poContenedorDocumentos.count()];
		for(int i=0; i<poContenedorDocumentos.count(); i++)
			oContenedorDocumentosArray[i] = getContenedorDocumentoWS((ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento)poContenedorDocumentos.get(i));
		oContenedorDocumentos.setContenedorDocumentos(oContenedorDocumentosArray);

		return oContenedorDocumentos;
	}


	private RegistroDocumento getRegistroDocumentoWS(ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento poRegistroDocumento){
		RegistroDocumento oRegistroDocumento = new RegistroDocumento();
		if(poRegistroDocumento == null){
			return oRegistroDocumento;
		}

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}


	private ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento getRegistroDocumentoServicio(RegistroDocumento poRegistroDocumento){
		ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento oRegistroDocumento = new ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento();
		if(poRegistroDocumento == null){
			return oRegistroDocumento;
		}

		oRegistroDocumento.setCode(poRegistroDocumento.getCode());
		oRegistroDocumento.setGuid(poRegistroDocumento.getGuid());
		oRegistroDocumento.setRegistryNumber(poRegistroDocumento.getRegistryNumber());

		return oRegistroDocumento;
	}


	private RegistroDocumentos getRegistroDocumentosWS(ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos poRegistroDocumentos) {
		if (poRegistroDocumentos == null)
			return null;

		RegistroDocumentos oRegistroDocumentos = new RegistroDocumentos();
		RegistroDocumento[] oRegistroDocumentosArray = new RegistroDocumento[poRegistroDocumentos.count()];
		for(int i=0; i<poRegistroDocumentos.count(); i++)
			oRegistroDocumentosArray[i] = getRegistroDocumentoWS((ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento)poRegistroDocumentos.get(i));
		oRegistroDocumentos.serRegistroDocumentos(oRegistroDocumentosArray);

		return oRegistroDocumentos;
	}


}
