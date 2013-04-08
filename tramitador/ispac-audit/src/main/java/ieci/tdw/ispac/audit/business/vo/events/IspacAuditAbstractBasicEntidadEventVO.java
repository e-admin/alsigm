/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditAbstractBasicEntidadEventVO extends IspacAuditAbstractBasicUserEventVO{
	
	String numExpediente;
	String id;
	String entidadAppId;
	String entidadAppName;
	
	/**
	 * @return el numExpediente
	 */
	public String getNumExpediente() {
		return numExpediente;
	}

	/**
	 * @param numExpediente el numExpediente a fijar
	 */
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	/**
	 * @return el idTramite
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param idTramite el idTramite a fijar
	 */
	public void setId(String idTramite) {
		this.id = idTramite;
	}

	/**
	 * @return el entidadAppId
	 */
	public String getEntidadAppId() {
		return entidadAppId;
	}

	/**
	 * @param entidadAppId el entidadAppId a fijar
	 */
	public void setEntidadAppId(String entidadAppId) {
		this.entidadAppId = entidadAppId;
	}

	/**
	 * @return el entidadAppName
	 */
	public String getEntidadAppName() {
		return entidadAppName;
	}

	/**
	 * @param entidadAppName el entidadAppName a fijar
	 */
	public void setEntidadAppName(String entidadAppName) {
		this.entidadAppName = entidadAppName;
	}
	

}
