package gcontrol.model;

import gcontrol.vos.GrupoVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioVO;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.bi.IServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;

public class AuditUsuarios {

	private static LoggingEvent getLogginEvent(IServiceBase serviceToAudit,
			int action) {

		LoggingEvent event = new LoggingEvent(ArchivoModules.USUARIOS_MODULE,
				action, serviceToAudit.getServiceClient(), false);

		serviceToAudit.getLogger().add(event);

		return event;
	}

	public static LoggingEvent getLogginEventAltaUsuario(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_ALTA_USUARIO);
	}

	public static LoggingEvent getLogginEventModificacionUsuario(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_MODIFICACION_USUARIO);
	}

	public static LoggingEvent getLogginEventEliminacionUsuario(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_ELIMINACION_USUARIO);
	}

	public static LoggingEvent getLogginEventAsignacionRole(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_ASIGNACION_ROLE);
	}

	public static LoggingEvent getLogginEventDeasignacionRole(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_DEASIGNACION_ROLE);
	}

	public static LoggingEvent getLogginEventAsignacionGrupo(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_ASIGNACION_GRUPO);
	}

	public static LoggingEvent getLogginEventDeasignacionGrupo(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.USUARIOS_MODULE_DEASIGNACION_GRUPO);
	}

	public static DataLoggingEvent addDataLogInfoUsuario(Locale locale,
			LoggingEvent event, UsuarioVO usuario) {
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_USUARIO, usuario.getId());
		data.addDetalle(locale, ArchivoDetails.USUARIOS_ID, usuario.getId())
				.addDetalle(locale, ArchivoDetails.USUARIOS_NOMBRE,
						usuario.getNombre())
				.addDetalle(locale, ArchivoDetails.USUARIOS_APELLIDOS,
						usuario.getApellidos());
		return data;
	}

	public static void addDataLogInfoUsuarios(Locale locale,
			LoggingEvent event, List usuarios) {
		for (Iterator itUsuario = usuarios.iterator(); itUsuario.hasNext();) {
			UsuarioVO usuarioVO = (UsuarioVO) itUsuario.next();
			AuditUsuarios.addDataLogInfoUsuario(locale, event, usuarioVO);
		}
	}

	public static void addDetalleInfoOrgano(Locale locale,
			DataLoggingEvent dataLogicEvent, UsuarioVO usuario) {
		dataLogicEvent
				.addDetalle(locale, ArchivoDetails.USUARIOS_ID, usuario.getId())
				.addDetalle(locale, ArchivoDetails.USUARIOS_NOMBRE,
						usuario.getNombre())
				.addDetalle(locale, ArchivoDetails.USUARIOS_APELLIDOS,
						usuario.getApellidos());
	}

	public static void addDataLogInfoRole(Locale locale, LoggingEvent event,
			RolVO role) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ROLE, role.getId())
				.addDetalle(locale, ArchivoDetails.USUARIOS_NOMBRE_ROL,
						role.getNombre());
	}

	public static void addDataLogInfoRoles(Locale locale, LoggingEvent event,
			List roles) {
		for (Iterator itRole = roles.iterator(); itRole.hasNext();) {
			RolVO roleVO = (RolVO) itRole.next();
			AuditUsuarios.addDataLogInfoRole(locale, event, roleVO);
		}
	}

	public static void addDataLogInfoGrupo(Locale locale, LoggingEvent event,
			GrupoVO grupo) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_GRUPO, grupo.getId())
				.addDetalle(locale, ArchivoDetails.USUARIOS_NOMBRE_GRUPO,
						grupo.getNombre());
	}

	public static void addDataLogInfoGrupos(Locale locale, LoggingEvent event,
			List grupos) {
		for (Iterator itGrupos = grupos.iterator(); itGrupos.hasNext();) {
			GrupoVO grupoVO = (GrupoVO) itGrupos.next();
			AuditUsuarios.addDataLogInfoGrupo(locale, event, grupoVO);
		}
	}

}
