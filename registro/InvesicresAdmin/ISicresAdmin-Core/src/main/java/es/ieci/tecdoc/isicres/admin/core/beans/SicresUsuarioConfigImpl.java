package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

public class SicresUsuarioConfigImpl {
	/**
	 * Valor por defecto de la columna CN
	 */
	public static final String DEFAULT_VALUE_CN_DATA = "<Configuration></Configuration>";

	
	private int userId;
	private String data;
	private int idOficPref;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getIdOficPref() {
		return idOficPref;
	}
	public void setIdOficPref(int idOficPref) {
		this.idOficPref = idOficPref;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
