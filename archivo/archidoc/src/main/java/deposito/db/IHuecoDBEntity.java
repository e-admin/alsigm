package deposito.db;

import ieci.core.exception.IeciTdException;

import java.util.List;
import java.util.Map;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.util.IntervalOptions;

import deposito.model.DepositoException;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;

/**
 * Métodos de acceso a datos referentes a los huecos en los que se dividen cada
 * uno de los elementos del fondo físico manejado por el sistema <br>
 * Entidad: <b>ASGDHUECO</b>
 */
public interface IHuecoDBEntity extends IDBEntity {
	public boolean checkHuecosByDeposito(final String idDeposito,
			final String[] estados);

	public boolean checkHuecosByElementoNoAsignable(
			final String idElementoNoAsignable, final String[] estados);

	public boolean checkHuecosByElementoAsignable(final String idAsignable,
			final String[] estados);

	// private void fillHuecoVO(HuecoVO huecoVO, DbOutputStatement stmt) throws
	// Exception {
	public HuecoVO getHueco(HuecoID idHueco);

	public HuecoVO getHuecoUInstalacion(String unidadInstalacion);

	public List getHuecosEnElemento(String idElementoAsignable);

	public List getHuecosEnElemento2(String idAsignable);

	public List getHuecosEnElementoXEstado(String idElementoAsignable,
			String[] estados);

	/**
	 * Cuenta el número de una ubicación del fondo físico de un determinado
	 * formato y que se encuentran en los estados indicados
	 * 
	 * @param idFormato
	 *            Formato de hueco. Puede ser nulo
	 * @param idDeposito
	 *            Identificador de ubicación
	 * @param estados
	 *            Conjunto de estados de hueco. Puede ser nulo
	 * @return Número de huecos que verifican los criterios
	 */
	public int countHuecosEnUbicacion(String idFormato, String idDeposito,
			String[] estados);

	/**
	 * 
	 * @param idFormato
	 * @return Numero de huecos de un determinado formato
	 */
	public int countHuecosByFormato(String idFormato);

	/**
	 * Recupera los huecos de una ubicación del fondo físico de un determinado
	 * formato y que se encuentran en los estados indicados
	 * 
	 * @param idFormato
	 *            Formato de hueco. Puede ser nulo
	 * @param idDeposito
	 *            Identificador de ubicación
	 * @param estados
	 *            Conjunto de estados de hueco. Puede ser nulo
	 * @return Lista de huecos que verifican los criterios {@link HuecoVO}
	 */
	public List getHuecosEnUbicacion(String idFormato, String idDeposito,
			String[] estados);

	public List getHuecosXRelacionEntregaYEstados(String idRelacionEntrega,
			String[] estados);

	public List getHuecosXId(HuecoID[] ids);

	List getHuecosYSignaturaXId(HuecoID[] ids);

	public void insertHueco(final HuecoVO huecoVO);

	public void deleteHuecos(final String idAsignablePadre);

	public void deleteHuecos(String[] idsPadresAsignables);

	public int updateEstadoHueco(final HuecoID idHueco,
			final String estadoHueco, final String estadoHuecoEsperado);

	/**
	 * Establece el estado del hueco a libre ademas de modificar la fecha de
	 * estado y borrar la relacion de entrega de la que viene y la uinstalacion
	 * 
	 * @param conn
	 * @param idsHuecos
	 * @throws Exception
	 */
	public void setEstadoLibre(HuecoID[] idsHuecos);

	/*
	 * public void updateEstadoHuecoIdRelacionEntrega(HuecoID idHueco, String
	 * estadoHueco, String idRelacionEntrega);
	 */

	public int updateEstadoHuecoIdRelacionEntrega(HuecoID idHueco,
			String estadoHueco, String estadoHuecoEsperado,
			String idRelacionEntrega, Integer ordenEnRelacion);

	public int updateEstadoHuecoIdRelacionEntregaUnidadInstalacion(
			HuecoID idHueco, String estadoHueco, String estadoHuecoEsperado,
			String idRelacionEntrega, String idUnidadInstalacion,
			Integer ordenEnRelacion, boolean isConReencajado);

	/**
	 * Realiza la liberacion de una unidad de instalacion poniendo su estado a
	 * libre 'L' y a nulo el campo IDUINSTALACION
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad a liberar
	 * @throws Exception
	 *             Si se produce un error durante el proceso
	 */
	public void liberaUnidadInstalacion(String idUnidadInstalacion);

	public void updatePathHuecos(final String idAsignablePadre,
			final String parentPath);

	public void liberarHuecosReservados(String idRelacionEntrega);

	public List getHuecosEnElementoXEstadoYPrimeraParteIdentificacion(
			String primeraParteIdentificacion, String[] estados,
			String idElementoAsignable);

	public List getHuecosBetweenXEstadoByCodOrden(HuecoVO huecoOrigenVO,
			HuecoVO huecoDestinoVO, int tipoOrdenacion, String[] estados);

	public List getHuecosNoBloqueados(String idElementoAsignable);

	/**
	 * Permite obtener el número de huecos que están en una ubicación de un
	 * archivo determinado y que tienen unidades de instalación con una
	 * signatura determinada
	 * 
	 * @param signatura
	 *            Signatura a buscar
	 * @param idArchivoReceptor
	 *            IdArchivo de la ubicación donde están los huecos
	 * 
	 * @return número de registros que cumplen las condiciones
	 */
	public int countSignaturaInDeposito(String signatura,
			String idArchivoReceptor);

	public void updateFieldsHuecoVREA(HuecoVO huecoVO);

	public void updateFieldsHueco(String idElemAPadre, int numOrden,
			final Map columnsToUpdate);

	/**
	 * Devuelve el numero de huecos no libres de un depósito
	 * 
	 * @param String
	 *            id del depósito
	 * @return int con el número de huecos no libres de un depósito
	 */
	public int getNumHuecosNoLibres(String idDeposito);

	/**
	 * Devuelve el id del asignable padre de un hueco cualquiera de en los que
	 * se ha ubicado la relación de entrega. Se utiliza para poder acceder
	 * después a ese asignable y poder calcular la longitud ocupada por un hueco
	 * con formato irregular.
	 * 
	 * @param idRelacionEntrega
	 * @return idAsignablePadre
	 */
	public String getIdAsignablePadreXRelacionEntrega(String idRelacionEntrega);

	/**
	 * Comprueba si existe la asignatura en algún deposito perteneciente al
	 * archivo de custodia especificado en el parametro, en cuyo caso se
	 * devuelve el hueco y en otro caso NULL.
	 * 
	 * @param signatura
	 * @param idArchivoReceptor
	 * @return HuecoVO
	 */
	public HuecoVO getHuecoBySignaturaInDeposito(String signatura,
			String idArchivoReceptor);

	/**
	 * Devuelve el hueco en el que se encuentra la unidad de instalación pasada
	 * como parámetro dentro de las ubicaciones del archivo indicado
	 * 
	 * @param idUInstalacion
	 * @param idArchivo
	 * @return HuecoVO
	 */
	public HuecoVO getHuecoUInstalacionXArchivo(String idUInstalacion,
			String idArchivo);

	/**
	 * Obtiene el número de huecos pertenecientes a alguno de los depositos del
	 * archivo.
	 * 
	 * @param idArchivo
	 * @return
	 */
	public int getNumHuecosXArchivo(String idArchivo);

	/**
	 * Obtiene el hueco perteneciente al archivo y cuya numeracion es la misma
	 * que la pasada como parametro.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public HuecoVO getHuecoAsociadoNumeracion(String idArchivo,
			String numeracion);

	int countHuecosAsociadoNumeracion(String idArchivo, String numeracion);

	long getMaxNumeracionHueco(String idArchivo) throws Exception;

	/**
	 * Obtiene la numeracion del hueco segun el tipo de busqueda(min o max) para
	 * obtener el menor o el mayor de las numeraciones de los huecos. En caso de
	 * producirse algun error o no devolver nada retornará -1.
	 * 
	 * @param idElemAPadre
	 * @param tipoBusqueda
	 * @return
	 */
	public long obtenerNumeracionHueco(String idElemAPadre, String tipoBusqueda);

	/**
	 * Comprueba si la renumeracion del hueco se puede llevar a cabo o no.
	 * 
	 * @param huecoVO
	 * @param nuevaNumeracion
	 * @return
	 */
	public boolean comprobarRenumeracionHueco(final HuecoVO huecoVO,
			final String nuevaNumeracion, final String idArchivo)
			throws DepositoException;

	/**
	 * Comprueba si la numeracion ya esta utilizada por alguno de los huecos del
	 * archivo.
	 * 
	 * @param idArchivo
	 * @param numeracion
	 * @return
	 */
	public boolean isNumeracionEnUso(final String idArchivo,
			final String numeracion);

	/**
	 * Se encarga de actualizar la numeración de un determinado hueco
	 * 
	 * @param idHueco
	 * @param nuevaNumeracion
	 */
	public void updateNumeracionHueco(final HuecoID idHueco,
			final String nuevaNumeracion);

	/**
	 * Se encarga de llevar a cabo la renumeración de los huecos siempre que sea
	 * posible.
	 * 
	 * @param idArchivo
	 * @param huecoVO
	 * @param nuevaNumeracion
	 * @param renumerar
	 */
	public void renumerarHuecos(final String idArchivo, final HuecoVO huecoVO,
			final String nuevaNumeracion, final boolean renumerar)
			throws DepositoException;

	/**
	 * Obtiene la numeracion del hueco segun el tipo de busqueda(min o max) para
	 * obtener el menor o el mayor de las numeraciones de los huecos según el
	 * orden.
	 * 
	 * @param idElemAPadre
	 * @param tipoBusqueda
	 * @return
	 */
	public String obtenerNumeracionOrdenHueco(String idElemAPadre,
			String tipoBusqueda);

	/**
	 * Obtener el numero de huecos numericos que tiene un determinado elemento.
	 * 
	 * @param idElementoPadre
	 * @return
	 */
	public int countNumHuecosNumericos(final String idElementoPadre);

	/**
	 * Obtiene el número de Huecos de una Ubicacion
	 * 
	 * @param idUbicacion
	 *            Identificador de la Ubicación.
	 * @return
	 */
	public int getNumHuecosUbicacion(String idUbicacion);

	/**
	 * Actualiza el estado de los huecos que coincidan con la relacion de
	 * entrega
	 * 
	 * @param idRelEntrega
	 *            Identificador de la relación de entrega
	 * @param estadoHueco
	 *            estado se establecer en los huecos afectados
	 */
	public void updateEstadoHuecoUInstalacion(String idRelEntrega,
			String estadoHueco);

	/**
	 * Obtiene los huecos por las signaturas introducidas y el formato de los
	 * huecos del elemento No Asignable
	 * 
	 * @param idElementoNoAsignable
	 * @param idUbicacion
	 * @param idFormato
	 * @param options
	 * @return
	 */
	public List getHuecosBySignaturas(String idElementoNoAsignable,
			String idUbicacion, String idFormato, IntervalOptions options)
			throws IeciTdException, TooManyResultsException;

	/**
	 * Actuliza el valor de la column IDUIREEACR con el valor de la nueva unidad
	 * de instalación
	 * 
	 * @param huecoId
	 *            Identificador del Hueco.
	 * @param idUIREEACR
	 *            Identificador de la unidad de instalación asociada al
	 *            reencajado.
	 */
	public void updateUIConReencajado(HuecoID huecoId, String idUIREEACR);

	/**
	 * Actualiza el valor del campo IDUIREEACR a 'null' de los huecos pasados
	 * como parámetro
	 * 
	 * @param huecosID
	 */
	public void liberarUIsConReencajado(HuecoID[] huecosID);

	/**
	 * Actualizar el
	 * 
	 * @param idUIReeaCR
	 */
	public void updateHuecoIdUInstalacionByIdUIReeaCR(String idRelEntrega,
			String idUIReeaCR, String idUInstalacion);

	/**
	 * Actualizar los huecos de deposito que son hijos del elemento NO asignable
	 * pasado como parámetro
	 * 
	 * @param idNoAsignable
	 * @param pathAntiguo
	 * @param pathNuevo
	 */
	public void updateHuecosElementoNoAsignable(final String idNoAsignable,
			final String pathAntiguo, final String pathNuevo);
}