package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Datos de la tabla axpageh
 *
 */
public class InfoRegistroInfoDocumentoVO {

	public Integer id;
	public String nombre;
	public String extension; //Campo loc
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	
}
