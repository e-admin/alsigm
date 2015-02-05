package deposito.forms;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en la gestión de ubicaciones del
 * depósito físico
 */
public class UbicacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idUbicacion = null;
	String nombre = null;
	String ubicacion = null;
	String idArchivo = null;
	String nombreArchivo = null;
	String nombreUbicacion = null;
	String signatura_like = null;
	String signatura = null;
	String fondo = null;
	String formato = null;
	private String[] codOrdenAmbito = new String[0];
	private String[] idAmbito = new String[0];
	private String[] nombreAmbito = new String[0];

	/* Fecha */
	private String fechaComp = null;
	private String fechaFormato = null;
	private String fechaA = null;
	private String fechaM = null;
	private String fechaD = null;
	private String fechaS = null;
	private String fechaIniFormato = null;
	private String fechaIniA = null;
	private String fechaIniM = null;
	private String fechaIniD = null;
	private String fechaIniS = null;
	private String fechaFinFormato = null;
	private String fechaFinA = null;
	private String fechaFinM = null;
	private String fechaFinD = null;
	private String fechaFinS = null;

	private String calificador = null;
	private String calificadorInicial = null;
	private String calificadorFinal = null;

	public void clear() {
		idUbicacion = null;
		nombre = null;
		ubicacion = null;
		idArchivo = null;
		signatura_like = null;
		signatura = null;
		fondo = null;
		formato = null;
		idAmbito = new String[0];
		nombreAmbito = new String[0];
		codOrdenAmbito = new String[0];
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public void setSignatura_like(String signatura_like) {
		this.signatura_like = signatura_like;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getNombreUbicacion() {
		return nombreUbicacion;
	}

	public void setNombreUbicacion(String nombreUbicacion) {
		this.nombreUbicacion = nombreUbicacion;
	}

	public String[] getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(String[] idAmbito) {
		this.idAmbito = idAmbito;
	}

	public String[] getNombreAmbito() {
		return nombreAmbito;
	}

	public void setNombreAmbito(String[] nombreAmbito) {
		this.nombreAmbito = nombreAmbito;
	}

	public String[] getCodOrdenAmbito() {
		return codOrdenAmbito;
	}

	public void setCodOrdenAmbito(String[] codOrdenAmbito) {
		this.codOrdenAmbito = codOrdenAmbito;
	}

	public String getFechaComp() {
		return fechaComp;
	}

	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	public String getFechaFormato() {
		return fechaFormato;
	}

	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	public String getFechaA() {
		return fechaA;
	}

	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	public String getFechaM() {
		return fechaM;
	}

	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	public String getFechaD() {
		return fechaD;
	}

	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	public String getFechaS() {
		return fechaS;
	}

	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	public String getFechaIniA() {
		return fechaIniA;
	}

	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	public String getFechaIniM() {
		return fechaIniM;
	}

	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	public String getFechaIniD() {
		return fechaIniD;
	}

	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	public String getFechaIniS() {
		return fechaIniS;
	}

	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	public String getFechaFinA() {
		return fechaFinA;
	}

	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	public String getFechaFinM() {
		return fechaFinM;
	}

	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	public String getFechaFinD() {
		return fechaFinD;
	}

	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	public String getCalificador() {
		return calificador;
	}

	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	public String getCalificadorInicial() {
		return calificadorInicial;
	}

	public void setCalificadorInicial(String calificadorInicial) {
		this.calificadorInicial = calificadorInicial;
	}

	public String getCalificadorFinal() {
		return calificadorFinal;
	}

	public void setCalificadorFinal(String calificadorFinal) {
		this.calificadorFinal = calificadorFinal;
	}
}