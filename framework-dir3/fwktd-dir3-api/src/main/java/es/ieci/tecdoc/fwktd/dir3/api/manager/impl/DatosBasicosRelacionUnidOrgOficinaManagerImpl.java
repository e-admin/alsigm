package es.ieci.tecdoc.fwktd.dir3.api.manager.impl;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosRelacionUnidOrgOficinaDao;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosRelacionUnidOrgOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.relacion.RelacionesUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioRelacionUnidOrgOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

public class DatosBasicosRelacionUnidOrgOficinaManagerImpl extends
		BaseManagerImpl<DatosBasicosRelacionUnidOrgOficinaVO, String> implements
		DatosBasicosRelacionUnidOrgOficinaManager {

	private static final String VIGENTE = "V";
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DatosBasicosRelacionUnidOrgOficinaManagerImpl.class);


	/**
	 * Constructor.
	 *
	 * @param aGenericDao
	 */
	public DatosBasicosRelacionUnidOrgOficinaManagerImpl(
			BaseDao<DatosBasicosRelacionUnidOrgOficinaVO, String> aGenericDao) {
		super(aGenericDao);
	}

	public int countRelaciones(Criterios<CriterioOficinaEnum> criterios) {
		logger.info("Obteniendo el número de relaciones entre las oficinas y las unidades orgánicas. Criterios: {}", criterios);

		// Obtiene el número de unidades orgánicas en base a los criterios
		return ((DatosBasicosRelacionUnidOrgOficinaDao)getDao()).countRelacionesUnidOrgOficina(criterios);
	}

	public List<DatosBasicosRelacionUnidOrgOficinaVO> findRelaciones(
			Criterios<CriterioOficinaEnum> criterios) {
		logger.info("Realizando búsqueda de relaciones entre las oficinas y las unidades orgánicas. Criterios: {}", criterios);

		// Realiza la búsqueda de unidades orgánicas en base a los criterios
		return ((DatosBasicosRelacionUnidOrgOficinaDao)getDao()).findRelacionesUnidOrgOficina(criterios);
	}

	public DatosBasicosRelacionUnidOrgOficinaVO getRelacionesByOficinaAndUnidad(
			String codOficina, String codUnidad) {

		DatosBasicosRelacionUnidOrgOficinaVO result = null;

		// Buscamos datos sobre la relacion de la oficina y la unid. organica indicada
		Criterios criterios = new Criterios<CriterioRelacionUnidOrgOficinaEnum>().addCriterio(
		(new Criterio(
				CriterioRelacionUnidOrgOficinaEnum.OFICINA_ID,
				OperadorCriterioEnum.EQUAL, codOficina))).addCriterio(
		 new Criterio(
				CriterioRelacionUnidOrgOficinaEnum.UO_ID,
				OperadorCriterioEnum.EQUAL, codUnidad));

		//Realizamos la búsqueda según el criterio
		List<DatosBasicosRelacionUnidOrgOficinaVO> listado = (List<DatosBasicosRelacionUnidOrgOficinaVO>) ((DatosBasicosRelacionUnidOrgOficinaDao) getDao())
				.findRelacionesUnidOrgOficina(criterios);

		if(!listado.isEmpty()){
			result = listado.get(0);
		}

		return result;
	}

	public void saveDatosBasicosRelacionesUnidOrgOficinaVO(
			RelacionesUnidOrgOficinaVO relacionesUnidOrgOficina) {
		ListIterator<RelacionUnidOrgOficinaVO> itr = relacionesUnidOrgOficina.getRelacionesUnidOrgOficinaVO().listIterator();
		RelacionUnidOrgOficinaVO relacionUnidOrgOficina = null;
		while(itr.hasNext()){
			relacionUnidOrgOficina = itr.next();
			if(VIGENTE.equals(relacionUnidOrgOficina.getEstadoRelacion())){
				guardarRelacionUnidOrgOficina(relacionUnidOrgOficina);
			}
		}

	}

	private void guardarRelacionUnidOrgOficina(
			RelacionUnidOrgOficinaVO relacionUnidOrgOficina) {
		DatosBasicosRelacionUnidOrgOficinaVO datosBasicos = setDatosRelacionUniOrgOficinaVO(relacionUnidOrgOficina);
		//almacenamos los datos basicos de la unidad
		((DatosBasicosRelacionUnidOrgOficinaDao) getDao()).save(datosBasicos);
	}

	private DatosBasicosRelacionUnidOrgOficinaVO setDatosRelacionUniOrgOficinaVO(
			RelacionUnidOrgOficinaVO relacionUnidOrgOficina) {
		DatosBasicosRelacionUnidOrgOficinaVO datosBasicos = new DatosBasicosRelacionUnidOrgOficinaVO();
		//TODO recuperar id
		datosBasicos.setId("0");
		datosBasicos.setCodigoOficina(relacionUnidOrgOficina.getCodigoOficina());
		datosBasicos.setCodigoUnidadOrganica(relacionUnidOrgOficina.getCodigoUnidadOrganica());
		datosBasicos.setDenominacionOficina(relacionUnidOrgOficina.getDenominacionOficina());
		datosBasicos.setDenominacionUnidadOrganica(relacionUnidOrgOficina.getDenominacionUnidadOrganica());
		datosBasicos.setEstadoRelacion(relacionUnidOrgOficina.getEstadoRelacion());
		return datosBasicos;
	}

	public void updateDatosBasicosRelacionesUnidOrgOficinaVO(
			RelacionesUnidOrgOficinaVO relacionesUnidOrgOficina) {

		ListIterator<RelacionUnidOrgOficinaVO> itr = relacionesUnidOrgOficina
				.getRelacionesUnidOrgOficinaVO().listIterator();
		RelacionUnidOrgOficinaVO relacionUnidOrgOficina = null;
		while (itr.hasNext()) {
			relacionUnidOrgOficina = itr.next();

			//buscamos la relacion por unid. y oficina
			DatosBasicosRelacionUnidOrgOficinaVO datosBasicosUnidadOrganica = getRelacionesByOficinaAndUnidad(
					relacionUnidOrgOficina.getCodigoOficina(),
					relacionUnidOrgOficina.getCodigoUnidadOrganica());

			if (null != datosBasicosUnidadOrganica) {
				// Actualizamos o Borramos
				actualizarOBorrarRelacionUnidOrgOficina(relacionUnidOrgOficina,
						datosBasicosUnidadOrganica);
			} else {
				//si la relación esta vigente la insertamos
				if (VIGENTE.equals(relacionUnidOrgOficina.getEstadoRelacion())) {
					// insertamos los datos basicos de la relacion
					((DatosBasicosRelacionUnidOrgOficinaDao) getDao())
							.save(setDatosRelacionUniOrgOficinaVO(relacionUnidOrgOficina));
				}
			}
		}
	}

	private void actualizarOBorrarRelacionUnidOrgOficina(
			RelacionUnidOrgOficinaVO relacionUnidOrgOficina,
			DatosBasicosRelacionUnidOrgOficinaVO datosBasicosUnidadOrganica) {
		if (VIGENTE.equals(relacionUnidOrgOficina.getEstadoRelacion())) {
			// Actualizamos los datos basicos de la unidad organica
			((DatosBasicosRelacionUnidOrgOficinaDao) getDao())
					.update(setDatosRelacionUniOrgOficinaVO(relacionUnidOrgOficina));
		} else {
			// Borramos la relacion de la oficina y la unid. organica indicada
			((DatosBasicosRelacionUnidOrgOficinaDao) getDao())
					.deleteRelacionesUnidOrgOficina(
							relacionUnidOrgOficina.getCodigoOficina(),
							relacionUnidOrgOficina.getCodigoUnidadOrganica());
		}
	}
}
