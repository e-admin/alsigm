package export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.RGBColor;

/**
 * Exportación a Excel
 * 
 * @author lucas
 * 
 */
public class ExcelReport {
	/**
	 * Libro Activo
	 */
	private HSSFWorkbook workbook;

	/**
	 * Hoja Activa
	 */
	private HSSFSheet currentSheet;

	/**
	 * Fila Activa
	 */
	private HSSFRow currentRow;

	private HSSFCellStyle defaultCellStyle;

	private HSSFCellStyle getStyle(RGBColor color) {
		short indexColor = 250;
		createCustomizedColor(indexColor, color.getRed(), color.getGreen(),
				color.getBlue());

		HSSFCellStyle style = this.workbook.createCellStyle();
		HSSFFont font = this.workbook.createFont();

		font.setColor(indexColor);
		style.setFont(font);

		return style;
	}

	private void createCustomizedColor(short indexColor, byte red, byte green,
			byte blue) {
		HSSFPalette palette = this.workbook.getCustomPalette();
		palette.setColorAtIndex(indexColor, red, green, blue);
	}

	/**
	 * Crea un nuevo Libro
	 * 
	 * @return HSSFWorkbook
	 */
	private void createNewWorkbook() {
		this.workbook = new HSSFWorkbook();
		RGBColor color = new RGBColor((byte) 255, (byte) 255, (byte) 255);
		this.defaultCellStyle = getStyle(color);
	}

	/**
	 * Crea una nueva Hoja, con una fila
	 * 
	 * @param workbook
	 *            Libro
	 * @param nombreHoja
	 *            Nombre de la nueva Hoja
	 * @return HSSFSheet
	 */
	private void createNewSheet(String nombreHoja) {
		if (nombreHoja != null) {
			this.currentSheet = this.workbook.createSheet(nombreHoja);
		} else {
			this.currentSheet = this.workbook.createSheet();
		}

		this.currentRow = this.currentSheet.createRow(0);
	}

	/**
	 * Crea una nueva Fila en la hoja especificada
	 * 
	 * @param sheet
	 *            Hoja
	 * @param index
	 *            índice la nueva fila
	 * @return HSSFRow
	 */
	private void createNewRow() {
		int index = this.currentSheet.getLastRowNum() + 1;
		this.currentRow = this.currentSheet.createRow(index);
	}

	/**
	 * Crea una nueva celda
	 * 
	 * @param row
	 *            Fila
	 * @param index
	 *            Indice de la Celda
	 * @return
	 */
	private void createNewCell(short index, String valor, HSSFCellStyle style) {
		HSSFCell cell = this.currentRow.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(valor);

	}

	/**
	 * Constructor por Defecto. Crea el Workbook
	 */
	public ExcelReport() {
		createNewWorkbook();
	}

	/**
	 * Añade una Hoja al Workbook con un nombre específico
	 * 
	 * @param nombre
	 *            Nombre de la Hoja
	 */
	public void addHoja(String nombreHoja) {
		createNewSheet(nombreHoja);
	}

	/**
	 * Añade una Hoja al Workbook
	 */
	public void addHoja() {
		createNewSheet(null);
	}

	/**
	 * Añade una fila a la hoja especificada
	 * 
	 * @param indexHoja
	 *            Indice de la hoja
	 * @return Índice de la Fila creada
	 */
	public void addFila() {
		createNewRow();
	}

	/**
	 * Añade una celda
	 * 
	 * @param indexCelda
	 * @param valor
	 */
	public void addCelda(short indexCelda, String valor) {
		createNewCell(indexCelda, valor, defaultCellStyle);
	}

	/**
	 * Añade una Celda con el Color de Fuente especificado
	 * 
	 * @param indexCelda
	 * @param valor
	 * @param colorFuente
	 */
	public void addCelda(short indexCelda, String valor, RGBColor colorFuente) {
		createNewCell(indexCelda, valor, getStyle(colorFuente));
	}

	/**
	 * Obtiene los Bytes del fichero creado
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		// byteArrayOutputStream.close();
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Obtiene la Cadena de Bytes
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getString() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		// byteArrayOutputStream.close();
		return byteArrayOutputStream.toString();
	}

}
