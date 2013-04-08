/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventExpedienteAltaVO extends IspacAuditAbstractBasicUserEventVO{
	
	String numExpediente;
	String idProceso;

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
	 * @return el idProceso
	 */
	public String getIdProceso() {
		return idProceso;
	}

	/**
	 * @param idProceso el idProceso a fijar
	 */
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	
	
	

}
