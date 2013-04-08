package ieci.tecdoc.sgm.certificacion.pdf.bean;

/**
 * Clase que almacena los datos pertenecientes a las imágenes (logos, marca de agua)
 * @author José Antonio Nogales
 */
public class Imagenes {
	String imagenCabecera;
	String marcaAgua;
	String imagenFirma;

	/**
	 * Constructor de la clase
	 * @param imagenCabecera Imagen de cabecera del PDF (normalmente logo de la entidad)
	 * @param marcaAgua Marca de agua del PDF
	 * @param imagenFirma Imagen para fondo de la firma digital del PDF
	 */
	public Imagenes(String imagenCabecera, String marcaAgua, String imagenFirma) {
		this.imagenCabecera = imagenCabecera;
		this.marcaAgua = marcaAgua;
		this.imagenFirma = imagenFirma;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public Imagenes() {
		this.imagenCabecera = null;
		this.marcaAgua = null;
		this.imagenFirma = null;
	}

	/**
	 * Métodos get y set de la clase
	 */
	public String getImagenCabecera() {
		return imagenCabecera;
	}

	public void setImagenCabecera(String imagenCabecera) {
		this.imagenCabecera = imagenCabecera;
	}

	public String getMarcaAgua() {
		return marcaAgua;
	}

	public void setMarcaAgua(String marcaAgua) {
		this.marcaAgua = marcaAgua;
	}

	public String getImagenFirma() {
		return imagenFirma;
	}

	public void setImagenFirma(String imagenFirma) {
		this.imagenFirma = imagenFirma;
	}
}
