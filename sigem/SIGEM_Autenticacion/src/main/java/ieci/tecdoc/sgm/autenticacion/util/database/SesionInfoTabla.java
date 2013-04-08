package ieci.tecdoc.sgm.autenticacion.util.database;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

import java.util.Date;

/**
 * Mapeo de la tabla SGMAFSESSION_INFO.
 * @see SesionInfoDatos
 */
public class SesionInfoTabla
{
   private static final String TN = "SGMAFSESION_INFO";

   protected static final String CN_HOOK_ID = "CONECTORID";
   protected static final String CN_SESSION_ID = "SESIONID";
   protected static final String CN_LOGIN_DATE = "FECHA_LOGIN";
   protected static final String CN_SENDER_NAME = "NOMBRE_SOLICITANTE";
   //protected static final String CN_SENDER_SURNAME = "APELLIDOS_SOLICITANTE";
   protected static final String CN_SENDER_ID = "SOLICITANTE_ID";
   protected static final String CN_SENDER_EMAIL = "CORREO_ELECTRONICO_SOLICITANTE";
   protected static final String CN_SENDER_CERTIFICATE_ISSUER = "EMISOR_CERTIFICADO_SOLICITANTE";
   protected static final String CN_SENDER_CERTIFICATE_SN = "SOLICITANTE_CERTIFICADO_SN";
   protected static final String CN_SENDER_INQUALITY = "SOLICITANTE_INQUALITY";
   protected static final String CN_SOCIAL_NAME = "RAZON_SOCIAL";
   protected static final String CN_CIF = "CIF";
   protected static final String CN_ENTIDAD_ID = "ID_ENTIDAD";
   protected static final String CN_SENDER_FIRSTNAME = "SOLICITANTE_FIRSTNAME";
   protected static final String CN_SENDER_SURNAME = "SOLICITANTE_SURNAME";
   protected static final String CN_SENDER_SURNAME2 = "SOLICITANTE_SURNAME2";
   //protected static final String CN_SENDER_ADDITIONALINFO = "SENDER_ADDITIONALINFO";

   public SesionInfoTabla()
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
   public String getHookIdColumnName()
   {
   	  return CN_HOOK_ID;
   }

   /**
    * Devuelve el nombre completo de la columna identificador de sesión.
    * @return El nombre de dicha columna.
    */
   public String getSessionIdColumnName()
   {
   	  return CN_SESSION_ID;
   }

   /**
    * Devuelve el nombre completo de la columna fecha de autenticación.
    * @return El nombre de dicha columna.
    */
   public String getLoginDateColumnName()
   {
   	  return CN_LOGIN_DATE;
   }

   /**
    * Devuelve el nombre completo de la columna nombre del solicitante.
    * @return El nombre de dicha columna.
    */
   public String getSenderNameColumnName()
   {
   	  return CN_SENDER_NAME;
   }

   /**
    * Devuelve el nombre completo de la columna inQuality.
    * @return El nombre de dicha columna.
    */
   public String getInQualityColumnName(){
       return CN_SENDER_INQUALITY;
   }

   /**
    * Devuelve el nombre completo de la columna razón social.
    * @return El nombre de dicha columna.
    */
   public String getSocialNameColumnName(){
   	  return CN_SOCIAL_NAME;
   }

   /**
    * Devuelve el nombre completo de la columna CIF.
    * @return El nombre de dicha columna.
    */
   public String getCIFColumnName(){
      return CN_CIF;
   }

   /**
    * Devuelve el nombre completo de la columna CIF.
    * @return El nombre de dicha columna.
    */
   public String getIdEntidadColumnName(){
      return CN_ENTIDAD_ID;
   }
   /*
   public String getAdditionalInfoColumnName()
   {
   	  return CN_SENDER_ADDITIONALINFO;
   }*/

   /**
    * Devuelve el nombre completo de la columna identificador de solicitante.
    * @return El nombre de dicha columna.
    */
   public String getSenderIdColumnName()
   {
   	  return CN_SENDER_ID;
   }

   /**
    * Devuelve el nombre completo de la columna dirección de correo electrónico
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderEmailColumnName()
   {
   	  return CN_SENDER_EMAIL;
   }

   /**
    * Devuelve el nombre de la columna que almacena el emisor del certificado
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderCertificateIssuerColumnName()
   {
   	  return CN_SENDER_CERTIFICATE_ISSUER;
   }

   /**
    * Devuelve el nombre de la columna que almacena el número de serie del
    * certificado del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderCertificateSNColumnName()
   {
   	  return CN_SENDER_CERTIFICATE_SN;
   }

   /**
    * Devuelve el nombre completo de la columna primer apellido
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderSurnameColumnName()
   {
   	  return CN_SENDER_SURNAME;
   }

   /**
    * Devuelve el nombre completo de la columna segundo apellido
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderSurname2ColumnName()
   {
   	  return CN_SENDER_SURNAME2;
   }

   /**
    * Devuelve el nombre completo de la columna nombre sin apellidos
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderFirstnameColumnName()
   {
   	  return CN_SENDER_FIRSTNAME;
   }

   /**
    * Devuelve los nombres completos de todas la columnas mapeadas de la tabla,
    * separados por comas.
    *
    * @return Los nombres de las columnas.
    */
   public String getAllColumnsName()
   {
      String val = CN_HOOK_ID + ", " + CN_SESSION_ID + ", "
	  			   + CN_LOGIN_DATE + ", " + CN_SENDER_NAME + ", " + /*CN_SENDER_SURNAME + ", " +*/ CN_SENDER_ID + ", " + CN_SENDER_EMAIL  + ", " + CN_SENDER_CERTIFICATE_ISSUER
               + ", " + CN_SENDER_CERTIFICATE_SN
               + ", " + CN_SENDER_INQUALITY
			   + ", " + CN_SOCIAL_NAME
			   + ", " + CN_CIF
			   + ", " + CN_ENTIDAD_ID
			   + ", " + CN_SENDER_FIRSTNAME
			   + ", " + CN_SENDER_SURNAME
			   + ", " + CN_SENDER_SURNAME2;

				   //+ CN_SENDER_ADDITIONALINFO;
   	  return val;
   }

   /**
    * Devuelve la cláusula de consulta por el valor del identificador de
    * sesión que se pasa como parámetro.
    *
    * @param sessionId Identificador de sesión.
    * @return La cláusula.
    */
   public String getBySessionIdQual(String sessionId)
   {
   	  return " WHERE " + CN_SESSION_ID + " = '" + DbUtil.replaceQuotes(sessionId) + "'";
   }

   /**
    * Construye una expresión de búsqueda para eliminar todas las sesiones
    * caducadas.
    *
    * @param timeout Tiempo (en minutos) en el que la sesión caduca.
    * @return La expresión mencionada.
    */
   public String getByTimeoutQual(int timeout, DbConnection conn) throws Exception
   {
      Date date;

      date = DateTimeUtil.addMinutes(DateTimeUtil.getCurrentDateTime(), -timeout);

  	   return "WHERE " + CN_LOGIN_DATE + " < " + DbUtil.getNativeDateTimeSyntax(conn, date, false);
//	   DbUtil.getNativeDateTimeSyntax(date, false, conn);
   }

   public String getByTimeoutQualDBConn(int timeout, DbConnection conn) throws Exception
   {
      Date date;

      date = DateTimeUtil.addMinutes(DateTimeUtil.getCurrentDateTime(), -timeout);

  	   return "WHERE " + CN_LOGIN_DATE + " < " + DbUtil.getNativeDateTimeSyntax(conn, date, false);
//	   DbUtil.getNativeDateTimeSyntax(date, false, conn);
   }

}
