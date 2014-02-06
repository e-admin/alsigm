
package ieci.tecdoc.core.search;

public class SearchOpr
{   
   public static final String EQUAL         =  "=";  
   public static final String DISTINCT      =  "!=";    
   public static final String GREATER       =  ">";   
   public static final String GREATER_EQUAL =  ">=";   
   public static final String LOWER         =  "<";   
   public static final String LOWER_EQUAL   =  "<=";  
   public static final String BETWEEN       =  "..";  
   public static final String LIKE          =  "%";  
   public static final String OR            =  "|";
   public static final String FULL_TEXT     =  "Abc";
   public static final String FULL_TEXT_NOT =  "~Abc";
   public static final String IS_NULL		=  "=null";
   public static final String IS_NOT_NULL   =  "!=null";
   
   protected SearchOpr()
   {
   }
   
} // class
