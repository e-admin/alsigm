/**
 * 
 */
package es.ieci.tecdoc.fwktd.core.model;

/**
 * 
 * @author IECISA
 *
 */
public class NamedEntity extends Entity {

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}
	
	// Members
	private static final long serialVersionUID = 6991948486816617413L;

	private String name;
	
}
