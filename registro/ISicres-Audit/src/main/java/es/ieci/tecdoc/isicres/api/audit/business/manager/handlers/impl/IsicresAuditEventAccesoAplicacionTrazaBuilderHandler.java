package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresObjectAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoAplicacionVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;

/**
 * Clase manejadora para la generación de trazas de auditoría para el evento de entrada a la aplicación de registro
 * @author IECISA
 *
 */
public class IsicresAuditEventAccesoAplicacionTrazaBuilderHandler implements
		IsicresAuditoriaEventHandler {

	public IsicresEventAuditHandlerTypeEnum getTypeHandler() {
		return IsicresEventAuditHandlerTypeEnum.TRAZA_BUILDER;
	}

	public Object handle(IsicresAuditEventVO isicresAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IsicresAuditEventAccesoAplicacionVO eventoAccesoAplicacion = (IsicresAuditEventAccesoAplicacionVO) isicresAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.add(generateTrazaAccesoApplicacion(eventoAccesoAplicacion));

		return result;
	}

	protected TrazaAuditoriaVO generateTrazaAccesoApplicacion(
			IsicresAuditEventAccesoAplicacionVO eventoAccesoAplicacion) {

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(eventoAccesoAplicacion, result);

		// tipo de evento ACCESOO APLICACION DE REGISTRO
		result.setEventType(new Long(
				IsicresEventAuditTypeEnum.ACCESO_APLICACION.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = IsicresEventAuditTypeEnum.ACCESO_APLICACION.getName();
		result.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IsicresObjectAuditTypeEnum.USUARIO
				.getValue()));

		result.setObjectTypeDescription(IsicresObjectAuditTypeEnum.USUARIO
				.getName());

		return result;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IsicresAuditEventAccesoAplicacionVO eventoAccesoAplicacion,
			TrazaAuditoriaVO traza) {

		IsicresAuditEventTrazaBuilderHandlerHelper
				.populateBasicUserEventTrazaAuditoria(eventoAccesoAplicacion,
						traza);

	}

}
