package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.dir3.api.helper.OficinaHelper;
import es.ieci.tecdoc.fwktd.dir3.api.helper.RelacionesUnidOrgOficinaHelper;
import es.ieci.tecdoc.fwktd.dir3.api.helper.UnidadOrganicaHelper;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosRelacionUnidOrgOficinaManager;
import es.ieci.tecdoc.fwktd.dir3.api.manager.DatosBasicosUnidadOrganicaManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosRelacionUnidOrgOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosRelacionUnidOrgOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;

/**
 * Implementación local del servicio de consulta del Directorio Común (DIR3).
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioConsultaDirectorioComunImpl implements
		ServicioConsultaDirectorioComun {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioConsultaDirectorioComunImpl.class);

	/**
	 * Gestor de oficinas.
	 */
	private DatosBasicosOficinaManager datosBasicosOficinaManager;

	/**
	 * Gestor de unidades orgánicas.
	 */
	private DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager;

	/**
	 * Gestor de las relaciones entre oficina y unid. organicas
	 */
	private DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager;

	/**
	 * Constructor.
	 */
	public ServicioConsultaDirectorioComunImpl() {
		super();
	}

	public DatosBasicosOficinaManager getDatosBasicosOficinaManager() {
		return datosBasicosOficinaManager;
	}

	public void setDatosBasicosOficinaManager(
			DatosBasicosOficinaManager datosBasicosOficinaManager) {
		this.datosBasicosOficinaManager = datosBasicosOficinaManager;
	}

	public DatosBasicosUnidadOrganicaManager getDatosBasicosUnidadOrganicaManager() {
		return datosBasicosUnidadOrganicaManager;
	}

	public void setDatosBasicosUnidadOrganicaManager(
			DatosBasicosUnidadOrganicaManager datosBasicosUnidadOrganicaManager) {
		this.datosBasicosUnidadOrganicaManager = datosBasicosUnidadOrganicaManager;
	}



	public DatosBasicosRelacionUnidOrgOficinaManager getDatosBasicosRelacionUnidOrgOficinaManager() {
		return datosBasicosRelacionUnidOrgOficinaManager;
	}

	public void setDatosBasicosRelacionUnidOrgOficinaManager(
			DatosBasicosRelacionUnidOrgOficinaManager datosBasicosRelacionUnidOrgOficinaManager) {
		this.datosBasicosRelacionUnidOrgOficinaManager = datosBasicosRelacionUnidOrgOficinaManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#countOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public int countOficinas(Criterios<CriterioOficinaEnum> criterios) {

		logger.info("Llamada a countOficinas: criterios=[{}]", criterios);

		// Obtener el número de oficinas que cumplan los criterios
		return getDatosBasicosOficinaManager().countOficinas(criterios);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#findOficinas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public List<DatosBasicosOficina> findOficinas(
			Criterios<CriterioOficinaEnum> criterios) {

		logger.info("Llamada a findOficinas: criterios=[{}]", criterios);

		// Búsqueda de oficinas según los criterios
		return OficinaHelper
				.getDatosBasicosOficinas(getDatosBasicosOficinaManager()
						.findOficinas(criterios));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#getDatosBasicosOficina(java.lang.String)
	 */
	public DatosBasicosOficina getDatosBasicosOficina(String id) {

		logger.info("Llamada a getDatosBasicosOficina: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener los datos básicos de la oficina
		return OficinaHelper
				.getDatosBasicosOficina(getDatosBasicosOficinaManager().get(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#countUnidadesOrganicas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public int countUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios) {

		logger.info("Llamada a countUnidadesOrganicas: criterios=[{}]",
				criterios);

		// Obtener el número de unidades orgánicas que cumplan los criterios
		return getDatosBasicosUnidadOrganicaManager().countUnidadesOrganicas(
				criterios);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#findUnidadesOrganicas(es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios)
	 */
	public List<DatosBasicosUnidadOrganica> findUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios) {

		logger.info("Llamada a findUnidadesOrganicas: criterios=[{}]",
				criterios);

		// Búsqueda de unidades orgánicas según los criterios
		return UnidadOrganicaHelper
				.getDatosBasicosUnidadesOrganicas(getDatosBasicosUnidadOrganicaManager()
						.findUnidadesOrganicas(criterios));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#findUnidadesOrganicasByEntidad(String, String, String)
	 */
	public List<DatosBasicosUnidadOrganica> findUnidadesOrganicasByEntidad(
			String codeEntity, String codeUnid, String nameUnid) {

		DatosBasicosRelacionUnidOrgOficinaVO relacion = new DatosBasicosRelacionUnidOrgOficinaVO();
		relacion.setCodigoOficina(codeEntity);
		relacion.setCodigoUnidadOrganica(codeUnid);
		relacion.setDenominacionUnidadOrganica(nameUnid);

		// Búsqueda de unidades orgánicas según los criterios
		return UnidadOrganicaHelper
				.getDatosBasicosUnidadesOrganicas(getDatosBasicosUnidadOrganicaManager()
						.findUnidadesOrganicasByEntidad(relacion));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#getDatosBasicosUnidadOrganica(java.lang.String)
	 */
	public DatosBasicosUnidadOrganica getDatosBasicosUnidadOrganica(String id) {

		logger.info("Llamada a getDatosBasicosOficina: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		// Obtener los datos básicos de la unidad orgánica
		return UnidadOrganicaHelper
				.getDatosBasicosUnidadOrganica(getDatosBasicosUnidadOrganicaManager()
						.get(id));
	}

	/**
	 * @see es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun#getDatosBasicosRelacionUnidOrgOficinaByCodes()
	 */
	public DatosBasicosRelacionUnidOrgOficina getDatosBasicosRelacionUnidOrgOficinaByCodes(
			String codOficina, String codUnidOrg) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"Llamada a getDatosBasicosRelacionUnidOrgOficinaByCodes: codOficina=[{")
				.append(codOficina).append("}] y codUnidOrg=[{")
				.append(codUnidOrg).append("}]");

		logger.info(sb.toString());

		// Obtener los datos básicos de la unidad orgánica
		return RelacionesUnidOrgOficinaHelper
				.getDatosBasicosRelacionUnidOrgOficina(getDatosBasicosRelacionUnidOrgOficinaManager()
						.getRelacionesByOficinaAndUnidad(codOficina, codUnidOrg));

	}

}
