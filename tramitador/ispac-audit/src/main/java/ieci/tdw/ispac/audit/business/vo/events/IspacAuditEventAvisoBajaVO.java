/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventAvisoBajaVO extends IspacAuditAbstractBasicUserEventVO {
	
	private String idAviso;
	private String newValue;

	/**
	 * @return el idAviso
	 */
	public String getIdAviso() {
		return idAviso;
	}

	/**
	 * @param idAviso el idAviso a fijar
	 */
	public void setIdAviso(String idAviso) {
		this.idAviso = idAviso;
	}

	/**
	 * @return el newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue el newValue a fijar
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	

}
