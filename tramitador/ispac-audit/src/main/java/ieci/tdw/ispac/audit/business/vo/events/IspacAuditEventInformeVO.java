/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventInformeVO extends IspacAuditAbstractBasicUserEventVO {

	private String informeEjecutado;

	/**
	 * @return el informeEjecutado
	 */
	public String getInformeEjecutado() {
		return informeEjecutado;
	}

	/**
	 * @param informeEjecutado el informeEjecutado a fijar
	 */
	public void setInformeEjecutado(String informeEjecutado) {
		this.informeEjecutado = informeEjecutado;
	}
	
	

}
