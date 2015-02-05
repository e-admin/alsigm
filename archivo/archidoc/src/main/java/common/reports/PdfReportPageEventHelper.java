package common.reports;

import java.awt.Color;
import java.util.Date;

import xml.config.ConfiguracionArchivoManager;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import common.OrganizationMessages;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Clase que implementa los eventos de creación de informes en PDF.
 */
public class PdfReportPageEventHelper extends PdfPageEventHelper {

	/** Plantilla para el número total de páginas. */
	protected PdfTemplate tpl;

	// Colores
	protected static final Color BORDER_COLOR = new Color(182, 203, 235);
	protected static final Color WHITE_COLOR = new Color(255, 255, 255);
	protected static final Color BACKGROUND_COLOR = new Color(244, 246, 242);
	protected static final Color HEADER_COLOR = new Color(0, 0, 128);
	protected static final Color FOOTER_COLOR = new Color(102, 102, 102);

	// Fuentes
	protected static BaseFont HELVETICA;
	protected static BaseFont ASTURICA;

	static {
		try {
			// inicialización de las fuentes base
			HELVETICA = BaseFont.createFont("Helvetica", BaseFont.WINANSI,
					BaseFont.NOT_EMBEDDED);
			ASTURICA = BaseFont.createFont("asturica.ttf", BaseFont.WINANSI,
					BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	// Tipos de letra
	protected static final Font CABECERA_H1_FONT = new Font(ASTURICA, 9,
			Font.NORMAL, HEADER_COLOR);
	protected static final Font CABECERA_H2_FONT = new Font(ASTURICA, 7,
			Font.NORMAL, HEADER_COLOR);
	protected static final Font CABECERA_H3_FONT = new Font(ASTURICA, 6,
			Font.NORMAL, HEADER_COLOR);
	protected static final Font FOOTER_FONT = new Font(Font.HELVETICA, 8,
			Font.NORMAL, FOOTER_COLOR);
	protected static final Font FOOTER_ITALIC_FONT = new Font(Font.HELVETICA,
			8, Font.ITALIC, FOOTER_COLOR);

	protected static final String TITULO = OrganizationMessages
			.getString(OrganizationMessages.TITLE);
	protected static final String SUBTITULO1 = OrganizationMessages
			.getString(OrganizationMessages.SUBTITLE1);
	protected static final String SUBTITULO2 = OrganizationMessages
			.getString(OrganizationMessages.SUBTITLE2);
	protected static final String SUBTITULO3 = OrganizationMessages
			.getString(OrganizationMessages.SUBTITLE3);
	protected static final String DIRECCION = OrganizationMessages
			.getString(OrganizationMessages.POSTAL_ADDRESS);

	// Imágenes
	protected final String ARCHIVO_IMAGE_URL;
	protected final String ORGANIZACION_IMAGE_URL;
	protected final String LOGO_CABECERA_URL;

	static {

	}

	/**
	 * Constructor.
	 */
	public PdfReportPageEventHelper(String entity) {
		super();

		ARCHIVO_IMAGE_URL = ConfiguracionArchivoManager.getInstance()
				.getPathImagenInformes("logo_archivo.gif", entity);
		ORGANIZACION_IMAGE_URL = ConfiguracionArchivoManager.getInstance()
				.getPathImagenInformes("logo_organizacion.gif", entity);
		LOGO_CABECERA_URL = ConfiguracionArchivoManager.getInstance()
				.getPathImagenInformes("cabeceraInformes.png", entity);
	}

	/**
	 * Método que se ejecuta cuando se abre el documento.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		// initialization of the template
		tpl = writer.getDirectContent().createTemplate(100, 100);
		tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
	}

	/**
	 * Método que se ejecuta cuando se inicia una página del documento.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	public void onStartPage(PdfWriter writer, Document document) {
		// Cabecera de la página
		drawHeader(writer, document);

		// Pie de la página
		drawFooter(writer, document);

		// Bordes de la página
		drawBorders(writer, document);
	}

	/**
	 * Método que se ejecuta cuando se finaliza una página del documento.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		// Número de página
		drawPageNumber(writer, document);
	}

	/**
	 * Método que se ejecuta cuando se cierra el documento.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	public void onCloseDocument(PdfWriter writer, Document document) {
		// Plantilla con el número total de páginas del documento
		tpl.beginText();
		tpl.setFontAndSize(HELVETICA, 8);
		tpl.setTextMatrix(0, 0);
		tpl.showText("" + (writer.getPageNumber() - 1));
		tpl.endText();
	}

	/**
	 * Método que dibuja los bordes de la página.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	protected void drawBorders(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();

		// Dibujar el borde de la página
		cb.setColorStroke(BORDER_COLOR);
		cb.setLineWidth(0.5f);
		cb.rectangle(document.left(), document.bottom(), document.right()
				- document.left(), document.top() - document.bottom());
		cb.stroke();

		cb.restoreState();
	}

	/**
	 * Método que dibuja la cabecera de la página.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	protected void drawHeader(PdfWriter writer, Document document) {
		try {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();

			PdfPTable tableExt = new PdfPTable(1);
			tableExt.setTotalWidth(document.right() - document.left());
			tableExt.setLockedWidth(true);
			tableExt.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableExt.getDefaultCell().setBorder(Rectangle.BOX);
			tableExt.getDefaultCell().setBorderWidth(0.1F);
			tableExt.getDefaultCell().setBorderColor(BORDER_COLOR);
			tableExt.getDefaultCell().setFixedHeight(69);
			tableExt.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			tableExt.getDefaultCell()
					.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPTable tableInt = new PdfPTable(3);
			tableInt.setTotalWidth(new float[] { 108,
					document.right() - document.left() - 222, 108 });
			tableInt.setLockedWidth(true);
			tableInt.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInt.getDefaultCell().setBorder(
					Rectangle.TOP | Rectangle.BOTTOM);
			tableInt.getDefaultCell().setBorderWidth(1);
			tableInt.getDefaultCell().setBorderColor(Color.BLUE);
			tableInt.getDefaultCell().setFixedHeight(69);
			tableInt.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			tableInt.getDefaultCell()
					.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Image logo = null;
			try {
				logo = Image.getInstance(ORGANIZACION_IMAGE_URL);
			} catch (Exception e) {
				logo = ConfiguracionArchivoManager.getInstance()
						.getBlankImage();
			}
			logo.setBorder(Rectangle.BOX);
			logo.setBorderWidth(0.1F);
			logo.setBorderColor(BORDER_COLOR);
			tableInt.addCell(logo);

			PdfPTable tableInfo = new PdfPTable(1);
			tableInfo.setTotalWidth(300);
			tableInfo.setLockedWidth(true);
			tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInfo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableInfo.getDefaultCell().setBorderWidth(0.5f);
			tableInfo.getDefaultCell().setBorderColor(Color.BLUE);
			tableInfo.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			tableInfo.getDefaultCell().setVerticalAlignment(
					Element.ALIGN_MIDDLE);

			// tableInfo.addCell(new Phrase(new Chunk(GOBIERNO,
			// CABECERA_H1_FONT)));
			tableInfo.addCell(new Phrase(new Chunk(TITULO, CABECERA_H1_FONT)));

			PdfPCell cell = new PdfPCell(tableInfo.getDefaultCell());
			if (StringUtils.isNotEmpty(SUBTITULO1)) {
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				// cell.setPhrase(new Phrase(new Chunk(CONSEJERIA,
				// CABECERA_H2_FONT)));
				cell.setPhrase(new Phrase(new Chunk(SUBTITULO1,
						CABECERA_H2_FONT)));
				tableInfo.addCell(cell);
			}

			if (StringUtils.isNotEmpty(SUBTITULO2)) {
				cell = new PdfPCell(tableInfo.getDefaultCell());
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				// cell.setPhrase(new Phrase(new Chunk(VICECONSEJERIA,
				// CABECERA_H3_FONT)));
				cell.setPhrase(new Phrase(new Chunk(SUBTITULO2,
						CABECERA_H3_FONT)));
				tableInfo.addCell(cell);
			}

			if (StringUtils.isNotEmpty(SUBTITULO3)) {
				// tableInfo.addCell(new Phrase(new Chunk(DIRECCION,
				// CABECERA_H3_FONT)));
				tableInfo.addCell(new Phrase(new Chunk(SUBTITULO3,
						CABECERA_H3_FONT)));
			}

			tableInt.addCell(tableInfo);

			// tableInt.addCell(new Phrase(new Chunk(APPLICATION_NAME,
			// APP_NAME_FONT)));
			Image logoArchivo = null;

			try {
				logoArchivo = Image.getInstance(ARCHIVO_IMAGE_URL);
			} catch (Exception e) {
				logoArchivo = ConfiguracionArchivoManager.getInstance()
						.getBlankImage();
			}
			tableInt.addCell(logoArchivo);

			tableExt.addCell(tableInt);
			tableExt.writeSelectedRows(0, -1, document.left(),
					document.top() + 80, cb);

			cb.restoreState();
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * Método que dibuja el pie de la página.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	private void drawFooter(PdfWriter writer, Document document) {
		try {
			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();

			PdfPTable table = new PdfPTable(2);
			table.setTotalWidth(new float[] { 70,
					document.right() - document.left() - 70 });
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(Rectangle.BOX);
			table.getDefaultCell().setBorderWidth(0.1F);
			table.getDefaultCell().setBorderColor(BORDER_COLOR);
			table.getDefaultCell().setBackgroundColor(BACKGROUND_COLOR);
			table.getDefaultCell().setFixedHeight(45);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			/*
			 * Image logo = Image.getInstance(FOOTER_IMAGE_URL);
			 * logo.setBorder(Rectangle.BOX); logo.setBorderWidth(0.1F);
			 * logo.setBorderColor(BORDER_COLOR); table.addCell(logo);
			 */
			table.addCell("");

			PdfPTable tableInfo = new PdfPTable(1);
			tableInfo.setTotalWidth(document.right() - document.left() - 75);
			tableInfo.setLockedWidth(true);
			tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInfo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tableInfo.getDefaultCell().setFixedHeight(20);
			tableInfo.getDefaultCell().setPadding(5);
			tableInfo.addCell(new Phrase(new Chunk(
					DateUtils.EXTENDED_DATE_FORMATTER.format(new Date()),
					FOOTER_FONT)));

			PdfPCell tableInfoCell = new PdfPCell(tableInfo.getDefaultCell());
			tableInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableInfoCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			tableInfoCell.setPhrase(new Phrase(new Chunk(DIRECCION,
					FOOTER_ITALIC_FONT)));
			tableInfo.addCell(tableInfoCell);
			table.addCell(tableInfo);

			table.writeSelectedRows(0, -1, document.left(),
					document.bottom() - 5, cb);

			cb.restoreState();
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * Método que dibuja el número de página.
	 * 
	 * @param writer
	 *            Creador de documentos.
	 * @param document
	 *            Documento del informe.
	 */
	public void drawPageNumber(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();

		// Número de página
		String text = new StringBuffer().append("Página ")
				.append(writer.getPageNumber()).append(" de ").toString();

		float textSize = HELVETICA.getWidthPoint(text, 8);
		float textBase = document.bottom() - 20;

		cb.beginText();
		cb.setColorFill(FOOTER_COLOR);
		cb.setFontAndSize(HELVETICA, 8);
		cb.setTextMatrix(document.right() - textSize - 20, textBase);
		cb.showText(text);
		cb.endText();
		cb.addTemplate(tpl, document.right() - 20, textBase);

		cb.restoreState();
	}

}
