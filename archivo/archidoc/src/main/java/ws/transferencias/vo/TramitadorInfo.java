/**
 *
 */
package ws.transferencias.vo;

import java.io.Serializable;



public class TramitadorInfo implements Serializable{

	public TramitadorInfo(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public TramitadorInfo() {
		super();
	}
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo el codigo a fijar
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
