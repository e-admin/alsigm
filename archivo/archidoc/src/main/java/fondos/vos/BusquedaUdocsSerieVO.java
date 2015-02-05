/**
 *
 */
package fondos.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaUdocsSerieVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String codigo = null;
	private String titulo = null;
	private String productor = null;
	private String numeroExpediente = null;
	private Date fechaIniIni = null;
	private Date fechaIniFin = null;
	private Date fechaFinIni = null;
	private Date fechaFinFin = null;

	public String getCodigo() {
		return codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getProductor() {
		return productor;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public Date getFechaIniIni() {
		return fechaIniIni;
	}

	public Date getFechaIniFin() {
		return fechaIniFin;
	}

	public Date getFechaFinIni() {
		return fechaFinIni;
	}

	public Date getFechaFinFin() {
		return fechaFinFin;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setFechaIniIni(Date fechaIniIni) {
		this.fechaIniIni = fechaIniIni;
	}

	public void setFechaIniFin(Date fechaIniFin) {
		this.fechaIniFin = fechaIniFin;
	}

	public void setFechaFinIni(Date fechaFinIni) {
		this.fechaFinIni = fechaFinIni;
	}

	public void setFechaFinFin(Date fechaFinFin) {
		this.fechaFinFin = fechaFinFin;
	}
}
