package ieci.tecdoc.sgm.autenticacion;

import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadExcepcion;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfoImpl;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conector;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuario;

public class SigemServicioSesionUsuarioAdapter implements ServicioSesionUsuario {

	private static final Logger logger = Logger.getLogger(SigemServicioSesionUsuarioAdapter.class);

	public void crearSesion(SesionUsuario poSessionUsuario, Entidad entidad)
			throws SesionUsuarioException {
		try {
			SesionManager.add(getSesionInfo(poSessionUsuario), getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error creando sesión de usuario.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado creando sesión de usuario.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void limpiarSesiones(Entidad entidad) throws SesionUsuarioException {
		try {
			SesionManager.clean(getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error limpiando sesiones.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado limpiando sesiones.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void limpiarSesiones(int timeout, Entidad entidad) throws SesionUsuarioException {
		try {
			SesionManager.clean(timeout, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error limpiando sesiones.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado limpiando sesiones.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void borrarSesion(String psIdSesion, Entidad entidad) throws SesionUsuarioException {
		try {
			SesionManager.delete(psIdSesion, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error eliminando sesión de usuario.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado eliminando sesión de usuario.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public SesionUsuario obtenerSesion(String sessionId, Entidad entidad)
			throws SesionUsuarioException {
		try {
			return getSesionUsuario(SesionManager.get(sessionId, getIdEntidad(entidad)));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error obteniendo datos de sesión de usuario.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo datos de sesión de usuario.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public MetodoAutenticacion getMetodoAutenticacion(String sessionId, Entidad entidad)
			throws SesionUsuarioException {
		try {
			return getMetodoAutenticacion(SesionManager.getHook(sessionId, getIdEntidad(entidad)));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error obteniendo método de autenticación.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo método de autenticación.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String getIdMetodoAutenticacion(String sessionId, Entidad entidad)
			throws SesionUsuarioException {
		try {
			return SesionManager.getHookId(sessionId, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error obteniendo id de método de autenticación.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo id de método de autenticación.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public InfoUsuario getInfoUsuario(String sessionId, Entidad entidad)
			throws SesionUsuarioException {
		try {
			return getInfoUsuario(SesionManager.getSender(sessionId, getIdEntidad(entidad)));
		} catch (AutenticacionExcepcion e) {
			logger.error("Error obteniendo información de usuario.", e);
			throw getServicioSesionUsuarioException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo información de usuario.", e);
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private SesionUsuarioException getServicioSesionUsuarioException(AutenticacionExcepcion poException){
		if(poException == null){
			return new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_USER_SESSION_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new SesionUsuarioException(Long.valueOf(cCodigo.toString()).longValue(), poException);

	}

	private SesionUsuario getSesionUsuario(SesionInfo poSesion){
		if(poSesion == null){
			return null;
		}
		SesionUsuario oSesion = new SesionUsuario();
		oSesion.setHookId(poSesion.getHookId());
		oSesion.setLoginDate(poSesion.getLoginDate());
		oSesion.setSender(getInfoUsuario(poSesion.getSender()));
		oSesion.setSessionId(poSesion.getSessionId());
		return oSesion;
	}

	private SesionInfo getSesionInfo(SesionUsuario poSesion){
		if(poSesion == null){
			return null;
		}
		SesionInfo oSesion = new SesionInfoImpl();
		oSesion.setHookId(poSesion.getHookId());
		oSesion.setLoginDate(poSesion.getLoginDate());
		oSesion.setSender(getSolicitante(poSesion.getSender()));
		oSesion.setSessionId(poSesion.getSessionId());
		return oSesion;
	}

	public InfoUsuario getInfoUsuario(Solicitante poUsuario){
		if(poUsuario == null){
			return null;
		}
		InfoUsuario oUsuario = new InfoUsuario();
		oUsuario.setCertificateIssuer(poUsuario.getCertificateIssuer());
		oUsuario.setCertificateSN(poUsuario.getCertificateSN());
		oUsuario.setCIF(poUsuario.getCIF());
		oUsuario.setEmail(poUsuario.getEmail());
		oUsuario.setId(poUsuario.getId());
		oUsuario.setInQuality(poUsuario.getInQuality());
		oUsuario.setName(poUsuario.getName());
		oUsuario.setSocialName(poUsuario.getSocialName());
		oUsuario.setFirstName(poUsuario.getFirstName());
		oUsuario.setSurName(poUsuario.getSurName());
		oUsuario.setSurName2(poUsuario.getSurName2());
		return oUsuario;
	}

	public Solicitante getSolicitante(InfoUsuario poUsuario){
		if(poUsuario == null){
			return null;
		}
		Solicitante oUsuario = new Solicitante();
		oUsuario.setCertificateIssuer(poUsuario.getCertificateIssuer());
		oUsuario.setCertificateSN(poUsuario.getCertificateSN());
		oUsuario.setCIF(poUsuario.getCIF());
		oUsuario.setEmail(poUsuario.getEmail());
		oUsuario.setId(poUsuario.getId());
		oUsuario.setInQuality(poUsuario.getInQuality());
		oUsuario.setName(poUsuario.getName());
		oUsuario.setSocialName(poUsuario.getSocialName());
		oUsuario.setFirstName(poUsuario.getFirstName());
		oUsuario.setSurName(poUsuario.getSurName());
		oUsuario.setSurName2(poUsuario.getSurName2());
		return oUsuario;
	}

	private MetodoAutenticacion getMetodoAutenticacion(Conector poMetodo){
		if(poMetodo == null){
			return null;
		}
		MetodoAutenticacion oMetodo = new MetodoAutenticacion();
		oMetodo.setDescription(poMetodo.getDescription());
		oMetodo.setId(poMetodo.getId());
		oMetodo.setType(poMetodo.getType());
		return oMetodo;
	}

	public String login(String actSessionId, String nombre, String apellidos, String email, String senderId, Entidad entidad) throws SesionUsuarioException {
		String idSesion = null;
		try {
			idSesion = AutenticacionManager.login(actSessionId, nombre, apellidos, email, senderId, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.AUTENTICATION_ERROR_CODE, e.getMessage(), e);
		} catch (SeguridadExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.SECURITY_ERROR_CODE, e.getMessage(), e);
		}
		return idSesion;
	}

	public String login(String actSessionId, String authId, X509Certificate certificate, Entidad entidad) throws SesionUsuarioException {
		String idSesion = null;
		try {
			idSesion = AutenticacionManager.login(actSessionId, authId, certificate, "", getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.AUTENTICATION_ERROR_CODE, e.getMessage(), e);
		} catch (SeguridadExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.SECURITY_ERROR_CODE, e.getMessage(), e);
		}
		return idSesion;
	}

	public String login(String procedureId, X509Certificate certificate, Entidad entidad) throws SesionUsuarioException {
		String idSesion = null;
		try {
			idSesion = AutenticacionManager.login(procedureId, certificate, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.AUTENTICATION_ERROR_CODE, e.getMessage(), e);
		} catch (SeguridadExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.SECURITY_ERROR_CODE, e.getMessage(), e);
		}
		return idSesion;
	}

	public void logout(String sessionId, Entidad entidad) throws SesionUsuarioException {
		try {
			AutenticacionManager.logout(sessionId, getIdEntidad(entidad));
		} catch (AutenticacionExcepcion e) {
			logger.error(e.getMessage());
			throw new SesionUsuarioException(SesionUsuarioException.AUTENTICATION_ERROR_CODE, e.getMessage(), e);
		}
	}

	private String getIdEntidad(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		return poEntidad.getIdentificador();
	}

}
