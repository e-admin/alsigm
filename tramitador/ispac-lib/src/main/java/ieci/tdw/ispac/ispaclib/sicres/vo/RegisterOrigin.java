package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.util.Calendar;

/**
 * Información sobre la procedencia de un registro
 */
public class RegisterOrigin extends RegisterInfo {
	
	/* Fecha de alta.
	 */
	private Calendar creationDate;
	
    /* Usuario que realizó el apunte.
     */
    private String user;
    
    /* Número de expediente relacionado con el registro
     */
    private String numExpedient;
    
    public RegisterOrigin() {
    }

    public RegisterOrigin(RegisterOffice registerOffice,
           			   	  String registerNumber,
           			   	  Calendar registerDate,
           			   	  String registerType,
           			   	  Calendar creationDate,
           			   	  String user) {
    	
    	super(registerOffice, registerNumber, registerDate, registerType);

        this.creationDate = creationDate;
        this.user = user;
    }

	/**
	 * @return Returns the creationDate.
	 */
	public Calendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public String getNumExpedient() {
		return numExpedient;
	}

	public void setNumExpedient(String numExpedient) {
		this.numExpedient = numExpedient;
	}
    
}