package es.ieci.tecdoc.fwktd.dm.bd.dao.impl;

import java.sql.Blob;

import javax.sql.DataSource;

import es.ieci.tecdoc.fwktd.dm.bd.dao.DocumentoDao;
import es.ieci.tecdoc.fwktd.dm.bd.vo.DocumentoVO;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.util.file.FileUtils;

public class DocumentoDaoImpl extends IbatisGenericDaoImpl<DocumentoVO, String>
		implements DocumentoDao {

	public DocumentoDaoImpl(Class<DocumentoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public void setDataSource(DataSource dataSource) {
		getSqlMapClientTemplate().setDataSource(dataSource);
	}

	public byte[] getContenidoDocumento(String id) {

		byte[] contenido = null;

		Object result = getSqlMapClientTemplate().queryForObject("DocumentoVO.getContenidoDocumentoVO", id);
		if (result != null) {
			if (result instanceof byte[]) {
				contenido = (byte[]) result;
			} else if (result instanceof Blob) {
				Blob blob = (Blob) result;
				if (blob != null) {
					try {
						contenido = FileUtils.retrieve(blob.getBinaryStream());
					} catch (Exception e) {
						logger.error("Error al obtener el contenido del documento [" + id + "]", e);
						throw new RuntimeException("Error al obtener el contenido del documento", e);
					}
				}
			}
		}

		return contenido;
	}

	public DocumentoVO updateWithContent(DocumentoVO documento) {

		getSqlMapClientTemplate().update("DocumentoVO.updateDocumentoVOWithContent", documento);

		return documento;
	}

}
