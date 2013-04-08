package es.ieci.tecdoc.fwktd.applets.scan.vo;

import java.util.ArrayList;

import es.ieci.tecdoc.fwktd.applets.scan.actions.ActionScan;

public class PerfilVO {
	private String name;
	private String device;
	private String resolution;
	private String size;
	private String color;
	private String format;
	private float contrast;
	private float bright;
	private boolean enableUI;
	private boolean duplex;
	private boolean adf;
	private boolean compress;
	private int quality;

	public PerfilVO(){
		enableUI = true;
		duplex = false;
	}
		
	public boolean isDuplex() {
		return duplex;
	}

	public void setDuplex(boolean duplex) {
		this.duplex = duplex;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isEnableUI() {
		return enableUI;
	}

	public void setEnableUI(boolean enableUI) {
		this.enableUI = enableUI;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public String getDevice() {
		//TODO Recogemos posibles fuentes
		ArrayList<String> sources = ActionScan.getSources();
		for (String source : sources) {
			if(source.equals(device)){
				return device;
			}
			
			String[] sourcePart = source.split(":");
			String[] devicePart = device.split(":");
			String backEnd = devicePart[0];
			
			for (String sourceBackEnd : sourcePart) {
				if(sourceBackEnd.contains(backEnd)){
					return source;
				}
			}
		}
		return ActionScan.getSourceDefautl();
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getContrast() {
		return contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	public float getBright() {
		return bright;
	}

	public void setBright(float bright) {
		this.bright = bright;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public void setAdf(boolean adf) {
		this.adf = adf;
	}

	public boolean isAdf() {
		return adf;
	}	
}
