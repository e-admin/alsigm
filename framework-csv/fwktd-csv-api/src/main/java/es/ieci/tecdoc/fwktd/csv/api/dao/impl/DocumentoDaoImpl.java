package es.ieci.tecdoc.fwktd.csv.api.dao.impl;

import es.ieci.tecdoc.fwktd.csv.api.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.csv.api.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;

/**
 * DAO de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoDaoImpl extends IbatisGenericDaoImpl<DocumentoVO, String>
		implements DocumentoDao {

	protected static final String GET_DOCUMENTO_BY_CSV = "DocumentoVO.getDocumentoVOByCSV";
	protected static final String DELETE_DOCUMENTO_BY_CSV = "DocumentoVO.deleteDocumentoVOByCSV";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public DocumentoDaoImpl(Class<DocumentoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericReadOnlyDaoImpl#get(java.io.Serializable)
	 */
	public DocumentoVO get(String id) {

		logger.info("Obteniendo el documento [{}]", id);

		return (DocumentoVO) getSqlMapClientTemplate().queryForObject(
				getFindQuery(getPersistentClass()), id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.dao.DocumentoDao#getDocumentoByCSV(java.lang.String)
	 */
	public DocumentoVO getDocumentoByCSV(String csv) {

		logger.info("Obteniendo el documento a partir del CSV [{}]", csv);

		return (DocumentoVO) getSqlMapClientTemplate().queryForObject(
				GET_DOCUMENTO_BY_CSV, csv);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.dao.DocumentoDao#deleteDocumentoByCSV(java.lang.String)
	 */
	public void deleteDocumentoByCSV(String csv) {

		logger.info("Eliminando el documento a partir del CSV [{}]", csv);

		getSqlMapClientTemplate().delete(DELETE_DOCUMENTO_BY_CSV, csv);
	}
}
