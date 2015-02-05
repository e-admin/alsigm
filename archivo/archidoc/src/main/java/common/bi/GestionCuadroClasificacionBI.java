package common.bi;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.usuarios.ServiceClient;
import xml.config.Busqueda;

import common.exceptions.TooManyResultsException;
import common.util.CustomDateRange;

import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.model.CuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.BusquedaUdocsVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.JerarquiaNivelCFVO;
import fondos.vos.INivelCFVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.model.NivelAcceso;
import gcontrol.vos.ArchivoVO;

/**
 * Interface del servicio del cuadro de clasificacion
 *
 */
public interface GestionCuadroClasificacionBI extends IServiceBase {

	/**
	 * @return Obtiene la estructura de nodos del arbol del cuadro de
	 *         clasificacion
	 */
	public List getRootNodes();

	/**
	 * @return Obtiene la estructura de nodos del arbol del cuadro de
	 *         clasificacion
	 */
	public CuadroClasificacion getCuadroClasificacion();

	/**
	 *
	 * @param idElemento
	 * @return Un elemento del cuadro por id
	 */
	public ElementoCuadroClasificacionVO getElementoCuadroClasificacion(
			String idElemento);

	public List getAncestors(String idElemento);

	/**
	 *
	 * @param idListaControlAcceso
	 * @return Lista de elementoCF cuyo idLCA sea igual al pasado por parametro
	 */
	public List getElementoCuadroClasificacionXListaControlAcceso(
			String idListaControlAcceso);

	/**
	 * Obtiene la información del elementod el cuadro de clasificación.
	 *
	 * @param codReferencia
	 *            Código de referencia
	 * @return Un elemento del cuadro por el código de referencia.
	 */
	public ElementoCuadroClasificacionVO getElementoCuadroClasificacionXCodReferencia(
			String codReferencia);

	/**
	 * Obtiene los elementos del cuadro de clasificacion de un determinado nivel
	 * o conjunto de niveles
	 *
	 * @param nivelesID
	 */
	public List getElementosCuadroClasificacionXNivel(String[] nivelesID,
			String idFondo);

	/**
	 * Obtiene el numero de elementos del cuadro de clasificacion de un
	 * determinado nivel
	 *
	 * @param idNivel
	 */
	public int getElementosCFByNivel(String idNivel);

	/**
	 *
	 * @param elementoCF
	 * @return Los hijos del elementoCF
	 */
	public List getHijosElementoCuadroClasificacion(String idElementoCF);

	/**
	 * Muestra los Elementos del Cuadro de Clasificación.
	 *
	 * @param idElementoCF
	 * @param mostrarTemporales
	 *            Si se le pasa true, muestra todos.
	 * @return
	 */
	public List getHijosElementoCuadroClasificacion(String idElementoCF,
			boolean mostrarTemporales);

	/**
	 * Muestra los Elementos Hijos del Elemento especificado. (Solo los
	 * vigentes)
	 *
	 * @param idElementoCF
	 *            Identificador del Elemento.
	 * @return
	 */
	public List getHijosVigentesElementoCuadroClasificacion(String idElementoCF);

	/**
	 * @param idNivel
	 * @return Objeto de tipo NivelCFVO que contiene la infomracion de la tabla
	 *         ASGFNivelCF
	 */
	public INivelCFVO getNivelCF(String idNivel);

	public INivelCFVO getNivelCFById(String idNivel);

	/**
	 * Los niveles existentes son de diferente tipos (clasificador, fondo,
	 * serie, ...). Cada elemento del cuadro puede tener un determinado tipo de
	 * hijos.
	 *
	 * @param tipoNivel
	 * @param idNivelPadre
	 * @return Objetos NivelCFVO que seran los hijos de tipo tipoNivel de un
	 *         padre cuyo idNivel sea idNivelPadre
	 */
	public List getNivelesByTipo(TipoNivelCF tipoNivel, String idNivelPadre);

	public List getNivelesCFByTipo(int[] tiposHijo, List nivelesHijo);

	public List getNivelesPadre(String idNivel, int tipoNivel);

	/**
	 * Creacion de un clasificador de serie
	 *
	 * @param parent
	 * @param tipoClasificador
	 * @param codigo
	 * @param denominacion
	 * @return un clasificador de serie
	 */
	public ElementoCuadroClasificacionVO crearClasificadorSeries(
			ElementoCuadroClasificacionVO parent, String tipoClasificador,
			String codigo, String denominacion, String codOrdenacion)
			throws FondosOperacionNoPermitidaException;

	ElementoCuadroClasificacionVO crearClasificadorFondos(
			ElementoCuadroClasificacionVO parent, String tipoClasificador,
			String codigo, String denominacion, String codOrdenacion)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Actualizacion de un clasificador de serie
	 *
	 * @param elementoCF
	 *            Clasificador de series que se desea actualizar
	 * @param tipoClasificador
	 *            Tipo de clasificador
	 * @param codigo
	 *            Codigo para el clasificador
	 * @param denominacion
	 *            Denominación para el clasificador
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la actualización del clasificador no esté
	 *             permitida
	 */
	public void updateClasificadorSeries(
			ElementoCuadroClasificacionVO elementoCF, String tipoClasificador,
			String codigo, String denominacion, String codigoOld)
			throws FondosOperacionNoPermitidaException;

	void updateClasificadorFondos(ElementoCuadroClasificacionVO elementoCF,
			String tipoClasificador, String codigo, String denominacion,
			String codigoOld) throws FondosOperacionNoPermitidaException;

	// public void removeClasificadorSeries(ElementoCuadroClasificacionVO
	// elementoCF)
	// throws NotEmptyElementException;

	// public void removeClasificadorSeries(ElementoCuadroClasificacionVO
	// elementoCF)
	// throws FondosOperacionNoPermitidaException;

	/**
	 * Cambio del estado de de vigencia de un elemento del cuadro de
	 * clasificacion
	 *
	 * @param elementoCF
	 * @param b
	 */
	public void setEstadoVigencia(ElementoCuadroClasificacionVO elementoCF,
			boolean b) throws FondosOperacionNoPermitidaException;

	/**
	 * Obtiene los Elementos del cuadro de clasificacion, de un determinado
	 * tipo, que cuelgan de un fondo
	 *
	 * @param codFondo
	 * @param serie
	 * @return
	 */
	// public List getElementosFondo(String codFondo, TipoNivelCF serie);

	/**
	 * Obtiene la lista de niveles de descripción (excepto los clasificadores de
	 * fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesCF();

	/**
	 * Obtiene la lista de niveles de descripción (incluidos los clasificadores
	 * de fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getAllNivelesCF();

	public INivelCFVO insertarNivelJerarquiaCF(final INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO);

	public INivelCFVO actualizarNivelJerarquiaCF(INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO,
			boolean cambioInitialYesCheck);

	public void eliminarNivelJerarquiaCF(final INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO);

	public void deleteJerarquiaNivelCF(
			final JerarquiaNivelCFVO jerarquiaNivelCFVO);

	/**
	 * Comprueba si el nivel esta puesto en la jerarquia como nivel inicial
	 *
	 * @param idNivel
	 *            String
	 * @return boolean
	 */
	public boolean isNivelInicial(String idNivel);

	/**
	 * Comprueba si el nivel es hijo excepto si se trata del NIVEL_RAIZ
	 *
	 * @param idNivelHijo
	 * @return
	 */
	public boolean isNivelHijoNoRaiz(String idNivelHijo);

	/**
	 * Obtiene la lista de niveles de descripción.
	 *
	 * @param nivelPadre
	 *            Nivel del padre.
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesCF(String idNivelPadre);

	/**
	 * Obtiene la lista de niveles no serie de descripción.
	 *
	 * @param nivelPadre
	 *            Nivel del padre.
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesNoSerieCF(String idNivelPadre);

	/**
	 * Obtiene la lista de niveles de descripción.
	 *
	 * @param idNivelPadre
	 *            Identificador del nivel del padre.
	 * @param tipoNivel
	 *            Tipo de nivel.
	 * @return Lista de niveles de descripción.
	 */
	// Funcionalidad repetida
	// public List getNivelesCF(String idNivelPadre, int tipoNivel);

	/**
	 * Obtiene los tipos de niveles.
	 *
	 * @param idNivelPadre
	 *            Identificador del nivel padre.
	 * @return Tipos de niveles.
	 */
	public Collection getTiposSubniveles(String idNivelPadre);

	/**
	 * Borrado de un elemento del cuadro. Borrara sus datos descriptivos en el
	 * caso de que los tenga
	 *
	 * @param elementoCF
	 * @throws NotEmptyElementException
	 */
	public void removeElementoCF(ElementoCuadroClasificacionVO elementoCF)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Mueve un elemento dentro del cuadro de clasificación madificando el
	 * elemento padre del que depende
	 *
	 * @param clasificador
	 * @param nuevoPadre
	 */
	public void moverElemento(ElementoCuadroClasificacionVO clasificador,
			String nuevoPadre) throws FondosOperacionNoPermitidaException;

	/**
	 *
	 * @param idElemento
	 * @param idPadre
	 * @param codigo
	 * @return TRUE si existen hermanos del elemento con el codigo pasado por
	 *         parametro
	 */
	public boolean existHermanosConMismoCodigo(
			ElementoCuadroClasificacionVO elementoCF, String codigo);

	public void setTieneDescr(String idElementoCF, boolean tieneDescr);

	public void setEditClfDocs(String idElementoCF, boolean editClfDocs);

	/**
	 * Obtiene los estados de los elementos del cuadro.
	 *
	 * @return Estados de los elementos del cuadro.
	 */
	public List getEstadosElementos();

	/**
	 * Busca los elementos del cuadro que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de elementos del cuadro @link ElementoCuadroClasificacion
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getElementos(BusquedaElementosVO vo)
			throws TooManyResultsException;

	/**
	 * Busca de unidades documentales
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de elementos del cuadro @link fondos.model.UnidadDocumental
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getUnidadesDocumentales(BusquedaUdocsVO vo)
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

	/**
	 * Obtiene la lista de productores de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de productores.
	 */
	public List getProductoresClasificadorSeries(String idClfSeries);

	/**
	 * Obtiene la lista de volúmenes de las series de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de volúmenes de las series de un clasificador de series.
	 */
	public List getVolumenesYSoportesSeriesClasificadorSeries(String idClfSeries);

	public ElementoCuadroClasificacionVO getElementoPadre(String idElemento);

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
	 * Actualiza la información de control de acceso de un elemento.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	// public void updateControlAcceso(String[] idsElemento, int nivelAcceso,
	// String idListaControlAcceso);

	public List getElementos(String[] ids);

	public List getElementos(TablaTemporalFondosVO tablaTemporal);

	// public List getElementos(String[]ids, BusquedaElementosVO
	// busquedaElementosVO) throws TooManyResultsException;

	/*
	 * public List getElementosBRF(String[] ids, BusquedaElementosVO
	 * busquedaElementosVO)throws TooManyResultsException; public List
	 * getElementosBRB(String[] ids, BusquedaElementosVO
	 * busquedaElementosVO)throws TooManyResultsException;
	 */

	public List getElementos(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException;

	/**
	 * Mueve las unidades documentales
	 *
	 * @param unidadesDocumentales
	 *            Elementos a mover
	 * @param elementoPadre
	 *            Elemento padre destino
	 * @param idLCA
	 *            Identificador de la lista de acceso
	 * @throws FondosOperacionNoPermitidaException
	 * @throws Exception
	 */
	public void moverUnidadesDocumentales(
			TablaTemporalFondosVO tablaTemporalFondosVO,
			ElementoCuadroClasificacionVO elementoPadre, String idLCA)
			throws FondosOperacionNoPermitidaException, Exception;

	/**
	 * Devuelve una lista de Strings de los elementos del cuadro de
	 * clasificación
	 *
	 * @param BusquedaElementosVO
	 *            vo
	 * @return List
	 * @throws TooManyResultsException
	 */
	public List getIdsElementos(BusquedaElementosVO vo, int numMaxResultados,
			Busqueda busqueda) throws TooManyResultsException;

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
			String idFondo, String codigo, String titulo);

	public List getElementosCFXNivelYFondoYCodigoYTitulo(String[] niveles,
			String idFondo, String codigo, String titulo,
			boolean conCriterioEstado);

	public void updateElementoCFVEA(ElementoCuadroClasificacionVO elemento);

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
	public List getIdsElementosYPartes(BusquedaElementosVO vo,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException;

	/**
	 * Comprueba si el Archivo se existe algún elemento del cuadro con el
	 * idarchivo especificado
	 *
	 * @param idArchivo
	 *            Id del Archivo
	 * @return true si existe. false en caso contrario.
	 */
	public boolean existeArchivoEnCuadro(String idArchivo);

	String[] getNivelesSerie();

	public boolean isAccesibleWithPermissions(ServiceClient serviceClient,
			String idElemento);

	int reemplazoElementosCF(BusquedaElementosVO vo, Busqueda busqueda,
			String nombreCampoCambio, String formaReemplazo)
			throws TooManyResultsException;

	boolean checkUpdateFechasElementos(BusquedaElementosVO vo,
			Busqueda busqueda, boolean isFechaIni);

	boolean checkUpdateFechasSeries(BusquedaElementosVO vo, Busqueda busqueda,
			boolean isFechaIni);

	List getSeriesAfectadas(BusquedaElementosVO vo, Busqueda busqueda,
			boolean isFechaIni);

	int getCountElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda);

	List checkUdocsInRangeNewFechasSeries(BusquedaElementosVO vo,
			Busqueda busqueda, boolean isFechaIni);

	public List getElementosYPartes(String[] idsYSignaturas,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException;

	public boolean isSubtipoCaja(String idNivel);

	public int getSubtipo(String idNivel);

	public INivelCFVO getNivelCF(int tipo, int subtipo);

	List getNombresNiveles(List idsElementos);

	List getIdsElementosBloqueados(List ids);

	List getElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda);

	public int getTipoElemento(String idElemento);

	public boolean checkElementosMismoTipo(String[] ids);

	public boolean checkElementosDistintoPadre(String[] ids,
			boolean permitidoDistintoPadre);

	/**
	 * Comprueba que los elementos elementos pertenezcan al mismo archivo
	 *
	 * @param ids
	 * @return
	 */
	public boolean checkElementosMismoArchivo(String[] ids);

	List getHijosElementoCuadroClasificacionWithPermissions(String idElementoCF);

	public List getFondosWithPermissions();

	boolean checkPermisosElemento(String idElemento, Object[] permisos);

	boolean checkPermitidoConsultaElemento(String idElemento) throws Exception;

	List getIdsElementosCampoVacioFicha(List idsElementos, int tipoCampo,
			String idCampo);

	HashMap getPairsIdCodigo(List idsElementos);

	HashMap getPairsIdCodigo(TablaTemporalFondosVO tablaTemporalFondosVO);

	HashMap getPairsIdCodRefPadre(List idsElementos);

	public ArchivoVO getArchivoByIdElemento(String idElemento);

	public List getElementosCFVOByIds(String[] ids);

	public int getMarcasBloqueo(String idElementoCF);

	/**
	 * Obtiene las Series descendientes de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del Elemento del cuadro de clasficiacion
	 * @return
	 */
	public List getSeriesHijas(String nivelCFSerie, String idElementoCF);

	public TablaTemporalFondosVO getTablaTemporal(String idUsuario,
			String identificador, String[] ids) throws Exception;

	public void liberarTablaTemporalNoTransaccional(
			TablaTemporalFondosVO tablaTemporalFondosVO);

	/**
	 * Obtiene los interesados de un elemento del cuadro
	 *
	 * @param idsElementos
	 *            Array de cadenas que contienen los identificadores de los
	 *            elementos del cuadro
	 * @return Map Key=IdElementoCf Value=InteresadoVO;
	 */
	public Map getInteresadosByIdsElementoCF(List idsElementos);

	/**
	 * Obtiene los productores de un elemento del cuadro
	 *
	 * @param idElementos
	 *            idsElementos Array de cadenas que contienen los
	 *            identificadores de los elementos del cuadro
	 * @return Map key=Idelementocf Value=String (Nombre completo productor)
	 */
	public Map getProductoresByIdsElementoCF(List idElementos);

	public IElementoCuadroClasificacion getElementoCuadroClasificacionConFechas(
			String idElemento);

	/**
	 * @param tablaTemporalFondosVO
	 * @return
	 */
	public List getNombresNiveles(TablaTemporalFondosVO tablaTemporalFondosVO);

	/**
	 * @param tablaTemporalFondosVO
	 * @return
	 */
	public HashMap getPairsIdCodRefPadre(
			TablaTemporalFondosVO tablaTemporalFondosVO);

	public void updateElementoCF(ElementoCuadroClasificacionVO uDocEnCF);

}