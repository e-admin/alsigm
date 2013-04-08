package ieci.tecdoc.sgm.rpadmin.beans;


/**
 * Gestión de la Tabla SCR_CADOCS
 */
public class SicresDocumentoTipoAsuntoImpl {
	private int id;
	private int idMatter;
	private String description;
	private int mandatory;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdMatter() {
		return idMatter;
	}
	public void setIdMatter(int idMatter) {
		this.idMatter = idMatter;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMandatory() {
		return mandatory;
	}
	public void setMandatory(int mandatory) {
		this.mandatory = mandatory;
	}
}
