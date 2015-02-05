package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Oficina Registrar
 * 
 * 
 */
public class Oficina {

	private int id;
	private String codigo;
	private String nombre;
	private String abreviatura;
	private String entidadRegistral;

	/**
	 * @return
	 */
	public String getAbreviatura() {
		return abreviatura;
	}

	/**
	 * @param abreviatura
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	/**
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return
	 */
	public String getEntidadRegistral() {
		return entidadRegistral;
	}

	/**
	 * @param entidadRegistral
	 */
	public void setEntidadRegistral(String entidadRegistral) {
		this.entidadRegistral = entidadRegistral;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
