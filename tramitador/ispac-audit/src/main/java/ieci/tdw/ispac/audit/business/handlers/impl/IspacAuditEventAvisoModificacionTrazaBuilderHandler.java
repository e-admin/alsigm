/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.IspacAuditoriaValorModificado;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;

/**
 * 
 * Manejador para gestionar los eventos de tipo "Expediente - Alta"
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventAvisoModificacionTrazaBuilderHandler implements IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventAvisoModificacionVO evento = (IspacAuditEventAvisoModificacionVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.addAll(generateTrazasPorCampoModificado(evento));
		//result.add(generateTrazaConsulta(evento));
		return result;
	}

	/**
	 * Genera una traza por cada campo modificado
	 * 
	 * @param evento
	 * @return
	 */
	protected List<TrazaAuditoriaVO> generateTrazasPorCampoModificado(
			IspacAuditEventAvisoModificacionVO evento) {

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
			result.setEventType(new Long(IspacEventAuditTypeEnum.AVISO_MODIFICACION.getValue()));

			// descripción del evento que se produjo con info adicional
			String eventDescription = "Aviso - MODIFICACIÓN";
			result.setEventDescription(eventDescription);

			// tipo de objeto de negocio que auditamos registro, campo de
			// regitro,
			// usuario, campo de usuario, etc ..
			result.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.AVISO.getValue()));
			result.setObjectTypeDescription(IspacObjectAuditTypeEnum.AVISO.getName());

			result.setObjectId(evento.getIdAviso());
			result.setObjectField(valorModificado.getFieldName());
			result.setOldValue(valorModificado.getOldValue());
			result.setNewValue(valorModificado.getNewValue());
			results.add(result);
		}
		return results;

	}


	protected TrazaAuditoriaVO generateTrazaConsulta(IspacAuditEventAvisoModificacionVO evento) {

		
		TrazaAuditoriaVO traza = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(evento, traza);

		// Tipo de evento Expediente - Alta
		traza.setEventType(new Long(IspacEventAuditTypeEnum.AVISO_MODIFICACION.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Aviso - Modificación";
		traza.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de
		// regitro,
		// usuario, campo de usuario, etc ..
		traza.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.AVISO.getValue()));

		traza.setObjectTypeDescription(IspacObjectAuditTypeEnum.AVISO.getName());
		
		traza.setObjectId(evento.getIdAviso());

		return traza;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventAvisoModificacionVO eventoAccesoAplicacion, TrazaAuditoriaVO traza) {

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
