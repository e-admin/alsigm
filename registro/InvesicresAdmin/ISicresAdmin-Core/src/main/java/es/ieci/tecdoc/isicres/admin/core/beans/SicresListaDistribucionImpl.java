package es.ieci.tecdoc.isicres.admin.core.beans;


public class SicresListaDistribucionImpl {
	
	public static final int TIPO_USUARIO=1;
	public static final int TIPO_DEPARTAMENTO=2;
	public static final int TIPO_GRUPO=3;
	public static final int TIPO_UNIDAD_ADMINISTRATIVA=4;
	
	private int id;
	private int idOrgs;
	private int typeDest;
	private int idDest;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDest() {
		return idDest;
	}
	public void setIdDest(int idDest) {
		this.idDest = idDest;
	}
	public int getIdOrgs() {
		return idOrgs;
	}
	public void setIdOrgs(int idOrgs) {
		this.idOrgs = idOrgs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeDest() {
		return typeDest;
	}
	public void setTypeDest(int typeDest) {
		this.typeDest = typeDest;
	}
}
