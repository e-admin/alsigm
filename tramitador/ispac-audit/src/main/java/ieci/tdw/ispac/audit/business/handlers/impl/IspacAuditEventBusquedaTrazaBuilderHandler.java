/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventBusquedaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;

/**
 * 
 * Manejador para gestionar los eventos de acceso a la aplicación
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventBusquedaTrazaBuilderHandler implements IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventBusquedaVO eventoBusqueda = (IspacAuditEventBusquedaVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.add(generateTrazaBusquedaAvanzada(eventoBusqueda));
		return result;
	}

	protected TrazaAuditoriaVO generateTrazaBusquedaAvanzada(
			IspacAuditEventBusquedaVO eventoBusqueda) {

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(eventoBusqueda, result);

		// tipo de evento ACCESO APLICACION
		result.setEventType(new Long(IspacEventAuditTypeEnum.BUSQUEDA_AVANZADA.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Búsqueda";
		result.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.EXPEDIENTE.getValue()));

		result.setObjectTypeDescription(IspacObjectAuditTypeEnum.EXPEDIENTE.getName());

		// Metemos en newValue el texto de la consulta
		result.setNewValue(eventoBusqueda.getConsulta());

		return result;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventBusquedaVO eventoAccesoAplicacion, TrazaAuditoriaVO traza) {

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
