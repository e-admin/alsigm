package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class OficinaLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {

	protected OficinaDAO oficinaDao = new OficinaLegacyDAOImpl();

	 public void testGetOficinaByCodigo(){
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
		 OficinaVO result = oficinaDao.getOficinaById(locale, "2");

		 assertNotNull(result);
	 }

	 public void testGetOficinaById2(){

		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT);
		 OficinaVO result = oficinaDao.getOficinaById(locale, "888");

		 assertNull(result);
	 }

	 public void testFindOficinaByUsuario(){

		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT);

		 UsuarioVO usuarioVO = new UsuarioVO();
		 usuarioVO.setId(3);
		 List<OficinaVO> oficinas = oficinaDao.getOficinasByUsuario(locale, usuarioVO);
		 Assert.assertEquals(2, oficinas.size());
	 }

	 public void testGetOficinas(){
		 Locale locale = new Locale(
					ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
					ConstantKeys.LOCALE_COUNTRY_DEFAULT);
		 List<OficinaVO> oficinas = oficinaDao.getOficinas(locale);

		 Assert.assertEquals(2, oficinas.size());

	 }


}
