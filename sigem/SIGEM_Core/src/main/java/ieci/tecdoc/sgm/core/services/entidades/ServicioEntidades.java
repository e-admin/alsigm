package ieci.tecdoc.sgm.core.services.entidades;

import java.util.List;

public interface ServicioEntidades {

	/**
	 * Método que da de alta una nueva entidad
	 * @param poEntidad Datos de la entidad
	 * @return Entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad nuevaEntidad(Entidad poEntidad) throws EntidadesException;
	
	/**
	 * Método que elimina una entidad
	 * @param poEntidad Datos de la entidad a eliminar
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public void eliminarEntidad(Entidad poEntidad) throws EntidadesException;
	
	/**
	 * Método que actualiza los datos de una entidad
	 * @param poEntidad Datos de la entidad a actualizar
	 * @return Entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad actualizarEntidad(Entidad poEntidad) throws EntidadesException;
	
	/**
	 * Método que obtiene los datos de una entidad a partir de su código
	 * @param identificador Identificador de la entidad a obtener
	 * @return Datos de la entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad obtenerEntidad(String identificador) throws EntidadesException;
	
	/**
	 * Método que obtiene un listado de entidad a partir de un criterio de búsqueda
	 * @param poCriterio Criterio de búsqueda
	 * @return Listado de entidades
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public List buscarEntidades(CriterioBusquedaEntidades poCriterio) throws EntidadesException;

	/**
	 * Método que obtiene un listado de todas las entidades del sistema
	 * @return Listado de entidades
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public List obtenerEntidades() throws EntidadesException;
	
	/**
	 * Método que obtiene el siguiente identificador de entidad
	 * @return Identificador de la nueva entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public String obtenerIdentificadorEntidad() throws EntidadesException;
}
