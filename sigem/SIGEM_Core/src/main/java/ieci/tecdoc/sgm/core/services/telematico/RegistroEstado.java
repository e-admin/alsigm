package ieci.tecdoc.sgm.core.services.telematico;

/**
 * Clase que define los diferentes estados para un registro 
 *
 */
public class RegistroEstado 
{
   protected RegistroEstado()
   {
   }
   
   public static final int STATUS_NOT_CONSOLIDATED = 0;
   public static final int STATUS_CONSOLIDATED = 1;
   public static final int STATUS_ERROR = 3;
}