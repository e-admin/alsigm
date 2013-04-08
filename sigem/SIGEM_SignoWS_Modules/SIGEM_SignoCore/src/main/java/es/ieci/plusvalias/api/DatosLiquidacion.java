package es.ieci.plusvalias.api;

import java.io.Serializable;
import java.util.Date;

public class DatosLiquidacion implements Serializable{
	
	private static final long serialVersionUID = -4653500632481735875L;
	
	/**
	 * 'Fecha de transmisión actual
	 */
	private Date fechaTransActual;

	public DatosLiquidacion() {
		super();
	}

	public DatosLiquidacion(Date fechaTransActual) {
		super();
		this.fechaTransActual = fechaTransActual;
	}

	public Date getFechaTransActual() {
		return fechaTransActual;
	}

	public void setFechaTransActual(Date fechaTransActual) {
		this.fechaTransActual = fechaTransActual;
	}
}
