package ieci.tdw.ispac.ispaclib.templates;

public class TemplateDocumentInfo {
	private String url;
	private boolean asText;
	private String bookmark;
	public TemplateDocumentInfo(String url) {
		setUrl(url);
		asText = false;
		bookmark = null;
		
	}
	public TemplateDocumentInfo(String url, boolean asText) {
		setUrl(url);
		setAsText(asText);
		bookmark = null;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isAsText() {
		return asText;
	}
	public void setAsText(boolean asText) {
		this.asText = asText;
	}
	public String getBokkmark() {
		return bookmark;
	}
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
}
