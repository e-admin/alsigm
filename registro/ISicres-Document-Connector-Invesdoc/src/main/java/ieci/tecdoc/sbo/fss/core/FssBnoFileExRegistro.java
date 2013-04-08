package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbConnection;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.BookTypeConf;

public class FssBnoFileExRegistro {
	private static final Logger log = Logger
			.getLogger(FssBnoFileExRegistro.class);

	private FssBnoFileExRegistro() {
	}

	public static byte[] retrieveFileForSign(Connection jdbcCnt,
			Integer bookID, Integer fdrId, int listId, int docId, int fileID,
			BookTypeConf bookTypeConf) throws Exception {
		byte[] fileCont = null;

		try {

			DbConnection.open(jdbcCnt);

			fileCont = FssMdoFileRegistro.retrieveFileForSign(bookID, fdrId,
					listId, docId, fileID, bookTypeConf);

			DbConnection.close();

			return fileCont;

		} catch (Exception e) {

			DbConnection.ensureClose(e);
			log.error(e.getMessage(), e);
			throw e;

		}

	}

	public static byte[] updateFile(Connection jdbcCnt, FssFileInfo fileInfo,
			int listId, int fileID, byte[] fileCont) throws Exception {

		try {

			DbConnection.open(jdbcCnt);

			fileCont = FssMdoFileRegistro.updateFile(fileInfo, listId, fileID,
					fileCont);

			DbConnection.close();

			return fileCont;

		} catch (Exception e) {

			DbConnection.ensureClose(e);
			log.error(e.getMessage(), e);
			throw e;

		}

	}

}
