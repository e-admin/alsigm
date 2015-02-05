package ieci.tdw.ispac.ispaclib.sicres.vo;

/**
 * Información sobre un transporte de los documentos del registro.
 */
public class Transport {

	/* Tipo de transporte.
     */
    private String type;

    /* Identifica, para un determinado tipo de transporte, una entrega de documentos.
     */
    private String number;

    public Transport() {
    }

    public Transport(String type,
           			 String number) {
    	
        this.type = type;
        this.number = number;
    }

	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

}