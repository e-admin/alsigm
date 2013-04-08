package valoracion.vos;

import common.vos.BaseVO;

/**
 * Objeto con la informacion de las fechas para los criterios de eliminacion
 */
public class FechaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String dia = null;
	private String mes = null;
	private String operador = null;

	public String getDia() {
		return dia;
	}

	public void setDia(String fecha) {
		this.dia = fecha;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
}
