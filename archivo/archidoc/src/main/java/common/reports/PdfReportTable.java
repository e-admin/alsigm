package common.reports;

import java.awt.Color;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import common.Constants;

/**
 * Tabla del informe.
 */
public class PdfReportTable extends PdfPTable {

	// Colores
	private static final Color DATA_LINE_COLOR = new Color(0xCC, 0xCC, 0xCC);
	private static final Color TITLE_BG_COLOR = new Color(0xB6, 0xCB, 0xEB);
	private static final Color TITLE_COLOR = new Color(0x66, 0x66, 0xFF);
	private static final Color DATA_COLOR = new Color(0, 0, 0x66);

	// Tipos de letra
	private static final Font FONT_HEADER = new Font(Font.HELVETICA, 8,
			Font.BOLDITALIC, DATA_COLOR);
	private static final Font FONT_ROW = new Font(Font.HELVETICA, 8,
			Font.NORMAL, DATA_COLOR);

	/**
	 * Constructor.
	 * 
	 * @param report
	 *            Informe.
	 * @param columnWidths
	 *            Anchura de las columnas (en porcentaje).
	 */
	public PdfReportTable(PdfReport report, float[] columnWidths) {
		super(columnWidths);

		setTotalWidth(report.right() - report.left() - 40);
		setLockedWidth(true);
		setSpacingAfter(5);
		setHorizontalAlignment(Element.ALIGN_CENTER);
		getDefaultCell().setBorder(0);
		getDefaultCell().setBorderWidthBottom(0.1F);
		getDefaultCell().setBorderColorBottom(DATA_LINE_COLOR);
		getDefaultCell().setPaddingLeft(5);
	}

	/**
	 * Añade los títulos de las columnas.
	 * 
	 * @param titles
	 *            Títulos de las columnas.
	 */
	public void addColumnTitles(String[] titles) {
		PdfPCell cell = null;

		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];

			if (title != null) {

				cell = new PdfPCell();
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor(TITLE_BG_COLOR);
				cell.setBorder(0);
				cell.setBorderColor(TITLE_COLOR);
				cell.setBorderWidthTop(0.1F);
				cell.setBorderWidthBottom(0.1F);
				cell.setPaddingLeft(10);

				if (i == 0)
					cell.setBorderWidthLeft(0.1F);
				if (i == titles.length - 1)
					cell.setBorderWidthRight(0.1F);

				cell.setPhrase(new Phrase(new Chunk(title, FONT_HEADER)));

				addCell(cell);
			}
		}
	}

	/**
	 * Añade un valor de la tabla.
	 * 
	 * @param value
	 *            Valor de la tabla.
	 */
	public void addRowValue(String value) {
		if (value == null)
			value = Constants.BLANK;
		addCell(new Phrase(new Chunk(value, FONT_ROW)));
	}
}
