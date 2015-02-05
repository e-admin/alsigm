package configuracion.vos;

import java.util.Date;

import common.vos.BaseVO;

public class InfoSistemaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String valor;
	private Date fechaactualizacion;

	public Date getFechaactualizacion() {
		return fechaactualizacion;
	}

	public void setFechaactualizacion(Date fechaactualizacion) {
		this.fechaactualizacion = fechaactualizacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String id) {
		this.nombre = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
