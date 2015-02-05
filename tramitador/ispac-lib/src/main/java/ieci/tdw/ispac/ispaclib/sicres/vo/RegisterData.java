package ieci.tdw.ispac.ispaclib.sicres.vo;

/**
 * Información sobre el contenido de un registro.
 */
public class RegisterData {

	/* Participantes: interesados para los registros de entrada y destinatarios para los de salida.
	 */
	private ThirdPerson[] participants;

    /* Asunto del apunte registral.
     */
    private Subject subject;

    /* Resumen del contenido de la anotación.
     */
    private String summary;
    
    /* Documentos registrados.
     */
	private DocumentInfo[] infoDocuments;

    public RegisterData() {
    }

    public RegisterData(ThirdPerson[] participants,
    					Subject subject,
    				    String summary,
    				    DocumentInfo[] infoDocuments) {
    	
        this.participants = participants;
        this.subject = subject;
        this.summary = summary;
        this.infoDocuments = infoDocuments;
    }

	/**
	 * @return Returns the participants.
	 */
	public ThirdPerson[] getParticipants() {
		return participants;
	}
	/**
	 * @param participants The participants to set.
	 */
	public void setParticipants(ThirdPerson[] participants) {
		this.participants = participants;
	}

	/**
	 * @return Returns the subject.
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the summary.
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary The summary to set.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * @return Returns the infoDocuments.
	 */
	public DocumentInfo[] getInfoDocuments() {
		return infoDocuments;
	}
	/**
	 * @param infoDocuments The infoDocuments to set.
	 */
	public void setInfoDocuments(DocumentInfo[] infoDocuments) {
		this.infoDocuments = infoDocuments;
	}
    
}