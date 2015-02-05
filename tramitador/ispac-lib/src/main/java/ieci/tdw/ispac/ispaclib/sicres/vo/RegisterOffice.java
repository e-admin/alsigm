package ieci.tdw.ispac.ispaclib.sicres.vo;

/**
 * Información sobre una oficina de registro.
 */
public class RegisterOffice {
	
	/* Código de la oficina.
     */
	private String code;
	
	/* Nombre de la oficina.
     */
    private String name;

    public RegisterOffice() {
    }

    public RegisterOffice(String code,
    					  String name) {
    	
        this.code = code;
        this.name = name;
    }

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
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
    
}