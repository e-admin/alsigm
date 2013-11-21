package es.ieci.tecdoc.fwktd.dir3.api.manager.impl;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosOficinaDao;
import es.ieci.tecdoc.fwktd.dir3.api.helper.OficinaHelper;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

/**
 * Implementación del manager de datos básicos de oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DatosBasicosOficinaManagerImpl extends
		BaseManagerImpl<DatosBasicosOficinaVO, String> implements
		DatosBasicosOficinaManager {

	private static final String VIGENTE = "V";
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DatosBasicosOficinaManagerImpl.class);

	/**
	 * Constructor.
	 *
	 * @param aGenericDao
	 */
	public DatosBasicosOficinaManagerImpl(
			BaseDao<DatosBasicosOficinaVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager#countOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public int countOficinas(Criterios<CriterioOficinaEnum> criterios) {

		logger.info("Obteniendo el número de oficinas. Criterios: {}", criterios);

		// Obtiene el número de oficinas en base a los criterios
		return ((DatosBasicosOficinaDao)getDao()).countOficinas(criterios);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager#findOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public List<DatosBasicosOficinaVO> findOficinas(
			Criterios<CriterioOficinaEnum> criterios) {

		logger.info("Realizando búsqueda de oficinas. Criterios: {}", criterios);

		// Realiza la búsqueda de oficinas en base a los criterios
		return ((DatosBasicosOficinaDao) getDao()).findOficinas(criterios);
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager#saveDatosBasicosOficinas(es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO)
	 */
	public void saveDatosBasicosOficinas(OficinasVO oficinasDCO) {
		ListIterator<OficinaVO> itr = oficinasDCO.getOficinas().listIterator();
		OficinaVO oficina = null;
		while(itr.hasNext()){
			oficina = itr.next();
			if(VIGENTE.equals(oficina.getDatosVigencia().getEstadoOficina())){
				guardarDatosBasicosOficina(oficina);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager#updateDatosBasicosOficinas(es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinaVO)
	 */
	public void updateDatosBasicosOficinas(OficinasVO oficinasDCO) {
		ListIterator<OficinaVO> itr = oficinasDCO.getOficinas().listIterator();
		OficinaVO oficina = null;
		while (itr.hasNext()) {
			oficina = itr.next();

			// Buscamos datos sobre la oficina
			DatosBasicosOficinaVO datosBasicosOficina = getDatosBasicosOficinaByCode(oficina
					.getDatosIdentificativos().getCodigoOficina());

			if (null != datosBasicosOficina) {
				// Actualizamos o Borramos
				actualizarOBorrarDatosBasicosOficina(oficina,
						datosBasicosOficina);
			} else {
				// Guardamos datos
				guardarDatosBasicosOficina(oficina);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager#getDatosBasicosOficinaByCode(java.lang.String)
	 */
	public DatosBasicosOficinaVO getDatosBasicosOficinaByCode(String codeOficina) {
		DatosBasicosOficinaVO result = null;
		try {
			// Buscamos datos sobre la oficina
			result = ((DatosBasicosOficinaDao) getDao()).get(codeOficina);
		} catch (ObjectRetrievalFailureException oRFE) {
			if (logger.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append(
						"No se ha encontrado datos para la oficina con el código: ")
						.append(codeOficina);
				logger.debug(sb.toString());
			}
		}
		return result;
	}

	/**
	 * Método encargado de actualizar o borrar la {@link DatosBasicosOficinaVO}
	 * según el estado de {@link OficinaVO} pasado como parámetro
	 *
	 * @param oficina
	 *            - Datos con la oficina a borrar
	 * @param datosBasicosOficina
	 *            - Objeto que se borra
	 */
	private void actualizarOBorrarDatosBasicosOficina(OficinaVO oficina,
			DatosBasicosOficinaVO datosBasicosOficina) {
		// Comprobamos el estado vigente de la oficina
		if (VIGENTE.equals(oficina.getDatosVigencia().getEstadoOficina())) {
			setDatosBasicosOficina(datosBasicosOficina, oficina);
			// Actualizamos los datos de la oficina
			((DatosBasicosOficinaDao) getDao()).update(datosBasicosOficina);
		} else {
			// Borramos los datos de la oficina
			((DatosBasicosOficinaDao) getDao()).delete(datosBasicosOficina
					.getId());
		}
	}


	/**
	 * Método que almacena en BBDD los datos básicos de una oficina a partir de
	 * un objeto {@link OficinasVO}
	 *
	 * @param oficina
	 *            - Datos de la oficina
	 */
	private void guardarDatosBasicosOficina(OficinaVO oficina) {
		//comprobamos si la oficina esta vigente
		if (VIGENTE.equals(oficina.getDatosVigencia().getEstadoOficina())) {
			DatosBasicosOficinaVO datosBasicosOficina = new DatosBasicosOficinaVO();
			setDatosBasicosOficina(datosBasicosOficina, oficina);
			((DatosBasicosOficinaDao) getDao()).save(datosBasicosOficina);
		}
	}


	/**
	 * Método encargado de setear los datos básicos de un objeto
	 * {@link OficinasVO} en un {@link DatosBasicosOficinaVO}
	 *
	 * @param datosBasicosOficina
	 *            - Objeto en el que se copian los datos de {@link OficinasVO}
	 * @param oficina
	 *            - Objeto con los datos que se copian a
	 *            {@link DatosBasicosOficinaVO}
	 */
	private void setDatosBasicosOficina(DatosBasicosOficinaVO datosBasicosOficina, OficinaVO oficina)
	{
		datosBasicosOficina.setId(oficina.getDatosIdentificativos().getCodigoOficina());
		datosBasicosOficina.setNombre(oficina.getDatosIdentificativos().getDenominacionOficina());
		datosBasicosOficina.setIdExternoFuente(oficina.getDatosIdentificativos().getExternoFuente());
		datosBasicosOficina.setIdUnidadResponsable(oficina.getDatosDependenciaJerarquica().getCodigoUnidadResponsable());

		datosBasicosOficina.setDireccion(OficinaHelper.getOficinaAddress(oficina.getDatosLocalizacion()));
		datosBasicosOficina.setLocalidad(oficina.getDatosLocalizacion().getLocalidad());
		datosBasicosOficina.setProvincia(oficina.getDatosLocalizacion().getProvincia());
	}

}
