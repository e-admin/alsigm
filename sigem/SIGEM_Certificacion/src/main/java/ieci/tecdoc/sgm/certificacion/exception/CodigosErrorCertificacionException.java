package ieci.tecdoc.sgm.certificacion.exception;

public class CodigosErrorCertificacionException {
		public static final long ERROR_BASE = 71000000; 
	
		/**
		 * Error inesperado en la aplicación
		 */
		public static final long ERROR_INESPERADO = ERROR_BASE + 1;
		
		/**
		 * Error en la lectura del fichero de configuración del PDF
		 */
		public static final long ERROR_LECTURA_FICHERO_CONFIGURACION = ERROR_BASE + 2;
		
		/**
		 * Error en la lectura del fichero de datos del certificado de firma
		 */
		public static final long ERROR_LECTURA_FICHERO_CERTIFICADO = ERROR_BASE + 3;
		
		/**
		 * Error al generar la parte de datos comunes del XML
		 */
		public static final long ERROR_GENERACION_DATOS_COMUNES = ERROR_BASE + 4;
		
		/**
		 * Elemento no reconocido en el fichero de configuración del PDF
		 */
		public static final long ERROR_ELEMENTO_NO_RECONOCIDO = ERROR_BASE + 5;
		
		/**
		 * Error al obtener la posición de un elemento de la configuración del PDF
		 */
		public static final long ERROR_OBTENER_POSICION_ELEMENTO = ERROR_BASE + 6;
		
		/**
		 * Error en el procesamiento de un valor de cabecera del fichero de configuración del PDF
		 */
		public static final long ERROR_PROCESAR_VALOR_CABECERA = ERROR_BASE + 7;
		
		/**
		 * Error en el procesamiento de un valor de certificación del fichero de configuración del PDF
		 */
		public static final long ERROR_PROCESAR_VALOR_CERTIFICACION = ERROR_BASE + 8;
		
		/**
		 * Error en el procesamiento de un valor central del fichero de configuración del PDF
		 */
		public static final long ERROR_PROCESAR_VALOR_CENTRAL = ERROR_BASE + 9;
		
		/**
		 * Error al añadir un nuevo valor leído del fichero de configuración del PDF
		 */
		public static final long ERROR_ADJUNTAR_VALOR = ERROR_BASE + 10;
		
		/**
		 * Error al añadir un nuevo valor de una imagen leído del fichero de configuración del PDF
		 */
		public static final long ERROR_PROCESAR_VALOR_IMAGEN = ERROR_BASE + 11;
		
		/**
		 * Error al firmar el PDF con el certificado de la entidad correspondiente
		 */
		public static final long ERROR_FIRMA_PDF = ERROR_BASE + 12;

		/**
		 * Error al generar el PDF de la certificación
		 */
		public static final long ERROR_GENERACION_CERTIFICACION = ERROR_BASE + 13;
		
		/**
		 * Error al insertar la marca de agua del PDF
		 */
		public static final long ERROR_INSERTAR_MARCA_AGUA = ERROR_BASE + 14;
		
		/**
		 * Error al crear los datos de cabecera del PDF
		 */
		public static final long ERROR_CREAR_DATOS_CABECERA = ERROR_BASE + 15;
		
		/**
		 * Error al crear los datos de certificación del PDF
		 */
		public static final long ERROR_CREAR_DATOS_CERTIFICACION = ERROR_BASE + 16;
		
		/**
		 * Error al crear los datos centrales del PDF
		 */
		public static final long ERROR_CREAR_DATOS_CENTRALES = ERROR_BASE + 17;
		
		/**
		 * Error al insertar el logo de la entidad en el PDF
		 */
		public static final long ERROR_INSERTAR_LOGO_ENTIDAD = ERROR_BASE + 18;
		
		/**
		 * Error al obtener el máximo número de campos por linea
		 */
		public static final long ERROR_OBTENER_MAX_CAMPOS_LINEA = ERROR_BASE + 19;
		
		/**
		 * Error al obtener el número de campos de una linea
		 */
		public static final long ERROR_OBTENER_NUMERO_CAMPOS_LINEA = ERROR_BASE + 20;
		
		/**
		 * Error al obtener el número de lineas de un bloque
		 */
		public static final long ERROR_OBTENER_NUMERO_LINEAS = ERROR_BASE + 21;
		
		/**
		 * Error al obtener los campos de una línea
		 */
		public static final long ERROR_OBTENER_CAMPOS_LINEA = ERROR_BASE + 22;
		
		/**
		 * Error al ordenar los campos
		 */
		public static final long ERROR_ORDENAR_CAMPOS = ERROR_BASE + 23;
		
		/**
		 * Error al obtener el valor de un campo del XML
		 */
		public static final long ERROR_OBTENER_VALOR_XML = ERROR_BASE + 24;
		
		/**
		 * Error al obtener valores de campos múltiples del XML
		 */
		public static final long ERROR_OBTENER_VALORES_MULTIPLES_XML = ERROR_BASE + 25;
		
		/**
		 * Error al redistribuir las longitudes de las columnas del PDF
		 */
		public static final long ERROR_REDISTRIBUIR_LONGITUDES_COLUMNA = ERROR_BASE + 26;
		
		/**
		 * Error al generar el XML
		 */
		public static final long ERROR_GENERACION_XML = ERROR_BASE + 27;
		
		/**
		 * Error al obtener el certificado de firma del PDF de la entidad 
		 */
		public static final long ERROR_OBTENER_CERTIFICADO = ERROR_BASE + 28;
		
		/**
		 * Error al obtener una certificacion 
		 */
		public static final long ERROR_OBTENER_CERTIFICACION = ERROR_BASE + 29;
		
		/**
		 * Error al obtener las certificaciones de un usuario 
		 */
		public static final long ERROR_OBTENER_CERTIFICACIONES = ERROR_BASE + 30;
		
		/**
		 * Error al insertar la certificacion de un usuario 
		 */
		public static final long ERROR_INSERTAR_CERTIFICACION = ERROR_BASE + 31;
		
		/**
		 * Error al eliminar la certificacion de un usuario 
		 */
		public static final long ERROR_ELIMINAR_CERTIFICACION = ERROR_BASE + 32;
		
		/**
		 * Error al almacenar la certificación en BBDD 
		 */
		public static final long ERROR_ALMACENAR_CERTIFICACION = ERROR_BASE + 33;
}
