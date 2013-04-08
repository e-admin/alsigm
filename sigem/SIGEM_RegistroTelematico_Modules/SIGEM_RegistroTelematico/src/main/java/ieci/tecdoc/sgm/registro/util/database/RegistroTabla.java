/*
 * Created on 08-ago-2005
 * @author IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package ieci.tecdoc.sgm.registro.util.database;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.registro.util.RegistroConsulta;

import java.util.Date;
import java.util.List;


/**
 * Mapea la tabla SGMRTREGISTRO.
 *
 * @see RegistroDatos
 */
public class RegistroTabla {
   private static final String TN = "SGMRTREGISTRO";

   protected static final String CN_REGISTRY_NUMBER = "NUMERO_REGISTRO";
   protected static final String CN_REGISTRY_DATE = "FECHA_REGISTRO";
   protected static final String CN_EFFECTIVE_DATE = "FECHA_EFECTIVA";
   protected static final String CN_SENDER_ID = "EMISOR_ID";
   protected static final String CN_NAME = "NOMBRE";
   //protected static final String CN_SURNAME = "APELLIDOS";
   protected static final String CN_EMAIL = "CORREO_ELECTRONICO";
   protected static final String CN_TOPIC = "ASUNTO";
   protected static final String CN_ADDRESSEE = "ORGANO";
   protected static final String CN_STATUS = "ESTADO";
   protected static final String CN_ADDITIONAL_INFO = "INFO_ADICIONAL";
   protected static final String CN_SENDER_ID_TYPE = "TIPO_EMISOR_ID";
   protected static final String CN_OFICINA = "OFICINA";
   protected static final String CN_NUMERO_EXPEDIENTE = "NUMERO_EXPEDIENTE";

   public RegistroTabla() {
   }

   /**
    * Devuelve el nombre completo de la tabla mapeada sin cualificar.
    *
    * @return El nombre tabla mencionada.
    */
   public String getTableName() {
      return TN;
   }

   /**
    * Devuelve el nombre completo de todas las columnas mapeadas de
    * la tabla, separadas por comas.
    *
    * @return El nombre de las columnas.
    */
   public String getAllColumnsName() {
      String val = CN_REGISTRY_NUMBER + ","
              + CN_REGISTRY_DATE + ","
              + CN_SENDER_ID + "," + CN_NAME + ","
              /*+ CN_SURNAME + ","*/ + CN_EMAIL + ","
              + CN_TOPIC + "," + CN_ADDRESSEE + ","
              + CN_STATUS + ","
              + CN_ADDITIONAL_INFO + ","
      		  + CN_SENDER_ID_TYPE + ","
      		  + CN_OFICINA + ","
      		  + CN_NUMERO_EXPEDIENTE + ","
      		  + CN_EFFECTIVE_DATE;
      return val;
   }

   /**
    * Devuelve el nombre completo de todas las columnas mapeadas de
    * la tabla, separadas por comas.
    *
    * @return El nombre de las columnas.
    */
   public String getInfoColumnsName() {
      String val = CN_REGISTRY_NUMBER + ","
              + CN_REGISTRY_DATE + ","
              + CN_EFFECTIVE_DATE + ","
              + CN_STATUS + ","
              + CN_SENDER_ID + "," + "DESCRIPCION AS " + CN_NAME + ","
              + " ID,"
              + TN + "." + CN_TOPIC + " AS " + CN_TOPIC + ","
              + CN_ADDITIONAL_INFO;
      return val;
   }

   /**
    * Devuelve el nombre completo de las columnas mapeadas de la tabla
    * para la creación del registro.
    *
    * @return El nombre de las columnas.
    */
   public String getInsertColumnsName() {
      String val = CN_REGISTRY_NUMBER + ","
              + CN_REGISTRY_DATE + ","
              + CN_SENDER_ID + "," + CN_NAME + ","
              /*+ CN_SURNAME + ","*/ + CN_EMAIL + ","
              + CN_TOPIC + "," + CN_ADDRESSEE + ","
              + CN_STATUS + ","
              + CN_ADDITIONAL_INFO + ","
      		  + CN_SENDER_ID_TYPE + ","
      		  + CN_OFICINA + ","
      		  + CN_NUMERO_EXPEDIENTE /* + ","
      		  + CN_EFFECTIVE_DATE*/;
      return val;
   }

   /**
    * Devuelve el nombre completo de las columnas mapeadas de la tabla
    * para la fecha efectiva y el estado.
    *
    * @return Las columnas mencionadas.
    */
   public String getUpdateEffectiveDateAndStatus() {
      String val = CN_EFFECTIVE_DATE + ","
      			+ CN_STATUS;
      return val;
   }

   /**
    * Devuelve el nombre completo de las columnas mapeadas de la tabla
    * susceptibles de ser actualizadas.
    *
    * @return Las columnas mencionadas.
    */
   public String getUpdateColumnNames() {
      String val = CN_SENDER_ID + "," + CN_NAME + ","
              /*+ CN_SURNAME + ","*/ + CN_EMAIL + ","
              + CN_TOPIC + "," + CN_ADDRESSEE + ","
              + CN_STATUS  + ","
              + CN_ADDITIONAL_INFO  + ","
              + CN_SENDER_ID_TYPE + ","
              + CN_OFICINA + ","
              + CN_NUMERO_EXPEDIENTE;
      return val;
   }

   /**
    * Devuelve el nombre completo de la columna número de registro.
    *
    * @return El nombre de dicha columna.
    */
   public String getRegistryNumberColumnName() {
      return CN_REGISTRY_NUMBER;
   }

   /**
    * Devuelve el nombre completo de la columna fecha de realización
    * del asiento de registro.
    *
    * @return El nombre de dicha columna.
    */
   public String getRegistryDateColumnName() {
      return CN_REGISTRY_DATE;

   }

   /**
    * Devuelve el nombre completo de la columna fecha efectiva
    * del asiento de registro.
    *
    * @return El nombre de dicha columna.
    */
   public String getEffectiveDateColumnName() {
      return CN_EFFECTIVE_DATE;

   }

   /**
    * Devuelve el nombre completo de la columna identificador de
    * solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderIdColumnName() {
      return CN_SENDER_ID;
   }

   /**
    * Devuelve el nombre completo de la columna nombre del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getNameColumnName() {
      return CN_NAME;
   }

   /**
    * Devuelve el nombre completo de la columna apellidos del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   /*public String getSurnameColumnName() {
      return CN_SURNAME;
   }*/

   /**
    * Devuelve el nombre completo de la columna dirección de correo electrónico
    * del solicitante.
    *
    * @return El nombre de dicha columna.
    */
   public String getEmailColumnName() {
      return CN_EMAIL;
   }


   /**
    * Devuelve el nombre completo de la columna asunto.
    *
    * @return El nombre de dicha columna.
    */
   public String getTopicColumnName() {
      return CN_TOPIC;
   }

   /**
    * Devuelve el nombre completo de la columna organo destino.
    *
    * @return El nombre de dicha columna.
    */
   public String getAddresseeColumnName() {
      return CN_ADDRESSEE;
   }


   /**
    * Devuelve el nombre completo de la columna estado del registro.
    *
    * @return El nombre de dicha columna.
    */
   public String getStatusColumnName() {
      return CN_STATUS;
   }

   /**
    * Devuelve el nombre completo de la columna información adicional.
    *
    * @return El nombre de dicha columna.
    */
   public String getAdditionalInfoColumnName() {
      return CN_ADDITIONAL_INFO;
   }

   /**
    * Devuelve el nombre completo de la columna tipo de nif del registro.
    *
    * @return El nombre de dicha columna.
    */
   public String getSenderIdTypeColumnName() {
      return CN_SENDER_ID_TYPE;
   }

   /**
    * Devuelve el nombre completo de la columna oficina.
    *
    * @return El nombre de dicha columna.
    */
   public String getOficinaColumnName() {
      return CN_OFICINA;
   }

   /**
    * Devuelve el nombre completo de la columna número de expediente.
    *
    * @return El nombre de dicha columna.
    */
   public String getNumeroExpedienteColumnName() {
      return CN_NUMERO_EXPEDIENTE;
   }

   /**
    * Devuelve la cláusula de consulta por el valor de número de registro
    * que se pasa como parámetro.
    *
    * @param registryNumber Número de registro.
    * @return La cláusula.
    */
   public String getByRegistryNumberQual(String registryNumber) {
      String val = " WHERE " + CN_REGISTRY_NUMBER + " = '"
              + DbUtil.replaceQuotes(registryNumber) + "'";
      return  val;
   }

   /**
    * Devuelve la cláusula de consulta por el valor del estado
    * que se pasa como parámetro.
    *
    * @param status Identificador del estado del registro:
    * 	0 No consolidado
    * 	1 consolidado
    * 	3 registro error
    * @return La cláusula.
    */
   public String getByRegistryStatusQual(String status) {
      String val = " WHERE " + CN_STATUS + " = '" + DbUtil.replaceQuotes(status) + "'";
      return  val;
   }
   /**
    * Devuelve la cláusula de consulta aquellos valores que
    * se pasan como parámetros.
    * @param query Valores de los campos para la cláusula
    * @param operator Identificador de operación.
    * @return Cadena de caracteres con la cláusula.
    */
   public String getByQuery(DbConnection dbConn, List query, String operator) {
      String val = " WHERE ";
      boolean notempty = false;
      if (!query.get(0).equals("")) {
         notempty = true;
         if(operator.equals("")) {
            val = val + CN_REGISTRY_NUMBER  + " = '" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
         } else {
        	 if (operator.equals(new Integer(RegistroConsulta.COD_CONTIENE).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " like '%" + DbUtil.replaceQuotes((String) query.get(0)) + "%'";
        	 else if (operator.equals(new Integer(RegistroConsulta.COD_EMPIEZA).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " like '" + DbUtil.replaceQuotes((String) query.get(0)) + "%'";
        	 else if (operator.equals(new Integer(RegistroConsulta.COD_TERMINA).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " like '%" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
        	 else if (operator.equals(new Integer(RegistroConsulta.COD_MAYOR).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " > '" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
        	 else if (operator.equals(new Integer(RegistroConsulta.COD_MENOR).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " < '" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
        	 else if (operator.equals(new Integer(RegistroConsulta.COD_IGUAL).toString()))
        		 val = val + CN_REGISTRY_NUMBER + " = '" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
        	 else
        		 val = val + CN_REGISTRY_NUMBER + operator + "'" + DbUtil.replaceQuotes((String) query.get(0)) + "'";
         }
      }
      if (!query.get(1).equals("") | !query.get(2).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         if (!query.get(1).equals("") & !query.get(2).equals("")) {

        	 val = val + getClausulaPorFechas((String)query.get(1), (String)query.get(2),CN_REGISTRY_DATE, dbConn);
         }
         else if (!query.get(1).equals("") & query.get(2).equals("")) {

            val = val + getClausulaPorFechaMayorQue((String)query.get(1),CN_REGISTRY_DATE, dbConn);
         }
         else if (query.get(1).equals("") & !query.get(2).equals("")) {

        	 val = val + getClausulaPorFechaMenorQue((String)query.get(2), CN_REGISTRY_DATE, dbConn);
         }
      }
      if (!query.get(3).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         val = val + CN_SENDER_ID + " = '" + DbUtil.replaceQuotes((String) query.get(3)) + "'";
      }
      if (!query.get(4).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         val = val + TN + "." + CN_TOPIC + " = '" + DbUtil.replaceQuotes((String) query.get(4)) + "'";
      }
      if (!query.get(5).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         val = val + CN_ADDRESSEE + " = '" + DbUtil.replaceQuotes((String) query.get(5)) + "'";
      }
      if (!query.get(10).equals("")) {
         if (notempty) {
            val = val + " and ";
         }
         notempty = true;
         val = val + CN_STATUS + " = '" + DbUtil.replaceQuotes((String) query.get(10)) + "'";
      }
      if (!query.get(11).equals("") | !query.get(12).equals("")) {
          if (notempty) {
             val = val + " and ";
          }
          notempty = true;
          if (!query.get(11).equals("") & !query.get(12).equals("")) {

         	 val = val + getClausulaPorFechas((String)query.get(11), (String)query.get(12), CN_EFFECTIVE_DATE, dbConn);
          }
          else if (!query.get(11).equals("") & query.get(12).equals("")) {

             val = val + getClausulaPorFechaMayorQue((String)query.get(11),CN_EFFECTIVE_DATE,  dbConn);
          }
          else if (query.get(11).equals("") & !query.get(12).equals("")) {

         	 val = val + getClausulaPorFechaMenorQue((String)query.get(12),CN_EFFECTIVE_DATE,  dbConn);
          }
       }
      if (!notempty)
         val = "";
      else
      	val = val + " order by " + CN_REGISTRY_DATE + " desc";
      return val;
   }

   /**
    * Devuelve la cláusula de consulta por el valor del estado
    * que se pasa como parámetro.
    *
    * @param status Identificador del estado del registro:
    * 	0 No consolidado
    * 	1 consolidado
    * 	3 registro error
    * @return La cláusula.
    */
   public String getAuxInfo() {
      String val = "left join sgmrtcatalogo_tramites on " + TN + "." + CN_TOPIC + "= sgmrtcatalogo_tramites.asunto ";
      return  val;
   }

	/**
	 * Devuelve la clausula de consulta cuando la fecha de registro
	 * esta comprendida entre las dos fechas que entran como parametro
	 *
	 * @param fechaDesde Fecha desde la que se busca
	 * @param fechaHasta Fecha hasta que se busca
	 *
	 * @return String Clausula de consulta entre fechas
	 */

	public String getClausulaPorFechas(String fechaDesde, String fechaHasta, String column, DbConnection dbConn) {
		 Date dateDesde;
		 Date dateHasta;
		 String clausula;
	  try{
	     dateDesde = DateTimeUtil.getDate(fechaDesde + " 00:00:00", "dd-MM-yyyy HH:mm:ss");
	     dateHasta = DateTimeUtil.getDate(fechaHasta + " 23:59:59", "dd-MM-yyyy HH:mm:ss");

		 clausula = column + " >= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateDesde, false)+ " and " + column + " <= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateHasta, false) + "";
	  }
	  catch (Exception e){
		  clausula = column + " >= '" + DbUtil.replaceQuotes(fechaDesde) + "' and " + column + " <= '" + DbUtil.replaceQuotes(fechaHasta) + "'";
	  }
	  return clausula;
	}

	/**
	 * Devuelve la clausula de consulta cuando la fecha de registro
	 * es posterior que la fecha de busqueda
	 *
	 * @param fecha Fecha de Busqueda
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorFechaMayorQue(String fecha,String column, DbConnection dbConn) {

		 Date date;

			String clausula;
		  try{
		     date = DateTimeUtil.getDate(fecha + " 23:59:59", "dd-MM-yyyy HH:mm:ss");
		     clausula = column + " > " + DbUtil.getNativeDateTimeSyntax(dbConn, date, false) + "";

		  }
		  catch (Exception e){
			 clausula = column + " > '" + DbUtil.replaceQuotes(fecha) + "'";
		  }
		  return clausula;

	}

	/**
	 * Devuelve la clausula de consulta cuando la fecha de registro
	 * es anterior que la fecha de busqueda
	 *
	 * @param fecha Fecha de Busqueda
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorFechaMenorQue(String fecha,String column, DbConnection dbConn) {

		 Date date;
		 String clausula;
		  try{
		     date = DateTimeUtil.getDate(fecha + " 00:00:00", "dd-MM-yyyy HH:mm:ss");
		     clausula = column + " < " + DbUtil.getNativeDateTimeSyntax(dbConn, date, false) + "";

		  }
		  catch (Exception e){
			  clausula = column + " < '" + DbUtil.replaceQuotes(fecha) + "'";
		  }
		  return clausula;
	}

}