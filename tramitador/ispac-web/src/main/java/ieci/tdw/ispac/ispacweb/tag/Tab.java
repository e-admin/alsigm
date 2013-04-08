package ieci.tdw.ispac.ispacweb.tag;

public class Tab {
	
	protected String id;
	protected String titleKey;
	protected String title;
	protected String contenido_tab;
	
	
	public Tab() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitleKey() {
		return titleKey;
	}
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContenido_tab() {
		return contenido_tab;
	}
	public void setContenido_tab(String contenido_tab) {
		this.contenido_tab = contenido_tab;
	}
	
	

}
