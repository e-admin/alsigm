package es.ieci.tecdoc.fwktd.applets.scan.vo;

public class PropertiesVO {
	private int[] pixTypes;
	private int[] supportSizes;
	private float[] resolutionTypes;
	private boolean isSupportDuplex;
	private boolean isSupportADF;

	public int[] getPixTypes() {
		return pixTypes;
	}

	public void setPixTypes(int[] pixTypes) {
		this.pixTypes = pixTypes;
	}

	public int[] getSupportSizes() {
		return supportSizes;
	}

	public void setSupportSizes(int[] supportSizes) {
		this.supportSizes = supportSizes;
	}

	public float[] getResolutionTypes() {
		return resolutionTypes;
	}

	public void setResolutionTypes(float[] resolutionTypes) {
		this.resolutionTypes = resolutionTypes;
	}

	public boolean isSupportDuplex() {
		return isSupportDuplex;
	}

	public void setSupportDuplex(boolean isSupportDuplex) {
		this.isSupportDuplex = isSupportDuplex;
	}

	public void setSupportADF(boolean isSupportADF) {
		this.isSupportADF = isSupportADF;
	}

	public boolean isSupportADF() {
		return isSupportADF;
	}	
}
