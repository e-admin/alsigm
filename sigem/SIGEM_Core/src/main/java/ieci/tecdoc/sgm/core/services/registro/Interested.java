package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de Interesado
 *
 */
public class Interested {
	
    /** Identificador del tercero.
     */
	protected String id;

    /** Nombre o razón social del tercero.
     */
    protected String name;

    /**
     * Identificador de la dirección asociada al tercero 
     */
    protected String addressId;
    
    /**
     * Dirección
     */
    protected String address;
    
    
    public Interested() {
    }

    /**
     * @param id
     * @param name
     */
    public Interested(String id,
    				   String name) {
    	
        this.id = id;
        this.name = name;
    }
    /**
     * @param id
     * @param name
     * @param addressId
     */
    public Interested(String id,
    				   String name,
    				   String addressId) {
    	
        this.id = id;
        this.name = name;
        this.addressId = addressId;
    }

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
