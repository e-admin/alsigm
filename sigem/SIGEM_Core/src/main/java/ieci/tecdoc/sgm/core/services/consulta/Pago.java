package ieci.tecdoc.sgm.core.services.consulta;

public class Pago extends SolicitudAportacionDocumentacion{
	
	private String entidadEmisoraId;
	private String autoliquidacionId;
	private String importe;
	
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getAutoliquidacionId() {
		return autoliquidacionId;
	}
	public void setAutoliquidacionId(String autoliquidacionId) {
		this.autoliquidacionId = autoliquidacionId;
	}
	public String getEntidadEmisoraId() {
		return entidadEmisoraId;
	}
	public void setEntidadEmisoraId(String entidadEmisoraId) {
		this.entidadEmisoraId = entidadEmisoraId;
	}
	
	
	
}
