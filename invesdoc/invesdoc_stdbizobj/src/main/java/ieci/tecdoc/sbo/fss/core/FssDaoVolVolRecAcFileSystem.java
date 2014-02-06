package ieci.tecdoc.sbo.fss.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;

/**
 * PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
 * Se ha tomado como base la clase FssDaoVolVolRecAc.
 */
public final class FssDaoVolVolRecAcFileSystem implements DbInputRecord, DbOutputRecord
{
   public String m_locId;
   public int    m_extId1;
   public int    m_extId2;
   public int    m_extId3;
   public int    m_extId4;
   public String m_fileExt;
   //public byte[] m_fileVal;
   //public int m_fileSize;
   public File   m_fileVal;
   
   public FssDaoVolVolRecAcFileSystem(File destFile) {
       m_fileVal = destFile;
   }
   
// ~ Methods ----------------------------------------------------------------

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;

		stmt.setShortText(i++, m_locId);
		stmt.setLongInteger(i++, m_extId1);
		stmt.setLongInteger(i++, m_extId2);
		stmt.setLongInteger(i++, m_extId3);
		stmt.setLongInteger(i++, m_extId4);
		stmt.setShortText(i++,m_fileExt);
		//stmt.setBlob(i++, m_fileVal, m_fileSize);		
        stmt.setInputStream(i++, new FileInputStream (m_fileVal), (int) m_fileVal.length());
	}
	
	public void getStatementValues(DbOutputStatement stmt)
				throws Exception
	{

	   int i = 1;
	   
	   m_locId = stmt.getShortText(i++);
	   m_extId1 = stmt.getLongInteger(i++);
	   m_extId2 = stmt.getLongInteger(i++);
	   m_extId3 = stmt.getLongInteger(i++);
	   m_extId4 = stmt.getLongInteger(i++);
	   m_fileExt = stmt.getShortText(i++);
	   stmt.getStreamContent(i++, new FileOutputStream(m_fileVal));
	}
	
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoVolVolRecAc[");
		buffer.append("m_locId = ").append(m_locId);
		buffer.append("m_extId1 = ").append(m_extId1);
		buffer.append("m_extId2 = ").append(m_extId2);
		buffer.append("m_extId3 = ").append(m_extId3);
		buffer.append("m_extId4 = ").append(m_extId4);
		buffer.append("m_fileExt = ").append(m_fileExt);
		buffer.append(", m_fileVal = ").append(m_fileVal.getAbsolutePath());
		buffer.append("]");
		
		return buffer.toString();
		
	}
}
