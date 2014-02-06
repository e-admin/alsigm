package ieci.tecdoc.core.db;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Clob;
import java.util.Date;
import java.sql.ResultSetMetaData;

public class DbOutputStatement
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(DbOutputStatement.class);
   
   private Statement    m_jdbcStmt;
   private ResultSet    m_jdbcRS; 
   private String       m_stmtText; 
    
   public DbOutputStatement()
   {
      m_jdbcStmt = null;
      m_jdbcRS   = null;
      m_stmtText = null;      
   }
   
   public void create(String stmtText) throws Exception
   {
      m_jdbcStmt = DbConnection.getJdbcConnection().createStatement();         
      if(logger.isDebugEnabled())
         logger.debug("stmtText: "+stmtText);
      m_stmtText = stmtText;
   }
   
   public void release() throws Exception
   {
      
      m_stmtText = null;
      
      if (m_jdbcRS != null)
      {
         m_jdbcRS.close();
         m_jdbcRS = null;
      }
      
      if (m_jdbcStmt != null)
      {
         m_jdbcStmt.close();
         m_jdbcStmt = null;
      }
      
   }     
     
   public void execute() throws Exception
   {      
      m_jdbcRS = m_jdbcStmt.executeQuery(m_stmtText);      
   }   
     
   public boolean next() throws Exception
   {      
      return m_jdbcRS.next();       
   }
   
   public String getShortText(int index) throws Exception
   {
      
      String val;
      
      val = m_jdbcRS.getString(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_SHORT_TEXT;
      
      return val;

   }
   
   public String getLongText(int index) throws Exception
   {
      
      String            val= null;
      String            version;
      boolean           isClob = false;
      ResultSetMetaData metadata;
      int               columnType = 0;
      
      if (DbConnection.getEngine() == DbEngine.ORACLE)
      {      
         version = DbConnection.getEngineVersion();
         if (version.indexOf(DbEngine.ORACLE_VERSION_8) == -1)
         {
            metadata   =  m_jdbcRS.getMetaData();
            columnType =  metadata.getColumnType(index);
            
            if (columnType == java.sql.Types.CLOB)
               isClob = true;
         }
           
      }
     
      val = getLongText(index, isClob);      
      
      return val;

   }
   
   public String getLongText(int index, boolean isClob) throws Exception
   {
      
      String val= null;
      int    length;
      Clob   clob = null;
      
      if ( isClob )
      {
         
         clob = m_jdbcRS.getClob(index);
         
         if (clob != null)
         {
            length = (int)clob.length();
            val = clob.getSubString(1,length);
            clob = null; 
         }     
         
      }
      else
         val = m_jdbcRS.getString(index);
      
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_LONG_TEXT;
      
      return val;

   }
   
   public short getShortInteger(int index) throws Exception
   {
      
      short val;
      
      val = m_jdbcRS.getShort(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_SHORT_INTEGER;
      
      return val;

   }
   
   public int getLongInteger(int index) throws Exception
   {
      
      int val;
      
      val = m_jdbcRS.getInt(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_LONG_INTEGER;
      
      return val;

   }
   
   public float getShortDecimal(int index) throws Exception
   {
      
      float val;      
      
      val = m_jdbcRS.getFloat(index);      
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_SHORT_DECIMAL;
      
      return val;

   }
   
   public double getLongDecimal(int index) throws Exception
   {
      
      double val;
      
      val = m_jdbcRS.getDouble(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_LONG_DECIMAL;
      
      return val;

   }
   
   public Date getDateTime(int index) throws Exception
   {
      
      Date      val;
      Timestamp ts;
      
      ts = m_jdbcRS.getTimestamp(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_DATE_TIME;
      else
         val = new Date(ts.getTime());
      
      return val;

   }
   
   public byte[] getBlob(int index) throws Exception
   {
      byte[] val = new byte[100];      
      byte[] tval;
      
      InputStream in;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      
      in = m_jdbcRS.getBinaryStream(index);      
      
      int nBytes = -1;
      while((nBytes = in.read(val)) != -1)
      {
        out.write(val, 0, nBytes);  
      }
     
      tval = out.toByteArray();
      in.close();
      
      return (tval);
   }
   
   public static void ensureRelease(DbOutputStatement stmt, Exception exc)
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
            
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public void getStreamContent(int index, OutputStream outputStream) throws Exception
   {
      byte[] val = new byte[1024*8];      
      byte[] tval;
      
      InputStream in;
      
      in = m_jdbcRS.getBinaryStream(index);      
      
      int nBytes = -1;
      while((nBytes = in.read(val)) != -1)
      {
          outputStream.write(val, 0, nBytes);  
      }
     
      outputStream.flush();
      outputStream.close();
      
      in.close();
   }
   
} // class
