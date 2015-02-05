package descripcion.model.xml.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.usuarios.ServiceClient;

import common.Constants;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.security.SecurityManagerBase;
import common.util.XmlFacade;

import descripcion.exceptions.DefFmtFichaException;
import descripcion.vos.FmtFichaVO;
import descripcion.vos.FmtPrefFichaVO;

/**
 * Factoría para la creación de la definición de formato de una ficha ISAD(G).
 */
public class DefFmtFichaFactory {

	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(DefFmtFichaFactory.class);

	/** Cliente de servicio. */
	private ServiceClient serviceClient = null;

	/**
	 * Constructor.
	 */
	private DefFmtFichaFactory(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Obtiene una instancia de la clase.
	 * 
	 * @param serviceClient
	 *            Cliente de servicio.
	 * @return Instancia de la clase.
	 */
	public static DefFmtFichaFactory getInstance(ServiceClient serviceClient) {
		return new DefFmtFichaFactory(serviceClient);
	}

	/**
	 * Obtiene la definición del formato de ficha del usuario.
	 * 
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @param tipoAcceso
	 *            Tipo de formato ({@link gcontrol.model.TipoAcceso}).
	 * @return Definición del formato de la ficha.
	 */
	public DefFmtFicha getDefFmtFicha(String idFicha, int tipoAcceso) {
		// Obtener el formato de ficha preferente
		FmtPrefFichaVO fmtPrefFicha = getFmtPrefFicha(idFicha, tipoAcceso);
		if (logger.isDebugEnabled())
			logger.debug("FmtPrefFichaVO:\n" + fmtPrefFicha);

		// Comprobar la validez del formato preferente
		if ((fmtPrefFicha == null)
				|| !SecurityManagerBase.isAccessAllowed(serviceClient,
						fmtPrefFicha.getTipoFmt(),
						fmtPrefFicha.getNivelAcceso(), null,
						fmtPrefFicha.getIdlca())) {
			// Seleccionar un nuevo formato preferente
			fmtPrefFicha = findNewFmtPrefFicha(idFicha, tipoAcceso,
					fmtPrefFicha != null);

			if (logger.isDebugEnabled())
				logger.debug("Nuevo FmtPrefFichaVO:\n" + fmtPrefFicha);
		}

		// Obtener el formato de ficha
		FmtFichaVO fmtFicha = getFmtFicha(fmtPrefFicha.getIdFmt());
		if (fmtFicha == null)
			throw new DefFmtFichaException(
					"No se ha encontrado el formato de la ficha");

		return createDefFmtFicha(fmtFicha.getId(), tipoAcceso,
				fmtFicha.getDefinicion());
	}

	/**
	 * Obtiene la información del formato de ficha preferente del usuario.
	 * 
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @param tipoFormato
	 *            Tipo de formato ({@link gcontrol.model.TipoAcceso}).
	 * @return Formato de ficha preferente.
	 */
	protected FmtPrefFichaVO getFmtPrefFicha(String idFicha, int tipoFormato) {
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		return descripcionBI.getFmtPrefFicha(idFicha, serviceClient.getId(),
				tipoFormato);
	}

	/**
	 * Obtiene el formato de ficha.
	 * 
	 * @param id
	 *            Identificador del formato de ficha
	 * @return Formato de ficha.
	 */
	protected FmtFichaVO getFmtFicha(String id) {
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		return descripcionBI.getFmtFicha(id);
	}

	/**
	 * Busca un nuevo formato de ficha preferente.
	 * 
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @param tipoFormato
	 *            Tipo de formato ({@link gcontrol.model.TipoAcceso}).
	 * @param oldOneExists
	 *            Indica si existe un formato preferente inválido.
	 * @return Nuevo formato de ficha preferente.
	 */
	protected FmtPrefFichaVO findNewFmtPrefFicha(String idFicha,
			int tipoFormato, boolean oldOneExists) {
		FmtPrefFichaVO newFmtPrefFicha = null;

		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		List fmtFichas = descripcionBI.getFmtFichas(idFicha, tipoFormato);

		if (fmtFichas != null) {
			for (int i = 0; (newFmtPrefFicha == null) && (i < fmtFichas.size()); i++) {
				FmtFichaVO fmtFicha = (FmtFichaVO) fmtFichas.get(i);
				if (SecurityManagerBase.isAccessAllowed(serviceClient,
						fmtFicha.getTipo(), fmtFicha.getNivelAcceso())) {
					newFmtPrefFicha = new FmtPrefFichaVO();
					newFmtPrefFicha.setIdFicha(fmtFicha.getIdFicha());
					newFmtPrefFicha.setIdUsuario(serviceClient.getId());
					newFmtPrefFicha.setTipoFmt(fmtFicha.getTipo());
					newFmtPrefFicha.setIdFmt(fmtFicha.getId());
				}
			}

			if (newFmtPrefFicha != null) {
				if (oldOneExists)
					descripcionBI.updateFmtPrefFicha(newFmtPrefFicha);
				else
					descripcionBI.insertFmtPrefFicha(newFmtPrefFicha);
			} else
				throw new DefFmtFichaException(
						"El usuario no tiene permisos suficientes para conseguir un formato de ficha preferente");
		}

		return newFmtPrefFicha;
	}

	/**
	 * Busca los posibles formatos para una ficha y tipo de formato para el
	 * usuario conectado.
	 * 
	 * @param idFicha
	 *            Identificador de la ficha.
	 * @param tipoFormato
	 *            Tipo de formato ({@link gcontrol.model.TipoAcceso}).
	 * @return Lista de formatos accesibles por el usuario.
	 */
	public List findFmtFichaAccesibles(String idFicha, int tipoFormato) {
		FmtPrefFichaVO newFmtPrefFicha = null;

		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		// Obtener los posibles formatos de fichas
		List fmtFichas = descripcionBI.getFmtFichas(idFicha, tipoFormato);
		List fmtFichasAccesibles = new ArrayList();

		if (fmtFichas != null) {
			// Comprobar que el usuario tenga acceso a esos formatos de fichas
			for (int i = 0; (newFmtPrefFicha == null) && (i < fmtFichas.size()); i++) {
				FmtFichaVO fmtFicha = (FmtFichaVO) fmtFichas.get(i);
				if (SecurityManagerBase.isAccessAllowed(serviceClient,
						fmtFicha.getTipo(), fmtFicha.getNivelAcceso())) {
					fmtFichasAccesibles.add(fmtFicha);
				}
			}
		}

		return fmtFichasAccesibles;
	}

	/**
	 * Crea la definición del formato de una ficha.
	 * 
	 * @param id
	 *            identificador del formato
	 * @param tipoAcceso
	 *            tipo de acceso a la ficha ({@link gcontrol.model.TipoAcceso}).
	 * @param xml
	 *            Cadena con el XML de la definición del formato de la ficha.
	 * @return Definición del formato de la ficha.
	 */
	public DefFmtFicha createDefFmtFicha(String id, int tipoAcceso, String xml) {
		DefFmtFicha definicionFmtFicha = null;

		if (xml != null) {
			XmlFacade xmlFacade = new XmlFacade(xml);
			definicionFmtFicha = createDefFmtFicha(id, tipoAcceso,
					xmlFacade.getDocument());
		}

		return definicionFmtFicha;
	}

	/**
	 * Crea la definición del formato de una ficha.
	 * 
	 * @param id
	 *            Identificador del formato de la ficha
	 * @param tipoAcceso
	 *            Tipo de acceso a la ficha ({@link gcontrol.model.TipoAcceso}).
	 * @param doc
	 *            Documento XML de la definición del formato de la ficha.
	 * @return Definición del formato de la ficha.
	 */
	public DefFmtFicha createDefFmtFicha(String id, int tipoAcceso, Document doc) {
		DefFmtFicha definicionFmtFicha = null;

		if (doc != null) {
			definicionFmtFicha = new DefFmtFicha();

			// Identificador del formato
			definicionFmtFicha.setId(id);

			// Tipo de acceso
			definicionFmtFicha.setTipoAcceso(tipoAcceso);

			// Versión
			definicionFmtFicha.setVersion(XmlFacade.get(doc, "/"
					+ DefFmtTags.TAG_DEFINITION + "/@"
					+ DefFmtTags.ATTR_VERSION));

			// Editable
			definicionFmtFicha.setEditable(Constants.TRUE_STRING
					.equals(XmlFacade.get(doc, "/" + DefFmtTags.TAG_DEFINITION
							+ "/" + DefFmtTags.TAG_EDITABLE + "/text()")));

			// Automaticos
			definicionFmtFicha.setAutomaticos(Constants.TRUE_STRING
					.equals(XmlFacade.get(doc, "/" + DefFmtTags.TAG_DEFINITION
							+ "/" + DefFmtTags.TAG_AUTOMATICOS + "/text()")));

			// Elementos de tipo sección
			NodeIterator nodeIt = XmlFacade.getNodeIterator(doc, "/"
					+ DefFmtTags.TAG_DEFINITION + "/"
					+ DefFmtTags.TAG_ELEMENTOS + "/" + DefFmtTags.TAG_ELEMENTO);
			for (Node node = nodeIt.nextNode(); node != null; node = nodeIt
					.nextNode())
				definicionFmtFicha.addElemento(createElementoSeccion(node));
		}

		return definicionFmtFicha;
	}

	/**
	 * Crea un elemento con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento.
	 */
	protected DefFmtElemento createElemento(Node node) {
		DefFmtElemento elemento = null;

		if (node != null) {
			String tipo = XmlFacade.get(node, "@"
					+ DefFmtTags.ATTR_TIPO_ELEMENTO);
			if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_SECCION).equals(tipo))
				elemento = createElementoSeccion(node);
			else if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO).equals(
					tipo))
				elemento = createElementoEtiquetaDato(node);
			else if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_TABLA).equals(tipo))
				elemento = createElementoTabla(node);
			else if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL).equals(
					tipo))
				elemento = createElementoTablaTextual(node);
			else if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_HIPERVINCULO)
					.equals(tipo))
				elemento = createElementoHipervinculo(node);
			else if (DefFmtTiposElemento.getNombreTipoElemento(
					DefFmtTiposElemento.TIPO_ELEMENTO_CABECERA).equals(tipo))
				elemento = createElementoCabecera(node);

			String scroll = XmlFacade.get(node, "@"
					+ DefFmtTags.ATTR_SCROLL_ELEMENTO);
			if (scroll != null)
				elemento.setScroll(Constants.TRUE_STRING);
			else
				elemento.setScroll(Constants.FALSE_STRING);
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo sección con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo sección.
	 */
	protected DefFmtElementoSeccion createElementoSeccion(Node node) {
		DefFmtElementoSeccion elemento = new DefFmtElementoSeccion();

		if (node != null) {
			// Desplegada
			elemento.setDesplegada(XmlFacade.get(node,
					DefFmtTags.TAG_DESPLEGADA + "/text()"));

			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Elementos
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
					DefFmtTags.TAG_ELEMENTOS_SECCION + "/"
							+ DefFmtTags.TAG_ELEMENTO);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento));
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo etiqueta-dato con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo etiqueta-dato.
	 */
	protected DefFmtElementoEtiquetaDato createElementoEtiquetaDato(Node node) {
		DefFmtElementoEtiquetaDato elemento = new DefFmtElementoEtiquetaDato();

		if (node != null) {
			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Campo
			elemento.setCampo(createCampo(XmlFacade.getNode(node,
					DefFmtTags.TAG_CAMPO)));
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo tabla con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo tabla.
	 */
	protected DefFmtElementoTabla createElementoTabla(Node node) {
		DefFmtElementoTabla elemento = new DefFmtElementoTabla();

		if (node != null) {
			// Id_CampoTbl
			elemento.setIdCampoTbl(XmlFacade.get(node,
					DefFmtTags.TAG_ID_CAMPO_TBL + "/text()"));

			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Mostrar_Cabeceras
			elemento.setMostrarCabeceras(!Constants.FALSE_STRING
					.equals(XmlFacade.get(node,
							DefFmtTags.TAG_MOSTRAR_CABECERAS + "/text()")));

			// Elementos
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
					DefFmtTags.TAG_ELEMENTOS_TABLA + "/"
							+ DefFmtTags.TAG_ELEMENTO);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento));
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo tabla textual con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo tabla textual.
	 */
	protected DefFmtElementoTablaTextual createElementoTablaTextual(Node node) {
		DefFmtElementoTablaTextual elemento = new DefFmtElementoTablaTextual();

		if (node != null) {
			// Id_CampoTbl
			elemento.setIdCampoTbl(XmlFacade.get(node,
					DefFmtTags.TAG_ID_CAMPO_TBL + "/text()"));

			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Mostrar_Cabeceras
			elemento.setMostrarCabeceras(!Constants.FALSE_STRING
					.equals(XmlFacade.get(node,
							DefFmtTags.TAG_MOSTRAR_CABECERAS + "/text()")));

			// Elementos
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
					DefFmtTags.TAG_ELEMENTOS_TABLA + "/"
							+ DefFmtTags.TAG_ELEMENTO);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento));
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo hipervínculo con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo hipervínculo.
	 */
	protected DefFmtElementoHipervinculo createElementoHipervinculo(Node node) {
		DefFmtElementoHipervinculo elemento = new DefFmtElementoHipervinculo();

		if (node != null) {
			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Vínculo
			elemento.setVinculo(createVinculo(XmlFacade.getNode(node,
					DefFmtTags.TAG_VINCULO_ELEMENTO)));
		}

		return elemento;
	}

	/**
	 * Crea un elemento de tipo cabecera con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Elemento de tipo cabecera.
	 */
	protected DefFmtElementoCabecera createElementoCabecera(Node node) {
		DefFmtElementoCabecera elemento = new DefFmtElementoCabecera();

		if (node != null) {
			// Etiqueta
			elemento.setEtiqueta(createEtiqueta(XmlFacade.getNode(node,
					DefFmtTags.TAG_ETIQUETA)));

			// Elementos
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
					DefFmtTags.TAG_ELEMENTOS_CABECERA + "/"
							+ DefFmtTags.TAG_ELEMENTO);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento));
		}

		return elemento;
	}

	/**
	 * Crea una etiqueta de elemento con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Etiqueta del elemento.
	 */
	protected DefFmtEtiqueta createEtiqueta(Node node) {
		DefFmtEtiqueta etiqueta = new DefFmtEtiqueta();

		if (node != null) {
			// Título
			etiqueta.getTitulo().setPredeterminado(
					XmlFacade.get(node, DefFmtTags.TAG_TITULO + "/@"
							+ DefFmtTags.ATTR_TITULO_PREDETERMINADO));

			// Estilo
			etiqueta.setEstilo(XmlFacade.get(node, DefFmtTags.TAG_ESTILO
					+ "/text()"));
		}

		return etiqueta;
	}

	/**
	 * Crea un vínculo de elemento con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Vínculo del elemento.
	 */
	protected DefFmtVinculo createVinculo(Node node) {
		DefFmtVinculo vinculo = new DefFmtVinculo();

		if (node != null) {
			// Título
			vinculo.getTitulo().setPredeterminado(
					XmlFacade.get(node, DefFmtTags.TAG_TITULO + "/@"
							+ DefFmtTags.ATTR_TITULO_PREDETERMINADO));

			// Estilo
			vinculo.setEstilo(XmlFacade.get(node, DefFmtTags.TAG_ESTILO
					+ "/text()"));

			// URL
			vinculo.setUrl(XmlFacade.get(node, DefFmtTags.TAG_URL_ELEMENTO
					+ "/text()"));

			// Target
			vinculo.setTarget(XmlFacade.get(node,
					DefFmtTags.TAG_TARGET_ELEMENTO + "/text()"));

			// Parametro
			vinculo.setParameter(XmlFacade.get(node, DefFmtTags.TAG_PARAMETRO
					+ "/text()"));

			// Valor Parametro
			vinculo.setIdCampo(XmlFacade.get(node,
					DefFmtTags.TAG_VALOR_PARAMETRO + "/text()"));
		}

		return vinculo;
	}

	/**
	 * Crea un campo con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Campo.
	 */
	protected DefFmtCampo createCampo(Node node) {
		DefFmtCampo campo = new DefFmtCampo();

		if (node != null) {
			// Id
			campo.setId(XmlFacade.get(node, "@" + DefFmtTags.ATTR_ID_CAMPO));

			// ValorSeleccionPorDefecto
			campo.setValorSeleccionPorDefecto(XmlFacade.get(node,
					DefFmtTags.TAG_VALOR_SELECCION_POR_DEFECTO + "/text()"));

			// MostrarTipoNumero
			campo.setMostrarTipoNumero(!Constants.FALSE_STRING.equals(XmlFacade
					.get(node, DefFmtTags.TAG_MOSTRAR_TIPO_NUMERO + "/text()")));

			// MostrarUnidadNumero
			campo.setMostrarUnidadNumero(!Constants.FALSE_STRING
					.equals(XmlFacade.get(node,
							DefFmtTags.TAG_MOSTRAR_UNIDAD_NUMERO + "/text()")));

			// Estilo
			campo.setEstilo(XmlFacade.get(node, DefFmtTags.TAG_ESTILO_CAMPO
					+ "/text()"));

			// Multilinea
			campo.setMultilinea(Constants.TRUE_STRING.equals(XmlFacade.get(
					node, DefFmtTags.TAG_MULTILINEA + "/text()")));
		}

		return campo;
	}

}
