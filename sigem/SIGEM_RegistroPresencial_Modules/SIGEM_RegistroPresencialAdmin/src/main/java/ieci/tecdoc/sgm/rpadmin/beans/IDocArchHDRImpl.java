package ieci.tecdoc.sgm.rpadmin.beans;
/*$Id*/

public class IDocArchHDRImpl {
	private int id;
	private String name;
	private int type;
	private int acsId;
	
	private int estadoLibro;
	
	public int getEstadoLibro() {
		return estadoLibro;
	}
	public void setEstadoLibro(int estadoLibro) {
		this.estadoLibro = estadoLibro;
	}
	public int getAcsId() {
		return acsId;
	}
	public void setAcsId(int acsId) {
		this.acsId = acsId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
