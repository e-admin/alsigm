package common.reports;

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
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import common.util.DateUtils;

/**
 * Clase que implementa los eventos de creación de informes en PDF.
 */
public class PdfReportPageEventHelperWithHeader extends
		PdfReportPageEventHelper {

	private static final BaseFont ARIAL;
	static {
		try {
			// inicialización de las fuentes base
			ARIAL = BaseFont.createFont("Helvetica", BaseFont.WINANSI,
					BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	protected static final Font CABECERA_H1_FONT = new Font(ARIAL, 8,
			Font.BOLD, HEADER_COLOR);
	protected static final Font CABECERA_H2_FONT = new Font(ARIAL, 8,
			Font.NORMAL, HEADER_COLOR);
	protected static final Font CABECERA_H3_FONT = new Font(ARIAL, 14,
			Font.NORMAL, HEADER_COLOR);

	/**
	 * Constructor.
	 */
	public PdfReportPageEventHelperWithHeader(String entity) {
		super(entity);
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
			// tableExt.getDefaultCell().setBorder(Rectangle.BOTTOM);
			// tableExt.getDefaultCell().setBorderWidth(1F);
			tableExt.getDefaultCell().setBorderColor(WHITE_COLOR);
			// tableExt.getDefaultCell().setFixedHeight(69);
			tableExt.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			tableExt.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

			Image cabeceraInforme = null;

			try {
				cabeceraInforme = Image.getInstance(LOGO_CABECERA_URL);
			} catch (Exception e) {
				cabeceraInforme = ConfiguracionArchivoManager.getInstance()
						.getBlankImage();
			}

			cabeceraInforme.setWidthPercentage(100);

			// final int ANCHO_PRIMERA_COLUMNA_TABLEINT=430;
			// PdfPTable tableInt = new PdfPTable(2);
			// tableInt.setTotalWidth(new float []
			// {ANCHO_PRIMERA_COLUMNA_TABLEINT,
			// document.right() - document.left() -
			// ANCHO_PRIMERA_COLUMNA_TABLEINT});
			// tableInt.setLockedWidth(true);
			// tableInt.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			// tableInt.getDefaultCell().setFixedHeight(69);
			// tableInt.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			// tableInt.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			// //tableInt.getDefaultCell().setPadding(5);
			//
			// //Image logo = Image.getInstance(ORGANIZACION_IMAGE_URL);
			// //tableInt.addCell(logo);
			// tableInt.addCell(new Phrase(new Chunk(TITULO,CABECERA_H3_FONT)));
			//
			//
			// PdfPTable tableInfo = new PdfPTable(1);
			// tableInfo.setTotalWidth(document.right() - document.left() -
			// ANCHO_PRIMERA_COLUMNA_TABLEINT);
			// tableInfo.setLockedWidth(true);
			// tableInfo.getDefaultCell().setNoWrap(false);
			// tableInfo.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
			// //tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			// tableInfo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			// //tableInfo.getDefaultCell().setBorderWidth(0.5f);
			// //tableInfo.getDefaultCell().setBorderColor(Color.BLUE);
			// //tableInfo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			// tableInfo.addCell(new Phrase(new Chunk(DIRECCION,
			// CABECERA_H2_FONT)));
			// tableInt.addCell(tableInfo);

			tableExt.addCell(cabeceraInforme);
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

			PdfPTable table = new PdfPTable(1);
			table.setTotalWidth(document.right() - document.left());
			table.setLockedWidth(true);
			// table.setHorizontalAlignment(Element.ALIGN_CENTER);
			// table.getDefaultCell().setBorder(Rectangle.BOX);
			// table.getDefaultCell().setBorderWidth(0.1F);
			// table.getDefaultCell().setBorderColor(BORDER_COLOR);
			// table.getDefaultCell().setBackgroundColor(BACKGROUND_COLOR);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setFixedHeight(30);
			table.getDefaultCell().setPadding(5);
			// table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			// table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(new Phrase(new Chunk(
					DateUtils.EXTENDED_DATE_FORMATTER.format(new Date()),
					FOOTER_FONT)));
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
