package ieci.tecdoc.sgm.tram.sign;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.sign.ISignConnector;

import java.util.List;

import com.lowagie.text.pdf.PdfContentByte;

public interface ISigemSignConnector extends ISignConnector {

	/**
	 * Genera la imagen para la banda gris lateral del documento PDF a firmar.
	 *
	 * @param dateFirma
	 *            Fecha de la firma.
	 * @param pdfContentByte
	 *            Contenido del documento PDF para el que se genera la banda
	 *            gris lateral.
	 * @param margen
	 *            Margen para la banda lateral.
	 * @param vh
	 *            Indicador de orientación del documento (vertical/horizontal).
	 * @param x
	 *            Ancho de línea para cada línea de la banda.
	 * @param numberOfPages
	 *            Número de páginas del documento PDF.
	 * @param pageActual
	 *            Número de página actual.
	 * @param codCotejo
	 *            Código de cotejo (código seguro de verificación) para el
	 *            documento.
	 * @param signerList
	 *            Lista de firmantes a mostrar en la banda lateral.
	 *
	 * @throws ISPACException
	 *             Si se produce algún error.
	 */
	public void generateGrayBandImagen(String dateFirma,
			PdfContentByte pdfContentByte, float margen, boolean vh, float x,
			int numberOfPages, int pageActual, String codCotejo, List signerList)
			throws ISPACException;

}
