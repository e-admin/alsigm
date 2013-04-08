/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo.events;

import java.util.Map;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditEventRegDistModificacionVO extends IspacAuditAbstractBasicUserEventVO {
	
	String idRegistroDistribuido;
	String oldValue;
	String newValue;
	/**
	 * @return el idRegistroDistribuido
	 */
	public String getIdRegistroDistribuido() {
		return idRegistroDistribuido;
	}
	/**
	 * @param idRegistroDistribuido el idRegistroDistribuido a fijar
	 */
	public void setIdRegistroDistribuido(String idRegistroDistribuido) {
		this.idRegistroDistribuido = idRegistroDistribuido;
	}
	/**
	 * @return el oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}
	/**
	 * @param oldValue el oldValue a fijar
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
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
