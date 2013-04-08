package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.LibroDAO;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;

public class LibroLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {

	protected LibroDAO libroDAO = new LibroLegacyDAOImpl();
	protected Locale locale = new Locale(ConstantKeys.LOCALE_LENGUAGE_DEFAULT, ConstantKeys.LOCALE_COUNTRY_DEFAULT);
	
	
	public void testfindLibrosEntrada(){
		List result = libroDAO.findLibrosEntrada(locale);
		assertNotNull(result);
		assertTrue(result.size()>0);
	}
	
	public void testfindLibrosSalida(){
		List result = libroDAO.findLibrosSalida(locale);
		assertNotNull(result);
		assertTrue(result.size()>0);
	}
	
	public void testgetEsquemaLibroById(){
		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("1");
		EsquemaLibroVO result = libroDAO.getEsquemaLibroById(libro);
		assertNotNull(result);
	}
	
	public void testgetEsquemaLibroById2(){
		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("999");
		EsquemaLibroVO result = libroDAO.getEsquemaLibroById(libro);
		assertNull(result);
	}
}
