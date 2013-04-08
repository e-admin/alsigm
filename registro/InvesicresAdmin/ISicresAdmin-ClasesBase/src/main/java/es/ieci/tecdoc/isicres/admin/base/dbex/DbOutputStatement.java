package es.ieci.tecdoc.isicres.admin.base.dbex;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

//import oracle.jdbc.OracleResultSet;
//import oracle.sql.BLOB;

import org.apache.log4j.Logger;


public class DbOutputStatement
{
   /**
    * Log4J Logger for this class
    */
   private static final Logger logger = Logger.getLogger(DbOutputStatement.class);

   private Statement    m_jdbcStmt;
   private PreparedStatement m_jdbcBlobStmt;
   private ResultSet    m_jdbcRS;
   private String       m_stmtText;
   private DbConnection m_connection;

   public DbOutputStatement(DbConnection connection)
   {
      m_jdbcStmt = null;
      m_jdbcBlobStmt = null;
      m_jdbcRS   = null;
      m_stmtText = null;
      m_connection = connection;
   }

   public void create(String stmtText) throws Exception
   {
      m_jdbcStmt = m_connection.getJdbcConnection().createStatement();
      if(logger.isDebugEnabled())
         logger.debug("stmtText: "+stmtText);
      m_stmtText = stmtText;
   }

   public void createBlob(String stmtText) throws Exception
   {
      m_jdbcBlobStmt = m_connection.getJdbcConnection().prepareStatement(stmtText);
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

      if (m_jdbcBlobStmt != null)
      {
         m_jdbcBlobStmt.close();
         m_jdbcBlobStmt = null;
      }

   }

   public void execute() throws Exception
   {
      m_jdbcRS = m_jdbcStmt.executeQuery(m_stmtText);
   }

   public void executeBlob() throws Exception
   {
      m_jdbcRS = m_jdbcBlobStmt.executeQuery();
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

   public InputStream getBinaryStream(int index) throws Exception
   {

      InputStream val;

      val = m_jdbcRS.getBinaryStream(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_BINARY_STREAM;

      return val;

   }

   public byte[] getBytes(int index) throws Exception
   {

      byte[] val;

      val = m_jdbcRS.getBytes(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_BYTES;

      return val;

   }

   public String getLongText(int index) throws Exception
   {

      String            val= null;
      String            version;
      boolean           isClob = false;
      ResultSetMetaData metadata;
      int               columnType = 0;

      if (m_connection.getEngine() == DbEngine.ORACLE)
      {
         version = m_connection.getEngineVersion();
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


   public Timestamp getTimestamp(int index) throws Exception
   {

      Timestamp val;

      val = m_jdbcRS.getTimestamp(index);
      if (m_jdbcRS.wasNull())
         val = DbDataType.NULL_TIMESTAMP;

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

   /*public InputStream getLongBinary(int index) throws Exception {

      BLOB binaryObject = null;

      binaryObject = getBlob(index);

      return binaryObject.binaryStreamValue();

   }*/

   /*public InputStream getLongBinary(String fldName) throws Exception {

      BLOB binaryObject = null;

      binaryObject = getBlob(fldName);

      return binaryObject.binaryStreamValue();

   }*/

   /*public BLOB getBlob(int index) throws Exception {

      BLOB blob = null;

      blob = ((OracleResultSet)m_jdbcRS).getBLOB(index);

      return blob;
   }*/

   /*public BLOB getBlob(String fldName) throws Exception {

      BLOB blob = null;

      Object[] args = {fldName};
      Class[] argTypes = {Class.forName("java.lang.String")};
      Object result = null;//WSCallHelper.jdbcCall(null, m_jdbcRS, "getBLOB", args,argTypes);
      blob = (BLOB)result;

      return blob;
   }*/

   /*public void updateBlobValue(String fldName, InputStream value)
            throws Exception {

      byte[] data = new byte[10 * 1024];
      BLOB blob;
      OutputStream blobOutputStream = null;

      try {
         blob = getBlob(fldName);
         blobOutputStream = blob.getBinaryOutputStream();

         int chunk = blob.getChunkSize();

         byte[] imageBuffer = new byte[chunk];
         int length = -1;
         while ((length = value.read(imageBuffer)) != -1) {
             blobOutputStream.write(imageBuffer, 0, length);
         }

      }
      catch (Exception exc)
      {
         throw exc;
      }
      finally
      {
         if (blobOutputStream != null) blobOutputStream.close();
      }

   }*/

   /*public void updateBlobValue(int index, InputStream value)
            throws Exception {

      byte[] data = new byte[10 * 1024];
      BLOB blob;
      OutputStream blobOutputStream = null;

      try {
         blob = getBlob(index);
         blobOutputStream = blob.getBinaryOutputStream();


         int chunk = blob.getChunkSize();

         byte[] imageBuffer = new byte[chunk];
         int length = -1;
         while ((length = value.read(imageBuffer)) != -1) {
             blobOutputStream.write(imageBuffer, 0, length);
         }

      }
      catch (Exception exc)
      {
         throw exc;
      }
      finally
      {
         if (blobOutputStream != null) blobOutputStream.close();
      }

   }*/

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

} // class
