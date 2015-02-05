/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class IspacAuditEventBusquedaVO extends IspacAuditAbstractBasicUserEventVO {
	
	private String consulta;

	/**
	 * @return el consulta
	 */
	public String getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta el consulta a fijar
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	
	

}
