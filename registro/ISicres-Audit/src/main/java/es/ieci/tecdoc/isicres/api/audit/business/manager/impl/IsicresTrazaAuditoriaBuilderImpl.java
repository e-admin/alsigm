package es.ieci.tecdoc.isicres.api.audit.business.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.TrazaAuditoriaBuilder;
import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.IsicresAuditoriaEventHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventAccesoAplicacionTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventAccesoRegistroTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventCreacionCampoRegistroTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventCreacionRegistroTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventModificacionCampoRegistroTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl.IsicresAuditEventModificacionRegistroTrazaBuilderHandler;
import es.ieci.tecdoc.isicres.api.audit.business.vo.enums.IsicresEventAuditHandlerTypeEnum;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoAplicacionVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventAccesoRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventCreacionRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventModificacionRegistroVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditEventVO;
import es.ieci.tecdoc.isicres.api.audit.config.ConfigurationAuditFileKeys;
import es.ieci.tecdoc.isicres.api.audit.config.ConfiguratorAudit;

/**
 * Clase encargada de crear las trazas que generan los eventos de auditoría del
 * producto de registro
 *
 * @author Iecisa
 *
 */
public class IsicresTrazaAuditoriaBuilderImpl implements TrazaAuditoriaBuilder {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.fwktd.audit.integration.business.manager.TrazaAuditoriaBuilder
	 * #
	 * buildTrazas(es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO
	 * )
	 */
	public List<TrazaAuditoriaVO> buildTrazas(AuditEventVO auditEvent) {
		List<TrazaAuditoriaVO> result = null;
		if (auditEvent instanceof IsicresAuditEventVO) {
			// solo procesamos eventos de auditoria de registro
			IsicresAuditEventVO eventoAuditoriaRegistro = (IsicresAuditEventVO) auditEvent;
			result = buildTrazasRegistro(eventoAuditoriaRegistro);
		} else {
			throw new RuntimeException(IsicresTrazaAuditoriaBuilderImpl.class
					.getSimpleName()
					+ " solo soporta objetos de tipo: "
					+ IsicresAuditEventVO.class.getName());
		}

		return result;
	}

	/**
	 * Genera las trazas para el evento de auditoria de registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<TrazaAuditoriaVO> buildTrazasRegistro(
			IsicresAuditEventVO auditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList();

		if(ConfiguratorAudit.getInstance().getPropertyBoolean(ConfigurationAuditFileKeys.KEY_AUDITORIA_ENABLE)){
			List<IsicresAuditoriaEventHandler> builders = getHandlers(auditEvent);

			for (Iterator iterator = builders.iterator(); iterator.hasNext();) {
				IsicresAuditoriaEventHandler isicresAuditoriaEventHandler = (IsicresAuditoriaEventHandler) iterator
						.next();
				// se ejecutan todos los manejadores del evento pero solo se añaden
				// los referentes a la ejecucion de trazas
				Object halderResult = isicresAuditoriaEventHandler
						.handle(auditEvent);

				if (isicresAuditoriaEventHandler.getTypeHandler() == IsicresEventAuditHandlerTypeEnum.TRAZA_BUILDER) {
					result
							.addAll((Collection<? extends TrazaAuditoriaVO>) halderResult);
				}
			}
		}

		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores de eventos relacionados con
	 * los eventos de auditoria del producto registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<IsicresAuditoriaEventHandler> getHandlers(
			IsicresAuditEventVO auditEvent) {
		List<IsicresAuditoriaEventHandler> result = new ArrayList<IsicresAuditoriaEventHandler>();

		boolean skip = false;


		// manejadores para el evento acceso a la aplicacion
		if (!skip && auditEvent instanceof IsicresAuditEventAccesoAplicacionVO) {
			result
					.addAll(getHandlersEspecificos((IsicresAuditEventAccesoAplicacionVO) auditEvent));
			skip = true;
		}

		// manejadores para el evento de Creacion
		if (!skip && auditEvent instanceof IsicresAuditEventCreacionRegistroVO) {
			result
					.addAll(getHandlersEspecificos((IsicresAuditEventCreacionRegistroVO) auditEvent));
			skip = true;
		}

		// manejadores para el evento de modificacion
		if (!skip && auditEvent instanceof IsicresAuditEventModificacionRegistroVO) {
			result
					.addAll(getHandlersEspecificos((IsicresAuditEventModificacionRegistroVO) auditEvent));
			skip = true;
		}

		// manejadores para el evento de consulta de registro
		if (!skip && auditEvent instanceof IsicresAuditEventAccesoRegistroVO) {
			result
					.addAll(getHandlersEspecificos((IsicresAuditEventAccesoRegistroVO) auditEvent));
			skip = true;
		}

		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de acceso a la aplicación de
	 * registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<IsicresAuditoriaEventHandler> getHandlersEspecificos(
			IsicresAuditEventAccesoAplicacionVO auditEvent) {
		List<IsicresAuditoriaEventHandler> result = new ArrayList<IsicresAuditoriaEventHandler>();
		result.add(new IsicresAuditEventAccesoAplicacionTrazaBuilderHandler());
		return result;
	}


	/**
	 * Metodo encargado de obtener los manejadores del evento de creacion de
	 * registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<IsicresAuditoriaEventHandler> getHandlersEspecificos(
			IsicresAuditEventCreacionRegistroVO auditEvent) {

		// añadimos los manejadores que queramos, podriamos hacer esto
		// configurable y añadir más o menos en funcion de configuracion
		List<IsicresAuditoriaEventHandler> result = new ArrayList<IsicresAuditoriaEventHandler>();
		//traza de creacion de registro
		result.add(new IsicresAuditEventCreacionRegistroTrazaBuilderHandler());
		//traza para creacion de campo de registro (más detalle)
		result.add(new IsicresAuditEventCreacionCampoRegistroTrazaBuilderHandler());

		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de modificación de
	 * registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<IsicresAuditoriaEventHandler> getHandlersEspecificos(
			IsicresAuditEventModificacionRegistroVO auditEvent) {

		// añadimos los manejadores que queramos, podriamos hacer esto
		// configurable y añadir más o menos en funcion de configuracion
		List<IsicresAuditoriaEventHandler> result = new ArrayList<IsicresAuditoriaEventHandler>();
		//traza de creacion de registro
		result.add(new IsicresAuditEventModificacionRegistroTrazaBuilderHandler());
		//traza para creacion de campo de registro (más detalle)
		result.add(new IsicresAuditEventModificacionCampoRegistroTrazaBuilderHandler());

		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de modificación de
	 * registro
	 *
	 * @param auditEvent
	 * @return
	 */
	protected List<IsicresAuditoriaEventHandler> getHandlersEspecificos(
			IsicresAuditEventAccesoRegistroVO auditEvent) {

		// añadimos los manejadores que queramos, podriamos hacer esto
		// configurable y añadir más o menos en funcion de configuracion
		List<IsicresAuditoriaEventHandler> result = new ArrayList<IsicresAuditoriaEventHandler>();
		//traza de creacion de registro
		result.add(new IsicresAuditEventAccesoRegistroTrazaBuilderHandler());

		return result;
	}



}
