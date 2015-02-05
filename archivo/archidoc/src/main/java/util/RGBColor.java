package util;

public class RGBColor {
	private byte red = (byte) 255;
	private byte green = (byte) 255;
	private byte blue = (byte) 0;
	private String hexColor = null;

	public RGBColor(byte _red, byte _green, byte _blue) {

		/*
		 * this.red = red; this.blue = blue; this.green = green;
		 * 
		 * this.red = (byte)5; this.blue =(byte)7; this.green = (byte)255;
		 */
	}

	/*
	 * public RGBColor(String color) { if(color != null && color.length()== 6) {
	 * String redColor = color.substring(0,1); String greenColor =
	 * color.substring(2,3); String blueColor; = color.substring(3,4);
	 * 
	 * } }
	 */

	/**
	 * @return el blue
	 */
	public byte getBlue() {
		return blue;
	}

	/**
	 * @param blue
	 *            el blue a establecer
	 */
	public void setBlue(byte blue) {
		this.blue = blue;
	}

	/**
	 * @return el green
	 */
	public byte getGreen() {
		return green;
	}

	/**
	 * @param green
	 *            el green a establecer
	 */
	public void setGreen(byte green) {
		this.green = green;
	}

	/**
	 * @return el hexColor
	 */
	public String getHexColor() {
		return hexColor;
	}

	/**
	 * @param hexColor
	 *            el hexColor a establecer
	 */
	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	/**
	 * @return el red
	 */
	public byte getRed() {
		return red;
	}

	/**
	 * @param red
	 *            el red a establecer
	 */
	public void setRed(byte red) {
		this.red = red;
	}

	public String toString() {
		return "R:" + red + " G:" + green + " B:" + blue;
	}

}
