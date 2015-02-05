package ieci.tecdoc.sgm.certificacion.pdf;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import xtom.parser.Element;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatoGenerico;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCabecera;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCentrales;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosCertificacion;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosPropiedades;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;


/**
 * Clase que genera, a partir de un XML y una configuración, una certificación en formato PDF
 * @author José Antonio Nogales
 */
public class CertificacionPDF  extends PdfPageEventHelper {
	private static final Logger logger = Logger.getLogger(CertificacionPDF.class);

	private static PdfWriter writer = null;
	private static Document document = null;
	private static PdfPTable head = null;
	private static PdfPTable identCert = null;
	private static PdfPTable body = null;
	private static PdfPTable certificacion = null;
	private static float margen_superior = 0;
	private static float margen_inferior = 0;
	private static Rectangle page = null;
	private static String guid = null;
	private static ByteArrayOutputStream baos = new ByteArrayOutputStream();
	protected static String rutaDatosMultiples;
	protected static String objetoDatosMultiples;
	protected static String rutaRecursos;
	protected static String idioma;

	/**
	 * Método que genera la certificación en formato PDF
	 * @param datosPropiedades Datos relativos a la configuración de los datos en el PDF
	 * @param xml XML con los datos a incluir en el PDF
	 * @return Array de bytes que contiene la certificación PDF
	 * @throws CertificacionException En caso de producirse algún error
	 */
	protected static RetornoPdf GenerarCertificacion(DatosPropiedades datosPropiedades, String xml) throws CertificacionException {
		try{
			certificacion = crearCertificacion(datosPropiedades.getCertificacion(), xml);
			certificacion.setTotalWidth(longitudCabecera);
			head = crearCabecera(datosPropiedades.getCabecera(), xml, datosPropiedades.getImagenes().getImagenCabecera());
			head.setTotalWidth(longitudInicial);
			identCert = crearIdentificadorCertificacion();
			identCert.setTotalWidth(longitudPie);
			margen_superior = margenVertical + head.getTotalHeight();
			margen_inferior = margenVertical + identCert.getTotalHeight();
			newCertificacion(margen_superior, margen_inferior);
			head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
			certificacion.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
			body = crearCentro(datosPropiedades.getCentro(), xml);
			body.setWidthPercentage(100);
			document.add(body);
			destroy(datosPropiedades.getImagenes().getMarcaAgua());

			RetornoPdf retorno = new RetornoPdf();
			retorno.setContenido(baos.toByteArray());
			retorno.setIdentificador(guid);
			return retorno;
		} catch (CertificacionException ce) {
			throw ce;
		} catch (Exception e) {
			logger.error("Se ha producido un error al generar la certificación en formato PDF", e);
			throw new CertificacionException(
				CodigosErrorCertificacionException.ERROR_GENERACION_CERTIFICACION,
				e.getMessage(),
				e.getCause());
		}
	}

	/**
	 * Método que inicializa el PDF de la certificación
	 * @param top Margen superior del PDF
	 * @param bottom Margen inferior del PDF
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static void newCertificacion(float top, float bottom) throws CertificacionException {
		try {
			document = new Document(PageSize.A4, margenHorizontal, margenHorizontal, top, bottom);
			writer = PdfWriter.getInstance(document, baos);
			writer.setPageEvent(new CertificacionPDF());
			document.addAuthor("");
   			document.addSubject("");
   			page = document.getPageSize();
			document.open();
		} catch (Exception e) {
			logger.error("Se ha producido un error al generar la certificación en formato PDF", e);
			throw new CertificacionException(
				CodigosErrorCertificacionException.ERROR_GENERACION_CERTIFICACION,
				e.getMessage(),
				e.getCause());
		}
	}

	/**
	 * Método que finaliza la creaciónde la certificación.
	 * @param marcaAgua Imagen que se va a utilizar como marca de agua del documento
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static void destroy(String marcaAgua) throws CertificacionException {
		document.close();
		establecerMarcaAgua(marcaAgua);
	}

	/**
	 * Método que establece en el PDF creado una imgen como marca de agua en todas sus páginas
	 * @param marcaAgua Imagen que se va a utilizar como marca de agua del documento
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static void establecerMarcaAgua(String marcaAgua) throws CertificacionException {
        try{
        	byte[] bytes = null;
        	if (marcaAgua != null && !marcaAgua.equals("") && !marcaAgua.endsWith(Defs.BARRA) && !marcaAgua.endsWith(Defs.NULO)){
        		try {
        			bytes = Utilidades.leerFicheroRecursosArray(rutaRecursos + marcaAgua);
        		}catch(Exception ex){
        			logger.error("La marca de agua'" + marcaAgua + "' no se ha encontrado");
        			throw ex;
        		}
        	}
        	if (bytes != null){
	        	ImageIcon icon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(bytes)));
	        	PdfReader reader = new PdfReader(baos.toByteArray());
	        	int n = reader.getNumberOfPages();
				PdfStamper stamp = new PdfStamper(reader, baos);
		        PdfContentByte under;
		        Image img = Image.getInstance(bytes);

		        float percent_width = 0f, percent_height = 0f, percent = 0f;
		        if (page.getWidth() < icon.getIconWidth())
		        	percent_width = page.getWidth() / icon.getIconWidth();
		        if (page.getHeight() < icon.getIconHeight())
		        	percent_height = page.getHeight() / icon.getIconHeight();

		        percent = (percent_width > percent_height) ? percent_width : percent_height;
		        if (percent < 1 && percent > 0){
		        	img.scalePercent(percent * 100);
		        	img.setAbsolutePosition((page.getWidth() - (icon.getIconWidth() * percent)) / 2, (page.getHeight() - (icon.getIconHeight() * percent)) / 2);
		        } else img.setAbsolutePosition((page.getWidth() - icon.getIconWidth()) / 2, (page.getHeight() - icon.getIconHeight()) / 2);
		       	for(int pi=1; pi<=n; pi++){
		       		under = stamp.getUnderContent(pi);
		       		under.addImage(img);
	        	}
		       	stamp.close();
        	}
        }catch(Exception e){
        	logger.error("Se ha producido un error al insertar la marca de agua de la certificación", e);
        	throw new CertificacionException(
    			CodigosErrorCertificacionException.ERROR_INSERTAR_MARCA_AGUA,
    			e.getMessage(),
    			e.getCause());
        }
	}

	/**
	 * Método que crea una tabla con los datos de la cabecera a partir del formato configurado
	 * @param datosCabecera Datos relativos a la configuración de la tabla de cabecera
	 * @param xml XML con los datos a incluir en la cabecera
	 * @param logoCabecera Imagen que se va a usar como cabecera del PDF
	 * @return Tabla con la cabecera configurada
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static PdfPTable crearCabecera(DatosCabecera datosCabecera, String xml, String logoCabecera) throws CertificacionException{
		try {
			PdfPTable tabla = new PdfPTable(1);
			PdfPCell imagenCabecera = new PdfPCell(establecerImagenCabecera(logoCabecera));
			imagenCabecera.setBorder(PdfPCell.NO_BORDER);
			tabla.addCell(imagenCabecera);
			tabla.addCell(obtenerCeldaVacia());
			if (!Utilidades.estaVacia(datosCabecera.getTitulo()))
				tabla.addCell(obtenerCeldaTitulo(datosCabecera.getTitulo()));

			int maxLineas = Utilidades.maxLinea(datosCabecera.getDatoCabecera());
			for (int i=1; i<= maxLineas; i++){
				ArrayList campos = Utilidades.obtenerCamposLinea(datosCabecera.getDatoCabecera(), i);
				if (campos != null && campos.size()>0) {
					PdfPTable tablaCampos = new PdfPTable(campos.size());
					ArrayList arrayCampos = new ArrayList();
					for (int j=0; j<campos.size(); j++){
						DatoGenerico dato = (DatoGenerico)campos.get(j);
						PdfPCell cellCampo = new PdfPCell();
						Phrase p = new Phrase();
						ArrayList rutas = dato.getDatos();
						String valorFinal = "";
						for (int k=0; k<rutas.size(); k++){
							valorFinal += (k!=0?" ":"") + Utilidades.obtenerValorXML(xml, (String)rutas.get(k));
						}
						Chunk c1 = new Chunk("\t" + dato.getNombre() + ": ");
						c1.setFont(fuenteNegrita12);
						Chunk c2 = new Chunk(valorFinal);
						c2.setFont(fuenteNormal12);
						p.add(c1);
						p.add(c2);
						cellCampo.addElement(p);
						cellCampo.setBorderWidth(1);
						cellCampo.setBorderColor(bordeGris);
						cellCampo.setUseAscender(true);
						cellCampo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
						cellCampo.setFixedHeight(20);
						tablaCampos.addCell(cellCampo);
						arrayCampos.add("\t" + dato.getNombre() + ": " + valorFinal);
					}
					tablaCampos.setTotalWidth(Utilidades.calcularLongitudesColumna((String[])arrayCampos.toArray(new String[arrayCampos.size()])));
					PdfPCell cellTabla = new PdfPCell(tablaCampos);
					cellTabla.setBorder(PdfPCell.NO_BORDER);
					tabla.addCell(cellTabla);
				}
			}
			return tabla;
		} catch(Exception e){
			logger.error("Se ha producido un error al crear los datos de cabecera", e);
			throw new CertificacionException(
	    		CodigosErrorCertificacionException.ERROR_CREAR_DATOS_CABECERA,
	    		e.getMessage(),
	    		e.getCause());
		}
	}

	/**
	 * Método que crea una tabla con el identificador del documento
	 * @return Tabla con el valor del identificador de la certificación
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static PdfPTable crearIdentificadorCertificacion() throws CertificacionException{
		try {
			PdfPTable tabla = new PdfPTable(new float[]{200});
			PdfPCell celda = obtenerCeldaVacia();
			celda.setBorder(PdfPCell.NO_BORDER);
			guid = new Guid().toString();
			Chunk c = new Chunk(guid);
			c.setFont(fuenteNegrita8);
			celda.addElement(c);
			tabla.addCell(celda);
			return tabla;
		} catch(Exception e){
			logger.error("Se ha producido un error al crear la tabla con el guid del documento", e);
			throw new CertificacionException(
	    		CodigosErrorCertificacionException.ERROR_CREAR_DATOS_CABECERA,
	    		e.getMessage(),
	    		e.getCause());
		}
	}

	/**
	 * Método que crea una tabla con los datos de la certificación a partir del formato configurado
	 * @param datosCertificacion Datos relativos a la configuración de la tabla de certificación
	 * @param xml XML con los datos a incluir en la certificación
	 * @return Tabla con la certificación configurada
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static PdfPTable crearCertificacion(DatosCertificacion datosCertificacion, String xml) throws CertificacionException {
		try {
			PdfPTable tabla = new PdfPTable(new float[] {44, 56});
			if (!Utilidades.estaVacia(datosCertificacion.getTitulo())){
				tabla.addCell(obtenerCeldaVacia());
				tabla.addCell(obtenerCeldaTitulo(datosCertificacion.getTitulo()));
			}

			int maxLineas = Utilidades.maxLinea(datosCertificacion.getDatoCertificacion());
			for (int i=1; i<= maxLineas; i++){
				ArrayList campos = Utilidades.obtenerCamposLinea(datosCertificacion.getDatoCertificacion(), i);
				if (campos != null && campos.size()>0) {
					PdfPTable tablaCampos = new PdfPTable(campos.size());
					ArrayList arrayCampos = new ArrayList();
					for (int j=0; j<campos.size(); j++){
						DatoGenerico dato = (DatoGenerico)campos.get(j);
						PdfPCell cellCampo = new PdfPCell();
						Phrase p = new Phrase();
						ArrayList rutas = dato.getDatos();
						String valorFinal = "";
						for (int k=0; k<rutas.size(); k++){
							valorFinal += (k!=0?" ":"") + Utilidades.obtenerValorXML(xml, (String)rutas.get(k));
						}
						Chunk c1 = new Chunk("\t" + dato.getNombre() + ": ");
						c1.setFont(fuenteNegrita12);
						Chunk c2 = new Chunk(valorFinal);
						c2.setFont(fuenteNormal12);
						p.add(c1);
						p.add(c2);
						cellCampo.addElement(p);
						cellCampo.setBorderWidth(1);
						cellCampo.setBorderColor(bordeGris);
						cellCampo.setUseAscender(true);
						cellCampo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
						cellCampo.setFixedHeight(20);
						cellCampo.setBackgroundColor(Color.WHITE);
						tablaCampos.addCell(cellCampo);
						arrayCampos.add("\t" + dato.getNombre() + ": " + valorFinal);
					}
					tablaCampos.setTotalWidth(Utilidades.calcularLongitudesColumna((String[])arrayCampos.toArray(new String[arrayCampos.size()])));
					PdfPCell cellTabla = new PdfPCell(tablaCampos);
					//cellTabla.setBorder(PdfPCell.NO_BORDER);
					tabla.addCell(obtenerCeldaVacia());
					tabla.addCell(cellTabla);
				}
			}
			return tabla;
		}catch(Exception e){
			logger.error("Se ha producido un error al crear los datos de certificación", e);
			throw new CertificacionException(
		    	CodigosErrorCertificacionException.ERROR_CREAR_DATOS_CERTIFICACION,
		    	e.getMessage(),
		    	e.getCause());
		}
	}

	/**
	 * Método que crea una tabla con los datos centrales a partir del formato configurado
	 * @param datosCentrales Datos relativos a la configuración de la tabla central
	 * @param xml XML con los datos a incluir en el centro
	 * @return Tabla con los datos centrales configurada
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static PdfPTable crearCentro(DatosCentrales datosCentrales, String xml) throws CertificacionException{
		try {
			PdfPTable tabla = new PdfPTable(1);
			tabla.addCell(obtenerCeldaVacia());
			if (!Utilidades.estaVacia(datosCentrales.getTitulo()))
				tabla.addCell(obtenerCeldaTitulo(datosCentrales.getTitulo()));
			tabla.addCell(obtenerCeldaFinaVacia());
			tabla.setHeaderRows(3);

			int maxLineas = Utilidades.maxLinea(datosCentrales.getDatosCentrales());
			Element[] multiples = Utilidades.obtenerValoresMultiplesXML(xml, rutaDatosMultiples + Defs.SEPARADOR + objetoDatosMultiples);

			for(int h=0; h<multiples.length; h++){
				Element objeto = multiples[h];
				PdfPTable completa = new PdfPTable(1);
				for (int i=1; i<= maxLineas; i++){
					ArrayList campos = Utilidades.obtenerCamposLinea(datosCentrales.getDatosCentrales(), i);
					if (campos != null && campos.size()>0) {
						PdfPTable tablaCampos = new PdfPTable(campos.size());
						ArrayList arrayCampos = new ArrayList();
						for (int j=0; j<campos.size(); j++){
							DatoGenerico dato = (DatoGenerico)campos.get(j);
							PdfPCell cellCampo = new PdfPCell();
							Phrase p = new Phrase();
							ArrayList rutas = dato.getDatos();
							String valorFinal = "";
							String xmlObjeto = Utilidades.obtenerXMLValoresMultiples(objeto.getName(), objeto.getChildren());
							for (int k=0; k<rutas.size(); k++){
								String rutaPartida = ((String)rutas.get(k)).substring(rutaDatosMultiples.length() + Defs.SEPARADOR.length() + objetoDatosMultiples.length() + 1);
								valorFinal += (k!=0?" ":"") + Utilidades.obtenerValorXML(xmlObjeto, rutaPartida);
							}
							Chunk c1 = new Chunk("\t" + dato.getNombre() + ": ");
							c1.setFont(fuenteNegrita12);
							Chunk c2 = new Chunk(valorFinal);
							c2.setFont(fuenteNormal12);
							p.add(c1);
							p.add(c2);
							cellCampo.addElement(p);
							cellCampo.setBorderWidth(1);
							cellCampo.setBorderColor(bordeGris);
							cellCampo.setUseAscender(true);
							cellCampo.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
							cellCampo.setFixedHeight(20);
							cellCampo.setNoWrap(false);
							tablaCampos.addCell(cellCampo);
							arrayCampos.add("\t" + dato.getNombre() + ": " + valorFinal);
						}
						tablaCampos.setTotalWidth(Utilidades.calcularLongitudesColumna((String[])arrayCampos.toArray(new String[arrayCampos.size()])));
						PdfPCell cellTabla = new PdfPCell(tablaCampos);
						cellTabla.setBorder(PdfPCell.NO_BORDER);
						//tabla.addCell(cellTabla);
						completa.addCell(cellTabla);
					}
				}
				completa.addCell(obtenerCeldaFinaVacia());
				PdfPCell cellTablaCompleta = new PdfPCell(completa);
				cellTablaCompleta.setBorder(PdfPCell.NO_BORDER);
				tabla.addCell(cellTablaCompleta);
			}
			return tabla;
		}catch(Exception e){
			logger.error("Se ha producido un error al crear los datos centrales", e);
			throw new CertificacionException(
		    	CodigosErrorCertificacionException.ERROR_CREAR_DATOS_CENTRALES,
		    	e.getMessage(),
		    	e.getCause());
		}
	}

	/**
	 * Método que situa la tabla de la certificación en la parte superior derecha del PDF
	 */
	private static void ponerCertificacion(){
		float x = 80;
		float y = page.getHeight() - margenVertical;
		certificacion.writeSelectedRows(0, -1, x, y, writer.getDirectContent());
	}

	/**
	 * Método que situa la tabla con el identificador del documento
	 */
	private static void ponerIdentificador(){
		float x = page.getHeight() - 420;
		float y = 40;
		identCert.writeSelectedRows(0, -1, x, y, writer.getDirectContent());
	}

	/**
	 * Método que escribe automáticamente en cada nueva página (cuando acaba esta) la cabecera y la certificación
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - margenVertical, writer.getDirectContent());
		ponerIdentificador();
		ponerCertificacion();
	}

	/**
	 * Método que escribe automaticamente caundo se acaba el documento (no se usa)
	 * @param writer Objeto de escritura del PDF
	 * @param document Documento
	 */
	public void onEndDocument(PdfWriter writer, Document document) {
	}

	/**
	 * Método que establece la imagen de la cabecera, normalmente el logo de la entidad
	 * @param imagen Imagen que se va a usa como imagen de cabecera
	 * @return Tabla con la imagen de cabecera
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private static PdfPTable establecerImagenCabecera(String imagen) throws CertificacionException {
		try{
			byte[] bytes = null;
			if (imagen != null && !imagen.equals("") && !imagen.endsWith(Defs.BARRA) && !imagen.endsWith(Defs.NULO)){
				try {
					bytes = Utilidades.leerFicheroRecursosArray(rutaRecursos + imagen);
				}catch(Exception ex){
					logger.error("La imagen de cabecera '" + imagen + "' no se ha encontrado");
					throw ex;
				}
			}
			PdfPTable tabla = new PdfPTable(1);
			if(bytes != null){
				ImageIcon icon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(bytes)));
				Image image = Image.getInstance(bytes);
				PdfPCell cell = new PdfPCell(image);
				cell.setBorder(PdfPCell.NO_BORDER);
				if (icon.getIconHeight() < certificacion.getTotalHeight())
					cell.setFixedHeight(certificacion.getTotalHeight());
				tabla.addCell(cell);
			}else{
				PdfPCell cell = obtenerCeldaVacia();
				cell.setFixedHeight(certificacion.getTotalHeight());
				tabla.addCell(cell);
			}
			return tabla;
		}catch(Exception e){
			logger.error("Se ha producido un error al insertar la imagen de cabecera de la certificación", e);
			throw new CertificacionException(
			    CodigosErrorCertificacionException.ERROR_INSERTAR_LOGO_ENTIDAD,
			    e.getMessage(),
			    e.getCause());
		}
	}

	/**
	 * Método que genera una celda vacia sin borde
	 * @return Celda generada
	 */
	private static PdfPCell obtenerCeldaVacia(){
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		Phrase p = new Phrase(" ", fuenteNegrita12);
		cell.addElement(p);
		return cell;
	}

	/**
	 * Método que obtiene una celda vacia, sin borde y de poco grosor
	 * @return Celda generada
	 */
	private static PdfPCell obtenerCeldaFinaVacia(){
		PdfPCell cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);
		Phrase p = new Phrase(" ", fuenteNegrita12);
		cell.addElement(p);
		cell.setFixedHeight(5);
		return cell;
	}

	/**
	 * Método que genera una celda con título en negrita y fondo gris
	 * @param titulo Título a incluir en la celda
	 * @return Celda generada
	 */
	private static PdfPCell obtenerCeldaTitulo(String titulo){
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(1);
		cell.setBorderColor(bordeGris);
		cell.setFixedHeight(22);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setBackgroundColor(fondoGrisClaro);
		Phrase p = new Phrase("\t" + titulo.toUpperCase(), fuenteNegrita12);
		cell.addElement(p);
		return cell;
	}

	/*
	 * Definición de constantes
	 */
	private static final Color bordeGris = new Color(225, 225, 225);
	private static final Color fondoGrisClaro = new Color(240, 240, 240);
	private static final Font fuenteNegrita12 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	private static final Font fuenteNegrita8 = new Font(Font.TIMES_ROMAN, 8, Font.BOLD);
	private static final Font fuenteNormal12 = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL);
	private static final float  margenHorizontal = 50;
	private static final float margenVertical = 70;
	private static final float longitudInicial = 495;
	private static final float longitudCabecera = 200;
	private static final float longitudPie = 200;
}
