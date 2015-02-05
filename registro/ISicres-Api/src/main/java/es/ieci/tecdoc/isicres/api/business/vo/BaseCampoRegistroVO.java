package es.ieci.tecdoc.isicres.api.business.vo;

public abstract class BaseCampoRegistroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8399498377817846461L;

	/**
	 * Nombre del campo en la base de datos.
	 * 
	 * El nombre en la base de datos sera FLD + name
	 * 
	 * (Ejemplo: si name = 22 => el campo en la base de datos sera FLD22)
	 */
	protected String name;
	/**
	 * Value del campo
	 */
	protected String value;

	public BaseCampoRegistroVO() {
		super();
	}

	/**
	 * Constructor con parámetros de la clase.
	 * 
	 * @param aName
	 *            nombre del campo
	 * @param aValue
	 *            valor del campo
	 */
	public BaseCampoRegistroVO(String aName, String aValue) {
		super();
		this.name = aName;
		this.value = aValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}