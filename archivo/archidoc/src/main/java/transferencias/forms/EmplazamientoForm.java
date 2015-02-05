package transferencias.forms;

import common.Constants;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * ActionForm empleado en la recogida de los datos introducidos por el usuario
 * en la creacion y edicion de un emplazamiento asociado a una unidad documental
 * 
 */
public class EmplazamientoForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String codigoPaisDefecto = null;
	String paisDefecto = null;
	String codigoProvinciaDefecto = null;
	String provinciaDefecto = null;

	String codigoPais = null;
	String pais = null;
	String codigoProvincia = null;
	String provincia = null;
	String municipio = null;
	String codigoMunicipio = null;
	String poblacion = null;
	String codigoPoblacion = null;
	String direccion = null;
	String validado = Constants.TRUE_STRING;

	String[] seleccionEmplazamiento = null;

	String contextPath = null;

	String ref = null;

	String refPais = null;
	String refProvincia = null;
	String refConcejo = null;
	String refPoblacion = null;
	String refLocalizacion = null;
	String refValidado = null;

	String validadoTextNo = null;
	String validadoTextSi = null;

	String listasPais = null;
	String listasProvincia = null;
	String listasConcejo = null;
	String listasPoblacion = null;
	String listasLocalizacion = null;

	String refTypePais = null;
	String refTypeProvincia = null;
	String refTypeConcejo = null;
	String refTypePoblacion = null;
	String refTypeLocalizacion = null;

	String idRow = null;
	String linkPressed;

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String concejo) {
		this.municipio = concejo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String[] getSeleccionEmplazamiento() {
		return seleccionEmplazamiento;
	}

	public void setSeleccionEmplazamiento(String[] seleccionEmplazamiento) {
		this.seleccionEmplazamiento = seleccionEmplazamiento;
	}

	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getCodigoPoblacion() {
		return codigoPoblacion;
	}

	public void setCodigoPoblacion(String codigoPoblacion) {
		this.codigoPoblacion = codigoPoblacion;
	}

	public String getValidado() {
		return validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

	public String getCodigoPaisDefecto() {
		return codigoPaisDefecto;
	}

	public void setCodigoPaisDefecto(String codigoPaisDefecto) {
		this.codigoPaisDefecto = codigoPaisDefecto;
	}

	public String getCodigoProvinciaDefecto() {
		return codigoProvinciaDefecto;
	}

	public void setCodigoProvinciaDefecto(String codigoProvinciaDefecto) {
		this.codigoProvinciaDefecto = codigoProvinciaDefecto;
	}

	public String getPaisDefecto() {
		return paisDefecto;
	}

	public void setPaisDefecto(String paisDefecto) {
		this.paisDefecto = paisDefecto;
	}

	public String getProvinciaDefecto() {
		return provinciaDefecto;
	}

	public void setProvinciaDefecto(String provinciaDefecto) {
		this.provinciaDefecto = provinciaDefecto;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRefConcejo() {
		return refConcejo;
	}

	public void setRefConcejo(String refConcejo) {
		this.refConcejo = refConcejo;
	}

	public String getRefPais() {
		return refPais;
	}

	public void setRefPais(String refPais) {
		this.refPais = refPais;
	}

	public String getRefPoblacion() {
		return refPoblacion;
	}

	public void setRefPoblacion(String refPoblacion) {
		this.refPoblacion = refPoblacion;
	}

	public String getRefProvincia() {
		return refProvincia;
	}

	public void setRefProvincia(String refProvincia) {
		this.refProvincia = refProvincia;
	}

	public String getRefLocalizacion() {
		return refLocalizacion;
	}

	public void setRefLocalizacion(String refLocalizacion) {
		this.refLocalizacion = refLocalizacion;
	}

	public String getListasConcejo() {
		return listasConcejo;
	}

	public void setListasConcejo(String listasConcejo) {
		this.listasConcejo = listasConcejo;
	}

	public String getListasLocalizacion() {
		return listasLocalizacion;
	}

	public void setListasLocalizacion(String listasLocalizacion) {
		this.listasLocalizacion = listasLocalizacion;
	}

	public String getListasPais() {
		return listasPais;
	}

	public void setListasPais(String listasPais) {
		this.listasPais = listasPais;
	}

	public String getListasPoblacion() {
		return listasPoblacion;
	}

	public void setListasPoblacion(String listasPoblacion) {
		this.listasPoblacion = listasPoblacion;
	}

	public String getListasProvincia() {
		return listasProvincia;
	}

	public void setListasProvincia(String listasProvincia) {
		this.listasProvincia = listasProvincia;
	}

	public String getRefTypeConcejo() {
		return refTypeConcejo;
	}

	public void setRefTypeConcejo(String refTypeConcejo) {
		this.refTypeConcejo = refTypeConcejo;
	}

	public String getRefTypeLocalizacion() {
		return refTypeLocalizacion;
	}

	public void setRefTypeLocalizacion(String refTypeLocalizacion) {
		this.refTypeLocalizacion = refTypeLocalizacion;
	}

	public String getRefTypePais() {
		return refTypePais;
	}

	public void setRefTypePais(String refTypePais) {
		this.refTypePais = refTypePais;
	}

	public String getRefTypePoblacion() {
		return refTypePoblacion;
	}

	public void setRefTypePoblacion(String refTypePoblacion) {
		this.refTypePoblacion = refTypePoblacion;
	}

	public String getRefTypeProvincia() {
		return refTypeProvincia;
	}

	public void setRefTypeProvincia(String refTypeProvincia) {
		this.refTypeProvincia = refTypeProvincia;
	}

	public String getRefValidado() {
		return refValidado;
	}

	public void setRefValidado(String refValidado) {
		this.refValidado = refValidado;
	}

	public String getValidadoTextNo() {
		return validadoTextNo;
	}

	public void setValidadoTextNo(String validadoTextNo) {
		this.validadoTextNo = validadoTextNo;
	}

	public String getValidadoTextSi() {
		return validadoTextSi;
	}

	public void setValidadoTextSi(String validadoTextSi) {
		this.validadoTextSi = validadoTextSi;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public String getLinkPressed() {
		return linkPressed;
	}

	public void setLinkPressed(String linkPressed) {
		this.linkPressed = linkPressed;
	}

}
