package ieci.tecdoc.sgm.admsistema.form;

import org.apache.struts.action.ActionForm;

public class EntidadForm extends ActionForm {

	private String idEntidad;
	private String nombreCorto;
	private String nombreLargo;
	private String codigoINE;
	private String provincia;
	private String municipio;
	
	public String getCodigoINE() {
		return codigoINE;
	}

	public void setCodigoINE(String codigoINE) {
		this.codigoINE = codigoINE;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	private final static long serialVersionUID = 0;

}