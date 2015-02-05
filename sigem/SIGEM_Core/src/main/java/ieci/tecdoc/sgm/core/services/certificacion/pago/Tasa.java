package ieci.tecdoc.sgm.core.services.certificacion.pago;

public class Tasa {

	public static final String TIPO_AL1 = "AL1";
	public static final String TIPO_AL2 = "AL2";
	public static final String TIPO_AL3 = "AL3";
	
	private String codigo;
	private String idEntidadEmisora;
	private String nombre;
	private String tipo;
	private String modelo;
	private String datosEspecificos;
	/**
	 * Identificador de los datos a capturar en una autoliquidación.
	 * Valores posibles:
	 * 0 - No se capturan datos opcionales.
	 * 1 - Se captura el expediente
	 * 2 - Fecha de devengo
	 * 3 - Expediente y fecha de devengo.
	 * 4 - Dato específico.
	 * 5 - Expediente y dato específico
	 * 6 - Fecha de devengo y dato específico
	 * 7 - Fecha de devengo, dato específico y expediente
	 */
	private String captura;
	
	public String getCaptura() {
		return captura;
	}
	public void setCaptura(String captura) {
		this.captura = captura;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDatosEspecificos() {
		return datosEspecificos;
	}
	public void setDatosEspecificos(String datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
	public String getIdEntidadEmisora() {
		return idEntidadEmisora;
	}
	public void setIdEntidadEmisora(String idEntidadEmisora) {
		this.idEntidadEmisora = idEntidadEmisora;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
