package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;

public class OficinaLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {
	
	protected OficinaDAO oficinaDao = new OficinaLegacyDAOImpl();
		 
	 public void testPruebaTest(){
		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT);
		 OficinaVO oficina = oficinaDao.getOficinaByCodigo(locale,"001");
		 assertNotNull(oficina);
		 
	 }
	 
	 public void testGetOficinaById(){
		 
		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT); 
		 OficinaVO result = oficinaDao.getOficinaById(locale, "001");
		 
		 assertNotNull(result);
	 }

	 public void testGetOficinaById2(){
		 
		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT); 
		 OficinaVO result = oficinaDao.getOficinaById(locale, "888");
		 
		 assertNull(result);
	 }
	

}
