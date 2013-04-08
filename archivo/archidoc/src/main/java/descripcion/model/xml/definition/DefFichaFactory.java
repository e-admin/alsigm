package descripcion.model.xml.definition;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.usuarios.ServiceClient;

import common.ConfigConstants;
import common.Constants;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;

import descripcion.exceptions.DefFichaException;
import descripcion.vos.FichaVO;

/**
 * Factoría para la creación de la definición de una ficha ISAD(G).
 */
public class DefFichaFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(DefFichaFactory.class);

	/** Cliente de servicio. */
	private ServiceClient serviceClient = null;

	/**
	 * Constructor.
	 */
	private DefFichaFactory(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Obtiene una instancia de la clase.
	 * 
	 * @param serviceClient
	 *            Cliente de servicio.
	 * @return Instancia de la clase.
	 */
	public static DefFichaFactory getInstance(ServiceClient serviceClient) {
		return new DefFichaFactory(serviceClient);
	}

	/**
	 * Obtiene la definición de una ficha.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Definición de la ficha.
	 */
	public DefFicha getDefFichaById(String id) {
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		FichaVO fichaVO = descripcionBI.getFicha(id);
		if (fichaVO == null) {
			logger.error("No se ha encontrado la ficha de descripci\u00F3n con id "
					+ id);
			throw new DefFichaException(
					"No se ha encontrado la ficha de descripci\u00F3n");
		}

		return createDefFicha(fichaVO.getId(), fichaVO.getDefinicion());
	}

	/**
	 * Crea la definición de una ficha.
	 * 
	 * @param id
	 *            Identificador de la ficha
	 * @param xml
	 *            Cadena con el XML de la definición de la ficha.
	 * @return Definición de la ficha.
	 */
	public DefFicha createDefFicha(String id, String xml) {
		DefFicha definicionFicha = null;

		if (StringUtils.isNotBlank(xml)) {
			XmlFacade xmlFacade = new XmlFacade(xml);
			definicionFicha = createDefFicha(id, xmlFacade.getDocument());
		}

		return definicionFicha;
	}

	/**
	 * Crea la definición de una ficha.
	 * 
	 * @param id
	 *            Identificador de la ficha
	 * @param doc
	 *            Documento XML de la definición de la ficha.
	 * @return Definición de la ficha.
	 */
	public DefFicha createDefFicha(String id, Document doc) {
		DefFicha definicionFicha = null;

		if (doc != null) {
			definicionFicha = new DefFicha(ConfigConstants.getInstance()
					.getSeparadorDefectoFechasRelacion());

			// Id
			definicionFicha.setId(id);

			// Versión
			definicionFicha.setVersion(XmlFacade.get(doc, "/"
					+ DefTags.TAG_DEFINITION + "/@" + DefTags.ATTR_VERSION));

			// Áreas
			NodeIterator nodeAreasIt = XmlFacade.getNodeIterator(doc, "/"
					+ DefTags.TAG_DEFINITION + "/"
					+ DefTags.TAG_INFORMACION_CAMPOS + "/" + DefTags.TAG_AREA);
			DefArea area = null;
			for (Node nodeArea = nodeAreasIt.nextNode(); nodeArea != null; nodeArea = nodeAreasIt
					.nextNode()) {
				area = new DefArea();
				area.setIdAreaAsociada(XmlFacade.get(nodeArea,
						DefTags.TAG_ID_AREA_ASOCIADA + "/text()"));

				// Campos del área
				NodeIterator nodeCamposIt = XmlFacade.getNodeIterator(nodeArea,
						DefTags.TAG_CAMPOS + "/" + DefTags.TAG_CAMPO);
				for (Node nodeCampo = nodeCamposIt.nextNode(); nodeCampo != null; nodeCampo = nodeCamposIt
						.nextNode())
					area.addCampo(createCampo(nodeCampo));

				definicionFicha.addArea(area);
			}

			// Clase
			definicionFicha.setClaseGenerarAutomaticos(XmlFacade.get(doc, "/"
					+ DefTags.TAG_DEFINITION + "/"
					+ DefTags.TAG_CLASE_GENERAR_AUTOMATICOS + "/text()"));

			// Eventos
			NodeIterator nodeEventosIt = XmlFacade.getNodeIterator(doc, "/"
					+ DefTags.TAG_DEFINITION + "/" + DefTags.TAG_EVENTOS + "/"
					+ DefTags.TAG_EVENTO);
			for (Node node = nodeEventosIt.nextNode(); node != null; node = nodeEventosIt
					.nextNode())
				definicionFicha.addEvento(new DefEvento(XmlFacade.get(node,
						DefTags.TAG_TIPO_EVENTO + "/text()"), XmlFacade.get(
						node, DefTags.TAG_CLASE_EVENTO + "/text()")));
		}

		return definicionFicha;
	}

	/**
	 * Crea un campo con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return DefCampo.
	 */
	protected DefCampo createCampo(Node node) {
		DefCampo campo = null;

		if (node != null) {
			String tipo = XmlFacade.get(node, "@" + DefTags.ATTR_TIPO_CAMPO);
			if (DefTipos.getNombreTipoCampo(DefTipos.TIPO_CAMPO_DATO).equals(
					tipo))
				campo = createCampoDato(node);
			else if (DefTipos.getNombreTipoCampo(DefTipos.TIPO_CAMPO_TABLA)
					.equals(tipo))
				campo = createCampoTabla(node);
			else if (DefTipos.getNombreTipoCampo(DefTipos.TIPO_CAMPO_ESPECIAL)
					.equals(tipo))
				campo = createCampoEspecial(node);
		}

		return campo;
	}

	/**
	 * Crea un campo de tipo dato con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Campo de tipo dato.
	 */
	protected DefCampoDato createCampoDato(Node node) {
		DefCampoDato campo = null;

		if (node != null) {
			campo = new DefCampoDato();

			campo.setId(XmlFacade.get(node, DefTags.TAG_ID_CAMPO + "/text()"));
			campo.setDescripcion(XmlFacade.get(node,
					DefTags.TAG_DESCRIPCION_CAMPO + "/text()"));
			campo.setTipoDato(TypeConverter.toShort(XmlFacade.get(node,
					DefTags.TAG_TIPO_CAMPO_DATO + "/text()")));
			campo.setMultivalor(Constants.TRUE_STRING.equals(XmlFacade.get(
					node, DefTags.TAG_MULTIVALOR_CAMPO_DATO + "/text()")));
			campo.setEditable(Constants.TRUE_STRING.equals(XmlFacade.get(node,
					DefTags.TAG_EDITABLE_CAMPO_DATO + "/text()")));
			campo.setObligatorio(Constants.TRUE_STRING.equals(XmlFacade.get(
					node, DefTags.TAG_OBLIGATORIO_CAMPO_DATO + "/text()")));

			// Valor inicial
			Node valorInicialNode = XmlFacade.getNode(node,
					DefTags.TAG_VALOR_INICIAL_CAMPO_DATO);
			if (valorInicialNode != null) {
				DefValorInicial valorInicial = new DefValorInicial();
				valorInicial
						.setValor(XmlFacade.get(valorInicialNode, "text()"));
				valorInicial.setIdRef(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_IDREF));
				valorInicial.setTipoRef(TypeConverter.toInt(
						XmlFacade.get(valorInicialNode, "@"
								+ DefTags.ATTR_TIPOREF), 0));
				valorInicial.setAnio(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_YEAR));
				valorInicial.setMes(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_MONTH));
				valorInicial.setDia(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_DAY));
				valorInicial.setSiglo(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_CENTURY));
				valorInicial.setFormato(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_FORMAT));
				valorInicial.setSeparador(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_SEPARATOR));
				valorInicial.setCalificador(XmlFacade.get(valorInicialNode, "@"
						+ DefTags.ATTR_QUALIFIER));
				valorInicial.setTipoMedida(TypeConverter.toInt(
						XmlFacade.get(valorInicialNode, "@"
								+ DefTags.ATTR_MEASURE_TYPE), 0));
				valorInicial.setUnidadMedida(XmlFacade.get(valorInicialNode,
						"@" + DefTags.ATTR_MEASURE_UNIT));

				campo.setValorInicial(valorInicial);
			}

			DefInformacionEspecifica infoEspec = ((DefCampoDato) campo)
					.getInformacionEspecifica();
			switch (campo.getTipoDato()) {
			case DefTipos.TIPO_DATO_TEXTO_CORTO:
				infoEspec.setIdTblVld(XmlFacade.get(node,
						DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO + "/"
								+ DefTags.TAG_VALIDACION_INFO_ESPEC + "/"
								+ DefTags.TAG_IDTBLVLD_INFO_ESPEC + "/text()"));
				break;

			case DefTipos.TIPO_DATO_FECHA:
				NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
						DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO + "/"
								+ DefTags.TAG_VALIDACION_INFO_ESPEC + "/"
								+ DefTags.TAG_FORMATOS_INFO_ESPEC + "/"
								+ DefTags.TAG_FORMATO);
				for (Node nodeFormato = nodeIt.nextNode(); nodeFormato != null; nodeFormato = nodeIt
						.nextNode())
					infoEspec.addFormato(new DefFormatoFecha(XmlFacade.get(
							nodeFormato, "@" + DefTags.ATTR_TIPO_FORMATO),
							XmlFacade.get(nodeFormato, "@"
									+ DefTags.ATTR_SEP_FORMATO)));
				break;

			case DefTipos.TIPO_DATO_NUMERICO:
				infoEspec.setTipoNumerico(TypeConverter.toShort(XmlFacade.get(
						node, DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
								+ "/" + DefTags.TAG_TIPO_NUMERICO_INFO_ESPEC
								+ "/text()"),
						DefTipos.TIPO_NUMERICO_DESCONOCIDO));
				infoEspec
						.setRangoMinimo(TypeConverter.toDoubleObject(XmlFacade
								.get(node,
										DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
												+ "/"
												+ DefTags.TAG_VALIDACION_INFO_ESPEC
												+ "/"
												+ DefTags.TAG_RANGO_INFO_ESPEC
												+ "/@"
												+ DefTags.ATTR_RANGO_MINIMO_INFO_ESPEC)));
				infoEspec
						.setRangoMaximo(TypeConverter.toDoubleObject(XmlFacade
								.get(node,
										DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
												+ "/"
												+ DefTags.TAG_VALIDACION_INFO_ESPEC
												+ "/"
												+ DefTags.TAG_RANGO_INFO_ESPEC
												+ "/@"
												+ DefTags.ATTR_RANGO_MAXIMO_INFO_ESPEC)));
				infoEspec
						.setTipoMedida(TypeConverter.toShort(XmlFacade.get(
								node,
								DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
										+ "/"
										+ DefTags.TAG_VALIDACION_INFO_ESPEC
										+ "/" + DefTags.TAG_MEDIDA_INFO_ESPEC
										+ "/"
										+ DefTags.TAG_TIPO_MEDIDA_INFO_ESPEC
										+ "/text()"),
								DefTipos.TIPO_MEDIDA_DESCONOCIDO));
				infoEspec.setIdTblVld(XmlFacade.get(node,
						DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO + "/"
								+ DefTags.TAG_VALIDACION_INFO_ESPEC + "/"
								+ DefTags.TAG_MEDIDA_INFO_ESPEC + "/"
								+ DefTags.TAG_UNIDAD_MEDIDA_INFO_ESPEC + "/"
								+ DefTags.TAG_IDTBLVLD_INFO_ESPEC + "/text()"));
				break;

			case DefTipos.TIPO_DATO_REFERENCIA:
				infoEspec
						.setTipoReferencia(TypeConverter.toShort(
								XmlFacade
										.get(node,
												DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
														+ "/"
														+ DefTags.TAG_TIPO_REFERENCIA_INFO_ESPEC
														+ "/text()"),
								DefTipos.TIPO_REFERENCIA_DESCONOCIDO));
				infoEspec.setIdsListasDescriptoras(XmlFacade.get(node,
						DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO + "/"
								+ DefTags.TAG_VALIDACION_INFO_ESPEC + "/"
								+ DefTags.TAG_IDS_LISTAS_DESC_INFO_ESPEC
								+ "/text()"));
				break;
			}
		}

		return campo;
	}

	/**
	 * Crea un campo de tipo tabla con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Campo de tipo tabla.
	 */
	protected DefCampoTabla createCampoTabla(Node node) {
		DefCampoTabla campo = null;

		if (node != null) {
			campo = new DefCampoTabla();

			campo.setId(XmlFacade.get(node, DefTags.TAG_ID_CAMPO + "/text()"));
			campo.setEditable(Constants.TRUE_STRING.equals(XmlFacade.get(node,
					DefTags.TAG_EDITABLE_CAMPO_DATO + "/text()")));
			campo.setDescripcion(XmlFacade.get(node,
					DefTags.TAG_DESCRIPCION_CAMPO + "/text()"));
			campo.setSistemaExterno(XmlFacade.get(node,
					DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO + "/"
							+ DefTags.TAG_VALIDACION_INFO_ESPEC + "/"
							+ DefTags.TAG_SISTEMA_EXTERNO + "/text()"));

			NodeIterator nodeIt = XmlFacade.getNodeIterator(node,
					DefTags.TAG_COLUMNAS_CAMPO_TABLA + "/" + DefTags.TAG_CAMPO);
			for (Node nodeCampo = nodeIt.nextNode(); nodeCampo != null; nodeCampo = nodeIt
					.nextNode())
				campo.addCampo(createCampoDato(nodeCampo));
		}

		return campo;
	}

	/**
	 * Crea un campo de tipo especial con la información del nodo XML.
	 * 
	 * @param node
	 *            Nodo XML.
	 * @return Campo de tipo especial.
	 */
	protected DefCampoEspecial createCampoEspecial(Node node)
			throws NumberFormatException {
		DefCampoEspecial campo = null;

		if (node != null) {
			campo = new DefCampoEspecial();

			campo.setId(XmlFacade.get(node, DefTags.TAG_ID_CAMPO + "/text()"));
			campo.setDescripcion(XmlFacade.get(node,
					DefTags.TAG_DESCRIPCION_CAMPO + "/text()"));

			String editableValue = XmlFacade.get(node,
					DefTags.TAG_EDITABLE_CAMPO_DATO + "/text()");
			if (!StringUtils.isEmpty(editableValue))
				campo.setEditable(Constants.TRUE_STRING.equals(editableValue));

			editableValue = XmlFacade.get(node, DefTags.ATTR_TIPO_CAMPO
					+ "/text()");

			if (!StringUtils.isEmpty(editableValue)
					&& NumberUtils.isNumber(editableValue))
				campo.setTipoDato(Short.parseShort(XmlFacade.get(node,
						DefTags.ATTR_TIPO_CAMPO + "/text()")));

			String obligatorioValue = XmlFacade.get(node,
					DefTags.TAG_OBLIGATORIO_CAMPO_DATO + "/text()");

			if (!StringUtils.isEmpty(obligatorioValue)
					&& obligatorioValue.equals(Constants.TRUE_STRING)) {
				campo.setObligatorio(true);
			} else {
				campo.setObligatorio(false);
			}

		}

		return campo;
	}
}
