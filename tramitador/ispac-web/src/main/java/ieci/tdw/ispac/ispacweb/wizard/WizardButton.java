package ieci.tdw.ispac.ispacweb.wizard;

import java.io.Serializable;

public class WizardButton implements Serializable {

	private String titleKey = null;
	private String icon = null;
	private String url = null;
	private boolean active = true;
	
	
	/**
	 * Constructor.
	 *
	 */
	public WizardButton() {
		this(null, null, null, false);
	}

	public WizardButton(String titleKey, String url) {
		this(titleKey, null, url, true);
	}

	public WizardButton(String titleKey, boolean active) {
		this(titleKey, null, null, active);
	}

	public WizardButton(String titleKey, String icon, String url) {
		this(titleKey, icon, url, true);
	}

    public WizardButton(String titleKey, String url, boolean active) {
        this(titleKey, null, url, active);
    }

    public WizardButton(String titleKey, String icon, String url, boolean active) {
        setTitleKey(titleKey);
        setIcon(icon);
        setUrl(url);
        setActive(active);
    }


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getTitleKey() {
		return titleKey;
	}


	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
