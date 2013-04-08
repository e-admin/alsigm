package ieci.tecdoc.sgm.certificacion.pdf;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatoGenerico;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCabecera;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCentrales;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCertificacion;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosPropiedades;
import ieci.tecdoc.sgm.certificacion.pdf.bean.Imagenes;
import ieci.tecdoc.sgm.certificacion.pdf.bean.Posicion;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;

/**
 * Clase que lee las propiedades de configuración de los datos para la certificación PDF
 * @author José Antonio Nogales
 */
public class LectorPropiedadesPDF {
		private static final Logger logger = Logger.getLogger(LectorPropiedadesPDF.class); 
	
		/**
		 * Método que lee el fichero de propiedades donde se configura el formato de la certificación PDF
		 * @param propiedades Fichero de propiedades
		 * @return Objeto con los datos contenidos en el fichero de propiedades
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static DatosPropiedades LeerPropiedades(Properties propiedades) throws CertificacionException {
				if(propiedades == null)
					return null;
				
				DatosPropiedades datosPropiedades = new DatosPropiedades();
				DatosCabecera datosCabecera = new DatosCabecera();
				DatosCertificacion datosCertificacion = new DatosCertificacion();
				DatosCentrales datosCentrales = new DatosCentrales();
				Imagenes imagenes = new Imagenes();
				
				try {
					for (Enumeration e = propiedades.keys(); e.hasMoreElements() ; ) {
					    String nombre = (String)e.nextElement();
					    String valor = (String)propiedades.getProperty(nombre);
					    
					    switch (tipoPropiedad(nombre)){
					    	case Defs.TIPO_IMAGEN: 
					    		establecerValorImagen(imagenes, nombre, valor);
					    		break;
					    	case Defs.TIPO_CABECERA: 
					    		establecerValorCabecera(datosCabecera, nombre, valor);
					    		break;
					    	case Defs.TIPO_CENTRAL: 
					    		establecerValorCentral(datosCentrales, nombre, valor);
					    		break;
					    	case Defs.TIPO_CERTIFICACION: 
					    		establecerValorCertificacion(datosCertificacion, nombre, valor);
					    		break;
					    	case Defs.TIPO_TITULO:
					    		datosPropiedades.setTitulo(valor);
					    		break;
					    	case Defs.TIPO_INDEFINIDO:
					    		logger.error("Se ha encontrado un elemento no reconocido en el fichero de configuración");
					    		throw new CertificacionException(
					    			CodigosErrorCertificacionException.ERROR_ELEMENTO_NO_RECONOCIDO,
					    			"Elemento " + nombre + " no reconocido",
					    			null);
					    }				    
					}
				} catch (CertificacionException e){
					throw e;
				}
				
				datosPropiedades.setCabecera(compactarRutas(datosCabecera));
				datosPropiedades.setCentro(datosCentrales);
				datosPropiedades.setCertificacion(datosCertificacion);
				datosPropiedades.setImagenes(imagenes);
				
				return datosPropiedades;
		}
	
		/**
		 * Método que indica de que tipo es el dato leído (imagen, cabecera, centro, certificación o título)
		 * @param nombre Nombre de la propiedad leída
		 * @return Tipo de dato
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static int tipoPropiedad(String nombre) throws CertificacionException {
			try {
				if (nombre.startsWith(Defs.TAG_PROP_IMAGEN))
					return Defs.TIPO_IMAGEN;
				else if (nombre.startsWith(Defs.TAG_PROP_CABECERA))
					return Defs.TIPO_CABECERA;
				else if (nombre.startsWith(Defs.TAG_PROP_CENTRAL))
					return Defs.TIPO_CENTRAL;
				else if (nombre.startsWith(Defs.TAG_PROP_CERTIFICACION))
					return Defs.TIPO_CERTIFICACION;
				else if (nombre.startsWith(Defs.TAG_PROP_TITULO))
					return Defs.TIPO_TITULO;
				else
					return Defs.TIPO_INDEFINIDO;
			}catch(Exception e){
				logger.error("Se ha encontrado un elemento no reconocido en el fichero de configuración", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_ELEMENTO_NO_RECONOCIDO,
					"Elemento " + nombre + " no reconocido",
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene el tipo de propiedad (tag, path, orden, linea, titulo, ...)
		 * @param nombre Nombre de la propiedad leída
		 * @return Nombre del tipo de propiedad
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static String obtenerNombreElemento(String nombre) throws CertificacionException{
			try {
				int index1 = nombre.indexOf(Defs.PUNTO);
				int index2 = nombre.indexOf(Defs.PUNTO, index1+1);
				if (index1 != -1 && index2 != -1 && index1 != index2)
					return nombre.substring(index1 + 1, index2);
				return null;
			}catch(Exception e){
				logger.error("Se ha encontrado un elemento no reconocido en el fichero de configuración", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_ELEMENTO_NO_RECONOCIDO,
					"Elemento " + nombre + " no reconocido",
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene la posición de la propiedad de cabecera que se está leyendo dentro del 
		 * conjunto de propiedades ya leídas
		 * @param datosCabecera Datos ya leídos de la cabecera
		 * @param elemento Elemento del que se quiere buscar su posición
		 * @return Posición del elemento actual. -1 si no se había insertado ninguna propiedad de este elemento todavía
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static int posicionElemento(DatosCabecera datosCabecera, String elemento) throws CertificacionException{
			try{
				int posicion = -1;
				if (datosCabecera != null){
					int size = datosCabecera.getSize();
					for(int i=0; i<size; i++){
						if (elemento.equals(datosCabecera.getDatoCabecera(i).getEtiqueta())) {
							posicion = i;
							break;
						}
					}
				}
				return posicion;
			}catch(Exception e){
				logger.error("Se ha producido un error al obtener la posición de un elemento", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_POSICION_ELEMENTO,
					"Elemento " + elemento,
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene la posición de la propiedad de certificación que se está leyendo dentro del 
		 * conjunto de propiedades ya leídas
		 * @param datosCertificacion Datos ya leídos de la certificación
		 * @param elemento Elemento del que se quiere buscar su posición
		 * @return Posición del elemento actual. -1 si no se había insertado ninguna propiedad de este elemento todavía
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static int posicionElemento(DatosCertificacion datosCertificacion, String elemento) throws CertificacionException{
			try{
				int posicion = -1;
				if (datosCertificacion != null){
					int size = datosCertificacion.getSize();
					for(int i=0; i<size; i++){
						if (elemento.equals(datosCertificacion.getDatoCertificacion(i).getEtiqueta())) {
							posicion = i;
							break;
						}
					}
				}
				return posicion;
			}catch(Exception e){
				logger.error("Se ha producido un error al obtener la posición de un elemento", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_POSICION_ELEMENTO,
					"Elemento " + elemento,
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene la posición de la propiedad del centro que se está leyendo dentro del 
		 * conjunto de propiedades ya leídas
		 * @param datosCertificacion Datos ya leídos del centro
		 * @param elemento Elemento del que se quiere buscar su posición
		 * @return Posición del elemento actual. -1 si no se había insertado ninguna propiedad de este elemento todavía
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static int posicionElemento(DatosCentrales datosCentrales, String elemento) throws CertificacionException{
			try{
				int posicion = -1;
				if (datosCentrales != null){
					int size = datosCentrales.getSize();
					for(int i=0; i<size; i++){
						if (elemento.equals(datosCentrales.getDatoCentral(i).getEtiqueta())) {
							posicion = i;
							break;
						}
					}
				}
				return posicion;
			}catch(Exception e){
				logger.error("Se ha producido un error al obtener la posición de un elemento", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_OBTENER_POSICION_ELEMENTO,
					"Elemento " + elemento,
					e.getCause());
			}
		}
				
		/**
		 * Método que almacena un valor leído del fichero de propiedades
		 * @param datosCabecera Datos ya almacenados de la cabecera
		 * @param posicion Posición en la que se va a insertar el dato
		 * @param tipo Tipo de dato
		 * @param ordenRuta Orden de la ruta (path.1, path.2, ...)
		 * @param elemento Elemento leído
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void insertarValorCabecera(DatosCabecera datosCabecera, int posicion, int tipo, int ordenRuta, String elemento, String nombre, String valor) throws CertificacionException{
			try {
				DatoGenerico datoGenerico;
				if (posicion != -1)
					datoGenerico = datosCabecera.getDatoCabecera(posicion);
				else datoGenerico = new DatoGenerico();
				
				modificarDatoGenerico(datoGenerico, tipo, ordenRuta, valor);
				
				if (tipo != Defs.TIPO_PROP_INDEFINIDO){
					datoGenerico.setEtiqueta(elemento);
					if (posicion == -1)
						datosCabecera.addDatoCabecera(datoGenerico);
					else datosCabecera.setDatoCabecera(datoGenerico, posicion);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos de cabecera", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CABECERA,
					"Elemento " + elemento,
					e.getCause());
			}
		}

		/**
		 * Método que almacena un valor leído del fichero de propiedades
		 * @param datosCabecera Datos ya almacenados de la certificación
		 * @param posicion Posición en la que se va a insertar el dato
		 * @param tipo Tipo de dato
		 * @param ordenRuta Orden de la ruta (path.1, path.2, ...)
		 * @param elemento Elemento leído
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void insertarValorCertificacion(DatosCertificacion datosCertificacion, int posicion, int tipo, int ordenRuta, String elemento, String nombre, String valor) throws CertificacionException{
			try{
				DatoGenerico datoGenerico;
				if (posicion != -1)
					datoGenerico = datosCertificacion.getDatoCertificacion(posicion);
				else datoGenerico = new DatoGenerico();
				
				modificarDatoGenerico(datoGenerico, tipo, ordenRuta, valor);
				
				if (tipo != Defs.TIPO_PROP_INDEFINIDO){
					datoGenerico.setEtiqueta(elemento);
					if (posicion == -1)
						datosCertificacion.addDatoCertificacion(datoGenerico);
					else datosCertificacion.setDatoCertificacion(datoGenerico, posicion);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos de certificación", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CERTIFICACION,
					"Elemento " + elemento,
					e.getCause());
			}
		}
		
		/**
		 * Método que almacena un valor leído del fichero de propiedades
		 * @param datosCabecera Datos ya almacenados del centro
		 * @param posicion Posición en la que se va a insertar el dato
		 * @param tipo Tipo de dato
		 * @param ordenRuta Orden de la ruta (path.1, path.2, ...)
		 * @param elemento Elemento leído
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void insertarValorCentral(DatosCentrales datosCentrales, int posicion, int tipo, int ordenRuta, String elemento, String nombre, String valor) throws CertificacionException{
			try{
				DatoGenerico datoGenerico;
				if (posicion != -1)
					datoGenerico = datosCentrales.getDatoCentral(posicion);
				else datoGenerico = new DatoGenerico();
				
				modificarDatoGenerico(datoGenerico, tipo, ordenRuta, valor);
				
				if (tipo != Defs.TIPO_PROP_INDEFINIDO){
					datoGenerico.setEtiqueta(elemento);
					if (posicion == -1)
						datosCentrales.addDatoCentral(datoGenerico);
					else datosCentrales.setDatoCentral(datoGenerico, posicion);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos centrales", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CENTRAL,
					"Elemento " + elemento,
					e.getCause());
			}
		}
		
		/**
		 * Método que añade el valor de una propiedad a un elemento
		 * @param datoGenerico Dato en el que añadir el valor
		 * @param tipo Tipo de dato
		 * @param ordenRuta Orden de la ruta (en caso de ser path.1, path.2, ...)
		 * @param valor Valor del elemento a añadir
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void modificarDatoGenerico(DatoGenerico datoGenerico, int tipo, int ordenRuta, String valor) throws CertificacionException{
			try{
				switch(tipo){
					case Defs.TIPO_PROP_ETIQUETA: 
						datoGenerico.setNombre(valor);
						break;
					case Defs.TIPO_PROP_RUTA:
						ArrayList datos = datoGenerico.getDatos();
						if (datos == null)
							datos = new ArrayList();
						if (ordenRuta == -1) {
							datos.add(valor);
						} else {
							if (ordenRuta <= datos.size()) {
								if ((String)datos.get(ordenRuta-1) != null) {
									datos.add(valor);								
								}else{
									datos.set(ordenRuta-1, valor);
								}
							} else {
								for(int i=datos.size(); i<ordenRuta-1; i++)
									datos.add(null);
								datos.add(valor);
							}
						}
						datoGenerico.setDatos(datos);
						break;
					case Defs.TIPO_PROP_ORDEN: 
						datoGenerico.setOrden(new Integer(valor).intValue());
						break;
					case Defs.TIPO_PROP_LINEA: 
						datoGenerico.setLinea(new Integer(valor).intValue());
						break;
					case Defs.TIPO_PROP_INDEFINIDO: 
						logger.error("Se ha encontrado un elemento no reconocido en el fichero de configuración");
						throw new CertificacionException(
							CodigosErrorCertificacionException.ERROR_ELEMENTO_NO_RECONOCIDO,
							"Tipo no reconocido para el valor " + valor,
							null);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al añadir un valor del fichero de configuración", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_ADJUNTAR_VALOR,
					"Valor " + valor,
					e.getCause());
			}
		}
		
		/**
		 * Método que elimina huecos vacios de los datos de cabecera
		 * @param datosCabecera Datos de cabecera a compactar
		 * @return Datos de cabecera compactados
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static DatosCabecera compactarRutas(DatosCabecera datosCabecera) throws CertificacionException{
			try{
				if (datosCabecera != null && datosCabecera.getDatoCabecera() != null){
					ArrayList datosArray = datosCabecera.getDatoCabecera();
					for(int i=0; i<datosArray.size(); i++){
						DatoGenerico dato = (DatoGenerico)datosArray.get(i);
						ArrayList rutas = dato.getDatos();
						if (rutas != null){
							ArrayList rutasCompactadas = new ArrayList();
							for(int j=0; j<rutas.size(); j++){
								if((String)rutas.get(j) != null)
									rutasCompactadas.add(rutas.get(j));
							}
							dato.setDatos(rutasCompactadas);
							datosArray.set(i, dato);
						}
					}
					datosCabecera.setDatosCabecera(datosArray);
				}
				
				return datosCabecera;
			}catch(Exception e){
				logger.error("Se ha producido un error en la lectura del fichero de configuración", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_LECTURA_FICHERO_CONFIGURACION,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que almacena un valor leído del fichero de propiedades correspondiente a las imágenes
		 * @param imagenes Datos de imágenes ya almacenados
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void establecerValorImagen(Imagenes imagenes, String nombre, String valor) throws CertificacionException{
			try{
				if ((Defs.TAG_PROP_IMAGEN + Defs.PUNTO + Defs.TAG_PROP_IMAGEN_LOGO).equals(nombre))
					imagenes.setImagenCabecera(valor);
				else if ((Defs.TAG_PROP_IMAGEN + Defs.PUNTO + Defs.TAG_PROP_IMAGEN_MARCA_AGUA).equals(nombre))
					imagenes.setMarcaAgua(valor);
				else if ((Defs.TAG_PROP_IMAGEN + Defs.PUNTO + Defs.TAG_PROP_IMAGEN_LOGO_FIRMA).equals(nombre))
					imagenes.setImagenFirma(valor);
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos de imágenes", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_IMAGEN,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que almacena un valor leído del fichero de propiedades correspondiente a la cabecera
		 * @param imagenes Datos de cabecera ya almacenados
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void establecerValorCabecera(DatosCabecera datosCabecera, String nombre, String valor) throws CertificacionException{
			try {
				String elemento = obtenerNombreElemento(nombre);
				if (!Utilidades.estaVacia(elemento)) {
					int posicion = posicionElemento(datosCabecera, elemento);
					int tipo = Defs.TIPO_PROP_INDEFINIDO;
					int ordenRuta = Defs.TIPO_PROP_INDEFINIDO;
					Posicion p = establecerValor(Defs.TAG_PROP_CABECERA, tipo, ordenRuta, elemento, nombre);
					insertarValorCabecera(datosCabecera, posicion, p.getTipo(), p.getOrdenRuta(), elemento, nombre, valor);
				}else{
					if ((Defs.TAG_PROP_CABECERA + Defs.PUNTO + Defs.TAG_PROP_TITULO).equals(nombre))
						datosCabecera.setTitulo(valor);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos de cabecera", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CABECERA,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que almacena un valor leído del fichero de propiedades correspondiente a la certificación
		 * @param imagenes Datos de certificación ya almacenados
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */		
		private static void establecerValorCertificacion(DatosCertificacion datosCertificacion, String nombre, String valor) throws CertificacionException{
			try{
				String elemento = obtenerNombreElemento(nombre);
				if (!Utilidades.estaVacia(elemento)) {
					int posicion = posicionElemento(datosCertificacion, elemento);
					int tipo = Defs.TIPO_PROP_INDEFINIDO;
					int ordenRuta = Defs.TIPO_PROP_INDEFINIDO;
					Posicion p = establecerValor(Defs.TAG_PROP_CERTIFICACION, tipo, ordenRuta, elemento, nombre);
					insertarValorCertificacion(datosCertificacion, posicion, p.getTipo(), p.getOrdenRuta(), elemento, nombre, valor);
				}else{
					if ((Defs.TAG_PROP_CERTIFICACION + Defs.PUNTO + Defs.TAG_PROP_TITULO).equals(nombre))
						datosCertificacion.setTitulo(valor);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos de certificación", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CERTIFICACION,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que almacena un valor leído del fichero de propiedades correspondiente al centro
		 * @param imagenes Datos del centro ya almacenados
		 * @param nombre Nombre de la prodiedad leída
		 * @param valor Valor de la propiedad leída
		 * @throws CertificacionException En caso de producirse algún error
		 */
		private static void establecerValorCentral(DatosCentrales datosCentrales, String nombre, String valor) throws CertificacionException{
			try{
				String elemento = obtenerNombreElemento(nombre);
				if (!Utilidades.estaVacia(elemento)) {
					int posicion = posicionElemento(datosCentrales, elemento);
					int tipo = Defs.TIPO_PROP_INDEFINIDO;
					int ordenRuta = Defs.TIPO_PROP_INDEFINIDO;
					Posicion p = establecerValor(Defs.TAG_PROP_CENTRAL, tipo, ordenRuta, elemento, nombre);
					insertarValorCentral(datosCentrales, posicion, p.getTipo(), p.getOrdenRuta(), elemento, nombre, valor);
				}else{
					if ((Defs.TAG_PROP_CENTRAL + Defs.PUNTO + Defs.TAG_PROP_TITULO).equals(nombre))
						datosCentrales.setTitulo(valor);
				}
			}catch(CertificacionException ce){
				throw ce;
			}catch(Exception e){
				logger.error("Se ha producido un error al procesar un valor de los datos centrales", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_PROCESAR_VALOR_CENTRAL,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método que obtiene la posición en la que debería un valor dentro de un elemento
		 * @param tag Etiqueta del fichero de propiedades
		 * @param tipo Tipo de dato
		 * @param ordenRuta Orden de la ruta (path.1, path.2, ...)
		 * @param elemento Nombre del elemento
		 * @param nombre Nombre del dato
		 * @return Posición del dato en el elemento
		 * @throws Exception En caso de producirse algún error
		 */
		private static Posicion establecerValor(String tag, int tipo, int ordenRuta, String elemento, String nombre) throws Exception{
			try{
				if ((tag + Defs.PUNTO + elemento + Defs.PUNTO + Defs.TAG_PROP_ETIQUETA).equals(nombre))
					tipo = Defs.TIPO_PROP_ETIQUETA;
				else if (nombre.startsWith(tag + Defs.PUNTO + elemento + Defs.PUNTO + Defs.TAG_PROP_RUTA)){
					tipo = Defs.TIPO_PROP_RUTA;
					int index = nombre.lastIndexOf(Defs.PUNTO);
					if (!(tag + Defs.PUNTO + elemento + Defs.PUNTO + Defs.TAG_PROP_RUTA).equals(nombre))
						ordenRuta = new Integer(nombre.substring(index+1)).intValue();
				}
				else if ((tag + Defs.PUNTO + elemento + Defs.PUNTO + Defs.TAG_PROP_ORDEN).equals(nombre))
					tipo = Defs.TIPO_PROP_ORDEN;
				else if ((tag + Defs.PUNTO + elemento + Defs.PUNTO + Defs.TAG_PROP_LINEA).equals(nombre))
					tipo = Defs.TIPO_PROP_LINEA;
				
				return new Posicion(ordenRuta, tipo);
			}catch(Exception e){
				throw new Exception(e.getMessage(), e.getCause());
			}
		}
}
