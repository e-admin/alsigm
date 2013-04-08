package ieci.tecdoc.sgm.registro.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.rde.datatypes.ContenedorDocumentoImpl;

/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * documentos de registros.
 *
 */
public class RegistroDocumentoTabla extends ContenedorDocumentoImpl {
   private static final String TABLE_NAME = "SGMRTREGISTRO_DOCUMENTOS";
   private static final String CN_GUID = "GUID";
   private static final String CN_CODE = "CODIGO";
   private static final String CN_REGISTRY_NUMBER = "NUMERO_REGISTRO";
   private static final String ALL_COLUMN_NAMES = CN_REGISTRY_NUMBER + "," + CN_CODE + "," + CN_GUID;

   /**
    * Constructor de la clase RegistroDocumentoTabla
    */
   public RegistroDocumentoTabla() {
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

   /**
    * Devuelve el nombre de la columna guid
    * @return String Nombre de la columna guid
    */
   public String getGuidColumnName(){
     return CN_GUID;
   }


   /**
    * Devuelve el nombre de la columna code
    * @return String Nombre de la columna code
    */
   public String getCodeColumnName(){
     return CN_CODE;
   }


   /**
    * Devuelve el nombre de la columna registry_number
    * @return String Nombre de la columna registry_number
    */
   public String getRegistryNumberColumnName(){
     return CN_REGISTRY_NUMBER;
   }


   /**
    * Devuelve la clausula de consulta por guid
    * @param guid Valor del campo guid
    * @return String Clausula de consulta por guid
    */
   public String getByGuidQual(String guid) {
      String qual;

      qual = "WHERE " + CN_GUID + " = '" + DbUtil.replaceQuotes(guid) + "'";

      return qual;
   }

   /**
    * Devuelve la clausula de consulta por registry_number
    * @param registryNumber Valor del número de registro
    * @return String Clausula de consulta por número de registro
    */
   public String getByRegistryNumberQual(String registryNumber) {
      String qual;

      qual = "WHERE " + CN_REGISTRY_NUMBER + " = '" + DbUtil.replaceQuotes(registryNumber) + "'";

      return qual;
   }


   /**
    * Devuelve la clausula de consulta por registry_number y code
    * @param registryNumber Valor del número de registro
    * @param code Valor del código de documento
    * @return String Clausula de consulta por registry_number
    */
   public String getByRegistryNumberAndCodeQual(String registryNumber, String code) {
      String qual;

      qual = "WHERE " + CN_REGISTRY_NUMBER + " = '" + DbUtil.replaceQuotes(registryNumber) + "' AND " + CN_CODE + " = '" + DbUtil.replaceQuotes(code) + "'";

      return qual;
   }

   /**
    * Devuelve la cláusula de orden por código de documento
    * @return Cláusula de ordenación por código de documento
    */
   public String getOrderByCode(){
     return "ORDER BY " + CN_CODE;
   }

}