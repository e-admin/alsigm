/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventRegDistConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;

/**
 * 
 * Manejador para gestionar los eventos de tipo "Expediente - Alta"
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventRegDistConsultaTrazaBuilderHandler implements IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventRegDistConsultaVO evento = (IspacAuditEventRegDistConsultaVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.addAll(generateTrazaConsultaIndividual(evento));
		//result.add(generateTrazaConsulta(evento));
		return result;
	}

	protected Collection<TrazaAuditoriaVO> generateTrazaConsultaIndividual(
			IspacAuditEventRegDistConsultaVO evento) {

		List<TrazaAuditoriaVO> result = new LinkedList<TrazaAuditoriaVO>();

		Map registros = evento.getRegistros();

		Set registrosSet = registros.keySet();
		Iterator iterRegistros = registrosSet.iterator();
		while (iterRegistros.hasNext()) {
			String key = (String) iterRegistros.next();

			TrazaAuditoriaVO traza = new TrazaAuditoriaVO();

			populateBasicUserEventTrazaAuditoria(evento, traza);

			// Tipo de evento Expediente - Alta
			traza.setEventType(new Long(IspacEventAuditTypeEnum.REGISTRO_DISTRIBUIDO_CONSULTA.getValue()));

			// descripción del evento que se produjo con info adicional
			String eventDescription = "Registro Distribuido - Consulta";
			traza.setEventDescription(eventDescription);

			// tipo de objeto de negocio que auditamos registro, campo de
			// regitro,
			// usuario, campo de usuario, etc ..
			traza.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.REGISTRO_DISTRIBUIDO.getValue()));

			traza.setObjectTypeDescription(IspacObjectAuditTypeEnum.REGISTRO_DISTRIBUIDO.getName());

			traza.setObjectId(key);
			String newValue = registros.get(key).toString();
			traza.setNewValue(newValue);
			
			//Añadimos la traza a la lista
			result.add(traza);

		}
		return result;
	}

	protected TrazaAuditoriaVO generateTrazaConsulta(IspacAuditEventRegDistConsultaVO evento) {

		
		TrazaAuditoriaVO traza = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(evento, traza);

		// Tipo de evento Expediente - Alta
		traza.setEventType(new Long(IspacEventAuditTypeEnum.REGISTRO_DISTRIBUIDO_CONSULTA.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Registro Distribuido - Consulta";
		traza.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de
		// regitro,
		// usuario, campo de usuario, etc ..
		traza.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.REGISTRO_DISTRIBUIDO.getValue()));

		traza.setObjectTypeDescription(IspacObjectAuditTypeEnum.REGISTRO_DISTRIBUIDO.getName());

		Map registros = evento.getRegistros();
		traza.setNewValue(registros.toString());

		return traza;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventRegDistConsultaVO eventoAccesoAplicacion, TrazaAuditoriaVO traza) {

		IspacAuditEventTrazaBuilderHandlerHelper.populateBasicUserEventTrazaAuditoria(
				eventoAccesoAplicacion, traza);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#getTypeHandler()
	 */
	public IspacEventAuditHandlerTypeEnum getTypeHandler() {

		return IspacEventAuditHandlerTypeEnum.TRAZA_BUILDER;
	}

}
