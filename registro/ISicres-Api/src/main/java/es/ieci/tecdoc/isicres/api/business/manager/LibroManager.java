package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public abstract class LibroManager {

	/**
	 * Método para abrir un libro.
	 * 
	 * Esta operación es básica para poder realizar operaciones sobre un libro.
	 * Es equivalente a la accion de seleccionar un libro en la aplicación web.
	 * 
	 * @param libro
	 * @return
	 */
	public abstract BaseLibroVO abrirLibro(UsuarioVO usuario, BaseLibroVO libro);

	/**
	 * Método para abrir un libro en un modo específico. Es decir, se puede
	 * abrir el libro en modo ENTRADA o SALIDA.
	 * 
	 * Esta operación es básica para poder realizar operaciones sobre un libro.
	 * Es equivalente a la accion de seleccionar un libro en la aplicación web.
	 * 
	 * @param libro
	 * @return
	 */
	public abstract BaseLibroVO abrirLibro(UsuarioVO usuario,
			BaseLibroVO libro, TipoLibroEnum tipoLibro);

	/**
	 * 
	 * @param usuario
	 * @param libro
	 * @return
	 */
	public abstract BaseLibroVO cerrarLibro(UsuarioVO usuario, BaseLibroVO libro);

	/**
	 * 
	 * @param usuario
	 * @param libro
	 * @return
	 */
	public abstract BaseLibroVO cerrarLibro(UsuarioVO usuario,
			BaseLibroVO libro, TipoLibroEnum tipoLibro);

	/**
	 * Obteniene el esquema del libro
	 * 
	 * @param usuario
	 * @param libro
	 * @return {@link EsquemaLibroVO}
	 */
	public abstract EsquemaLibroVO getEsquemaLibro(UsuarioVO usuario,
			BaseLibroVO libro);

	/**
	 * Obtiene los libros de entrada disponibles para el usuario
	 * 
	 * @param usuario
	 * @return lista de objetos tipo {@link BaseLibroVO}
	 */
	public abstract List findLibrosEntradaByUser(UsuarioVO usuario);
	
	/**
	 * Obtiene los libros de entrada disponibles para el usuario y de intercambioRegistral
	 * 
	 * @param usuario
	 * @return lista de objetos tipo {@link BaseLibroVO}
	 */
	public abstract List findLibrosEntradaIntercambioByUser(UsuarioVO usuario);
	
	

	/**
	 * Obtiene los libros de salida disponibles para el usuario
	 * 
	 * @param usuario
	 * @return lista de objetos tipo {@link BaseLibroVO}
	 */
	public abstract List findLibrosSalidaByUser(UsuarioVO usuario);
	
	/**
	 * Obtiene los libros de salida disponibles para el usuario y de intercambioRegistral
	 * 
	 * @param usuario
	 * @return lista de objetos tipo {@link BaseLibroVO}
	 */
	public abstract List findLibrosSalidaIntercambioByUser(UsuarioVO usuario);

	/**
	 * Obtiene los libros del usuario <code>usuario</code> que sean del tipo
	 * <code>bookType</code>
	 * 
	 * @param usuario
	 *            credenciales del usuario para el que se recuperan los libros
	 * @param bookType
	 *            tipo de libro a recuperar
	 * @return
	 */
	public List findLibrosByUser(UsuarioVO usuario, TipoLibroEnum bookType) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Obtiene un libro base {@link BaseLibroVO} a partir de su identificador
	 * 
	 * @param bookId Identificador del libro
	 * @return El {@link BaseLibroVO} con el identificador especificado
	 */
	public abstract BaseLibroVO getLibro(UsuarioVO usuario, Integer idLibro);
	
	/**
	 * Metodo que nos dice si un libro es de intercambio registral, tanto de 
	 * entrada como de salida.
	 * 
	 * @param libro
	 * @return
	 */
	public abstract boolean isLibroIntercambioRegistral(BaseLibroVO libro);
	
	

}
