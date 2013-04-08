package descripcion.actions;

import fondos.FondosConstants;
import gcontrol.ControlAccesoConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import transferencias.vos.MapDescUDocVO;
import util.ErrorsTag;
import util.StringOwnTokenizer;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoTables;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.FileHelper;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.XmlFacade;

import descripcion.DescripcionConstants;
import descripcion.ErrorKeys;
import descripcion.TipoCampoDatoConstants;
import descripcion.TipoObjetoUsado;
import descripcion.TipoObjetoUsuario;
import descripcion.exceptions.CampoNotFoundException;
import descripcion.forms.FichasForm;
import descripcion.model.TipoNorma;
import descripcion.model.xml.definition.DefTags;
import descripcion.model.xml.format.DefFmtCampo;
import descripcion.model.xml.format.DefFmtElemento;
import descripcion.model.xml.format.DefFmtElementoEtiquetaDato;
import descripcion.model.xml.format.DefFmtElementoSeccion;
import descripcion.model.xml.format.DefFmtElementoTablaTextual;
import descripcion.model.xml.format.DefFmtEtiqueta;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.vos.AreaVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.FichaVO;
import descripcion.vos.FmtFichaVO;
import descripcion.vos.UsoObjetoVO;

public class GestionFichaAction extends BaseAction {
	private static final String LABEL_FICHA = "archigest.archivo.cf.ficha";
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Crea un campo con el id pasado por parámetro.
	 *
	 * @param String
	 *            id.
	 * @return DefFmtCampo.
	 */
	protected DefFmtCampo createCampo(String id) {
		DefFmtCampo campo = new DefFmtCampo();
		campo.setId(id);
		return campo;
	}

	/**
	 * Crea un campo con el id pasado por parámetro, y le añade al campo un
	 * valor seleccionado por defecto.
	 *
	 * @param String
	 *            id.
	 * @param String
	 *            valorSeleccionPorDefecto
	 * @return DefFmtCampo.
	 */
	protected DefFmtCampo createCampo(String id, String valorSeleccionPorDefecto) {
		DefFmtCampo campo = new DefFmtCampo();
		campo.setId(id);
		campo.setValorSeleccionPorDefecto(valorSeleccionPorDefecto);
		return campo;
	}

	/**
	 * Crea un campo con el id pasado por parámetro, y le añade la información
	 * pasada por parámetro.
	 *
	 * @param String
	 *            id.
	 * @param String
	 *            valorSeleccionPorDefecto
	 * @param boolean mostrarTipoNumero
	 * @param boolean mostrarUnidadNumero
	 * @param String
	 *            estilo
	 * @return DefFmtCampo.
	 */
	protected DefFmtCampo createCampo(String id,
			String valorSeleccionPorDefecto, boolean mostrarTipoNumero,
			boolean mostrarUnidadNumero, String estilo) {
		DefFmtCampo campo = new DefFmtCampo();
		campo.setId(id);
		campo.setValorSeleccionPorDefecto(valorSeleccionPorDefecto);
		campo.setMostrarTipoNumero(mostrarTipoNumero);
		campo.setMostrarUnidadNumero(mostrarUnidadNumero);
		campo.setEstilo(estilo);
		return campo;
	}

	/**
	 * Crea un elemento de tipo etiqueta-dato pasandole un id y el valor
	 * predeterminado.
	 *
	 * @param String
	 *            valorPredeterminado.
	 * @param String
	 *            id
	 * @return DefFmtElementoEtiquetaDato Elemento de tipo etiqueta-dato.
	 */
	protected DefFmtElementoEtiquetaDato createElementoEtiquetaDato(
			String valorPredeterminado, String id, String mostrarScroll) {
		DefFmtElementoEtiquetaDato elemento = null;
		if (mostrarScroll != null)
			elemento = new DefFmtElementoEtiquetaDato(mostrarScroll);
		else
			elemento = new DefFmtElementoEtiquetaDato();

		// Etiqueta
		elemento.setEtiqueta(createEtiqueta(valorPredeterminado));

		// Campo
		elemento.setCampo(createCampo(id));

		return elemento;
	}

	/**
	 * Obtiene la descripción del id correspondiente a un campo de tipo especial
	 *
	 * @param int id del campo del tipo especial
	 * @return Descripción del campo de tipo especial
	 */
	protected String getValorPredeterminadoTipoEspecial(int id) {
		if (id == -1)
			return Constants.CODIGO_DE_REFERENCIA;
		if (id == -2)
			return Constants.NUMERO_DE_EXPEDIENTE;
		if (id == -3)
			return Constants.TITULO;
		if (id == -4)
			return Constants.NIVEL_DE_DESCRIPCION;
		return null;

	}

	/**
	 * Crea un elemento de tipo tabla con la información del nodo XML.
	 *
	 * @param node
	 *            Nodo XML.
	 * @param HttpServletRequest
	 *            request
	 * @return DefFmtElementoTablaTextual Elemento de tipo tabla.
	 */
	protected DefFmtElementoTablaTextual createElementoTablaTextual(Node node,
			HttpServletRequest request) throws CampoNotFoundException {
		DefFmtElementoTablaTextual elemento = new DefFmtElementoTablaTextual();

		if (node != null) {
			// Id_CampoTbl
			Node idNode = XmlFacade.getNode(node, DefTags.TAG_ID_CAMPO);
			String id = idNode.getFirstChild().getNodeValue();

			elemento.setIdCampoTbl(id);

			// Etiqueta
			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			CampoTablaVO campoTablaVO = descripcionService.getCampoTabla(id);

			if (campoTablaVO == null) {
				throw new CampoNotFoundException(id);
			}

			elemento.setEtiqueta(createEtiqueta(campoTablaVO.getNombre()));

			// Mostrar_Cabeceras
			elemento.setMostrarCabeceras(false);

			// Elementos

			String path = DefTags.TAG_COLUMNAS_CAMPO_TABLA + Constants.SLASH
					+ DefTags.TAG_CAMPO;
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node, path);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento, request));
		}

		return elemento;
	}

	/**
	 * Crea en la request una lista llamada listaUsoObjeto, para almacenar
	 * objetos de tipo UsoObjetoVO
	 *
	 * @param HttpServletRequest
	 *            request
	 * @return List lista que contiene objetos de tipo UsoObjetoVO
	 */
	private List createListaUsoObjeto(HttpServletRequest request) {
		List listaUsoObjeto = new ArrayList();
		request.setAttribute(Constants.LISTA_USO_OBJETO, listaUsoObjeto);
		return listaUsoObjeto;
	}

	/**
	 * Obtiene de la request una lista llamada listaUsoObjeto, que almacena
	 * objetos de tipo UsoObjetoVO. Si no existe, la crea.
	 *
	 * @param HttpServletRequest
	 *            request
	 * @return List lista que contiene objetos de tipo UsoObjetoVO
	 */
	private List getListaUsoObjeto(HttpServletRequest request) {
		List listaUsoObjeto = (List) request
				.getAttribute(Constants.LISTA_USO_OBJETO);
		if (listaUsoObjeto == null) {
			listaUsoObjeto = createListaUsoObjeto(request);
		}
		return listaUsoObjeto;
	}

	/**
	 * Añade a listaUsoObjeto de la request un usoObjetoVO perteneciente a un
	 * campo area que tiene como identificador, el id pasado por parámetro
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            id del objeto usado
	 */
	private void addUsoObjetoCampoArea(HttpServletRequest request, String id) {
		List listaUsoObjeto = getListaUsoObjeto(request);

		UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
		usoObjetoVO.setIdObj(id);
		usoObjetoVO.setTipoObj(TipoObjetoUsado.AREA);
		usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.FICHA);
		listaUsoObjeto.add(usoObjetoVO);
	}

	/**
	 * Añade a listaUsoObjeto de la request un usoObjetoVO perteneciente a un
	 * campo tabla que tiene como identificador, el id pasado por parámetro
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            id del objeto usado
	 */
	private void addUsoObjetoCampoTabla(HttpServletRequest request, String id) {
		List listaUsoObjeto = getListaUsoObjeto(request);

		UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
		usoObjetoVO.setIdObj(id);
		usoObjetoVO.setTipoObj(TipoObjetoUsado.CAMPO_TABLA);
		usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.FICHA);
		listaUsoObjeto.add(usoObjetoVO);
	}

	/**
	 * Añade a listaUsoObjeto de la request un usoObjetoVO perteneciente a un
	 * campo que tiene como identificador, el id pasado por parámetro
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            id del campo en uso.
	 */
	private void addUsoObjetoCampo(HttpServletRequest request, String id) {

		List listaUsoObjeto = getListaUsoObjeto(request);

		UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
		usoObjetoVO.setIdObj(id);
		usoObjetoVO.setTipoObj(TipoObjetoUsado.CAMPO_DATO);
		usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.FICHA);
		listaUsoObjeto.add(usoObjetoVO);
	}

	/**
	 * Añade a listaUsoObjeto de la request tantos objetos de tipo usoObjetoVO
	 * como listas descriptoras tenga el node pasado por parámetro.El
	 * UsoObjetoVO añadido tendra como identificador, el id de la lista
	 * descriptora.
	 *
	 * @param HttpServletRequest
	 *            request.
	 * @param Node
	 *            node que contiene listas descriptoras.
	 */
	private void addUsoObjetoListaDescriptora(HttpServletRequest request,
			Node node) {

		List listaUsoObjeto = getListaUsoObjeto(request);

		String path = DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
				+ Constants.SLASH + DefTags.TAG_VALIDACION_INFO_ESPEC
				+ Constants.SLASH + DefTags.TAG_IDS_LISTAS_DESC_INFO_ESPEC;
		Node listasDescriptorasNode = XmlFacade.getNode(node, path);
		if (listasDescriptorasNode != null
				&& listasDescriptorasNode.getFirstChild() != null) {
			String listasDescriptoras = listasDescriptorasNode.getFirstChild()
					.getNodeValue();
			StringOwnTokenizer str = new StringOwnTokenizer(listasDescriptoras,
					Constants.COMMA);
			while (str.hasMoreElements()) {
				UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
				usoObjetoVO.setIdObj(str.nextToken());
				usoObjetoVO.setTipoObj(TipoObjetoUsado.LISTA_DESCRIPTORES);
				usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.FICHA);
				listaUsoObjeto.add(usoObjetoVO);
			}
		}
	}

	/**
	 * Obtiene el tipo de dato para un nodo
	 *
	 * @param node
	 * @return
	 */
	private String getTipoDato(Node node) {

		String path = DefTags.TAG_TIPO_CAMPO_DATO;
		Node myNode = XmlFacade.getNode(node, path);

		if (myNode != null && myNode.getFirstChild() != null) {
			return myNode.getFirstChild().getNodeValue();
		}

		return null;
	}

	/**
	 * Añade a listaUsoObjeto de la request un objeto de tipo usoObjetoVO El
	 * UsoObjetoVO añadido tendra como identificador, el id de la tabla de
	 * validación que contiene el node pasado por parametro.
	 *
	 * @param HttpServletRequest
	 *            request.
	 * @param Node
	 *            node que contiene la tabla de validación.
	 */
	private void addUsoObjetoTablaValidacion(HttpServletRequest request,
			Node node) {
		List listaUsoObjeto = getListaUsoObjeto(request);

		String path = DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
				+ Constants.SLASH + DefTags.TAG_VALIDACION_INFO_ESPEC
				+ Constants.SLASH;

		if (TipoCampoDatoConstants.NUMERICO.equals(getTipoDato(node))) {
			path += DefTags.TAG_MEDIDA_INFO_ESPEC + Constants.SLASH
					+ DefTags.TAG_UNIDAD_MEDIDA_INFO_ESPEC + Constants.SLASH;
		}

		path += DefTags.TAG_IDTBLVLD_INFO_ESPEC;

		Node listasTablaValidacionNode = XmlFacade.getNode(node, path);
		if (listasTablaValidacionNode != null
				&& listasTablaValidacionNode.getFirstChild() != null) {
			String listasTablaValidacion = listasTablaValidacionNode
					.getFirstChild().getNodeValue();
			UsoObjetoVO usoObjetoVO = new UsoObjetoVO();
			usoObjetoVO.setIdObj(listasTablaValidacion);
			usoObjetoVO.setTipoObj(TipoObjetoUsado.TABLA_VALIDACION);
			usoObjetoVO.setTipoObjUsuario(TipoObjetoUsuario.FICHA);
			listaUsoObjeto.add(usoObjetoVO);
		}
	}

	/**
	 * Crea un elemento con la información del nodo XML.
	 *
	 * @param node
	 *            Nodo XML.
	 * @param HttpServletRequest
	 *            request
	 * @return Elemento de tipo DefFmtElemento.
	 * @throws CampoNotFoundException
	 */
	protected DefFmtElemento createElemento(Node node,
			HttpServletRequest request) throws CampoNotFoundException {
		DefFmtElemento elemento = null;

		if (node != null) {
			String tipo = XmlFacade.get(node, Constants.ARROBA
					+ DefTags.ATTR_TIPO_FORMATO);
			if (tipo.equalsIgnoreCase(DefTags.TIPO_ESPECIAL)) {
				String idNodeValue = null;
				String valorPredeterminado = null;
				String mostrarScroll = XmlFacade.get(node, Constants.ARROBA
						+ DefTags.ATTR_SCROLL);

				Node NodeId = XmlFacade.getNode(node, DefTags.TAG_ID_CAMPO);
				if (NodeId != null) {
					idNodeValue = NodeId.getFirstChild().getNodeValue();
					valorPredeterminado = getValorPredeterminadoTipoEspecial(Integer
							.parseInt(idNodeValue));
				}
				elemento = createElementoEtiquetaDato(valorPredeterminado,
						idNodeValue, mostrarScroll);

			} else if (tipo.equalsIgnoreCase(DefTags.TIPO_DATO)) {
				Node idNode = XmlFacade.getNode(node, DefTags.TAG_ID_CAMPO);
				Node tipoNode = XmlFacade.getNode(node,
						DefTags.TAG_TIPO_MEDIDA_INFO_ESPEC);

				String id = idNode.getFirstChild().getNodeValue();
				String tipoValue = tipoNode.getFirstChild().getNodeValue();

				ServiceRepository services = getServiceRepository(request);
				GestionDescripcionBI descripcionService = services
						.lookupGestionDescripcionBI();

				CampoDatoVO campoDatoVO = descripcionService.getCampoDato(id);

				if (campoDatoVO == null) {
					logger.info("Error: en la tabla ADCAMPODATO, no existe el registro con id "
							+ id);

					throw new CampoNotFoundException(id);

				}

				String mostrarScroll = XmlFacade.get(node, Constants.ARROBA
						+ DefTags.ATTR_SCROLL);
				elemento = createElementoEtiquetaDato(campoDatoVO.getNombre(),
						id, mostrarScroll);
				if (tipoValue.equals("3")) {
					((DefFmtElementoEtiquetaDato) elemento).getCampo()
							.setValorSeleccionPorDefecto(
									Constants.FORMATO_FECHA_DDMMAAAA);
				}

				addUsoObjetoCampo(request, id);
				addUsoObjetoListaDescriptora(request, node);
				addUsoObjetoTablaValidacion(request, node);
			}

			else if (tipo.equalsIgnoreCase(DefTags.TIPO_TABLA)) {
				Node idNode = XmlFacade.getNode(node, DefTags.TAG_ID_CAMPO);
				String id = idNode.getFirstChild().getNodeValue();
				addUsoObjetoCampoTabla(request, id);
				elemento = createElementoTablaTextual(node, request);
			}

			else {
			}
		}

		return elemento;
	}

	/**
	 * Crea una etiqueta de elemento asignandole un valor Predeterminado.
	 *
	 * @param String
	 *            valorPredeterminado.
	 * @return DefFmtEtiqueta Etiqueta del elemento.
	 */
	protected DefFmtEtiqueta createEtiqueta(String valorPredeterminado) {
		DefFmtEtiqueta etiqueta = new DefFmtEtiqueta();

		// Título
		etiqueta.getTitulo().setPredeterminado(valorPredeterminado);

		return etiqueta;
	}

	/**
	 * Obtiene el texto perteneciente a un identificador de Area
	 *
	 * @param node
	 *            que contiene el id del area asociada
	 * @param HttpServletRequest
	 *            request
	 * @return String, texto del area asociada
	 */
	protected String getTextoAreaAsociada(Node node, HttpServletRequest request) {

		String idAreaAsociada = node.getFirstChild().getNodeValue();

		if (idAreaAsociada != null
				&& !idAreaAsociada.trim().equals(Constants.BLANK)) {
			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			AreaVO areaVO = descripcionService.getArea(idAreaAsociada);
			if (areaVO != null) {
				return areaVO.getNombre();
			}
		}

		return null;

	}

	/**
	 * Crea un elemento de tipo sección con la información del nodo XML.
	 *
	 * @param node
	 *            Nodo XML.
	 * @param HttpServletRequest
	 *            request
	 * @return DefFmtElementoSeccion Elemento de tipo sección.
	 * @throws CampoNotFoundException
	 */
	protected DefFmtElementoSeccion createElementoSeccion(Node node,
			HttpServletRequest request) throws CampoNotFoundException {
		DefFmtElementoSeccion elemento = new DefFmtElementoSeccion();

		if (node != null) {
			// Desplegada
			elemento.setDesplegada(Constants.TRUE_STRING);

			// Etiqueta para el area
			Node nodeArea = XmlFacade.getNode(node,
					DefTags.TAG_ID_AREA_ASOCIADA);
			elemento.setEtiqueta(createEtiqueta(getTextoAreaAsociada(nodeArea,
					request)));

			String id = nodeArea.getFirstChild().getNodeValue();
			addUsoObjetoCampoArea(request, id);

			// Elementos
			String path = DefTags.TAG_CAMPOS + Constants.SLASH
					+ DefTags.TAG_CAMPO;
			NodeIterator nodeIt = XmlFacade.getNodeIterator(node, path);
			for (Node nodeElemento = nodeIt.nextNode(); nodeElemento != null; nodeElemento = nodeIt
					.nextNode())
				elemento.addElemento(createElemento(nodeElemento, request));
		}

		return elemento;
	}

	/**
	 * Crea la definición del formato de una ficha.
	 *
	 * @param doc
	 *            Documento XML de la definición del formato de la ficha.
	 * @param HttpServletRequest
	 *            request
	 * @return DefFmtFicha Definición del formato de la ficha.
	 * @throws CampoNotFoundException
	 */
	public DefFmtFicha createDefFmtFicha(Document doc,
			HttpServletRequest request) throws CampoNotFoundException {
		DefFmtFicha definicionFmtFicha = null;

		if (doc != null) {
			definicionFmtFicha = new DefFmtFicha();

			String path = Constants.SLASH + DefTags.TAG_DEFINITION
					+ Constants.SLASH + DefTags.TAG_INFORMACION_CAMPOS
					+ Constants.SLASH + DefTags.TAG_AREA;
			NodeIterator nodeAreasIt = XmlFacade.getNodeIterator(doc, path);
			for (Node nodeArea = nodeAreasIt.nextNode(); nodeArea != null; nodeArea = nodeAreasIt
					.nextNode()) {
				definicionFmtFicha.addElemento(createElementoSeccion(nodeArea,
						request));
			}
		}

		return definicionFmtFicha;
	}

	/**
	 * Crea la definición del formato de una ficha.
	 *
	 * @param xml
	 *            Cadena con el XML de la definición del formato de la ficha.
	 * @param HttpServletRequest
	 *            request
	 * @return DefFmtFicha Definición del formato de la ficha.
	 * @throws CampoNotFoundException
	 */
	public DefFmtFicha createDefFmtFicha(String xml, HttpServletRequest request)
			throws CampoNotFoundException {
		DefFmtFicha definicionFmtFicha = null;

		if (xml != null) {
			XmlFacade xmlFacade = new XmlFacade(xml);
			definicionFmtFicha = createDefFmtFicha(xmlFacade.getDocument(),
					request);
		}

		return definicionFmtFicha;
	}

	/**
	 * Crea el formato de una ficha.
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param FichaVO
	 *            fichaVO
	 * @return createFmtFicha Definición del formato de la ficha.
	 * @throws CampoNotFoundException
	 */
	public String createFmtFicha(HttpServletRequest request, FichaVO fichaVO)
			throws CampoNotFoundException {
		DefFmtFicha defFmtFicha = createDefFmtFicha(fichaVO.getDefinicion(),
				request);
		return defFmtFicha.toString();
	}

	/**
	 * Introduce en la request la lista de fichas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void listExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de listExecuteLogic");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List fichas = descripcionService.getFichas();
		request.setAttribute(ControlAccesoConstants.LISTA_FICHAS, fichas);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.FICHAS_LISTADO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mapping.findForward("list"));
	}

	/**
	 * Inicializa la creación de una ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.FICHAS_EDICION, request);

		FichasForm fichasForm = (FichasForm) form;
		fichasForm.reset();

		String definicion = Constants.XML_HEADER + Constants.NEWLINE
				+ Constants.XML_DEFINITION_OPEN + Constants.NEWLINE + "   "
				+ Constants.XML_INFORMACION_CAMPOS_OPEN + Constants.NEWLINE
				+ "   " + Constants.XML_INFORMACION_CAMPOS_CLOSE
				+ Constants.NEWLINE + Constants.XML_DEFINITION_CLOSE;

		fichasForm.setDefinicion(definicion);
		loadListas(request, fichasForm);

		setReturnActionFordward(request, mapping.findForward("new"));
	}

	/**
	 * Introduce en sesión la lista de niveles cuando se cambia en el tipo de
	 * norma
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void changeTipoNormaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FichasForm fichasForm = (FichasForm) form;
		loadNiveles(fichasForm, request);
		setReturnActionFordward(request, mapping.findForward("changeTipoNorma"));
	}

	/**
	 * Crea una nueva ficha, y un formato
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void createExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		FichasForm fichasForm = (FichasForm) form;

		ActionErrors errors = fichasForm.validate(request);

		if (errors == null || errors.isEmpty()) {
			try {
				ServiceRepository services = getServiceRepository(request);
				GestionDescripcionBI descripcionService = services
						.lookupGestionDescripcionBI();

				FichaVO fichaVO = fichasForm.populate(new FichaVO());

				errors = comprobarDuplicados(descripcionService, request, null,
						fichaVO, fichasForm.getGuid());

				if (errors == null || errors.isEmpty()) {

					FmtFichaVO fmtFichaVO = new FmtFichaVO();
					fmtFichaVO.setDefinicion(createFmtFicha(request, fichaVO));

					List listaUsoObjeto = (List) request
							.getAttribute(Constants.LISTA_USO_OBJETO);

					if (StringUtils.isNotEmpty(fichasForm.getIdFichaOrigen())) {
						fichaVO = descripcionService.duplicarFicha(
								fichasForm.getIdFichaOrigen(), fichaVO);
						fichasForm.setIdFichaOrigen(null);
					} else {
						fichaVO = descripcionService.createFicha(fichaVO,
								fmtFichaVO, listaUsoObjeto);
					}
					goReturnPointExecuteLogic(mapping, form, request, response);
				} else {
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("create-failed"));
				}

			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("create-failed"));
			} catch (CampoNotFoundException e) {
				errors = getErrors(request, true);
				errors.add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								"archigest.archivo.campo.descripcion.inexistente",
								e.getId()));
				ErrorsTag.saveErrors(request, errors);

				setReturnActionFordward(request,
						mapping.findForward("create-failed"));

			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
		}
	}

	/**
	 * Comprueba que no existan duplicados en la base de datos.
	 *
	 * @param descripcionService
	 * @param request
	 * @param errors
	 * @param campoDatoVO
	 * @param guid
	 * @return
	 */
	private ActionErrors comprobarDuplicados(
			GestionDescripcionBI descripcionService,
			HttpServletRequest request, ActionErrors errors, FichaVO fichaVO,
			String guid) {

		if (errors == null) {
			errors = new ActionErrors();
		}
		// Comprobar GUID único
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE, ArchivoTables.ADFICHA_TABLE,
				guid, LABEL_FICHA, Constants.ETIQUETA_GUID);

		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE, ArchivoTables.ADFICHA_TABLE,
				fichaVO.getId(), fichaVO.getNombre(), LABEL_FICHA,
				Constants.ETIQUETA_NOMBRE);

		return errors;
	}

	/**
	 * Actualiza una ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void updateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		FichasForm fichasForm = (FichasForm) form;
		ActionErrors errors = fichasForm.validate(request);

		if (errors == null || errors.isEmpty()) {

			try {

				ServiceRepository services = getServiceRepository(request);
				GestionDescripcionBI descripcionService = services
						.lookupGestionDescripcionBI();

				FichaVO fichaVO = fichasForm.populate(new FichaVO());

				errors = comprobarDuplicados(descripcionService, request, null,
						fichaVO, fichasForm.getGuid());

				if (errors == null || errors.isEmpty()) {

					createFmtFicha(request, fichaVO);

					List listaUsoObjeto = (List) request
							.getAttribute(Constants.LISTA_USO_OBJETO);
					fichaVO = descripcionService.updateFicha(fichaVO,
							listaUsoObjeto);
					goReturnPointExecuteLogic(mapping, form, request, response);
				} else {
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("update-failed"));
				}

			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
				setReturnActionFordward(request,
						mapping.findForward("update-failed"));
			} catch (CampoNotFoundException e) {
				errors = getErrors(request, true);
				errors.add(
						Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								"archigest.archivo.campo.descripcion.inexistente",
								e.getId()));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mapping.findForward("update-failed"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
		}
	}

	/**
	 * Recupera una ficha desde la lista de fichas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveFromListExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idFicha = request.getParameter("idFicha");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		FichaVO fichaVO = descripcionService.getFicha(idFicha);

		setInTemporalSession(request, "fichaVO", fichaVO);
		setReturnActionFordward(request,
				mapping.findForward("retrieveFromList"));
	}

	/**
	 * Recupera una ficha desde la lista de fichas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void duplicarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.DUPLICAR_FICHA, request);

		FichasForm fichasForm = (FichasForm) form;

		fichasForm.setIdFichaOrigen(fichasForm.getId());
		fichasForm.setId(null);
		fichasForm.setGuid(null);

		setReturnActionFordward(request, mapping.findForward("duplicarFicha"));
	}

	/**
	 * Crea una nueva ficha, y un formato
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void aceptarDuplicarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ActionNotAllowedException {

		FichasForm fichasForm = (FichasForm) form;

		ActionErrors errors = fichasForm.validateDuplicar(request);

		if (errors == null || errors.isEmpty()) {
			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			FichaVO fichaVO = fichasForm.populate(new FichaVO());

			errors = comprobarDuplicados(descripcionService, request, null,
					fichaVO, fichasForm.getGuid());

			if (errors == null || errors.isEmpty()) {
				fichaVO = descripcionService.duplicarFicha(
						fichasForm.getIdFichaOrigen(), fichaVO);
				fichasForm.setIdFichaOrigen(null);
				goReturnPointExecuteLogic(mapping, form, request, response);
			} else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mapping.findForward("duplicarFicha"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("duplicarFicha"));
		}
	}

	/**
	 * Recupera una ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.FICHAS_EDICION, request);

		FichaVO fichaVO = (FichaVO) getFromTemporalSession(request, "fichaVO");
		FichasForm fichasForm = (FichasForm) form;
		fichasForm.set(fichaVO);

		loadListas(request, fichasForm);

		setReturnActionFordward(request, mapping.findForward("retrieve"));
	}

	/**
	 * Elimina una ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void deleteExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = null;

		FichasForm fichasForm = (FichasForm) form;
		if ((errors = validateFormParaEliminarFicha(fichasForm)) == null) {
			String idFichasABorrar[] = fichasForm.getFichasABorrar();
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
			try {
				descripcionService.deleteFichas(idFichasABorrar);
			} catch (ArchivoModelException e) {
				errors = new ActionErrors();
				errors.add(ErrorKeys.ERROR_NO_REMOVE_FICHA, new ActionError(
						ErrorKeys.ERROR_NO_REMOVE_FICHA));
				ErrorsTag.saveErrors(request, errors);
				getInvocationStack(request).getLastClientInvocation()
						.addParameters(fichasForm.getMap());
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		setReturnActionFordward(request, mapping.findForward("actionList"));
	}

	/**
	 * Accede a la página que permite importar una definición de ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void importExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.FICHAS_IMPORTAR, request);
		setReturnActionFordward(request, mapping.findForward("import"));
	}

	/**
	 * Copia a la definición de una ficha, el contenido de un fichero de texto.
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void importAcceptExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FichasForm fichasForm = (FichasForm) form;

		try {
			FormFile file = fichasForm.getFichero();
			String definicion = FileHelper.getTextFileContent(file
					.getInputStream());
			fichasForm.setDefinicion(definicion);
		} catch (IOException ex) {
			logger.info(ex.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_LEER_EN_FICHERO, new ActionError(
					ErrorKeys.ERROR_LEER_EN_FICHERO));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
			return;
		}

		popLastInvocation(request);
		setReturnActionFordward(request, mapping.findForward("importAccept"));
	}

	/**
	 * Sale de la página que permite importar una definición de ficha
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void importCancelExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		popLastInvocation(request);
		setReturnActionFordward(request, mapping.findForward("importCancel"));

	}

	/**
	 * Accede a la página que permite exportar la definición de una ficha a un
	 * fichero
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void exportExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			FichasForm formulario = (FichasForm) form;

			String definicion = formulario.getDefinicion();

			download(
					response,
					formulario.getNombreFicheroExportXml(null,
							formulario.getNombre()), definicion.getBytes());

		} catch (Exception e) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
							.toString()));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Accede a la página que permite exportar la definición de una ficha a un
	 * fichero
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void exportMapeoExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			FichasForm formulario = (FichasForm) form;

			String definicion = formulario.getDefinicionMapeo();

			download(response, formulario.getNombreFicheroExportXml(
					"Mapeo Descripcion ficha ", formulario.getNombre()),
					definicion.getBytes());

		} catch (Exception e) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
							.toString()));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Carga en sesión las listas de normas y de niveles
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param FichasForm
	 *            fichasForm
	 */
	private void loadListas(HttpServletRequest request, FichasForm fichasForm) {
		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY,
				descripcionService.makeListTipoNorma());
		loadNiveles(fichasForm, request);
	}

	/**
	 * Carga en sesión la lista de niveles
	 *
	 * @param FichasForm
	 *            fichasForm
	 * @param HttpServletRequest
	 *            request
	 */
	private void loadNiveles(FichasForm fichasForm, HttpServletRequest request) {
		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		removeInTemporalSession(request, FondosConstants.LISTA_SUBTIPOS_NIVEL);

		removeInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS);

		if (fichasForm.getTipoNorma() == null
				|| fichasForm.getTipoNorma().equalsIgnoreCase(
						String.valueOf(TipoNorma.SIN_TIPO))) {
			setInTemporalSession(request,
					DescripcionConstants.LISTA_TIPO_NIVELES_KEY,
					descripcionService.makeListTipoNivelesNinguno());
		} else if (fichasForm.getTipoNorma().equalsIgnoreCase(
				String.valueOf(TipoNorma.NORMA_ISADG))) {
			setInTemporalSession(request,
					DescripcionConstants.LISTA_TIPO_NIVELES_KEY,
					descripcionService.makeListTipoNivelesIsad());

			List listaSubtipos = descripcionService
					.makeListSubTipoNivelesIsad(fichasForm.getTipoNivel());

			if (ListUtils.isNotEmpty(listaSubtipos)) {
				setInTemporalSession(request,
						FondosConstants.LISTA_SUBTIPOS_NIVEL, listaSubtipos);
				setInTemporalSession(request, FondosConstants.MOSTRAR_SUBTIPOS,
						Boolean.TRUE);
			}
		} else {
			setInTemporalSession(request,
					DescripcionConstants.LISTA_TIPO_NIVELES_KEY,
					descripcionService.makeListTipoNivelesIsaar());
		}
	}

	/**
	 * Comprueba si hay alguna ficha seleccionada a borrar
	 *
	 * @param FichasForm
	 *            frm
	 * @return
	 */
	private ActionErrors validateFormParaEliminarFicha(FichasForm frm) {

		ActionErrors errors = new ActionErrors();

		String idFichasABorrar[] = frm.getFichasABorrar();
		if (idFichasABorrar == null || idFichasABorrar.length == 0) {
			errors.add(ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UNA_FICHA,
					new ActionError(
							ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UNA_FICHA));
		}
		return errors.size() > 0 ? errors : null;
	}

	/**
	 * Recupera una ficha desde la lista de fichas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void edicionAvanzadaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.FICHAS_EDICION_AVANZADA,
				request);

		FichasForm fichasForm = (FichasForm) form;

		if (fichasForm.isConEdicionAvanzada()) {
			MapDescUDocVO mapeoDescripcion = getGestionDescripcionBI(request)
					.getMapeoDescripcion(fichasForm.getId());

			if (mapeoDescripcion != null) {
				fichasForm.setId(mapeoDescripcion.getIdFicha());
				fichasForm.setDefinicionMapeo(mapeoDescripcion.getInfo());
			}

			setReturnActionFordward(request,
					mapping.findForward("editAvanzado"));

		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_FICHA_SIN_EDICION_AVANZADA,
					new ActionError(ErrorKeys.ERROR_FICHA_SIN_EDICION_AVANZADA));
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(request, mapping.findForward("retrieve"));
		}
	}

	/**
	 * Recupera una ficha desde la lista de fichas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void actualizarAvanzadoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		FichasForm fichasForm = (FichasForm) form;

		ActionErrors errors = fichasForm.validateAvanzado(request);

		if (errors == null || errors.isEmpty()) {
			MapDescUDocVO mapDescUDocVO = new MapDescUDocVO(fichasForm.getId(),
					fichasForm.getDefinicionMapeo());
			getGestionDescripcionBI(request).updateMapeoDescripcion(
					mapDescUDocVO);
			setReturnActionFordward(request,
					mapping.findForward("editAvanzado"));
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("editAvanzado"));
		}

	}

}
