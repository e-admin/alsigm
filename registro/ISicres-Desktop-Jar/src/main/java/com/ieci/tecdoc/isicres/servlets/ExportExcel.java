/**
 * 
 * @author IECISA
 * 
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * Exporta a Excel una lista de Resultados de Búsqueda.
 * 
 * @author IECISA
 * 
 */
public class ExportExcel extends HttpServlet implements Keys {
	private static final long serialVersionUID = 1L;
	private static Logger _logger = Logger.getLogger(TblText.class);
	private BookUseCase bookUseCase = null;

	private static final String FILE_DOWNLOAD_TOKEN = "fileDownloadToken";
	
	public void init() throws ServletException {
		super.init();
		bookUseCase = new BookUseCase();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	private void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        // Recuperamos el id de libro
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        //Token de descarga
        String fileDownloadToken = RequestUtils.parseRequestParameterAsStringWithEmpty(request,	FILE_DOWNLOAD_TOKEN);
		// Tipo de consulta: Aceptar(TypeSearch=0), BuscarUltimo(TypeSearch=1).
		Integer typeSearch = RequestUtils.parseRequestParameterAsInteger(
				request, "typeSearch", new Integer(0));        
        
        try {
        	//Obtener el XML con los resultados
            Document xmlDocument = null;
            //Si el usuario realizo una busqueda indicando un filtro
            if (typeSearch.equals(Keys.SEARCH_WITH_FILTER)) {
	            xmlDocument = bookUseCase.getTableResults(useCaseConf,
	                    bookID,
	                    com.ieci.tecdoc.common.isicres.Keys.QUERY_ALL, XML_FLD_UPPER_TEXT + 1);
            } else {
            	//Si el usuario busco el ultimo registro
				xmlDocument = bookUseCase.getLastRegisterForUser(useCaseConf,
						bookID);
            }
    		// Crear un nuevo libro de excel
    		Workbook wb = new HSSFWorkbook();
    		// Crear una nueva hoja de excel
    		Sheet sheet = wb.createSheet(); 
    		//Escribir la cabecera
			WriteHeaders(xmlDocument, sheet);
			//Escribir los registros
			WriteRegisters(xmlDocument, sheet);
			//Ajustar tamaño de celdas
			AutoSizeColumns(xmlDocument, sheet);    		
    		
    		//Devolver el Excel
    		OutputStream out = response.getOutputStream();
    		
    		response.setContentType("application/excel"); 
    		response.setHeader("Content-disposition","attachment; filename=registro.xls"); 	
    		//Establecemos la cookie con el token de finalizada descarga del excel.
            response.addCookie(new Cookie(FILE_DOWNLOAD_TOKEN, fileDownloadToken));
    		
    		wb.write(out);
    		out.close();        	
    		
        } catch (SecurityException e) {
            _logger.fatal("Error de seguridad", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptError(writer , e, useCaseConf.getLocale());
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer , RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptError(writer , e);
        } catch (AttributesException e) {
            _logger.fatal("Error en los atributos", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL);
            ResponseUtils.generateJavaScriptError(writer, e, useCaseConf.getLocale());
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
            //ResponseUtils.generateJavaScriptError(writer, e);
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al cargar la lista", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (Exception e) {
            _logger.fatal("Error al cargar la lista", e);
    		PrintWriter writer = response.getWriter();            
            writer.write(ACTIVATE_SEVERAL_2);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        }

    }
	
	private void WriteHeaders(Document doc, Sheet sheet) {
		List headerList = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		Font font = null;
		int cellCount = 0; 
		
		font = sheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = sheet.getWorkbook().createCellStyle();
		style.setFont(font);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		
		headerList = doc.selectNodes("//invesDoc/TableInfo/TableFormat/Columns/Column");
		
		row = sheet.createRow(0); //Primera fila del excel, hay que tenerlo en cuenta a la hora de pintar los datos.
		for (Iterator iter = headerList.iterator(); iter.hasNext(); ) {
			String headerName = ((Node)iter.next()).selectSingleNode("Title").getText();
			cell = row.createCell(cellCount);
			cell.setCellValue(headerName);
			cell.setCellStyle(style);
			cellCount++;
		}
	}
	
	private void WriteRegisters(Document doc, Sheet sheet) {
		List folderList = null;
		List fieldList = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		int rowCount = 1; //Hay que tener en cuenta la cabecera, asi que debemos empezar a escribir los datos en la fila 2
		int cellCount = 0;
		
		style = sheet.getWorkbook().createCellStyle();
		//style.setDataFormat((short)23);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		
		folderList = doc.selectNodes("//invesDoc/TableInfo/TableText/FoldersInfo/FolderInfo");
		for (Iterator iterRow = folderList.iterator(); iterRow.hasNext(); ) {
			row = sheet.createRow(rowCount); 
			fieldList = ((Node)iterRow.next()).selectNodes("Values/Text");
			cellCount = 0; //Inicializamos el contador de celda, para empezar a escribir al principio de la linea.
			for (Iterator iterCell = fieldList.iterator(); iterCell.hasNext(); ) {
				cell = row.createCell(cellCount);
				cell.setCellValue(((Node)iterCell.next()).getText());
				if (!iterRow.hasNext()) cell.setCellStyle(style);
				cellCount++;
			}
			rowCount++;
		}		
	}
	
	private void AutoSizeColumns(Document doc, Sheet sheet) {
		int colNum = 0;
		
		colNum = doc.selectNodes("//invesDoc/TableInfo/TableFormat/Columns/Column").size();
		for (int i = 0; i < colNum; i++) {
			sheet.autoSizeColumn(i);
		}
	}
}