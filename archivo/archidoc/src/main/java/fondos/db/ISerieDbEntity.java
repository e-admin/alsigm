package fondos.db;

import java.util.Date;
import java.util.List;
import java.util.Map;

import se.usuarios.ServiceClient;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

import fondos.model.UnidadDocumental;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.SerieDocAntVO;
import fondos.vos.SerieVO;

/**
 * Series del cuadro de clasificación de fondos documentales <br>
 * Entidad: <b>ASGFSERIE</b>
 */
public interface ISerieDbEntity extends IDBEntity {

	public SerieVO getSerie(String idSerie);

	public List getSeries(String[] serieIDs);

	public SerieVO insertSerie(SerieVO serie, String idElementoCF);

	public List getSeriesXFondoYEstados(String idFondo, int[] estados);

	public List getSeriesXFondo(String idFondo);

	public void updateFieldsSerie(String idSerie, final Map columnsToUpdate);

	public void updateEstadoSerie(String idSerie, int estado, int estadoPrevio);

	/**
	 * Actualiza el valor del campo estado de la tabla donde se almacenan las
	 * series del cuadro de clasificación de fondos documentales
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param estado
	 *            Estado a establecer a la serie
	 * @param fechaEstado
	 *            Fecha a establecer como momento desde el que la serie tiene el
	 *            estado. Caso de ser nula se asignará como fecha de estado la
	 *            fecha actual del sistema
	 */
	public void setEstadoSerie(String idSerie, int estado, Date fechaEstado);

	/**
	 * Recupera el número de series asignadas a un determinado gestor que se
	 * encuentren en alguno de los estados que se indican
	 * 
	 * @param idGestor
	 *            Identificador de usuario.
	 * @param estados
	 *            Lista de posibles estados de serie. Puede ser nulo.
	 * @return Número de series
	 */
	public int getCountSeriesXGestor(String idGestor, int[] estados);

	/**
	 * Recupera las series asignadas a un determinado gestor que se encuentren
	 * en alguno de los estados que se indican
	 * 
	 * @param idGestor
	 *            Identificador de usuario.
	 * @param estados
	 *            Lista de posibles estados de serie. Puede ser nulo.
	 * @return Lista de series {@link SerieVO}
	 */
	public List getSeriesXGestor(String idGestor, int[] estados);

	public List getSerieXProcedimiento(String idProcedimiento);

	public List getSerieXProcedimientos(String[] idProcedimiento);

	public SerieVO getSerieXCodigo(String codigoSerie, String idFondo);

	public List findSeries(List searchConditions);

	public List findSeriesInEstados(List searchConditions, int[] estados);

	public List findSeriesInEstados(List searchConditions, int[] estados,
			String[] idsSerie, boolean alwaysAddIdsSerie);

	/**
	 * Obtiene la lista de series valorables.
	 * 
	 * @param searchConditions
	 *            Condiciones de la búsqueda.
	 * @param estadosSerie
	 *            Estados de la serie.
	 * @return Lista de series.
	 */
	public List findSeriesValorables(List searchConditions, int[] estadosSerie);

	/**
	 * Obtiene la lista de series seleccionables.
	 * 
	 * @param searchConditions
	 *            Condiciones de la búsqueda.
	 * @param estadosSerie
	 *            Estados de la serie.
	 * @return Lista de series.
	 */
	public List findSeriesSeleccionables(List searchConditions,
			int[] estadosSerie);

	/**
	 * Elimina una serie de la tabla de series en la base de datos
	 * 
	 * @param idSerie
	 *            Identificador de la serie a eliminar
	 */
	public void removeSerie(String idSerie);

	/**
	 * Actualiza en la base de datos los datos de identificacion de una serie
	 * 
	 * @param idSerie
	 *            Identificador de la serie cuya identificacion se actualiza
	 * @param identificacion
	 *            Texto XML con la identificacion de la serie
	 * @param procedimiento
	 *            Procedimiento que ha sido vinculado a la serie
	 * @param tipoProcedimiento
	 *            Tipo de procedimiento (AUTOMATIZADO o NO AUTOMATIZADO)
	 */
	public void updateIdentificacion(String idSerie, String identificacion,
			String procedimiento, int tipoProcedimiento);

	/**
	 * Actualiza la columna de la tabla de series en la que se especifica el
	 * gestor de la serie
	 * 
	 * @param idSerie
	 *            Identificador de la serie a actualizar
	 * @param idGestor
	 *            Identificador del usuario que se establece como gestor de la
	 *            serie
	 */
	public void updateGestor(String idSerie, String idGestor);

	/**
	 * Cuenta el número de series que contiene un fondo del cuadro de
	 * clasificación de fondos
	 * 
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Número de series que contiene el fondo
	 */
	public int countSeriesEnFondo(String idFondo);

	/**
	 * Actualiza los valores establecidos para las opciones de almacenamiento y
	 * descripción de una serie del cuadro de clasificacion de fondos
	 * documentales
	 * 
	 * @param Identificador
	 *            de serie del cuadro de clasificación de fondos documentales
	 * @param fichaDescripcionSerie
	 *            Identificador de la definición de ficha descriptiva a utilizar
	 *            para la descripción de la serie
	 * @param idRepEcmSerie
	 *            Identificador del repositorio ECM en el que se guardarán los
	 *            documentos anexados a la serie
	 * @param fichaDescripcionUdoc
	 *            Identificador de la definición de ficha descriptiva que se
	 *            empleará por defecto para la descripción de las unidades
	 *            documentales de la serie
	 * @param clasificadoresDocumentos
	 *            Platilla a utilizar en la inicialización de la estrutura de
	 *            clasificadores para la organización de los documentos anexados
	 *            a la serie
	 */
	public void updateInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, String infoFichaUDocRepEcm);

	public void updateInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, String infoFichaUDocRepEcm, String idLCA,
			int nivelAcceso);

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param fechaInicio
	 *            Fecha a establecer como fecha extrema inicial
	 * @param fechaFin
	 *            Fecha a establecer como fecha extrema final
	 */
	public void updateFechasExtremas(String idSerie, Date fechaInicio,
			Date fechaFin);

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void updateFechasExtremas(String idSerie);

	/**
	 * Obtiene las series que están vinculadas con los procedimientos que se
	 * indican
	 * 
	 * @param procedimientoIDs
	 *            Conjunto de códigos de procedimiento
	 * @return Conjunto de pares clave - valor {@link Map.Entry} en los que como
	 *         clave aparece el identificador de procedimientos y como clave el
	 *         identificador de la serie a la que está asociado
	 */
	public Map getSeriesAsociadas(String[] procedimientoIDs);

	public List getUdocsSerie(String idPadre, int[] estados, PageInfo pageInfo,
			boolean ignorarConservadas) throws TooManyResultsException;

	public List getUdocsSerie(String idPadre, int[] estados, PageInfo pageInfo,
			boolean ignorarConservadas, boolean conNivel)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de unidades documentales de la serie cuyos id coinciden
	 * 
	 * @param idsElementos
	 *            Identificador de los elementos
	 * @return Lista de {@link UnidadDocumental}
	 */
	public List getUdocsSerieByIdsElementos(String idSerie,
			String[] idsElementos, boolean conservadas);

	// /**
	// * Obtiene la lista de unidades documentales conservadas de la serie cuyos
	// id coinciden
	// * @param idsElementos Identificador de los elementos
	// * @return Lista de {@link UnidadDocumental}
	// */
	// public List getUdocsSerieConservadas(String[] idsElementos);

	/**
	 * Permite obtener las unidades documentales de una serie con su nivel
	 * 
	 * @param idSerie
	 *            Id de la serie
	 * @param pageInfo
	 *            información de paginación
	 * @param idDescrProductor
	 *            identificador del productor por el que se busca
	 * @return Lista de unidades documentales
	 * @throws TooManyResultsException
	 */
	public List getUdocsSerieConFechasExtremas(ServiceClient serviceClient,
			String idSerie, PageInfo pageInfo, BusquedaUdocsSerieVO busquedaVO)
			throws TooManyResultsException;

	/**
	 * Recupera los identificadores de los procedimientos que han sido
	 * vinculados a alguna serie
	 * 
	 * @return Lista de identificadores de procedimiento
	 */
	public List getProcedimientosIdentificados();

	/**
	 * Obtiene las series que contienen documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series ({@link SerieDocAntVO}).
	 */
	public List getSeriesDocumentosAntecedentes(String idTercero);

	/**
	 * @param serie
	 */
	public void updateSerie(SerieVO serie);

	void updateSeriesFondo(List ids);

	public void setEstadoSerie(String idSerie, int estado, int estadoPrevio,
			Date fechaEstado);

	public Date getFechaInicialExtremaUdocsSerie(String idSerie);

	public Date getFechaFinalExtremaUdocsSerie(String idSerie);

	public List getProductoresUdocsSerie(ServiceClient serviceClient,
			String idSerie) throws TooManyResultsException;

	public int getCountProductoresUdocsSerie(ServiceClient serviceClient,
			String idSerie);

	/**
	 * Permite obtener el número de unidades documentales de una serie y
	 * productor.
	 * 
	 * @param idPadre
	 *            Id de la serie
	 * @param idProductor
	 *            Identificador del Productor.
	 * @return Número de unidades documentales
	 */
	public int getCountUdocsSerieByProductor(String idSerie, String idProductor);

	public void updateVolumenSerie(String idSerie, int numUdocs, int numUIs,
			double volumen);

}
