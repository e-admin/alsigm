package common.db;

public class PropertyNoAccesible extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PropertyNoAccesible(String cause) {
		super("Esta propiedad no es accesible." + cause);
	}
}
