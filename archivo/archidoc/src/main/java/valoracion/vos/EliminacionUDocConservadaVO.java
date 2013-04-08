package valoracion.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Clase que encapsula la información de una unidad documental a conservar en
 * una eliminacion de serie
 */
public class EliminacionUDocConservadaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idEliminacion = null;
	private String idUdoc = null;
	private String signaturaUdoc = null;
	private String tituloUdoc = null;
	private Date fechaIniUdoc = null;
	private Date fechaFinUdoc = null;

	public Date getFechaFinUdoc() {
		return fechaFinUdoc;
	}

	public void setFechaFinUdoc(Date fechaFinUdoc) {
		this.fechaFinUdoc = fechaFinUdoc;
	}

	public Date getFechaIniUdoc() {
		return fechaIniUdoc;
	}

	public void setFechaIniUdoc(Date fechaIniUdoc) {
		this.fechaIniUdoc = fechaIniUdoc;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	public String getIdUdoc() {
		return idUdoc;
	}

	public void setIdUdoc(String idUdoc) {
		this.idUdoc = idUdoc;
	}

	public String getSignaturaUdoc() {
		return signaturaUdoc;
	}

	public void setSignaturaUdoc(String signaturaUdoc) {
		this.signaturaUdoc = signaturaUdoc;
	}

	public String getTituloUdoc() {
		return tituloUdoc;
	}

	public void setTituloUdoc(String tituloUdoc) {
		this.tituloUdoc = tituloUdoc;
	}
}
