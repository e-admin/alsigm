package salas.vos;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaUsuarioSalaConsultaVO extends UsuarioSalasConsultaVO {

	private static final long serialVersionUID = 1L;

	private String[] idsArchivo;
	private Date fechaIniAlta = null;
	private Date fechaFinAlta = null;
	private Date fechaIniExp = null;
	private Date fechaFinExp = null;
	private Integer numVeces = null;

	public void setIdsArchivo(String[] idsArchivo) {
		this.idsArchivo = idsArchivo;
	}

	public String[] getIdsArchivo() {
		return idsArchivo;
	}

	public Date getFechaIniAlta() {
		return fechaIniAlta;
	}

	public void setFechaIniAlta(Date fechaIniAlta) {
		this.fechaIniAlta = fechaIniAlta;
	}

	public Date getFechaFinAlta() {
		return fechaFinAlta;
	}

	public void setFechaFinAlta(Date fechaFinAlta) {
		this.fechaFinAlta = fechaFinAlta;
	}

	public Date getFechaIniExp() {
		return fechaIniExp;
	}

	public void setFechaIniExp(Date fechaIniExp) {
		this.fechaIniExp = fechaIniExp;
	}

	public Date getFechaFinExp() {
		return fechaFinExp;
	}

	public void setFechaFinExp(Date fechaFinExp) {
		this.fechaFinExp = fechaFinExp;
	}

	public Integer getNumVeces() {
		return numVeces;
	}

	public void setNumVeces(Integer numVeces) {
		this.numVeces = numVeces;
	}
}
