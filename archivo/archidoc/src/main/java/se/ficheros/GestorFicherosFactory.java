package se.ficheros;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.ficheros.exceptions.GestorFicherosException;
import se.ficheros.impl.GestorFicherosECMExterno;
import se.ficheros.impl.GestorFicherosECMInterno;
import se.ficheros.impl.GestorFicherosInvesdoc8;
import se.repositoriosECM.GestorRepositoriosECMFactory;
import se.repositoriosECM.IGestorRepositoriosECM;
import se.terceros.exceptions.GestorTercerosException;

import common.ConfigConstants;

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


			if(StringUtils.isNotBlank(idRepEcm)){

				if(logger.isDebugEnabled()){
					logger.debug("Sin Identificador del repositorio");
				}

				IGestorRepositoriosECM gestorRepositoriosECM = GestorRepositoriosECMFactory.getGestorRepositoriosECM(parametros);

				IRepositorioEcmVO repositorioEcmVO = gestorRepositoriosECM
						.getRepositorioEcmById(idRepEcm);


				if(repositorioEcmVO == null){
					throw new GestorFicherosException("No existe el repositorio ECM con identificador:" + idRepEcm);
				}

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
			}
			else{
				if(logger.isDebugEnabled()){
					logger.debug("Identificador del repositorio:" +  idRepEcm);
				}

				//Compatibilidad con documentos almacenados en versiones de archidoc anteriores a 5.0, solo disponia de INVESDOC
				if(ConfigConstants.getInstance().isInvesdoc8()){
					connector = new GestorFicherosInvesdoc8(null);
				}
				else{
					connector = new GestorFicherosECMInterno(null);
				}
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
