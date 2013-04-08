package ieci.tdw.ispac.ispaccatalog.breadcrumbs;

public class BreadCrumb {

	private String sURL;
	private String sName;
	private String sTitle;
	
	public BreadCrumb(String name) {
		this.sName = name;
		sURL="#";
	}
	
	public BreadCrumb(String name, String url){
		this.sURL = url;
		this.sName = name;
	}
	
	public BreadCrumb(String name, String url, String prefix){
		this.sName = name;
		this.sTitle = prefix;
		this.sURL = url;
	}

	/**
	 * @return Returns the sName.
	 */
	public String getName() {
		return sName;
	}

	/**
	 * @param name The sName to set.
	 */
	public void setName(String name) {
		sName = name;
	}

	/**
	 * @return Returns the sURL.
	 */
	public String getURL() {
		return sURL;
	}

	/**
	 * @param surl The sURL to set.
	 */
	public void setURL(String surl) {
		sURL = surl;
	}

	/**
	 * @return Returns the sTitle.
	 */
	public String getTitle() {
		return sTitle;
	}

	/**
	 * @param title The sTitle to set.
	 */
	public void setTitle(String title) {
		sTitle = title;
	}

	public boolean equals(BreadCrumb bc){
		return 	(this.sName.equals(bc.getName()))
			    && (this.sTitle.equals(bc.getTitle()))
			    && (this.sURL.equals(bc.getURL()));
	}
}
