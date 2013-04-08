/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.usuario.database;

import java.io.Serializable;

import ieci.tecdoc.sgm.base.crypto.CryptoUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.usuario.exception.UsuarioCodigosError;
import ieci.tecdoc.sgm.usuario.exception.UsuarioExcepcion;
import ieci.tecdoc.sgm.usuario.datatypes.UsuarioImpl;

import org.apache.log4j.Logger;

/**
 * Modela una fila de la tabla de asociaciones extensión-tipo mime del RDE.
 *  
 */
public class UsuarioDatos extends UsuarioImpl implements Serializable {
   private static final Logger logger = Logger.getLogger(UsuarioDatos.class);
   protected boolean isDebugeable = true;
   
   /**
    * Recupera todos los valores de los parámetros de la sentencia
    * de consulta que se pasa como parámetro.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámetro recogido
    * @throws DbExcepcion Si se produce algún error.
    */
   public Integer loadAllValues(DbOutputStatement statement, Integer idx)
   throws Exception {

      int index = idx.intValue();

      id = statement.getShortText(index ++);
      usuario = statement.getShortText(index ++);
      password = statement.getShortText(index ++);
      nombre = statement.getShortText(index ++);
      apellidos = statement.getShortText(index ++);
      email = statement.getShortText(index ++);
      
      return new Integer(index);
   }

   
   
   /**
    * Genera la sentencia de inserción de un mime type.
    *
    * @param statement Sentencia sql precompilada.
    * @param idx Indice de posición del primer parámetro que se recoge
    * de la consulta.
    * @return Indice de posición del último parámtro recogido
    * @throws Exception Si se produce algún error.
    */
   public Integer insert(DbInputStatement statement, Integer idx)
   throws Exception {

     int index = idx.intValue();

     statement.setShortText(index++, id);
     statement.setShortText(index ++, usuario);
     String encryptedPass = CryptoUtil.encryptPass(password);
     statement.setShortText(index++, encryptedPass);
     statement.setShortText(index++, nombre);
     statement.setShortText(index++, apellidos);
     statement.setShortText(index++, email);

     return new Integer(index);
   }
   
   
	  public Integer update(DbInputStatement statement, Integer idx)
	  throws Exception {

	    int index = idx.intValue();
	    
	     String encryptedPass = CryptoUtil.encryptPass(password);
	     statement.setShortText(index++, encryptedPass);
	     statement.setShortText(index++, nombre);
	     statement.setShortText(index++, apellidos);
	     statement.setShortText(index++, email);
	     statement.setShortText(index++, id);

	     return new Integer(index);
	  }

	  public Integer updatePassword(DbInputStatement statement, Integer idx)
	  throws Exception {

	    int index = idx.intValue();
	    
	     String encryptedPass = CryptoUtil.encryptPass(password);
	     statement.setShortText(index++, encryptedPass);

	     return new Integer(index);
	  }

	  public Integer updateWithoutPassword(DbInputStatement statement, Integer idx)
	  throws Exception {

	    int index = idx.intValue();
	     statement.setShortText(index++, nombre);
	     statement.setShortText(index++, apellidos);
	     statement.setShortText(index++, email);
	     statement.setShortText(index++, id);

	     return new Integer(index);
	  }
   /**
    * Realiza la consulta por usuario / contraseña.
    *
    * @param extension Extension.
    * @throws DbExcepcion Si se produce algún error.
    */
   public void load(String usuario, String password, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsuarioTabla table = new UsuarioTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load User <-- Usuario: " + usuario);
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsuarioTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UsuarioDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
//         String encryptedPass = CryptoUtil.encryptPass(password);
//         if (!DynamicFns.select(dbConn, table.getByUserAndPass(usuario, encryptedPass), tableInfo, rowsInfo)) {
//            throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
//         }

         if (DynamicFns.select(dbConn, table.getByUser(usuario), tableInfo, rowsInfo)) {
        	 String encryptedPass = CryptoUtil.encryptPass(password);
        	 if (!encryptedPass.equals(getPassword())) {
        		 throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
        	 }
         } else {
            throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
         }

         logger.debug("Load Usuario --> Usuario: " + this.toString());
      } catch (Exception e) {
        throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS, e.getCause());
      } finally {
        try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){ }
      }
   }
   

   /**
    * Realiza la consulta por usuario / contraseña.
    *
    * @param extension Extension.
    * @throws DbExcepcion Si se produce algún error.
    */
   public void load(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsuarioTabla table = new UsuarioTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Load User <-- Usuario: " + usuario);
      
      try {
         dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsuarioTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UsuarioDatos.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(dbConn, table.getByUser(usuario), tableInfo, rowsInfo)) {
            throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS);
         }
         logger.debug("Load Usuario --> Usuario: " + this.toString());
      } catch (Exception e) {
        throw new UsuarioExcepcion(UsuarioCodigosError.EC_BAD_USER_OR_PASS, e.getCause());
      } finally {
        try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){ }
      }
   }

   /**
    * Añade un usuario.
    *
    * @throws Exception Si se produce algún error.
    */
   public void add(String entidad) throws UsuarioExcepcion {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsuarioTabla table = new UsuarioTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Add Usuario <-- Usuario: " + this.toString());
      
      try {
    	  dbConn.open(DBSessionManager.getSession(entidad));
         
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsuarioTabla.class.getName());
         tableInfo.setTablesMethod("getTableName");
         tableInfo.setColumnsMethod("getAllColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UsuarioDatos.class.getName());
         rowInfo.setValuesMethod("insert");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
      } catch (Exception e) {
         throw new UsuarioExcepcion(UsuarioCodigosError.EC_ADD_USER, e.getCause());
      } finally {
         try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){
        	throw new UsuarioExcepcion(UsuarioCodigosError.EC_ADD_USER, e.getCause());
        }
      }
   }
   

   /**
    * Borra todos las extensiones asociadas a un mime type.
    *
    * @param mimeType Mime type.
    * @throws Exception Si se produce algún error.
    */
   public void delete(String entidad) throws Exception {
	   UsuarioTabla table = new UsuarioTabla();
      DbConnection dbConn = new DbConnection();
      
      logger.debug("Delete Usuario <-- Usuario: " + usuario );
      
      try {
    	  dbConn.open(DBSessionManager.getSession(entidad));
         
         DbDeleteFns.delete(dbConn, table.getTableName(), table.getByUser(usuario));
         
      } catch (Exception e) {
         throw new UsuarioExcepcion(UsuarioCodigosError.EC_DELETE_USER);
      } finally {
         try{
          if (dbConn.existConnection())
             dbConn.close();
        }catch(Exception e){}
      }
   }
   
	  public void update(String entidad) throws UsuarioExcepcion {
		     DynamicTable tableInfo = new DynamicTable();
		     DynamicRows rowsInfo = new DynamicRows();
		     DynamicRow rowInfo = new DynamicRow();
		     UsuarioTabla table = new UsuarioTabla();
		     DbConnection dbConn = new DbConnection();
		     
		     try {
		    	 dbConn.open(DBSessionManager.getSession(entidad));
		        tableInfo.setTableObject(table);
		        tableInfo.setClassName(UsuarioTabla.class.getName());
		        tableInfo.setTablesMethod("getTableName");
		        tableInfo.setColumnsMethod(getColumnMethod());
		        
		        rowInfo.addRow(this);
		        rowInfo.setClassName(UsuarioDatos.class.getName());
		        rowInfo.setValuesMethod(getUpdateMethod());
		        rowsInfo.add(rowInfo);
		        
		        DynamicFns.update(dbConn, table.getByUser(usuario), tableInfo, rowsInfo);
		     } catch (Exception e) {
		        throw new UsuarioExcepcion(UsuarioCodigosError.EC_UPDATE_USER);
		     } finally {
		       try{
		         if (dbConn.existConnection())
		           dbConn.close();
		       }catch(Exception ee){
		    	  throw new UsuarioExcepcion(UsuarioCodigosError.EC_UPDATE_USER, ee);
		       }
		     }
		  }	  

	  private String getColumnMethod() {
		  if(getId()==null || "".equals(getId())) {
			  return "getPasswordColumnName";
		  } else if(getPassword()==null || "".equals(getPassword())) {
			  return "getWhitoutPassColumn";
		  } else {
			  return "getAllColumnNames";
		  }
	  }
	  
	  private String getUpdateMethod() {
		  if(getId()==null || "".equals(getId())) {
			  return "updatePassword";
		  } else if(getPassword()==null || "".equals(getPassword())) {
			  return "updateWithoutPassword";
		  } else {
			  return "update";
		  }
	  }
}
