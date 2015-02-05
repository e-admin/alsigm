package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

/**
 *
 * @author IECISA
 *
 */
public class EDireccion extends Direccion {

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEliminar() {
		return eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	protected String tipo;
	protected String eliminar;
	private static final long serialVersionUID = -9201379024904914296L;

}
