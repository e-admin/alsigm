package transferencias.vos;

import common.vos.IKeyId;

public class UnidadInstalacionReeaVO extends UnidadInstalacionVO implements
		IKeyId {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String pathdepositoorigen;
	private String idElemaPadreHuecoOrigen;
	private Integer numOrdenHuecoOrigen;
	private String signaturaUIOrigen;

	public String getIduideposito() {
		return super.getId();
	}

	public void setIduideposito(String iduideposito) {
		super.setId(iduideposito);
	}

	public String getPathdepositoorigen() {
		return pathdepositoorigen;
	}

	public void setPathdepositoorigen(String pathdepositoorigen) {
		this.pathdepositoorigen = pathdepositoorigen;
	}

	public String getIdElemaPadreHuecoOrigen() {
		return idElemaPadreHuecoOrigen;
	}

	public void setIdElemaPadreHuecoOrigen(String idElemaPadreHuecoOrigen) {
		this.idElemaPadreHuecoOrigen = idElemaPadreHuecoOrigen;
	}

	public Integer getNumOrdenHuecoOrigen() {
		return numOrdenHuecoOrigen;
	}

	public void setNumOrdenHuecoOrigen(Integer numOrdenHuecoOrigen) {
		this.numOrdenHuecoOrigen = numOrdenHuecoOrigen;
	}

	public String getSignaturaUIOrigen() {
		return signaturaUIOrigen;
	}

	public void setSignaturaUIOrigen(String signaturaUIOrigen) {
		this.signaturaUIOrigen = signaturaUIOrigen;
	}

}
