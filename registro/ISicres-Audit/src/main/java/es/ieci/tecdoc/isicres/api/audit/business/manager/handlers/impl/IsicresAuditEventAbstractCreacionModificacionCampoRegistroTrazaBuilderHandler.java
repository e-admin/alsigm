package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler;
import es.ieci.tecdoc.isicres.api.audit.business.vo.IsicresAuditoriaValorModificadoVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresObjectAuditTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractBasicRegistroUserEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractCreacionModificacionRegistroEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;

/**
 * Clase abstracta  manejadora para la generación de trazas de auditoría para el eventos de creación o modificación de un campo de un registro
 * @author IECISA
 *
 */
public abstract class IsicresAuditEventAbstractCreacionModificacionCampoRegistroTrazaBuilderHandler
		implements IsicresAuditoriaEventHandler {
	private static final String MARCA_TEXTO_TRUNCADO = ">>>";
	private static final int VARCHAR_MAX_VALUE = 4000;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(IsicresAuditEventAbstractCreacionModificacionCampoRegistroTrazaBuilderHandler.class);


	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.api.audit.business.manager.handlers.
	 * IsicresAuditoriaEventHandler#getTypeHandler()
	 */
	public IsicresEventAuditHandlerTypeEnum getTypeHandler() {
		return IsicresEventAuditHandlerTypeEnum.TRAZA_BUILDER;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seees.ieci.tecdoc.isicres.api.audit.business.manager.handlers.
	 * IsicresAuditoriaEventHandler
	 * #handle(es.ieci.tecdoc.isicres.api.audit.business
	 * .vo.events.IsicresAuditEventVO)
	 */
	public Object handle(IsicresAuditEventVO isicresAuditEvent) {

		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacion = (IsicresAuditAbstractCreacionModificacionRegistroEventVO) isicresAuditEvent;

		result.addAll(generateTrazaCreacionCampoRegistro(eventoCreacion));

		return result;
	}

	/**
	 * Metodo que crea la traza para auditar la creacion de un campo de un
	 * registro
	 *
	 * @param eventoCreacionModificacion
	 * @return
	 */
	protected List<TrazaAuditoriaVO> generateTrazaCreacionCampoRegistro(
			IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacion) {

		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		List<IsicresAuditoriaValorModificadoVO> valores = eventoCreacionModificacion
				.getValores();

		for (Iterator<IsicresAuditoriaValorModificadoVO> iterator = valores.iterator(); iterator.hasNext();) {
			IsicresAuditoriaValorModificadoVO campoRegistro = (IsicresAuditoriaValorModificadoVO) iterator
					.next();
			TrazaAuditoriaVO trazaCampo = new TrazaAuditoriaVO();
			populateBasicUserEventTrazaAuditoria(eventoCreacionModificacion, trazaCampo);

			trazaCampo.setEventType(new Long(getIsicresEventAuditTypeEnum()
					.getValue()));

			// descripción del evento que se produjo con info adicional
			String eventDescription = getEventDescription(eventoCreacionModificacion);
			trazaCampo.setEventDescription(eventDescription);

			// tipo de objeto de negocio que auditamos registro, campo de
			// regitro,
			// usuario, campo de usuario, etc ..
			trazaCampo.setObjectType(String
					.valueOf(IsicresObjectAuditTypeEnum.REGISTRO_FIELD
							.getValue()));

			trazaCampo
					.setObjectTypeDescription(IsicresObjectAuditTypeEnum.REGISTRO_FIELD
							.getName());

			// idendtificador del objeto de negocio que auditamos, eje:
			// identificador de registro --> numero de libro + numero de registo
			String objectId = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroObjectID(eventoCreacionModificacion);

			trazaCampo.setObjectId(objectId);

			//ver que ponemos aqui como objecField -->fld1???? fld2?? ...
			String objectField = IsicresAuditEventTrazaBuilderHandlerHelper.generateRegistroFieldObject(eventoCreacionModificacion, campoRegistro);
			trazaCampo.setObjectField(objectField);


			/*
			 * Se trunca el valor antiguo y el nuevo valor a 4000 caracteres porque puede que dicho campo
			 * sea un campo extendido que en este caso es un CLOB.
			 *
			 *  Se añade una marca al final del valor y se saca por el log el valor original antes de ser truncado.
			 *
			 */
			String oldValue = campoRegistro.getOldValue();
			if (oldValue != null){
				if (oldValue.length() > VARCHAR_MAX_VALUE) {
					logger.error("ERROR AUDITORÍA. EL TAMAÑO DEL CAMPO OLD_VALUE EXCEDE LOS "+ VARCHAR_MAX_VALUE+" CARACTERES. SE PROCEDERÁ A TRUNCAR ESTE VALOR. EL CONTENIDO ORIGINAL DE ESTE VALOR ES EL SIGUIENTE: \n"
							+ oldValue);
					oldValue = oldValue.substring(0, VARCHAR_MAX_VALUE-5);
					oldValue = oldValue + MARCA_TEXTO_TRUNCADO;
				}
				trazaCampo.setOldValue(oldValue);
			}


			String newValue = campoRegistro.getNewValue();
			if (newValue != null){
				if (newValue.length() > VARCHAR_MAX_VALUE) {
				logger.error("ERROR AUDITORÍA. EL TAMAÑO DEL CAMPO NEW_VALUE EXCEDE LOS "+ VARCHAR_MAX_VALUE+" CARACTERES. SE PROCEDERÁ A TRUNCAR ESTE VALOR. EL CONTENIDO ORIGINAL DE ESTE VALOR ES EL SIGUIENTE: \n"
						+ newValue);
				newValue = newValue.substring(0, VARCHAR_MAX_VALUE-5);
				newValue = newValue + MARCA_TEXTO_TRUNCADO;
				}

			trazaCampo.setNewValue(newValue);
			}

			result.add(trazaCampo);
		}

		return result;
	}

	/**
	 * metodo para popular la traza con los datos referentes al usario
	 *
	 * @param eventoCreacion
	 * @param trazaCampo
	 */
	protected void populateBasicUserEventTrazaAuditoria(
			IsicresAuditAbstractBasicRegistroUserEventVO eventoCreacion,
			TrazaAuditoriaVO trazaCampo) {

		IsicresAuditEventTrazaBuilderHandlerHelper
				.populateBasicUserEventTrazaAuditoria(eventoCreacion,
						trazaCampo);

	}

	/**
	 * Metodo para obtener el tipo de envento a auditar (creacion o modificacion)
	 * @return
	 */
	abstract protected IsicresEventAuditTypeEnum getIsicresEventAuditTypeEnum();

	protected String getEventDescription(IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacion){
		return getIsicresEventAuditTypeEnum().getName();
	}

}
