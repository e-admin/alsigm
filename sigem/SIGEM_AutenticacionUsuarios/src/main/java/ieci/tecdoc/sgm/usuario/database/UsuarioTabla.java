/**
 * @author José Antonio Nogales Rincón
 *
 * Fecha de Creación: 14-mar-2007
 */
package ieci.tecdoc.sgm.usuario.database;

import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.usuario.CriterioBusquedaUsuario;
import ieci.tecdoc.sgm.usuario.datatypes.UsuarioImpl;
import ieci.tecdoc.sgm.usuario.exception.UsuarioCodigosError;
import ieci.tecdoc.sgm.usuario.exception.UsuarioExcepcion;


/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * asociaciones extensión-tipo mime.
 *
 */
public class UsuarioTabla extends UsuarioImpl {
	private static final Logger logger = Logger.getLogger(UsuarioTabla.class);

   private static final String TABLE_NAME = "sgm_au_usuarios";
   private static final String CN_USUARIO = "USUARIO";
   private static final String CN_PASSWORD = "PASSWORD";
   private static final String CN_EMAIL = "CORREO_ELECTRONICO";
   private static final String CN_ID = "NIF";
   private static final String CN_NOMBRE = "NOMBRE";
   private static final String CN_APELLIDOS = "APELLIDOS";
   private static final String ALL_COLUMN_NAMES = CN_ID + ", " + CN_USUARIO + "," + CN_PASSWORD + "," +
   												CN_NOMBRE + ", " + CN_APELLIDOS + ", " + CN_EMAIL;

   /**
    * Constructor de la clase MimeTypeTable
    */
   public UsuarioTabla() {
   }


   /**
    * Devuelve los nombres completos de todas las columnas mapeadas
    * actualizables, separados por comas.
    * @return El nombre de las columnas separados por comas.
    */
   public String getUpdateColumnNames() {
      String val = CN_PASSWORD + "," +  CN_NOMBRE + ", " +
      				CN_APELLIDOS + ", " + CN_EMAIL + ", " + CN_ID;
      return val;
   }

   /**
    * Devuelve el nombre de la tabla
    * @return String Nombre de la tabla
    */
   public String getTableName() {
      return TABLE_NAME;
   }

   /**
    * Devuelve los nombres de las columnas
    * @return String Nombres de las columnas
    */
   public String getAllColumnNames() {
      return ALL_COLUMN_NAMES;
   }

   public String getWhitoutPassColumn() {
	   return CN_NOMBRE + ", " +
			CN_APELLIDOS + ", " + CN_EMAIL + ", " + CN_ID;
   }

   /**
    * Devuelve el nombre de la columna id
    * @return String Nombre de la columna id
    */
   public String getIdColumnName(){
     return CN_ID;
   }

   /**
    * Devuelve el nombre de la columna usuario
    * @return String Nombre de la columna usuario
    */
   public String getUsuarioColumnName(){
     return CN_USUARIO;
   }

   /**
    * Devuelve el nombre de la columna contraseña
    * @return String Nombre de la columna contraseña
    */
   public String getPasswordColumnName(){
     return CN_PASSWORD;
   }

   /**
    * Devuelve el nombre de la columna correo electrónico
    * @return String Nombre de la columna correo electrónico
    */
   public String getEmailColumnName(){
     return CN_EMAIL;
   }

   /**
    * Devuelve el nombre de la columna nombre
    * @return String Nombre de la columna nombre
    */
   public String getNombreColumnName(){
     return CN_NOMBRE;
   }

   /**
    * Devuelve el nombre de la columna apellidos
    * @return String Nombre de la columna apellidos
    */
   public String getApellidosColumnName(){
     return CN_APELLIDOS;
   }

   /**
    * Devuelve la clausula de consulta por id
    * @param id Valor del campo id
    * @return String Clausula de consulta por id
    */
   public String getById(String id) {
      String qual;

      if (id != null) {
    	  id = id.replaceAll("'", "''");
      }

      qual = "WHERE " + CN_ID + " = '" + id + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por id
    * @param id Valor del campo id
    * @return String Clausula de consulta por id
    */
   public String getByUser(String id) {
      String qual;

      if (id != null) {
    	  id = id.replaceAll("'", "''");
      }

      qual = "WHERE " + CN_USUARIO + " = '" + id + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por usuario y contraseña
    * @param usuario Valor del campo usuario
    * @param password Valor del campo contraseña
    * @return String Clausula de consulta por usuario y contraseña
    */
   public String getByUserAndPass(String usuario, String password) {
     String qual;

     if (usuario != null) {
    	 usuario = usuario.replaceAll("'", "''");
     }

     if (password != null) {
    	 password = password.replaceAll("'", "''");
     }

     qual = "WHERE " + CN_USUARIO + " = '" + usuario + "' AND " + CN_PASSWORD + " = '" + password + "'";

     return qual;
  }

   public String getFindQual(CriterioBusquedaUsuario poCriterio){
	   StringBuffer sbQual = null;
	   if(poCriterio != null){
		   // referencia
		   if(	(poCriterio.getUser() != null)
					&&(!"".equals(poCriterio.getUser()))
			   ){
				   if(sbQual == null){
					   sbQual = new StringBuffer(" WHERE ");
				   }
				   sbQual.append("UPPER (" + getUsuarioColumnName() + ") ")
				   			.append(" LIKE '%")
				   			.append(DbUtil.replaceQuotes(poCriterio.getUser().toUpperCase()))
				   			.append("%' ");
			   }
		   if(sbQual == null){
			   return "";
		   }else{
			   return sbQual.toString();
		   }
	   }else{
		   return "";
	   }
   }

	  public ArrayList buscarUsuarios(CriterioBusquedaUsuario poCriterio, String entidad) throws UsuarioExcepcion{
		  DynamicTable tableInfo = new DynamicTable();
		  DynamicRows rowsInfo = new DynamicRows();
		  DynamicRow rowInfo = new DynamicRow();
		  UsuarioTabla table = new UsuarioTabla();
		  UsuarioDatos expediente;
		  ArrayList list = new ArrayList();
		  int size;
		  DbConnection dbConn =  new DbConnection();

		  try {
			  dbConn.open(DBSessionManager.getSession(entidad));
			  tableInfo.setTableObject(table);
			  tableInfo.setClassName(UsuarioTabla.class.getName());
			  tableInfo.setTablesMethod("getTableName");
			  tableInfo.setColumnsMethod("getAllColumnNames");
			  rowInfo.setClassName(UsuarioDatos.class.getName());
		      rowInfo.setValuesMethod("loadAllValues");
		      rowsInfo.add(rowInfo);
		      DynamicFns.selectMultiple(dbConn, getFindQual(poCriterio), false, tableInfo, rowsInfo);
		      size = rowInfo.getRowCount();
		      for (int i = 0; i < size; i++) {
		    	  expediente = (UsuarioDatos) rowInfo.getRow(i);
		    	  list.add(expediente);
		      }
		  }catch(Exception e){
			  logger.error(e);
			  throw new UsuarioExcepcion(UsuarioCodigosError.EC_FIND_USERS, e.getCause());
		  } finally {
		       try{
		    		   if (dbConn.existConnection())
		    			   dbConn.close();
			   }catch(Exception ee){
				   throw new UsuarioExcepcion(UsuarioCodigosError.EC_ADD_USER, ee.getCause());
			   }
		  }
		  return list;
	  }

}
