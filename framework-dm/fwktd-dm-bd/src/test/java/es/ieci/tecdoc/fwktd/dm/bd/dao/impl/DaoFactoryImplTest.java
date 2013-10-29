/*package es.ieci.tecdoc.fwktd.dm.bd.dao.impl;

import junit.framework.Assert;

import org.junit.Test;

import es.ieci.tecdoc.fwktd.dm.bd.BaseDocumentoTest;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DaoFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public class DaoFactoryImplTest extends BaseDocumentoTest {


	@Test
	public void testGetDocumentoDao() {

		DaoFactory daoFactory = getDaoFactory();
		Assert.assertNotNull("DaoFactory nulo", daoFactory);

		ContentType contentType = getConfiguration().findContentTypeByName(CONTENT_TYPE_BD);
		DocumentoDao documentoDao = daoFactory.getDocumentoDao(contentType);
		Assert.assertNotNull("DocumentoDao nulo", documentoDao);
	}

}
*/