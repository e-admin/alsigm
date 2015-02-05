package ieci.tecdoc.sgm.certificacion.util;

public class Defs {
		public static final String PUNTO = ".";
		public static final String BARRA = "/";
		public static final String NULO = "null";
	
		/****** ETIQUETAS XML ******/
		public static final String TAG_XML_CERTIFICACION = "DatosCertificacion";
		public static final String TAG_XML_DATOS_COMUNES = "DatosComunes";
		public static final String TAG_XML_DATOS_ESPECIFICOS = "DatosEspecificos";
		public static final String TAG_XML_USUARIO = "Usuario";
		public static final String TAG_XML_NOMBRE = "Nombre";
		public static final String TAG_XML_APELLIDOS = "Apellidos";
		public static final String TAG_XML_ID = "DocumentoIdentidad";
		public static final String TAG_XML_ENTIDAD = "Entidad";
		public static final String TAG_XML_DOMICILIO = "Domicilio";
		public static final String TAG_XML_LOCALIDAD = "Localidad";
		public static final String TAG_XML_PROVINCIA = "Provincia";
		public static final String TAG_XML_CP = "CodigoPostal";
		public static final String TAG_XML_MAIL = "CorreoElectronico";
		public static final String TAG_XML_TELEFONO = "Telefono";
		public static final String TAG_XML_FECHA = "Fecha";
		public static final String TAG_XML_HORA = "Hora";
		
		/****** TIPO DE DATO DEL PROPERTIES  ******/
		public static final int TIPO_IMAGEN = 1;
		public static final int TIPO_CABECERA = 2;
		public static final int TIPO_CERTIFICACION = 3;
		public static final int TIPO_CENTRAL = 4;
		public static final int TIPO_TITULO = 5;
		public static final int TIPO_INDEFINIDO = -1;
		
		/****** DEFINICION NOMBRE PROPIEDADES ******/
		public static final String FILE_PROPERTIES1 = "configuracion";
		public static final String FILE_PROPERTIES2 = ".properties";
		public static final String TAG_PROP_IMAGEN = "imagen";
		public static final String TAG_PROP_IMAGEN_LOGO = "logo";
		public static final String TAG_PROP_IMAGEN_MARCA_AGUA = "marca_agua";
		public static final String TAG_PROP_IMAGEN_LOGO_FIRMA = "firma";
		public static final String TAG_PROP_CABECERA = "cabecera";
		public static final String TAG_PROP_CENTRAL = "centro";
		public static final String TAG_PROP_CERTIFICACION = "certificacion";
		public static final String TAG_PROP_ETIQUETA = "tag";
		public static final String TAG_PROP_RUTA = "path";
		public static final String TAG_PROP_ORDEN = "orden";
		public static final String TAG_PROP_LINEA = "linea";
		public static final String TAG_PROP_TITULO = "titulo";
		
		/****** DEFINICION TIPO DEL NOMBRE PROPIEDADES ******/
		public static final int TIPO_PROP_ETIQUETA = 1;
		public static final int TIPO_PROP_RUTA = 2;
		public static final int TIPO_PROP_ORDEN = 3;
		public static final int TIPO_PROP_LINEA = 4;
		public static final int TIPO_PROP_TITULO = 5;
		public static final int TIPO_PROP_INDEFINIDO = -1;
		
		/*************** XML PAGOS ***************/
		public static final String TAG_XML_PAGOS = "Pagos";
		public static final String TAG_XML_PAGO = "Pago";
		public static final String TAG_XML_PAGO_ID_TASA = "IdTasa";
		public static final String TAG_XML_PAGO_ID_ENTIDAD_EMISORA = "IdEntidadEmisora";
		public static final String TAG_XML_PAGO_IMPORTE = "Importe";
		public static final String TAG_XML_PAGO_FECHA = "Fecha";
		public static final String TAG_XML_PAGO_HORA = "Hora";
		
		
		/***************** OTROS ********************/
		public static final String MONEDA = "Euros";
		public static final String SEPARADOR = "/";
		public static final String CONF = "Conf";
		public static final String RECURSOS = "ieci/tecdoc/sgm/certificacion/resources";
		public static final String PROPIEDADES = "properties";
		public static final String FORMATO_FECHA = "dd-MM-yyyy HH:mm:ss";
		
		/***************** DEFINICION RUTA DE CERTIFICADOPDF ***************/
		public static final String TAG_RUTA_MULTIPLES_PAGOS = "DatosEspecificos/Pagos";
		public static final String TAG_OBJETO_MULTIPLES_PAGOS = "Pago";
		
		/***************** DEFINICIONES DEL PROPERTIES DEL CERTIFICADO ***********/
		public static final String KEYSTORE_KEY = "keystore.key";
		public static final String KEYSTORE_PASS = "keystore.pass";
		public static final String KEYSTORE_ALIAS = "keystore.alias";
		public static final String FILE_KEYSTORE = "certificado.properties";
		
		/**************** TIPOS DE CERTIFICACIONES **************************/
		public static final String CERTIFICACION_PAGOS = "_pago";
}
