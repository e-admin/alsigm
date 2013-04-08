/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventExpedienteBajaVO extends IspacAuditAbstractBasicUserEventVO{
	
	String numExpediente;
	String idProcess;

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
	 * @return el idProcess
	 */
	public String getIdProcess() {
		return idProcess;
	}

	/**
	 * @param idProcess el idProcess a fijar
	 */
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
	
	
	

}
