package es.ieci.tecdoc.fwktd.dir3.api.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.oficina.OficinasVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

/**
 * Interfaz para los managers de datos básicos de oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DatosBasicosOficinaManager extends
		BaseManager<DatosBasicosOficinaVO, String> {

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
	public List<DatosBasicosOficinaVO> findOficinas(
			Criterios<CriterioOficinaEnum> criterios);

	/**
	 * Metodo que obtiene la información de la oficina según su codigo
	 * @param codeOficina - Código de la oficina a buscar
	 * @return {@link DatosBasicosOficinaVO} o NULO en caso de no encontrar nada
	 */
	public DatosBasicosOficinaVO getDatosBasicosOficinaByCode(String codeOficina);

	/**
	 * Guarda los datos basicos de las oficinas obtenidas del DCO
	 * @param oficinasDCO
	 */
	public void saveDatosBasicosOficinas(OficinasVO oficinasDCO);

	/**
	 * Actualiza los datos basicos de las oficinas obtenidas del DCO
	 * @param oficinasDCO
	 */
	public void updateDatosBasicosOficinas(OficinasVO oficinasDCO);
}
