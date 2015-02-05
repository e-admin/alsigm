package descripcion.vos;

import java.util.Date;

import common.vos.BaseVO;

public class FechaInicialFinalElementoCF extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idelementocf = null;
	private Date fechaInicio = null;
	private Date fechaFin = null;

	public FechaInicialFinalElementoCF() {
		super();
	}

	public FechaInicialFinalElementoCF(String idelementocf, Date fechaInicio,
			Date fechaFin) {
		super();
		this.idelementocf = idelementocf;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getIdelementocf() {
		return idelementocf;
	}

	public void setIdelementoCf(String idelementocf) {
		this.idelementocf = idelementocf;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
