package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

public class SicresLibroOficinaImpl {

	public static final int NUMERACION_OFICINA = 2;
	private int id;
	private int idBook;
	private int idOfic;
	private String nameOfic;
	private int deptId;
	private String nameBook;
	private int numeration;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	public int getIdOfic() {
		return idOfic;
	}
	public void setIdOfic(int idOfic) {
		this.idOfic = idOfic;
	}
	public int getNumeration() {
		return numeration;
	}
	public void setNumeration(int numeration) {
		this.numeration = numeration;
	}
	public String getNameBook() {
		return nameBook;
	}
	public void setNameBook(String nameBook) {
		this.nameBook = nameBook;
	}
	public String getNameOfic() {
		return nameOfic;
	}
	public void setNameOfic(String nameOfic) {
		this.nameOfic = nameOfic;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	
	
}
