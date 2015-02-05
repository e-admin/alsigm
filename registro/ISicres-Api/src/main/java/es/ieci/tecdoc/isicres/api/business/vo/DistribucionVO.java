package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DistribucionVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8057159014208685621L;

	protected String id;
	protected BaseRegistroVO registro;

	protected Date fechaDistribucion;

	/**
	 * Estado actual de la distribución
	 */
	protected EstadoDistribucionVO estado;
	protected String mensajeDistribucion;
	protected ImplicadoDistribucionVO origenDistribucion;
	protected ImplicadoDistribucionVO destinoDistribucion;

	/**
	 * Listado de objetos de tipo <code>EstadoDistribucionVO</code> que sera el
	 * histórico de los estados por los que ha pasado la distribucion
	 */
	protected List historialDistribucion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BaseRegistroVO getRegistro() {
		if (null == registro) {
			registro = new BaseRegistroVO();
		}
		return registro;
	}

	public void setRegistro(BaseRegistroVO registro) {
		this.registro = registro;
	}

	public EstadoDistribucionVO getEstado() {
		if (null == estado) {
			estado = new EstadoDistribucionVO();
		}
		return estado;
	}

	public void setEstado(EstadoDistribucionVO estado) {
		this.estado = estado;
	}

	public String getMensajeDistribucion() {
		return mensajeDistribucion;
	}

	public void setMensajeDistribucion(String mensajeDistribucion) {
		this.mensajeDistribucion = mensajeDistribucion;
	}

	public ImplicadoDistribucionVO getOrigenDistribucion() {
		if (null == origenDistribucion) {
			origenDistribucion = new ImplicadoDistribucionVO();
		}
		return origenDistribucion;
	}

	public void setOrigenDistribucion(ImplicadoDistribucionVO origenDistribucion) {
		this.origenDistribucion = origenDistribucion;
	}

	public ImplicadoDistribucionVO getDestinoDistribucion() {
		if (null == destinoDistribucion) {
			destinoDistribucion = new ImplicadoDistribucionVO();
		}
		return destinoDistribucion;
	}

	public void setDestinoDistribucion(
			ImplicadoDistribucionVO destinoDistribucion) {
		this.destinoDistribucion = destinoDistribucion;
	}

	public List getHistorialDistribucion() {
		if (null == historialDistribucion) {
			historialDistribucion = new ArrayList();
		}
		return historialDistribucion;
	}

	public void setHistorialDistribucion(List historialDistribucion) {
		this.historialDistribucion = historialDistribucion;
	}

	public Date getFechaDistribucion() {
		return fechaDistribucion;
	}

	public void setFechaDistribucion(Date fechaDistribucion) {
		this.fechaDistribucion = fechaDistribucion;
	}

}
