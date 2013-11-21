package es.ieci.tecdoc.fwktd.dir3.core.service;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosRelacionUnidOrgOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;

/**
 * Interfaz del servicio de consulta del Directorio Común (DIR3).
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ServicioConsultaDirectorioComun {

	/**
	 * Obtiene el número de oficinas encontradas según los criterios de
	 * búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de oficinas encontradas.
	 */
	public int countOficinas(Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Realiza una búsqueda de oficinas.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Oficinas encontradas.
	 */
	public List<DatosBasicosOficina> findOficinas(
			Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Obtiene los datos básicos de una oficina.
	 *
	 * @param id
	 *            Identificador de la oficina.
	 * @return Datos básicos de la oficina.
	 */
	public DatosBasicosOficina getDatosBasicosOficina(String id);

	/**
	 * Obtiene el número de unidades orgánicas encontradas según los criterios
	 * de búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de unidades orgánicas encontradas.
	 */
	public int countUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios);

	/**
	 * Realiza una búsqueda de unidades orgánicas.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Unidades orgánicas encontradas.
	 */
	public List<DatosBasicosUnidadOrganica> findUnidadesOrganicas(
			Criterios<CriterioUnidadOrganicaEnum> criterios);


	/**
	 * Realiza una búsqueda de unidades orgánicas. Con relación a una entidad.
	 *
	 * @param codeEntity
	 * @param codeUnid
	 * @param nameUnid
	 * @return
	 */
	public List<DatosBasicosUnidadOrganica> findUnidadesOrganicasByEntidad(
			String codeEntity, String codeUnid, String nameUnid);

	/**
	 * Obtiene los datos básicos de una unidad orgánica.
	 *
	 * @param id
	 *            Identificador de la unidad orgánica.
	 * @return Datos básicos de la unidad orgánica.
	 */
	public DatosBasicosUnidadOrganica getDatosBasicosUnidadOrganica(String id);


	public DatosBasicosRelacionUnidOrgOficina getDatosBasicosRelacionUnidOrgOficinaByCodes(String codOficina, String codUnidOrg);

}
