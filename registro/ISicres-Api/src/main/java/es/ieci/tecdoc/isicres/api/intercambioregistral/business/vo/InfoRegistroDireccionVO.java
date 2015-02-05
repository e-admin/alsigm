package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

public class InfoRegistroDireccionVO {

	protected Integer id;
	protected Integer idPersona;
	protected Integer tipoDireccion;
	
	protected InfoRegistroDomicilioInteresadoVO domicilioInteresado;
	protected InfoRegistroDireccionTelematicaInteresadoVO direccionTelematicaInteresado;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public Integer getTipoDireccion() {
		return tipoDireccion;
	}
	public void setTipoDireccion(Integer tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}
	public InfoRegistroDomicilioInteresadoVO getDomicilioInteresado() {
		return domicilioInteresado;
	}
	public void setDomicilioInteresado(
			InfoRegistroDomicilioInteresadoVO domicilioInteresado) {
		this.domicilioInteresado = domicilioInteresado;
	}
	public InfoRegistroDireccionTelematicaInteresadoVO getDireccionTelematicaInteresado() {
		return direccionTelematicaInteresado;
	}
	public void setDireccionTelematicaInteresado(
			InfoRegistroDireccionTelematicaInteresadoVO direccionTelematicaInteresado) {
		this.direccionTelematicaInteresado = direccionTelematicaInteresado;
	}
	
	
	
}
