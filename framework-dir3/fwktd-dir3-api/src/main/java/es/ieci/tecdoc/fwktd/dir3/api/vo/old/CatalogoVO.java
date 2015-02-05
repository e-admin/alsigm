package es.ieci.tecdoc.fwktd.dir3.api.vo.old;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Información de catálogo de elementos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CatalogoVO extends Entity {

	private static final long serialVersionUID = 2410892171903543629L;

	private String nombre;
	private String estado;

	private Date fechaAlta;
	private Date fechaModificacion;

	private Date fechaInicioVigencia;
	private Date fechaFinVigencia;

	/**
	 * Constructor.
	 */
	public CatalogoVO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}
}
