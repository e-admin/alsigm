package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;

/**
 * Manejador que genera las trazas para el evento de creacion de registro
 * @author IECISA
 *
 */
public class IsicresAuditEventCreacionRegistroTrazaBuilderHandler extends IsicresAuditEventAbstractCreacionModificacionAccesoRegistroTrazaBuilderHandler
{

	@Override
	protected IsicresEventAuditTypeEnum getIsicresEventAuditTypeEnum() {
		return IsicresEventAuditTypeEnum.CREACION_REGISTRO;
	}

	

}
