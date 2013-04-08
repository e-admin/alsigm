package es.ieci.tecdoc.isicres.terceros.business.vo;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 * Clase que modela una dirección telemática de un tercero.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DireccionTelematicaVO extends BaseDireccionVO {

	private static final long serialVersionUID = -7434706595222616928L;

	/**
	 * Tipo de dirección telemática.
	 *
	 * @see TipoDireccionTelematicaVO
	 */
	protected TipoDireccionTelematicaVO tipoDireccionTelematica;

	/**
	 * Constructor por defecto.
	 */
	public DireccionTelematicaVO() {
		setTipo(DireccionType.TELEMATICA);
	}

	public TipoDireccionTelematicaVO getTipoDireccionTelematica() {
		return tipoDireccionTelematica;
	}

	public void setTipoDireccionTelematica(
			TipoDireccionTelematicaVO tipoDireccionTelematica) {
		this.tipoDireccionTelematica = tipoDireccionTelematica;
	}

	@Override
	public String toString() {
		return StringUtils.join(new String[] { getDireccion(),
				getTipoDireccionTelematica().getDescripcion() }, " ");
	}
}
