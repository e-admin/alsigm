package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms;

public class CapabilitiesValues {

    private int [] supportedSizes = null;
    private int[] pixelTypeList;
    private float[] xResolutionList;
    private boolean duplexSupport;
    private boolean adfSupport;

	public int[] getSupportedSizes() {
		return supportedSizes;
	}
	public void setSupportedSizes(int[] supportedSizes) {
		this.supportedSizes = supportedSizes;
	}

	public int[] getPixelTypeList() {
		return pixelTypeList;
	}
	public void setPixelTypeList(int[] pixelTypeList) {
		this.pixelTypeList = pixelTypeList;
	}

	public float[] getxResolutionList() {
		return xResolutionList;
	}
	public void setxResolutionList(float[] xResolutionList) {
		this.xResolutionList = xResolutionList;
	}

	public boolean isDuplexSupport() {
		return duplexSupport;
	}
	public void setDuplexSupport(boolean duplexSupport) {
		this.duplexSupport = duplexSupport;
	}

	public boolean isAdfSupport() {
		return adfSupport;
	}
	public void setAdfSupport(boolean adfSupport) {
		this.adfSupport = adfSupport;
	}

}
