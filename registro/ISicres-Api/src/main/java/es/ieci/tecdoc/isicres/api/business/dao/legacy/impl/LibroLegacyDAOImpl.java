package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrct;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdreu;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrgl;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.VFldVldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.ValidationFormat;

import es.ieci.tecdoc.isicres.api.business.dao.LibroDAO;
import es.ieci.tecdoc.isicres.api.business.exception.LibroException;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;

public class LibroLegacyDAOImpl extends IsicresBaseHibernateDAOImpl implements LibroDAO{

	private static final Logger log = Logger.getLogger(LibroLegacyDAOImpl.class);
	
	protected LibroAdapter libroAdapter;
	
	public static final int IDOCARCHDET_FLD_DEF = 1;
	public static final int IDOCARCHDET_VLD_DEF = 5;
	
	
	public LibroLegacyDAOImpl(){
		this.libroAdapter=new LibroAdapter();
	}
	
	protected String getSessionID(){
		return null;
	}
		
	/* (non-Javadoc)
	 * 
	 * Metodo que obtiene el equema del Libro pasado como parametro
	 * 
	 * @see es.ieci.tecdoc.isicres.api.business.dao.LibroDAO#getEsquemaLibroById(es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO)
	 */
	public EsquemaLibroVO getEsquemaLibroById(BaseLibroVO libro)  {

		EsquemaLibroVO result = null;
		
		try {
			//obtiene la informacion de los campos del libro
			Idocarchdet idocarchdetFldDef = getIdocarchdet(new Integer(libro.getId()), new Integer(IDOCARCHDET_FLD_DEF));
			Idocarchdet idocarchdetVldDef = getIdocarchdet(new Integer(libro.getId()), new Integer(IDOCARCHDET_VLD_DEF));
			result=getLibroAdapter().getEsquemaLibro(idocarchdetFldDef,idocarchdetVldDef);
			
		} catch (HibernateException e) {
			log.warn("No se puede recuperar el esquema del libro para el libro: " + libro.toString());
			throw new LibroException("No se puede recuperar el esquema del libro con id "+ libro.getId(),e);
		}

		return result;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.business.dao.LibroDAO#findLibrosEntrada(java.util.Locale)
	 */
	public List findLibrosEntrada(Locale locale){
		
		return findLibros(locale, ISicresKeys.SCRREGSTATE_IN_BOOK);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.api.business.dao.LibroDAO#findLibrosSalida(java.util.Locale)
	 */
	public List findLibrosSalida(Locale locale){
		
		return findLibros(locale, ISicresKeys.SCRREGSTATE_OUT_BOOK);
	}
	
	/**
	 * Metodo que obtiene una Lista de BaseLibroVO segun su tipo (0:Standar-1:Entrada-2:Salida)
	 * @param locale
	 * @param tipoLibro
	 * @return listado de {@link BaseLibroVO}
	 */
	protected List findLibros(Locale locale, int tipoLibro){
		List result = null;
		try {
			//obtenemos los libros
			List scrRegStates = getScrRegstateByTipo(new Integer(tipoLibro));
			
			if (scrRegStates != null){

				//obtenemos los esquemas de los libros
				Map esquemasLibros = getEsquemaLibros(scrRegStates);
				
				//adaptamos los datos
				result = getLibroAdapter().librosAdapter(scrRegStates, esquemasLibros);
			}
		
		} catch (HibernateException e) {
			log.warn("No se puede recuperar la lista de libro del tipo: " + tipoLibro);
			throw new LibroException("No se puede recuperar la lista de libros de tipo [" + tipoLibro + "] ",e);
		}
	
		return result;		
	}
	
	
	/**
	 * Metodo que obtiene los esquemaLibroVo de los libros pasados como parametro
	 * 
	 * @param session
	 * @param scrRegStates
	 * @return una coleccion compuesta por idLibro y el {@link EsquemaLibroVO}
	 */
	protected Map getEsquemaLibros(List scrRegStates){
		Map result = new HashMap();
		
		EsquemaLibroVO esquemaLibro = null;
		for (Iterator it = scrRegStates.iterator(); it.hasNext();){
			ScrRegstate scrregstate = (ScrRegstate) it.next();

			BaseLibroVO libro = new LibroEntradaVO();
			libro.setId(scrregstate.getIdocarchhdr().getId().toString());

			esquemaLibro = getEsquemaLibroById(libro);
			
			result.put(libro.getId(), esquemaLibro);
		}
		
		return result;
	}
	
	/**
	 * Metodo obtiene los campos que son validados
	 * 
	 * @param idLibro
	 * @return listado de id de fields
	 * @throws NumberFormatException
	 * @throws HibernateException
	 */
	protected List getCamposValidados(String idLibro) throws NumberFormatException, HibernateException{
		List fields = new ArrayList();
		//Recuperamos de BBDD la lista de campos validados para un libro
		Idocarchdet idocarchdet = getIdocarchdet(new Integer(idLibro), new Integer(IDOCARCHDET_VLD_DEF));
		
		if(idocarchdet != null){
			//Formateamos la informacion recuperada a formato ValidationFormat
			ValidationFormat validationFormat = new ValidationFormat(idocarchdet.getDetval());
			
			//Generamos un array con el ID de los campos validados
			for (Iterator it = validationFormat.getFldvlddefs().values().iterator(); it.hasNext();) {
				VFldVldDef vldDef = (VFldVldDef) it.next();
				fields.add(new Integer(vldDef.getFldid()));
			}
		}
		return fields;		
	}
	
	/**
	 * Metodo que nos devuelve la clase a la que debemos acceder por el idioma
	 * @param language
	 * @return un objeto tipo Class
	 */
	protected static Class getIdocarchhdrLanguage(String language){
		Class idocarchhdrLanguage = Idocarchhdr.class;
		if (language.equals("es")){
			idocarchhdrLanguage = Idocarchhdr.class;
		}
		if (language.equals("eu")){
			idocarchhdrLanguage = Idocarchhdreu.class;
		}
		if (language.equals("gl")){
			idocarchhdrLanguage = Idocarchhdrgl.class;
		}
		if (language.equals("ca")){
			idocarchhdrLanguage = Idocarchhdrct.class;
		}
		
		return idocarchhdrLanguage;
	}
	
	/**
	 * Obtiene los objetos de {@link ScrRegstate} que representa libros segun su
	 * tipo TIPO: 0-standar 1-entrada o 2-salida
	 * @param tipoLibro
	 * @return listado de objeto {@link ScrRegstate}
	 * @throws HibernateException
	 */
	protected List getScrRegstateByTipo(Integer tipoLibro)
			throws HibernateException {
		Session session = null;
		List result = null;
		try{
			session = getSession();
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HibernateKeys.HIBERNATE_ScrRegstate);
			query.append(" scr WHERE scr.idocarchhdr.type=?");
			result = session.find(query.toString(), tipoLibro, Hibernate.INTEGER);
		}finally{
			this.closeSession(session);
		}
		
		return result;
	}

	/**
	 * Metodo que obtiene la informacion de la tabla Idocarchhdr segun el idioma y el id del libro
	 * @param locale
	 * @param bookId
	 * @return retorna un objeto {@link Idocarchhdr}
	 * @throws HibernateException
	 */
	protected Idocarchhdr getIdocarchhdr(Locale locale, Integer bookId) throws HibernateException{
		
		Idocarchhdr result = null;
		List listado = null;
		
		//generamos la consulta
		StringBuffer query = new StringBuffer();
		query.append(" id = ");
		query.append(bookId);
		
		//obtiene el listado de tipo de unidades adminitrativas
		listado = executeCriteriaReturnIdocarchhdrList(locale, query);
		
		//comprobamos que el listado solo nos devuelve un objeto 
		Object idocarchhdr = DataAccessUtils.uniqueResult(listado);
		if(idocarchhdr!=null){
			result = new Idocarchhdr();
			//parseamos el objeto a un objeto tipo ScrCa()
			BeanUtils.copyProperties(idocarchhdr, result);
		}
		return result;
		
	}
	
	public BaseLibroVO getLibro(Integer bookId) throws HibernateException{
		
		
		BaseLibroVO libroABuscar = new BaseLibroVO(String.valueOf(bookId));
		EsquemaLibroVO esquemaLibro = getEsquemaLibroById(libroABuscar);
		Session session = null;
		
		ScrRegstate scrRegstate = null;
		try{
			session = getSession();
			// compone la consulta que debemos lanzar
			scrRegstate = ISicresQueries.getScrRegstate(session, bookId);			
		}finally{
			this.closeSession(session);
		}
		BaseLibroVO baseLibroVO = getLibroAdapter().libroAdapter(scrRegstate, esquemaLibro);
		return baseLibroVO;
		
	}
	
	/**
	 * Metodo que ejecuta la consulta pasada como parametro mediante Criteria de Hibernate
	 * @param locale
	 * @param query
	 * @return listado de objeto tipo {@link Idocarchhdr}
	 * @throws HibernateException
	 */
	protected List executeCriteriaReturnIdocarchhdrList(Locale locale, StringBuffer query) throws HibernateException{
		Session session = null;
		List result = null;
		try{
			session = getSession();
			// compone la consulta que debemos lanzar
			Criteria criteriaResults = session.createCriteria(
					getIdocarchhdrLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.sql(query.toString()));
	
			//obtiene el listado de tipo de unidades adminitrativas
			result = criteriaResults.list();
		}finally{
			this.closeSession(session);
		}
		
		return result;
	}
		
	/**
	 * Metodo que obtiene la informacion de los campos del libro
	 * @param bookId
	 * @param dettype
	 * @return listado de objetos {@link Idocarchdet}
	 * @throws HibernateException
	 */
	protected Idocarchdet getIdocarchdet(Integer bookId,
			Integer dettype) throws HibernateException {
		Idocarchdet result = null;
		List listadoIdocarchdet = null;
		Session session = null;
		try{
			session = getSession();
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HibernateKeys.HIBERNATE_Idocarchdet);
			query.append(" idoc WHERE idoc.archid=? and idoc.dettype=?");
			listadoIdocarchdet = session.find(query.toString(), new Object[] { bookId, dettype },
					new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
			if (listadoIdocarchdet != null){
				result = (Idocarchdet) DataAccessUtils.uniqueResult(listadoIdocarchdet);
			}
		}finally{
			this.closeSession(session);
		}
		return result;
	}

	
	public LibroAdapter getLibroAdapter() {
		return libroAdapter;
	}

	public void setLibroAdapter(LibroAdapter libroAdapter) {
		this.libroAdapter = libroAdapter;
	}
	
	
}
