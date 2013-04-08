/**
 * 
 */
package ieci.tdw.ispac.audit.business.vo;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class IspacAuditoriaValorModificado {

	
private String fieldName;
	
	private String oldValue;
	
	private String newValue;

	/**
	 * @return el fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName el fieldName a fijar
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
