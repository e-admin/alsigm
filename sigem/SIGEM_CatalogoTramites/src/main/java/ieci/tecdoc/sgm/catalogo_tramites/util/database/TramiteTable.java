/*
 * Created on 08-ago-2005
 * @author IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

import java.util.List;

/**
 * Mapea la tabla SGMRTCATALOGO_TRAMITES.
 *
 * @see TramiteDatos
 */
public class TramiteTable {
   private static final String TN = "SGMRTCATALOGO_TRAMITES";

   protected static final String CN_ID = "ID";
   protected static final String CN_TOPIC = "ASUNTO";
   protected static final String CN_DESCRIPTION = "DESCRIPCION";
   protected static final String CN_ADDRESSEE = "ORGANO";
   protected static final String CN_DOCUMENT_LIMIT = "LIMITE_DOCUMENTOS";
   protected static final String CN_FIRMA = "FIRMA";
   protected static final String CN_OFICINA = "OFICINA";
   protected static final String CN_ID_PROCEDIMIENTO = "ID_PROCEDIMIENTO";

   /**
    * Constructor de la clase ProcedureTable
    */
   public TramiteTable() {
   }


   /**
    * Devuelve el nombre completo de la tabla mapeada sin cualificar.
    * @return El nombre de la tabla.
    */
   public String getTableName() {
      return TN;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de procedimiento o
    * trámite.
    * @return El nombre de dicha columna.
    */
   public String getIdColumnName() {
      return CN_ID;
   }

   /**
    * Devuelve el nombre completo de la columna descripción del asunto.
    * @return El nombre de dicha columna.
    */
   public String getDescriptionColumnName() {
      return CN_DESCRIPTION;
   }

   /**
    * Devuelve el nombre completo de la columna mapeada. Existe una función por
    * cada columna mapeada de la tabla.
    * @return El nombre de dicha columna.
    */
   public String getTopicColumnName() {
      return CN_TOPIC;
   }

   /**
    * Devuelve el nombre completo de la columna organo destino.
    * @return El nombre de dicha columna.
    */
   public String getAddresseeColumnName() {
      return CN_ADDRESSEE;
   }

   /**
    * Devuelve el nombre completo de la columna oficina.
    * @return El nombre de dicha columna.
    */
   public String getOficinaColumnName() {
      return CN_OFICINA;
   }


   /**
    * Devuelve el nombre completo de la columna límite de documentos.
    * @return El nombre de dicha columna.
    */
   public String getDocumentLimit() {
      return CN_DOCUMENT_LIMIT;
   }

   /**
    * Devuelve el nombre completo de la columna de firma.
    * @return El nombre de dicha columna.
    */
   public String getFirmaColumnName() {
      return CN_FIRMA;
   }


   /**
    * Devuelve el nombre completo de la columna identificador de procedimiento o
    * trámite.
    * @return El nombre de dicha columna.
    */
   public String getIdProcedimientoColumnName() {
      return CN_ID_PROCEDIMIENTO;
   }

   /**
    * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
    * separados por comas.
    * @return El nombre de las columnas.
    */
   public String getAllColumnNames() {
      String val = CN_ID + ", "
               + CN_TOPIC + ", " + CN_DESCRIPTION + ", " + CN_ADDRESSEE
               + ", " + CN_DOCUMENT_LIMIT + ", " + CN_FIRMA
               + ", " + CN_OFICINA + ", " + CN_ID_PROCEDIMIENTO;
      return val;
   }


   /**
    * Devuelve la clausula de orden por la descripción.
    * @return Los nombres de las columnas.
    */
   public String getOrderByDesc() {
      return " ORDER BY "+ CN_DESCRIPTION;
   }


   /**
    * Devuelve los nombres completos de todas las columnas mapeadas
    * actualizables, separados por comas.
    * @return El nombre de las columnas separados por comas.
    */
   public String getUpdateColumnNames() {
      String val = CN_TOPIC + ", " + CN_DESCRIPTION + ", " + CN_ADDRESSEE
               + ", " + CN_DOCUMENT_LIMIT + ", " + CN_FIRMA
               + ", " + CN_OFICINA + ", " + CN_ID_PROCEDIMIENTO;
      return val;
   }


   /**
    * Devuelve la cláusula de consulta con el valor del identificador que se
    * pasa como parámetro.
    * @param id Identificador de trámite.
    * @return La cláusula.
    */
   public String getByIdQual(String id) {
      return " WHERE " + CN_ID + " = '" + DbUtil.replaceQuotes(id) + "'";
   }

   /**
    * Devuelve la cláusula de consulta aquellos valores que
    * se pasan como parámetros.
    * @param query Valores de los campos para la cláusula
    * @return Cadena de caracteres con la cláusula.
    */
   public String getByQuery(List query) {
      String val = " where ";
      boolean notempty = false;

      if (!query.get(0).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         val = val + CN_ID + " = '" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
      }
      if (!query.get(1).equals("")) {
          if (notempty) {
             val = val + " and ";
          }
          notempty = true;
          val = val + CN_TOPIC + " = '" + DbUtil.replaceQuotes((String) query.get(1)) + "'";
      }
      if (!query.get(2).equals("")) {
          if (notempty) {
             val = val + " and ";
          }
          notempty = true;
          val = val + CN_ADDRESSEE + " = '" + DbUtil.replaceQuotes((String) query.get(2)) + "'";
      }

      if (!notempty)
         val = "";
      else
      	val = val + getOrderByDesc();
      return val;
   }

}
