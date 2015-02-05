/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

import java.util.Date;

import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditEventVO;

/**
 * 
 * Clase base, abstracta, para agrupar los atributos básicos de los eventos de InvesFlow
 * 
 * @author IECISA
 * @version $Revision$
 *
 */
public abstract class IspacAuditEventVO implements AuditEventVO{
	
	private Date fecha;

	protected Long appId;

	protected String appDescription;

	/**
	 * @return el fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha el fecha a fijar
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return el appId
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 * @param appId el appId a fijar
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * @return el appDescription
	 */
	public String getAppDescription() {
		return appDescription;
	}

	/**
	 * @param appDescription el appDescription a fijar
	 */
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}
	
	

}
