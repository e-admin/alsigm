package se.terceros.ispac;

import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.ThirdPartyConnectorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.terceros.GestorTerceros;
import se.terceros.InfoTercero;
import se.terceros.InfoTerceroImpl;
import se.terceros.TipoAtributo;
import se.terceros.exceptions.GestorTercerosException;

/**
 * Implementación del interfaz para la gestión de terceros para usar con
 * producto.
 */
public class GestorTercerosIspac implements GestorTerceros {
	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(GestorTercerosIspac.class);

	/**
	 * Constructor.
	 */
	public GestorTercerosIspac() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 */
	public void initialize(Properties params) {

	}

	/**
	 * Recupera la información de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Información del tercero.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 */
	public InfoTercero recuperarTercero(String idTercero)
			throws GestorTercerosException {

		InfoTerceroImpl infoTercero = null;

		try {
			IThirdPartyAPI itpApi = ThirdPartyConnectorFactory.getInstance()
					.getThirdPartyAPI();

			IThirdPartyAdapter tercero = itpApi.lookupById(idTercero);

			if (tercero != null) {
				infoTercero = new InfoTerceroImpl();
				infoTercero.setId(tercero.getIdExt());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
				if (tercero.getDefaultDireccionPostal() != null)
					infoTercero.setDireccion(tercero
							.getDefaultDireccionPostal().getDireccionPostal());
				infoTercero.setNombre(tercero.getNombre());
				infoTercero.setPrimerApellido(tercero.getPrimerApellido());
				infoTercero.setSegundoApellido(tercero.getSegundoApellido());
				infoTercero.setIdentificacion(tercero.getIdentificacion());
			}
		} catch (Exception e) {
			logger.error("Error al recuperar el tercero con id: " + idTercero,
					e);
			throw new GestorTercerosException(e);
		}

		return infoTercero;
	}

	/**
	 * Recupera la lista de terceros que tienen el valor valorAtrib como
	 * subtexto en el valor del atributo que se indica en tipoAtrib.
	 * <p>
	 * Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}
	 * </p>
	 * 
	 * @param tipoAtrib
	 *            Tipo de atributo ({@TipoAtributo}).
	 * @param valorAtrib
	 *            Valor del atributo.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 */
	public List recuperarTerceros(short tipoAtrib, String valorAtrib)
			throws GestorTercerosException {
		List retTerceros = new ArrayList();

		try {
			IThirdPartyAPI itpApi = ThirdPartyConnectorFactory.getInstance()
					.getThirdPartyAPI();

			IThirdPartyAdapter[] terceros = null;

			switch (tipoAtrib) {
			case TipoAtributo.IDENTIFICACION: {
				// if (IFValidator.isValidIF(valorAtrib,
				// IFValidator.DOCUMENTO_CIF))
				terceros = itpApi.lookup(valorAtrib);
				/*
				 * else if (IFValidator.isValidIF(valorAtrib,
				 * IFValidator.DOCUMENTO_NIE)) terceros =
				 * tpa.getThirdPartyAPI(new ClientContext()).lookup(valorAtrib);
				 * else if (IFValidator.isValidIF(valorAtrib,
				 * IFValidator.DOCUMENTO_NIF)) terceros =
				 * tpa.getThirdPartyAPI(new ClientContext()).lookup(valorAtrib);
				 * else throw new
				 * GestorTercerosException("Valor de atributo no v\u00E1lido ("
				 * + valorAtrib + ")");
				 */
				break;
			}

			case TipoAtributo.NOMBRE:
			case TipoAtributo.RAZON_SOCIAL: {
				terceros = itpApi.lookup(valorAtrib, null, null);
				break;
			}
			case TipoAtributo.APELLIDO1: {
				terceros = itpApi.lookup(null, valorAtrib, null);
				break;
			}
			case TipoAtributo.APELLIDO2: {
				terceros = itpApi.lookup(null, null, valorAtrib);
				break;
			}

			default:
				throw new GestorTercerosException(
						"Tipo de atributo no v\u00E1lido (" + tipoAtrib + ")");
			}

			if (terceros != null) {
				IThirdPartyAdapter tercero;
				InfoTerceroImpl infoTercero;

				for (int i = 0; i < terceros.length; i++) {
					tercero = (IThirdPartyAdapter) terceros[i];

					infoTercero = new InfoTerceroImpl();
					infoTercero.setId(tercero.getIdExt());
					infoTercero.setNombre(tercero.getNombre());
					infoTercero.setPrimerApellido(tercero.getPrimerApellido());
					infoTercero
							.setSegundoApellido(tercero.getSegundoApellido());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					if (tercero.getDefaultDireccionPostal() != null)
						infoTercero.setDireccion(tercero
								.getDefaultDireccionPostal()
								.getDireccionPostal());

					retTerceros.add(infoTercero);
				}
			}
		} catch (GestorTercerosException e) {
			logger.error("Error al recuperar los terceros: tipoAtrib=["
					+ tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al recuperar los terceros: tipoAtrib=["
					+ tipoAtrib + "], valorAtrib=[" + valorAtrib + "]", e);
			throw new GestorTercerosException(e);
		}

		return retTerceros;
	}

	/**
	 * Recupera la lista de terceros.
	 * <p>
	 * Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}
	 * </p>
	 * 
	 * @param nombre
	 *            Nombre del tercero.
	 * @param apellido1
	 *            Primer apellido del tercero.
	 * @param apellido2
	 *            Segundo apellido del tercero.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 */
	public List recuperarTerceros(String nombre, String apellido1,
			String apellido2) throws GestorTercerosException {

		List lista = new ArrayList();

		try {
			IThirdPartyAPI itpApi = ThirdPartyConnectorFactory.getInstance()
					.getThirdPartyAPI();

			IThirdPartyAdapter[] terceros = null;// tpa.getThirdPartyAPI(new
													// ClientContext()).lookupById(idTercero);
			terceros = itpApi.lookup(nombre, apellido1, apellido2);

			if (terceros != null) {
				IThirdPartyAdapter tercero;
				InfoTerceroImpl infoTercero;

				for (int i = 0; i < terceros.length; i++) {
					tercero = (IThirdPartyAdapter) terceros[i];

					infoTercero = new InfoTerceroImpl();
					infoTercero.setId(tercero.getIdExt());
					infoTercero.setIdentificacion(tercero.getIdentificacion());
					if (tercero.getDefaultDireccionPostal() != null)
						infoTercero.setDireccion(tercero
								.getDefaultDireccionPostal()
								.getDireccionPostal());
					infoTercero.setNombre(tercero.getNombre());
					infoTercero.setPrimerApellido(tercero.getPrimerApellido());
					infoTercero
							.setSegundoApellido(tercero.getSegundoApellido());

					lista.add(infoTercero);
				}
			}
		} catch (Exception e) {
			logger.error("Error al recuperar los terceros: nombre=[" + nombre
					+ "], apellido1=[" + apellido1 + "], apellido2=["
					+ apellido2 + "]", e);
			throw new GestorTercerosException(e);
		}

		return lista;
	}
}
