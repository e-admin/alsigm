package transferencias.db;

import java.util.Date;
import java.util.List;

import transferencias.vos.UnidadDocumentalVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

/**
 * Clase con los metodos encargados de recuperar y actualizar la tabla en la que
 * se almacenan los datos de unidades documentales. <br>
 * Entidad: <b>ASGTUNIDADDOCRE</b>
 *
 */
public interface IUDocRelacionDBEntity extends IDBEntity {

	public static String EXPEDIENTE_ORDER_CRITERION = "expediente";

	public static String ASUNTO_ORDER_CRITERION = "asunto";

	/**
	 * Inserta una unidad documenta en la base de datos
	 *
	 * @param udoc
	 *            Datos de la unidad documental a insertar
	 */
	public void insertRow(final UnidadDocumentalVO udoc);

	/**
	 * Recupera de la base de datos una unidad documental a partir de su
	 * identificador
	 *
	 * @param udocID
	 *            Identficador de unidad documental
	 * @return Datos de unidad documental {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO fetchRow(String udocID);

	/**
	 * Borra una unidad documental de la base de datos
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 */
	public void dropRow(final String udocID);

	/**
	 * Obtiene la lista de unidades documentales incluidas en una relacion de
	 * entrega
	 *
	 * @param codigoRelacion
	 *            Codigo de la relacion de entrega cuyas unidades documentales
	 *            se desean recuperar
	 * @return Lista con la informacion referente a las unidades documentales
	 *         que estan incluidas en la relacion de entrega
	 */
	// public List fetchRowsByCodigoRelacion(String codigoRelacion,PageInfo
	// pageInfo) throws TooManyResultsException ;
	public List fetchRowsByCodigoRelacion(String codigoRelacion);

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion);

	/**
	 * Obtiene la unidad documental perteneciente a la relación de entrega
	 * @param idRelacion
	 * @return
	 */
	public List fetchRowsByIdRelacionOrderByOrdenNoValidada(
			String idRelacion);

	public List fetchRowsByCodigoRelacionOrderByOrden(String codigoRelacion,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obteniene una lista de unidades documentales
	 *
	 * @param idsUDocs
	 * @return
	 */
	public List fetchRowsByIds(String[] idsUDocs);

	/**
	 * Obtiene la lista de unidades documentales incluidad en una determinada
	 * unidad de instalacion de la relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relacion de entrega
	 * @param idUnidadInstalacion
	 *            Identificador de unidad de instalacion
	 * @return Lista con las unidades documentales incluidas en la unidad de
	 *         instalacion especificada
	 */
	public List fetchRowsByUInstalacion(String idRelacion,
			String idUnidadInstalacion);

	/**
	 * Actualiza en la base de datos los datos de una unidad documental
	 *
	 * @param udoc
	 *            Datos de unidad documental a actualizar
	 */
	public void updateRow(UnidadDocumentalVO udoc);

	/**
	 * Actualiza en la base de datos los datos de una unidad documental
	 *
	 * @param idUdoc
	 *            Id de unidad documental a actualizar
	 * @param xmlInfo
	 *            información a actualizar
	 */
	public void updateXmlInfo(UnidadDocumentalVO udoc, String xmlInfo);

	/**
	 * Actualiza el estado de validacion de las unidades documentales de una
	 * relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param validada
	 *            <b>true</b> en caso de que se quieran marcar las unidades
	 *            documentales como validadas y <b>false</b> para marcarlas como
	 *            no validadas
	 */
	public void setEstadoValidacion(String idRelacion, boolean validada);

	/**
	 * Elimina de la base de datos las unidades documentales incluidas en una
	 * relacion de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 */
	public void deleteXIdRelacion(String idRelacion);

	public boolean isAllUdocsConDocFisicosEnCaja(String idRelacion);

	public boolean existUdocsConDocumentosFisicos(String idRelacion);

	/**
	 * Establece el numero de partes en que esta divida una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param numeroPartes
	 *            Numero de partes a establecer
	 */
	public void updateNumPartesUdoc(String udocID, int numeroPartes);

	/**
	 * Establece el asunto de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param titulo
	 *            asunto a establecer
	 */
	public void updateAsunto(String udocID, String titulo);

	/**
	 * Establece el valor del campo orden
	 *
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param orden
	 *            Número de Orden a establecer
	 */
	public void updateOrden(String idUdoc, int orden);

	/**
	 * Permite establecer la marca de bloqueo de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param marca
	 *            valor de la marca a establecer
	 */
	public void updateMarcaBloqueo(String udocID, int marca);

	/**
	 * Establece el número de expediente de una unidad documental
	 *
	 * @param udocID
	 *            Identificador de unidad documental
	 * @param numExp
	 *            número de expediente a establecer
	 */
	public void updateNumeroExpediente(String udocID, String numExp);

	public List fetchRowsByNumExpediente(String numeroExpediente,
			String idRelacion);

	/**
	 * Cuenta el numero de unidades documentales de una relacion de entrega que
	 * cuyo estado de cotejo sea alguno de los solicitados. El conjunto de
	 * estados de cotejo puede ser nulo en cuyo caso se devuelve el numero total
	 * de unidades documentales de la relacion
	 *
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param estadoCotejo
	 *            Conjunto de estados de cotejo
	 * @return numero de unidades documentales
	 */
	public int countUdocsConEstado(String id, int[] estadoCotejo);

	/**
	 * Cuenta el numero de unidades documentales de una relación de entrega cuyo
	 * id coincide con el que se le pasa como parámetro
	 *
	 * @param codigoRelacion
	 * @return
	 */
	public int countRowsByCodigoRelacion(String codigoRelacion);

	/**
	 * Incrementa el numero de partes en que se encuentra dividida una unidad
	 * documental en una determinada cantidad
	 *
	 * @param idUnidadDoc
	 *            Identificador de unidad documental
	 * @param inc
	 *            Cantidad en la que se quiere incrementar el numero de partes
	 */
	public void incrementNumPartesUdoc(String idUnidadDoc, int i);

	/**
	 * Recupera las unidades documentales de una relación de entrega que todavía
	 * no han sido asignadas a ninguna caja
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocsSinCaja(String idRelacion);

	/**
	 * Comprueba si la relación solo tiene relaciones Electrónicas.
	 *
	 * @param codigoRelacion
	 *            Código de Relaci´no
	 * @return true si solo tiene documentos electrónicos.
	 */
	public boolean isOnlyDocumentosElectronicos(String codigoRelacion);

	/**
	 * Obtiene las Unidades Documentales que son físicas.
	 *
	 * @param codigoRelacion
	 * @return
	 */
	public List fetchRowsFisicasByCodigoRelacionOrderByOrden(
			String codigoRelacion);

	/**
	 * Recupera las unidades documentales de una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de unidades documentales {@link UnidadDocumentalVO}
	 */
	public List getUdocsByIdRelacion(String idRelacion);

	/**
	 * Actualiza la fecha extrema inicial de la unidad documental en relación de
	 * entrega pasada como parámetro
	 *
	 * @param idUdocRE
	 * @param fechaExtIni
	 */
	public void updateFechaExtIni(String idUdocRE, Date fechaExtIni);

	/**
	 * Actualiza la fecha extrema final de la unidad documental en relación de
	 * entrega pasada como parámetro
	 *
	 * @param idUdocRE
	 * @param fechaExtIni
	 */
	public void updateFechaExtFin(String idUdocRE, Date fechaExtFin);

	/**
	 * Actualiza el orden de las unidades documentales pertenecientes a la
	 * relacion de entrega
	 *
	 * @param idRelacionEntrega
	 * @param orden
	 */
	public void incrementOrdenUdoc(String idRelacionEntrega, int orden,
			int incremento);

	/**
	 * Obtiene los Ids de las Unidades Documentales que contienen los
	 * descriptores seleccionados.
	 *
	 * @param idsDescriptores
	 *            Identificadores de los descriptores a buscar
	 * @return Lista de {@link UnidadDocumentalVO}
	 */
	public List getUdocsByInfoDescriptor(String[] idsDescriptores);

	/**
	 * Permite obtener el siguiente orden a uno pasado dentro de la relación
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param orden
	 *            Orden de la unidad documental en la relación
	 * @return orden del siguiente elemento en la relación de entrega
	 */
	public int getNextOrdenUdocInRelacionEntrega(String idRelacion, int orden);

	/**
	 * Permite obtener el anterior orden a uno pasado dentro de la relación
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param orden
	 *            Orden de la unidad documental en la relación
	 * @return orden del anterior elemento en la relación de entrega
	 */
	public int getPrevOrdenUdocInRelacionEntrega(String idRelacion, int orden);

	/**
	 * Permite obtener una unidad documental de una relación por orden
	 *
	 * @param idRelacion
	 *            Identificador de la relación
	 * @param orden
	 *            Orden de la unidad documental en la relación
	 * @return {@link UnidadDocumentalVO}
	 */
	public UnidadDocumentalVO getUdocInRelacionEntregaByOrden(
			String idRelacion, int orden);

	/**
	 * Permite obtener la lista de rangos de una unidad documental en relación
	 * de entrega de las tablas de descripción de udoc en relación
	 *
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @param idCampoInicial
	 *            identificador del campo rango inicial
	 * @param idCampoFinal
	 *            identificador del campo rango final
	 * @return Lista de rangos {@link transferencias.vos.RangoVO}
	 */
	public List getRangosUDocRE(String idUDoc, String idCampoInicial,
			String idCampoFinal);

	/**
	 * Obtiene una unidad documental
	 *
	 * @param numExp
	 *            Cadena que contiene el número de expediente.
	 * @param idRelacion
	 *            Cadena que contiene el identificador de la relación de entrega
	 * @param asunto
	 *            Cadena que contiene el identificador de
	 * @param fechaExtIni
	 * @param fechaExtFin
	 * @param codSistemaProductor
	 * @return
	 */
	public UnidadDocumentalVO getUnidadDocumental(
			UnidadDocumentalVO filtroUdocVO);

	/**
	 * Obtiene el máximo orde por transferencia
	 *
	 * @param idRelacion
	 *            Cadena que contiene el identificador de la relación de
	 *            entrega.
	 * @return
	 */
	public int getMaxOrden(String idRelacion);

	/**
	 * Obtiene la unidad documental perteneciente a una relación de entrega.
	 * @param idRelacion Identificador de la relación de entrega
	 * @param id Identificador de la unidad documental.
	 * @return
	 */
	public UnidadDocumentalVO getUnidadDocumentalByRelacionAndId(
			String idRelacion, String id);

}