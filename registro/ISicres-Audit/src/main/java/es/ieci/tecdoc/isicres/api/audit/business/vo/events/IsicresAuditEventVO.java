package es.ieci.tecdoc.isicres.api.audit.business.vo.events;

import java.util.Date;

import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;


public abstract class IsicresAuditEventVO  implements AuditEventVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1599643363665723579L;
	
	private Date fecha;

	protected Long appId;

	protected String appDescription;
	

	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	public String getAppDescription() {
		return appDescription;
	}
	
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

}
