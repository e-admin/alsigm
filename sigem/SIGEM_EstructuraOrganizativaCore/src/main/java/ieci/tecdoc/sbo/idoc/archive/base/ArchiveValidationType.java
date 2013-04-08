
package ieci.tecdoc.sbo.idoc.archive.base;

/**
 * Tipos de validación
 * @author
 */
	
public final class ArchiveValidationType
{
   
   /**
    * Sin validación
    */      
   public static final int NONE            = 0;
   
   /**
    * Validación simple: el valor debe estar dentro de un conjunto de valores
    */
   public static final int TBL_SIMPLE      = 1;
   
   /**
    * Validación con identificador: el valor debe estar dentro de un conjunto de valores
    * cada uno de los cuales esta relacionado con un identificador que es que realmente
    * se guarda en base de datos
    */
   public static final int TBL_ID          = 2;
   
   /**
    * Validación con sustituto: el valor debe estar dentro de un conjunto de valores cada
    * uno de los cuales esta asociado con otro valor que es el que realmente se guarda
    * en base de datos
    */
   public static final int TBL_SUST        = 3;
   
   /**
    * Validación jerárquica: define una relación entre los valores de dos campos a través
    * de una tercera tabla que relaciona dichos valores. Un ejemplo sería por ejemplo
    * una tabla de paises que valida un campo, una tabla de cuidades que validad otro campo,
    * y otra tabla que relaciona los paises con las cuidades
    */
   public static final int TBL_HIERARCHIC  = 4;
   
   /**
    * Validación numérica
    */
   public static final int AUTONUM         = 7;
    
   private ArchiveValidationType()
   {
   }
   
} // class
