package es.ieci.tecdoc.isicres.document.connector.invesdoc;

import ieci.tecdoc.sbo.fss.core.FssBnoFileEx;
import ieci.tecdoc.sbo.fss.core.FssBnoFileExRegistro;
import ieci.tecdoc.sbo.fss.core.FssFileInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.utils.BBDDUtils;

import es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.exception.IsicresDocumentConnectorException;
import es.ieci.tecdoc.isicres.document.connector.invesdoc.vo.InvesDocDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

/**
 * @author 66575267
 * 
 * Implementación del Conector de Gestor Documental para Invesdoc
 */
public class InvesDocDocumentConnector implements ISicresDocumentConnector {

	private static final Logger log = Logger
			.getLogger(InvesDocDocumentConnector.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector#create(es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO)
	 */
	public ISicresAbstractDocumentVO create(ISicresAbstractDocumentVO document) {
		try {
			InvesDocDatosEspecificosVO datosEspecificosVO = (InvesDocDatosEspecificosVO) document
					.getDatosEspecificos();

			Connection connection = BBDDUtils.getConnection(datosEspecificosVO
					.getEntidad());
			boolean autocommit = connection.getAutoCommit();
			connection = prepareConnectionForInvesdoc(connection);

			FssFileInfo fileInfo = new FssFileInfo();
			fileInfo.m_ext = datosEspecificosVO.getExtension();
			fileInfo.m_extId1 = datosEspecificosVO.getBookId();
			fileInfo.m_extId2 = datosEspecificosVO.getFdrid();
			fileInfo.m_extId3 = datosEspecificosVO.getDocId();

			int fileID = FssBnoFileEx.storeFileInList(connection,
					datosEspecificosVO.getListId(), fileInfo, null, document
							.getContent());

			String strFileID = String.valueOf(fileID);
			document.setId(strFileID);

			// restauramos el valor del autocommit original
			connection = restaureConnectionAfterInvesdoc(connection, autocommit);

			return document;
		} catch (Exception e) {
			log.error("Impossible to save the file  [" + document.getName()
					+ "]", e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
	}

	public ISicresAbstractDocumentVO retrieve(ISicresAbstractDocumentVO document) {
		try {
			InvesDocDatosEspecificosVO datosEspecificosVO = (InvesDocDatosEspecificosVO) document
					.getDatosEspecificos();

			Connection connection = BBDDUtils.getConnection(datosEspecificosVO
					.getEntidad());

			byte[] content = FssBnoFileEx.retrieveFile(connection, new Integer(
					document.getId()).intValue());

			document.setContent(content);

			return document;
		} catch (Exception e) {
			log.error("Impossible to load the file [" + document.getId() + "]",
					e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
	}

	public ISicresAbstractDocumentVO update(ISicresAbstractDocumentVO document) {
		try {
			InvesDocDatosEspecificosVO datosEspecificosVO = (InvesDocDatosEspecificosVO) document
					.getDatosEspecificos();

			Connection connection = BBDDUtils.getConnection(datosEspecificosVO
					.getEntidad());
			boolean autocommit = connection.getAutoCommit();
			connection = prepareConnectionForInvesdoc(connection);

			FssFileInfo fileInfo = new FssFileInfo();
			fileInfo.m_ext = datosEspecificosVO.getExtension();
			fileInfo.m_extId1 = datosEspecificosVO.getBookId();
			fileInfo.m_extId2 = datosEspecificosVO.getFdrid();
			fileInfo.m_extId3 = datosEspecificosVO.getDocId();

			FssBnoFileExRegistro.updateFile(connection, fileInfo,
					datosEspecificosVO.getListId(), new Integer(document
							.getId()).intValue(), document.getContent());

			// restauramos el valor del autocommit original
			connection = restaureConnectionAfterInvesdoc(connection, autocommit);

			return document;
		} catch (Exception e) {
			log.error("Impossible to load the file [" + document.getId() + "]",
					e);
			throw new IsicresDocumentConnectorException(
					"Impossible to save the file  [" + document.getName() + "]",
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector#delete(es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO)
	 */
	public void delete(ISicresAbstractDocumentVO document) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector#find(es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractCriterioBusquedaVO)
	 */
	public List find(ISicresAbstractCriterioBusquedaVO criterioBusqueda) {
		// TODO Auto-generated method stub
		return null;
	}

	private Connection prepareConnectionForInvesdoc(Connection connection)
			throws NamingException, SQLException {

		boolean autocommit = connection.getAutoCommit();
		if (autocommit == false) {
			connection.setAutoCommit(true);
		}

		return connection;
	}

	private Connection restaureConnectionAfterInvesdoc(Connection connection,
			boolean autocommit) throws SQLException {

		connection.setAutoCommit(autocommit);

		return connection;
	}

}
