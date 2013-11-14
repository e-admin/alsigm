/**
 *
 */
package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 *
 * Extiende la clase @see es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO para
 * añadir el nombre del país, provincia y municipio, ya que la clase
 * InteresadoVO solo contiene los codigos de éstos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InteresadoExReg extends InteresadoVO {

	private static final long serialVersionUID = -579647416089509621L;

	private String nombrePaisInteresado;

	private String nombreProvinciaInteresado;

	private String nombreMunicipioInteresado;

	private String nombrePaisRepresentante;

	private String nombreProvinciaRepresentante;

	private String nombreMunicipioRepresentante;

	public String getNombrePaisInteresado() {
		return nombrePaisInteresado;
	}

	public void setNombrePaisInteresado(String nombrePaisInteresado) {
		this.nombrePaisInteresado = nombrePaisInteresado;
	}

	public String getNombreProvinciaInteresado() {
		return nombreProvinciaInteresado;
	}

	public void setNombreProvinciaInteresado(String nombreProvinciaInteresado) {
		this.nombreProvinciaInteresado = nombreProvinciaInteresado;
	}

	public String getNombreMunicipioInteresado() {
		return nombreMunicipioInteresado;
	}

	public void setNombreMunicipioInteresado(String nombreMunicipioInteresado) {
		this.nombreMunicipioInteresado = nombreMunicipioInteresado;
	}

	public String getNombrePaisRepresentante() {
		return nombrePaisRepresentante;
	}

	public void setNombrePaisRepresentante(String nombrePaisRepresentante) {
		this.nombrePaisRepresentante = nombrePaisRepresentante;
	}

	public String getNombreProvinciaRepresentante() {
		return nombreProvinciaRepresentante;
	}

	public void setNombreProvinciaRepresentante(String nombreProvinciaRepresentante) {
		this.nombreProvinciaRepresentante = nombreProvinciaRepresentante;
	}

	public String getNombreMunicipioRepresentante() {
		return nombreMunicipioRepresentante;
	}

	public void setNombreMunicipioRepresentante(String nombreMunicipioRepresentante) {
		this.nombreMunicipioRepresentante = nombreMunicipioRepresentante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}






}
