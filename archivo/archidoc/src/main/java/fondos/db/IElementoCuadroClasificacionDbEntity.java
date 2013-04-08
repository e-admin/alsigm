package fondos.db;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.usuarios.ServiceClient;
import xml.config.Busqueda;

import common.db.IDBEntity;
import common.db.VOList;
import common.exceptions.TooManyResultsException;
import common.util.CustomDate;
import common.util.CustomDateRange;
import common.vos.ConsultaConnectBy;

import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.model.NivelAcceso;

/**
 * Métodos de acceso a datos almacenados en la tabla <b>ASGFSERIE</b>, en la que
 * se mantienen las series del cuadro de clasificación de fondos documentales. <br>
 * Entidad: <b>ASGFELEMENTOCF</b>
 */
public interface IElementoCuadroClasificacionDbEntity extends IDBEntity {
	/**
	 * Obtiene los hijos de un determinado tipo de un elemento. (Obtencion
	 * recursiva, recoge todos los hijos)
	 *
	 * @param idElementoCuadro
	 * @param tipoElemento
	 * @return
	 */
	public List getIdsHijos(String idElementoCuadro, int tipoElemento);

	public List getElementosCuadroClasificacion(String idPadre, int[] estados);

	public IElementoCuadroClasificacion getElementoCuadroClasificacion(
			String idElemento);

	public IElementoCuadroClasificacion getElementoCuadroClasificacionConFechas(
			String idElemento);



	/**
	 * Obtiene la información del elementod el cuadro de clasificación.
	 *
	 * @param codReferencia
	 *            Código de referencia
	 * @return Un elemento del cuadro por el código de referencia.
	 */
	public ElementoCuadroClasificacionVO getElementoCuadroClasificacionXCodReferencia(
			String codReferencia);

	public ElementoCuadroClasificacionVO getElementoCFXCodigo(String codigo,
			String idPadre);

	/**
	 * Permite obtener un elemento del cuadro de clasificación a partir de su
	 * código
	 *
	 * @param codigo
	 *            Código a buscar
	 * @return ElementoCuadroClasificacionVO
	 */
	public ElementoCuadroClasificacionVO getElementoCFXCodigo(String codigo);

	public IElementoCuadroClasificacion insertElementoCF(
			ElementoCuadroClasificacionVO elementoVO);

	public void updateElementoCF(String idElementoCF, String nivel,
			String codigo, String denominacion, String codOrdenacion);

	/**
	 * Cuenta el numero de elementos que cuelgan de un determino elemento del
	 * cuadro de clasificación
	 *
	 * @param idElementoCF
	 *            Identficador de elemento del cuadro de clasificacion
	 * @return Numero de descendientes que dependen directamente del elemento
	 *         suministrado
	 */
	public int countNumChilds(String idElementoCF);

	/**
	 * Obtiene el campo marcasBloqueo de un determinado elemento por su
	 * identificador
	 *
	 * @param idElementoCF
	 * @return
	 */
	public int getMarcasBloqueoElemento(String idElementoCF);

	public void updateEstadoElementoCF(String id, final int estado);

	public void updateTieneDescrElementoCF(String id, String tieneDescr);

	public void updateEditClfDocsElementoCF(String id, String editClfDocs);

	public void deleteElementoCF(String id);

	public void deleteElementosCFXIdFondoExceptFondo(String idFondo);

	public void updateCodRefFondoElementoCF(final String idFondo,
			final String newCodRefFondo);

	public void updateCodReferenciaCF(String id, String newCodRef);

	public void updatePadreCodRefUnidadesDocumentales(final String idPadre,
			final String idFondo, final String codRefFondo,
			final String codRefPadre,
			final TablaTemporalFondosVO tablaTemporalFondosVO) throws Exception;

	public String getCodRefElementoCfFunction(final String id);

	public void getElementosCFXTipoYFondo(final int tipo, final String idFondo,
			VOList elementosCuadro);

	public List getElementosCFXIdFondo(final String idFondo);

	public void getElementos(final String qual, final VOList list);

	/**
	 * Comprueba si entre los descendientes de un elemento en el cuadro de
	 * clasificación existe uno que tenga el código que se indica
	 *
	 * @param idPadre
	 *            Identificador del elemento entre cuyos descendientes se debe
	 *            realizar la comprobación
	 * @param codigo
	 *            Código cuya existencia se quiere verificar
	 */
	public boolean existeElementoConCodigo(String idPadre, String codigo);

	public abstract List getElementosCFXTipo(String idParent, int tipoElemento);

	public abstract List getElementosCFXNivel(String[] niveles, String idFondo);

	public abstract int getCountElementosCFByNivel(String nivel);

	public abstract List getElementosCFXLCAcceso(String idListaControlAcceso);

	// TODO REVISAR ESTO NO DEBERIA EXISTIR YA HACE QUE EL SERVICIO DEPENDA DE
	// LA IMPLEMNTACION Q TIEEN LAS DEF DE COLUMNAS
	public abstract void updateFieldsECF(String idElementoCF,
			final Map columnsToUpdate);

	public abstract void updateElementoCFVEA(
			ElementoCuadroClasificacionVO elemento);

	/**
	 * Obtiene el elemento del cuadro por su código de referencia y su tipo
	 *
	 * @param codigo
	 *            Código de Referencia
	 * @param tipo
	 *            Tipo
	 * @return {@link ElementoCuadroClasificacionVO}
	 */
	public ElementoCuadroClasificacionVO getElementoCFXCodigoYTipo(
			String codigo, String tipo);

	/**
	 * Obtiene la lista de elementos que cumplan los requisitos de la búsqueda.
	 *
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @return Lista de elementos del cuadro de clasificación.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	/*
	 * public List getElementosCuadroClasificacion(ServiceClient serviceClient,
	 * BusquedaGeneralVO busqueda) throws TooManyResultsException;
	 */

	public List getElementosCuadroClasificacion(ServiceClient serviceClient,
			BusquedaElementosVO busqueda) throws TooManyResultsException;

	public UpdateCodRefsReturns updateCodRefElementoYHijos(String idElemento,
			String separador, boolean updateOtherTables);

	public String composeFinalCodRefPadre(INivelCFVO tipoNivelPadre,
			String finalCodRefDelPadre, String codigoDelPadre);

	public String composeCodigoReferencia(INivelCFVO nivelPadre,
			String finalCodRefPadreDelPadre, String codigoDelPadre,
			String codRefFondo, String codigoElemento);

	public List getElementosXNivelYPadre(String idNivelCFVO, String idPadre);

	/**
	 * Recupera la lista de antecesares jerarquicos de un elemento en el cuadro
	 * de clasificacion de fondos
	 *
	 * @param idElemento
	 *            Identificador de elemento del cuadro de clasificacion cuya
	 *            jerarquia se va a obtener
	 * @return Lista de elementos del cuadro de clasificacion
	 *         {@link ElementoCuadroClasificacionVO} que forman parte de la
	 *         jerarquia del elemento. El primer elemento de la lista sera uno
	 *         de los elementos raiz del arbol de clasificacion de fondos y el
	 *         ultimo sera el padre directo del que cuelga el elemento del
	 *         cuadro identificado por el parametro idElemento.
	 */
	public List getAncestors(String idElemento);

	public boolean hasHermanosConMismoCodigo(String idElemento,
			String idNivelElemento, String nuevoCodigo, String idNuevoPadre);

	boolean hasHermanosConMismoCodigo(TablaTemporalFondosVO tablaTemporal,
			String idNuevoPadre);

	/**
	 * Permite actualizar el estado de eliminacion de una unidad documental
	 *
	 * @param id
	 *            Identificador de la unidad
	 * @param estado
	 *            Estado
	 * @param idEliminacion
	 *            Identificador de la eliminacion
	 */
	public abstract void updateEstadoEliminacionElementoCF(String id,
			final int estado, String idEliminacion);

	/**
	 * Permite actualizar el estado de eliminacion de varias unidades
	 * documentales
	 *
	 * @param ids
	 *            Identificadores de las unidades documentales
	 * @param estado
	 *            Estado
	 * @param idEliminacion
	 *            Identificador de la eliminacion
	 */
	public void updateEstadoEliminacionElementoCF(String[] ids,
			final int estado, String idEliminacion);

	/**
	 * Permite actualizar el estado de eliminacion de varias unidades
	 * documentales
	 *
	 * @param subqueryIds
	 *            subquery de las unidades documentales a actualizar
	 * @param estado
	 *            Estado
	 * @param idEliminacion
	 *            Identificador de la eliminacion
	 */
	public abstract void updateEstadoEliminacionElementoCFSubquery(
			String subqueryIds, final int estado, String idEliminacion);

	/**
	 * Busca los elementos del cuadro que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de elementos del cuadro.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getElementos(final BusquedaElementosVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene las fechas extremas de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Fechas extremas.
	 */
	public CustomDateRange getFechasExtremasClasificadorSeries(
			String idClfSeries);

	public ElementoCuadroClasificacionVO getElementoPadre(String idElemento);

	/**
	 * Modifica en la base de datos el titulo de un elemento del cuadro de
	 * clasificacion
	 *
	 * @param idElemento
	 *            Identificador del elemento al que se modifica el titulo
	 * @param denominacion
	 *            Nuevo titulo a establecer
	 */
	public void updateTitulo(String idElemento, String denominacion);

	/**
	 * Actualiza el valor de la columna en la que se mantiene el identificador
	 * del elemento padre de un elemento del cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param idElementoCF
	 *            Identificador de elemento del cuadro de clasificación de
	 *            fondos
	 * @param idNuevoPadre
	 *            Nuevo valor para la columna en la que se almacena el valor del
	 *            padre del elemento del cuadro de clasificación
	 */
	public void setElementoPadre(String idElementoCF, String idNuevoPadre);

	public void setListaControlAcceso(String idSerie,
			String idListaControlAcceso);

	public void updateElementoCuadroClasificacion(
			ElementoCuadroClasificacionVO elementoCuadroClasificacion);

	/**
	 * Obtiene la lista de volúmenes de las series de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de volúmenes de las series de un clasificador de series.
	 */
	public List getVolumenesSeriesClasificadorSeries(String idClfSeries);

	/**
	 * Actualiza la información de control de acceso de un elemento.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	public void updateControlAcceso(String idElemento, int nivelAcceso,
			String idListaControlAcceso);

	/**
	 * Actualiza la información de control de acceso de los elementos.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	public void updateControlAcceso(
			TablaTemporalFondosVO tablaTemporalFondosVO, int nivelAcceso,
			String idListaControlAcceso);

	public List getElementosCuadroClasificacion(
			TablaTemporalFondosVO tablaTemporal);

	// public List getElementosCuadroClasificacion(String[] ids,
	// BusquedaElementosVO busquedaElementosVO)throws TooManyResultsException;
	// public List getElementosCuadroClasificacionBRF(String [] ids,
	// BusquedaElementosVO busquedaElementosVO) throws TooManyResultsException;
	// public List getElementosCuadroClasificacionBRB(String [] ids,
	// BusquedaElementosVO busquedaElementosVO) throws TooManyResultsException;
	public List getElementosCuadroClasificacion(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException;

	// public List getElementosCuadroClasificacion(String[] ids,
	// BusquedaGeneralVO busquedaGeneralVO) throws TooManyResultsException;

	public List getIdsHijos(String idElementoCuadro);

	// public List getIdsElementos(final BusquedaElementosVO vo, int
	// numMaxResultados) throws TooManyResultsException;
	public List getIdsElementos(final BusquedaElementosVO vo,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException;

	// public List getIdsElementos(final BusquedaGeneralVO vo, int
	// numMaxResultados) throws TooManyResultsException;

	/**
	 * Obtiene la lista de productores de un elemento del cuadro de
	 * clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampo
	 *            Identificador del campo de productor
	 * @return Lista de productores.
	 */
	public List getProductoresElementoCF(String idElementocf, String idCampo);

	/**
	 * Obtiene la lista de rangos de un elemento del cuadro de clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosElementoCF(String idElementocf, String idCampoInicial,
			String idCampoFinal);

	public List getElementosCFXNivelYFondoYCodigoYTitulo(String[] niveles,
			String idFondo, String codigo, String titulo,
			boolean conCriterioEstado);

	/**
	 * Obtiene los distintos archivos por id. Este método se utilza para
	 * comprobar no permitir eliminar el arhivo si existe en elementos del
	 * cuadro.
	 *
	 * @param idArchivo
	 * @return List de ElementoCuadroClasificacion.class
	 */
	public List getDistinctIdsArchivo(String idArchivo);

	/**
	 * Obtiene las unidades documentales de acuerdo con los parametros pasados.
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idSerieOrigen
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idFormato
	 * @param idCampoFechaExtremaFinal
	 * @return Lista de ids de las unidades documentales
	 */

	/**
	 * Obtener los ids de elementos que cumplan las condiciones de búsqueda
	 * incluyendo uno por cada una de sus partes en caso de que las tuviesen
	 *
	 * @param vo
	 * @param numMaxResultados
	 * @param busqueda
	 * @return
	 * @throws TooManyResultsException
	 */
	public List getIdsElementosYPartes(BusquedaElementosVO busquedaElementosVO,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException;

	public boolean isAccesibleWithPermissions(ServiceClient serviceClient,
			String idElemento);

	/**
	 * Obtiene la cadena con la asignacion, campo = valor para el set del update
	 *
	 * @param vo
	 * @param numMaxResultados
	 * @param vo
	 * @return
	 */
	ConsultaConnectBy getSQLIdsElementos(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda,
			boolean onlyUdocs);

	// String getSQLIdsPadresElementos(BusquedaElementosVO busquedaElementosVO,
	// Busqueda busqueda);
	ConsultaConnectBy getSQLPadresEIdsElementos(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda);

	int updateCampoGenerico(ConsultaConnectBy subqueryForIdsElementos,
			int tipoCampo, String idCampo, String valorAntiguo,
			String valorNuevo, String formatoRangoFechas,
			String condicionValor, boolean soloValoresNulos,
			Integer tipoReferencia);

	int updateCampoGenerico(List idsElementos, int tipoCampo, String idCampo,
			String valorAntiguo, String valorNuevo, String formatoRangoFechas,
			String condicionValor, boolean soloValoresNulos,
			Integer tipoReferencia);

	boolean checkUpdateFechaIniFin(ConsultaConnectBy subqueryIdsElementos,
			Date fechaIni, String idCampo, boolean isFechaIni);

	boolean checkUpdateFechaIniFin(List idsElementos, Date fecha,
			String idCampo, boolean isFechaIni);

	boolean checkUpdateSerieFechaIniFin(ConsultaConnectBy subqueryIdsUdocs,
			Date fecha, String idCampo, boolean isFechaIni);

	List getSeriesAfectadasFechaIniFin(ConsultaConnectBy subqueryIdsUdocs,
			Date fecha, String idCampo, boolean isFechaIni, boolean onlyCount);

	int updateSerieAfectadasFechaIniFin(ConsultaConnectBy subqueryIdsUdocs,
			CustomDate fecha, String idCampo, boolean isFechaIni);

	int getCountElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda);

	// List getElementosVO(BusquedaElementosVO busquedaElementosVO, Busqueda
	// busqueda);

	public List getElementosCuadroClasificacionYPartes(String[] idsYSignaturas,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException;

	List checkUdocsInRangeNewFechasSeries(
			ConsultaConnectBy subqueryIdsElementos, Date fecha, String idCampo,
			boolean isFechaIni);

	String getCondicionValorCampo(BusquedaElementosVO vo);

	public void updateElementosXIdPadreYNivel(String idPadre, String idNivel,
			Map colsToUpdate);

	List getIdsElementosBloqueados(List ids);

	List getElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda);

	/**
	 * Permite comprobar que varios elementos del cuadro pertenezcan al mismo
	 * fondo
	 *
	 * @param ids
	 *            Identificadores de los elementos del cuadro
	 * @param idFondo
	 *            Identificador del fondo
	 * @return <b>true</b> Si todos los elementos pertenecen al mismo fondo<br>
	 *         <b>false</b> Si los elementos pertenecen a distintos fondos
	 */
	public boolean checkUdocsEnMismoFondo(List ids, String idFondo);

	/**
	 * Permite comprobar que varias unidades del cuadro, pertenezcan todas al
	 * mismo archivo.
	 *
	 * @param ids
	 *            Array que contiene los ids de las unidades documentales.
	 * @return
	 */
	public boolean checkUdocsEnMismoArchivo(String[] ids);

	/**
	 * Comprueba que los elementos seleccionados sean del mismo tipo.
	 *
	 * @param ids
	 * @return
	 */
	public boolean checkElementosMismoTipo(String[] ids);

	/**
	 * Comprueba si los padres de los elementos seleccionados son distintos
	 * siempre que no este permitido seleccionar distintos padres.
	 *
	 * @param ids
	 * @param permitidoDistintoPadre
	 * @return
	 */
	public boolean checkElementosDistintoPadre(String[] ids,
			boolean permitidoDistintoPadre);

	List getElementosCuadroClasificacionWithPermissions(
			ServiceClient serviceClient, String idPadre, int[] estados);

	public List getFondosWithPermissions(ServiceClient serviceClient,
			int[] estados);

	boolean checkAccesibilidadElementoWithPermissions(String idElemento,
			ServiceClient serviceClient, Object[] permisos);

	boolean checkPermisosAccesibilidadElementoYPadres(String idElemento,
			ServiceClient serviceClient, Object[] permisos) throws Exception;

	List getIdsElementosCampoVacioFicha(List idsElementos, int tipoCampo,
			String idCampo);

	List getNombresNiveles(List idsElementos);

	HashMap getPairsIdCodigo(List idsElementos);

	HashMap getPairsIdCodRefPadre(List idsElementos);

	List getNumUdocsLongFormatoMovimiento(String idSerieOrigen,
			String idSerieDestino, List idsUIs,
			String[] aliasCamposDevueltosConsulta);

	List getNombresNiveles(TablaTemporalFondosVO tablaTemporalFondosVO);

	HashMap getPairsIdCodRefPadre(TablaTemporalFondosVO tablaTemporalFondosVO);

	/**
	 * Obtiene los ElementosCFVO con los ids especificados
	 *
	 * @param ids
	 *            Identificadores de las unidades documentales
	 * @return Lista de ElementosCFVO
	 */
	public List getElementosCFVOByIds(String[] ids);

	/**
	 * Elimina las unidades en el cuadro de clasificación de la relación
	 *
	 * @param idsUdoc
	 */
	public void deleteElementosCFRelacion(String[] idsUdoc);

	/**
	 * @param Identificadores
	 * @return
	 */
	public List getElementosCuadroClasificacion(String[] ids);

	/**
	 * @param ids
	 */
	public void removeIdEliminacionElementoCF(String[] ids);

}
