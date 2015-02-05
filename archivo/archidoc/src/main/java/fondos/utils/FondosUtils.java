package fondos.utils;

import org.apache.commons.lang.StringUtils;

import se.usuarios.AppUser;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionFondosBI;
import common.bi.ServiceRepository;
import common.vos.ComunidadVO;

import fondos.actions.FondoPO;
import fondos.actions.FondoToPOTransformer;
import fondos.vos.FondoVO;

/**
 * 
 * Utilidad para realizar todo tipo de acciones sobre la busqueda.
 */
public class FondosUtils {

	public static String getCodigoReferenciaPorSecciones(String codReferencia,
			boolean mostrarPaisProvincia,
			boolean mostrarArchivoYClasificadoresFondo, ComunidadVO comunidad) {

		String delimitadorCodigoReferencia = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getDelimitadorCodigoReferencia();

		if (mostrarPaisProvincia && mostrarArchivoYClasificadoresFondo)
			return codReferencia;

		String codRefPersonalizado = codReferencia;
		String cadPaisProvincia = comunidad.getCodigopais()
				+ delimitadorCodigoReferencia + comunidad.getCodigocomunidad();

		int pos = -1;
		if (!mostrarPaisProvincia) {
			pos = codRefPersonalizado.indexOf(cadPaisProvincia);
			// resto cadena sin el pais y provincia
			if (pos != -1)
				codRefPersonalizado = codRefPersonalizado.substring(pos
						+ cadPaisProvincia.length() + 1);
			cadPaisProvincia = Constants.STRING_EMPTY;
		}

		if (!mostrarArchivoYClasificadoresFondo) {
			pos = codRefPersonalizado.lastIndexOf(delimitadorCodigoReferencia);
			codRefPersonalizado = cadPaisProvincia
					+ (StringUtils.isEmpty(cadPaisProvincia) ? Constants.STRING_EMPTY
							: delimitadorCodigoReferencia)
					+ codRefPersonalizado.substring(pos + 1);
		}

		return codRefPersonalizado;
	}

	public static String getCodReferenciaPorSecciones(AppUser user,
			String idFondo, String codRef, ServiceRepository services)
			throws Exception {

		if (user == null) {
			throw new Exception(
					"Error al Obtener codigo de referecia por secciones. user es nulo");
		} else if (services == null) {
			throw new Exception(
					"Error al obtener codigo de referencia por secciones. services es nulo");
		}
		if (codRef == null) {
			throw new Exception(
					"Error al obtener codigo de referencia por secciones. Codigo Referencia es nulo");
		} else {
			// obtener el FondoVO
			GestionFondosBI fondosService = services.lookupGestionFondosBI();
			FondoVO fondo = fondosService.abrirFondo(idFondo);

			if (fondo == null) {
				throw new Exception(
						"Error al obtener codigo de referencia por secciones. fondo es nulo");
			} else {
				// a partir del FondoVO el FondoPO
				FondoPO fondoPO = (FondoPO) FondoToPOTransformer.getInstance(
						services).transform(fondo, user);

				if (fondoPO == null) {
					throw new Exception(
							"Error al obtener codigo de referencia por secciones. fondoPO es nulo");
				} else {
					// con el fondoPO obtener el ComunidadVO
					// obtener del AppUser las banderas necesarias y llamar al
					// metodo de FondosUtil para generar el
					// codigo de referencia por secciones
					return FondosUtils.getCodigoReferenciaPorSecciones(codRef,
							user.isMostrarPaisProvincia(),
							user.isMostrarArchivoCodigoClasificadores(),
							fondoPO.getComunidad());
				}
			}
		}
	}
}