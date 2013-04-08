package common.bi;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.exceptions.GestorCatalogoException;
import transferencias.vos.TransferenciaElectronicaInfo;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;
import common.pagination.PageInfo;

import es.archigest.framework.core.exceptions.ArchigestModelException;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.exceptions.ProductorProcedimientoNoIncorporadoException;
import fondos.model.IdentificacionSerie;
import fondos.vos.BusquedaSolicitudesSerieVO;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.OpcionesDescripcionSerieVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.SolicitudSerieVO;
import fondos.vos.VolumenSerieVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.ListaAccesoVO;

/**
 * Interface con los metodos de servicio de gestión de series documentales
 *
 */
public interface GestionSeriesBI {

	/**
	 * Incorpora una nueva serie al cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param elementoPadre
	 *            Clasificador de series donde se crea la series
	 * @param infoAltaSerie
	 *            Datos de serie
	 * @return Datos de la serie creada
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporación de la serie no esté permitida
	 */
	public SerieVO nuevaSerie(ElementoCuadroClasificacionVO elementoPadre,
			SerieVO infoAltaSerie) throws ActionNotAllowedException;

	/**
	 * Crea en el sistema una solicitud de creación de una serie documental con
	 * los datos que se indican para que alguien con los privilegios oportunos
	 * pueda aceptar dicha solicitud y que la serie cuya alta se pide sea
	 * incorporada al sistema
	 *
	 * @param elementoPadre
	 *            Clasificador de series donde se solicita el alta
	 * @param infoSerie
	 *            Datos de serie
	 * @param motivoSolicitud
	 *            Explicación del motivo por el que se solicita el alta de la
	 *            serie
	 * @param idUsrSolititante
	 *            Identificador del usuario que solicita el alta de la serie
	 * @return Datos de la solicitud que se genera
	 * @throws ActionNotAllowedException
	 *             Caso de que no esté permitida la creación de lo solictud que
	 *             se pretende generar
	 */
	public SolicitudSerieVO solicitarAltaDeSerie(
			ElementoCuadroClasificacionVO elementoPadre, SerieVO infoSerie,
			String motivoSolicitud, String idUsrSolititante)
			throws ActionNotAllowedException;

	/**
	 * Obtiene un conjunto de series del cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param serieIDs
	 *            Conjunto de identificadores de serie
	 * @return Lista de series {@link SerieVO}
	 */
	public List getSeries(String[] serieIDs);

	public IdentificacionSerie getIdentificacionSerie(SerieVO serie);

	public IdentificacionSerie abrirIdentificacionSerie(SerieVO serie)
			throws Exception;

	/**
	 * Obtiene el número de solicitudes realizadas por un gestor de series. La
	 * gestión de series documnetales incluye un conjunto de acciones que
	 * requieren un proceso de solicitud y aprobación
	 *
	 * @param Identificador
	 *            de usuario
	 * @return Número de solicitudes de autorización de acciones de gestión de
	 *         series realizadas por el usuario indicado
	 */
	public int getCountSolicitudesGestor(String iduser);

	/**
	 * Obtiene las solicitudes realizadas por un gestor de series. La gestión de
	 * series documnetales incluye un conjunto de acciones que requieren un
	 * proceso de solicitud y aprobación
	 *
	 * @param Identificador
	 *            de usuario
	 * @return Lista de las solicitudes de autorización de acciones de gestión
	 *         de series realizadas por el usuario indicado
	 *         {@link SolicitudSerieVO}
	 */
	public List getSolicitudesGestor(String iduser);

	public SolicitudSerieVO getDetalleSolicitud(String idSolicitud);

	public SolicitudSerieVO abrirSolicitud(String idSolicitud);

	/**
	 * Aceptar una solicitud de autorización para la realización de una accion
	 * de gestión de series. La aceptación de la solicitud supone la ejecución
	 * de la acción solicitada
	 *
	 * @param solicitud
	 *            Solicitud que se autoriza
	 * @param idGestor
	 *            Identificador del usuario que autoriza la solicitud
	 * @param infoAutorizacion
	 *            Información adicional suministrada para la autorización de la
	 *            solicitud
	 * @return Datos de la solicitud autorizada
	 * @throws ActionNotAllowedException
	 *             Caso de la que autorización de la solicitud no sea posible
	 */
	public SolicitudSerieVO autorizarSolicitud(SolicitudSerieVO solicitud,
			String idGestor, Map infoSolicitud)
			throws ActionNotAllowedException;

	/**
	 * Rechaza una solicitud de autorización para la realización de una accion
	 * de gestión de series.
	 *
	 * @param solicitud
	 *            Solicitud que se autoriza
	 * @param motivo
	 *            Motivo por el que se rechaza la solicitud
	 * @param idGestor
	 *            Identificador del usuario que rechaza la solcitud
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de la que el rechazo de la solicitud no esté permitido
	 */
	public void rechazarSolicitud(SolicitudSerieVO solicitudAlta,
			String motivo, String idGestor);

	public Collection busquedaSolicitudesSeries(
			BusquedaSolicitudesSerieVO busquedaSolicitudSerieVO)
			throws TooManyResultsException;

	/**
	 * Obtiene una serie del cuadro de clasificación de fondos documentales
	 *
	 * @param serieID
	 *            Identificador de serie documental
	 * @return Datos de la serie
	 */
	public SerieVO getSerie(String idSerie);

	public SerieVO getSerieProcedimiento(String procedimiento);

	/**
	 * Registra una solicitud de paso de una serie a estado 'Vigente'
	 *
	 * @param idSerie
	 *            Identificador de la serie para la que se realiza la solicitud
	 * @param motivo
	 *            Motivo por el que se realiza la solicitud
	 * @param idSolicitante
	 *            Identificador del usuario que realiza la solicitud
	 * @return Datos de la solicitud generada
	 * @throws ActionNotAllowedException
	 *             Caso de que la solicitud del paso de la serie a histórica no
	 *             esté permitida
	 */
	public SolicitudSerieVO solicitarPasoAVigente(String idSerie,
			String motivo, String idSolicitante)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Registra una solicitud de paso de una serie a estado 'Histórica'
	 *
	 * @param idSerie
	 *            Identificador de la serie para la que se realiza la solicitud
	 * @param motivo
	 *            Motivo por el que se realiza la solicitud
	 * @param idSolicitante
	 *            Identificador del usuario que realiza la solicitud
	 * @return Datos de la solicitud generada
	 * @throws ActionNotAllowedException
	 *             Caso de que la solicitud del paso de la serie a histórica no
	 *             esté permitida
	 */
	public SolicitudSerieVO solicitarPasoAHistorica(String idSerie,
			String motivo, String idSolicitante)
			throws ActionNotAllowedException;

	/**
	 * Registra una solicitud de autorización de los cambios realizados en la
	 * identificación de una serie
	 *
	 * @param idSerie
	 *            Identificador de la serie para la que se realiza la solicitud
	 * @param motivo
	 *            Motivo por el que se realiza la solicitud
	 * @param idSolicitante
	 *            Identificador del usuario que realiza la solicitud
	 * @return Datos de la solicitud generada
	 * @throws ActionNotAllowedException
	 *             Caso de que la solicitud del paso de la serie a histórica no
	 *             esté permitida
	 */
	public SolicitudSerieVO solicitarAutorizacionCambios(String idSerie,
			String motivo, String idSolicitante)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Autoriza los cambios que hayan sido realizados en la identificación de
	 * una serie
	 *
	 * @param idSerie
	 *            Identificación de la serie para la que se realiza la
	 *            autorización
	 * @throws ActionNotAllowedException
	 *             Caso de que la autorización de cambios a la identificación de
	 *             la serie no esté permitida
	 */
	public void autorizarCambiosEnIdentificacion(String idSerie)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Guarda los datos de identificación de una serie
	 *
	 * @param serie
	 *            Serie cuya identificación se desea guardar
	 * @param identificacionSerie
	 *            Datos de identificación de la serie
	 * @param listaProductoresPasadosAHistoricos
	 *            Lista {@link ProductorSerieVO} con los productores que se han
	 *            pasado a historicos.
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la modificación de la identificación de la serie
	 *             no esté permitida
	 */
	public void saveIdentificacionSerie(SerieVO serie,
			IdentificacionSerie identificacionSerie)
			throws FondosOperacionNoPermitidaException,
			GestorOrganismosException, NotAvailableException;

	public void saveIdentificacionSerieSinProcedimientoEnEstudio(SerieVO serie,
			IdentificacionSerie identificacionSerie,
			List listaProductoresHistoricosEnMemoria,
			List sustitucionVigenteHistorico,
			List listaProductoresPasadosAHistoricos,
			List listaProductoresHistoricosEliminados)
			throws FondosOperacionNoPermitidaException,
			GestorOrganismosException, NotAvailableException;

	/**
	 * Refresca los datos de la identificación de una serie. La información de
	 * la identificación de la serie que puede haber variado son los productores
	 * del procedimiento vinculado a la serie caso de que en el proceso de
	 * identificación se le haya asociado un procedimiento a la serie.
	 *
	 * @param idSerie
	 *            Identificador de la serie cuya identificación se desea
	 *            actualizar
	 */
	public HashMap actualizarIdentificacionSerie(String idSerie)
			throws GestorCatalogoException, GestorOrganismosException,
			NotAvailableException, FondosOperacionNoPermitidaException,
			ArchigestModelException,
			ProductorProcedimientoNoIncorporadoException;

	/**
	 * Paso de una serie a estado 'Vigente
	 *
	 * @param idSerie
	 *            Identificador de serie del cuadro de clasificación de fondos
	 *            documentales
	 * @throws ActionNotAllowedException
	 *             Caso de que el paso a vigente de la serie no esté permitido
	 */
	public void serieVigente(String idSerie) throws ActionNotAllowedException;

	/**
	 * Paso de una serie a estado 'En Estudio'. La fase de 'En Estudio' sirve
	 * para poder realizar modificaciones en la identificación de una serie
	 * vigente.
	 *
	 * @param idSerie
	 *            Identficador de la serie a pasar a 'En Estudio'
	 * @param gestorSerie
	 *            Usurario que se asigna como responsable del estudio de la
	 *            serie
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que el paso de la serie a 'En Estudio' no esté
	 *             permitido
	 */
	public void serieEnEstudio(String idSerie, String gestorSerie)
			throws FondosOperacionNoPermitidaException;

	/**
	 * Pasa una serie a estado 'Histórica'
	 *
	 * @param idSerie
	 *            Identificador de serie
	 * @throws ActionNotAllowedException
	 *             Caso de que el paso de la serie a estado 'Histórica' no esté
	 *             permitido
	 */
	public void serieHistorica(String idSerie) throws ActionNotAllowedException;

	/**
	 * Obtiene las series de las que es gestor un determinado usuario
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Lista de series {@link SerieVO}
	 */
	public List findSeriesByGestor(String idGestor);

	// public SerieVO findSerieXProcedimiento(String idProcedimiento);
	/**
	 * Obtiene los productores del procedimiento actual asociado a la serie
	 *
	 * @param idSerie
	 *            Identificador de una serie
	 * @return Lista de elementos
	 * @link ProductorVGSerieVO
	 */
	public List getProductoresSerie(String idSerie);

	/**
	 * Obtiene la lista de entidades identificadas como productores de una serie
	 * documental ordenadas por fecha
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param orderByDate
	 *            Indica si se ordenan por fecha
	 * @return Lista de productores de la serie {@link ProductorSerieVO}
	 */
	public List getProductoresSerie(String idSerie, boolean orderByDate);

	/**
	 * Obtiene la lista de Productores Vigentes
	 *
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return List de productores de la {@link ProductorSerieVO}
	 */
	public List getProductoresVigentesBySerie(String idSerie);

	/**
	 * Obtiene la lista de Productores Vigentes
	 *
	 * @param idSerie
	 *            Identificador de la Serie
	 * @return List de productores de la {@link ProductorSerieVO}
	 */
	public List getProductoresHistoricosBySerie(String idSerie);

	public ProductorSerieVO getProductorVigenteBySerie(String idSerie,
			String idProductor);

	public ProductorSerieVO getProductorHistoricoBySerie(String idSerie,
			String idProductor);

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt);

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt,
			final Boolean vigente);

	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano);

	public ProductorSerieVO getProductorXIdSerieEIdDescriptorOrgano(
			String idSerie, String idOrgano);

	/**
	 * Obtiene la informacion referente al productor de una serie
	 *
	 * @param idProductor
	 *            Identificador de organo
	 * @param idSerie
	 *            Identificador de serie
	 * @return Información referente al productor de la serie
	 */
	// public ProductorSerieVO getProductorSerie(String idProductor, String
	// idSerie);

	/**
	 * Localiza las series dentro del cuadro de clasificacion que verifican las
	 * condiciones suministradas
	 *
	 * @param fondoID
	 *            Identificador del fondo al que deben pertenecer las series.
	 *            Puede ser nulo
	 * @param codigo
	 *            Cadena que debe estar contenida en el codigo de la serie
	 * @param titulo
	 *            Cadena que debe estar contenida en la denominación de la serie
	 * @return Lista de series que verifican los criterios {@link SerieVO}
	 */
	public List findSeries(String fondoID, String codigo, String titulo);

	public List findSeriesParaPrevision(String fondoID, String codigo,
			String titulo);

	/**
	 * Obtiene la lista de Series Para una Previsión filtradas por el
	 * idProductor. Esta consulta se utiliza para los usuarios que no tienen el
	 * permiso 203 Ampliado de Transferencias Extraordinarias
	 *
	 * @param fondoID
	 *            Filtro del Fondo
	 * @param codigo
	 *            Filtro del Código de Serie
	 * @param titulo
	 *            Filtro del Título de la Serie
	 * @param idProductor
	 *            Identificador del Órgano
	 * @return Lista de {@link SerieVO}
	 */
	public List findSeriesParaPrevisionByIdOrgano(String fondoID,
			String codigo, String titulo, String idOrgano);

	/**
	 * Busca las series que admiten ingresos y que verifican los criterios que
	 * se suministran
	 *
	 * @param fondo
	 *            Identificador de fondo al que deben pertenecer las series
	 * @param codigo
	 *            Cadena que debe contener el codigo de la serie
	 * @param titulo
	 *            Cadena que debe contener el titulo de la serie
	 * @return Lista de series {@link SerieVO}
	 */
	public List getSeriesActivas(String fondo, String codigo, String titulo);

	List getSeriesPorEstados(String fondo, String codigo, String titulo,
			int[] estados);

	public List getSeriesDestinoMovimientoUDocs(String fondo, String codigo,
			String titulo, String idSerieOriginal);

	/**
	 * Localiza las series dentro del cuadro de clasificacion que verifican las
	 * condiciones suministradas y cuya serie se encuentra en estado vigente o
	 * historica
	 *
	 * @param fondoID
	 *            Identificador del fondo al que deben pertenecer las series.
	 *            Puede ser nulo
	 * @param codigo
	 *            Cadena que debe estar contenida en el codigo de la serie
	 * @param titulo
	 *            Cadena que debe estar contenida en la denominación de la serie
	 * @return Lista de series que verifican los criterios {@link SerieVO}
	 */
	public List findSeriesValorables(String fondoID, String codigo,
			String titulo);

	/**
	 * Localiza las series dentro del cuadro de clasificacion que verifican las
	 * condiciones suministradas y cuya serie se encuentra en estado vigente o
	 * historica y tiene una valoración dictaminada
	 *
	 * @param fondoID
	 *            Identificador del fondo al que deben pertenecer las series.
	 *            Puede ser nulo
	 * @param codigo
	 *            Cadena que debe estar contenida en el codigo de la serie
	 * @param titulo
	 *            Cadena que debe estar contenida en la denominación de la serie
	 * @return Lista de series que verifican los criterios {@link SerieVO}
	 */
	public List findSeriesSeleccionables(String fondoID, String codigo,
			String titulo);

	/**
	 * Asigna a un usuario como gestor de las series especificadas
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @param serieIDs
	 *            Lista de identificadores de serie
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que no se puede llevar a cabo la asignacion de alguna
	 *             de las series al gestor indicado
	 */
	public void asignarGestor(String idGestor, String[] serieIDs)
			throws FondosOperacionNoPermitidaException;

	// public OrganoProductorVO getProductorXId(String idDescrProductor);
	//
	// public OrganoProductorVO getProductorXIdOrgYIdSistGestor(String idOrg,
	// String idSistGestor);

	/**
	 * Obtiene las series de un fondo documental
	 *
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Lista de series del fondo {@link SerieVO}
	 */
	public List getSeriesFondo(String codFondo);

	/**
	 * Obtiene el número de solicitudes que están pendientes de aprobación
	 *
	 * @return Número de solicitudes
	 */
	public int getCountSolicitudesAGestionar();

	public List getSolicitudesAGestionar();

	/**
	 * Actualiza el volumen de la serie en las cantidades indicadas
	 *
	 * @param idSerie
	 *            Identificador de la serie
	 */
	public void updateVolumenSerieNoTransaccional(String idSerie);

	/**
	 * Actualiza el número de unidades documentales que contiene un serie
	 *
	 * @param idSerie
	 *            Identificador de serie del cuadro de clasificación
	 */
	public void updateContenidoSerieNoTransaccional(String idSerie);

	/**
	 * Obtiene el número de series que pueden se identificadas por un usuario
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Número de series
	 */
	public int getCountSeriesEnIdentificacion(String idGestor);

	/**
	 * Obtiene las series que a gestionar por parte de un usuario
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Lista de series {@link SerieVO}
	 */
	public List getSeriesEnIdentificacion(String idGestor);

	/**
	 * Obtiene los órganos que pueden ser incorporados como productores de una
	 * serie
	 *
	 * @param queryString
	 *            Patrón que debe estar contenido en el nombre de los
	 *            productores
	 * @param serie
	 *            Serie para la que se solicitan los productores
	 * @return Lista de productores
	 */
	public List findPosiblesProductores(String queryString,
			IdentificacionSerie serie) throws GestorOrganismosException,
			NotAvailableException;

	/**
	 * Obtiene el volumen de una serie.
	 *
	 * @param idSerie
	 *            Identificador de una serie.
	 * @param tipoDocumental
	 *            Tipo documental.
	 * @return Volumen de una serie.
	 */
	public VolumenSerieVO getVolumenYSoporteSerie(String idSerie,
			String tipoDocumental);

	/**
	 * Obtiene los volumenes y soportes de la serie
	 *
	 * @param idSerie
	 *            Identificador de una serie.
	 * @return Lista de Volumenes {@link VolumenSerieVO}.
	 */
	public List getVolumenesYSoporteSerie(String idSerie);

	/**
	 * Obtiene el número de unidades documentales que contiene una serie
	 * documental
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Númer de unidades documentales en la serie
	 */
	public int getCountUnidadesDocumentales(String idSerie);

	/**
	 * Elimina una serie del cuadro de clasificación de fondos documentales
	 *
	 * @param idSerie
	 *            Identificador de serie
	 * @throws ActionNotAllowedException
	 *             Cuando la eliminación de la serie no está permitida
	 */
	public void eliminarSerie(String idSerie) throws ActionNotAllowedException;

	/**
	 * Estable valores para las opciones de almacenamiento y visualización de
	 * descripción de una serie del cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param idSerie
	 *            Identificador de serie del cuadro de clasificación de fondos
	 *            documentales
	 * @param fichaDescripcionSerie
	 *            Identificador de la definición de ficha descriptiva a utilizar
	 *            para la descripción de la serie
	 * @param idRepositorioEcm
	 *            Identificador del repositorio Ecm en el que se guardarán los
	 *            documentos anexados a la serie
	 * @param nivelesFichaUDocRepEcm
	 */
	public void setInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepositorioEcm, List nivelesFichaUDocRepEcm)
			throws ActionNotAllowedException;

	void setInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepositorioEcm, List nivelesFichaUDocRepEcm, String idLCA,
			int nivelAcceso, String codOrdenacion)
			throws ActionNotAllowedException;

	/**
	 * Obtiene las opciones de visualización y almacenamiento definidos para una
	 * serie
	 *
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Opciones de descripción y almacenamiento establecidas para la
	 *         serie
	 */
	public OpcionesDescripcionSerieVO getOpcionesDescripcionSerie(SerieVO serie);

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
	 * Obtiene la lista de unidades documentales que contiene una serie del
	 * cuadro de clasificación de fondos documentales.
	 *
	 * @param idPadre
	 *            Identificador de serie.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de unidades documentales que contiene la serie
	 *         {@link ElementoCuadroClasificacionVO}.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUdocsSerie(String idSerie, PageInfo pageInfo)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de unidades documentales de una lista de ids
	 *
	 * @param idsElmentos
	 *            Array que contiene las cadenas con los ids de los elementos a
	 *            mostrar.
	 * @return Lista de unidades documentales que contiene la serie
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUdocsSerieByIdsElementos(String[] idsElementos);

	public List getUdocsSerieByProductor(String idSerie, PageInfo pageInfo,
			String productor) throws TooManyResultsException;

	public List getUdocsSerie(String idSerie, PageInfo pageInfo,
			BusquedaUdocsSerieVO busquedaUdocsSerieVO)
			throws TooManyResultsException;

	// public List getUdocsSerieFromVista(String idSerie, PageInfo pageInfo,
	// BusquedaUdocsSerieVO busquedaUdocsSerieVO) throws
	// TooManyResultsException;

	/**
	 * Obtiene las unidades documentales conservadas cuyos identificadores
	 * coinciden con los del parametro.
	 *
	 * @param Array
	 *            de cadenas que contienen los identficadores de los elementos.
	 */
	public List getUdocsSerieConservadas(String[] idsElementos);

	public List getUdocsSerieConservadasByIdSerie(String idSerie);

	// /**
	// * Obtiene las unidades documentales conservadas pertenecientes a una
	// serie
	// * @param Array de cadenas que contienen los identficadores de los
	// elementos.
	// */
	// public List getUdocsSerieConservadas(String idSerie);

	public List getUdocsSerieConFechasExtremas(String idSerie, PageInfo pageInfo)
			throws TooManyResultsException;

	public Map getProductoresUdocsSerie(String idSerie)
			throws TooManyResultsException;

	/**
	 * Elimina del sistema las solicitudes de autorización de acciones de
	 * gestión de series cuyos identificadores se suministran
	 *
	 * @param solicitudSeleccionada
	 *            Conjunto de identificadores de solicitudes de autorización
	 * @throws ActionNotAllowedException
	 *             Caso de que la eliminación de alguna de las solicitudes no
	 *             esté permitida
	 */
	public void eliminarSolicitudes(String[] solicitudSeleccionada)
			throws ActionNotAllowedException;

	/**
	 *
	 * @param tipoProcedimientos
	 *            1 - Retorna todos; 2 - Busqueda de automatizados ; 3 -
	 *            Busqueda en No Automatizados
	 * @param searchToken
	 * @return
	 * @throws GestorCatalogoException
	 * @throws NotAvailableException
	 */
	public List findProcedimientos(int tipoProcedimientos, String searchToken)
			throws GestorCatalogoException, NotAvailableException;

	/**
	 * @param serie
	 * @param nuevoPadre
	 */
	public void moverSerie(SerieVO serie, String nuevoPadre)
			throws FondosOperacionNoPermitidaException;

	public OrganoProductorVO getOrgProductorXIdDescr(String idDescr);

	public void createNuevoProductor(CAOrganoVO organo, String idSerie)
			throws GestorCatalogoException, NotAvailableException;

	public void sustituirProductor(String idOrganoProductorAntiguo,
			CAOrganoVO nuevoCAOrganoVO, String idSerie)
			throws GestorCatalogoException, NotAvailableException;

	public void pasarAHistorico(ProductorSerieVO productorSerieVO,
			CAOrganoVO antiguoCAorganoVO, CAOrganoVO nuevoCAorganoVO);

	public void pasarAHistoricoProductoresVigentesDespues(
			List listaProductoresAntiguosVigentesDespues,
			String[] productoresAntiguos,
			CAOrganoVO nuevoProductorCAOrganoVODespues);

	public void delete(String idSerie, String idProductor);

	public void deletePermisosLista(String idLista, int tipoDestinatarios,
			String[] idDestinatarios);

	public List getPermisosXIdListaIdDestino(String idLista, String idDestino);

	public ListaAccesoVO getListaAcceso(String idListaAcceso);

	public List getPermisosXIdLista(String idLista);

	public void actualizarAccesoOrganoProductor(String idLCA,
			String idOrganoProductor);

	// public CAOrganoVO crearOrgano(SerieVO serie, String idOrgano) throws
	// GestorOrganismosException, NotAvailableException;
	public List getProductoresVigentesOriginalesBySerie(String idSerie);

	public List getProductoresHistoricosBySerieFechaFinalMayorQueFecha(
			String idSerie, Date date);

	// public void darDeAltaNuevosOrganos(List listaOrganos, SerieVO serie)
	// throws GestorOrganismosException, NotAvailableException;

	/**
	 * Comprueba si el usario puede editar la serie.
	 */
	public void verificarPermitidoModificarIdentificacion(SerieVO serie)
			throws ActionNotAllowedException;

	public void updateByIdSerieIdProductor(ProductorSerieVO productorVO);

	public List setMarcas(List list, int marcas);

	public void actualizarFechasSerie(String idSerie, Date fechaInicial,
			Date fechaFinal, GestionDescripcionBI descripcionBI);

	public SolicitudSerieVO getSolicitudBySerie(String idSerie);

	public Date getFechaInicialExtremaUdocsSerie(String idSerie);

	public Date getFechaFinalExtremaUdocsSerie(String idSerie);

	public int getCountProductorUdocsSerie(String idSerie);

	/**
	 * Comprueba si existe el productor seleccionado, tiene alguna unidad
	 * documental suya en la serie.
	 *
	 * @param idSerie
	 *            Cadena que contiene el identificador de la serie.
	 * @param idProductor
	 *            identificador del productor.
	 * @return true si el productor contiene unidades documentales en la serie.
	 */
	public boolean isProductorConUdocs(String idSerie, String idProductor);

	public String getGuid() throws Exception;

	public void completarInformacionInteresadosFechasProductores(
			List listaUnidadesDocumentales, boolean addInteresados,
			boolean addFechas, boolean addProductores);

	public List getUdocsSerieFromView(String idSerie, PageInfo pageInfo,
			BusquedaUdocsSerieVO busquedaUdocsSerieVO);

	/**
	 * Establece los valores de la serie para la transferencia electrónica
	 * @param transferenciaElectronicaInfo Objeto que contiene todos los datos de la transfernecia electrónica.
	 * @param crearSerie boolean que indica si se debe crear la serie si no existe.
	 * @throws TransferenciaElectronicaException
	 */
	public void establecerSerieTransferenciaElectronica(
			TransferenciaElectronicaInfo transferenciaElectronicaInfo, boolean crearSerie) throws TransferenciaElectronicaException;
}