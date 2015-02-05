package ieci.tdw.ispac.ispaclib.templates;


public class TemplateGraphicInfo {

	private String url;
	private boolean asLink;
	
	public TemplateGraphicInfo(String url, boolean asLink) {
		setUrl(url);
		setAsLink(asLink);
	}
	public TemplateGraphicInfo() {
		url = null;
		asLink = false;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isAsLink() {
		return asLink;
	}
	public void setAsLink(boolean asLink) {
		this.asLink = asLink;
	}
}
