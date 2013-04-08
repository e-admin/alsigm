package es.ieci.scsp.verifdata.persistence.sw;


public class PostprocesadoresSW{

	private PostprocesadoresIdSW id;
	private CertificadosAutorizacionesSW certificadosAutorizaciones;
	private String clase;

	public PostprocesadoresSW() {
	}

	public PostprocesadoresSW(PostprocesadoresIdSW id,
			CertificadosAutorizacionesSW certificadosAutorizaciones, String clase) {
		this.id = id;
		this.certificadosAutorizaciones = certificadosAutorizaciones;
		this.clase = clase;
	}

	public PostprocesadoresIdSW getId() {
		return this.id;
	}

	public void setId(PostprocesadoresIdSW id) {
		this.id = id;
	}


	public CertificadosAutorizacionesSW getCertificadosAutorizaciones() {
		return this.certificadosAutorizaciones;
	}

	public void setCertificadosAutorizaciones(
			CertificadosAutorizacionesSW certificadosAutorizaciones) {
		this.certificadosAutorizaciones = certificadosAutorizaciones;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

}
