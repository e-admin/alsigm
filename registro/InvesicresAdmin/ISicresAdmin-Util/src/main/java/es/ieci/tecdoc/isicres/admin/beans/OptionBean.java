package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Operador sobre un campo de registro
 * 
 * Se utiliza principalmente para la gestión de filtros
 * 
 */
public class OptionBean {

	private String codigo;
	private String descripcion;

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
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
