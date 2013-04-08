package es.ieci.tecdoc.isicres.terceros.business.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 *
 * @author IECISA
 *
 */
public class InteresadoVO extends Entity {

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public BaseTerceroVO getTercero() {
		return tercero;
	}

	public void setTercero(BaseTerceroVO tercero) {
		this.tercero = tercero;
	}

	public BaseDireccionVO getDireccionNotificacion() {
		return direccionNotificacion;
	}

	public void setDireccionNotificacion(BaseDireccionVO direccionNotificacion) {
		this.direccionNotificacion = direccionNotificacion;
	}

	public RepresentanteInteresadoVO getRepresentante() {
		return representante;
	}

	public void setRepresentante(RepresentanteInteresadoVO representante) {
		this.representante = representante;
	}

	// Members
	protected String idRegistro;

	protected String idLibro;

	protected int orden;

	protected boolean principal;

	protected BaseTerceroVO tercero;

	protected BaseDireccionVO direccionNotificacion;

	protected RepresentanteInteresadoVO representante;

	protected String nombre;

	private static final long serialVersionUID = 2290234428256162323L;

}
