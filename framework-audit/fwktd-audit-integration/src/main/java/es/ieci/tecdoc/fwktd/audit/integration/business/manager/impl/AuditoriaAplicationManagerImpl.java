package es.ieci.tecdoc.fwktd.audit.integration.business.manager.impl;

import java.util.Iterator;
import java.util.List;

import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.AuditoriaAplicationManager;
import es.ieci.tecdoc.fwktd.audit.integration.business.manager.TrazaAuditoriaBuilder;
import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;

/**
 * @author IECISA
 * 
 */
public abstract class AuditoriaAplicationManagerImpl implements
		AuditoriaAplicationManager {

	protected AuditoriaService auditoriaService;

	protected TrazaAuditoriaBuilder trazaAuditoriaBuilder;

	public void audit(AuditEventVO evento) {
		List<TrazaAuditoriaVO> trazaAuditorias = getTrazaAuditoriaBuilder()
				.buildTrazas(evento);
		if (trazaAuditorias != null) {

			for (Iterator iterator = trazaAuditorias.iterator(); iterator
					.hasNext();) {
				TrazaAuditoriaVO trazaAuditoriaVO = (TrazaAuditoriaVO) iterator
						.next();

				getAuditoriaService().audit(trazaAuditoriaVO);
			}
		}

	}

	public AppAuditoriaVO getAppAuditoria(String descripcion){
		return auditoriaService.getAppAuditoria(descripcion);
	}
	
	public AuditoriaService getAuditoriaService() {
		return auditoriaService;
	}

	public TrazaAuditoriaBuilder getTrazaAuditoriaBuilder() {
		return trazaAuditoriaBuilder;
	}

	public void setAuditoriaService(AuditoriaService auditoriaService) {
		this.auditoriaService = auditoriaService;
	}

	public void setTrazaAuditoriaBuilder(
			TrazaAuditoriaBuilder trazaAuditoriaBuilder) {
		this.trazaAuditoriaBuilder = trazaAuditoriaBuilder;
	}

}
