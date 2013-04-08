package ieci.tdw.ispac.ispaclib.thirdparty;

/**
 * Adaptador para contener la información de la dirección electrónica
 * de un tercero.
 *
 */
public class ElectronicAddressAdapter implements IElectronicAddressAdapter {

	/**
	 * Identificador de la dirección electrónica.
	 */
	private String id;
	
	/**
	 * Tipo de dirección electrónica:
	 * @see IElectronicAddressAdapter
	 */
	private int tipo;
	
	/**
	 * Dirección electrónica.
	 */
	private String direccion;

	
	/**
	 * Constructor.
	 */
	public ElectronicAddressAdapter() {
		super();
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
