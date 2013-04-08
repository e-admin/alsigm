package common.bi;

import gcontrol.vos.ArchivoVO;

import java.util.List;

import se.usuarios.AppUser;

import common.vos.BandejaActividadesVO;

/**
 * Bussines Interface con los metodos de negocio para el modulo de sistema.
 */
public interface GestionSistemaBI {
	/**
	 * Registra el intento de acceso de un usuario al sistema
	 * 
	 * @param identifier
	 *            Identificador del usuario
	 * @param ip
	 *            Dirección IP desde la que el usuario accede al sistema
	 * @param isOk
	 *            <b>true</b> si el acceso al sistema ha sido garantizado por el
	 *            subsistema de autentificación y control de acceso y
	 *            <b>false</b> en caso contrario
	 */
	public void doLogin(String identifier, String ip, boolean isOk);

	/**
	 * Registra la salida de un usuario del sistema
	 */
	public void doLogout();

	/**
	 * Obtiene la lista de soportes de documentación que están actualmente
	 * definidos en el sistema
	 * 
	 * @return Lista de posibles valores de soporte
	 *         {@link descripcion.vos.TextoTablaValidacionVO}
	 */
	public List getListaSoportes();

	/**
	 * Obtiene la lista de archivos cuya gestión está siendo realizada por el
	 * sistema
	 * 
	 * @return Lista de archivos {@link ArchivoVO}
	 */
	public List getArchivos();

	/**
	 * Obtiene la lista de formtos de documento que están actualmente definidos
	 * en el sistema
	 * 
	 * @return Lista de valores con los formatos de documento
	 *         {@link descripcion.vos.TextoTablaValidacionVO}
	 */
	public Object getListaFormatosDocumento();

	/**
	 * Obtiene la informacion referente a un archivo
	 * 
	 * @param idArchivoReceptor
	 *            Identificador del archivo
	 * @return Datos del archivo {@link ArchivoVO}
	 */
	public ArchivoVO getArchivo(String idArchivoReceptor);

	/**
	 * Obtiene por codigo de archivo. En caso de exsitri mas de uno devolvera el
	 * primero
	 * 
	 * @param codigoArchivoReceptor
	 * @return Datos del archivo {@link ArchivoVO}
	 */
	public ArchivoVO getArchivoXCodigo(String codigoArchivoReceptor);

	/**
	 * Construye un resumen de las actividades que tiene pendientes un usuario
	 * 
	 * @param userVO
	 *            Usuario para el que se solicitan la actividades pendientes
	 * @return Resumen de las actividades pendientes del usuario
	 */
	public BandejaActividadesVO getActividadesUsuario(AppUser userVO);

	/**
	 * Obtiene la lista de posibles tipos de entrega de unidades documentales
	 * para préstamos y consultas que están actualmente definidos en el sistema
	 * 
	 * @return Lista de tipos de entrega
	 *         {@link descripcion.vos.TextoTablaValidacionVO}
	 */
	public List getListaTiposEntrega();

}
