package fondos.vos;

public class NivelFichaUDocRepEcmVO {

	String idNivel;
	String idFichaDescrPrefUdoc;
	String idFichaClfDocPrefUdoc;
	String idRepEcmPrefUdoc;
	String updateUDocs;

	public NivelFichaUDocRepEcmVO() {

	}

	public String getIdFichaClfDocPrefUdoc() {
		return idFichaClfDocPrefUdoc;
	}

	public void setIdFichaClfDocPrefUdoc(String idFichaClfDocPrefUdoc) {
		this.idFichaClfDocPrefUdoc = idFichaClfDocPrefUdoc;
	}

	public String getIdFichaDescrPrefUdoc() {
		return idFichaDescrPrefUdoc;
	}

	public void setIdFichaDescrPrefUdoc(String idFichaDescrPrefUdoc) {
		this.idFichaDescrPrefUdoc = idFichaDescrPrefUdoc;
	}

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public String getIdRepEcmPrefUdoc() {
		return idRepEcmPrefUdoc;
	}

	public void setIdRepEcmPrefUdoc(String idRepEcmPrefUdoc) {
		this.idRepEcmPrefUdoc = idRepEcmPrefUdoc;
	}

	public boolean isUpdateUDocs() {
		if (updateUDocs != null && updateUDocs.equalsIgnoreCase("true"))
			return true;
		else
			return false;
	}

	public void setUpdateUDocs(String updateUDocs) {
		this.updateUDocs = updateUDocs;
	}

}
