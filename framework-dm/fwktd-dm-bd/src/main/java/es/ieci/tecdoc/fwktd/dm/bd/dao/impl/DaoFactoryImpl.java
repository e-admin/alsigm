package es.ieci.tecdoc.fwktd.dm.bd.dao.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import es.ieci.tecdoc.fwktd.dm.bd.dao.DaoFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DataSourceFactory;
import es.ieci.tecdoc.fwktd.dm.bd.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public class DaoFactoryImpl implements DaoFactory, ApplicationContextAware {

	private DataSourceFactory dataSourceFactory = null;

	private ApplicationContext applicationContext = null;

	private String documentoDaoBean = null;


	/**
	 * Constructor.
	 */
	public DaoFactoryImpl() {
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	public String getDocumentoDaoBean() {
		return documentoDaoBean;
	}

	public void setDocumentoDaoBean(String documentoDaoBean) {
		this.documentoDaoBean = documentoDaoBean;
	}

	/**
	 * Obtiene el DAO para la gestión de documentos.
	 *
	 * @param contentType
	 *            Información del tipo de contenido.
	 * @return DAO para la gestión de documentos.
	 */
	public DocumentoDao getDocumentoDao(ContentType contentType) {

		DocumentoDao documentoDao = null;

		if (getApplicationContext() != null) {
			documentoDao = (DocumentoDao) getApplicationContext().getBean(getDocumentoDaoBean());
			if (documentoDao != null) {
				documentoDao.setDataSource(dataSourceFactory.getDataSource(contentType));
			}
		}

		return documentoDao;
	}

}
