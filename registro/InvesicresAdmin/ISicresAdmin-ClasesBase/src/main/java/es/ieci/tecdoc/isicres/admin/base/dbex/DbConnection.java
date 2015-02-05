
package es.ieci.tecdoc.isicres.admin.base.dbex;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException;


public class DbConnection {
   
   private int     m_engine;
   private String  m_engineVersion;
   private Connection m_jdbcConn;
   private boolean    m_inTrans;
   
   public DbConnection()
   {
      m_jdbcConn = null;
      m_inTrans  = false;
      m_engine   = DbEngine.UNSPECIFIED;
   }
   
   public void open() throws Exception
   {
		if (DbConfig.esDataSource())
		{
			open(DbConfig.getDataSource());
		}
		else
		{
			open(DbConfig.getDriver(), DbConfig.getUrl(), DbConfig
					.getUsuario(), DbConfig.getClave());
		}
   }
   
   public void open(Connection jdbcConn) throws Exception {
      
      close();
      m_jdbcConn = jdbcConn;
      
      m_engine        = getConnectionEngine();
      m_engineVersion = getConnectionEngineVersion();
   }
   
   public void open(String ctxName) throws Exception {
         
      close();

      openConnection(ctxName);

      m_engine        = getConnectionEngine();
      m_engineVersion = getConnectionEngineVersion();
     
   }
   
   public void open(String drvClsName, String url, String user, String pwd)
           throws Exception {
      
      openConnection(drvClsName, url, user, pwd);

      m_engine        = getConnectionEngine();
      m_engineVersion = getConnectionEngineVersion();
   }
   
   public void open(String ctxName, String user,String pwd) throws Exception {
      
      openConnection(ctxName, user, pwd);

      m_engine        = getConnectionEngine();
      m_engineVersion = getConnectionEngineVersion();
         
   }
   
   public void open(DbConnectionConfig dbConConfig) throws Exception {
      
      String     cntParam;
      String     url;
      String     user;
      String     pwd;
      
      cntParam = dbConConfig.getCntParam();
      user     = dbConConfig.getUser();
      pwd      = dbConConfig.getPwd();
      
      if(dbConConfig.isCntByDriver()) {
         
         url      = dbConConfig.getUrl();
         open(cntParam, url, user, pwd);
         
      } else {
         
         if((user == null) && (pwd == null))
            open(cntParam);
         else
            open(cntParam, user, pwd);
         
      }
      
   }
   
   public void close() throws Exception
   {      
     if (m_jdbcConn != null)
      {
         if (m_inTrans)
            endTransaction(false);
         try{
        	 m_jdbcConn.close();        	 
         }
         catch(SQLException e){
        	 
         }
         m_engine   = DbEngine.UNSPECIFIED;
         m_jdbcConn = null;          
      }
 
   }
    
   public int getEngine() throws Exception {
      return m_engine;
   }
   
   public String getEngineVersion() throws Exception {
      return m_engineVersion;
   }
   
   public boolean beginTransaction() throws Exception 
   {
      if (m_inTrans)
         return true;
      else
      {
         m_jdbcConn.setAutoCommit(false);          
         m_inTrans = true;     
         return false;
      }
   }
   
   public void endTransaction(boolean commit) throws Exception {
      
      if (m_inTrans)
      {
         if (commit)
            m_jdbcConn.commit();  
         else
            m_jdbcConn.rollback();    
         m_jdbcConn.setAutoCommit(true);          
         m_inTrans = false;     
      }
      
   }

   public boolean inTransaction() 
   {
	   return m_inTrans; 
   }
   
   public Connection getJdbcConnection() throws Exception {
      
      if (m_jdbcConn == null) {
         
         throw new IeciTdException(DbError.EC_NO_EXIST_CONN,
                 DbError.EM_NO_EXIST_CONN);
      }
      
      return m_jdbcConn;
      
   }
   
   private int getConnectionEngine() throws Exception {
      
      String dbVendor = m_jdbcConn.getMetaData().getDatabaseProductName();
      
      return determineDBVendor(dbVendor);
      
   }
   
   private int determineDBVendor(String dbVendor) throws Exception {
      
      if(dbVendor.indexOf(DbEngine.SQLSERVER_STR) != -1)
         return DbEngine.SQLSERVER;
      else if(dbVendor.indexOf(DbEngine.ORACLE_STR) != -1)
         return DbEngine.ORACLE;
      else if(dbVendor.indexOf(DbEngine.MYSQL_STR) != -1)
         return DbEngine.MYSQL;
      else if(dbVendor.indexOf(DbEngine.POSTGRESQL_STR) != -1)
        return DbEngine.POSTGRESQL;
      
      /*
       * Modificaciones para preguntar por DB2
       * 05-feb-2009 / SLuna
       * Inicio modificaciones
       */
      else if(dbVendor.indexOf(DbEngine.DB2_STR) != -1)
    	  return DbEngine.DB2;
      /*
       * Fin modificaciones
       */
      
      throw new IeciTdException(DbError.EC_INVALID_ENGINE,
              DbError.EM_INVALID_ENGINE);
      
   }
   
   public String getConnectionEngineVersion() throws Exception {
      
      String     version;
      
      version = m_jdbcConn.getMetaData().getDatabaseProductVersion();
      
      return version;
      
   }
   
   public boolean existConnection() throws Exception {
      
      boolean    existConnection = false;
      
      if (m_jdbcConn != null)
         existConnection = true;
      
      return existConnection;
      
   }
   
   private void openConnection(String drvClsName, String url,
           String user, String pwd)
           throws Exception {
      
      Class.forName(drvClsName);
      
      drvMgrGetConnection(url, user, pwd);
      SQLWarning   warning  =  m_jdbcConn.getWarnings();
      
      DbUtil.manipulateWarning(warning);
      
   }
   
   private void openConnection(String ctxName) throws Exception {
      
      DataSource   ds       =  DbDataSourceMgr.getInstance().getDataSource(ctxName);
      m_jdbcConn =  ds.getConnection();
      
      SQLWarning   warning  =  m_jdbcConn.getWarnings();
      
      DbUtil.manipulateWarning(warning);
      
   }
   
   private void openConnection(String ctxName, String user, String pwd)
           throws Exception {
      
      Context      ctx;
      DataSource   ds;
      SQLWarning   warning  =  null;
      
      ctx       =  new InitialContext();
      ds        =  (DataSource)ctx.lookup(ctxName);
      m_jdbcConn  =  ds.getConnection(user, pwd);
      warning   =  m_jdbcConn.getWarnings();
      
      DbUtil.manipulateWarning(warning);
      
   }
   
   private void drvMgrGetConnection(String url, String user, String pwd)
           throws Exception {
      
      String       sqlState;
      SQLWarning   warning  =  null;
      
      try {
         
         m_jdbcConn =  DriverManager.getConnection(url, user, pwd);
         warning  =  m_jdbcConn.getWarnings();
         
         DbUtil.manipulateWarning(warning);
         
      } catch(SQLException e) {
         
         sqlState = e.getSQLState();
         
         if(sqlState.equals("28000")) {
            
            throw new IeciTdException(DbError.EC_INVALID_AUTH_SPEC,
                    DbError.EM_INVALID_AUTH_SPEC, e);
            
         } else
            throw e;
         
      }
      
   }
}


//package ieci.tecdoc.core.db;
//
//
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.DatabaseMetaData;
//import java.sql.ResultSet;
//import java.sql.Types;
//import ieci.tecdoc.core.exception.IeciTdException;
//
///** Representa una conexión a una base de datos. */
//
//public final class DbConnection
//{
//
//   private Connection m_jdbcConn;
//   private boolean    m_inTrans;
//   private int        m_engine;
//   private String[]   m_dtnn; // Data Type Native Names
//
//   /** Construye un objeto de la clase. */
//
//   public DbConnection()
//   {
//      m_jdbcConn = null;
//      m_inTrans  = false;
//      m_engine   = DbEngine.UNSPECIFIED;
//      m_dtnn     = null;
//   }
//
//   /** Abre una conexión a una base de datos.
//   * <br><br>La conexión debe ser cerrada explícitamente utilizando el método
//   * close.
//   * @param drvClsName Nombre de la clase de driver.
//   * @param url Url de la base de datos.
//   * @param user Usuario de la base de datos.
//   * @param pwd Contraseña del usuario.
//   * @throws Exception si se produce algún error.
//   * <br><br>IeciTdException - EC_INVALID_AUTH_SPEC - si el usuario o la
//   * contraseña son incorrectos. */
//
//   public void open(String drvClsName, String url, String user, String pwd)
//               throws Exception
//   {
//      Class.forName(drvClsName);
//      drvMgrGetConnection(url, user, pwd);
//      getEngineP();
//      fillDataTypeNativeNames();
//   }
//
//   /** Cierra la conexión a la base de datos.
//   * <br><br>Toda conexión abierta debe ser cerrada explícitamente utilizando
//   * este método.
//   * @throws Exception si se produce algún error. */
//
//   public void close() throws Exception
//   {
//
//      if (m_jdbcConn != null)
//      {
//         m_jdbcConn.close();
//         m_dtnn     = null;
//         m_engine   = DbEngine.UNSPECIFIED;
//         m_inTrans  = false;
//         m_jdbcConn = null;
//      }
//
//   }
//
//   /** Inicia una transacción en la base de datos.
//   * @throws Exception si se produce algún error.
//   * <br><br>IeciTdException - EC_ALREADY_IN_TRANS - si ya existe una
//   * transacción en curso. */
//
//   public void beginTransaction() throws Exception
//   {
//      if (!m_inTrans)
//      {
//         m_jdbcConn.setAutoCommit(false);
//         m_inTrans = true;
//      }
//   }
//
//   /** Finaliza la transacción en curso.
//   * @param commit Especifica si hacer el commit de la transacción.
//   * @throws Exception si se produce algún error.
//   * <br><br>IeciTdException - EC_NOT_IN_TRANS si no existe una transacción
//   * en curso. */
//
//   public void endTransaction(boolean commit) throws Exception
//   {
//      if (m_inTrans)
//      {
//         if (commit)
//            m_jdbcConn.commit();
//         else
//            m_jdbcConn.rollback();
//         m_jdbcConn.setAutoCommit(true);
//         m_inTrans = false;
//      }
//   }
//
//   /** Devuelve si la conexión ha sido abierta.
//   * @return true si la conexión ha sido abierta. */
//
//   public boolean opened()
//   {
//      return (m_jdbcConn != null);
//   }
//
//   /** Devuelve si existe una transacción en curso.
//   * @return true si existe una transacción en curso. */
//
//   public boolean inTransaction()
//   {
//      return m_inTrans;
//   }
//
//   /** Devuelve la conexión Jdbc subyacente.
//   * @return La conexión Jdbc subyacente. */
//
//   public Connection getJdbcConnection()
//   {
//      return m_jdbcConn;
//   }
//
//   /** Devuelve el motor de la base de datos.
//   * @return El motor de la base de datos. */
//
//   public int getEngine()
//   {
//      return m_engine;
//   }
//
//   /** Devuelve los nombres nativos de los tipos de dato básicos.
//   * @return Los nombres mencionados. */
//
//   public String[] getDataTypeNativeNames()
//   {
//      return m_dtnn;
//   }
//
//   public String getEngineVersion() throws Exception
//   {
//      return m_jdbcConn.getMetaData().getDatabaseProductVersion();
//   }
//
//   /** Devuelve un texto xml con la información de los tipos de dato básicos.
//   * @return El texto mencionado.
//   * @throws Exception si se produce algún error. */
//
//   public String getDataTypeInfoXml() throws Exception
//   {
//
//      String           info = "";
//      DatabaseMetaData md;
//      ResultSet        rs = null;
//      String           aux;
//      String           typeText;
//
//      try
//      {
//
//         md = m_jdbcConn.getMetaData();
//         rs = md.getTypeInfo();
//
//         aux  = "<Type_Info>\r\n";
//         info = info + aux;
//
//         while (rs.next())
//         {
//
//            typeText = getDbDataTypeText(rs.getInt(2));
//
//            if (typeText != null)
//            {
//               aux = "   <Type>\r\n";
//               aux = aux + "      <Name>" + rs.getString(1)
//                     + "</Name>\r\n";
//               aux = aux + "      <DbDataType>" + typeText
//                     + "</DbDataType>\r\n";
//               aux = aux + "      <Create_Params>" + rs.getString(6)
//                     + "</Create_Params>\r\n";
//               aux = aux + "      <Precision>" + rs.getInt(3)
//                     + "</Precision>\r\n";
//               aux = aux + "   </Type>\r\n";
//               info = info + aux;
//            }
//
//         }
//
//         aux  = "</Type_Info>";
//         info = info + aux;
//
//         rs.close();
//
//         return info;
//
//      }
//      catch (Exception e)
//      {
//         try
//         {
//            if (rs != null) rs.close();
//            throw e;
//         }
//         catch (Exception e1)
//         {
//            throw e;
//         }
//      }
//
//   }
//
//   // **************************************************************************
//
//   private void drvMgrGetConnection(String url, String user, String pwd)
//                throws Exception
//   {
//
//      String sqlState;
//
//      try
//      {
//         m_jdbcConn = DriverManager.getConnection(url, user, pwd);
//      }
//      catch (SQLException e)
//      {
//
//         sqlState = e.getSQLState();
//
//         if (sqlState.compareTo("28000") == 0)
//         {
//                throw new IeciTdException(DbError.EC_INVALID_AUTH_SPEC,
//                                          DbError.EM_INVALID_AUTH_SPEC, e);
//         }
//         else
//            throw e;
//
//      }
//
//   }
//
//   private void getEngineP() throws Exception
//   {
//
//      DatabaseMetaData md;
//      String           eName;
//
//      md    = m_jdbcConn.getMetaData();
//      eName = md.getDatabaseProductName();
//      eName = eName.toUpperCase();
//
//      if (eName.indexOf("MICROSOFT SQL SERVER") >= 0)
//         m_engine = DbEngine.SQLSERVER;
//      else if (eName.indexOf("ORACLE") >= 0)
//         m_engine = DbEngine.ORACLE;
//      else
//         m_engine = DbEngine.UNSPECIFIED;
//
//   }
//
//   private void fillDataTypeNativeNames() throws Exception
//   {
//
//      int i;
//
//      m_dtnn = new String[8];
//      for (i = 0; i < 8; i++) m_dtnn[i] = null;
//
//      if (m_engine == DbEngine.SQLSERVER)
//         fillDataTypeNativeNamesSqlServer();
//      else if (m_engine == DbEngine.ORACLE)
//         fillDataTypeNativeNamesOracle();
//      else
//         fillDataTypeNativeNamesOthers();
//
//   }
//
//   private void fillDataTypeNativeNamesSqlServer()
//   {
//      m_dtnn[DbDataType.SHORT_TEXT]    = "varchar";
//      m_dtnn[DbDataType.LONG_TEXT]     = "text";
//      m_dtnn[DbDataType.SHORT_INTEGER] = "smallint";
//      m_dtnn[DbDataType.LONG_INTEGER]  = "int";
//      m_dtnn[DbDataType.SHORT_DECIMAL] = "real";
//      m_dtnn[DbDataType.LONG_DECIMAL]  = "float";
//      m_dtnn[DbDataType.DATE_TIME]     = "datetime";
//   }
//
//   private void fillDataTypeNativeNamesOracle()
//   {
//      m_dtnn[DbDataType.SHORT_TEXT]    = "VARCHAR2";
//      m_dtnn[DbDataType.LONG_TEXT]     = "LONG";
//      m_dtnn[DbDataType.SHORT_INTEGER] = "NUMBER(5)";
//      m_dtnn[DbDataType.LONG_INTEGER]  = "NUMBER(10)";
//      m_dtnn[DbDataType.SHORT_DECIMAL] = "NUMBER";
//      m_dtnn[DbDataType.LONG_DECIMAL]  = "NUMBER";
//      m_dtnn[DbDataType.DATE_TIME]     = "DATE";
//   }
//
//   private void fillDataTypeNativeNamesOthers() throws Exception
//   {
//
//      DatabaseMetaData md;
//      ResultSet        rs = null;
//      int              jdbcType, type;
//
//      try
//      {
//
//         md = m_jdbcConn.getMetaData();
//         rs = md.getTypeInfo();
//
//         while (rs.next())
//         {
//
//            jdbcType = rs.getInt(2);
//            type     = getDbDataType(jdbcType);
//
//            if (type != DbDataType.UNSPECIFIED)
//            {
//               if (m_dtnn[type] == null)
//               {
//                  m_dtnn[type] = rs.getString(1);
//               }
//            }
//
//         }
//
//         rs.close();
//
//      }
//      catch (Exception e)
//      {
//         try
//         {
//            if (rs != null) rs.close();
//            throw e;
//         }
//         catch (Exception e1)
//         {
//            throw e;
//         }
//      }
//
//   }
//
//   private String getDbDataTypeText(int jdbcType)
//   {
//
//      String text;
//
//      if (jdbcType == Types.VARCHAR)
//         text = "SHORT_TEXT";
//      else if (jdbcType == Types.LONGVARCHAR)
//         text = "LONG_TEXT";
//      else if (jdbcType == Types.SMALLINT)
//         text = "SHORT_INTEGER";
//      else if (jdbcType == Types.INTEGER)
//         text = "LONG_INTEGER";
//      else if (jdbcType == Types.REAL)
//         text = "SHORT_DECIMAL";
//      else if (jdbcType == Types.FLOAT)
//         text = "LONG_DECIMAL";
//      else if (jdbcType == Types.TIMESTAMP)
//         text = "DATE_TIME";
//      else
//         text = null;
//
//      return text;
//
//   }
//
//   private int getDbDataType(int jdbcType)
//   {
//
//      int type;
//
//      if (jdbcType == Types.VARCHAR)
//         type = DbDataType.SHORT_TEXT;
//      else if (jdbcType == Types.LONGVARCHAR)
//         type = DbDataType.LONG_TEXT;
//      else if (jdbcType == Types.SMALLINT)
//         type = DbDataType.SHORT_INTEGER;
//      else if (jdbcType == Types.INTEGER)
//         type = DbDataType.LONG_INTEGER;
//      else if (jdbcType == Types.REAL)
//         type = DbDataType.SHORT_DECIMAL;
//      else if (jdbcType == Types.FLOAT)
//         type = DbDataType.LONG_DECIMAL;
//      else if (jdbcType == Types.TIMESTAMP)
//         type = DbDataType.DATE_TIME;
//      else
//         type = DbDataType.UNSPECIFIED;
//
//      return type;
//
//   }
//
//} // class DbConnection

