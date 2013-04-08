package es.ieci.tecdoc.sicres.terceros.web.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.FilterVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.OperatorEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;
import es.ieci.tecdoc.sicres.terceros.web.delegate.MasterValuesDelegate;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class DWRService {

	/**
	 *
	 *
	 * @return
	 */
	public List<PaisVO> getPaises() {
		return getMasterValuesDelegate().getPaises();
	}

	/**
	 *
	 * @return
	 */
	public List<ProvinciaVO> getProvincias() {
		return getMasterValuesDelegate().getProvincias();
	}

	/**
	 *
	 * @return
	 */
	public String getProvinciaPorDefecto() {
		return Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_PROVINCIA_POR_DEFECTO);
	}

	/**
	 *
	 * @param nombreProvincia
	 * @return
	 */
	public List<CiudadVO> getCiudades(String nombreProvincia) {
		ProvinciaVO provincia = new ProvinciaVO();
		provincia.setNombre(nombreProvincia);

		return getMasterValuesDelegate().getCiudades(provincia);
	}

	/**
	 * Devuelve los terceros físicos
	 *
	 * @param numeroDocumento
	 * @param idTercero
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TerceroValidadoVO> getTercerosFisicos(String numeroDocumento,
			String idTercero) {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);
		FilterVO filter = new FilterVO();
		filter.setField("nif");
		filter.setOperator(OperatorEnum.ES_IGUAL);
		filter.setValue(numeroDocumento);
		criteria.setFilters(Arrays.asList(new FilterVO[] { filter }));

		List<TerceroValidadoVO> terceros = getTercerosFacade().findByCriteria(
				criteria);

		if (!StringUtils.isEmpty(idTercero)) {
			terceros = (List<TerceroValidadoVO>) CollectionUtils
					.selectRejected(terceros,
							new BeanPropertyValueEqualsPredicate("id",
									idTercero));
		}

		return terceros;
	}

	/**
	 *
	 * @param numeroDocumento
	 * @param idTercero
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TerceroValidadoVO> getTercerosJuridicos(String numeroDocumento,
			String idTercero) {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.JURIDICO);
		FilterVO filter = new FilterVO();
		filter.setField("cif");
		filter.setOperator(OperatorEnum.ES_IGUAL);
		filter.setValue(numeroDocumento);
		criteria.setFilters(Arrays.asList(new FilterVO[] { filter }));

		List<TerceroValidadoVO> terceros = getTercerosFacade().findByCriteria(
				criteria);

		if (!StringUtils.isEmpty(idTercero)) {
			terceros = (List<TerceroValidadoVO>) CollectionUtils
					.selectRejected(terceros,
							new BeanPropertyValueEqualsPredicate("id",
									idTercero));
		}

		return terceros;
	}

	public MasterValuesDelegate getMasterValuesDelegate() {
		return masterValuesDelegate;
	}

	public void setMasterValuesDelegate(
			MasterValuesDelegate masterValuesDelegate) {
		this.masterValuesDelegate = masterValuesDelegate;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	protected MasterValuesDelegate masterValuesDelegate;

	protected TercerosFacade tercerosFacade;
}
