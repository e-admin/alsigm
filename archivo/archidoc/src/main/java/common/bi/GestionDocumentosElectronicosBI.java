package common.bi;

import java.util.List;

import se.repositorios.archigest.InfoFicheroVO;
import transferencias.vos.DocumentoElectronicoVO;
import transferencias.vos.TransferenciaElectronicaInfo;
import util.TreeModel;

import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.vos.Restringible;

import docelectronicos.TipoObjeto;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.model.GestionDocumentosElectronicosBIImpl.DataClfDocYRepEcm;
import docelectronicos.vos.BusquedaTareasVO;
import docelectronicos.vos.ClasificadoDocumentos;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;
import docelectronicos.vos.FichaClfVO;
import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.InfoBFichaClfVO;

/**
 * Interfaz de negocio de documentos electrónicos
 */
public interface GestionDocumentosElectronicosBI {

	// ========================================================================
	// VOLÚMENES
	// ========================================================================

	/**
	 * Obtiene los repositorioes ECM disponibles
	 *
	 * @return Lista Repositorios ECM.
	 */
	public List getRepositoriosEcm();

	// ========================================================================
	// FICHAS
	// ========================================================================

	/**
	 * Obtiene la lista de fichas de clasificadores documentales.
	 *
	 * @return Listas fichas de clasificadores documentales.
	 */
	public List getFichas();

	/**
	 * Obtiene la fichas de clasificadores documentales.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public FichaClfVO getFicha(String id);

	/**
	 * Obtiene la información básica de la ficha de clasificadores documentales.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public InfoBFichaClfVO getInfoBFicha(String id);

	// ========================================================================
	// CLASIFICADORES DE DOCUMENTOS
	// ========================================================================

	/**
	 * Obtiene la lista de clasificadores publicados de un objeto visibles por
	 * el usuario.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Lista de clasificadores.
	 */
	public List getClasificadoresVisiblesDesdeCuadro(int tipoObjeto, String id,
			String idClfPadre);

	/**
	 * Obtiene la lista de clasificadores publicados de un objeto.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Lista de clasificadores.
	 */
	public List getClasificadores(int tipoObjeto, String id, String idClfPadre,
			int[] estados);

	/**
	 * Obtiene el clasificador de documentos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @parma idObjeto Identificador del objeto.
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador de documentos.
	 */
	public DocClasificadorVO getClasificador(int tipoObjeto, String idObjeto,
			String id);

	/**
	 *
	 * @param tipoObjeto
	 * @param idObject
	 * @return Clasificador padre del objeto que cumpla los parametros pasado
	 *         por parametro
	 */
	public DocClasificadorVO getClasificadorPadre(int tipoObjeto,
			String idObject);

	/**
	 * Añade los documentos transferidos desde el sistema tramitador.
	 *
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @param documentos
	 *            Lista de documentos electrónicos (
	 *            {@link DocumentoElectronicoVO}).
	 */
	public void insertDocumentosDesdeValidacion(String idUdoc, List documentos);

	/**
	 * Crea un clasificador de documentos electrónicos.
	 *
	 * @param clasificador
	 *            Clasificador.
	 * @return Clasificador.
	 */
	public DocClasificadorVO insertClasificadorDesdeTarea(
			DocClasificadorVO clasificador);

	public DocClasificadorVO insertClasificadorDesdeCuadro(
			DocClasificadorVO clasificador);

	public DocClasificadorVO insertClasificadorXDefecto(
			DocClasificadorVO clasificador);

	/**
	 * Modifica un clasificador de documentos electrónicos.
	 *
	 * @param documentoExt
	 *            Documento electrónico.
	 */
	public void updateClasificadorDesdeTarea(DocClasificadorVO clasificador);

	public void updateClasificadorDesdeCuadro(DocClasificadorVO clasificador);

	/**
	 * Elimina un clasificador de documentos electrónicos.
	 *
	 * @param clasificador
	 *            Clasificador.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeClasificador(DocClasificadorVO clasificador)
			throws Exception;

	/**
	 * Elimina el contenido seleccionado del clasificador de documentos.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param listaIdsClasificadores
	 *            Lista de identificadores de clasificadores.
	 * @param listaIdsDocumentos
	 *            Lista de identificadores de documentos.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeContenidoClasificador(int tipoObjeto, String idObjeto,
			String[] listaIdsClasificadores, String[] listaIdsDocumentos)
			throws Exception;

	/**
	 * Indica si el clasificador es de tipo fijo.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @return True si el padre del clasificador es de tipo fijo.
	 */
	public boolean esFijo(String id, int tipoObjeto, String idObjeto);

	/**
	 * Indica si el clasificador tiene algún hijo de tipo fijo.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @return True si el clasificador tiene algún hijo de tipo fijo.
	 */
	public boolean tieneHijoFijo(String id, int tipoObjeto);

	// ========================================================================
	// DOCUMENTOS ELECTRÓNICOS
	// ========================================================================

	public List getDocumentosElementoCuadro(String id);

	/**
	 * Obtiene la lista de documentos de un objeto visibles por el usuario.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Lista de documentos.
	 */
	public List getDocumentosVisiblesDesdeCuadro(int tipoObjeto, String id,
			String idClfPadre);

	/**
	 * Obtiene la lista de documentos de un objeto.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Lista de documentos.
	 */
	public List getDocumentos(int tipoObjeto, String id, String idClfPadre,
			int[] estados);

	/**
	 * Obtiene el documento electrónico.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Documento electrónico.
	 */
	public DocDocumentoVO getDocumento(int tipoObjeto, String idObjeto,
			String id);

	/**
	 * Obtiene la información del fichero del documento electrónico.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Información del fichero.
	 */
	public DocDocumentoExtVO getDocumentoExt(int tipoObjeto, String idObjeto,
			String id) throws Exception;

	public DocDocumentoVO insertDocumentoDesdeTarea(
			DocDocumentoExtVO documentoExt) throws Exception;

	public DocDocumentoVO insertDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt) throws Exception;

	/**
	 * Inserta el documento en el sistema y si es necesario lo almacena
	 * @param documentoExt Objeto que contiene el documento externo.
	 * @param almacenarFichero indica si se debe almacenar el documento en el sistema externo.
	 * @return
	 * @throws Exception
	 */
	public DocDocumentoVO insertDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt, boolean almacenarFichero)
			throws Exception;

	/**
	 * Modifica un documento electrónico.
	 *
	 * @param documentoExt
	 *            Documento electrónico.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public DocDocumentoVO updateDocumentoDesdeTarea(
			DocDocumentoExtVO documentoExt) throws Exception;

	public DocDocumentoVO updateDocumentoDesdeCuadro(
			DocDocumentoExtVO documentoExt) throws Exception;

	/**
	 * Elimina un documento electrónico.
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param documento
	 *            Documento electrónico.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	public void removeDocumento(DocDocumentoVO documento) throws Exception;

	/**
	 * Comprueba si un objeto tiene documentos asociados
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @return <b>true</b> si el objeto tiene docuementos asociados y
	 *         <b>false</b> en caso contrario
	 */
	public boolean tieneDocumentosAsociados(int tipoObjeto, String id);

	// ========================================================================
	// FICHEROS EN EL REPOSITORIO
	// ========================================================================

	/**
	 * Obtiene el contenido de un fichero del repositorio de ficheros.
	 *
	 * @param idExtDep
	 *            Identificador externo del depósito electrónico.
	 * @param idFich
	 *            Identificador del fichero en el gestor documental.
	 * @return Contenido del fichero.
	 */
	public byte[] getFile(String idRepEcm, String idExtDep, String idFich);

	/**
	 * Guarda el fichero en el repositorio de ficheros.
	 *
	 * @param docDocumentoExtVO
	 *            Datos del Documento
	 */
	public String storeFile(DocDocumentoExtVO docDocumentoExtVO);

	/**
	 * Elimina el fichero del repositorio de ficheros.
	 *
	 * @param idDeposito
	 *            Identificador del depósito (nulo si es interno).
	 * @param idFich
	 *            Identificador del fichero en el gestor documental.
	 */
	public void deleteFile(String idRepEcm, String idDeposito, String idFich);

	// ========================================================================
	// GENERALES
	// ========================================================================

	/**
	 * Inicializa los clasificadores por defecto del objeto.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param isTarea
	 *            Booleano que indica si se está realizando la operación para
	 *            una tarea de digitalización o no
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 *
	 */
	public void inicializaClasificadoresYRepEcm(String idObjeto,
			int tipoObjeto, boolean isTarea) throws DocElectronicosException;

	/**
	 * Elimina los clasificadores y documentos del objeto.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public void deleteDocumentos(String idObjeto, int tipoObjeto);

	/**
	 * Obtiene la raíz del árbol de documentos.
	 *
	 * @param id
	 *            Identificador del objeto que contiene los documentos.
	 * @param tipo
	 *            Tipo de objeto que contiene los documentos.
	 * @return Árbol de documentos.
	 */
	public TreeModel getTreeModel(String id, int tipo);

	/**
	 *
	 * @return El número de tareas a gestionar por el usuario que solicita el
	 *         servicio
	 */
	public int getCountTareasAGestionar();

	/**
	 *
	 * @return La lista de tareas a gestionar por el usuario que solicita el
	 *         servicio
	 */
	public List getTareasAGestionar();

	/**
	 *
	 * @return Número de tareas pendientes del usuario que solicita el servicio
	 */
	public int getCountTareasPendientes();

	/**
	 *
	 * @return Lista de tareas pendientes del usuario que solicita el servicio
	 */
	public List getTareasPendientes();

	/**
	 *
	 * @param idTarea
	 * @return Tarea por id
	 */
	public DocTCapturaVO getTarea(String idTarea);

	/**
	 *
	 * @param tareaVO
	 * @return
	 */
	public DocTCapturaVO updateTarea(DocTCapturaVO tareaVO);

	/**
	 *
	 * @param newTarea
	 * @return
	 * @throws DocElectronicosException
	 *             : si no existe un repositorio ecm
	 */
	public DocTCapturaVO insertTarea(DocTCapturaVO newTarea)
			throws DocElectronicosException;

	public List getListasDescriptorasDigitalizables();

	public List getFondosDigitalizables();

	/**
	 *
	 * @param idFondo
	 * @param titulo
	 * @param codigo
	 * @return @link fondos.model.ElementoCuadroClasificacion
	 * @throws TooManyResultsException
	 */
	public List getElementosDigitalizables(String idFondo, String titulo,
			String codigo, PageInfo pageInfo) throws TooManyResultsException;

	/**
	 *
	 * @param idLista
	 * @param titulo
	 * @return AutoridadVO
	 * @throws TooManyResultsException
	 */
	public List getDescriptoresDigitalizables(String idLista, String titulo,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 *
	 * @param idTarea
	 * @param observaciones
	 * @throws DocElectronicosException
	 *             si la tarea no es finzalible
	 */
	public void finalizarCaptura(String idTarea, String observaciones)
			throws DocElectronicosException;

	/**
	 *
	 * @param idObjeto
	 * @param tipoObjeto
	 * @param estado
	 * @throws DocElectronicosException
	 *             si la tarea no es finalizable
	 */
	public void checkCapturaFinalizable(String idObjeto, int tipoObjeto,
			int estado) throws DocElectronicosException;

	public void checkCapturaValidable(DocTCapturaVO tarea)
			throws DocElectronicosException;

	public void checkValidacionCapturaFinalizable(DocTCapturaVO tarea)
			throws DocElectronicosException;

	public void checkPermisosParaFinalizarTarea(DocTCapturaVO tarea)
			throws DocElectronicosException;

	/**
	 *
	 * @param idTarea
	 * @throws DocElectronicosException
	 *             Si se intenta finalziar una tarea que no se encuentre en
	 *             estado finalizada
	 */
	public void finalizarValidacionTarea(String idTarea, String motivoError)
			throws DocElectronicosException;

	/**
	 *
	 * @param tipoObjeto
	 *            (valor del clasificador en BD, de 1 a 7)
	 * @param idClasificador
	 * @throws DocElectronicosException
	 *             Si se intenta validar el estado de un documento en estado
	 *             publicado
	 */
	public void validarClasificador(DocTCapturaVO tarea, String[] idClasificador)
			throws DocElectronicosException;

	/**
	 *
	 * @param tipoObjeto
	 *            (valor del clasificador en BD, de 1 a 7)
	 * @param idClasificador
	 * @throws DocElectronicosException
	 *             Si se intenta invalidar el estado de un documento en estado
	 *             publicado
	 */
	public void invalidarClasificador(DocTCapturaVO tarea,
			String[] idClasificador) throws DocElectronicosException;

	/**
	 *
	 * @param tipoObjeto
	 * @param idDocumento
	 * @throws DocElectronicosException
	 *             Si se intenta validar el estado de un documento en estado
	 *             publicado
	 */
	public void validarDocumento(DocTCapturaVO tarea, String[] idDocumento)
			throws DocElectronicosException;

	/**
	 *
	 * @param tipoObjeto
	 * @param idDocumento
	 * @throws DocElectronicosException
	 *             Si se intenta invalidar el estado de un documento en estado
	 *             publicado
	 */
	public void invalidarDocumento(DocTCapturaVO tarea, String[] idDocumento)
			throws DocElectronicosException;

	/**
	 * Comprueba si una tarea tiene elementos en estado 'No valido'
	 *
	 * @param idTarea
	 * @return cierto si la tarea tiene elementos 'No validos'
	 * @throws DocElectronicosException
	 */
	public boolean tareaTieneElementosNoValidos(String idObjeto, int tipoObjeto);

	/**
	 * Comprueba si una tarea es eliminable
	 *
	 * @param tarea
	 * @throws DocElectronicosException
	 */
	public void checkTareaEliminable(DocTCapturaVO tarea)
			throws DocElectronicosException;

	/**
	 * Elimina una tarea de captura
	 *
	 * @param idTarea
	 * @throws DocElectronicosException
	 */
	public void eliminarTareaCaptura(String idTarea)
			throws DocElectronicosException;

	/**
	 * Comprueba si para la tarea se pueden seguir asignado o elminando
	 * documentos,clasificadores..
	 *
	 * @param capturaVO
	 * @throws DocElectronicosException
	 */
	public void checkTareaModificablePorElementos(DocTCapturaVO capturaVO)
			throws DocElectronicosException;

	/**
	 * Eliminar las tareas de un elemento
	 *
	 * @param idElemento
	 * @param tipoElemento
	 *            (de 1 a 7)
	 */
	public void eliminarTareas(String idElemento, int tipoElemento);

	public List busquedaTareas(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException;

	/**
	 * @return
	 */
	public List getEstadosTareas();

	/**
	 * @param busquedaVO
	 * @return
	 */
	public List getTareasCedibles(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException;

	/**
	 * Asigna una tarea de captura existente a un capturador
	 *
	 * @param idTarea
	 * @param idCapturador
	 */
	public void asignarTareaACapturador(String[] idTareas, String idCapturador)
			throws DocElectronicosException;

	/**
	 * @param tareasSeleccionadas
	 * @return
	 */
	public List getTareasXIds(String[] tareasSeleccionadas);

	/**
	 * @param id
	 * @param tipo
	 *            objeto 1 a 7 (mirar documentacion de diseño)
	 */
	public void checkTareaCreableSobreElemento(String id, int tipoObjeto)
			throws DocElectronicosException;

	/**
	 *
	 * @param id
	 * @param tipoObjeto
	 * @throws DocElectronicosException
	 */
	public void checkTareaCreableParaUsuario(Restringible restringibleObj,
			String idUsuario) throws DocElectronicosException;

	/**
	 * @param tipoObjeto
	 * @param idObjeto
	 * @param id
	 * @return
	 */
	public List getClasificadoresVisiblesDesdeTarea(int tipoObjeto,
			String idObjeto, String id);

	/**
	 * @param tipoObjeto
	 * @param idObjeto
	 * @param id
	 * @return
	 */
	public List getDocumentosVisiblesDesdeTarea(int tipoObjeto,
			String idObjeto, String id);

	/**
	 * Obtiene la información del fichero del documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
	 * @return Información del fichero del documento electrónico.
	 */
	public InfoFicheroVO getInfoFichero(DocDocumentoVO documento);

	/**
	 * Obtiene el identificador de la ficha de los clasificadores por defecto un
	 * descriptor.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public DataClfDocYRepEcm getIdFichaClfDocYRepEcmDescriptor(String idObjeto);

	/**
	 * Obtiene el identificador de la ficha de los clasificadores por defecto un
	 * elemento del cuadro de clasificación.
	 *
	 * @param idObjeto
	 *            Identificador del objeto.
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 */
	public DataClfDocYRepEcm getIdFichaClfDocYRepEcmElementoCF(String idObjeto)
			throws DocElectronicosException;

	/**
	 * Comprueba si un objeto tiene documentos asociados
	 *
	 * @param tipoObjeto
	 *            Tipo de objeto ({@link TipoObjeto}).
	 * @param id
	 *            Identificador del objeto.
	 * @param idClfPadre
	 *            Identificador del clasificador padre
	 * @return <b>true</b> si el objeto tiene docuementos hijos y <b>false</b>
	 *         en caso contrario
	 */
	public boolean tieneDescendientes(int tipoObjeto, String idObjeto,
			String idClfPadre);

	/**
	 * Obtiene el Repositorio ECM asociado al identificador pasado como
	 * parámetro
	 *
	 * @param idRepEcm
	 *            Identificador del repositorio ECM
	 * @return Repositorio ECM
	 */
	public IRepositorioEcmVO getRepositorioEcm(String idRepEcm);

	// ========================================================================
	// BÚSQUEDA EN CONTENIDOS DE FICHEROS
	// ========================================================================

	/**
	 * Obtiene la lista de identificadores de fichero en el sistema de
	 * almacenamiento que contienen la cadena pasada como parámetro restringidos
	 * a los documentos electrónicos asociados a la lista de identificadores de
	 * elementos del cuadro pasados como parámetro
	 *
	 * @return lista de identificadores de fichero
	 */
	public List buscarFicherosXContenido(String cadenaBusqueda,
			List idsElementos);

	/**
	 * Obtiene la informacion del documento por el identificador del elemento
	 * del cuadro y el identificador interno del documento.
	 *
	 * @param tipoObjeto
	 * @param idObjeto
	 * @param idInterno
	 * @return
	 */
	public DocDocumentoVO getDocumentoByIdInterno(int tipoObjeto,
			String idObjeto, String idInterno);

	/**
	 * Actualiza los identificadores de los documentos y los clasificadores.
	 * @param idElementocfAntiguo
	 * @param idElementoCfNuevo
	 * @param idsClasificadores
	 * @param idsInternosDocumentos
	 */
	public void updateIdElementoCfDocumentosYClasificadores(String idElementocfAntiguo, String idElementoCfNuevo, String[] idsClasificadores, String[] idsInternosDocumentos);

	/**
	 * Establece el nombre del clasificador y si no existe lo crea.
	 *
	 * @param nombreCompleto
	 *            Nombre completo separado por /
	 * @param idsClasificadoresCreados
	 * @return Identificador del clasificador de documentos
	 */

	public String establecerClasificador(int tipoObjeto, String idObjeto,
			String nombreCompleto,
			TransferenciaElectronicaInfo info
		);

}