package se.ficheros;

import java.util.Properties;

import org.apache.log4j.Logger;

import common.ConfigConstants;

import se.ficheros.exceptions.GestorFicherosException;
import se.ficheros.impl.GestorFicherosECMExterno;
import se.ficheros.impl.GestorFicherosECMInterno;
import se.ficheros.impl.GestorFicherosInvesdoc8;
import se.repositoriosECM.GestorRepositoriosECMFactory;
import se.repositoriosECM.IGestorRepositoriosECM;
import se.terceros.exceptions.GestorTercerosException;
import docelectronicos.vos.IRepositorioEcmVO;

/**
 * Factoría para la creación de conectores con Gestores de Ficheros.
 */
public class GestorFicherosFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorFicherosFactory.class);

	/**
	 * Obtiene el conector con el Gestor de Ficheros.
	 *
	 * @param params
	 *            Parámetros del conector
	 * @return Conector con el Gestor de Terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 */
	public static IGestorFicheros getConnector(String idRepEcm,
			Properties parametros) throws GestorFicherosException {
		IGestorFicheros connector = null;
		try {

			IGestorRepositoriosECM gestorRepositoriosECM = GestorRepositoriosECMFactory
					.getGestorRepositoriosECM(parametros);

			IRepositorioEcmVO repositorioEcmVO = gestorRepositoriosECM
					.getRepositorioEcmById(idRepEcm);

			if (IRepositorioEcmVO.REPOSITORIO_ECM_INTERNO
					.equals(repositorioEcmVO.getTipo())) {
				if(ConfigConstants.getInstance().isInvesdoc8()){
					connector = new GestorFicherosInvesdoc8(repositorioEcmVO);
				}
				else{
					connector = new GestorFicherosECMInterno(repositorioEcmVO);
				}
			} else {
				connector = new GestorFicherosECMExterno(repositorioEcmVO);
			}

			connector.initialize(parametros);

			if (logger.isInfoEnabled())
				logger.info("GestorFicheros: " + connector.getClass().getName());
		} catch (GestorFicherosException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new GestorFicherosException(e,
					"Obtener conector de gestor de ficheros"
					,"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Ficheros"
					);
		}

		return connector;
	}

}
