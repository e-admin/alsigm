package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Información sobre una anotación en el registo.
 */
public class Register {
	
	/* Procedencia del registro.
	 */
	private RegisterOrigin registerOrigin;
	
	/* Contenido del registro.
	 */
	private RegisterData registerData;

	/* Organismos de origen y destino.
     */
    private Organization originOrganization;
    private Organization destinationOrganization;

	/* Información de registro donde originariamente se presentó la documentación.
     */
    private RegisterInfo originalRegister;

	/* Transporte de la documentación.
     */
    private Transport transport;

    /* Datos extra
     */
    private Map extraDatas;
    
    public Register() {
    	extraDatas = new HashMap();
    }

    public Register(RegisterOrigin registerOrigin,
    				RegisterData registerData,
    				Organization originOrganization,
    				Organization destinationOrganization,
    				RegisterInfo originalRegister,
    				Transport transport) {
    	
    	this.registerOrigin = registerOrigin;
    	this.registerData = registerData;
    	this.originOrganization = originOrganization;
    	this.destinationOrganization = destinationOrganization;
    	this.originalRegister = originalRegister;
    	this.transport = transport;
    	extraDatas = new HashMap();
    }

	/**
	 * @return Returns the registerOrigin.
	 */
	public RegisterOrigin getRegisterOrigin() {
		return registerOrigin;
	}
	/**
	 * @param registerOrigin The registerOrigin to set.
	 */
	public void setRegisterOrigin(RegisterOrigin registerOrigin) {
		this.registerOrigin = registerOrigin;
	}

	/**
	 * @return Returns the registerData.
	 */
	public RegisterData getRegisterData() {
		return registerData;
	}
	/**
	 * @param registerData The registerData to set.
	 */
	public void setRegisterData(RegisterData registerData) {
		this.registerData = registerData;
	}

	/**
	 * @return Returns the destinationOrganization.
	 */
	public Organization getDestinationOrganization() {
		return destinationOrganization;
	}
	/**
	 * @param destinationOrganization The destinationOrganization to set.
	 */
	public void setDestinationOrganization(Organization destinationOrganization) {
		this.destinationOrganization = destinationOrganization;
	}

	/**
	 * @return Returns the originalRegister.
	 */
	public RegisterInfo getOriginalRegister() {
		return originalRegister;
	}
	/**
	 * @param originalRegister The originalRegister to set.
	 */
	public void setOriginalRegister(RegisterInfo originalRegister) {
		this.originalRegister = originalRegister;
	}

	/**
	 * @return Returns the originOrganization.
	 */
	public Organization getOriginOrganization() {
		return originOrganization;
	}
	/**
	 * @param originOrganization The originOrganization to set.
	 */
	public void setOriginOrganization(Organization originOrganization) {
		this.originOrganization = originOrganization;
	}

	/**
	 * @return Returns the transport.
	 */
	public Transport getTransport() {
		return transport;
	}
	/**
	 * @param transport The transport to set.
	 */
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public void addExtraData(Object key, Object value){
		extraDatas.put(key, value);
	}
	
	public Object getExtraData(Object key){
		return extraDatas.get(key);
	}

	public Map getExtraDatas() {
		return extraDatas;
	}

	public void setExtraDatas(Map extraDatas) {
		this.extraDatas = extraDatas;
	}
	
}