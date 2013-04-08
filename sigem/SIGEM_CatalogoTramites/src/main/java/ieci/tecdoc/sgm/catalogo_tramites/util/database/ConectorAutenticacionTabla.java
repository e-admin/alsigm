package ieci.tecdoc.sgm.catalogo_tramites.util.database;

import ieci.tecdoc.sgm.base.dbex.DbUtil;

/**
 * Mapeo de la tabla SGMAFSESSION_INFO.
 * @see SesionInfoDatos
 */
public class ConectorAutenticacionTabla
{
   private static final String TN = "SGMAFCONECTOR_AUTENTICACION";

   protected static final String CN_CONECTOR_ID = "CONECTORID";
   protected static final String CN_TRAMITE_ID = "TRAMITEID";

   public ConectorAutenticacionTabla()
   {
   }

   /**
    * Devuelve el nombre completo de la tabla mapeada sin cualificar.
    * @return El nombre de la tabla.
    */
   public String getTableName()
   {
   	  return TN;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de conector
    * utilizado para autenticación.
    * @return El nombre de dicha columna.
    */
   public String getConectorIdColumnName()
   {
   	  return CN_CONECTOR_ID;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de trámite.
    * @return El nombre de dicha columna.
    */
   public String getTramiteIdColumnName()
   {
   	  return CN_TRAMITE_ID;
   }


   /**
    * Devuelve los nombres completos de todas la columnas mapeadas de la tabla,
    * separados por comas.
    *
    * @return Los nombres de las columnas.
    */
   public String getAllColumnNames()
   {
      String val = CN_TRAMITE_ID + ", " + CN_CONECTOR_ID;
   	  return val;
   }

   /**
    * Devuelve los nombres completos de todas la columnas mapeadas de la tabla,
    * separados por comas, que pueden ser actualizadas.
    *
    * @return Los nombres de las columnas actualizables.
    */
   public String getAllUpdateColumnNames()
   {
      String val = CN_CONECTOR_ID;
   	  return val;
   }

   /**
    * Devuelve la cláusula de consulta por el valor del identificador de
    * trámite que se pasa como parámetro.
    *
    * @param tramiteId Identificador de trámite.
    * @return La cláusula.
    */
   public String getByTramiteIdQual(String tramiteId)
   {
   	  return " WHERE " + CN_TRAMITE_ID + " = '" + DbUtil.replaceQuotes(tramiteId) + "'";
   }

   /**
    * Devuelve la cláusula de consulta por el valor del identificador de
    * trámite y de coector que se pasan como parámetro.
    *
    * @param tramiteId Identificador de trámite.
    * @param conectorId Identificador del conector de autenticación.
    * @return La cláusula.
    */
   public String getByTramiteAndConectorIdQual(String tramiteId, String conectorId)
   {
   	  return " WHERE " + CN_TRAMITE_ID + " = '" + DbUtil.replaceQuotes(tramiteId) + "' AND " +
   	  		CN_CONECTOR_ID + " = '" + DbUtil.replaceQuotes(conectorId) + "' ";
   }


}
