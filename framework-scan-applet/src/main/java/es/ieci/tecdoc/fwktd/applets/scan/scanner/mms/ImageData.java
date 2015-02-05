package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms;

import java.io.Serializable;

/**
 * Clase para almacenar la informacion necesaria de una imagen
 */
public class ImageData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int width;
	private int height;
	private byte [] data;
	int bpr;

	/**
	 * Constructor de la informacion de la imagen
	 * @param width ancho de la imagen
	 * @param height alto de la imagen
	 * @param bpr bits por fila de la imagen
	 * @param data buffer con la informacion de la imagen
	 */
	public ImageData(int width, int height, int bpr, byte[] data) {
		super();
		this.width = width;
		this.height = height;
		this.data = data;
		this.bpr = bpr;
	}

	/**
	 * Devuelve el ancho de la imagen
	 * @return ancho de la imagen
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Establece el ancho de la imagen
	 * @param width ancho de la imagen
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * alto de la imagen
	 * @return alto de la imagen
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Establece el alto de la imagen
	 * @param height alto de la imagen
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Devuelve el buffer con los datos de la imagen
	 * @return datos de la imagen
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Establece el buffer con los datos de la imagen
	 * @param data buffer con los datos de la imagen
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Devuelve los bits por fila de la imagen
	 * @return bits por fila de la imagen
	 */
	public int getBpr() {
		return bpr;
	}

	/**
	 * Establece los bits por fila de la imagen
	 * @param bpr bits por fila de la imagen
	 */
	public void setBpr(int bpr) {
		this.bpr = bpr;
	}

}
