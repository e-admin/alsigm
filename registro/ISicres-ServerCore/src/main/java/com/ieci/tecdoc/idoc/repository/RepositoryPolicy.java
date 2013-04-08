package com.ieci.tecdoc.idoc.repository;

import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;

/**
 * 
 * @author LMVICENTE
 * @creationDate 22-jul-2004 9:46:19
 * @version
 * @since
 * @deprecated
 * 
 * Esta interfaz ha sido deprecada.
 * La nueva interfaz se encuentra en la libreria ISicres-Common.
 * <code>com.ieci.tecdoc.common.adapter.RepositoryPolicy.java</code>
 * 
 * La signatura de los metodos ha cambiado
 * 
 */
public interface RepositoryPolicy {

	public byte[] loadFileFromRepository(Integer bookID, int fileID,
			String entidad) throws Exception;

	public byte[] loadFileFromRepository(Integer bookID, int fdrid, int pageId,
			int fileID, String entidad) throws Exception;

	public byte[] loadFileFromRepositoryForSign(Integer bookID, Integer fdrId,
			int listId, int docId, int pageId, int fileID,
			BookTypeConf bookTypeConf, String entidad) throws Exception;

	public int saveFileInRepository(Integer bookID, int listID, int fdrid,
			int docID, FlushFdrFile file, String entidad) throws Exception;

}
