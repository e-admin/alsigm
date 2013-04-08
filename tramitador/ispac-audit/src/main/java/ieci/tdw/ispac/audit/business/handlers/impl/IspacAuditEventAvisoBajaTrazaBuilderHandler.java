/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;

/**
 * 
 * Manejador para gestionar los eventos de tipo "Expediente - Alta"
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventAvisoBajaTrazaBuilderHandler implements IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventAvisoBajaVO evento = (IspacAuditEventAvisoBajaVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.add(generateTraza(evento));
		return result;
	}

	protected TrazaAuditoriaVO generateTraza(IspacAuditEventAvisoBajaVO evento) {

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(evento, result);

		// Tipo de evento Expediente - Alta
		result.setEventType(new Long(IspacEventAuditTypeEnum.AVISO_BAJA.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Aviso - BAJA";
		result.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.AVISO.getValue()));

		result.setObjectTypeDescription(IspacObjectAuditTypeEnum.AVISO.getName());

		result.setObjectId(evento.getIdAviso());
		

		return result;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventAvisoBajaVO eventoAccesoAplicacion, TrazaAuditoriaVO traza) {

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
