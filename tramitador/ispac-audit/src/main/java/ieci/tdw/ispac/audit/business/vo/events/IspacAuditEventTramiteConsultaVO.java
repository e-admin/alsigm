/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventTramiteConsultaVO extends IspacAuditAbstractBasicUserEventVO{
	
	String numExpediente;
	String idTramite;
	
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
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite el idTramite a fijar
	 */
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	
	
	

}
