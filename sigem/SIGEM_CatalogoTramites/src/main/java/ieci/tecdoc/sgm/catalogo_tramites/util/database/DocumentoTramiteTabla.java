package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Mapea la tabla SGMRTCATALOGO_DOCUMENTOSTRAMITE.
 *
 * @see DocumentoTramiteDatos
 */
public class DocumentoTramiteTabla {
   protected static final String TN = "SGMRTCATALOGO_DOCSTRAMITE";
   protected static final String CN_PROCEDURE_ID = "TRAMITE_ID";
   protected static final String CN_DOCUMENT_ID = "DOCUMENTO_ID";
   protected static final String CN_DOCUMENT_CODE = "CODIGO_DOCUMENTO";
   protected static final String CN_MANDATORY_DOCUMENT = "DOCUMENTO_OBLIGATORIO";

   public DocumentoTramiteTabla() {
   }

   /**
    * Devuelve el nombre completo de la tabla mapeada sin cualificar.
    *
    * @return Cadena de caracteres con el nombre completo de la tabla.
    */
   public String getTableName() {
      return TN;
   }

   /**
    * Devuelve el nombre completo de la columna idntificador de trámite.
    *
    * @return El nombre de dicha columna.
    */
   public String getProcedureIdColumnName() {
      return CN_PROCEDURE_ID;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getDocumentIdColumnName() {
      return CN_DOCUMENT_ID;
   }

   /**
    * Devuelve el nombre completo de la columna código de documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getCodeColumnName() {
      return CN_DOCUMENT_CODE;
   }


   /**
    * Devuelve el nombre completo de la columna documento obligatorio para
    * trámite.
    *
    * @return El nombre de dicha columna.
    */
   public String getMandatoryDocumentColumnName() {
      return CN_MANDATORY_DOCUMENT;
   }


   /**
    * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
    * separados por comas.
    *
    * @return Los nombres de las columnas.
    */
   public String getAllColumnNames() {
      String val = CN_PROCEDURE_ID + ", " + CN_DOCUMENT_ID + ", "
               + CN_DOCUMENT_CODE + ", " + CN_MANDATORY_DOCUMENT;
      return val;
   }

   /**
    * Devuelve los nombres completos de todas las columnas actualizables de la tabla,
    * separados por comas.
    *
    * @return Los nombres de las columnas.
    */
   public String getUpdateColumnNames() {
      String val = CN_DOCUMENT_CODE + ", " + CN_MANDATORY_DOCUMENT;
      return val;
   }

   /**
    * Devuelve la cláusula de consulta con el valor del identificador de trámite
    * que se pasa como parámetro.
    *
    * @param procedureId
    *           Identificador de trámite.
    * @return La cláusula.
    */
   public String getByProcedureIdQual(String procedureId) {
      String val = " WHERE " + CN_PROCEDURE_ID + " = '" + DbUtil.replaceQuotes(procedureId) + "'";
      return val;
   }

   /**
    * Devuelve la cláusula de consulta con los valores de clave primaria que se
    * pasan como parámetros.
    *
    * @param procedureId
    *           Identificador de trámite.
    * @param documentId
    *           Identificador de documento.
    * @return La cláusula.
    */
   public String getByPKQual(String procedureId, String documentId) {
      String val = " WHERE " + CN_PROCEDURE_ID + " = '" + DbUtil.replaceQuotes(procedureId)
               + "' AND " + CN_DOCUMENT_ID + " = '" + DbUtil.replaceQuotes(documentId) + "'";
      return val;
   }

   /**
    * Devuelve la cláusula de consulta con los valores de clave primaria que se
    * pasan como parámetros.
    *
    * @param procedureId
    *           Identificador de trámite.
    * @param documentId
    *           Identificador de documento.
    * @return La cláusula.
    */
   public String getByPKQual(String procedureId, String documentId, String code) {
      String val = " WHERE " + CN_PROCEDURE_ID + " = '" + DbUtil.replaceQuotes(procedureId)
               + "' AND " + CN_DOCUMENT_ID + " = '" + DbUtil.replaceQuotes(documentId)
               + "' AND " + CN_DOCUMENT_CODE + " = '" + DbUtil.replaceQuotes(code) + "'";
      return val;
   }

   /**
    * Devuelve la cláusula de consulta con los valores de clave primaria que se
    * pasan como parámetros.
    *
    * @param procedureId
    *           Identificador de trámite.
    * @return La cláusula.
    */
   public String getByPKQual(String procedureId) {
      String val = " WHERE " + CN_PROCEDURE_ID + " = '" + DbUtil.replaceQuotes(procedureId) + "'";
      return val;
   }

   /**
    * Construye una expresión de búsqueda para averiguar si un documento está
    * siendo referenciado por algún trámite dado de alta en el catálogo de
    * trámites.
    *
    * @param documentId Identificador del documento
    * @return La expresión de búsqueda mencionada.
    */
   public String getDocumentReferencedQual(String documentId)
   {
      String qual;

      qual = "WHERE " + CN_DOCUMENT_ID + " = '" + DbUtil.replaceQuotes(documentId) + "'";

      return qual;
   }

   /**
    * Construye la cláusula de búsqueda por el código de documento que
    * es único para la relación documento-trámite.
    *
    * @param code Código del documento
    * @return La expresión de búsqueda mencionada.
    */
   public String getByDocumentCodeQual(String code){
   	   return "WHERE " + CN_DOCUMENT_CODE + " = '" + DbUtil.replaceQuotes(code) + "'";
   }
}