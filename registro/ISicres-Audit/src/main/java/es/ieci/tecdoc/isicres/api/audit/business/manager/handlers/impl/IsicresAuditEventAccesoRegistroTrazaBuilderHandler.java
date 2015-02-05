package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresObjectAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;

/**
 * Clase manejadora para la generación de trazas de auditoría para el evento de acceso a un registro
 * @author IECISA
 *
 */
public class IsicresAuditEventAccesoRegistroTrazaBuilderHandler implements
		IsicresAuditoriaEventHandler {

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler#getTypeHandler()
	 */
	public IsicresEventAuditHandlerTypeEnum getTypeHandler() {
		return IsicresEventAuditHandlerTypeEnum.TRAZA_BUILDER;
	}

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler#handle(es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO)
	 */
	public Object handle(IsicresAuditEventVO isicresAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IsicresAuditEventAccesoRegistroVO eventoAccesoRegistro = (IsicresAuditEventAccesoRegistroVO) isicresAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.add(generateTrazaAccesoRegistro(eventoAccesoRegistro));

		return result;
	}

	/**
	 * Metodo que crea la traza para auditar el acceso al registro
	 * @param eventoAccesoRegistro - Evento {@link IsicresAuditEventAccesoRegistroVO}
	 * @return {@link TrazaAuditoriaVO}
	 */
	protected TrazaAuditoriaVO generateTrazaAccesoRegistro(
			IsicresAuditEventAccesoRegistroVO eventoAccesoRegistro) {

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(eventoAccesoRegistro, result);

		// tipo de evento ACCESO DE REGISTRO
		result.setEventType(new Long(
				IsicresEventAuditTypeEnum.ACCESO_REGISTRO.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = IsicresEventAuditTypeEnum.ACCESO_REGISTRO.getName();
		result.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IsicresObjectAuditTypeEnum.REGISTRO
				.getValue()));

		result.setObjectTypeDescription(IsicresObjectAuditTypeEnum.REGISTRO
				.getName());

		// idendtificador del objeto de negocio que auditamos, ejemplo:
		// identificador de registro --> numero de libro + numero de registo
		String objectId = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroObjectID(eventoAccesoRegistro);
		result.setObjectId(objectId);

		String valorAuditado = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroValorAuditado(eventoAccesoRegistro);
		result.setNewValue(valorAuditado);

		return result;
	}

	/**
	 * metodo para popular la traza con los datos referentes al usario
	 *
	 * @param eventoCreacion
	 * @param trazaCampo
	 */
	protected void populateBasicUserEventTrazaAuditoria(
			IsicresAuditEventAccesoRegistroVO eventoAccesoRegistro,
			TrazaAuditoriaVO trazaCampo) {

		IsicresAuditEventTrazaBuilderHandlerHelper
				.populateBasicUserEventTrazaAuditoria(eventoAccesoRegistro,
						trazaCampo);
	}

}
