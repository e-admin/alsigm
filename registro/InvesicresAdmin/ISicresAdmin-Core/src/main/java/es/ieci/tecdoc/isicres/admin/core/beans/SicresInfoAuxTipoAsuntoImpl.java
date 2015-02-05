package es.ieci.tecdoc.isicres.admin.core.beans;

/**
 * Gestión de la tabla: SCR_CAAUX
 */
public class SicresInfoAuxTipoAsuntoImpl {
	private int id;
	private int idMatter;
	private String datosAux;
	
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
	public String getDatosAux() {
		return datosAux;
	}
	public void setDatosAux(String datosAux) {
		this.datosAux = datosAux;
	}
}
