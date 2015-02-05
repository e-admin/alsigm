package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

public class SicresUserFilterImpl {
	
	public static final int TIPO_OBJETO_USUARIO = 1;
	public static final int TIPO_OBJETO_OFICINA = 2;
	private int idArch;
	private int idUser;
	private String filterDef;
	private int typeObj;
	public String getFilterDef() {
		return filterDef;
	}
	public void setFilterDef(String filterDef) {
		this.filterDef = filterDef;
	}
	public int getIdArch() {
		return idArch;
	}
	public void setIdArch(int idarch) {
		this.idArch = idarch;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int iduser) {
		this.idUser = iduser;
	}
	public int getTypeObj() {
		return typeObj;
	}
	public void setTypeObj(int typeObj) {
		this.typeObj = typeObj;
	}


}
