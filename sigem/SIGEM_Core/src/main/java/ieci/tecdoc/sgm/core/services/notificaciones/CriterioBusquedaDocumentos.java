package ieci.tecdoc.sgm.core.services.notificaciones;

public class CriterioBusquedaDocumentos {

    private String expediente;
    
    private String nifDestinatario;
    
    private String codigoNoti;
    
    private String codigDoc;

	public String getCodigDoc() {
		return codigDoc;
	}

	public void setCodigDoc(String codigDoc) {
		this.codigDoc = codigDoc;
	}

	public String getCodigoNoti() {
		return codigoNoti;
	}

	public void setCodigoNoti(String codigoNoti) {
		this.codigoNoti = codigoNoti;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getNifDestinatario() {
		return nifDestinatario;
	}

	public void setNifDestinatario(String nifDestinatario) {
		this.nifDestinatario = nifDestinatario;
	}

    
}
