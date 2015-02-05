package es.ieci.tecdoc.isicres.terceros.business.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;

/**
 * Tercero validado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TerceroValidadoVO extends BaseTerceroVO {

	private static final long serialVersionUID = 877775916903788223L;

	protected TipoDocumentoIdentificativoTerceroVO tipoDocumento;

	@Size(max = 17)
	protected String numeroDocumento;

	/**
	 * Direcciones del tercero validado. Pueden ser tanto postales como
	 * telemáticas.
	 */
	protected List<BaseDireccionVO> direcciones;

	/**
	 * Constructor por defecto.
	 */
	public TerceroValidadoVO() {
		direcciones = new ArrayList<BaseDireccionVO>();
	}

	/**
	 * Devuelve todas las direcciones del tercero.
	 *
	 * @return
	 */
	public List<BaseDireccionVO> getDirecciones() {
		return direcciones;
	}

	/**
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DireccionFisicaVO> getDireccionesFisicas() {
		return (List<DireccionFisicaVO>) CollectionUtils.select(
				getDirecciones(),
				PredicateUtils.instanceofPredicate(DireccionFisicaVO.class));

	}

	public DireccionFisicaVO getDireccionFisicaPrincipal() {
		return (DireccionFisicaVO) CollectionUtils.find(
				getDireccionesFisicas(), new BeanPropertyValueEqualsPredicate(
						"principal", true));
	}

	/**
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DireccionTelematicaVO> getDireccionesTelematicas() {
		return (List<DireccionTelematicaVO>) CollectionUtils
				.select(getDirecciones(), PredicateUtils
						.instanceofPredicate(DireccionTelematicaVO.class));
	}

	public DireccionTelematicaVO getDireccionTelematicaPrincipal() {
		return (DireccionTelematicaVO) CollectionUtils.find(
				getDireccionesTelematicas(),
				new BeanPropertyValueEqualsPredicate("principal", true));
	}

	public void setDirecciones(List<BaseDireccionVO> direcciones) {
		this.direcciones = direcciones;
	}

	public TipoDocumentoIdentificativoTerceroVO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(
			TipoDocumentoIdentificativoTerceroVO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
}
