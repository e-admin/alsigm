package es.ieci.tecdoc.fwktd.applets.scan.vo;

public class OptionsUIVO {
	private boolean archivo;
	private boolean open;
	private boolean save;
	private boolean capture;
	private boolean scan;
	private boolean scanFinish;
	private boolean scanAdd;
	private boolean scanReplace;
	private boolean scanDelete;
	private String pathTemp;
	private String pathConvert;
	private boolean isServlet;
	private String urlServlet;
	
	public OptionsUIVO(){
		
		urlServlet = null;
		archivo = true;
		save = true;
		open = true;
		capture = true;
		scan = true;
		scanAdd = true;
		scanFinish = true;
		scanReplace = true;
		scanDelete = true;
		pathTemp = System.getProperty("file.separator") + "iecisa" + System.getProperty("file.separator") + "tempo";
		pathConvert = System.getProperty("file.separator") + "iecisa" + System.getProperty("file.separator") + "final";
	}
	
	public boolean isScanAdd() {
		return scanAdd;
	}
	
	public void setScanAdd(boolean scanAdd) {
		this.scanAdd = scanAdd;
	}
	
	public boolean isArchivo() {
		return archivo;
	}
	public void setArchivo(boolean archivo) {
		this.archivo = archivo;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
	public boolean isCapture() {
		return capture;
	}
	public void setCapture(boolean capture) {
		this.capture = capture;
	}
	public boolean isScan() {
		return scan;
	}
	public void setScan(boolean scan) {
		this.scan = scan;
	}
	public boolean isScanFinish() {
		return scanFinish;
	}
	public void setScanFinish(boolean scanFinish) {
		this.scanFinish = scanFinish;
	}
	public boolean isScanReplace() {
		return scanReplace;
	}
	public void setScanReplace(boolean scanReplace) {
		this.scanReplace = scanReplace;
	}
	public boolean isScanDelete() {
		return scanDelete;
	}
	public void setScanDelete(boolean scanDelete) {
		this.scanDelete = scanDelete;
	}
	public String getPathTemp() {
		return pathTemp;
	}
	public void setPathTemp(String pathTemp) {
		this.pathTemp = pathTemp;
	}
	public boolean isServlet() {
		return isServlet;
	}
	public void setServlet(boolean isServlet) {
		this.isServlet = isServlet;
	}
	public String getUrlServlet() {
		return urlServlet;
	}
	public void setUrlServlet(String urlServlet) {
		this.urlServlet = urlServlet;
	}

	public String getPathConvert() {
		return pathConvert;
	}

	public void setPathConvert(String pathConvert) {
		this.pathConvert = pathConvert;
	}	
}
