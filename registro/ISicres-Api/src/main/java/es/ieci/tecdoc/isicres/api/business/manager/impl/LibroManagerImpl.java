/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.isicres.session.book.BookSession;

import es.ieci.tecdoc.isicres.api.business.dao.LibroDAO;
import es.ieci.tecdoc.isicres.api.business.dao.legacy.impl.LibroAdapter;
import es.ieci.tecdoc.isicres.api.business.exception.LibroException;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.PermisosManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroFieldVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 * 
 */
public class LibroManagerImpl extends LibroManager {

	public BaseLibroVO abrirLibro(UsuarioVO usuario, BaseLibroVO libro) {
		if (libro != null && StringUtils.isNotEmpty(libro.getId())
				&& StringUtils.isNumeric(libro.getId())) {

			try {
				BookSession.openBook(usuario.getConfiguracionUsuario()
						.getSessionID(), new Integer(libro.getId()), usuario
						.getConfiguracionUsuario().getIdEntidad());

			} catch (Exception e) {
				StringBuffer sb = new StringBuffer();
				sb.append("Se ha producido un error al abrir el libro[")
						.append(libro.getId()).append("]:").append(
								e.getMessage());
				if (logger.isDebugEnabled()) {
					logger.debug(sb.toString(), e);
				}
				throw new LibroException(sb.toString());
			}

			return libro;
		} else {
			logger.error("No se puede abrir un libro sin identificador");
			throw new LibroException(
					"No se puede abrir un libro sin identificador");
		}
	}

	public BaseLibroVO cerrarLibro(UsuarioVO usuario, BaseLibroVO libro) {
		if (libro != null && StringUtils.isNotEmpty(libro.getId())
				&& StringUtils.isNumeric(libro.getId())) {
			try {
				BookSession.closeBook(usuario.getConfiguracionUsuario()
						.getSessionID(), Integer.valueOf(libro.getId()));
			} catch (Exception e) {
				StringBuffer sb = new StringBuffer();
				sb.append("Se ha producido un error al cerrar el libro[")
						.append(libro.getId()).append("]:").append(
								e.getMessage());
				if (logger.isDebugEnabled()) {
					logger.debug(sb.toString(), e);
				}
				throw new LibroException(sb.toString());
			}
		} else {
			logger.error("No se puede cerrar un libro sin identificador");
			throw new LibroException(
					"No se puede cerrar un libro sin identificador");
		}
		return libro;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.api.business.manager.LibroManager#abrirLibro(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 *      es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO)
	 */
	public BaseLibroVO abrirLibro(UsuarioVO usuario, BaseLibroVO libro,
			TipoLibroEnum tipoLibro) {
		if (libro != null && StringUtils.isNotEmpty(libro.getId())
				&& StringUtils.isNumeric(libro.getId())) {

			try {
				checkBookType(usuario, libro, tipoLibro);

				BookSession.openBook(usuario.getConfiguracionUsuario()
						.getSessionID(), new Integer(libro.getId()), usuario
						.getConfiguracionUsuario().getIdEntidad());

			} catch (Exception e) {
				logger.error("Se ha producido un error al abrir el libro["
						+ libro.getId() + "]", e);
				throw new LibroException(
						"Se ha producido un error al abrir el libro["
								+ libro.getId() + "]");
			}

			return libro;
		} else {
			logger.error("No se puede abrir un libro sin identificador");
			throw new LibroException(
					"No se puede abrir un libro sin identificador");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public BaseLibroVO cerrarLibro(UsuarioVO usuario, BaseLibroVO libro,
			TipoLibroEnum tipoLibro) {
		if (libro != null && StringUtils.isNotEmpty(libro.getId())
				&& StringUtils.isNumeric(libro.getId())) {
			try {
				checkBookType(usuario, libro, tipoLibro);

				BookSession.closeBook(usuario.getConfiguracionUsuario()
						.getSessionID(), Integer.valueOf(libro.getId()));
			} catch (Exception e) {
				logger.error("Se ha producido un error al cerrar el libro["
						+ libro.getId() + "]", e);
				throw new LibroException("Error al cerrar el libro["
						+ libro.getId() + "]", e);
			}
		} else {
			logger.error("No se puede cerrar un libro sin identificador");
			throw new LibroException(
					"No se puede cerrar un libro sin identificador");
		}
		return libro;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.manager.LibroManager#
	 * findLibrosEntradaByUser(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List findLibrosEntradaByUser(UsuarioVO usuario) {
		List result = null;
		List listado = null;

		// Pasos a seguir
		try {
			// - Realizamos la consulta
			listado = (List) BookSession.getInBooks(usuario
					.getConfiguracionUsuario().getSessionID(), usuario
					.getConfiguracionUsuario().getLocale(), usuario
					.getConfiguracionUsuario().getIdEntidad());

			// - Adaptamos el objeto de la consulta al nuevo BaseLibroVO
			result = getLibroAdapter().fromScrRegStatesToBaseLibrosVO(listado);

		} catch (Exception e) {
			logger.warn("Error al obtener los libros de entrada del usuario: "
					+ usuario.toString());
			throw new LibroException(
					"No se ha podido recuperar el listado de libros de entrada para el usuario: ["
							+ usuario.getId() + "]", e);
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.business.manager.LibroManager#findLibrosEntradaIntercambioByUser(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public  List findLibrosEntradaIntercambioByUser(UsuarioVO usuario){
		List <LibroEntradaVO> result = new ArrayList<LibroEntradaVO>();
		List listado = null;
		List <LibroEntradaVO> librosEntrada=null;

		// Pasos a seguir
		try {
			// - Realizamos la consulta
			listado = (List) BookSession.getInBooks(usuario
					.getConfiguracionUsuario().getSessionID(), usuario
					.getConfiguracionUsuario().getLocale(), usuario
					.getConfiguracionUsuario().getIdEntidad());

			// - Adaptamos el objeto de la consulta al nuevo BaseLibroVO
			librosEntrada = getLibroAdapter().fromScrRegStatesToBaseLibrosVO(listado);
			
			for (Iterator iterator = librosEntrada.iterator(); iterator
					.hasNext();) {
				LibroEntradaVO libroEntrada = (LibroEntradaVO) iterator
						.next();
				if  (isLibroIntercambioRegistral(libroEntrada)){
		             result.add(libroEntrada);
					}
			}
			
			

		} catch (Exception e) {
			logger.warn("Error al obtener los libros de entrada del usuario: "
					+ usuario.toString());
			throw new LibroException(
					"No se ha podido recuperar el listado de libros de entrada para el usuario: ["
							+ usuario.getId() + "]", e);
		}

		return result;
		
	}
	
	/**
	 * Metodo que nos dice si un libro es de intercambio registral, tanto de entrada como de salida
	 * @param libro
	 * @return
	 */
	public boolean isLibroIntercambioRegistral(BaseLibroVO libro){
		boolean result=false;
		
		EsquemaLibroVO esquemaLibro = getLibroDAO().getEsquemaLibroById(libro);
		
		//comprobamos que este libro tiene uno de los campos de intercambio registral dentro de su esquema
		if (CollectionUtils.exists(esquemaLibro.getCamposLibro(),new Predicate() {  
	          		public boolean evaluate(Object o) {  
	              	          return ((EsquemaLibroFieldVO) o).getId().equals(Integer.toString(AxSf.FLD503_FIELD_ID));
	          		}})
				){
			result=true;
		}
			
		
		return result;
	}

	
	public List findLibrosSalidaIntercambioByUser(UsuarioVO usuario) {
		List<LibroSalidaVO> result = new ArrayList<LibroSalidaVO>();
		List<LibroSalidaVO> librosSalida=null;
		List listado = null;
		
		try {
			// - Realizamos la consulta
			listado = (List) BookSession.getOutBooks(usuario
					.getConfiguracionUsuario().getSessionID(), usuario
					.getConfiguracionUsuario().getLocale(), usuario
					.getConfiguracionUsuario().getIdEntidad());

			// - Adaptamos el objeto de la consulta al nuevo BaseLibroVO
			librosSalida = getLibroAdapter().fromScrRegStatesToBaseLibrosVO(listado);
			
			for (Iterator iterator = librosSalida.iterator(); iterator.hasNext();) {
				LibroSalidaVO libroSalida = (LibroSalidaVO) iterator.next();
				if  (isLibroIntercambioRegistral(libroSalida)){
		             result.add(libroSalida);
					}
			}
			
			

		} catch (Exception e) {
			logger.warn("Error al obtener los libros de salida del usuario: "
					+ usuario.toString());
			throw new LibroException(
					"No se ha podido recuperar el listado de libros de salida para el usuario: ["
							+ usuario.getId() + "]", e);
		}
		return result;
	}
	
	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.business.manager.LibroManager#findLibrosSalidaByUser(es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public  List findLibrosSalidaByUser(UsuarioVO usuario){
		List result = null;
		List listado = null;
		try {
			// - Realizamos la consulta
			listado = (List) BookSession.getOutBooks(usuario
					.getConfiguracionUsuario().getSessionID(), usuario
					.getConfiguracionUsuario().getLocale(), usuario
					.getConfiguracionUsuario().getIdEntidad());
			
			

			// - Adaptamos el objeto de la consulta al nuevo BaseLibroVO
			result = getLibroAdapter().fromScrRegStatesToBaseLibrosVO(listado);

		} catch (Exception e) {
			logger.warn("Error al obtener los libros de salida del usuario: "
					+ usuario.toString());
			throw new LibroException(
					"No se ha podido recuperar el listado de libros de salida para el usuario: ["
							+ usuario.getId() + "]", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.manager.LibroManager#getEsquemaLibro
	 * (es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO,
	 * es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO)
	 */
	public EsquemaLibroVO getEsquemaLibro(UsuarioVO usuario, BaseLibroVO libro) {
		EsquemaLibroVO result = null;

		PermisosLibroVO permisosUsuarioLibro = getPermisosManager()
				.getPermisosLibro(libro, usuario);
		if (permisosUsuarioLibro.isAdministrador()
				|| permisosUsuarioLibro.isConsulta()) {

			// Pasos a seguir
			// - Abrir Libro
			libro = abrirLibro(usuario, libro);

			// - Obtenemos el esquema del libro
			result = getLibroDAO().getEsquemaLibroById(libro);

			return result;
		} else {
			StringBuffer sb = new StringBuffer("El usuario [")
					.append(usuario.getLoginName())
					.append(
							"] no tiene permisos suficientes para recuperar el esquema del libro [")
					.append(libro.getId()).append("]");
			throw new java.lang.SecurityException(sb.toString());
		}

	}

	public BaseLibroVO getLibro(UsuarioVO usuario, Integer idLibro){
		//TODO: PROBAR
		try {
			return getLibroDAO().getLibro(idLibro);
		} catch (Exception e) {
			logger.warn("Error al obtener el libro con id: "
					+ idLibro);
			throw new LibroException(
					"No se ha podido recuperar el libro con identificador: ["
							+ idLibro + "]", e);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	public List findLibrosByUser(UsuarioVO usuario, TipoLibroEnum bookType) {
		List result = null;

		if (TipoLibroEnum.ENTRADA.equals(bookType)) {
			result = findLibrosEntradaByUser(usuario);
		} else if (TipoLibroEnum.SALIDA.equals(bookType)) {
			result = findLibrosSalidaByUser(usuario);
		}

		return result;
	}

	public LibroDAO getLibroDAO() {
		return libroDAO;
	}

	public void setLibroDAO(LibroDAO libroDAO) {
		this.libroDAO = libroDAO;
	}

	public LibroAdapter getLibroAdapter() {
		return libroAdapter;
	}

	public void setLibroAdapter(LibroAdapter libroAdapter) {
		this.libroAdapter = libroAdapter;
	}

	public PermisosManager getPermisosManager() {
		return permisosManager;
	}

	public void setPermisosManager(PermisosManager permisosManager) {
		this.permisosManager = permisosManager;
	}

	/**
	 * Verifica que <code>libro</code> es del tipo indicado en
	 * <code>tipoLibro</code>.
	 * 
	 * @param usuario
	 * @param libro
	 * @param tipoLibro
	 * @throws LibroException
	 *             en caso de que el libro no sea del tipo indicado
	 */
	private void checkBookType(UsuarioVO usuario, BaseLibroVO libro,
			TipoLibroEnum tipoLibro) {
		boolean checkType = false;
		if (TipoLibroEnum.ENTRADA.equals(tipoLibro)) {
			checkType = CollectionUtils.exists(
					findLibrosEntradaByUser(usuario),
					new BeanPropertyValueEqualsPredicate("id", libro.getId()));
		} else {
			checkType = CollectionUtils.exists(findLibrosSalidaByUser(usuario),
					new BeanPropertyValueEqualsPredicate("id", libro.getId()));
		}

		if (!checkType) {
			StringBuffer sb = new StringBuffer();
			sb.append("El libro con identificador [").append(libro.getId())
					.append("] no es de tipo [").append(tipoLibro.getName())
					.append("]");

			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString());
			}

			throw new LibroException(sb.toString());
		}
	}

	// Members
	protected PermisosManager permisosManager;

	protected LibroDAO libroDAO;

	protected LibroAdapter libroAdapter;

	private static final Logger logger = Logger
			.getLogger(LibroManagerImpl.class);

	

}
