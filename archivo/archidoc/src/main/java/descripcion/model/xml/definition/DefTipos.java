package descripcion.model.xml.definition;

/**
 * Clase que define los tipos de elementos.
 */
public class DefTipos {

	// ========================================================================
	// TIPOS DE FORMATOS DE FECHAS
	// ========================================================================
	/** Fecha con formato: AAAA. */
	public static final String TIPO_FORMATO_FECHA_AAAA = "AAAA";

	/** Fecha con formato: MMAAAA. */
	public static final String TIPO_FORMATO_FECHA_MMAAAA = "MMAAAA";

	/** Fecha con formato: DDMMAAAA. */
	public static final String TIPO_FORMATO_FECHA_DDMMAAAA = "DDMMAAAA";

	/** Fecha con formato: S. */
	public static final String TIPO_FORMATO_FECHA_S = "S";
	// ========================================================================

	// ========================================================================
	// TIPOS DE REFERENCIAS
	// ========================================================================
	/** Referencia de tipo desconocido. */
	public static final short TIPO_REFERENCIA_DESCONOCIDO = (short) 0;

	/** Referencia de tipo descriptor. */
	public static final short TIPO_REFERENCIA_DESCRIPTOR = (short) 1;

	/** Referencia de tipo elemento de cuadro de clasificación. */
	public static final short TIPO_REFERENCIA_ELEMENTO_CUADRO_CLASIFICACION = (short) 2;

	/** Referencia de tipo entidad productora. */
	public static final short TIPO_REFERENCIA_ENTIDAD_PRODUCTORA = (short) 3;
	// ========================================================================

	// ========================================================================
	// TIPOS DE NUMEROS
	// ========================================================================
	/** Número de tipo desconocido. */
	public static final short TIPO_NUMERICO_DESCONOCIDO = (short) 0;

	/** Número de tipo entero. */
	public static final short TIPO_NUMERICO_ENTERO = (short) 1;

	/** Número de tipo decimal. */
	public static final short TIPO_NUMERICO_DECIMAL = (short) 2;
	// ========================================================================

	// ========================================================================
	// TIPOS DE MEDIDAS
	// ========================================================================
	/** Medida de tipo desconocido. */
	public static final short TIPO_MEDIDA_DESCONOCIDO = (short) 0;

	/** Medida de tipo longitud. */
	public static final short TIPO_MEDIDA_LONGITUD = (short) 1;

	/** Medida de tipo volumen. */
	public static final short TIPO_MEDIDA_VOLUMEN = (short) 2;

	/** Medida de tipo peso. */
	public static final short TIPO_MEDIDA_PESO = (short) 3;
	// ========================================================================

	// ========================================================================
	// TIPOS DE CAMPOS
	// ========================================================================
	/** Campo de tipo desconocido. */
	public static final short TIPO_CAMPO_DESCONOCIDO = (short) 0;

	/** Campo de tipo dato. */
	public static final short TIPO_CAMPO_DATO = (short) 1;

	/** Campo de tipo tabla. */
	public static final short TIPO_CAMPO_TABLA = (short) 2;

	/** Campo de tipo especial. */
	public static final short TIPO_CAMPO_ESPECIAL = (short) 3;

	/** Literales de los tipos de campos posibles. */
	public static final String[] listaTiposCampo = new String[] {
			"Desconocido", "Dato", "Tabla", "Especial" };

	/**
	 * Obtiene el nombre del tipo de campo.
	 * 
	 * @param tipo
	 *            Tipo de campo.
	 * @return Nombre del tipo de campo.
	 */
	public static String getNombreTipoCampo(short tipo) {
		if ((tipo >= 0) && (tipo < listaTiposCampo.length))
			return listaTiposCampo[tipo];
		else
			return null;
	}

	// ========================================================================

	// ========================================================================
	// TIPOS DE DATOS DEL CAMPO DATO
	// ========================================================================
	/** Dato de tipo desconocido. */
	public static final short TIPO_DATO_DESCONOCIDO = (short) 0;

	/** Dato de tipo texto corto. */
	public static final short TIPO_DATO_TEXTO_CORTO = (short) 1;

	/** Dato de tipo texto largo. */
	public static final short TIPO_DATO_TEXTO_LARGO = (short) 2;

	/** Dato de tipo fecha. */
	public static final short TIPO_DATO_FECHA = (short) 3;

	/** Dato de tipo numérico. */
	public static final short TIPO_DATO_NUMERICO = (short) 4;

	/** Dato de tipo referencia. */
	public static final short TIPO_DATO_REFERENCIA = (short) 5;
	// ========================================================================

	// ========================================================================
	// IDENTIFICADORES DE LOS CAMPOS ESPECIALES
	// ========================================================================
	/** Identificador del código de referencia. */
	public static final String CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA = "-1";

	/** Identificador del número de expediente. */
	public static final String CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE = "-2";

	/** Identificador del título. */
	public static final String CAMPO_ESPECIAL_ID_TITULO = "-3";

	/** Nivel de descripción. */
	public static final String CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION = "-4";

	/** Nombre del descriptor. */
	public static final String CAMPO_ESPECIAL_ID_NOMBRE_DESCRIPTOR = "-5";

	/**
	 * Campos Especiales
	 */
	public static final String[] CAMPOS_ESPECIALES = new String[] {
			CAMPO_ESPECIAL_ID_CODIGO_REFERENCIA,
			CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE, CAMPO_ESPECIAL_ID_TITULO,
			CAMPO_ESPECIAL_ID_NIVEL_DESCRIPCION,
			CAMPO_ESPECIAL_ID_NOMBRE_DESCRIPTOR };

	/** Identificador que indica que se busque por cualquier tipo de descriptor */
	public static final String CAMPO_ESPECIAL_TODOS_DESCRIPTORES = "-100";

	// ========================================================================

}
