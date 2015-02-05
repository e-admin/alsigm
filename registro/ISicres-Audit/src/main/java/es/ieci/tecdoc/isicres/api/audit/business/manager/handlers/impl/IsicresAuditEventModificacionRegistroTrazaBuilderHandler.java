package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditTypeEnum;

/**
 * Clase manejadora para la generación de trazas de auditoría en la modificación de un registro
 * @author IECISA
 *
 */
public class IsicresAuditEventModificacionRegistroTrazaBuilderHandler extends IsicresAuditEventAbstractCreacionModificacionAccesoRegistroTrazaBuilderHandler
{

	@Override
	protected IsicresEventAuditTypeEnum getIsicresEventAuditTypeEnum() {
		return IsicresEventAuditTypeEnum.MODIFICACION_REGISTRO;
	}

}
