package ieci.tdw.ispac.ispaclib.bean;

public class BeanPropertyValidationFmt extends BeanPropertySimpleFmt {
	
	/*
	 * Campo validado de la entidad 
	 */
	protected String validatedField;
	
	/*
	 * Tabla de la entidad de validación
	 */
	protected String validationTable;

	public BeanPropertyValidationFmt() {
		super();
	}

	public String getValidatedField() {
		return validatedField;
	}

	public void setValidatedField(String validatedField) {
		this.validatedField = validatedField;
	}

	public String getValidationTable() {
		return validationTable;
	}

	public void setValidationTable(String validationTable) {
		this.validationTable = validationTable;
	}

}
