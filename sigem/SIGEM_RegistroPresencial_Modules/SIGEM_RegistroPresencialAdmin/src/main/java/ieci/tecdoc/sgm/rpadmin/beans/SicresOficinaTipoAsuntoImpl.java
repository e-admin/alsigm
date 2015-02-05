package ieci.tecdoc.sgm.rpadmin.beans;

/**
 * Tabla: SCR_CAOFIC
 * @author lucas
 *
 */
public class SicresOficinaTipoAsuntoImpl {
	//Campo de la Tabla
	private int id;
	private int idMatter;
	private int idOfic;
	
	//Campos Axiliares
	private String nameOfic;
	private String codeOfic;
	
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
	public int getIdOfic() {
		return idOfic;
	}
	public void setIdOfic(int idOfic) {
		this.idOfic = idOfic;
	}
	public void setNameOfic(String nameOfic) {
		this.nameOfic = nameOfic;
	}
	public String getNameOfic() {
		return nameOfic;
	}
	public String getCodeOfic() {
		return codeOfic;
	}
	public void setCodeOfic(String codeOfic) {
		this.codeOfic = codeOfic;
	}
	
}
