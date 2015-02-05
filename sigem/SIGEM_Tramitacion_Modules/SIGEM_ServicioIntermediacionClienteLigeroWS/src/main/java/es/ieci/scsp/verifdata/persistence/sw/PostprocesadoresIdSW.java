package es.ieci.scsp.verifdata.persistence.sw;

public class PostprocesadoresIdSW{

	private String certificado;
	private int postprocesadorOrden;

	public PostprocesadoresIdSW() {
	}

	public PostprocesadoresIdSW(String certificado, int postprocesadorOrden) {
		this.certificado = certificado;
		this.postprocesadorOrden = postprocesadorOrden;
	}

	public String getCertificado() {
		return this.certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public int getPostprocesadorOrden() {
		return this.postprocesadorOrden;
	}

	public void setPostprocesadorOrden(int postprocesadorOrden) {
		this.postprocesadorOrden = postprocesadorOrden;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PostprocesadoresIdSW))
			return false;
		PostprocesadoresIdSW castOther = (PostprocesadoresIdSW) other;

		return ((this.getCertificado() == castOther.getCertificado()) || (this
				.getCertificado() != null
				&& castOther.getCertificado() != null && this.getCertificado()
				.equals(castOther.getCertificado())))
				&& (this.getPostprocesadorOrden() == castOther
						.getPostprocesadorOrden());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCertificado() == null ? 0 : this.getCertificado()
						.hashCode());
		result = 37 * result + this.getPostprocesadorOrden();
		return result;
	}

}
