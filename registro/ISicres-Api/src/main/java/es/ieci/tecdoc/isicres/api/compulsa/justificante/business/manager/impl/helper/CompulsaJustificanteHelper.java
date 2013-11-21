package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl.helper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;

import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManagerFactory;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl.CompulsaJustificanteKeys;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo.ISicresCompulsaJustificanteDatosEspecificosVO;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo.ISicresCompulsaJustificanteVO;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.exception.ISicresCompulsaJustificanteException;
import gnu.trove.THashMap;


/**
 * Clase que contiene metodos tipicos para la compulsa de documentos.
 * @author IECISA
 *
 */
public class CompulsaJustificanteHelper {

	private static final Logger logger = Logger
	.getLogger(CompulsaJustificanteHelper.class);

	static CompulsaJustificanteHelper instance;

	public static synchronized CompulsaJustificanteHelper getInstance() {
		if (instance == null)
			instance = new CompulsaJustificanteHelper();
		return instance;
	}

	/**
	 * Obtiene la imagen que servira de fondo para los datos de información de la compulsa.
	 * @param imagePath Path a la imagen que se usara para la marca de agua.
	 * @return Un objeto Image con los datos de la imagen a de fondo para los datos de información de la compulsa.
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Image getBackgroundForData(String imagePath) {
		Image image;

		//Cargar la imagen
		try {
			image = Image.getInstance(imagePath);
		} catch (BadElementException e) {
			String msgError = "Se esta intentado leer una imagen no valida.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		} catch (MalformedURLException e) {
			String msgError = "El path al fichero de la imagen no es correcto.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		} catch (IOException e) {
			String msgError = "Ha ocurrido un error al leer el fichero de imagen.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		return image;
	}

	/**
	 * Devuelve una representación de una pagina con el hueco para la banda de datos de información de la compulsa.
	 * @param page Pagina original
	 * @param band Posición de la banda (1 - Margen izquierdo, Otro - Pie de pagina)
	 * @param bandSize Tamaño de la banda
	 * @return
	 * @throws BadElementException
	 */
	public Image getPageWithRoomForData(PdfImportedPage page, ISicresCompulsaJustificanteDatosEspecificosVO datosEspecificosVO) {
		Image pageImage;

		//Obtener una representacion imagen de la pagina
		try {
			pageImage = Image.getInstance(page);
		} catch (BadElementException e) {
			String msgError = "Se esta intentando crear una imagen de una pagina del PDF no valida.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		//Establecer la posicion de la banda de datos con informacion de la compulsa.
		if (datosEspecificosVO.getBand() == 1) {
			//Se deja hueco en el margen izquierdo para la informacion de la compulsa
			pageImage.setAbsolutePosition(datosEspecificosVO.getBandSize(), 0);
			pageImage.scaleAbsoluteWidth(page.getWidth() - datosEspecificosVO.getBandSize());
			pageImage.scaleAbsoluteHeight(page.getHeight());

		} else {
			//Se deja hueco en el margen inferior para la informacion de la compulsa
			pageImage.setAbsolutePosition(0, 0);
			pageImage.scaleAbsoluteWidth(page.getWidth());
			pageImage.scaleAbsoluteHeight(page.getHeight() - datosEspecificosVO.getBandSize());
		}

		return pageImage;
	}

	/**
	 * Añade la imagen que servira de fondo para los datos de información de la compulsa a la pagina actual del documento.
	 * @param document El documento pdf que se modificara. A este documento se añadira la imagen de fondo.
	 * @param band Posición de la banda (1 - Margen izquierdo, Otro - Pie de pagina)
	 * @param bandSize Tamaño de la banda
	 * @throws DocumentException
	 */
	public void addBackgroundImageForData(Document document, Image image, ISicresCompulsaJustificanteDatosEspecificosVO datosEspecificosVO)  {
		//Determinar donde ira la banda de Datos con información de la compulsa
		if (datosEspecificosVO.getBand() == 1) {
			//La banda ira en el margen izquierdo
			image.setRotationDegrees(90F); //Girar la imagen 90º
			//Colocar la imagen repetidamente para cubrir todo el margen izquierdo
			for (int i = 0; i < (int)document.getPageSize().getHeight(); i = (int)((float)i + image.getWidth())) {
				//Establecer la posicion de la imagen
				image.setAbsolutePosition(0, i);
				image.scaleAbsoluteHeight(datosEspecificosVO.getBandSize());
				//Pintar la imagen en la pagina actual
				try {
					document.add(image);
				} catch (DocumentException e) {
					String msgError = "Error al añadir una imagen al documento PDF.";
					logger.error(msgError, e);
					throw new ISicresCompulsaJustificanteException(msgError);
				}
			}
		} else {
			//La banda ira en el pie de pagina
			for (int i = 0; i < (int)document.getPageSize().getWidth(); i++) {
				//Establecer la posicion de la imagen
				image.setAbsolutePosition(i, ((int)document.getPageSize().getWidth()) - datosEspecificosVO.getBandSize());
				image.scaleAbsoluteHeight(datosEspecificosVO.getBandSize());
				//Pintar la imagen en la pagina actual
				try {
					document.add(image);
				} catch (DocumentException e) {
					String msgError = "Error al añadir una imagen al documento PDF.";
					logger.error(msgError, e);
					throw new ISicresCompulsaJustificanteException(msgError);
				}
			}
		}
	}

	/**
	 * Devuelve un objeto con las propiedades especificas de la compulsa, que sirven para colocar la banda de datos de informacion de la compulsa.
	 * @param locale Localización
	 * @return Un objeto con las propiedades especificas de la compulsa, que sirven para colocar la banda de datos de informacion de la compulsa.
	 */
	public ISicresCompulsaJustificanteDatosEspecificosVO getCompulsaDatosEspecificos(Locale locale) {
		ISicresCompulsaJustificanteDatosEspecificosVO compulsaDatosEspecificosVO;

		compulsaDatosEspecificosVO = new ISicresCompulsaJustificanteDatosEspecificosVO();

		//Margen
		compulsaDatosEspecificosVO.setMargen(new Float(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_MARK_POSITION_X)).floatValue());
		//Tipo de fuente
		compulsaDatosEspecificosVO.setFont(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_MARK_FONT));
		//Tamaño de la fuente
		compulsaDatosEspecificosVO.setFontSize(new Float(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_MARK_SIZE)).floatValue());
		//Codificación de la fuente
		compulsaDatosEspecificosVO.setEncoding(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_MARK_ENCODING));
		//Tipo de banda (1 - Marge izquierdo, vertical. Otro - Pie de pagina.
		compulsaDatosEspecificosVO.setBand(new Integer(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_BAND_VH)).intValue());
		//Tamaño de la banda.
		compulsaDatosEspecificosVO.setBandSize(new Integer(RBUtil.getInstance(locale)
				.getProperty(CompulsaJustificanteKeys.I18N_PDF_WATER_BAND_SIZE)).intValue());

		return compulsaDatosEspecificosVO;
	}

	/**
	 * Devuelve el texto del fichero usado de plantilla para los datos de información de la compulsa con los datos combinados.
	 * @param iSicresCompulsaVO Objeto con las propiedades de la compulsa, que se usara para obtener los datos.
	 * @param dataFilePath Path del fichero de la plantilla
	 * @param entidad Entidad.
	 * @return Un texto con los datos de informacion de la compulsa con los datos combinados.
	 */
	public String getParsedData(ISicresCompulsaJustificanteVO iSicresCompulsaVO) {
		String dataText;
		String tag;
		String value;

		//Leemos el fichero de texto con la plantilla a usar para los datos de información de compulsa
		try {
			dataText = readStream(new FileInputStream(iSicresCompulsaVO.getDatosPath()));
		} catch (FileNotFoundException e) {
			String msgError = "Error al leer el fichero que se usara de plantilla para los Datos de Información de la compulsa.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}
		//Abrimos la sesion de hibernate
		Session session;
		try {
			session = HibernateUtil.currentSession(iSicresCompulsaVO.getEntidad());
		} catch (HibernateException e) {
			String msgError = "Error al obtener la sesión de Hibernate.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		//Componer expresion regular para buscar todos los tags ${*}
		StringBuffer sb = new StringBuffer(dataText.length());
		Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(dataText);

		//Recorrer todos los tags encontrados en el mensaje
		while (matcher.find()) {
			//Obtener el tag sin eliminando "${" y "}". Si obtenemos el indice "0", matcher.group(0), obtendriamos el tag completo.
			tag = matcher.group(1);
			value = parseTag(iSicresCompulsaVO, session, tag);
			matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
		}

		//Añadir el final del texto.
		matcher.appendTail(sb);

		return sb.toString();
	}

	/**
	 * Añade texto a un documento PDF.
	 * @param pdfContentByte Contendio de texto y graficos del PDF al que se añadira el texto.
	 * @param datosEspecificosVO Objeto con las propiedades especificas de la compulsa (Fuente, tamaño de fuente, etc..)
	 * @param dataText El texto que se añadira al PDF.
	 */
	public void addDataTextToDocument(PdfContentByte pdfContentByte, ISicresCompulsaJustificanteDatosEspecificosVO datosEspecificosVO, String dataText) {
		BaseFont baseFont;
		String lineStr;
		float xPoint;

		//Abrir un buffer para la lectura linea por linea de los datos de informacion de la compulsa.
		BufferedReader reader = new BufferedReader(
				  new StringReader(dataText));

		try {
			//Crear la fuente que se usara para escribir los datos
			baseFont = BaseFont.createFont(datosEspecificosVO.getFont(), datosEspecificosVO.getEncoding(), false);
			//Obtener la posicion X de la primera linea.
			xPoint = datosEspecificosVO.getMargen();
			//Inicializar la escritura de Texto en el PDF
			pdfContentByte.setFontAndSize(baseFont, datosEspecificosVO.getFontSize());
			pdfContentByte.beginText();
			//Recorrer todas las lineas de texto
			while ((lineStr = reader.readLine()) != null) {
				//Establecer la matriz para recibir la linea de texto
				pdfContentByte.setTextMatrix(0, 1, -1, 0, xPoint, datosEspecificosVO.getMargen());
				//Escribir la linea de texto
				pdfContentByte.showText(lineStr);
				//Incrementar la posicion X para la siguiente linea.
				xPoint = xPoint + datosEspecificosVO.getFontSize();
			}
			//Finalizar la escritura de texto
			pdfContentByte.endText();
		} catch (DocumentException e) {
			String msgError = "Error al añadir texto al documento PDF.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		} catch (IOException e) {
			String msgError = "Error al añadir texto al documento PDF.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}
	}

	/**
	 * Retorna el texto de datos de informacion de la compulsa con los numeros de paginas totales y numero de pagina actual combinados.
	 * @param dataText Texto de datos de informacion de la compulsa
	 * @param totalPagesNumber Numero de paginas totales
	 * @param currentPageNumber Numero de pagina actual.
	 * @return El texto de datos de informacion de la compulsa con los numeros de paginas totales y numero de pagina actual combinados.
	 */
	public String getParsedPageNumbers(String dataText, int totalPagesNumber, int currentPageNumber) {
		String tag;
		String value;

		//Componer expresion regular para buscar todos los tags ${*}
		StringBuffer sb = new StringBuffer(dataText.length());
		Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(dataText);

		//Recorrer todos los tags encontrados en el mensaje
		while (matcher.find()) {
			tag = matcher.group(1);
			value = "";
			if (tag.equals(CompulsaJustificanteKeys.TAG_TOTAL_PAGES)) {
				value = Integer.toString(totalPagesNumber);
			}
			if (tag.equals(CompulsaJustificanteKeys.TAG_CURRENT_PAGE)) {
				value = Integer.toString(currentPageNumber);
			}
			matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
		}

		//Añadir el final del texto.
		matcher.appendTail(sb);

		return sb.toString();
	}

	/**
	 * Transforma una etiqueta al valor correspondiente de los datos del registro.
	 * @param ctx Contexto del evento con la información del registro.
	 * @param session Sesion Hibernate.
	 * @param tag Etiqueta
	 * @return El valor correspondiente de los datos del registro.
	 * @throws Exception
	 */
	private String parseTag(ISicresCompulsaJustificanteVO iSicresCompulsaVO, Session session, String tag)  {

		AxSf axsf=null;
		//Asunto del registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_ASUNTO)) {
			Integer asuntoId = getInteger(getAxsf(session,axsf).getAttributeValue(AxSf.FLD16_FIELD));
			if (asuntoId.intValue() != 0) return getAsunto(session, asuntoId);
		}

		//Numero de registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_NUM_REGISTRO)) {
			return getAxsf(session,axsf).getAttributeValue(AxSf.FLD1_FIELD).toString();
		}

		//Oficina de registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_OFICINA_REGISTRO)) {
			Integer oficinaId = getInteger(getAxsf(session,axsf).getAttributeValue(AxSf.FLD5_FIELD));
			if (oficinaId.intValue() != 0) return getNameOficina(session, oficinaId);
		}

		//Codigo oficina de registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_COD_OFICINA_REGISTRO)) {
			Integer oficinaId = getInteger(getAxsf(session,axsf).getAttributeValue(AxSf.FLD5_FIELD));
			if (oficinaId.intValue() != 0) return getCodeOficina(session, oficinaId);
		}

		//Usuario que ha realizado el registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_USUARIO)) {
			return getAxsf(session,axsf).getAttributeValue(AxSf.FLD3_FIELD).toString();
		}

		//Origen del registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_ORIGEN)) {
			Integer organismoId = getInteger(getAxsf(session,axsf).getAttributeValue(AxSf.FLD7_FIELD));
			if (organismoId.intValue() != 0) return getOrganismo(session, organismoId);
		}

		//Destino del registro
		if (tag.equals(CompulsaJustificanteKeys.TAG_DESTINO)) {
			Integer organismoId = getInteger(getAxsf(session,axsf).getAttributeValue(AxSf.FLD8_FIELD));
			if (organismoId.intValue() != 0) return getOrganismo(session, organismoId);
		}

		//Remitente del registro. Solo se obtiene el primer remitente.
		if (tag.equals(CompulsaJustificanteKeys.TAG_REMITENTE)) {

			//Object remitente = iSicresCompulsaVO.getAxsf().getAttributeValue(AxSf.FLD9_FIELD);
			//if (remitente != null && remitente.toString().length() != 0)
			//	return remitente.toString();
			iSicresCompulsaVO.getRegistro().getInteresadoPrincipal().getNombre();
		}

		//Certificado con el que se ha firmado
		if (tag.equals(CompulsaJustificanteKeys.TAG_CERTIFICADO)) {
			return iSicresCompulsaVO.getCNcertificado();
		}

		//Certificado con el que se ha firmado
		if (tag.equals(CompulsaJustificanteKeys.TAG_CERTIFICADO_SOLO_NOMBRE)) {
			//a partir del cnCertificado obtenemos unicamente el nombre del firmante
			return getNombreFirmante(iSicresCompulsaVO.getCNcertificado());
		}

		//Fecha de Compulsa
		if (tag.equals(CompulsaJustificanteKeys.TAG_FECHA_COMPULSA)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					CompulsaJustificanteKeys.DATE_FORMAT_COMPULSA);
			return sdf.format(iSicresCompulsaVO.getFechaCompulsa());
		}

		//Numero de paginas totales. Este tag se devuelve como el propio tag, ya que se combinara en la funcion "getParsedPageNumbers"
		if (tag.equals(CompulsaJustificanteKeys.TAG_TOTAL_PAGES)) {
			return "${" + tag + "}";
		}

		//Numero de paginas actual. Este tag se devuelve como el propio tag, ya que se combinara en la funcion "getParsedPageNumbers"
		if (tag.equals(CompulsaJustificanteKeys.TAG_CURRENT_PAGE)) {
			return "${" + tag + "}";
		}

		//Localizador
		if (tag.equals(CompulsaJustificanteKeys.TAG_LOCATOR)) {
			return iSicresCompulsaVO.getLocator();
		}

		return "";
	}


	/**
	 * Devuelve el literal de un asunto de inveSicres.
	 * @param session Sesion Hibernate.
	 * @param asuntoId Identificador del asunto.
	 * @return El literal del asunto de inveSicres.
	 * @throws Exception
	 */
	private String getAsunto(Session session, Integer asuntoId) {

		StringBuffer query = new StringBuffer();
		query.append("FROM  ");
		query.append(HibernateKeys.HIBERNATE_ScrCa);
		query.append(" scr WHERE scr.id=?");
		List asuntos;

		try {
			asuntos = session.find(query.toString(), new Object[] { asuntoId }, new Type[] { Hibernate.INTEGER });
			if (asuntos != null && !asuntos.isEmpty()) {
				ScrCa asunto = (ScrCa)asuntos.get(0);
				return asunto.getMatter();
			}
		} catch (HibernateException e) {
			String msgError = "Error al obtener el literal del asunto con Id:" + asuntoId.toString();
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		return "";
	}

	protected AxSf getAxsf(Session session,AxSf axsf) {
		AxSf result=null;
		if (axsf==null){
			try{
				ContextoAplicacionVO contextoAplicacion = ContextoAplicacionManagerFactory.getInstance().getContextoAplicacionVO();
				BaseRegistroVO registroActual = contextoAplicacion.getRegistroActual();

				String entidad=contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getIdEntidad();

				boolean load=false;

				Locale locale=contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getLocale();
				Integer idLibro= Integer.parseInt(registroActual.getIdLibro());
				Integer idRegistro=Integer.parseInt(registroActual.getIdRegistro());

				String sessionID=contextoAplicacion.getUsuarioActual().getConfiguracionUsuario().getSessionID();
				// Recuperamos la sesión
				CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(sessionID);
				THashMap bookInformation = (THashMap) cacheBag.get(idLibro);
				Idocarchdet idoc = (Idocarchdet) bookInformation.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

				result=FolderFileSession.getAxSf(session, idLibro, idRegistro, idoc, locale.getLanguage(), entidad, load);
			}catch (Exception ex){

				String msgError="Error en el proceso de compulsa, no se ha podido recuperar Axsf.";
				logger.error(msgError, ex);
				throw new ISicresCompulsaJustificanteException(msgError,ex);
			}
		}else{
			result=axsf;
		}
		return result;

	}


	/**
	 * Devuelve el nombre de la Oficina de Registro
	 * @param session Sesion de Hibernate
	 * @param oficinaId Identificador de la oficina
	 * @return El nombre de la Oficina de Registro
	 * @throws Exception
	 */
	private String getNameOficina(Session session, Integer oficinaId) {
		ScrOfic oficina;
		try {
			oficina = ISicresQueries.getScrOficById(session, oficinaId);
			if (oficina != null && oficina.getName() != null && oficina.getName().length() != 0)
				return oficina.getName();
		} catch (HibernateException e) {
			String msgError = "Error al obtener el literal de la oficina con Id:" + oficinaId.toString();
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		return "";
	}

	/**
	 * Devuelve el nombre de la Oficina de Registro
	 * @param session Sesion de Hibernate
	 * @param oficinaId Identificador de la oficina
	 * @return El nombre de la Oficina de Registro
	 * @throws Exception
	 */
	private String getCodeOficina(Session session, Integer oficinaId) {
		ScrOfic oficina;
		try {
			oficina = ISicresQueries.getScrOficById(session, oficinaId);
			if (oficina != null && oficina.getName() != null && oficina.getName().length() != 0)
				return oficina.getCode();
		} catch (HibernateException e) {
			String msgError = "Error al obtener el código de la oficina con Id:" + oficinaId.toString();
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}

		return "";
	}

	/**
	 * Devuelve el nombre de un Organismo
	 * @param session Sesion de Hibernate
	 * @param organismoId  Identificador del organismo.
	 * @return El nombre del Organismo.
	 * @throws Exception
	 */
	private String getOrganismo(Session session, Integer organismoId) {

		StringBuffer query = new StringBuffer();
		query.append("FROM  ");
		query.append(HibernateKeys.HIBERNATE_ScrOrg);
		query.append(" scr WHERE scr.id=?");
		List organismos;

		try {
			organismos = session.find(query.toString(), new Object[] { organismoId }, new Type[] { Hibernate.INTEGER });
			if (organismos != null && !organismos.isEmpty()) {
				ScrOrg organismo = (ScrOrg)organismos.get(0);
				return organismo.getName();
			}
		} catch (HibernateException e) {
			String msgError = "Error al obtener el literal del organismo con Id:" + organismoId.toString();
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}


		return "";
	}

	private String getNombreFirmante(String certificado){
		String result = certificado;

		if (certificado.lastIndexOf(" - ") != -1) {
			result = certificado.substring(0, certificado.lastIndexOf(" - "));
		}

		return result;
	}

	private Integer getInteger(Object value) {
		try {
			int aux_value = Integer.parseInt(value.toString());
			return new Integer(aux_value);
		} catch (Exception e ) {
			return new Integer(0);
		}
	}

	private String readStream(InputStream is) {
	    StringBuilder sb = new StringBuilder(512);
	    try {
	        Reader r = new InputStreamReader(is, "UTF-8");
	        int c = 0;
	        while (c != -1) {
	            c = r.read();
	            sb.append((char) c);
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return sb.toString();
	}

}
