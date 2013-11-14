package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 * Dirección física de un tercero.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DireccionFisicaVO extends BaseDireccionVO {

	/**
	 * Nombre de la ciudad asociada a la dirección.
	 */
	@Valid
	protected CiudadVO ciudad;

	/**
	 * Nombre de la provincia asociada a la dirección.
	 */
	@Valid
	protected ProvinciaVO provincia;

	/**
	 *
	 */
	protected PaisVO pais;

	/**
	 * Código postal de la dirección.
	 */
	@Size(max = 5)
	protected String codigoPostal;

	/**
	 * Constructor por defecto.
	 */
	public DireccionFisicaVO() {
		setTipo(DireccionType.FISICA);
	}

	public CiudadVO getCiudad() {
		if (null == ciudad) {
			ciudad = new CiudadVO();
		}
		return ciudad;
	}

	public void setCiudad(CiudadVO ciudad) {
		this.ciudad = ciudad;
	}

	public ProvinciaVO getProvincia() {
		if (null == provincia) {
			provincia = new ProvinciaVO();
		}
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public PaisVO getPais() {
		if (null == pais) {
			pais = new PaisVO();
		}
		return pais;
	}

	public void setPais(PaisVO pais) {
		this.pais = pais;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Override
	public String toString() {
		return StringUtils.join(new String[] { getDireccion(),
				getCiudad().getNombre(), getCodigoPostal(),
				getProvincia().getNombre() }, " ");
	}

	private static final long serialVersionUID = -3561288147089290466L;
}