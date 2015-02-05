package common.reports;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import common.Constants;
import common.util.StringUtils;

/**
 * Clase que encapsula la funcionalidad de creación de informes.
 */
public class PdfReport extends Document {

	/** Contenido del informe. */
	private ByteArrayOutputStream buffer = null;

	// Configuración de la página
	private static final Rectangle DEFAULT_PAGE_SIZE = PageSize.A4;
	private static final int DEFAULT_MARGIN_LEFT = 30;
	private static final int DEFAULT_MARGIN_RIGHT = 30;
	private static final int DEFAULT_MARGIN_TOP = 30 + 80;
	private static final int DEFAULT_MARGIN_BOTTOM = 30 + 50;

	private static final String DEFAULT_CREATOR = "IECISA";

	// Colores
	private static final Color REPORT_TITLE_COLOR = new Color(0x99, 0x99, 0x99);
	private static final Color SECTION_COLOR = new Color(0, 0, 0x66);
	private static final Color DATA_LINE_COLOR = new Color(0xCC, 0xCC, 0xCC);

	// Tipos de letra
	public static final Font REPORT_TITLE_FONT = new Font(Font.HELVETICA, 10,
			Font.BOLDITALIC, REPORT_TITLE_COLOR);
	public static final Font SECTION_FONT = new Font(Font.HELVETICA, 10,
			Font.BOLDITALIC, SECTION_COLOR);
	public static final Font DATA_NAME_FONT = new Font(Font.HELVETICA, 10,
			Font.BOLD, SECTION_COLOR);
	public static final Font DATA_VALUE_FONT = new Font(Font.HELVETICA, 10,
			Font.NORMAL, SECTION_COLOR);
	public static final Font MESSAGE_FONT = new Font(Font.HELVETICA, 8,
			Font.BOLDITALIC, SECTION_COLOR);

	/**
	 * Constructor.
	 */
	public PdfReport(PdfReportPageEventHelper helper, String entity) {
		super(DEFAULT_PAGE_SIZE, DEFAULT_MARGIN_LEFT, DEFAULT_MARGIN_RIGHT,
				DEFAULT_MARGIN_TOP, DEFAULT_MARGIN_BOTTOM);

		try {
			buffer = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(this, buffer);
			writer.setPageEvent(helper);

			// Creador del documento
			addCreator(DEFAULT_CREATOR);

			// Marca de agua
			// createWatermark();
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * Obtiene el contenido del informe en PDF.
	 * 
	 * @return Contenido del informe en PDF.
	 */
	public byte[] getPDFContent() {
		return buffer.toByteArray();
	}

	/**
	 * Añade el título del informe.
	 * 
	 * @param title
	 *            Título del informe.
	 * @throws DocumentException
	 *             si ocurre algún error.
	 */
	public void addReportTitle(String title) throws DocumentException {
		PdfPTable table = new PdfPTable(1);

		table.setTotalWidth(right() - left());
		table.setLockedWidth(true);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setMinimumHeight(50);
		table.getDefaultCell().setBorder(0);
		table.addCell(new Phrase(new Chunk(title, REPORT_TITLE_FONT)));

		add(table);
	}

	/**
	 * Añade una sección al informe.
	 * 
	 * @param name
	 *            Nombre de la sección.
	 * @throws DocumentException
	 *             si ocurre algún error.
	 */
	public void addSection(String name) throws DocumentException {
		PdfPTable table = new PdfPTable(1);

		table.setTotalWidth(right() - left() - 4);
		table.setLockedWidth(true);
		table.setSpacingAfter(10);
		table.setSpacingBefore(20);
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidth(1);
		table.getDefaultCell().setBorderColor(SECTION_COLOR);
		table.getDefaultCell().setIndent(5);
		table.addCell(new Phrase(new Chunk(name, SECTION_FONT)));

		add(table);
	}

	/**
	 * Añade una subsección al informe.
	 * 
	 * @param name
	 *            Nombre de la subsección.
	 * @throws DocumentException
	 *             si ocurre algún error.
	 */
	public void addSubSection(String name) throws DocumentException {
		PdfPTable table = new PdfPTable(1);

		table.setTotalWidth(right() - left() - 40);
		table.setLockedWidth(true);
		table.setSpacingAfter(5);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidth(0.1F);
		table.getDefaultCell().setBorderColor(DATA_LINE_COLOR);
		table.addCell(new Phrase(new Chunk(name, SECTION_FONT)));

		add(table);
	}

	/**
	 * Añade un campo al informe.
	 * 
	 * @param name
	 *            Nombre del campo.
	 * @param value
	 *            Valor del campo.
	 * @throws DocumentException
	 *             si ocurre algún error.
	 */
	public void addData(String name, String value) throws DocumentException {
		if (value == null)
			value = Constants.STRING_EMPTY;

		PdfPTable table = new PdfPTable(new float[] { 30F, 70F });

		table.setTotalWidth(right() - left() - 40);
		table.setLockedWidth(true);
		table.setSpacingAfter(5);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		table.getDefaultCell().setBorderWidth(0.1F);
		table.getDefaultCell().setBorderColor(DATA_LINE_COLOR);
		table.addCell(new Phrase(new Chunk(StringUtils.isNotBlank(name) ? name
				+ ": " : "\t", DATA_NAME_FONT)));

		PdfPCell cell = new PdfPCell(table.getDefaultCell());
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.setPhrase(new Phrase(new Chunk(
				StringUtils.isNotBlank(value) ? value : "", DATA_VALUE_FONT)));

		table.addCell(cell);
		add(table);
	}

	/**
	 * Añade un mensaje.
	 * 
	 * @param message
	 *            Texto del mensaje.
	 * @throws DocumentException
	 *             si ocurre algún error.
	 */
	public void addMessage(String message) throws DocumentException {
		PdfPTable table = new PdfPTable(1);

		table.setTotalWidth(right() - left() - 40);
		table.setLockedWidth(true);
		table.setSpacingAfter(5);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.addCell(new Phrase(new Chunk(message, MESSAGE_FONT)));

		add(table);
	}

	/**
	 * Crea una tabla para el informe.
	 * 
	 * @param columnWidths
	 *            Anchura de las columnas (en porcentaje).
	 * @return Tabla.
	 */
	public PdfReportTable createTable(float[] columnWidths) {
		return new PdfReportTable(this, columnWidths);
	}

}
