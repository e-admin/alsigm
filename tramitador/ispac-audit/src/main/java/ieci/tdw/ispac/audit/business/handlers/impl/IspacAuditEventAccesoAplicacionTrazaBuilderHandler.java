/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAccesoAplicacionVO;
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
public class IspacAuditEventAccesoAplicacionTrazaBuilderHandler implements IspacAuditoriaEventHandler{

	/**
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();
		
		IspacAuditEventAccesoAplicacionVO eventoAccesoAplicacion = (IspacAuditEventAccesoAplicacionVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.add(generateTrazaAccesoApplicacion(eventoAccesoAplicacion));
		return result;
	}
	
	
	protected TrazaAuditoriaVO generateTrazaAccesoApplicacion(
			IspacAuditEventAccesoAplicacionVO eventoAccesoAplicacion) {

		TrazaAuditoriaVO result = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(eventoAccesoAplicacion, result);

		// tipo de evento ACCESO APLICACION
		result.setEventType(new Long(
				IspacEventAuditTypeEnum.ACCESO_APLICACION.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Acceso Aplicación";
		result.setEventDescription(eventDescription);
		result.setObjectId(eventoAccesoAplicacion.getIdUser());

		// tipo de objeto de negocio que auditamos registro, campo de regitro,
		// usuario, campo de usuario, etc ..
		result.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.USUARIO
				.getValue()));

		result.setObjectTypeDescription(IspacObjectAuditTypeEnum.USUARIO
				.getName());

		return result;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventAccesoAplicacionVO eventoAccesoAplicacion,
			TrazaAuditoriaVO traza) {

		IspacAuditEventTrazaBuilderHandlerHelper
				.populateBasicUserEventTrazaAuditoria(eventoAccesoAplicacion,
						traza);

	}

	/**
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#getTypeHandler()
	 */
	public IspacEventAuditHandlerTypeEnum getTypeHandler() {
		
		return IspacEventAuditHandlerTypeEnum.TRAZA_BUILDER;
	}

}
