package ieci.tecdoc.sgm.certificacion.pdf.bean;

/**
 * Clase qie almacena todos los datos relacionados con la configuración de datos del PDF
 * @author José Antonio Nogales
 */
public class DatosPropiedades {
	String titulo;
	DatosCabecera cabecera;
	DatosCertificacion certificacion;
	DatosCentrales centro;
	Imagenes imagenes;
	
	/**
	 * Constructor de la clase
	 * @param titulo Título del bloque de datos
	 * @param cabecera Datos pertenecientes a la cabecera
	 * @param certificacion Datos pertenecientes a la certificación
	 * @param centro Datos pertenecientes a la parte central
	 * @param imagenes Datos pertenecientes a las imágenes
	 */
	public DatosPropiedades(String titulo, DatosCabecera cabecera, DatosCertificacion certificacion, DatosCentrales centro, Imagenes imagenes) {
		this.titulo = titulo;
		this.cabecera = cabecera;
		this.certificacion = certificacion;
		this.centro = centro;
		this.imagenes = imagenes;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public DatosPropiedades() {
		this.titulo = null;
		this.cabecera = null;
		this.certificacion = null;
		this.centro = null;
		this.imagenes = null;
	}

	/**
	 * Métodos get y set de la clase
	 */
	public DatosCabecera getCabecera() {
		return cabecera;
	}

	public void setCabecera(DatosCabecera cabecera) {
		this.cabecera = cabecera;
	}

	public DatosCentrales getCentro() {
		return centro;
	}

	public void setCentro(DatosCentrales centro) {
		this.centro = centro;
	}

	public Imagenes getImagenes() {
		return imagenes;
	}

	public void setImagenes(Imagenes imagenes) {
		this.imagenes = imagenes;
	}

	public DatosCertificacion getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(DatosCertificacion certificacion) {
		this.certificacion = certificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
