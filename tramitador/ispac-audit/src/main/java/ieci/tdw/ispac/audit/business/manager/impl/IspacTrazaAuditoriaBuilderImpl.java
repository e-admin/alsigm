/**
 * 
 */
package ieci.tdw.ispac.audit.business.manager.impl;

import ieci.tdw.ispac.audit.business.handlers.IspacAuditoriaEventHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventAccesoAplicacionTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventAvisoAltaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventAvisoBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventAvisoConsultaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventAvisoModificacionTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventBusquedaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventDocumentoAltaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventDocumentoBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventDocumentoConsultaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventDocumentoModificacionTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventEntidadAltaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventEntidadBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventEntidadConsultaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventEntidadModificacionCamposTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventExpedienteAPaeleraTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventExpedienteAltaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventExpedienteBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventFicheroBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventInformeTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventRegDistConsultaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventRegDistModificacionTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventTramiteAltaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventTramiteBajaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventTramiteConsultaTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.handlers.impl.IspacAuditEventTramiteModificacionCamposTrazaBuilderHandler;
import ieci.tdw.ispac.audit.business.vo.enums.IspacEventAuditHandlerTypeEnum;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAccesoAplicacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventBusquedaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventDocumentoModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteAPapeleraVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventFicheroBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventInformeVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventRegDistConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventRegDistModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteConsultaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventVO;
import ieci.tdw.ispac.audit.config.ConfigurationAuditFileKeys;
import ieci.tdw.ispac.audit.config.ConfiguratorAudit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.TrazaAuditoriaBuilder;
import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacTrazaAuditoriaBuilderImpl implements TrazaAuditoriaBuilder {

	public List<TrazaAuditoriaVO> buildTrazas(AuditEventVO auditEvent) {
		List<TrazaAuditoriaVO> result = null;
		if (auditEvent instanceof IspacAuditEventVO) {
			// solo procesamos eventos de auditoria de ispac
			IspacAuditEventVO eventoAuditoriaIspac = (IspacAuditEventVO) auditEvent;
			result = buildTrazasIspac(eventoAuditoriaIspac);

		} else {
			throw new RuntimeException(IspacTrazaAuditoriaBuilderImpl.class.getSimpleName()
					+ " solo soporta objetos de tipo: " + IspacAuditEventVO.class.getName());
		}

		return result;
	}

	/**
	 * Genera las trazas para el evento de auditoria de Ispac
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<TrazaAuditoriaVO> buildTrazasIspac(IspacAuditEventVO auditEvent) {
		List<TrazaAuditoriaVO> result = new ArrayList<TrazaAuditoriaVO>();

		if(ConfiguratorAudit.getInstance().getPropertyBoolean(ConfigurationAuditFileKeys.KEY_AUDITORIA_ENABLE)){		
			List<IspacAuditoriaEventHandler> handlers = getHandlers(auditEvent);
	
			for (Iterator iterator = handlers.iterator(); iterator.hasNext();) {
				IspacAuditoriaEventHandler ispacAuditoriaEventHandler = (IspacAuditoriaEventHandler) iterator
						.next();
				// se ejecutan todos los manejadores del evento pero solo se añaden
				// los referentes a la ejecucion de trazas
				Object handlerResultObj = ispacAuditoriaEventHandler.handle(auditEvent);
	
				if (ispacAuditoriaEventHandler.getTypeHandler() == IspacEventAuditHandlerTypeEnum.TRAZA_BUILDER) {
					result.addAll((Collection<? extends TrazaAuditoriaVO>) handlerResultObj);
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
	protected List<IspacAuditoriaEventHandler> getHandlers(IspacAuditEventVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		boolean skip = false;

		// manejadores para el evento "Acceso a la aplicacion"
		if (!skip && auditEvent instanceof IspacAuditEventAccesoAplicacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventAccesoAplicacionVO) auditEvent));
			skip = true;
		}
		// Manejadores para el evento "Búsqueda"
		if (!skip && auditEvent instanceof IspacAuditEventBusquedaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventBusquedaVO) auditEvent));
			skip = true;
		}
		// Manejadores para el evento "Ejecución de Informes"
		if (!skip && auditEvent instanceof IspacAuditEventInformeVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventInformeVO) auditEvent));
			skip = true;
		}
		// Manejadores para el evento "Expediente - ALTA"
		if (!skip && auditEvent instanceof IspacAuditEventExpedienteAltaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventExpedienteAltaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Expediente - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventExpedienteBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventExpedienteBajaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Expediente - Envío a la papelera"
		if (!skip && auditEvent instanceof IspacAuditEventExpedienteAPapeleraVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventExpedienteAPapeleraVO) auditEvent));
			skip = true;
		}
				
		// Manejadores para el evento "Aviso - ALTA"
		if (!skip && auditEvent instanceof IspacAuditEventAvisoAltaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventAvisoAltaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Aviso - CONSULTA"
		if (!skip && auditEvent instanceof IspacAuditEventAvisoConsultaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventAvisoConsultaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Aviso - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventAvisoBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventAvisoBajaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Aviso - MODIFICACION"
		if (!skip && auditEvent instanceof IspacAuditEventAvisoModificacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventAvisoModificacionVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Trámite - ALTA"
		if (!skip && auditEvent instanceof IspacAuditEventTramiteAltaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventTramiteAltaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Trámite - CONSULTA"
		if (!skip && auditEvent instanceof IspacAuditEventTramiteConsultaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventTramiteConsultaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Trámite - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventTramiteBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventTramiteBajaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Trámite - MODIFICACIÓN"
		if (!skip && auditEvent instanceof IspacAuditEventTramiteModificacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventTramiteModificacionVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Documento - ALTA"
		if (!skip && auditEvent instanceof IspacAuditEventDocumentoAltaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventDocumentoAltaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Documento - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventDocumentoBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventDocumentoBajaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Documento - CONSULTA"
		if (!skip && auditEvent instanceof IspacAuditEventDocumentoConsultaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventDocumentoConsultaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Documento - MODIFICACIÓN"
		if (!skip && auditEvent instanceof IspacAuditEventDocumentoModificacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventDocumentoModificacionVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Documento - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventFicheroBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventFicheroBajaVO) auditEvent));
			skip = true;
		}

		// Manejadores para el evento "Entidad - ALTA"
		if (!skip && auditEvent instanceof IspacAuditEventEntidadAltaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventEntidadAltaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Entidad - MODIFICACIÓN"
		if (!skip && auditEvent instanceof IspacAuditEventEntidadModificacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventEntidadModificacionVO) auditEvent));
			skip = true;
		}
		// Manejadores para el evento "Entidad - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventEntidadBajaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventEntidadBajaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Entidad - BAJA"
		if (!skip && auditEvent instanceof IspacAuditEventEntidadConsultaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventEntidadConsultaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Registro Distribuido - CONSULTA"
		if (!skip && auditEvent instanceof IspacAuditEventRegDistConsultaVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventRegDistConsultaVO) auditEvent));
			skip = true;
		}
		
		// Manejadores para el evento "Registro Distribuido - MODIFICACION"
		if (!skip && auditEvent instanceof IspacAuditEventRegDistModificacionVO) {
			result.addAll(getHandlersEspecificos((IspacAuditEventRegDistModificacionVO) auditEvent));
			skip = true;
		}
		return result;

	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de Acceso a la
	 * aplicación
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventAccesoAplicacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventAccesoAplicacionTrazaBuilderHandler());
		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de "Búsqueda"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventBusquedaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventBusquedaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento de tipo
	 * "Ejecución de Informe"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventInformeVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventInformeTrazaBuilderHandler());
		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento
	 * "Expediente - ALTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventExpedienteAltaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventExpedienteAltaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento
	 * "Expediente - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventExpedienteBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventExpedienteBajaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Metodo encargado de obtener los manejadores del evento
	 * "Expediente - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventExpedienteAPapeleraVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventExpedienteAPaeleraTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * 
	 * Obtiene los manejadores del evento "Aviso - ALTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventAvisoAltaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventAvisoAltaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Aviso - CONSULTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventAvisoConsultaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventAvisoConsultaTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Aviso - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventAvisoBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventAvisoBajaTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Aviso - MODIFICACIÓN"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventAvisoModificacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventAvisoModificacionTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Trámite - ALTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventTramiteAltaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventTramiteAltaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Trámite - CONSULTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventTramiteConsultaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventTramiteConsultaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Trámite - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventTramiteBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventTramiteBajaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Trámite - MODIFICACIÓN"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventTramiteModificacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		// Audita que se ha modificado el trámite pero no indica los campos
		//result.add(new IspacAuditEventTramiteModificacionTrazaBuilderHandler());

		// Audita cada campo que se ha modificado del trámite
		result.add(new IspacAuditEventTramiteModificacionCamposTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Documento - ALTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventDocumentoAltaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		// Audita la creación de un documento
		result.add(new IspacAuditEventDocumentoAltaTrazaBuilderHandler());

		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Documento - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventDocumentoBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		// Audita la creación de un documento
		result.add(new IspacAuditEventDocumentoBajaTrazaBuilderHandler());

		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Documento - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventFicheroBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		// Audita la creación de un documento
		result.add(new IspacAuditEventFicheroBajaTrazaBuilderHandler());

		return result;
	}

	
	/**
	 * Obtiene los manejadores del evento "Documento - CONSULTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventDocumentoConsultaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();

		result.add(new IspacAuditEventDocumentoConsultaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Documento - MODIFICACIÓN"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventDocumentoModificacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventDocumentoModificacionTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Documento - MODIFICACIÓN"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventEntidadAltaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventEntidadAltaTrazaBuilderHandler());
		return result;
	}

	/**
	 * Obtiene los manejadores del evento "Entidad - MODIFICACIÓN"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventEntidadModificacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventEntidadModificacionCamposTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Entidad - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventEntidadBajaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventEntidadBajaTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Entidad - BAJA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventEntidadConsultaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventEntidadConsultaTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Registro Distribuido - CONSULTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventRegDistConsultaVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventRegDistConsultaTrazaBuilderHandler());
		return result;
	}
	
	/**
	 * Obtiene los manejadores del evento "Registro Distribuido - CONSULTA"
	 * 
	 * @param auditEvent
	 * @return
	 */
	protected List<IspacAuditoriaEventHandler> getHandlersEspecificos(
			IspacAuditEventRegDistModificacionVO auditEvent) {
		List<IspacAuditoriaEventHandler> result = new ArrayList<IspacAuditoriaEventHandler>();
		result.add(new IspacAuditEventRegDistModificacionTrazaBuilderHandler());
		return result;
	}
}
