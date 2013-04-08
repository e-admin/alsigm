/*
 * Created on 19-abr-2005
 *
 */
package common.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

import se.util.MapUtil;

import common.Constants;
import common.Messages;
import common.exceptions.ConfigException;
import common.vos.ComunidadVO;
import common.vos.PaisVO;
import common.vos.PaisesVO;

import configuracion.bi.GestionInfoSistemaBI;

/**
 * Implementación del interfaz a través del que obtener los paises y comunidades
 * autónomas a las que se permite asociar los fondos documentales gestionados
 * por el sistema
 */
public class PaisesRIImpl implements PaisesRI {
	static Logger logger = Logger.getLogger(PaisesRIImpl.class);
	static PaisesRIImpl singlenton = null;
	static Map paises;

	private PaisesRIImpl() {
		paises = new LinkedHashMap();
	}

	public static PaisesRIImpl getInstance(GestionInfoSistemaBI infoSistema,
			Locale locale) throws ConfigException {
		if (singlenton == null) {
			PaisesVO paisesVO = infoSistema.getPaises();
			if (paisesVO == null) {
				// //Si no existe en la base de Datos
				// paisesVO = getPaisesXml();
				//
				// if(paisesVO == null) {
				throw new ConfigException(Messages.getString(
						Constants.ERROR_CONFIGURACION_ARCHIVO_MAP_PAISES,
						locale));
				// }
			}
			singlenton = new PaisesRIImpl();
			paises = paisesVO.getPaises();
		}
		return singlenton;
	}

	// /**
	// * Obtiene los paises del MapPaises.xml
	// * @return
	// */
	// private static PaisesVO getPaisesXml() {
	// PaisesVO paisesVO = new PaisesVO();
	// try {
	// ConfiguracionFondos config =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionFondos();
	// URL digesterRulesFile =
	// PaisesRIImpl.class.getResource(Globals.RULES_MAPPAISES);
	// Digester digester = DigesterLoader.createDigester(digesterRulesFile);
	// paisesVO = (PaisesVO)
	// digester.parse(PaisesRIImpl.class.getResourceAsStream(config.getListaPaisesMappingFile()));
	// return paisesVO;
	// } catch (MalformedURLException e1) {
	// logger.error(e1);
	// } catch (Exception e2) {
	// logger.error(e2);
	// }
	//
	// return null;
	// }
	//
	/**
	 * Obtiene la lista con los paises a los que se pueden asociar los fondos
	 * documentales gestionados por el sistema
	 * 
	 * @return Lista de paises {@link PaisVO}
	 */
	public List getAllPaises() {
		List ret = MapUtil.toList(paises);
		Collections.sort(ret, new Comparator() {
			public int compare(Object o1, Object o2) {
				PaisVO pais1 = (PaisVO) o1;
				PaisVO pais2 = (PaisVO) o2;
				return pais1.getNombre().compareTo(pais2.getNombre());
			}
		});
		return ret;
	}

	/**
	 * Obtiene los datos de un determinado pais
	 * 
	 * @param idPais
	 *            Identificador del pais
	 */
	public PaisVO getPaisXId(String id) {
		return (PaisVO) paises.get(id);
	}

	/**
	 * Obtiene los datos de una comunidad a la que se pueden asociar los fondos
	 * documentales gestionados por el sistema.
	 * 
	 * @param codPais
	 *            Código del pais al que pertenece la comunidad
	 * @param codComunidad
	 *            Códico de la comunidad
	 */
	public ComunidadVO getComunidad(String codPais, final String codComunidad) {
		PaisVO paises = getPaisXId(codPais);
		List comunidades = paises.getComunidades();
		if (!util.CollectionUtils.isEmptyCollection(comunidades)) {
			return (ComunidadVO) CollectionUtils.find(comunidades,
					new Predicate() {
						public boolean evaluate(Object arg0) {
							return ((ComunidadVO) arg0).getCodigocomunidad()
									.equalsIgnoreCase(codComunidad);
						}
					});
		}
		return null;
	}

	public void addPais(PaisVO paisVO) {
		if (paises == null)
			paises = new LinkedHashMap();
		paises.put(paisVO.getCodigo(), paisVO);
	}
}