package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;


public class InfoRegistroInteresadoVO {

	//SCR_REGINT
	protected Long id;
	protected Long idLibro;
	protected Long idRegistro;
	protected String nombre;
	protected Integer idPersona;
	protected Integer idDireccion;

	protected InfoRegistroDireccionVO direccion;

	protected InfoRegistroPersonaFisicaOJuridicaVO infoPersona;

	//Información del representante
	protected InfoRegistroPersonaFisicaOJuridicaVO infoRepresentante;
	protected InfoRegistroDireccionVO direccionRepresentante;


	public InfoRegistroPersonaFisicaOJuridicaVO getInfoPersona() {
		return infoPersona;
	}
	public void setInfoPersona(InfoRegistroPersonaFisicaOJuridicaVO infoPersona) {
		this.infoPersona = infoPersona;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}
	public Long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public InfoRegistroDireccionVO getDireccion() {
		return direccion;
	}
	public void setDireccion(InfoRegistroDireccionVO direccion) {
		this.direccion = direccion;
	}
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public Integer getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	public InfoRegistroPersonaFisicaOJuridicaVO getInfoRepresentante() {
		return infoRepresentante;
	}
	public void setInfoRepresentante(
			InfoRegistroPersonaFisicaOJuridicaVO infoRepresentante) {
		this.infoRepresentante = infoRepresentante;
	}

	public InfoRegistroDireccionVO getDireccionRepresentante() {
		return direccionRepresentante;
	}

	public void setDireccionRepresentante(
			InfoRegistroDireccionVO direccionRepresentante) {
		this.direccionRepresentante = direccionRepresentante;
	}




}
