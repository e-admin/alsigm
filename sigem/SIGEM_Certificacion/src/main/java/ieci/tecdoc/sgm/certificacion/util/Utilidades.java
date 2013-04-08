package ieci.tecdoc.sgm.certificacion.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import xtom.parser.Element;
import xtom.parser.Parser;
import xtom.parser.XMLTree;

import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatoGenerico;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCabecera;
import ieci.tecdoc.sgm.certificacion.xml.builder.XmlTextBuilder;

/**
 * Clase que recopila métodos de utilidades usados en la aplicación
 * @author José Antonio Nogales
 */
public class Utilidades {
		private static final Logger logger = Logger.getLogger(Utilidades.class);
				
		/**
		 * Método que comprueba si una cadena está vacia
		 * @param cadena Cadena de texto a comprobar
		 * @return tru si está vacía, false si no lo está
		 */
		public static boolean estaVacia(String cadena){
			return (cadena == null || "".equals(cadena));
		}
		
		/**
		 * Método que calcula el máximo número de campos entre todas las lineas
		 * @param datosCabecera Datos de la cabecera
		 * @return Máximo número de campos
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static int maxCamposPorLinea (DatosCabecera datosCabecera) throws CertificacionException {
			try {
				int valorMax = 0;
				ArrayList lineas = new ArrayList();
				if(datosCabecera!=null && datosCabecera.getDatoCabecera()!=null){
					ArrayList datos = datosCabecera.getDatoCabecera();
					for(int i=0; i<datos.size(); i++){
						DatoGenerico datoGenerico = (DatoGenerico)datos.get(i);
						int linea = datoGenerico.getLinea();
						if (linea <= lineas.size())
							lineas.set(linea-1, new Integer(((Integer)lineas.get(linea-1)).intValue()+1));
						else {
							realocarArray(lineas, linea);
							lineas.add(new Integer(1));
						}
					}
					for(int i=0; i<lineas.size(); i++){
						int valorLinea = ((Integer)lineas.get(i)).intValue();
						valorMax = (valorLinea > valorMax) ? valorLinea : valorMax;
					}
				}
				return valorMax;
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener el máximo número de campos de una línea", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_MAX_CAMPOS_LINEA,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que calcula el número de campos de una linea
		 * @param datosCabecera Datos de la cabecera
		 * @param numLinea Número de linea a comprobar
		 * @return Número de campos de la linea
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static int numeroCamposLinea (DatosCabecera datosCabecera, int numLinea) throws CertificacionException {
			try {
				int cantidad = 0;
				if(datosCabecera!=null && datosCabecera.getDatoCabecera()!=null){
					ArrayList datos = datosCabecera.getDatoCabecera();
					for(int i=0; i<datos.size(); i++){
						DatoGenerico datoGenerico = (DatoGenerico)datos.get(i);
						int linea = datoGenerico.getLinea();
						if (linea == numLinea)
							cantidad++;
					}
				}
				return cantidad;
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener el número de campos de una línea", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_NUMERO_CAMPOS_LINEA,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que calcula el núermo de lineas de un bloque de datos
		 * @param datos Datos sobre el que calcularlo
		 * @return Número de lineas del bloque
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static int maxLinea (ArrayList datos) throws CertificacionException {
			try {
				int maxLinea = 0;
				if(datos!=null && datos.size()>0){
					for(int i=0; i<datos.size(); i++){
						DatoGenerico datoGenerico = (DatoGenerico)datos.get(i);
						int linea = datoGenerico.getLinea();
						if (linea > maxLinea)
							maxLinea = linea;;
					}
				}
				return maxLinea;
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener el número de líneas", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_NUMERO_LINEAS,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene los campos de una linea
		 * @param datos Datos de los que obtener los campo
		 * @param numLinea Número de linea de la que obtener los campos
		 * @return Los campos de la linea
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static ArrayList obtenerCamposLinea (ArrayList datos, int numLinea) throws CertificacionException{
			try {
				ArrayList campos = new ArrayList();
				if(datos!=null && datos.size()>0){
					for(int i=0; i<datos.size(); i++){
						DatoGenerico datoGenerico = (DatoGenerico)datos.get(i);
						int linea = datoGenerico.getLinea();
						if (linea == numLinea)
							campos.add(datoGenerico);
					}
				}
				return ordenarArray(campos);
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener los campos de una línea", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_CAMPOS_LINEA,
					e.getMessage(),
					e.getCause());
			}
		}		
		
		/**
		 * Método que aumenta la capacidad de un array
		 * @param array Array a realocar
		 * @param size Nuevo tamaño
		 */
		public static void realocarArray(ArrayList array, int size){
			for(int i=array.size(); i<size-1; i++)
				array.add(new Integer(0));
		}
		
		/**
		 * Método que ordena los campos un array
		 * @param campos Listado de campos a ordenar
		 * @return Listado de campos ordenados
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static ArrayList ordenarArray(ArrayList campos) throws CertificacionException {
			try {
				ArrayList ordenado = new ArrayList();
				while (campos.size() > 0){
					int valor = Integer.MAX_VALUE, posicion = -1;
					for (int i=0; i< campos.size(); i++){
						DatoGenerico datoGenerico = (DatoGenerico)campos.get(i);
						if (datoGenerico.getOrden() < valor){
							valor = datoGenerico.getOrden();
							posicion = i;
						}
					}
					ordenado.add(campos.get(posicion));
					campos.remove(posicion);
				}
				return ordenado;
			} catch (Exception e) {
				logger.error("Se ha producido un error al intentar ordenar los campos", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_ORDENAR_CAMPOS,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene el valor de un nodo determinado dentro de un XML 
		 * @param xml XML del que obtener el valor
		 * @param etiqueta Ruta de la que obtener el valor
		 * @return Valor obtenido del XML
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static String obtenerValorXML(String xml, String etiqueta) throws CertificacionException {
			try {
				if (etiqueta == null || "".equals(etiqueta))
					return "";
				
				Parser parser = new Parser(new ByteArrayInputStream(xml.getBytes()));
				XMLTree tree = parser.parse();
				Element root = tree.getRootElement();
				Element search = root.getElementByPath(etiqueta);
				return estaVacia(search.getValue()) ? "" : search.getValue();
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener un valor del XML", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_VALOR_XML,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene el valor de nodos múltiples dentro de un XML 
		 * @param xml XML del que obtener el valor
		 * @param etiqueta Ruta de la que obtener el valor
		 * @return Listado de elementos obtenidos del XML
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static Element[] obtenerValoresMultiplesXML(String xml, String etiqueta) throws CertificacionException {
			try {
				if (etiqueta == null || "".equals(etiqueta))
					return new Element[0];
				
				Parser parser = new Parser(new ByteArrayInputStream(xml.getBytes()));
				XMLTree tree = parser.parse();
				Element root = tree.getRootElement();
				Element[] search = root.getElementsByPath(etiqueta);
				return search;
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener valores múltiples del XML", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_VALORES_MULTIPLES_XML,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene el valor de nodos múltiples dentro de un XML 
		 * @param xml XML del que obtener el valor
		 * @param etiqueta Ruta de la que obtener el valor
		 * @return XML con el contenido de los nodos múltiples
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static String obtenerXMLValoresMultiples(String name, Element[] elementos) throws CertificacionException {
			XmlTextBuilder bdr = new XmlTextBuilder();
			bdr.addOpeningTag(name);
			obtenerXMLValoresMultiples(bdr, elementos);
			bdr.addClosingTag(name);
			return bdr.getText();
		}
		
		/**
		 * Método que convierte un listado de elementos en XML
		 * @param bdr Generador de XML
		 * @param elementos Elementos a convertir a XML 
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static void obtenerXMLValoresMultiples(XmlTextBuilder bdr, Element[] elementos) throws CertificacionException {
			try {
				for(int i=0; i<elementos.length; i++){
					Element elemento = elementos[i];
					if (elemento.hasChildren()){
						bdr.addOpeningTag(elemento.getName());
						obtenerXMLValoresMultiples(bdr, elemento.getChildren());
						bdr.addClosingTag(elemento.getName());
					}else{
						bdr.addSimpleElement(elemento.getName(), elemento.getValue());
					}
				}
			} catch (Exception e) {
				logger.error("Se ha producido un error al obtener valores múltiples del XML", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_VALORES_MULTIPLES_XML,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene el contenido de un fichero
		 * @param fileName Nombre del fichero
		 * @return Datos del fichero
		 * @throws Exception En caso de producirse algún error
		 */
		public static InputStream leerFicheroRecursos(String fileName) throws Exception {
			InputStream inputStream = null;

			try {
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			} catch (Exception exc) {
				throw exc;
			}
			
			return inputStream;
		}
		
		/**
		 * Método que obtiene el contenido de un fichero
		 * @param fileName Nombre del fichero
		 * @return Array con el contenido del fichero
		 * @throws Exception En caso de producirse algún error
		 */
		public static byte[] leerFicheroRecursosArray(String fileName) throws Exception {
			byte[] bytes = null;

			try {
				InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				bytes = obtenerBytes(inputStream);
			} catch (Exception exc) {
				throw exc;
			}
			
			return bytes;
		}
		
		/**
		 * Método que obtiene un array de bytes a partir de un input stream
		 * @param stream Input stream a convertir a array de bytes
		 * @return Array de bytes con el contenido
		 * @throws Exception En caso de producirse algún error
		 */
		public static byte[] obtenerBytes(InputStream stream) throws Exception {
			byte[] bytes = null;
			byte[] buffer;
			BufferedInputStream input = new BufferedInputStream(stream);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int available, chunkSize, count;

			try {
				chunkSize = 10240;
				buffer = new byte[chunkSize];
				available = input.available();
				while (available > 0) {
					count = input.read(buffer);
					output.write(buffer, 0, count);
					available = input.available();
				}
				bytes = output.toByteArray();
			} catch (Exception exc) {
				throw exc;
			} finally {
				try {
					output.close();
				} catch (Exception exc) {
					throw exc;
				}
			}
			return bytes;
		}
		
		/**
		 * Método que calcula el tamaño de una cloumna de datos del fichero PDF dependiendo del contenido
		 * de esta y del contenido del resto de cloumnas que componen la linea
		 * @param campos Listado con los contenidos de cada columna de una linea
		 * @return Listado de longitudes de cada columna
		 * @throws CertificacionException
		 */
		public static float[] calcularLongitudesColumna(String[] campos) throws CertificacionException{
			try {
				int total = 0;
				float[] longitudes = new float[campos.length];
				for(int i=0; i<campos.length; i++)
					total += campos[i].length();
				for(int i=0; i<campos.length; i++)
					longitudes[i] = new Float(campos[i].length() * 100 / total).floatValue();
				for(int i=0; i<campos.length; i++)
					if (longitudes[i] <= 20)
						redistribuirLongitudes(longitudes, i);
				return longitudes;
			}catch(Exception e) {
				logger.error("Se ha producido un error al redistribuir las longitudes de las columnas del PDF", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_REDISTRIBUIR_LONGITUDES_COLUMNA,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que redistribuye la longitud de las columnas de una linea en caso que alguna
		 * sea muy pequeña.
		 * @param longitudes Longitudes de las columnas
		 * @param i Posición de la columna a la que hay que aumentar su tamaño 
		 */
		public static void redistribuirLongitudes(float[] longitudes, int i){
			longitudes[i] = longitudes[i] + 10;
		}
		
		/**
		 * Método que formatea una fecha al formato dd-MM-yyyy HH:mm:ss
		 * @param fecha Fecha a formatear
		 * @return Cadena con la fecha formateada
		 */
		public static String formatearFecha(Date fecha){
			SimpleDateFormat sdf = new SimpleDateFormat(Defs.FORMATO_FECHA);
			return sdf.format(fecha);
		}
		
		/**
		 * Método que convierte un string a utf-8
		 * @param text Texto a convertir
		 * @return Texto formateado a UTF-8
		 * @throws Exception En caso de producirse algún error
		 */
		public static byte[] fromStrToUTF8(String text) throws Exception {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				OutputStreamWriter output = new OutputStreamWriter(os, "UTF8");
				output.write(text);
				output.flush();
				output.close();

				return(os.toByteArray());
		}
		
}
