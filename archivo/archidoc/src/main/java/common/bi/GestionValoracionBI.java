package common.bi;

import java.util.Date;
import java.util.List;

import valoracion.exceptions.ValoracionActionNotAllowedException;
import valoracion.model.ValoracionSerie;
import valoracion.vos.BoletinInfoVO;
import valoracion.vos.BusquedaVO;
import valoracion.vos.SerieSeleccionableVO;
import valoracion.vos.ValoracionSerieVO;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

import fondos.vos.SerieVO;

/**
 * Bussines Interface con los metodos de negocio para el modulo de valoración.
 */
public interface GestionValoracionBI {
	/**
	 * Obtiene las valoraciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getValoraciones(BusquedaVO busqueda)
			throws TooManyResultsException;

	/**
	 * Obtiene un listado de los posibles estados de una valoración de serie.
	 * 
	 * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	 *         con la información del estado.
	 */
	public abstract List getEstadosValoracion();

	/**
	 * Obtiene un listado de los posibles tipos de ordenación de una valoración
	 * de serie.
	 * 
	 * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	 *         con la información del tipo de ordenación.
	 */
	public abstract List getTiposOrdenacion(int nivel);

	// /**
	// * Obtiene un listado de los posibles tipos de valor para una valoración
	// de serie.
	// * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	// con la información
	// * del tipo de valor.
	// */
	// public abstract List getTiposValor(int tipo);
	/**
	 * Obtiene un listado de las posibles técnicas de muestreo de una valoración
	 * de serie.
	 * 
	 * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	 *         con la información de la técnica de muestreo.
	 */
	public abstract List getTecnicasMuestreo();

	/**
	 * Obtiene un listado de las posibles valoraciones del dictamen de una
	 * valoración de serie.
	 * 
	 * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	 *         con la información de la valoración de dictamen.
	 */
	public abstract List getValoresDictamen();

	/**
	 * Obtiene un listado de los posibles estados de una eliminación de serie.
	 * 
	 * @return Listado de {@link es.archigest.framework.core.vo.PropertyBean}
	 *         con la información del estado.
	 */
	public abstract List getEstadosEliminacion();

	/**
	 * Realiza la solicitud de validación de una valoración dada.
	 * 
	 * @param id
	 *            Identificador de la valoración que se desea validar
	 * @throws ValoracionActionNotAllowedException
	 *             Si no se puede solicitar la validacion de la valoracion
	 */
	public abstract void solicitarValidacionValoracion(String id)
			throws ValoracionActionNotAllowedException;

	/**
	 * Realiza la autorizacion de una validación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su validacio
	 */
	public abstract void autorizarValidacionValoracion(String id)
			throws ValoracionActionNotAllowedException;

	/**
	 * Realiza el cierre del dictamen favorable de la valoración.
	 * 
	 * @param id
	 *            Identificador de la valoracion a dictaminar.
	 */
	// public void cerrarDictamenFavorableValoracion(String id) throws
	// ValoracionActionNotAllowedException;
	public void cerrarDictamenFavorableValoracion(String id, SerieVO serieVO)
			throws ValoracionActionNotAllowedException;

	/**
	 * Realiza el rechazi de una validación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su validación
	 */
	// public abstract void rechazarValidacionValoracion(String id,String
	// motivo) throws ValoracionActionNotAllowedException;
	public abstract void rechazarValidacionValoracion(String id, String motivo,
			SerieVO serieVO) throws ValoracionActionNotAllowedException;

	/**
	 * Inicia una nueva valoración para una serie
	 * 
	 * @param serie
	 *            Serie a la que se refiere la valoración
	 * @return Datos iniciales de la valoración de la serie
	 *         {@link ValoracionSerieVO}
	 */
	public abstract ValoracionSerieVO iniciarValoracionSerie(SerieVO serie)
			throws ActionNotAllowedException;

	// /**
	// * Crea una nueva valoracion para una serie
	// * @param valoracion Datos de la valoracion a crear
	// * @return Valoracion almancenada con sus ID generado
	// */
	// public abstract ValoracionSerieVO crearValoracion(ValoracionSerieVO
	// valoracion) throws ValoracionActionNotAllowedException;
	/**
	 * Realiza la actualizacion de los datos de una valoracion
	 * 
	 * @param valoracion
	 *            Datos de la valoracion a actualizar
	 * @return Valoracion actualizada
	 */
	public abstract ValoracionSerieVO actualizarValoracion(
			ValoracionSerieVO valoracion)
			throws ValoracionActionNotAllowedException;

	/**
	 * Realiza la eliminacion de las valoracion seleccionadas por sus ids.
	 * 
	 * @param ids
	 *            Identificadores de las valoraciones a eliminar.
	 */
	public abstract void eliminarValoracion(String ids[])
			throws ValoracionActionNotAllowedException;

	/**
	 * Obtiene un listado de las valoraciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las valoraciones que deseamos
	 *            recuperar.
	 * @return Valoraciones deseadas
	 */
	public abstract List getValoraciones(String[] ids);

	/**
	 * Obtiene un listado de las valoraciones de una serie dada por su
	 * identificador y cuyo estado de valoracion se encuentra en unos de los
	 * pasados, o todas las valoraciones de la series en caso de no venir
	 * definidos los estados.
	 * 
	 * @param codigoSerie
	 *            Serie de la que deseamos obtener las valoraciones.
	 * @param estados
	 *            Listado de los estado que deben tener las valoraciones o null
	 *            si deseamos obtener todas
	 * @return Listado de valoraciones de la serie
	 */
	// public List getValoracionesSerie(String codigoSerie,int[] estados);
	/**
	 * Realiza la autorizacion de una evaluación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su evaluación
	 * @param fechaEvaluacion
	 *            Fecha de la evaluacion
	 */
	public abstract void autorizarEvaluacionValoracion(String id,
			Date fechaEvaluacion) throws ValoracionActionNotAllowedException;

	/**
	 * Realiza el rechazo de una evaluación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su evaluación
	 * @param fechaEvaluacion
	 *            Fecha de la evaluacion
	 */
	// public abstract void rechazarEvaluacionValoracion(String id,String
	// motivo,Date fechaEvaluacion) throws ValoracionActionNotAllowedException;
	public abstract void rechazarEvaluacionValoracion(String id, String motivo,
			Date fechaEvaluacion, SerieVO serie)
			throws ValoracionActionNotAllowedException;

	/**
	 * Realiza la autorizacion de un dictamen de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su dictamen
	 * @param boletin
	 *            Informacion del boletin asociado a la valoracion
	 */
	public abstract void autorizarDictamenValoracion(String id,
			BoletinInfoVO boletin) throws ValoracionActionNotAllowedException;

	/**
	 * Realiza el rechazo de un dictamen de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su dictamen
	 * @param fechaDictamen
	 *            Fecha del dictamen
	 */
	// public abstract void rechazarDictamenValoracion(String id,String
	// motivo,Date fechaDictamen) throws ValoracionActionNotAllowedException;
	public abstract void rechazarDictamenValoracion(String id, String motivo,
			Date fechaDictamen, SerieVO serieVO)
			throws ValoracionActionNotAllowedException;

	/**
	 * Obtiene una valoración de una serie a partir de su identificador en la
	 * base de datos.
	 * 
	 * @param codigo
	 *            Identificador de la valoración en la base de datos
	 * @return Objeto {@link ValoracionSerieVO} con los detalles de la
	 *         valoración.
	 */
	public ValoracionSerieVO getValoracion(String codigo);

	/**
	 * Pone una valoración a disposicion del usuario que solicita su apertura de
	 * manera que unicamente dicho usuario puede realizar acciones sobre la
	 * valoración
	 * 
	 * @param idValoracion
	 *            Identificador de valoración
	 * @return Datos de valoración
	 */
	public ValoracionSerieVO abrirValoracion(String idValoracion);

	/**
	 * Obtiene las valoracióne dictaminada de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Listado de las valoraciones dictaminadas para la serie
	 */
	public ValoracionSerieVO getValoracionDictaminada(String idSerie);

	/**
	 * Obtiene la valoración en curso de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Valoración de la serie {@link ValoracionSerieVO}. Si la serie no
	 *         dispone de una valoración en elaboración se devuelve null.
	 */
	public ValoracionSerieVO getValoracionEnCurso(String parameter);

	// public ValoracionSerieVO getValoracionSerie(String idSerie);

	/**
	 * Obtiene el número de valoraciones que estan en elaboracion por parte de
	 * un gestor
	 * 
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de valoraciones
	 */
	public int getCountValoracionesEnElaboracion(String idUser);

	/**
	 * Obtiene las valoraciones que estan en elaboracion por parte de un gestor
	 * 
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de valoraciones {@link ValoracionSerieVO}
	 */
	public List getValoracionesEnElaboracion(String idUser);

	/**
	 * Obtiene el número de valoraciones que están a la espera de ser
	 * gestionadas y que dicha gestión puede ser llevada a cabo por el usuario
	 * indicado
	 * 
	 * @param idUser
	 *            Identificador de usuario
	 * @return Número de valoraciones
	 */
	public int getCountValoracionesAGestionar(String idUser);

	/**
	 * Obtiene las valoraciones que están a la espera de ser gestionadas y que
	 * dicha gestión puede ser llevada a cabo por el usuario indicado
	 * 
	 * @param idUser
	 *            Identificador de usuario
	 * @return Lista de valoraciones {@link ValoracionSerieVO}
	 */
	public List getValoracionesAGestionar(String idUser);

	/**
	 * Obtiene la lista de boletines oficiales.
	 * 
	 * @return Lista de boletines oficiales.
	 */
	public List getBoletinesOficiales();

	/**
	 * Obtiene las series con un número mínimo de unidades documentales
	 * seleccionables.
	 * 
	 * @param idFondo
	 *            Identificador del fondo.
	 * @param minNumUdocs
	 *            Número mínimo de unidades documentales.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Series seleccionables ({@link SerieSeleccionableVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getSeriesSeleccionables(String idFondo, int minNumUdocs,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene los datos de identificación de una serie documental en el momento
	 * de cierre de la valoración
	 * 
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Información que conforma la identificación de la serie documental
	 */
	public ValoracionSerie getIdentificacionSerieValoracion(String infoSerie,
			SerieVO serie);

	List getPlazosValoracion(String idValoracion);

	void updateDeletePlazosValoracion(String idValoracion, List plazosNuevos);

	int getCountPlazosEnValoracion(String idValoracion);

	List getValoracionesPorPlazos(String idNivelOrigen, String idNivelDestino);

	List getValoracionesPorIdNivelOrigenDestino(String idNivel);
}
