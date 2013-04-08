/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.IspacAuditoriaValorModificado;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

import java.util.ArrayList;
import java.util.Iterator;
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
public class IspacAuditEventEntidadModificacionCamposTrazaBuilderHandler implements
		IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventEntidadModificacionVO evento = (IspacAuditEventEntidadModificacionVO) ispacAuditEvent;				
		result.addAll(generateTrazasPorCampoModificado(evento));
		return result;
	}

	/**
	 * Genera una traza por cada campo modificado
	 * 
	 * @param evento
	 * @return
	 */
	protected List<TrazaAuditoriaVO> generateTrazasPorCampoModificado(
			IspacAuditEventEntidadModificacionVO evento) {

		List<TrazaAuditoriaVO> results = new ArrayList<TrazaAuditoriaVO>();
		List<IspacAuditoriaValorModificado> valoresModificados = evento.getValoresModificados();

		Iterator<IspacAuditoriaValorModificado> iterValoresModificados = valoresModificados
				.iterator();
		while (iterValoresModificados.hasNext()) {
			IspacAuditoriaValorModificado valorModificado = (IspacAuditoriaValorModificado) iterValoresModificados
					.next();
			TrazaAuditoriaVO result = new TrazaAuditoriaVO();
			populateBasicUserEventTrazaAuditoria(evento, result);

			// Tipo de evento Expediente - Alta
			result.setEventType(new Long(IspacEventAuditTypeEnum.ENTIDAD_MODIFICACION.getValue()));

			// descripción del evento que se produjo con info adicional
			String eventDescription = "Entidad - MODIFICACIÓN";
			result.setEventDescription(eventDescription);

			// tipo de objeto de negocio que auditamos registro, campo de
			// regitro,
			// usuario, campo de usuario, etc ..
			result.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.EXPEDIENTE.getValue()));

			result.setObjectTypeDescription(IspacObjectAuditTypeEnum.EXPEDIENTE.getName());

			// TODO: ¿Añadir el número de expediente en el objectId de la
			// auditoría?
			result.setObjectId(evento.getEntidadAppId()+"-"+evento.getId()+"-"+evento.getNumExpediente());

			result.setObjectField(valorModificado.getFieldName());
			result.setOldValue(valorModificado.getOldValue());
			result.setNewValue(valorModificado.getNewValue());
			results.add(result);
		}
		return results;

	}

	
	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventEntidadModificacionVO evento, TrazaAuditoriaVO traza) {

		IspacAuditEventTrazaBuilderHandlerHelper
				.populateBasicUserEventTrazaAuditoria(evento, traza);

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
