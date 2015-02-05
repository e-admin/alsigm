package es.ieci.tecdoc.fwktd.dm.business.config;

public class Source extends MappingElement {

	private String token = null;

	public Source() {
		super();
	}

	public Source(String type, String value, String format, String token) {
		super(type, value, format);
		setToken(token);
	}

	public Source(String type, String value) {
		super(type, value);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
