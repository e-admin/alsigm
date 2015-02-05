package ieci.tecdoc.sgm.rde.database.util;

import ieci.tecdoc.sgm.rde.database.exception.DbExcepcion;

/**
 * Clase que define los tipos de operadores en las cadenas de consulta
 * @author x53492no
 *
 */
public class QueryOperators 
{
   public static final int OPR_NONE = 0;   			// Sin operador  
   public static final int OPR_GREATER = 1; 		// Mayor que
   public static final int OPR_GREATER_EQUAL = 2;	// Mayor o igual que
   public static final int OPR_LESS = 3;			// Menor que
   public static final int OPR_LESS_EQUAL = 4;		// Menor o igual que
   public static final int OPR_CONTAINS = 5;		// Que contenga
   public static final int OPR_EQUAL = 6;			// Igual a
   
   /**
    * Devuelve, dependiendo del tipo de operador, una cadena con su 
    * correspondiente carater de operador
    * @param opr Tipo de operador
    * @return Cadena que representa la operación del operador
    * @throws DbExcepcion Si se produce algún error
    */
   public static String getSqlOperator(int opr) throws DbExcepcion{
      String sqlOper = "";
      
      switch (opr){
         case QueryOperators.OPR_GREATER:
         {
            sqlOper = " > ";
            break;
         }
         case QueryOperators.OPR_GREATER_EQUAL:
         {
            sqlOper = " >= ";
            break;
         }
         case QueryOperators.OPR_LESS:
         {
            sqlOper = " < ";
            break;
         }
         case QueryOperators.OPR_LESS_EQUAL:
         {
            sqlOper = " <= ";
            break;
         }
         case QueryOperators.OPR_CONTAINS:
         {
            sqlOper = " LIKE ";
            break;
         }
         case QueryOperators.OPR_EQUAL:
         {
            sqlOper = " = ";
            break;
         }
      }   
      return sqlOper;
   }
}