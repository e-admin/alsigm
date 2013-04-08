package es.ieci.tecdoc.fwktd.dm.bd;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dm.bd.dao.DaoFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DataSourceFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.dm.business.config.Configuration;
import es.ieci.tecdoc.fwktd.dm.business.config.ConfigurationFactory;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;

@ContextConfiguration( {
	"/beans/fwktd-dm-core-applicationContext.xml",
	"/beans/fwktd-dm-bd-applicationContext.xml" })
public abstract class BaseDocumentoTest extends AbstractJUnit4SpringContextTests {

	protected static final String ENTIDAD 					= "000";
	protected static final String ENTIDAD_NO_EXISTENTE 	= "999";

	protected static final String ID_DOCUMENTO_NO_EXISTENTE_BD	= "0";

	protected static final String CONTENT_TYPE_BD 				= "bd";

	protected static final String DATASOURCE_FACTORY_BEAN_NAME	= "fwktd_dm_bd_dataSourceFactory";
	protected static final String DOCUMENTO_DAO_BEAN_NAME		= "fwktd_dm_bd_documentoDao";

	protected static final String JDBC_DATASOURCE = "JDBC_DATASOURCE";
	protected static final String JDBC_DRIVER_CLASS_NAME = "JDBC_DRIVER_CLASS_NAME";
	protected static final String JDBC_URL = "JDBC_URL";
	protected static final String JDBC_USERNAME = "JDBC_USERNAME";
	protected static final String JDBC_PASSWORD = "JDBC_PASSWORD";

	@Autowired
	private ConfigurationFactory fwktd_dm_configurationFactory;

	@Autowired
	private GestionDocumentalServiceFactory fwktd_dm_serviceFactory;

	@Autowired
	private DaoFactory fwktd_dm_bd_daoFactory;

	private static ThreadLocal<String> idDocumento = new ThreadLocal<String>();


	protected Configuration getConfiguration() {
		return fwktd_dm_configurationFactory.getConfiguration();
	}

	protected GestionDocumentalServiceFactory getGestionDocumentalServiceFactory() {
		return fwktd_dm_serviceFactory;
	}

	protected GestionDocumentalService getService() {
		return fwktd_dm_serviceFactory.getService(CONTENT_TYPE_BD);
	}

	protected DaoFactory getDaoFactory() {
		return fwktd_dm_bd_daoFactory;
	}

	protected DocumentoDao getDocumentoDao() {
		ContentType contentType = getConfiguration().findContentTypeByName(CONTENT_TYPE_BD);

		DataSourceFactory dataSourceFactory = (DataSourceFactory) applicationContext.getBean(DATASOURCE_FACTORY_BEAN_NAME);
		DataSource dataSource = dataSourceFactory.getDataSource(contentType);

		DocumentoDao documentoDao = (DocumentoDao) applicationContext.getBean(DOCUMENTO_DAO_BEAN_NAME);
		documentoDao.setDataSource(dataSource);

		return documentoDao;
	}

	protected void setIdDocumento(String id) {
		idDocumento.set(id);
	}

	protected String getIdDocumento() {
		return idDocumento.get();
	}

}
