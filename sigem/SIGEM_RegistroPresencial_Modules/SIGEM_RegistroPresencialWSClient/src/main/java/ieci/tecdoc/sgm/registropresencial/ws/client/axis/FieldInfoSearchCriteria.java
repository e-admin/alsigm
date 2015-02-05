package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

/**
 * 
 * @author 66575267
 * 
 */
public class FieldInfoSearchCriteria {

	private String fieldId = null;

	private String value = null;

	private String operator = null;

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
