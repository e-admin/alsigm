package es.ieci.tecdoc.fwktd.applets.scan.vo;

import javax.swing.ImageIcon;

public class ImageVO {
	private ImageIcon icon;
	private int width;
	private int height;
	private String name;
	private String path;
	private String extension;
	private String color;

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	
	
	public String getImage(){
		return path + System.getProperty("file.separator") + name + "." + extension ;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}
}
