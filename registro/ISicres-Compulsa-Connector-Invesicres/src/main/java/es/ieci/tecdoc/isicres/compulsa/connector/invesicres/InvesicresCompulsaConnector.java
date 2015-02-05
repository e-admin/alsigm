/**
 *
 */
package es.ieci.tecdoc.isicres.compulsa.connector.invesicres;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.CompulsaKeys;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.ieci.tecdoc.isicres.compulsa.connector.ISicresCompulsaConnector;
import es.ieci.tecdoc.isicres.compulsa.connector.exception.ISicresCompulsaConnectorException;
import es.ieci.tecdoc.isicres.compulsa.connector.invesicres.vo.InvesicresCompulsaDatosEspecificosVO;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class InvesicresCompulsaConnector implements ISicresCompulsaConnector {

	private static final Logger logger = Logger
			.getLogger(InvesicresCompulsaConnector.class);

	private PdfReader reader;
	private Image img;
	private static String imagen;

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.compulsa.connector.ISicresCompulsaConnector#generateLocator(es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO)
	 */
	public ISicresAbstractCompulsaVO generateLocator(
			ISicresAbstractCompulsaVO compulsaVO) {
		String entidad = getEntidadLocator(compulsaVO.getEntidadId());
		String folderNumber = getFolderNumberLocator(compulsaVO
				.getFolderNumber());

		SimpleDateFormat sdf = new SimpleDateFormat(
				CompulsaKeys.DATE_FORMAT_LOCATOR);
		String fecha = sdf.format(compulsaVO.getFechaCompulsa());

		String codVerificacion = generateCodigoVerificacion(entidad,
				folderNumber, fecha);

		String alfaCodVerigicacion = getAlfanumericCodigoVerificacion(codVerificacion);

		compulsaVO.setLocator(alfaCodVerigicacion);
		return compulsaVO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.compulsa.connector.ISicresCompulsaConnector#generateXadesDocument(es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO)
	 */
	public void generateXadesDocument(ISicresAbstractCompulsaVO compulsaVO) {
		InvesicresCompulsaDatosEspecificosVO datosEspecificos = (InvesicresCompulsaDatosEspecificosVO) compulsaVO
				.getDatosEspecificos();

		StringBuffer buffer = new StringBuffer();
		buffer.append(datosEspecificos.getSessionID());
		buffer.append(CompulsaKeys.GUIONBAJO);
		buffer.append(compulsaVO.getFolderId());
		buffer.append(CompulsaKeys.GUIONBAJO);
		buffer.append(0);
		buffer.append(CompulsaKeys.PUNTO);
		buffer.append("xades");

		byte[] dataXades = datosEspecificos.getXadesFormat().getBytes();

		String out = datosEspecificos.getTempPath() + "/" + buffer.toString();

		FileOutputStream fileXades = null;
		try {
			File fNew = new File(out);
			fNew.deleteOnExit();
			fileXades = new FileOutputStream(out);
			fileXades.write(dataXades, 0, dataXades.length);

		} catch (Exception e) {
			logger.error(
					"Se ha producido un error al obtener el fichero xades", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producido un error al obtener el fichero xades", e);
		} finally {
			try {
				fileXades.close();
			} catch (IOException e) {
				logger.error(
						"Se ha producido un error al obtener el fichero xades",
						e);
				throw new ISicresCompulsaConnectorException(
						"Se ha producido un error al obtener el fichero xades",
						e);
			}
		}
	}

	public ISicresAbstractCompulsaVO compulsar(
			ISicresAbstractCompulsaVO iSicresAbstractCompulsaVO) {

		initInvesicresCompulsaConnector(iSicresAbstractCompulsaVO);

		Image imagen = img;
		int numPages = reader.getNumberOfPages();
		int largo = (int) reader.getPageSize(numPages).getHeight();
		int ancho = (int) reader.getPageSize(numPages).getWidth();

		Document document = new Document();

		InvesicresCompulsaDatosEspecificosVO datosEspecificosVO = (InvesicresCompulsaDatosEspecificosVO) iSicresAbstractCompulsaVO
				.getDatosEspecificos();

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					datosEspecificosVO.getOutputStream());
			document.open();
			Rectangle r = document.getPageSize();

			for (int i = 1; i <= numPages; i++) {
				PdfImportedPage page = writer.getImportedPage(reader, i);
				Image image = Image.getInstance(page);
				if (datosEspecificosVO.getBand() == 1) {
					image.setAbsolutePosition(datosEspecificosVO.getBandSize(),
							0);
					image.scaleAbsoluteWidth(r.getWidth()
							- datosEspecificosVO.getBandSize());
					image.scaleAbsoluteHeight(r.getHeight());
					imagen.setRotationDegrees(90F);
					document.add(image);
					if (imagen != null) {
						for (int j = 0; j < largo; j = (int) ((float) j + imagen
								.getWidth())) {
							imagen.setAbsolutePosition(0, j);
							imagen.scaleAbsoluteHeight(datosEspecificosVO
									.getBandSize());
							document.add(imagen);
						}
					}
					PdfContentByte over = writer.getDirectContent();

					getImagen(iSicresAbstractCompulsaVO, over, numPages, i);
				} else {
					image.setAbsolutePosition(0, 0);
					image.scaleAbsoluteWidth(r.getWidth());
					image.scaleAbsoluteHeight(r.getHeight()
							- datosEspecificosVO.getBandSize());
					document.add(image);
					if (imagen != null) {
						for (int j = 0; j < ancho; j = (int) ((float) j + imagen
								.getWidth())) {
							imagen.setAbsolutePosition(j, largo
									- datosEspecificosVO.getBandSize());
							imagen.scaleAbsoluteHeight(datosEspecificosVO
									.getBandSize());
							document.add(imagen);
						}

					}
					PdfContentByte over = writer.getDirectContent();

					getImagen(iSicresAbstractCompulsaVO, over, numPages, i);
				}
				document.newPage();
			}
		} catch (Exception e) {
			logger.error("Se ha producio un error al compulsar documentos", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producio un error al compulsar documentos", e);
		}
		document.close();

		return iSicresAbstractCompulsaVO;
	}

	protected void initInvesicresCompulsaConnector(
			ISicresAbstractCompulsaVO iSicresAbstractCompulsaVO) {

		InvesicresCompulsaDatosEspecificosVO datosEspecificos = (InvesicresCompulsaDatosEspecificosVO) iSicresAbstractCompulsaVO
				.getDatosEspecificos();

		imagen = datosEspecificos.getFondoPath();

		InputStream inputStream = datosEspecificos.getInputStream();
		try {
			reader = new PdfReader(inputStream);
		} catch (IOException e) {
			logger.error(
					"Se ha producido un error en la compulsa de documentos", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producido un error en la compulsa de documentos", e);
		}

		try {
			img = Image.getInstance(imagen);
			img.setAbsolutePosition(200F, 400F);
		} catch (BadElementException e) {
			logger.error(
					"Se ha producido un error en la compulsa de documentos", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producido un error en la compulsa de documentos", e);
		} catch (MalformedURLException e) {
			logger.error(
					"Se ha producido un error en la compulsa de documentos", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producido un error en la compulsa de documentos", e);
		} catch (IOException e) {
			logger.error(
					"Se ha producido un error en la compulsa de documentos", e);
			throw new ISicresCompulsaConnectorException(
					"Se ha producido un error en la compulsa de documentos", e);
		}

	}

	/**
	 * Método que obtiene el codigo de la entidad para la compulsa
	 *
	 * @param entidadId
	 * @return
	 */
	protected String getEntidadLocator(String entidadId) {
		String entidad = entidadId;
		if (CompulsaKeys.ENTIDAD_DEFAULT.equalsIgnoreCase(entidad)) {
			entidad = CompulsaKeys.ENTIDAD_DEFAULT_CODE;
		}

		return entidad;
	}

	/**
	 * Método que obtiene el los 4 ultimos digitos del numero de registro
	 *
	 * @param folderNumber
	 * @return
	 */
	protected String getFolderNumberLocator(String folderNumber) {
		String result = "0000";
		if (StringUtils.isNotBlank(folderNumber)) {
			if (folderNumber.length() >= 4) {
				result = folderNumber.substring(folderNumber.length() - 4);
			} else {
				result = result.substring(result.length()
						- folderNumber.length())
						+ folderNumber;
			}
		}

		return result;
	}

	/**
	 * Método que genera un localizador a partir de los parametros recibidos
	 *
	 * @param entidad
	 * @param folderNumber
	 * @param fecha
	 * @return
	 */
	protected String generateCodigoVerificacion(String entidad,
			String folderNumber, String fecha) {

		// Generar el código de verificación
		String codVerificacion = fecha.substring(11, 12) + // primer dígito de
				// la hora
				fecha.substring(5, 6) + // primer dígito del mes
				fecha.substring(2, 3) + // tercer dígito del año
				folderNumber.substring(2, 3) + // tercer dígito del id
				entidad.substring(0, 1) + // primer dígito de la entidad

				fecha.substring(12, 13) + // segundo dígito de la hora
				fecha.substring(6, 7) + // segundo dígito del mes
				fecha.substring(3, 4) + // cuarto dígito del año
				folderNumber.substring(1, 2) + // segundo dígito del id
				entidad.substring(1, 2) + // segundo dígito de la entidad

				fecha.substring(14, 15) + // primer dígito de los minutos
				fecha.substring(8, 9) + // primer dígito del día
				fecha.substring(1, 2) + // segundo dígito del año
				folderNumber.substring(3, 4) + // cuarto dígito del id
				entidad.substring(2, 3) + // tercer dígito de la entidad

				fecha.substring(15, 16) + // segundo dígito de los minutos
				fecha.substring(9, 10) + // segundo dígito del día
				fecha.substring(17, 18) + // primer dígito de los segundos
				fecha.substring(18, 19) + // segundo dígito de los segundos
				folderNumber.substring(0, 1);// primer dígito del id

		return codVerificacion;
	}

	/**
	 * Método que transforma el localizor numerico en un localizador
	 * alfanumerico
	 *
	 * @param codVerificacion
	 * @return
	 */
	protected String getAlfanumericCodigoVerificacion(String codVerificacion) {

		String alfaCodVerificacion = codVerificacion;

		// Obtenemos el penúltimo dígito del Código de verificación
		String choice = codVerificacion.substring(18, 19);
		int iChoice = Integer.parseInt(choice);

		switch (iChoice) {
		case 0:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 3,
					getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 5,
					getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			break;
		case 1:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 8,
					getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 11,
					getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 13,
					getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 15,
					getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 2:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 17,
					getSubstitute(17, codVerificacion.charAt(17), iChoice));
			break;
		case 3:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 4:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 5,
					getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 5:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 15,
					getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 6:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 3,
					getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 17,
					getSubstitute(17, codVerificacion.charAt(17), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 7:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 8:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 11,
					getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 9:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 8,
					getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 13,
					getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;

		}

		return alfaCodVerificacion;
	}

	/**
	 * Métodos para sustituir digitos por letras
	 *
	 * @param posicion
	 * @param digito
	 * @param choice
	 * @return
	 */
	private char getSubstitute(int posicion, char digito, int choice) {

		final char letras[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z' };

		final List grupo1 = new ArrayList(7);
		final List grupo2 = new ArrayList(6);
		final List grupo3 = new ArrayList(3);

		grupo1.add(new Integer(1));
		grupo1.add(new Integer(3));
		grupo1.add(new Integer(6));
		grupo1.add(new Integer(8));
		grupo1.add(new Integer(11));
		grupo1.add(new Integer(13));
		grupo1.add(new Integer(16));

		grupo2.add(new Integer(2));
		grupo2.add(new Integer(5));
		grupo2.add(new Integer(7));
		grupo2.add(new Integer(12));
		grupo2.add(new Integer(15));
		grupo2.add(new Integer(19));

		grupo3.add(new Integer(0));
		grupo3.add(new Integer(10));
		grupo3.add(new Integer(17));

		int iDigito = Integer.parseInt(Character.toString(digito));
		Integer iPosicion = new Integer(posicion);

		if (grupo1.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[iDigito]);
			} else {
				// minusculas
				return letras[iDigito];
			}

		} else if (grupo2.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[10 + iDigito]);
			} else {
				// minusculas
				return letras[10 + iDigito];
			}
		} else if (grupo3.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[20 + iDigito]);
			} else {
				// minusculas
				return letras[20 + iDigito];
			}
		}

		return digito;
	}

	/**
	 * Método que reemplaza caracteres de una cadena
	 *
	 * @param cadena
	 * @param pos
	 * @param car
	 * @return
	 */
	private String replaceChar(String cadena, int pos, char car) {

		String resultado = "";

		if (pos >= 0 && pos < cadena.length()) {
			int i = 0;
			while (i < pos) {
				resultado += cadena.charAt(i);
				i++;
			}
			resultado += car;
			i++;
			while (i > pos && i < cadena.length()) {
				resultado += cadena.charAt(i);
				i++;
			}
		}

		return resultado;
	}

	/**
	 * Método que comprueba si un par de numero son pares o no
	 *
	 * @param num1
	 * @param num2
	 * @return
	 */
	private boolean samePar(int num1, int num2) {
		boolean p = false;

		if ((num1 % 2 == 0 && num2 % 2 == 0)
				|| (num1 % 2 != 0 && num2 % 2 != 0)) {
			p = true;
		}

		return p;
	}

	private void getImagen(ISicresAbstractCompulsaVO compulsa,
			PdfContentByte pdfContentByte, int numPageTotal, int numPage)
			throws Exception {
		String sCadena = null;

		SimpleDateFormat sdf = new SimpleDateFormat(
				CompulsaKeys.DATE_FORMAT_COMPULSA);
		String dateCompul = sdf.format(compulsa.getFechaCompulsa());

		InvesicresCompulsaDatosEspecificosVO datosEspecificos = (InvesicresCompulsaDatosEspecificosVO) compulsa
				.getDatosEspecificos();

		BaseFont bf = BaseFont.createFont(datosEspecificos.getFont(),
				datosEspecificos.getEncoding(), false);
		float size = datosEspecificos.getFontSize();
		float x = datosEspecificos.getMargen();

		BufferedReader br = new BufferedReader(new FileReader(datosEspecificos
				.getDatosPath()));
		pdfContentByte.beginText();
		pdfContentByte.setFontAndSize(bf, size);

		int i = 0;
		while ((sCadena = br.readLine()) != null) {
			pdfContentByte.setTextMatrix(0, 1, -1, 0, x, datosEspecificos
					.getMargen());
			if (i == 0) {
				pdfContentByte.showText(sCadena + " " + compulsa.getLocator());
			} else if (i == 1) {
				sCadena = completeTextLineSignerDate(sCadena, compulsa
						.getFolderNumber(), dateCompul);
				pdfContentByte.showText(sCadena);
			} else if (i == 2) {
				pdfContentByte.showText(sCadena + " "
						+ datosEspecificos.getFuncName());
			} else if (i == 3) {
				sCadena = completeTextLinePages(sCadena, numPageTotal, numPage);
				pdfContentByte.showText(sCadena);
			} else {
				pdfContentByte.showText(sCadena);
			}
			i++;
			x = x + size;
		}
		pdfContentByte.endText();
	}

	private String completeTextLineSignerDate(String sCadena,
			String folderNumber, String date) {
		if (StringUtils.isNotBlank(sCadena)) {
			String cadenaAux = MessageFormat.format(sCadena, new String[] {
					folderNumber, date });
			return cadenaAux;
		} else {
			return folderNumber + " - " + date;
		}
	}

	private String completeTextLinePages(String sCadena, int numPageTotal,
			int numPage) {
		if (sCadena != null) {
			String cadenaAux = MessageFormat.format(sCadena, new String[] {
					String.valueOf(numPageTotal), String.valueOf(numPage),
					String.valueOf(numPageTotal) });

			return cadenaAux;
		}
		return sCadena;
	}

}
