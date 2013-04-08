package ieci.tecdoc.sgm.base.db;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

//import oracle.sql.BLOB;

//import org.postgresql.largeobject.*;
import java.sql.Blob;

public class DbInputStatement
{
   ByteArrayInputStream mInput = null;

   private PreparedStatement m_jdbcStmt;

   public DbInputStatement()
   {
      m_jdbcStmt = null;
   }

   public void create(String stmtText) throws Exception
   {
      m_jdbcStmt = DbConnection.getJdbcConnection().prepareStatement(stmtText);
   }

   public void release() throws Exception
   {
      if (m_jdbcStmt != null)
      {
         m_jdbcStmt.close();
         m_jdbcStmt = null;
      }
   }

   public void execute() throws Exception
   {
      m_jdbcStmt.executeUpdate();     
   }

   public void setShortText(int index, String value) throws Exception
   {
      if (DbDataType.isNullShortText(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.SHORT_TEXT));
      }
      else
         m_jdbcStmt.setString(index, value);
   }

   public void setLongText(int index, String value) throws Exception
   {
      if (DbDataType.isNullLongText(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.LONG_TEXT));
      }
      else
         m_jdbcStmt.setString(index, value);
   }

   public void setShortInteger(int index, short value) throws Exception
   {
      if (DbDataType.isNullShortInteger(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.SHORT_INTEGER));
      }
      else
         m_jdbcStmt.setShort(index, value);
   }

   public void setLongInteger(int index, int value) throws Exception
   {
      if (DbDataType.isNullLongInteger(value))
      {
         m_jdbcStmt.setNull(index, 
                            DbDataType.getJdbcType(DbDataType.LONG_INTEGER));
      }
      else
         m_jdbcStmt.setInt(index, value);
   }

   public void setShortDecimal(int index, float value) throws Exception
   {
      if (DbDataType.isNullShortDecimal(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.SHORT_DECIMAL));
      }
      else
         m_jdbcStmt.setFloat(index, value);
   }

   public void setLongDecimal(int index, double value) throws Exception
   {
      if (DbDataType.isNullLongDecimal(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.LONG_DECIMAL));
      }
      else
         m_jdbcStmt.setDouble(index, value);
   }

   public void setDateTime(int index, Date value) throws Exception
   {
      if (DbDataType.isNullDateTime(value))
      {
         m_jdbcStmt.setNull(index, DbDataType.getJdbcType(DbDataType.DATE_TIME));
      }
      else
      {
         Timestamp ts = new Timestamp(value.getTime());
         m_jdbcStmt.setTimestamp(index, ts);
      }
   }

   public void setLongBinary(int index, InputStream value)
            throws Exception {
      Blob blob = null;
      //blob = Blob.createTemporary( DbConnection.getJdbcConnection(), true, BLOB.DURATION_CALL); 
      
      if ((value == null) || (value.available() == 0)) {
         m_jdbcStmt.setBlob( index, blob);    
      }
      else {
        
         int length = -1;
         OutputStream os = null;
         byte[] imageBuffer = null;
                
         os = blob.setBinaryStream(0);//getBinaryOutputStream();
         imageBuffer = new byte[100]; //blob.getChunkSize()
         
         while ((length = value.read( imageBuffer)) != -1) {
            os.write(imageBuffer, 0, length);
         }
         os.close(); 
         
         m_jdbcStmt.setBlob( index, blob);   
      }
      
      //blob.freeTemporary();  

   }

   public void setLongBinary(int index, byte[] value)
            throws Exception {
    
      Blob blob = null;    
      //blob = Blob.createTemporary( DbConnection.getJdbcConnection(), true, BLOB.DURATION_CALL);
      
      if (value == null) {
         m_jdbcStmt.setBlob( index, blob);    
      }
      else {
        
         int length = -1;
         OutputStream os = null;
         byte[] imageBuffer = null;
         
         mInput = new ByteArrayInputStream(value);
         
         os = blob.setBinaryStream(0);//getBinaryOutputStream();
         imageBuffer = new byte[100]; //blob.getChunkSize()
         
         while ((length = mInput.read( imageBuffer)) != -1) {
            os.write(imageBuffer, 0, length);
         }
         os.close(); 
         
         m_jdbcStmt.setBlob( index, blob);         
      }
      
     // blob.freeTemporary();  

   }


   public static void ensureRelease(DbInputStatement stmt, Exception exc)
         throws Exception
   {

      try
      {
         if (stmt != null) stmt.release();
         throw exc;
      }
      catch (Exception e)
      {
         throw exc;
      }

   }

} // class
