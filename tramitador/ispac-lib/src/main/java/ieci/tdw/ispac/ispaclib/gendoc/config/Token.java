package ieci.tdw.ispac.ispaclib.gendoc.config;

public class Token {

	protected String name = null;
	protected String value = null;
	protected String valueType = null;

	public Token(String name, String value) {
		this.name = name;
		this.value = value;
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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}
