package se.geograficos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.geograficos.exceptions.GestorGeograficosException;
import xml.config.ConfiguracionArchivoManager;

import common.util.StringUtils;
import common.util.XmlFacade;

/**
 * Implementación del interfaz para la gestión de geográficos de prueba.
 */
public class DefaultGestorGeograficosImpl implements GestorGeograficos {

	/** Logger de la clase. */
	private static Logger logger = Logger
			.getLogger(DefaultGestorGeograficosImpl.class);

	/** XML con los datos. */
	private static XmlFacade xml = null;

	/**
	 * Constructor.
	 */
	public DefaultGestorGeograficosImpl() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 */
	public void initialize(Properties params) {
		try {
			if (xml == null) {
				xml = ConfiguracionArchivoManager.getInstance()
						.getDefaultSistemaGestorGeograficos();
				logger.info("Fichero XML del Sistema Gestor de Geográficos cargado");
			}
		} catch (Exception e) {
			logger.error("Error al leer el fichero de datos geográficos", e);
		}
	}

	/**
	 * Recupera la lista de países ordenados por orden alfabético.
	 * 
	 * @return Lista de países ({@link Pais}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPaises() throws GestorGeograficosException {
		return recuperarPaises(null);
	}

	/**
	 * Recupera la lista de países ordenados por orden alfabético.
	 * 
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de países ({@link Pais}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPaises(String pattern)
			throws GestorGeograficosException {
		List lista = new ArrayList();

		NodeIterator nodeIt = xml.getNodeIterator("/geograficos/paises/pais");
		Pais pais;
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			pais = new Pais(XmlFacade.get(node, "@id"), XmlFacade.get(node,
					"@name"));

			if ((pattern == null)
					|| (pais.getNombre().toUpperCase()
							.indexOf(pattern.toUpperCase()) > -1))
				lista.add(pais);
		}

		return lista;
	}

	/**
	 * Recupera el país.
	 * 
	 * @param codigo
	 *            Código del país.
	 * @return País.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Pais recuperarPais(String codigo) throws GestorGeograficosException {
		if (StringUtils.isNotBlank(codigo)) {
			List paises = recuperarPaises();
			Pais pais;
			for (int i = 0; i < paises.size(); i++) {
				pais = (Pais) paises.get(i);
				if (pais.getCodigo().equals(codigo))
					return pais;
			}
		}

		return null;
	}

	/**
	 * Recupera la lista de provincias de un país.
	 * 
	 * @param pais
	 *            País.
	 * @return Lista de provincias ({@link Provincia}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarProvincias(Pais pais)
			throws GestorGeograficosException {
		return recuperarProvincias(pais, null);
	}

	/**
	 * Recupera la lista de provincias de un país.
	 * 
	 * @param pais
	 *            País.
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de provincias ({@link Provincia}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarProvincias(Pais pais, String pattern)
			throws GestorGeograficosException {
		List lista = new ArrayList();

		NodeIterator nodeIt = xml
				.getNodeIterator("/geograficos/paises/pais[@id='"
						+ pais.getCodigo() + "']/provincias/provincia");
		Provincia provincia;
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			provincia = new Provincia(pais.getCodigo(), XmlFacade.get(node,
					"@id"), XmlFacade.get(node, "@name"));

			if ((pattern == null)
					|| (provincia.getNombre().toUpperCase()
							.indexOf(pattern.toUpperCase()) > -1))
				lista.add(provincia);
		}

		return lista;
	}

	/**
	 * Recupera la provincia.
	 * 
	 * @param pais
	 *            País.
	 * @param codigo
	 *            Código de la provincia.
	 * @return Provincia.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Provincia recuperarProvincia(Pais pais, String codigo)
			throws GestorGeograficosException {
		if ((pais != null) && StringUtils.isNotBlank(codigo)) {
			List provincias = recuperarProvincias(pais);
			Provincia provincia;
			for (int i = 0; i < provincias.size(); i++) {
				provincia = (Provincia) provincias.get(i);
				if (provincia.getCodigo().equals(codigo))
					return provincia;
			}
		}

		return null;
	}

	/**
	 * Recupera la lista de municipios de una provincia.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @return Lista de municipios ({@link Municipio}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarMunicipios(Provincia provincia)
			throws GestorGeograficosException {
		return recuperarMunicipios(provincia, null);
	}

	/**
	 * Recupera la lista de municipios de una provincia.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de municipios ({@link Municipio}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarMunicipios(Provincia provincia, String pattern)
			throws GestorGeograficosException {
		List lista = new ArrayList();

		NodeIterator nodeIt = xml
				.getNodeIterator("/geograficos/paises/pais[@id='"
						+ provincia.getCodigoPais()
						+ "']/provincias/provincia[@id='"
						+ provincia.getCodigo() + "']/municipios/municipio");
		Municipio municipio;
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			municipio = new Municipio(provincia.getCodigoPais(),
					provincia.getCodigo(), XmlFacade.get(node, "@id"),
					XmlFacade.get(node, "@name"));

			if ((pattern == null)
					|| (municipio.getNombre().toUpperCase()
							.indexOf(pattern.toUpperCase()) > -1))
				lista.add(municipio);
		}

		return lista;
	}

	/**
	 * Recupera el municipio.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @param codigo
	 *            Código del municipio.
	 * @return Municipio.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Municipio recuperarMunicipio(Provincia provincia, String codigo)
			throws GestorGeograficosException {
		if ((provincia != null) && StringUtils.isNotBlank(codigo)) {
			List municipios = recuperarMunicipios(provincia);
			Municipio municipio;
			for (int i = 0; i < municipios.size(); i++) {
				municipio = (Municipio) municipios.get(i);
				if (municipio.getCodigo().equals(codigo))
					return municipio;
			}
		}

		return null;
	}

	/**
	 * Recupera la lista de poblaciones de un municipio.
	 * 
	 * @param municipio
	 *            .
	 * @return Lista de poblaciones ({@link Poblacion}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPoblaciones(Municipio municipio)
			throws GestorGeograficosException {
		return recuperarPoblaciones(municipio, null);
	}

	/**
	 * Recupera la lista de poblaciones de un municipio.
	 * 
	 * @param municipio
	 *            .
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de poblaciones ({@link Poblacion}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPoblaciones(Municipio municipio, String pattern)
			throws GestorGeograficosException {
		List lista = new ArrayList();

		NodeIterator nodeIt = xml
				.getNodeIterator("/geograficos/paises/pais[@id='"
						+ municipio.getCodigoPais()
						+ "']/provincias/provincia[@id='"
						+ municipio.getCodigoProvincia()
						+ "']/municipios/municipio[@id='"
						+ municipio.getCodigo() + "']/localidades/localidad");
		Poblacion poblacion;
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			poblacion = new Poblacion(municipio.getCodigoPais(),
					municipio.getCodigoProvincia(), municipio.getCodigo(),
					XmlFacade.get(node, "@id"), XmlFacade.get(node, "@name"));

			if ((pattern == null)
					|| (poblacion.getNombre().toUpperCase()
							.indexOf(pattern.toUpperCase()) > -1))
				lista.add(poblacion);
		}

		return lista;
	}

	/**
	 * Recupera la población.
	 * 
	 * @param municipio
	 *            Municipio.
	 * @param codigo
	 *            Código de la población.
	 * @return Población.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Poblacion recuperarPoblacion(Municipio municipio, String codigo)
			throws GestorGeograficosException {
		if ((municipio != null) && StringUtils.isNotBlank(codigo)) {
			List poblaciones = recuperarPoblaciones(municipio);
			Poblacion poblacion;
			for (int i = 0; i < poblaciones.size(); i++) {
				poblacion = (Poblacion) poblaciones.get(i);
				if (poblacion.getCodigo().equals(codigo))
					return poblacion;
			}
		}

		return null;
	}

	/**
	 * Indica si podemos realizar una busqueda extendida en elementos
	 * geograficos para buscar tal y como estaba antes o para buscar por el
	 * pattern sin depender de lo que halla introducio anteriormente.
	 * 
	 * @return boolean
	 */
	public boolean soportaBusquedaExtendida() {
		return true;
	}

	/**
	 * Devuelve una lista de elementos geograficos con los resultados de la
	 * busqueda.
	 * 
	 * @param tipoBusqueda
	 * @param ids
	 * @param pattern
	 * @return {@link ElementoGeograficoVO}
	 * @throws GestorGeograficosException
	 */
	public List busquedaElementos(int tipoBusqueda, Map ids, String pattern)
			throws GestorGeograficosException {
		String cadenaIterador = "/geograficos";
		for (int i = 0; i < tipos.length && tipos[i] <= tipoBusqueda; i++) {
			switch (i) {
			case PAIS:
				cadenaIterador += "/paises";
				String idPais = (String) ids.get(new Integer(PAIS));
				if (StringUtils.isNotEmpty(idPais))
					cadenaIterador += "/pais[@id='" + idPais + "']";
				else
					cadenaIterador += "/pais";
				break;
			case PROVINCIA:
				cadenaIterador += "/provincias";
				String idProvincia = (String) ids.get(new Integer(PROVINCIA));
				if (StringUtils.isNotEmpty(idProvincia))
					cadenaIterador += "/provincia[@id='" + idProvincia + "']";
				else
					cadenaIterador += "/provincia";
				break;
			case MUNICIPIO:
				cadenaIterador += "/municipios";
				String idMunicipio = (String) ids.get(new Integer(MUNICIPIO));
				if (StringUtils.isNotEmpty(idMunicipio))
					cadenaIterador += "/municipio[@id='" + idMunicipio + "']";
				else
					cadenaIterador += "/municipio";
				break;
			case POBLACION:
				cadenaIterador += "/localidades";
				String idPoblacion = (String) ids.get(new Integer(POBLACION));
				if (StringUtils.isNotEmpty(idPoblacion))
					cadenaIterador += "/localidad[@id='" + idPoblacion + "']";
				else
					cadenaIterador += "/localidad";
				break;
			}
		}
		return recuperarElementos(cadenaIterador, tipoBusqueda, ids, pattern);
	}

	/**
	 * Genera el iterador sobre el xml y agrega los elementos que coincidan en
	 * la busqueda.
	 * 
	 * @param cadenaIterador
	 * @param tipoBusqueda
	 * @param ids
	 * @param pattern
	 * @return {@link ElementoGeograficoVO}
	 */
	private List recuperarElementos(String cadenaIterador, int tipoBusqueda,
			Map ids, String pattern) {
		ResultadoBusquedaGeograficos listaElementos = new ResultadoBusquedaGeograficos();
		NodeIterator nodeIt = xml.getNodeIterator(cadenaIterador);
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
				.nextNode()) {
			String name = XmlFacade.get(node, "@name");
			if ((pattern == null)
					|| (name.toUpperCase().indexOf(pattern.toUpperCase()) > -1)) {
				ElementoGeograficoVO elemento = new ElementoGeograficoVO();
				agregarElemento(tipoBusqueda, node, elemento);
				listaElementos.addElemento(elemento);
			}
		}
		return listaElementos.getElementos();
	}

	/**
	 * Agrega un nuevo elemento geografico al resultado de busqueda de
	 * geograficos de manera recursiva indicando los ids y names de cada
	 * elemento.
	 * 
	 * @param tipoBusqueda
	 * @param node
	 * @param elemento
	 */
	private void agregarElemento(int tipoBusqueda, Node node,
			ElementoGeograficoVO elemento) {
		String id = XmlFacade.get(node, "@id");
		String name = XmlFacade.get(node, "@name");
		switch (tipoBusqueda) {
		case PAIS:
			elemento.setIdPais(id);
			elemento.setNamePais(name);
			break;
		case PROVINCIA:
			elemento.setIdProvincia(id);
			elemento.setNameProvincia(name);
			agregarElemento(--tipoBusqueda, node.getParentNode()
					.getParentNode(), elemento);
			break;
		case MUNICIPIO:
			elemento.setIdMunicipio(id);
			elemento.setNameMunicipio(name);
			agregarElemento(--tipoBusqueda, node.getParentNode()
					.getParentNode(), elemento);
			break;
		case POBLACION:
			elemento.setIdPoblacion(id);
			elemento.setNamePoblacion(name);
			agregarElemento(--tipoBusqueda, node.getParentNode()
					.getParentNode(), elemento);
			break;
		}
	}
}