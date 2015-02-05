package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class TipoAsuntoVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -7831605265024347752L;

	protected String id;

	protected String codigo;
	protected String descripcion;
	protected boolean habilitado;
	protected boolean disponibleLibroEntrada;
	protected boolean disponibleLibroSalida;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public boolean isDisponibleLibroEntrada() {
		return disponibleLibroEntrada;
	}

	public void setDisponibleLibroEntrada(boolean disponibleLibroEntrada) {
		this.disponibleLibroEntrada = disponibleLibroEntrada;
	}

	public boolean isDisponibleLibroSalida() {
		return disponibleLibroSalida;
	}

	public void setDisponibleLibroSalida(boolean disponibleLibroSalida) {
		this.disponibleLibroSalida = disponibleLibroSalida;
	}

}
