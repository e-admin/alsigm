package xml.config;

import common.vos.BaseVO;

public class ConfigKeyValue extends BaseVO{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private Object value;
	private boolean singleton;
	private String path;
	private String pathRules;


	public ConfigKeyValue(String key, String path, String pathRules, boolean singleton) {
		this.key = key;
		this.path = path;
		this.singleton = singleton;
		this.pathRules  = pathRules;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}


	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the singleton
	 */
	public boolean isSingleton() {
		return singleton;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the pathRules
	 */
	public String getPathRules() {
		return pathRules;
	}
}
