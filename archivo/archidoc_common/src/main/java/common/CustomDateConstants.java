package common;

import java.text.SimpleDateFormat;

public class CustomDateConstants {
	//=========================================================================
	// Formateadores de fechas
	//=========================================================================
	public static final String FORMATO_ANIO 	= "yyyy";
	public static final String FORMATO_MES		= "MM";
	public static final String FORMATO_DIA		= "dd";
	public static final String FORMATO_ANIO_MES	= "yyyy-MM";
	public static final String FORMATO_ANIO_MES_DIA = "yyyy-MM-dd";
	public static final String FORMATO_DIA_MES_ANIO	= "dd/MM/yyyy";
	public static final String FORMATO_ANIO_MES_DIA_HORAS_MINUTOS_SEGUNDOS = "yyyy-MM-dd HH:mm:ss";




	/** Formateador de fechas del tipo "yyyy". */
	public static final SimpleDateFormat SDF_YYYY 		= new SimpleDateFormat(FORMATO_ANIO);

	/** Formateador de fechas del tipo "MM". */
	public static final SimpleDateFormat SDF_MM 		= new SimpleDateFormat(FORMATO_MES);

	/** Formateador de fechas del tipo "MM". */
	public static final SimpleDateFormat SDF_DD 		= new SimpleDateFormat(FORMATO_DIA);

	/** Formateador de fechas del tipo "yyyy-MM". */
	public static final SimpleDateFormat SDF_YYYYMM 	= new SimpleDateFormat(FORMATO_ANIO_MES);

    /** Formateador de fechas del tipo "yyyy-MM-dd". */
    public static final SimpleDateFormat SDF_YYYYMMDD   = new SimpleDateFormat(FORMATO_ANIO_MES_DIA);

    public static final SimpleDateFormat SDF_DDMMYYYY   = new SimpleDateFormat(FORMATO_DIA_MES_ANIO);

    /** Formateador de fechas del tipo "yyyy-MM-dd". */
    public static final SimpleDateFormat SDF_YYYYMMDD_HHMMSS   = new SimpleDateFormat(FORMATO_ANIO_MES_DIA_HORAS_MINUTOS_SEGUNDOS);
    //=========================================================================


	//=========================================================================
	// Formatos de las fechas
	//=========================================================================
	/** Año. */
	public static final String DATE_FORMAT_AAAA			= "AAAA";

	/** Mes y año. */
	public static final String DATE_FORMAT_MMAAAA		= "MMAAAA";

	/** Año y  mes. */
	public static final String DATE_FORMAT_AAAAMM		= "AAAAMM";

	/** Día, mes y año. */
	public static final String DATE_FORMAT_DDMMAAAA		= "DDMMAAAA";

	/** Año, mes y día. */
	public static final String DATE_FORMAT_AAAAMMDD		= "AAAAMMDD";

	/** Siglo. */
	public static final String DATE_FORMAT_S			= "S";
	//=========================================================================


	//=========================================================================
	// Operadores de fechas
	//=========================================================================
	/** Rango de fechas. */
	public static final String DATE_OPERATOR_RANGE		= "[..]";

	/** Exacta. */
	public static final String DATE_OPERATOR_EXACT		= "EX";

	/** Que contengan. */
	public static final String DATE_OPERATOR_CONTAINS	= "QCN";

	/** Igual. */
	public static final String DATE_OPERATOR_EQ			= "=";

	/** Mayor que. */
	public static final String DATE_OPERATOR_GT			= ">";

	/** Mayor o igual. */
	public static final String DATE_OPERATOR_GT_OR_EQ	= ">=";

	/** Menor que. */
	public static final String DATE_OPERATOR_LT			= "<";

	/** Menor o igual. */
	public static final String DATE_OPERATOR_LT_OR_EQ	= "<=";
	//=========================================================================
}
