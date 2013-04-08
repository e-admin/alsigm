package docelectronicos.model.helpers;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import common.bi.GestionDocumentosElectronicosBI;
import common.util.TypeConverter;
import common.util.XmlFacade;

import docelectronicos.EstadoClasificador;
import docelectronicos.MarcasClasificador;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

/**
 * Utilidad para gestionar clasificadores.
 */
public class ClasificadoresHelper {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(ClasificadoresHelper.class);

	// Constantes para los tipos de clasificadores por defecto
	protected static final int CLASIFICADOR_POR_DEFECTO_TIPO_FIJO = 1;
	protected static final int CLASIFICADOR_POR_DEFECTO_TIPO_NORMAL = 2;

	/** Identificador del objeto que contiene el clasificador. */
	private String idObjeto = null;

	/** Tipo de objeto que contiene el documento. */
	private int tipoObjeto = TipoObjeto.DESCRIPTOR;

	/** Servicio de gestión de documentos electrónicos. */
	private GestionDocumentosElectronicosBI documentosBI = null;

	/**
	 * Constructor.
	 */
	protected ClasificadoresHelper(String idObjeto, int tipoObjeto,
			GestionDocumentosElectronicosBI documentosBI) {
		this.idObjeto = idObjeto;
		this.tipoObjeto = tipoObjeto;
		this.documentosBI = documentosBI;
	}

	/**
	 * Obtiene una instancia de la clase.
	 * 
	 * @return Instancia de la clase.
	 */
	public static ClasificadoresHelper getInstance(String idObjeto,
			int tipoObjeto, GestionDocumentosElectronicosBI documentosBI) {
		return new ClasificadoresHelper(idObjeto, tipoObjeto, documentosBI);
	}

	/**
	 * Crea los clasificadores por defecto.
	 * 
	 * @param definicion
	 *            XML con la definición de los clasificadores por defecto.
	 */
	public void createClasificadoresPorDefecto(String definicion) {
		XmlFacade xml = new XmlFacade(definicion);
		createClasificadoresPorDefecto(
				xml.getNodeIterator("/Definicion_Ficha/Clasificadores_Predeterminados/Clasificador"),
				null);
	}

	/**
	 * Crea los clasificadores por defecto.
	 * 
	 * @param clasificadoresNodeIt
	 *            Nodos de los clasificadores.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 */
	protected void createClasificadoresPorDefecto(
			NodeIterator clasificadoresNodeIt, String idClfPadre) {
		if (clasificadoresNodeIt != null) {
			for (Node clasificadorNode = clasificadoresNodeIt.nextNode(); clasificadorNode != null; clasificadorNode = clasificadoresNodeIt
					.nextNode()) {
				// Obtener la información del clasificador
				DocClasificadorVO clasificador = createDocClasificadorVO(
						clasificadorNode, idClfPadre);
				if (logger.isDebugEnabled())
					logger.debug("Creando clasificador:\n" + clasificador);

				// Crear el clasificador
				clasificador = documentosBI
						.insertClasificadorXDefecto(clasificador);

				// Crear los clasificadores hijos
				createClasificadoresPorDefecto(XmlFacade.getNodeIterator(
						clasificadorNode, "Clasificadores_Hijos/Clasificador"),
						clasificador.getId());
			}
		}
	}

	/**
	 * Crea un VO con la información del clasificador.
	 * 
	 * @param clasificadorNode
	 *            Nodo con la informacióno del clasificador.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Clasificador.
	 */
	protected DocClasificadorVO createDocClasificadorVO(Node clasificadorNode,
			String idClfPadre) {
		DocClasificadorVO clasificador = new DocClasificadorVO();

		clasificador
				.setNombre(XmlFacade.get(clasificadorNode, "Nombre/text()"));
		clasificador.setIdClfPadre(idClfPadre);
		clasificador.setEstado(EstadoClasificador.VALIDADO);
		clasificador.setDescripcion(XmlFacade.get(clasificadorNode,
				"Descripcion/text()"));
		clasificador.setIdObjeto(idObjeto);
		clasificador.setTipoObjeto(tipoObjeto);
		clasificador
				.setMarcas(TypeConverter.toInt(
						XmlFacade.get(clasificadorNode, "Tipo/text()"), -1) == CLASIFICADOR_POR_DEFECTO_TIPO_FIJO ? MarcasClasificador.FIJO
						: MarcasClasificador.SIN_MARCAS);

		return clasificador;
	}

}
