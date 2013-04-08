package common.bi;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import valoracion.exceptions.EliminacionActionNotAllowedException;
import valoracion.vos.BusquedaVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.HistoricoUDOCVO;
import valoracion.vos.UnidadDocumentalEliminacionVO;
import valoracion.vos.UnidadesDocumentalesEliminacionVO;

import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

import fondos.vos.TablaTemporalFondosVO;

/**
 * Bussines Interface con los metodos de negocio para el modulo de eliminación
 * de series.
 */
public interface GestionEliminacionBI {
	/**
	 * Obtiene las eliminaciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getEliminaciones(BusquedaVO busqueda)
			throws TooManyResultsException;

	/**
	 * Obtiene la eliminacion actual asociada a una valoración de una serie
	 * 
	 * @param idValoracion
	 *            Identificador de la valroacion de la que deseamos obtener la
	 *            eliminacion
	 * @return Datos de la eliminacion
	 */
	public EliminacionSerieVO getEliminacionValoracion(String idValoracion);

	/**
	 * Pone una eliminacion a disposicion del usuario que solicita su apertura
	 * de manera que unicamente dicho usuario puede realizar acciones sobre la
	 * eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de eliminacion
	 * @return Datos de eliminacion
	 */
	public EliminacionSerieVO abrirEliminacion(String idEliminacion,
			boolean mostrarResumenUinstalacion);

	/**
	 * Obtiene el número de eliminaciones para un usuario gestor dado
	 * 
	 * @param idGestor
	 *            Identificador del usuario gestor de las eliminaciones
	 * @return Número de eliminaciones
	 */
	public int getCountEliminacionesEnElaboracion(String idGestor,
			String[] idsArchivo);

	/**
	 * Obtiene un listado de las eliminacion para un usuario gestor dado
	 * 
	 * @param idGestor
	 *            Identificador del usuario gestor de las eliminaciones
	 * @param idsArchivo
	 *            Identificadores de los archivos a los que pertenece el usaurio
	 * @return Listado de las eliminaciones
	 */
	public List getEliminacionesEnElaboracion(String idGestor,
			String[] idsArchivo);

	/**
	 * Inicia una seleccion de una serie.
	 * 
	 * @param eliminacion
	 *            Información de la eliminación.
	 * @return Selección de la serie.
	 * @throws EliminacionActionNotAllowedException
	 */
	public EliminacionSerieVO iniciarSeleccionSerie(
			EliminacionSerieVO eliminacion)
			throws EliminacionActionNotAllowedException;

	/**
	 * Obtiene un listado de las eliminacion de una valoracion dada por su
	 * identificador y cuyo estado de eliminacion se encuentra en unos de los
	 * pasados, o todas las eliminaciones en caso de no venir definidos los
	 * estados.
	 * 
	 * @param codigoValoracion
	 *            Valoracion de la que deseamos obtener las eliminaciones.
	 * @param estados
	 *            Listado de los estado que deben tener las eliminacion o null
	 *            si deseamos obtener todas
	 * @return Listado de eliminaciones de la valoracion
	 */
	public List getEliminacionesValoracion(String idValoracion, int[] estados);

	/**
	 * Obtiene una eliminacion de una valoración a partir de su identificador en
	 * la base de datos.
	 * 
	 * @param codigo
	 *            Identificador de la eliminacion en la base de datos
	 * @return Objeto {@link EliminacionSerieVO}con los detalles de la
	 *         eliminacion.
	 */
	public EliminacionSerieVO getEliminacion(String codigo);

	/**
	 * Realiza la actualizacion de una eliminacion dada
	 * 
	 * @param eliminacion
	 *            Eliminacion que se desea actualiza
	 * @return eliminacion con los datos actualizados
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede realizar la actualizacion de la eliminacion
	 */
	public EliminacionSerieVO actualizarEliminacion(
			EliminacionSerieVO eliminacion)
			throws EliminacionActionNotAllowedException;

	/**
	 * Realiza la actualizacion de los criterios de una eliminacion dada
	 * 
	 * @param eliminacion
	 *            Eliminacion que se desea actualizar sus criterios
	 * @return eliminacion con los datos actualizados
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede realizar la actualizacion de la eliminacion
	 */
	public EliminacionSerieVO actualizarCriteriosEliminacion(
			EliminacionSerieVO eliminacion)
			throws EliminacionActionNotAllowedException;

	/**
	 * Realiza el proceso de solicitud de aprobacion de una eliminacion
	 * 
	 * @param idEliminacion
	 *            Indetificador de la eliminación de la que deseamos realizar su
	 *            solicitud
	 * @param fechaEjecucion
	 *            Fecha de ejecución de la eliminación
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede realizar la solicitud
	 */
	public void solicitarAprobacionEliminacion(String idEliminacion,
			Date fechaEjecucion) throws EliminacionActionNotAllowedException;

	/**
	 * Realiza el proceso de aprobación de una eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion que deseamos aprobar
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se pueder aprobar la eliminación
	 */
	public void aprobarEliminacion(String idEliminacion)
			throws EliminacionActionNotAllowedException;

	/**
	 * Realiza el proceso de rechazo de una eliminación
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion que deseamos rechazar
	 * @param motivo
	 *            Motivo del rechazo de la eliminación
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede rechazar la eliminación
	 */
	public void rechazarEliminacion(String idEliminacion, String motivo)
			throws EliminacionActionNotAllowedException;

	/**
	 * Borra una eliminacion existente de la base de datos
	 * 
	 * @param ids
	 *            Listado de los identificadores de la eliminaciones que
	 *            deseamos borrar.
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se pueder realizar el borrado de la eliminación
	 */
	public void eliminarEliminaciones(String[] ids)
			throws EliminacionActionNotAllowedException;

	/**
	 * Obtiene un listado de las eliminaciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las eliminaciones que deseamos
	 *            recuperar.
	 * @return Eliminaciones deseadas
	 */
	public List getEliminaciones(String[] ids);

	/**
	 * Realizar el proceso de eliminación fisica de las unidades documentales
	 * asociadas a una eliminacion mediante la tabla asociada o la condicion de
	 * busqueda
	 * 
	 * @param id
	 *            Identificador de la eliminacion que deseamos ejecutar
	 * @return listado de las unidades que se pueden destruir
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede ejecutar la eliminacion
	 */
	public List ejecutarEliminacion(String id)
			throws EliminacionActionNotAllowedException;

	/**
	 * Realiza el proceso de destrucción fisica asociado a al eliminación
	 * 
	 * @param id
	 *            Identificador de la eliminación de la que se desea registrar
	 *            su destrucción física
	 * @param fechaEliminacion
	 *            Fecha en que se produjo la eliminación física
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede realizar el proceso de destrucción
	 */
	public void ejecutarDestruccionFisica(String id, Date fechaEliminacion)
			throws EliminacionActionNotAllowedException;

	/**
	 * Obtiene las unidades documentales a conservar en una eliminacion
	 * 
	 * @param idEliminacion
	 *            Id de eliminación de la que se quieren obtener las unidades
	 *            documentales
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getUnidadesEliminacionAConservar(String idEliminacion);

	/**
	 * Permite insertar unidades de eliminación a conservar
	 * 
	 * @param idEliminacion
	 *            Id de eliminación
	 * @param idsUdoc
	 *            Ids de unidades documentales
	 * @param Lista
	 *            de códigos de referencia con unidades sin fechas
	 */
	public List insertUnidadesEliminacionAConservar(String idEliminacion,
			String[] idsUdoc);

	/**
	 * Permite eliminar unidades documentales a conservar
	 * 
	 * @param idEliminacion
	 *            Id de eliminación
	 * @param idsUdoc
	 *            Ids de unidades documentales
	 */
	public void eliminarUnidadesEliminacionAConservar(String idEliminacion,
			String[] idsUdoc);

	/**
	 * Devuelve el listado de unidades documentales que se verán afectadas por
	 * el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO}afectadas por el
	 *         proceso de eliminacion
	 */
	// public List getUnidadesEliminacion(String idEliminacion, String
	// idArchivo);

	/**
	 * Devuelve el listado de unidades documentales que no se verán afectadas
	 * por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO}no afectadas por el
	 *         proceso de eliminacion
	 */
	public Collection getUnidadesConservacion(String idEliminacion,
			String idArchivo);

	/**
	 * Devuelve el listado de unidades documentales procesadas en la
	 * eliminación.
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales {@see HistoricoUDOCVO}
	 *         eliminadas.
	 */
	public List getUnidadesEliminadas(String idEliminacion);

	/**
	 * Devuelve el listado de unidades documentales que se verán afectadas por
	 * el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @param idArchivo
	 *            al que pertenecen
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} afectadas por el
	 *         proceso de eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public UnidadesDocumentalesEliminacionVO getUnidadesEliminacion(
			String idEliminacion, String idArchivo, PageInfo pageInfo,
			boolean addMarcaCajas);

	public UnidadesDocumentalesEliminacionVO getUnidadesEliminacion(
			EliminacionSerieVO eliminacion, String idArchivo,
			PageInfo pageInfo, boolean addMarcaCajas);

	/**
	 * Devuelve el listado de unidades documentales que no se verán afectadas
	 * por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @param idArchivo
	 *            al que pertenecen
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} no afectadas por el
	 *         proceso de eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getUnidadesConservacion(String idEliminacion,
			String idArchivo, PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Devuelve el listado de unidades documentales relacionadas que se verán
	 * afectadas por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} afectadas por el
	 *         proceso de eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getUnidadesRelEliminacion(String idEliminacion,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Realiza la creacion de un historico de una unidad documental que ha sido
	 * eliminada
	 * 
	 * @param historico
	 *            historioco de la unidad documental a guardar en bd
	 */
	public void crearHistoricoUDOC(HistoricoUDOCVO historico);

	/**
	 * Obtiene un listado de los posibles estados en que se puede encontrar una
	 * eliminacion
	 * 
	 * @return Listado de los posibles estados de una eliminacion
	 */
	public List getEstadosEliminacion();

	/**
	 * Obtiene la eliminación en curso sobre una serie determinada caso de
	 * existir
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Datos de eliminación {@link EliminacionSerieVO}
	 */
	public EliminacionSerieVO getEliminacionSerie(String idSerie);

	/**
	 * Obtiene las unidades documentales seleccionables de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de la serie.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de unidades documentales (
	 *         {@link UnidadDocumentalEliminacionVO}).
	 * @throws TooManyResultsException
	 *             si se excede el número máximo de resultados.
	 */
	public List getUdocsSeleccionables(String idSerie, PageInfo pageInfo)
			throws SerieNoValoradaException, TooManyResultsException;

	/**
	 * Devuelve una subconsulta de los ids de las unidades documentales a
	 * eliminar en una selección
	 * 
	 * @param eliminacion
	 *            Eliminacion
	 * @return Subconsulta para obtener los ids de las unidades documentales a
	 *         eliminar
	 */
	public String getSubConsultaIDsUdocsAEliminar(EliminacionSerieVO eliminacion);

	/**
	 * Devuelve los ids de las unidades documentales relacionadas con las
	 * unidades a eliminar en una selección
	 * 
	 * @param eliminacion
	 *            Eliminacion
	 * @return Array de String con los ids de las unidades documentales a
	 *         eliminar
	 */
	public String[] getIDsUdocsRelAEliminar(EliminacionSerieVO eliminacion);

	/**
	 * Obtiene los identificadores de de las unidades de instalación que se
	 * eliminarán parcialmente
	 * 
	 * @param idEliminacion
	 *            Cadena que contiene el identificador de la eliminación
	 * @param idArchivo
	 *            Cadena que contiene el identificador del archivo
	 * @return {@link HashMap} Key/Value: Cadena que contiene el identificador
	 *         de la unidad documental
	 */
	public HashMap getUInstalacionParciales(List listaUdocs);

	/**
	 * Comprueba si alguna de las unidades documetales de la tabla temporal
	 * están en alguna eliminación no eliminada
	 * 
	 * @param tablaTemporalUDocs
	 *            Datos de la tabla temporal
	 * @return
	 */
	public boolean hasUnidadesDocumentalesEnSeleccionSinFinalizar(
			TablaTemporalFondosVO tablaTemporalUDocs);

}