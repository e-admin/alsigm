package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl.helper.CompulsaJustificanteHelper;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl.helper.CompulsaJustificanteLocatorHelper;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo.ISicresCompulsaJustificanteDatosEspecificosVO;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo.ISicresCompulsaJustificanteVO;
import es.ieci.tecdoc.isicres.api.compulsa.justificante.exception.ISicresCompulsaJustificanteException;

/**
 * Clase para la realizacion de la compulsa de un documento PDF.
 * @author IECISA
 *
 */
public class CompulsaJustificanteManager {

	private static final Logger logger = Logger
	.getLogger(CompulsaJustificanteManager.class);
	
	/**
	 * 
	 * @param compulsaJustificanteVO
	 */
	public void generateJustificante(ISicresCompulsaJustificanteVO compulsaJustificanteVO) {
		PdfReader pdfOriginalReader;
		PdfWriter pdfCompulsaWriter;
		Document pdfCompulsaDocument;
		
		try {
			//Cargar el PDF Original
			pdfOriginalReader = new PdfReader(compulsaJustificanteVO.getPdfOriginalStream());
			//Cargar el stream de salida para escribir el PDF Compulsado. 
			pdfCompulsaDocument = new Document();
			pdfCompulsaWriter = PdfWriter.getInstance(pdfCompulsaDocument, compulsaJustificanteVO.getPdfCompulsaStream());
			pdfCompulsaDocument.open();

			//Compulsar documento
			generateJustificante(pdfOriginalReader, pdfCompulsaWriter, pdfCompulsaDocument, compulsaJustificanteVO);						
		} catch (IOException e) {
			String msgError = "Error al compulsar documento PDF.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		} catch (DocumentException e) {
			String msgError = "Error al compulsar documento PDF.";
			logger.error(msgError, e);
			throw new ISicresCompulsaJustificanteException(msgError);
		}
	}
	
	private void generateJustificante(PdfReader pdfOriginalReader, PdfWriter pdfCompulsaWriter, 
			Document pdfCompulsaDocument, ISicresCompulsaJustificanteVO compulsaVO) throws DocumentException
	{
		ISicresCompulsaJustificanteDatosEspecificosVO datosEspecificosVO;
		int numPages = pdfOriginalReader.getNumberOfPages();
		Image backgroundImage;
		Image pageImage;
		String dataText;
		String dataTextWithPageNumbers;

		//Obtener los datos especificos para incluir los datos de informacion de la compulsa en el documento PDF.
		//Fuente, tamaño de fuente, etc...
		datosEspecificosVO = CompulsaJustificanteHelper.getInstance().getCompulsaDatosEspecificos(compulsaVO.getLocale());
		
		//Obtener la imagen que servira de fondo para la banda con los datos de informacion de la compulsa
		backgroundImage = CompulsaJustificanteHelper.getInstance().getBackgroundForData(compulsaVO.getFondoPath());
		
		//Obtener el fichero de texto con los datos combinados de la compulsa a partir de la plantilla
		dataText = CompulsaJustificanteHelper.getInstance().getParsedData(compulsaVO);
		
		//Recorrer todas las paginas del documento PDF
		for (int i = 1; i <= numPages; i++) {
			//Obtener una representacion de la pagina del documento original PDF con el hueco para la banda de datos de 
			//informacion de la compulsa.
			pageImage = CompulsaJustificanteHelper.getInstance()
				.getPageWithRoomForData(pdfCompulsaWriter.getImportedPage(pdfOriginalReader, i), datosEspecificosVO);
			//Añadir la nueva pagina al documento PDF de salida
			pdfCompulsaDocument.add(pageImage);
			//Añadir a la pagina actual del documento PDF la imagen que servira de fondo para los datos
			CompulsaJustificanteHelper.getInstance().addBackgroundImageForData(pdfCompulsaDocument, backgroundImage, datosEspecificosVO);
			//Obtener el texto de datos con la informacion de la compulsa, añadiendo el numero total de paginas y la pagina actual.
			dataTextWithPageNumbers = CompulsaJustificanteHelper.getInstance().getParsedPageNumbers(dataText, numPages, i);
			//Añadir el texto al documento PDF
			CompulsaJustificanteHelper.getInstance()
				.addDataTextToDocument(pdfCompulsaWriter.getDirectContent(), datosEspecificosVO, dataTextWithPageNumbers);
			//Añadir nueva pagina al documento PDF
			pdfCompulsaDocument.newPage();
		}
		
		//Cerrar el documento
		pdfCompulsaDocument.close();
	}
	

	
	
}
