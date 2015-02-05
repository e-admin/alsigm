package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresObjectAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractCreacionModificacionRegistroEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;

/**
 * Clase abstracta para gestionar las trazas de creacion, modificacion y acceso a un registro
 * @author IECISA
 *
 */
public abstract class IsicresAuditEventAbstractCreacionModificacionAccesoRegistroTrazaBuilderHandler implements IsicresAuditoriaEventHandler{
	private static final String MARCA_TEXTO_TRUNCADO = ">>>";
	private static final int VARCHAR_MAX_VALUE = 4000;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(IsicresAuditEventAbstractCreacionModificacionAccesoRegistroTrazaBuilderHandler.class);

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

		IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacion = (IsicresAuditAbstractCreacionModificacionRegistroEventVO) isicresAuditEvent;

		// Crear traza de auditoria de creacion y de creacion de campos
		result.add(generateTrazaCreacionModificacionRegistro(eventoCreacionModificacion));

		return result;
	}

	/**
	 * Metodo que crea la traza para auditar la creacion de registro
	 * @param eventoCreacion
	 * @return
	 */
	protected TrazaAuditoriaVO generateTrazaCreacionModificacionRegistro(
			IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("generateTrazaCreacionModificacionRegistro(IsicresAuditAbstractCreacionModificacionRegistroEventVO) - start");
		}

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		//
		populateBasicUserEventTrazaAuditoria(eventoCreacionModificacion, result);

		// tipo de evento CREACION DE REGISTRO
		result.setEventType(new Long(
				getIsicresEventAuditTypeEnum().getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = getIsicresEventAuditTypeEnum().getName();
		result.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IsicresObjectAuditTypeEnum.REGISTRO
				.getValue()));

		result.setObjectTypeDescription(IsicresObjectAuditTypeEnum.REGISTRO
				.getName());

		// idendtificador del objeto de negocio que auditamos, ejemplo:
		// identificador de registro --> numero de libro + numero de registo
		String objectId = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroObjectID(eventoCreacionModificacion);
		result.setObjectId(objectId);

		String objectField = null;
		result.setObjectField(objectField);

		//String valorAuditado = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroValorAuditado(eventoCreacionModificacion);
		//Obtenemos la información en formato XML del registro
		String valorAuditado = eventoCreacionModificacion.getInfoRegistroXML();
		if (valorAuditado.length() > VARCHAR_MAX_VALUE) {
			logger.error("ERROR AUDITORÍA. EL TAMAÑO DEL CAMPO NEW_VALUE EXCEDE LOS "+ VARCHAR_MAX_VALUE+" CARACTERES. SE PROCEDERÁ A TRUNCAR ESTE VALOR. EL CONTENIDO ORIGINAL DE ESTE VALOR ES EL SIGUIENTE: \n"
					+ valorAuditado);
			valorAuditado = valorAuditado.substring(0, VARCHAR_MAX_VALUE-5);
			valorAuditado = valorAuditado+MARCA_TEXTO_TRUNCADO;
		}

		result.setNewValue(valorAuditado);

		if (logger.isDebugEnabled()) {
			logger.debug("generateTrazaCreacionModificacionRegistro(IsicresAuditAbstractCreacionModificacionRegistroEventVO) - end");
		}
		return result;
	}


	/**
	 * metodo para popular la traza con los datos referentes al usario
	 * @param eventoCreacion
	 * @param trazaCampo
	 */
	protected void populateBasicUserEventTrazaAuditoria(
			IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacion,
			TrazaAuditoriaVO trazaCampo) {

		IsicresAuditEventTrazaBuilderHandlerHelper.populateBasicUserEventTrazaAuditoria(eventoCreacionModificacion, trazaCampo);

	}

	abstract protected IsicresEventAuditTypeEnum getIsicresEventAuditTypeEnum();
}
