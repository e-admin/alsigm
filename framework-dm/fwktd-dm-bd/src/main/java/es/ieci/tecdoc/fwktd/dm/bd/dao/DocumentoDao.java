package es.ieci.tecdoc.fwktd.dm.bd.dao;

import javax.sql.DataSource;

import es.ieci.tecdoc.fwktd.dm.bd.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;


public interface DocumentoDao extends BaseDao<DocumentoVO, String>{

	public void setDataSource(DataSource dataSource);

	public byte[] getContenidoDocumento(String id);

	public DocumentoVO updateWithContent(DocumentoVO documento);

}
