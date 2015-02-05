/*
 * Created on 08-ago-2005
 * @author IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Mapea la tabla SGMRTCATALOGO_DOCUMENTOS.
 *
 * @see DocumentoDatos
 */
public class DocumentoTabla {

   private static final String TN = "SGMRTCATALOGO_DOCUMENTOS";

   private static final String CN_ID = "ID";
   private static final String CN_DESCRIPTION = "DESCRIPCION";
   private static final String CN_EXTENSION = "EXTENSION";
   private static final String CN_SIGNATURE_HOOK = "CONECTOR_FIRMA";
   private static final String CN_VALIDATION_HOOK = "CONECTOR_CONTENIDO";

   public DocumentoTabla() {
   }

   /**
    * Devuelve el nombre completo de la tabla mapeada sin cualificar.
    *
    * @return El nombre de la tabla.
    */
   public String getTableName() {
      return TN;
   }

   public String getTableNameExt() {
      return TN + ", " + DocumentoTramiteTabla.TN;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getIdColumnName() {
      return CN_ID;
   }

   /**
    * Devuelve el nombre completo de la columna descripción.
    *
    * @return El nombre de dicha columna.
    */
   public String getDescriptionColumnName() {
      return CN_DESCRIPTION;
   }

   /**
    * Devuelve el nombre completo de la columna extensión de archivo de
    * documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getExtensionColumnName() {
      return CN_EXTENSION;
   }

   /**
    * Devuelve el nombre completo de la columna objeto de verificación de firma
    * para documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getSignatureHookColumnName() {
      return CN_SIGNATURE_HOOK;
   }

   /**
    * Devuelve el nombre completo de la columna objeto de verificación de
    * formato de documento.
    *
    * @return El nombre de dicha columna.
    */
   public String getDocumentHookColumnName() {
      return CN_SIGNATURE_HOOK;
   }

   /**
    * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
    * separados por comas. Se incluye el campo MANDATORY_DOCUMENT de la tabla
    * EREG_PROCAT_DOCUMENTS.
    *
    * @return Los nombres de las columnas.
    */
   public String getProcedureDocumentsColumnNames() {
      String val = CN_ID + ", " + CN_DESCRIPTION + ", "
               + CN_EXTENSION + ", " + CN_SIGNATURE_HOOK
               + ", " + CN_VALIDATION_HOOK + ", " + DocumentoTramiteTabla.CN_DOCUMENT_CODE
               + ", " + DocumentoTramiteTabla.CN_MANDATORY_DOCUMENT;
      return val;
   }

   /**
    * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
    * separados por comas.
    *
    * @return Los nombres de las columnas.
    */
   public String getAllColumnNames() {
      String val = CN_ID + ", " + CN_DESCRIPTION + ", "
               + CN_EXTENSION + ", " + CN_SIGNATURE_HOOK
               + ", " + CN_VALIDATION_HOOK;
      return val;
   }

   /**
    * Devuelve los nombres completos de las columnas mapeadas supceptibles de
    * actualización, separados por comas.
    *
    * @return Los nombres completos de las columnas.
    */
   public String getUpdateColumnNames() {
      String val = CN_DESCRIPTION + ", " + CN_EXTENSION + ", " +
                   CN_SIGNATURE_HOOK + ", " + CN_VALIDATION_HOOK;
      return val;
   }

   /**
    * Devuelve la cláusula de consulta con el valor del identificador que se
    * pasa como parámetro.
    *
    * @param id
    *           Identificador de tipo de documento.
    * @return La cláusula.
    */
   public String getByIdQual(String id) {
      return " WHERE " + CN_ID + " = '" + DbUtil.replaceQuotes(id) + "'";
   }

   /**
    * Devuelve la clausula de orden por la descrición.
    *
    * @return Los nombres de las columnas.
    */
   public String getOrderByDesc() {
      return " ORDER BY "+ CN_DESCRIPTION;
   }

   /**
    * Construye una expresión de búsqueda de todos los documentos pertenecientes
    * a un trámite.
    *
    * @param procedureId Identificador del procedimiento.
    * @return La expresión de búsqueda.
    */
   public String getDocumentsByProcedureIdQual(String procedureId)
   {
      String qual;

      qual = "WHERE " + getTableName() + "." + getIdColumnName() + "=" +
            DocumentoTramiteTabla.TN + "." + DocumentoTramiteTabla.CN_DOCUMENT_ID +
            " AND " + DocumentoTramiteTabla.TN + "." +
             DocumentoTramiteTabla.CN_PROCEDURE_ID + " = '" + DbUtil.replaceQuotes(procedureId) + "'";

      return qual;
   }
}
