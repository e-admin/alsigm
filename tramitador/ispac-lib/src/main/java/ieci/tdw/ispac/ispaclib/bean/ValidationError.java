package ieci.tdw.ispac.ispaclib.bean;

import java.io.Serializable;

/**
 * @author marisa
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ValidationError implements Serializable {

	private static final long serialVersionUID = 54216924084063655L;
	
	private String property;
	private String errorKey;
	private String[] args;

	public ValidationError() {

	}

	public ValidationError(String property, String errorKey)
	{	this(property, errorKey, null);	}

	public ValidationError(String property,
			String errorKey, String[] args) {
		this.property = property;
		this.errorKey = errorKey;
		this.args = args;
	}


	/**
	 * @return Returns the args.
	 */
	public String[] getArgs() {
		return args;
	}
	/**
	 * @param args The args to set.
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}
	/**
	 * @return Returns the errorKey.
	 */
	public String getErrorKey() {
		return errorKey;
	}
	/**
	 * @param errorKey The errorKey to set.
	 */
	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}
	/**
	 * @return Returns the property.
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property The property to set.
	 */
	public void setProperty(String property) {
		this.property = property;
	}
}
