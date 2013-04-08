package salas.vos;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaRegistroConsultaSalaVO extends RegistroConsultaSalaVO {

	private static final long serialVersionUID = 1L;

	private String[] idsArchivo;
	private boolean registrado = false;
	private String numeroDoc_like;
	private Date fechaIniEntrada = null;
	private Date fechaFinEntrada = null;
	private Date fechaIniSalida = null;
	private Date fechaFinSalida = null;

	public void setIdsArchivo(String[] idsArchivo) {
		this.idsArchivo = idsArchivo;
	}

	public String[] getIdsArchivo() {
		return idsArchivo;
	}

	public boolean isRegistrado() {
		return registrado;
	}

	public void setRegistrado(boolean registrado) {
		this.registrado = registrado;
	}

	public String getNumeroDoc_like() {
		return numeroDoc_like;
	}

	public void setNumeroDoc_like(String numeroDocLike) {
		numeroDoc_like = numeroDocLike;
	}

	public Date getFechaIniEntrada() {
		return fechaIniEntrada;
	}

	public void setFechaIniEntrada(Date fechaIniEntrada) {
		this.fechaIniEntrada = fechaIniEntrada;
	}

	public Date getFechaFinEntrada() {
		return fechaFinEntrada;
	}

	public void setFechaFinEntrada(Date fechaFinEntrada) {
		this.fechaFinEntrada = fechaFinEntrada;
	}

	public Date getFechaIniSalida() {
		return fechaIniSalida;
	}

	public void setFechaIniSalida(Date fechaIniSalida) {
		this.fechaIniSalida = fechaIniSalida;
	}

	public Date getFechaFinSalida() {
		return fechaFinSalida;
	}

	public void setFechaFinSalida(Date fechaFinSalida) {
		this.fechaFinSalida = fechaFinSalida;
	}
}