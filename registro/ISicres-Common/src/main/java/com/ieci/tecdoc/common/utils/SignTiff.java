package com.ieci.tecdoc.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.media.jai.JAI;
import javax.media.jai.RenderedImageAdapter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.sun.media.jai.codec.FileCacheSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;

/**
 * @author 79426599
 * 
 */
public class SignTiff {

	/**
	 * 
	 */
	private static final int DEFAULT_FONT_SIZE = 20;

	/**
	 * 
	 */
	private static final String CONF_NUMERO_PAG_ATTR_NAME = "3002";

	/**
	 * 
	 */
	private static final String CONF_TIPO_REGISTRO_ATTR_NAME = "3000";

	/**
	 * 
	 */
	private static final String CONF_OFICINA_ATTR_NAME = "5";

	/**
	 * 
	 */
	private static final String CONF_USER_ATTR_NAME = "3";

	/**
	 * 
	 */
	private static final String CONF_FECHA_REGISTRO_ATTR_NAME = "2";

	/**
	 * 
	 */
	private static final String CONF_NUMERO_REGISTRO_ATTRIBUTE_NAME = "1";

	/**
	 * 
	 */
	public static final String PRINT_ONLY_FIRST_PAGE_VALUE = "1";

	/**
	 * 
	 */
	public static final String PRINT_ALLPAGES_VALUE = "0";

	/**
	 * 
	 */
	private static final String STREAM_METHOD = "stream";
	private static final Logger log = Logger.getLogger(SignTiff.class);
	// variables de objeto
	private SeekableStream is;

	private int code;

	private float resX;

	private float resY;

	// constantes
	private final static int TIFF_RESOLUTION_INCHES = 2;

	/**
	 * Constructor
	 */
	public SignTiff() {
	}

	/**
	 * ver compresion
	 */
	public void verCompresion() {
		String[] formatos = ImageCodec.getDecoderNames(is);
		System.out
				.println("Numero de compresores posibles: " + formatos.length);
		for (int i = 0; i < formatos.length; i++) {
			System.out.println("    Formato " + i + " : " + formatos[i]);
		}
		System.out.println("\n");
	}

	/**
	 * ver datos de la imagen
	 */
	public void datosTIFF(byte[] buffer) throws Exception {
		try {
			FileCacheSeekableStream in = new FileCacheSeekableStream(
					new ByteArrayInputStream(buffer));
			TIFFDirectory td = new TIFFDirectory(in, 0);

			// ver resolucion de imagen
			resX = td.getFieldAsFloat(TIFFImageDecoder.TIFF_X_RESOLUTION);
			resY = td.getFieldAsFloat(TIFFImageDecoder.TIFF_Y_RESOLUTION);

			// ver tipo de compresion
			code = (int) td.getFieldAsLong(TIFFImageDecoder.TIFF_COMPRESSION);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * imprimir tipo de compresion
	 */
	public void imprimirCompresion() {
		System.out.print("  Compresion: ");
		switch (code) {
		case 1:
			System.out.println("None");
			break;
		case 2:
			System.out.println("Grupo 3-1D");
			break;
		case 3:
			System.out.println("Grupo 3-2D");
			break;
		case 4:
			System.out.println("Grupo 4");
			break;
		case 5:
			System.out.println("LZW");
			break;
		case 7:
			System.out.println("JPG-TTN2");
			break;
		case 32946:
			System.out.println("Deflate");
			break;
		default:
			System.out.println("No registrada");
			break;
		}
	}

	/**
	 * cambiar resolucion (ppp) del archivo TIFF
	 */
	public TIFFField[] nuevaResolucion() throws Exception {
		TIFFField[] tiffFields = new TIFFField[3];
		long resolutionX = new Float(resX).longValue();
		long resolutionY = new Float(resY).longValue();
		tiffFields[0] = new TIFFField(TIFFImageDecoder.TIFF_X_RESOLUTION,
				TIFFField.TIFF_RATIONAL, 1, new long[][] { { resolutionX, 1 } });
		tiffFields[1] = new TIFFField(TIFFImageDecoder.TIFF_Y_RESOLUTION,
				TIFFField.TIFF_RATIONAL, 1, new long[][] { { resolutionY, 1 } });
		tiffFields[2] = new TIFFField(TIFFImageDecoder.TIFF_RESOLUTION_UNIT,
				TIFFField.TIFF_SHORT, 1, new char[] { TIFF_RESOLUTION_INCHES });
		return tiffFields;
	}

	private FileCacheSeekableStream printTiffTextToPage(RenderedImage rImage,
			int i, BookConf bookConf, BookTypeConf bookTypeConf)
			throws Exception {
		Object[] fldId = null;
		String fields = null;
		ByteArrayOutputStream outputStream1 = null;
		RenderedImageAdapter ria = new RenderedImageAdapter(rImage);

		BufferedImage bi = ria.getAsBufferedImage();

		Graphics2D big = bi.createGraphics();

		String fontName = getConfiguredFontName(bookConf, bookTypeConf);
		Integer fontSize = getConfiguredFontSize(bookConf, bookTypeConf);
		
		big.setFont(new Font(fontName, Font.PLAIN, fontSize.intValue()));
		big.setColor(Color.black);
		
		//1.- Imprimir el número de registro
		fldId = (Object[]) bookTypeConf.getFieldsInfo().get(
				CONF_NUMERO_REGISTRO_ATTRIBUTE_NAME);
		if (bookConf == null){
			bookConf = new BookConf();
		}
		if (bookConf.getFieldsInfo().get(CONF_NUMERO_REGISTRO_ATTRIBUTE_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo().get(
					CONF_NUMERO_REGISTRO_ATTRIBUTE_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getRegisterNumber();
		} else {
			fields = bookTypeConf.getRegisterNumber();
		}
		
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());
		
		//2.- Imprimir la fecha del registro
		fldId = (Object[]) bookTypeConf.getFieldsInfo().get(
				CONF_FECHA_REGISTRO_ATTR_NAME);
		if (bookConf.getFieldsInfo().get(CONF_FECHA_REGISTRO_ATTR_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo().get(
					CONF_FECHA_REGISTRO_ATTR_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getRegisterDate();
		} else {
			fields = bookTypeConf.getRegisterDate();
		}
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());
		
		//3.- Imprimir el nombre del usuario
		fldId = (Object[]) bookTypeConf.getFieldsInfo()
				.get(CONF_USER_ATTR_NAME);
		if (bookConf.getFieldsInfo().get(CONF_USER_ATTR_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo()
					.get(CONF_USER_ATTR_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getUser();
		} else {
			fields = bookTypeConf.getUser();
		}
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());
		
		//4.- Imprimir el código de la oficina
		fldId = (Object[]) bookTypeConf.getFieldsInfo().get(
				CONF_OFICINA_ATTR_NAME);
		if (bookConf.getFieldsInfo().get(CONF_OFICINA_ATTR_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo().get(
					CONF_OFICINA_ATTR_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getCodeOfic();
		} else {
			fields = bookTypeConf.getCodeOfic();
		}
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());
		
		//5.- Imprmir el tipo de registro
		fldId = (Object[]) bookTypeConf.getFieldsInfo().get(
				CONF_TIPO_REGISTRO_ATTR_NAME);
		if (bookConf.getFieldsInfo().get(CONF_TIPO_REGISTRO_ATTR_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo().get(
					CONF_TIPO_REGISTRO_ATTR_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getStringBookType();
		} else {
			fields = bookTypeConf.getStringBookType();
		}
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());
		
		//6.- Imprimir el número de página
		fldId = (Object[]) bookTypeConf.getFieldsInfo().get(
				CONF_NUMERO_PAG_ATTR_NAME);
		if (bookConf.getFieldsInfo().get(CONF_NUMERO_PAG_ATTR_NAME)!=null){
			fldId = (Object[]) bookConf.getFieldsInfo().get(
					CONF_NUMERO_PAG_ATTR_NAME);
		}
		if (StringUtils.isNotBlank((String) fldId[2])) {
			fields = fldId[2] + ":" + bookTypeConf.getPageNumber();
		} else {
			fields = bookTypeConf.getPageNumber();
		}
		big.drawString(fields, new Integer((String) fldId[0]).intValue(),
				new Integer((String) fldId[1]).intValue());

		outputStream1 = new ByteArrayOutputStream();
		// Then we create the image encoder and encode the buffered
		// image in
		// TIFF.

		TIFFEncodeParam params = new TIFFEncodeParam();
		params.setCompression(code);
		params.setExtraFields(nuevaResolucion());
		ImageEncoder encoder1 = ImageCodec.createImageEncoder("TIFF",
				outputStream1, params);
		if (encoder1 == null) {
			log.error("imageEncoder is null");
			System.exit(0);
		}
		encoder1.encode(bi);
		FileCacheSeekableStream inputPage = new FileCacheSeekableStream(
				new ByteArrayInputStream(outputStream1.toByteArray()));

		return inputPage;

	}

	/**
	 * Obtiene la configuración del tamaño de la fuente
	 * 
	 * @param bookConf
	 * @param bookTypeConf
	 */
	private Integer getConfiguredFontSize(BookConf bookConf,
			BookTypeConf bookTypeConf) {
		Integer fontSize = new Integer(DEFAULT_FONT_SIZE);
		
		try {
			fontSize = Integer.valueOf(bookTypeConf.getFontSize());
		} catch (Exception e) {
			log.error(
					"Error de parseo en la configuración del tamaño de la fuente del tipo de libro",
					e);
		}

		if (bookConf!=null && !StringUtils.isBlank(bookConf.getFontSize())) {
			try{
			fontSize =  Integer.valueOf(bookConf.getFontSize());
			}catch(Exception e){
				log.error(
						"Error de parseo en la configuración del tamaño de la fuente del libro",
						e);
			}
		}
		return fontSize;
	}

	/**
	 * Obtiene la configuración del nombre de la fuente
	 * 
	 * @param bookConf
	 * @param bookTypeConf
	 * @return
	 */
	private String getConfiguredFontName(BookConf bookConf,
			BookTypeConf bookTypeConf) {
		String fontName = bookTypeConf.getFontName();
		if (bookConf!=null && !StringUtils.isBlank(bookConf.getFontName())) {
			fontName = bookConf.getFontName();
		}
		return fontName;
	}

	/**
	 * @deprecated Este metodo está deprecado porque a partir de esta versión es
	 *             necesario indicar la configuración del libro además de la
	 *             configuración del tipo de libro.
	 * 
	 * @param bookTypeConf
	 * @param buffer
	 * @return
	 * @throws Exception
	 */
	public byte[] saveTiffWithText(BookTypeConf bookTypeConf, byte[] buffer)
			throws Exception {
		return saveTiffWithText(bookTypeConf, new BookConf(), buffer);
	}

	/**
	 * 
	 * Imprime en una imagen de formato TIFF la marca de la firma.
	 * 
	 * @param bookTypeConf
	 * @param bookConf
	 * @param buffer
	 * @return
	 * @throws Exception
	 */
	public byte[] saveTiffWithText(BookTypeConf bookTypeConf,
			BookConf bookConf, byte[] buffer) throws Exception {

		log.debug("Imprimiendo la marca de la firma en el documento TIFF con la siguiente configuración: ");
		log.debug("Configuración del TIPO de libro: ");
		log.debug("Configuración del libro: "+bookConf);
		byte[] buffer1 = null;
		ByteArrayOutputStream outputStream1 = null;
		FileCacheSeekableStream in = null;
		try {

			in = new FileCacheSeekableStream(new ByteArrayInputStream(buffer));

			TIFFDirectory td = new TIFFDirectory(in, 0);
			TIFFDecodeParam param = null;

			ImageDecoder dec = ImageCodec.createImageDecoder("tiff", in, param);
			int numPages = dec.getNumPages();
			RenderedImage[] img = new RenderedImage[numPages];
			// ver resolucion de imagen
			resX = td.getFieldAsFloat(TIFFImageDecoder.TIFF_X_RESOLUTION);
			resY = td.getFieldAsFloat(TIFFImageDecoder.TIFF_Y_RESOLUTION);

			// ver tipo de compresion
			code = (int) td.getFieldAsLong(TIFFImageDecoder.TIFF_COMPRESSION);
			if (code == 2 || code == 3 || code == 4) {

				// Comprobar la configuracion del libro ademas del tipo de libro
				if (printOnlyFirstPage(bookTypeConf, bookConf)) {
					// Crear la marca en la pagina 0
					RenderedImage rImage = dec.decodeAsRenderedImage(0);
					FileCacheSeekableStream inputPage = this
							.printTiffTextToPage(rImage, 0, bookConf,
									bookTypeConf);
					img[0] = JAI.create(STREAM_METHOD, inputPage);
					// Dejar la imagen tal como estaba en el resto de paginas
					for (int i = 1; i < numPages; i++) {
						RenderedImage rImage2 = dec.decodeAsRenderedImage(i);
						img[i] = rImage2;
					}
					buffer1 = saveAsMultipageTIFF(img);
				} else {
					for (int i = 0; i < numPages; i++) {
						RenderedImage rImage = dec.decodeAsRenderedImage(i);
						// img = JAI.create("stream", in);
						// RenderedImageAdapter ria = new
						// RenderedImageAdapter(img);
						FileCacheSeekableStream inputPage = this
								.printTiffTextToPage(rImage, i, bookConf,
										bookTypeConf);
						img[i] = JAI.create(STREAM_METHOD, inputPage);

						// buffer1 = outputStream1.toByteArray();
					}
					buffer1 = saveAsMultipageTIFF(img);
				}
			} else {
				buffer1 = buffer;
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				in.close();
				if (outputStream1 != null) {
					outputStream1.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
		return buffer1;
	}

	public byte[] saveAsMultipageTIFF(RenderedImage[] image) throws Exception {
		ByteArrayOutputStream out = null;
		try {
			byte[] buffer = null;
			out = new ByteArrayOutputStream();
			TIFFEncodeParam param = new TIFFEncodeParam();
			param.setCompression(code);
			param.setExtraFields(nuevaResolucion());
			ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", out,
					param);
			Vector vector = new Vector();
			for (int i = 1; i < image.length; i++) {
				vector.add(image[i]);
			}
			param.setExtraImages(vector.iterator());
			encoder.encode(image[0]);
			buffer = out.toByteArray();
			return buffer;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw e;
			}
		}
	}

	/**
	 * Nos devuelve si hay que imprimir el sello en todas las paginas o en la
	 * primera.
	 * 
	 * Esta configuracion puede estar a nivel de libro o a nivel de tipo de
	 * libro. Tiene prevalencia la configuración por libro, pero si esta no
	 * existe se obtendrá la configuración del tipo de libro.
	 * 
	 * En el caso de que no haya ninguna configuración establecdida se imprimirá
	 * el sello en todas las páginas.
	 * 
	 * @param bookTypePrintOnlyFirstPage
	 * @param bookPrintOnlyFirstPage
	 * @return
	 */
	private boolean printOnlyFirstPage(BookTypeConf bookTypeConf,
			BookConf bookConf) {

		String bookTypePrintOnlyFirstPage = "";
		String bookPrintOnlyFirstPage = "";

		if (bookTypeConf != null) {
			bookTypePrintOnlyFirstPage = bookTypeConf.getOnlyFirstPage();
		}
		if (bookConf != null) {
			bookPrintOnlyFirstPage = bookConf.getOnlyFirstPage();
		}

		boolean printOnlyFirstPage = false;

		if (!StringUtils.isEmpty(bookTypePrintOnlyFirstPage)) {
			if (bookTypePrintOnlyFirstPage
					.equalsIgnoreCase(PRINT_ONLY_FIRST_PAGE_VALUE)) {
				printOnlyFirstPage = true;
			}
		}

		if (!StringUtils.isEmpty(bookPrintOnlyFirstPage)) {
			if (bookPrintOnlyFirstPage
					.equalsIgnoreCase(PRINT_ONLY_FIRST_PAGE_VALUE)) {
				printOnlyFirstPage = true;
			} else {
				if (bookPrintOnlyFirstPage
						.equalsIgnoreCase(PRINT_ALLPAGES_VALUE)) {
					printOnlyFirstPage = false;
				}
			}

		}

		return printOnlyFirstPage;

	}

	/**
	 * @return Returns the code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return Returns the resX.
	 */
	public float getResX() {
		return resX;
	}

	/**
	 * @param resX
	 *            The resX to set.
	 */
	public void setResX(float resX) {
		this.resX = resX;
	}

	/**
	 * @return Returns the resY.
	 */
	public float getResY() {
		return resY;
	}

	/**
	 * @param resY
	 *            The resY to set.
	 */
	public void setResY(float resY) {
		this.resY = resY;
	}
}
