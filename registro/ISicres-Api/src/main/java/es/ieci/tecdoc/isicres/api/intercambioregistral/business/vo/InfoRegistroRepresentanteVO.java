package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

public class InfoRegistroRepresentanteVO {

	//SCR_REPRE
	protected Long id;
	protected Integer idRepresentante;
	protected Integer idRepresentado;
	protected Integer idAddress;
	protected String nombre;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIdRepresentante() {
		return idRepresentante;
	}
	public void setIdRepresentante(Integer idRepresentante) {
		this.idRepresentante = idRepresentante;
	}
	public Integer getIdRepresentado() {
		return idRepresentado;
	}
	public void setIdRepresentado(Integer idRepresentado) {
		this.idRepresentado = idRepresentado;
	}
	public Integer getIdAddress() {
		return idAddress;
	}
	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




}
