package transferencias.db;

import java.util.List;

import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.ParteUnidadDocumentalVO;

import common.db.IDBEntity;

/**
 * Clase con los metodos encargados de recuperar y almacenar en la base de datos
 * la informacion de en que unidad de instalacion se encuentran instaladas cada
 * una de las unidades documentales <br>
 * Entidad: <b>ASGTUDOCENUI</b>
 */
public interface IUdocEnUIDBEntity extends IDBEntity {
	public boolean checkUdocsConErrores(final String idRelacion);

	public boolean checkEstadoCotejoUDocsRelacion(final String idRelacion,
			final int estadoCotejo);

	/**
	 * Actualiza en la base de datos la posicion que ocupa una unidad documental
	 * dentro de una unidad de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param udoc
	 *            Identificador de unidad documental
	 * @param uinstalacion
	 *            Identificador de unidad de instalacion
	 * @param posicion
	 *            Posicion de la unidad documental dentro de la unidad de
	 *            instalacion
	 * @throws Exception
	 */
	public void updatePosicionEnCaja(final String udoc,
			final String uinstalacion, final int posicion);

	public void updatePosicionEnCaja2(final int posicionActualUdoc,
			final String uinstalacion, final int nuevaPosicion);

	// TODO Sustituir las llamadas a estos metodos por llamada a updateFields.
	// Eliminar todos estos metodos
	public void updateEstadoYSignatura(final String idUDoc,
			final String[] numsPartesUDoc, final int estado,
			final String signatura);

	public void updateSignatura(String idUDoc, String numParteUDoc,
			String signatura);

	public void updateEstado(String idUDoc, String numParteUDoc, int estado);

	public void updateEstadoCotejoUdoc(String idUDoc,
			String idUnidadInstalacion, int estado);

	public void updateEstadoCotejoUdocXIdRelacion(String idRelacion, int estado);

	public void updateEstadoYSignatura(String idUDoc, String numParteUDoc,
			int estado, String signatura);

	public void updateEstadoYObservacionesYSignatura(String idUDoc,
			String numParteUDoc, int estado, String observaciones,
			String signatura);

	/**
	 * Modifica el estado de cotejo del contenido de las unidades de
	 * instalación.
	 * 
	 * @param ids
	 *            Identificadores de las unidades de instalación.
	 * @param estado
	 *            Estado de cotejo.
	 */
	public void updateEstadoXUnidadesInstalacion(String[] ids, int estado);

	/**
	 * Obtiene las unidades documentales incluidas en una unidad de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalacion
	 * @return Lista con las unidades documentales incluidas en la unidad de
	 *         instalacion
	 */
	public List fetchRowsByUInstalacion(String idUnidadInstalacion);

	public List fetchRowsByUInstalacion(String[] idUnidadInstalacion);

	public List fetchRowsByUInstalacion(String idUnidadInstalacion,
			int[] posiciones);

	public void deleteXIdRelacion(final String idRelacionEntrega);

	public void deleteUdocFromUI(final String unidadInstalacionID,
			final int posUdoc);

	public void deleteParteUdoc(String idUnidadDoc, int numParteUdoc);

	public void updateUnidadInstalacionUdoc(String udocID, int numeroParte,
			String unidadInstalacionID, int posUdocEnUI);

	public void addUdocToUI(IParteUnidadDocumentalVO parteUdoc);

	public void dropByUdoc(final String udocID);

	public List getSignaturasUdoc(String idUdoc);

	public int getNumUdocsUbicadas(final String idRelacionEntrega);

	public void updateDescripcion(String idUnidadDocumental, int numeroParte,
			String descripcion);

	// public void updateFields(String idUnidadDocumental, int numeroParte, Map
	// fieldsToUpdate);

	/**
	 * Obtiene las partes de una unidad documental que se encuentran asignadas
	 * 
	 * @param idUnidadDocumental
	 *            identificador de unidad documental
	 * @return Lista de partes de unidad documental
	 *         {@link ParteUnidadDocumentalVO}
	 */
	public List fetchRowsByUdoc(String idUnidadDocumental);

	IParteUnidadDocumentalVO getRowByUdocUI(String idUnidadInstalacion,
			String idUnidadDocumental);

	/**
	 * Recupera los expedientes de una relación que están asignados a alguna
	 * caja
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de expedientes {@link ParteUnidadDocumentalVO}
	 */
	public List fetchRowsByIdRelacion(String idRelacion);

	public int countUdocsEnUi(String idUInstalacion);

	public int countPartesUdoc(String idUnidadDocumental);

	/**
	 * Obtiene las Parte de Unidad Documental, por el idUinstalación y la
	 * Posición.
	 * 
	 * @param idUinstalacion
	 * @param posicion
	 * @return
	 */
	public IParteUnidadDocumentalVO getRowByUdocPosicion(String idUinstalacion,
			int posicion);

	/**
	 * Obtiene todas las partes de una unidad documental
	 * 
	 * @param idUnidadDocumental
	 * @return Lista de ParteUnidadDocumentalVO
	 */
	public List getListaPartes(String idUnidadDocumental);

	/**
	 * Obtiene las partes de una unidad documental, en los estados
	 * especificados.
	 * 
	 * @param idUnidadDocumental
	 *            Identificador de la unidad documental
	 * @param estados
	 *            Estados
	 * @return Lista de ParteUnidadDocumentalVO
	 */
	public List getListaPartesByEstado(String idUnidadDocumental, int[] estados);

	// Udocs con la posicion de su caja en la relacion
	List getUdocsByIdRelacionOficinaArchivo(String idRelacion);

	List getUdocsByIdRelacionEntreArchivos(String idRelacion);

	/**
	 * Obtiene la posición máxima de las unidades documentales en la unidad de
	 * instalación para la relación de entrega que se le pasa como parámetro.
	 * 
	 * @param idUInstalacion
	 *            Identificador de la Unidad de Instalación
	 * @param idRelacion
	 *            Identifciador de la Relación de Entrega
	 * @return
	 */
	public int maxPosUdocEnUI(String idUInstalacion, String idRelacion);

}