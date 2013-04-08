package ieci.tdw.ispac.ispaclib.sicres.vo;

/**
 * Información sobre la persona física o jurídica interesada o destinatario.
 */
public class ThirdPerson {

    /* Identificador del tercero.
     */
	private String id;

    /* Nombre o razón social del tercero.
     */
    private String name;

    /**
     * Identificador de la dirección 
     */
    private String addressId;
    
    /**
     * Dirección completa 
     */
    private String address;
    
    /**
     * Identificador fiscal 
     */
    private String fiscalId;
    
    
    public ThirdPerson() {
    }

    public ThirdPerson(String id,
    				   String name) {
    	
        this.id = id;
        this.name = name;
    }
    public ThirdPerson(String id,
    				   String name,
    				   String addressId) {
    	
        this(id, name);
        this.addressId = addressId;
    }

    
    public ThirdPerson(String id,
			   String name,
			   String addressId,
			   String address) {

		this(id, name, addressId);
		this.address = address;
	}
    
    public ThirdPerson(String id,
			   String name,
			   String addressId,
			   String address,
			   String fiscalId) {

		this(id, name, addressId, address);
		this.fiscalId = fiscalId;
	}
    
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFiscalId() {
		return fiscalId;
	}

	public void setFiscalId(String fiscalId) {
		this.fiscalId = fiscalId;
	}

}