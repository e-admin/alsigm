package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $$Revision$$ 
 * 
 * ${tags} 
 */ 

/**
 *
 *  Clase que contendrá los valores que se podrán usar por ejemplo como datos especifigos
 */
public class ISicresBasicDatosEspecificosValueVO extends ISicresAbstractBaseConnectorVO{

	protected String id;
	
	protected String value;
	
	protected String name;
	
	protected String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
