package se.geograficos;

import org.apache.commons.lang.StringUtils;

/**
 * Clase que representa un elemento geografico para realizar las busquedas en
 * funcion del tipo de elemento seleccionado.
 * 
 */
public class ElementoGeograficoVO {

	private String idPais;
	private String namePais;
	private String idProvincia;
	private String nameProvincia;
	private String idMunicipio;
	private String nameMunicipio;
	private String idPoblacion;
	private String namePoblacion;

	public ElementoGeograficoVO() {

	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getNamePais() {
		return namePais;
	}

	public void setNamePais(String namePais) {
		this.namePais = namePais;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNameProvincia() {
		return nameProvincia;
	}

	public void setNameProvincia(String nameProvincia) {
		this.nameProvincia = nameProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNameMunicipio() {
		return nameMunicipio;
	}

	public void setNameMunicipio(String nameMunicipio) {
		this.nameMunicipio = nameMunicipio;
	}

	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public String getNamePoblacion() {
		return namePoblacion;
	}

	public void setNamePoblacion(String namePoblacion) {
		this.namePoblacion = namePoblacion;
	}

	public String toString() {
		String res = "";
		if (StringUtils.isNotBlank(namePais))
			res += namePais + " ";
		if (StringUtils.isNotBlank(nameProvincia))
			res += nameProvincia + " ";
		if (StringUtils.isNotBlank(nameMunicipio))
			res += nameMunicipio + " ";
		if (StringUtils.isNotBlank(namePoblacion))
			res += namePoblacion;
		return res;

	}
}