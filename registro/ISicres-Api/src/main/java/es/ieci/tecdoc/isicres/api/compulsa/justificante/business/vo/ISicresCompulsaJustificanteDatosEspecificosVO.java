package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo;

/**
 * Clase de propiedades con los datos especificos para dibujar la imagen de fondo y los datos del localizador.
 * @author IECISA
 *
 */
public class ISicresCompulsaJustificanteDatosEspecificosVO {

	//Margen
	private float margen;
	//Fuente
	private String font;
	//Codificación de la fuente
	private String encoding;
	//Tamaño de la fuente
	private float fontSize;
	//Tipo de banda
	private int band;
	//Tamaño de la banda
	private float bandSize;
	/**
	 * @param margen the margen to set
	 */
	public void setMargen(float margen) {
		this.margen = margen;
	}
	/**
	 * @return the margen
	 */
	public float getMargen() {
		return margen;
	}
	/**
	 * @param font the font to set
	 */
	public void setFont(String font) {
		this.font = font;
	}
	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	/**
	 * @return the fontSize
	 */
	public float getFontSize() {
		return fontSize;
	}
	/**
	 * @param band the band to set
	 */
	public void setBand(int band) {
		this.band = band;
	}
	/**
	 * @return the band
	 */
	public int getBand() {
		return band;
	}
	/**
	 * @param bandSize the bandSize to set
	 */
	public void setBandSize(float bandSize) {
		this.bandSize = bandSize;
	}
	/**
	 * @return the bandSize
	 */
	public float getBandSize() {
		return bandSize;
	}	
}
