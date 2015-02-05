package se.repositoriosECM;

import java.util.List;
import java.util.Properties;

import se.ficheros.exceptions.GestorFicherosException;

import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.RepositorioEcmVO;

public interface IGestorRepositoriosECM {

	/**
	 * Inicializa los parámetros necesarios del conector
	 *
	 * @param params
	 */
	public void initialize(Properties params) throws GestorFicherosException;

	/**
	 * Obtiene el {@link IRepositorioEcmVO} por su identificador
	 *
	 * @param idRepEcm
	 *            Cadena que define el identificador del repositorio ecm
	 * @return {@link IRepositorioEcmVO}
	 */
	public IRepositorioEcmVO getRepositorioEcmById(String idRepEcm)
			throws GestorFicherosException;

	/**
	 * Obtiene la lista de {@link IRepositorioEcmVO} definidos en el fichero de
	 * configuración
	 *
	 * @return Lista de {@link IRepositorioEcmVO}
	 * @throws Exception
	 */
	public List<IRepositorioEcmVO> getRepositoriosEcm()
			throws GestorFicherosException;

	/**
	 * Obtiene la lista de {@link IRepositorioEcmVO} definidos en el fichero de
	 * configuración que son de tipo interno
	 *
	 * @return Lista de {@link IRepositorioEcmVO}
	 * @throws Exception
	 */
	public List<IRepositorioEcmVO> getRepositoriosEcmInternos()
			throws GestorFicherosException;

}
