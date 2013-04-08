package es.ieci.tecdoc.isicres.api.business.vo;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BaseCriterioBusquedaVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -6149278585635760211L;

	/**
	 * Numero de elementos maximo a retornar
	 */
	protected Long limit;

	/**
	 * Posicion inicial del elemento
	 */
	protected Long offset;

	public static final Long MAX_RESULTS = new Long(
			Integer
					.parseInt(Configurator
							.getInstance()
							.getProperty(
									ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE)));

	public static final int DEFAULT_OFFSET = 0;

	/**
	 * Constructor por defecto de la clase.
	 */
	public BaseCriterioBusquedaVO() {
	}

	/**
	 * Constructor con parámetros de la clase.
	 * 
	 * @param limit
	 *            número máximo de resultados
	 * @param offset
	 *            posición inicial en la página de resultados
	 */
	public BaseCriterioBusquedaVO(Long limit, Long offset) {
		super();
		this.limit = limit;
		this.offset = offset;
	}

	public Long getLimit() {
		if (null == limit) {
			return MAX_RESULTS;
		}
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getOffset() {
		if (null == offset) {
			return new Long(DEFAULT_OFFSET);
		}
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

}
