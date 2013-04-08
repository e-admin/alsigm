package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;
import java.util.Locale;

import net.sf.hibernate.HibernateException;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;

public interface LibroDAO {
	
	/**
	 * Metodo que obtiene el {@link EsquemaLibroVO} mediante un BaseLibroVO
	 * @param libro
	 * @return un {@link EsquemaLibroVO}
	 */
	public EsquemaLibroVO getEsquemaLibroById(BaseLibroVO libro);
	
	/**
	 * Metodo que obtiene todos los libros de entrada
	 * @param locale
	 * @return Lista de objetos {@link BaseLibroVO}
	 */
	public List findLibrosEntrada(Locale locale);
	
	/**
	 * Metodo que obtiene todos los libros de salida
	 * @param locale
	 * @return Lista de objetos {@link BaseLibroVO}
	 */
	public List findLibrosSalida(Locale locale);
	
	/**
	 * Obtiene un libro base {@link BaseLibroVO} a partir de su identificador
	 * 
	 * @param bookId Identificador del libro
	 * @return El {@link BaseLibroVO} con el identificador especificado
	 * @throws HibernateException
	 */
	public BaseLibroVO getLibro(Integer bookId) throws HibernateException;
}
