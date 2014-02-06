package ieci.tecdoc.core.db;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

public class DbInputStatement
{
   private static final Logger logger = Logger.getLogger(DbInputStatement.class);
   
   private PreparedStatement m_jdbcStmt;

   public DbInputStatement()
   {
      m_jdbcStmt = null;
   }

   public void create(String stmtText) throws Exception
   {
	  if(logger.isDebugEnabled())
	     logger.debug("stmtText: "+stmtText);	   
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
      if(logger.isDebugEnabled())
         logger.debug("setShortText  index: "+index+", value:"+value);		
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
	      if(logger.isDebugEnabled())
	          logger.debug("setLongText  index: "+index+", value:"+value);		

      if (DbDataType.isNullLongText(value))
      {
         m_jdbcStmt.setNull(index,
                            DbDataType.getJdbcType(DbDataType.LONG_TEXT));
      }
      else
      {
         //ByteArrayInputStream in = new ByteArrayInputStream(value.getBytes());         
         //m_jdbcStmt.setAsciiStream(index, in ,value.length());
        
         m_jdbcStmt.setCharacterStream (index, new StringReader (value) ,value.length());
         
       //  m_jdbcStmt.setString(index, value);
      }
   }

   public void setShortInteger(int index, short value) throws Exception
   {
	      if(logger.isDebugEnabled())
	          logger.debug("setShortInteger  index: "+index+", value:"+value);		

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
	      if(logger.isDebugEnabled())
	          logger.debug("setLongInteger  index: "+index+", value:"+value);		

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
	      if(logger.isDebugEnabled())
	          logger.debug("setShortDecimal  index: "+index+", value:"+value);		

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
	      if(logger.isDebugEnabled())
	          logger.debug("setLongDecimal  index: "+index+", value:"+value);		

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
	      if(logger.isDebugEnabled())
	          logger.debug("setDateTime  index: "+index+", value:"+value);		

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
   
   public void setBlob(int index, byte[] value, int size) throws Exception
   {
	      if(logger.isDebugEnabled())
	          logger.debug("setBlob  index: "+index+", size:"+size);		

      
      ByteArrayInputStream in = new ByteArrayInputStream(value);
      
      m_jdbcStmt.setBinaryStream(index,in,size);
      
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

   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public void setInputStream(int index, InputStream inputStream, int size) throws Exception
   {
	      if(logger.isDebugEnabled())
	          logger.debug("setInputStream  index: "+index+", size: " + size);		

      m_jdbcStmt.setBinaryStream(index,inputStream,size);
   }
   
} // class
