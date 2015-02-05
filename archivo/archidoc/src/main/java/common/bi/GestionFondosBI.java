/*
 * Created on 05-may-2005
 *
 */
package common.bi;

import java.util.List;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.exceptions.GestorCatalogoException;

import common.exceptions.ActionNotAllowedException;
import common.util.CustomDateRange;

import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.FondoVO;
import fondos.vos.SerieVO;

/**
 * Interface para el servicio de fondos
 */
public interface GestionFondosBI {

	/**
	 * Devuelve todos los fondos de cuadro
	 *
	 * @return
	 */
	public List getFondos();

	/**
	 * @param entidadProductora
	 * @return Dada una entidad productora devuelve un fondoVO
	 */
	// public FondoVO getFondoXEntidadProductora(String entidadProductora);

	/**
	 * Obtiene un fondo del cuadro de clasificación de fondos documentales
	 *
	 * @param idFondo
	 *            Identificador del fondo a obtener
	 * @return Datos de fondo
	 */
	public FondoVO getFondoXId(String idFondo);


	/**
	 * Obtiene un fondo del cuadro de clasificación de fondos documentales
	 *
	 * @param codigoFondo
	 *            Código del fondo a obtener
	 * @return Datos de fondo
	 */
	public FondoVO getFondoXCodigo(String codigoFondo);

	/**
	 * Obtiene un fondo del cuadro de clasificación de fondos documentales
	 * poniendolo a disposición del usuario que invoca la acción
	 *
	 * @param idFondo
	 *            Identificador del fondo a obtener
	 * @return Datos de fondo
	 */
	public FondoVO abrirFondo(String idFondo);

	/**
	 * @param estados
	 * @return Obtencion de los fondos que se encuentran en alguno de los
	 *         estados pasados por parametro
	 */
	public List getFondosXEstados(int[] estados);

	/**
	 * @return Obtencion de fondos en estado vigente
	 */
	public List getFondosVigentes();

	/**
	 * @return Obtencion de fondos en estado no vigente
	 */
	public List getFondosNoVigentes();

	/**
	 * Comprueba si durante la identificacion de las series del fondo indicado
	 * es posible asociarles un procedimiento. El hecho de que se pueda o no
	 * vincular la serie con un procedimiento vendrá deteminado por la entidad
	 * productora del fondo al que pertenece la serie. Si esa entidad productora
	 * se corresponde con una institución que es gestionada por un sistema
	 * externo de gestión de organización y se dispone de un sistema de gestión
	 * de procedimientos asociado a dicho sistema externo de gestión de
	 * organización la vinculación de un procedimiento con la serie será
	 * posible. Esta comprobación es necesaria para asegurar que los órganos
	 * productores del procedimiento que se seleccione seán órganos
	 * pertenecientes a la misma estructura organizativa que la institución
	 * asignada como entidad productora del fondo documental al que pertenece la
	 * serie.
	 *
	 * @param idFondo
	 *            Identificador del fondo para el que se realiza la comprobación
	 * @return <b>true </b> caso de que la vinculación de procedimiento es
	 *         posible y <b>false </b> cuando no lo es
	 */
	// public boolean isPosibleTenerProcedimiento(String idFondo);

	/**
	 * Obtiene las series de un fondo contra las que es posible realizar
	 * transferencias
	 *
	 * @param idFondo
	 *            Identificador del fondo al que deben pertenecer esas series
	 * @return Series contra las que se pueden realizar transferencias
	 *         {@link SerieVO}
	 */
	public List getSeriesAceptanTransferencias(String idFondo);

	// /**
	// * Insercion de un fondo
	// *
	// * @param elementoPadre
	// * @param fondoVO
	// * @param entidadProductoraVO
	// * @return El fondo insertado
	// * @throws FondosOperacionNoPermitidaException
	// * @throws Exception
	// */
	// public FondoVO
	// insertFondoYEntidadProductora(ElementoCuadroClasificacionVO
	// elementoPadre,
	// FondoVO fondoVO, EntidadProductoraVO entidadProductoraVO)
	// throws FondosOperacionNoPermitidaException;

	/**
	 * Actualizacion de un fondo
	 *
	 * @param fondo
	 * @param entidadVO
	 * @throws FondosOperacionNoPermitidaException
	 */
	// public void updateFondo(FondoVO fondo, EntidadProductoraVO entidadVO)
	// throws FondosOperacionNoPermitidaException;

	/**
	 * @param id
	 * @return Una entidad productora donde el campo idDescr = id
	 */
	public EntidadProductoraVO getEntidadProductoraXIdDescr(String id);

	// /**
	// *
	// * @param idFondo
	// * @return True si el fondo tiene unidades documentales, falso en caso
	// * contrario
	// */
	// public boolean fondoTieneUDocs(String idFondo);
	//
	// public boolean fondoTieneSeries(String idFondo);

	/**
	 * No ha de tener series y todos sus hijos clasificadores de series tienen
	 * estado ‘No vigente’. Chequear que la entidad productora asociada al fondo
	 * no tiene organismos asociados. Si los tiene mostrar aviso y no permitir
	 * borrar.
	 *
	 * @param fondoVO
	 * @return True si es borrable
	 */
	// public boolean checkFondoBorrable(FondoVO fondoVO) throws
	// FondosOperacionNoPermitidaException;

	/**
	 * @param fondoVO
	 * @return Objeto (@link ExtendedFondo) con propiedades de consulta que
	 *         acceden directamente al servicio
	 */
	// public ExtendedFondo getExtendedFondo(FondoVO fondoVO);

	/**
	 * Eliminación de un fondo
	 *
	 * @param idFondo
	 */
	public void removeFondo(String idFondo)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Mueve el fondo a otro clasificador de fondos dentro del cuadro de
	 * clasificacion
	 *
	 * @param idFondo
	 *            Identificador del fondo a mover
	 * @param nuevoPadre
	 *            Identificador del clasificador de fondos del que pasa a
	 *            depender el fondo
	 */
	public void moverFondo(FondoVO fondo, String nuevoPadre)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Obtiene el fondo documental al que irá destinada la documentación
	 * transferida por un organo remitente
	 *
	 * @param idOrgano
	 *            Identificador de organo remitente
	 * @return Fondo del cuadro de clasificación de fondos documentales
	 */
	public FondoVO getFondoXOrganoRemitente(String idOrgano, String idArchivo,
			String codArchivo);

	// /**
	// * Obtiene las instituciones que pueden ser entidades productoras de un
	// fondo
	// * @return
	// */
	// public List getInstitucionesProductorasParaFondos() throws
	// NotAvailableException, GestorOrganismosException;

	/**
	 * Obtener el fondo de una valoración de serie.
	 *
	 * @param idValoracion
	 *            Identificador de la valoración de serie.
	 * @return Fondo.
	 */
	public FondoVO getFondoXIdValoracion(String idValoracion);

	// /**
	// * Obtiene los descriptores de las posibles entidades productoras del tipo
	// indicado y cuyo nombre
	// * contenga el patrón suministrado
	// * @param tipoEntidad Tipo de entidad productora
	// * @param queryString Patrón que debe estar contenido en el nombre de los
	// descriptores a devolver.
	// * @return Conjunto de descriptores que verifican las condiciones
	// */
	// public List findPosiblesEntidadesProductoras(int tipoEntidad, String
	// queryString);

	/**
	 * @param fondo
	 * @param idInstitucionEnSistemaExterno
	 */
	public FondoVO guardarFondo(FondoVO fondo,
			String idInstitucionEnSistemaExterno)
			throws ActionNotAllowedException, GestorOrganismosException,
			NotAvailableException;

	/**
	 * @param fondo
	 * @param descriptorEntidadProductora
	 */
	public FondoVO guardarFondo(FondoVO fondo, int tipoEntidad,
			String idDescriptorEntidadProductora)
			throws ActionNotAllowedException;

	/**
	 * Obtiene la entidad productra de un fondo documental
	 *
	 * @param idFondo
	 *            Identificador de fondo documental
	 * @return Datos de la entidad productora del fondo
	 */
	public EntidadProductoraVO getEntidadProductoraFondo(String idFondo);

	/**
	 * Obtiene las fechas extremas de un fondo.
	 *
	 * @param idFondo
	 *            Identificador del fondo.
	 * @return Fechas extremas.
	 */
	public CustomDateRange getFechasExtremas(String idFondo);

	/**
	 * Obtiene la lista de repositorieos ECM de las series de un fondo.
	 *
	 * @param idFondo
	 *            Identificador de un fondo.
	 * @return Lista de Repositorios ECM de las series de un fondo.
	 */
	public List getRepositoriosEcmSeriesFondo(String idFondo);

	public void setEstadoVigencia(FondoVO fondo, boolean vigente)
			throws ActionNotAllowedException;

	/**
	 * Permite obtener los procedimientos para la búsqueda
	 *
	 * @param tipoProcedimiento
	 *            tipo de procedimiento
	 * @param titulo
	 *            título
	 * @return Lista de procedimientos
	 *
	 * @throws GestorCatalogoException
	 * @throws NotAvailableException
	 */
	public List findProcedimientosBusqueda(int tipoProcedimiento, String titulo)
			throws GestorCatalogoException, NotAvailableException;

	/**
	 * Obtiene los fondos que pertenecen a un archivo pasándole el código.
	 *
	 * @param codArchivo
	 *            Código de Archivo.
	 * @return el código de archivo.
	 */
	public List getFondosXCodArchivo(String codArchivo);

	/**
	 * Actualiza la información de control de acceso de un fondo
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación
	 * @param nivelAcceso
	 *            nivel de acceso
	 * @param idLCA
	 *            identificador de la lista de acceso
	 */
	public void updateInfoAccesoElemento(String idElementoCF, int nivelAcceso,
			String idLCA);

	/**
	 * Permite bloquear las unidades documentales seleccionadas
	 *
	 * @param unidadDocumentalIDs
	 *            identificadores del cuadro de las unidades a bloquear
	 * @throws ActionNotAllowedException
	 */
	public void lockUnidadesDocumentales(String[] unidadDocumentalIDs)
			throws ActionNotAllowedException;

	/**
	 * Permite desbloquear las unidades documentales seleccionadas
	 *
	 * @param unidadDocumentalIDs
	 *            identificadores del cuadro de las unidades a desbloquear
	 * @throws ActionNotAllowedException
	 */
	public void unlockUnidadesDocumentales(String[] unidadDocumentalIDs)
			throws ActionNotAllowedException;
}