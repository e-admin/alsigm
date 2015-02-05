package ieci.tdw.ispac.ispaclib.sign;

public class ResultadoValidacionCertificado {

    public static final String VALIDACION_OK = "0";
    public static final String VALIDACION_ERROR = "-1";	
	
    private String resultadoValidacion;
    public String getResultadoValidacion() {
		return resultadoValidacion;
	}
	public void setResultadoValidacion(String resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}
	public String getMensajeValidacion() {
		return mensajeValidacion;
	}
	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}
	private String mensajeValidacion;
}
