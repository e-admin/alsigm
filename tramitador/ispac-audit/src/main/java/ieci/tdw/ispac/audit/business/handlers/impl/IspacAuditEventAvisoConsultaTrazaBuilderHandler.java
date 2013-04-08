/**
 * 
 */
package ieci.tdw.ispac.audit.business.handlers.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.enums.IspacObjectAuditTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoConsultaVO;
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
public class IspacAuditEventAvisoConsultaTrazaBuilderHandler implements IspacAuditoriaEventHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler#handle(ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO)
	 */
	public Object handle(IspacAuditEventVO ispacAuditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		IspacAuditEventAvisoConsultaVO evento = (IspacAuditEventAvisoConsultaVO) ispacAuditEvent;

		// Crear traza de auditoria de acceso a la aplicacion
		result.addAll(generateTrazaConsultaIndividual(evento));
		//result.add(generateTrazaConsulta(evento));
		return result;
	}

	/**
	 * Genera una traza por cada aviso consultado
	 * 
	 * @param evento
	 * @return
	 */
	protected Collection<TrazaAuditoriaVO> generateTrazaConsultaIndividual(
			IspacAuditEventAvisoConsultaVO evento) {

		List<TrazaAuditoriaVO> result = new LinkedList<TrazaAuditoriaVO>();

		Map avisos = evento.getAvisos();

		Set avisosSet = avisos.keySet();
		Iterator iterAvisos = avisosSet.iterator();
		while (iterAvisos.hasNext()) {
			String key = (String) iterAvisos.next();

			TrazaAuditoriaVO traza = new TrazaAuditoriaVO();

			populateBasicUserEventTrazaAuditoria(evento, traza);

			// Tipo de evento Expediente - Alta
			traza.setEventType(new Long(IspacEventAuditTypeEnum.AVISO_CONSULTA.getValue()));

			// descripción del evento que se produjo con info adicional
			String eventDescription = "Aviso - Consulta";
			traza.setEventDescription(eventDescription);

			// tipo de objeto de negocio que auditamos registro, campo de
			// regitro,
			// usuario, campo de usuario, etc ..
			traza.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.AVISO.getValue()));

			traza.setObjectTypeDescription(IspacObjectAuditTypeEnum.AVISO.getName());

			traza.setObjectId(key);
			traza.setNewValue(avisos.get(key).toString());
			result.add(traza);

		}
		return result;
	}

	/**
	 * Audita la consulta de avisos introduciendo en NEW_VALUE los
	 * identificadores de los avisos separados por comas.
	 * 
	 * @param evento
	 * @return
	 */
	protected TrazaAuditoriaVO generateTrazaConsulta(IspacAuditEventAvisoConsultaVO evento) {

		TrazaAuditoriaVO traza = new TrazaAuditoriaVO();

		populateBasicUserEventTrazaAuditoria(evento, traza);

		// Tipo de evento Expediente - Alta
		traza.setEventType(new Long(IspacEventAuditTypeEnum.AVISO_CONSULTA.getValue()));

		// descripción del evento que se produjo con info adicional
		String eventDescription = "Aviso - Consulta";
		traza.setEventDescription(eventDescription);

		// tipo de objeto de negocio que auditamos registro, campo de
		// regitro,
		// usuario, campo de usuario, etc ..
		traza.setObjectType(String.valueOf(IspacObjectAuditTypeEnum.AVISO.getValue()));

		traza.setObjectTypeDescription(IspacObjectAuditTypeEnum.AVISO.getName());

		Map avisos = evento.getAvisos();
		StringBuffer idAvisos = new StringBuffer();
		if (avisos != null && !avisos.isEmpty()) {
			Iterator iterAvisos = avisos.keySet().iterator();

			while (iterAvisos.hasNext()) {
				String idAviso = (String) iterAvisos.next();
				idAvisos.append(idAviso);
				if (iterAvisos.hasNext()) {
					idAvisos.append(",");
				}
			}
			traza.setNewValue(idAvisos.toString());
		}

		return traza;
	}

	protected void populateBasicUserEventTrazaAuditoria(
			IspacAuditEventAvisoConsultaVO eventoAccesoAplicacion, TrazaAuditoriaVO traza) {

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
