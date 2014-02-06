package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 23-nov-2004
 * Time: 10:26:48
 */
public class VldTblUtil
{
   public static final int VLD_TBL_SIMPLE   = 1;  // Tabla de validación simple
   public static final int VLD_TBL_ID       = 2;  // Tabla de validación con identificador
   public static final int VLD_TBL_SUST     = 3;  // Tabla de validación con sustituto
   public static final int VLD_TBL_JERAR    = 4;  // Tabla de validación jerarquica

	public static final int MAP_FROM_VALUE_TO_SUST    = 100;  // Mapea un valor a su sustituto
	public static final int MAP_FROM_SUST_TO_VALUE    = 101;  // Mapea un sustituto a su valor
	public static final int MAP_FROM_ID_TO_VALUE      = 102;  // Mapea un id a su valor
	public static final int MAP_FROM_VALUE_TO_ID      = 103;  // Mapea un valor a su id

}
