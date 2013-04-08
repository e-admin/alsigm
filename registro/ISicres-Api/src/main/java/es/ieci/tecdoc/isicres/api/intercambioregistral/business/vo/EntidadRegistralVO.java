package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Clase  que represeta la entidad registral correspondiente con el directorio comun
 * Incialmente lo mapearemos en nuestro modelo de datos en la tabla scr_entityreg
 * @author Iecisa
 *
 */
public class EntidadRegistralVO extends BaseIntercambioRegistralVO {

	/**
	 * idetificador unico
	 */
	protected String id;

	/**
	 * codigo correspondiente con el del directorio comun
	 */
	protected String code;

	/**
	 * nombre correspondiente con el del directorio comun
	 */
	protected String name;

	/**
	 * identificador de mapeo correpondiente con el id de la tabla scr_ofic
	 */
	protected String idOfic;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getIdOfic() {
		return idOfic;
	}
	public void setIdOfic(String idOfic) {
		this.idOfic = idOfic;
	}


}
