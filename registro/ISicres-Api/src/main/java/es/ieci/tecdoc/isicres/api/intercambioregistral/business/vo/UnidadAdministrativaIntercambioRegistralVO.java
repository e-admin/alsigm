package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Clase que representa una unidad administrativa (generalmente tras mapearla del modulo comun)
 *
 */
public class UnidadAdministrativaIntercambioRegistralVO {

	protected String id;
	protected String code;
	protected String name;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
