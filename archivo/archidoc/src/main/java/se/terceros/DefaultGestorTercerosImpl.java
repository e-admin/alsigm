package se.terceros;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.terceros.exceptions.GestorTercerosException;
import xml.config.ConfiguracionArchivoManager;

import common.util.StringUtils;
import common.util.XmlFacade;

/**
 * Implementación del interfaz para la gestión de terceros de prueba.
 */
public class DefaultGestorTercerosImpl implements GestorTerceros {

	/** Logger de la clase. */
	private static Logger logger = Logger
			.getLogger(DefaultGestorTercerosImpl.class);

	/** XML con los datos. */
	private static XmlFacade xml = null;

	/**
	 * Constructor.
	 */
	public DefaultGestorTercerosImpl() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 */
	public void initialize(Properties params) {
		try {
			// if (xml == null)
			// {
			// // Carga del documento XML
			// xml = new XmlFacade(getClass().getClassLoader()
			// .getResourceAsStream("test/sistema_gestor_terceros.xml"));
			//
			// logger.info("Fichero XML del Sistema Gestor de Terceros cargado");
			// }

			xml = ConfiguracionArchivoManager.getInstance()
					.getDefaultSistemaGestorTerceros();
		} catch (Exception e) {
			logger.error("Error al leer el fichero de datos de terceros", e);
		}
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

		if (StringUtils.isNotBlank(idTercero))
			infoTercero = getInfoTercero(xml.getNode("/terceros/tercero[@id='"
					+ idTercero + "']"));

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
		List lista = new ArrayList();
		// InfoTerceroImpl infoTercero;

		if (StringUtils.isNotBlank(valorAtrib)) {
			switch (tipoAtrib) {
			case TipoAtributo.IDENTIFICACION: {
				NodeIterator nodeIt = xml.getNodeIterator("/terceros/tercero");
				String identificacion;
				for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
						.nextNode()) {
					identificacion = XmlFacade.get(node,
							"identificacion/text()");
					if ((identificacion != null)
							&& (identificacion.indexOf(valorAtrib) > -1))
						lista.add(getInfoTercero(node));
				}
				break;
			}

			case TipoAtributo.NOMBRE:
			case TipoAtributo.RAZON_SOCIAL: {
				NodeIterator nodeIt = xml.getNodeIterator("/terceros/tercero");
				String nombre;
				for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
						.nextNode()) {
					nombre = XmlFacade.get(node, "nombre/text()");
					if ((nombre != null) && (nombre.indexOf(valorAtrib) > -1))
						lista.add(getInfoTercero(node));
				}
				break;
			}

			case TipoAtributo.APELLIDO1: {
				NodeIterator nodeIt = xml.getNodeIterator("/terceros/tercero");
				String apellido1;
				for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
						.nextNode()) {
					apellido1 = XmlFacade.get(node, "apellido1/text()");
					if ((apellido1 != null)
							&& (apellido1.indexOf(valorAtrib) > -1))
						lista.add(getInfoTercero(node));
				}
				break;
			}

			case TipoAtributo.APELLIDO2: {
				NodeIterator nodeIt = xml.getNodeIterator("/terceros/tercero");
				String apellido2;
				for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
						.nextNode()) {
					apellido2 = XmlFacade.get(node, "apellido2/text()");
					if ((apellido2 != null)
							&& (apellido2.indexOf(valorAtrib) > -1))
						lista.add(getInfoTercero(node));
				}
				break;
			}

			default:
				throw new GestorTercerosException(
						"Tipo de atributo no v\u00E1lido (" + tipoAtrib + ")");
			}
		}

		return lista;
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

		NodeIterator nodeIt = xml.getNodeIterator("/terceros/tercero");
		String aux;
		boolean aceptable;
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			aceptable = true;

			if (StringUtils.isNotEmpty(nombre)) {
				aux = XmlFacade.get(node, "nombre/text()");
				if ((aux == null) || (aux.indexOf(nombre) < 0))
					aceptable = false;
			}

			if (aceptable && StringUtils.isNotEmpty(apellido1)) {
				aux = XmlFacade.get(node, "apellido1/text()");
				if ((aux == null) || (aux.indexOf(apellido1) < 0))
					aceptable = false;
			}

			if (aceptable && StringUtils.isNotEmpty(apellido2)) {
				aux = XmlFacade.get(node, "apellido2/text()");
				if ((aux == null) || (aux.indexOf(apellido2) < 0))
					aceptable = false;
			}

			if (aceptable)
				lista.add(getInfoTercero(node));
		}

		return lista;
	}

	/**
	 * Obtiene la información de un tercero.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Información del tercero.
	 */
	private InfoTerceroImpl getInfoTercero(Node node) {
		InfoTerceroImpl infoTercero = null;

		if (node != null) {
			infoTercero = new InfoTerceroImpl();
			infoTercero.setId(XmlFacade.get(node, "@id"));
			infoTercero.setIdentificacion(XmlFacade.get(node,
					"identificacion/text()"));
			infoTercero.setNombre(XmlFacade.get(node, "nombre/text()"));
			infoTercero.setPrimerApellido(XmlFacade.get(node,
					"apellido1/text()"));
			infoTercero.setSegundoApellido(XmlFacade.get(node,
					"apellido2/text()"));
			infoTercero.setDireccion(XmlFacade.get(node, "direccion/text()"));
			infoTercero.setEmail(XmlFacade.get(node, "email/text()"));
		}

		return infoTercero;
	}
}
