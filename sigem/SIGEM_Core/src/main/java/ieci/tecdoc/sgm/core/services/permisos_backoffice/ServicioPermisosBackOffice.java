package ieci.tecdoc.sgm.core.services.permisos_backoffice;


public interface ServicioPermisosBackOffice {

	/**
	 * Este método devuelve un array con todos los usuarios con permisos en cualquiera de 
	 * las aplicaciones de administración.
	 * @param entidad Nombre de la entidad 
	 * @return DatosUsuario[] Array con todos los usuarios con permisos en cualquiera de 
	 * las aplicaciones de administración.
	 * @throws PermisosBackOfficeException En caso de producirse alguna excepción.
	 */
	public DatosUsuario[] obtenerUsuarios(String entidad) throws PermisosBackOfficeException;
	
	/**
	 * Este método devuelve un array con todos los usuarios que respondan al criterio de búsqueda.
	 * @param criterio Contiene (al menos) los siguientes valores de búsqueda: usuario, nombre y apellidos.
	 * @param idEntidad Nombre de la entidad
	 * @return DatosUsuario[] Array con todos los usuarios que respondan al criterio de búsqueda
	 * @throws PermisosBackOfficeExceiption
	 */
	public DatosUsuario[] busquedaUsuarios(CriterioBusqueda criterio, String idEntidad) throws PermisosBackOfficeException;
	
	/**
	 * Este método devuelve los datos del usuario que corresponda al id pasado por parámetro
	 * @param idUsuario Identificador del usuario que queremos buscar
	 * @param idEntidad Nombre de la entidad
	 * @return DatosUsuario Usuario que corresponda al identificador
	 * @throws PermisosBackOfficeExceiption
	 */
	public DatosUsuario obtenerDatosUsuario (int idUsuario, String idEntidad) throws PermisosBackOfficeException;
	
	
}
