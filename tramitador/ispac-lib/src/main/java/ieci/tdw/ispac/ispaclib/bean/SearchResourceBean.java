package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.Serializable;

public class SearchResourceBean implements Serializable {
		
	private String locale;
	
	private String key;

	private String value;
	
	private String isdefault;
    
	public SearchResourceBean() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public boolean isDefault() {
		return StringUtils.equalsIgnoreCase(isdefault, "true");
	}

}