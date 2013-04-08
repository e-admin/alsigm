/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.helper.ConfigExtensionFileHelper;
import com.ieci.tecdoc.isicres.desktopweb.utils.IOUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

/**
 * Este servlet se invoca para mostrar los documentos asociados de una carpeta.
 *
 *
 * @author jcebrien
 *
 */
public class FileDownload extends HttpServlet {
	private static Logger _logger = Logger.getLogger(FileDownload.class);
	private BookUseCase bookUseCase = null;

	private static final String PUNTO = ".";
    private static final String PDF = "pdf";
    private static final String XADES ="xades";
    private static final String XSIG = "xsig";

	public void init() throws ServletException {
		super.init();
		bookUseCase = new BookUseCase();
	}

	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doWork(arg0, arg1);
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doWork(arg0, arg1);
	}

	private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // identificador de libro.
        Integer bookId  = RequestUtils.parseRequestParameterAsInteger(request, "BookId");
        // Identificador de registro.
        Integer regId = RequestUtils.parseRequestParameterAsInteger(request, "RegId");
        // Identificador de documento.
        Integer docId = RequestUtils.parseRequestParameterAsInteger(request, "DocId");
        // Identificador de página.
        Integer pageId = RequestUtils.parseRequestParameterAsInteger(request, "PageId");
        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);

        // Nombre del fichero.
        String fileName = RequestUtils.parseRequestParameterAsString(request,"FileName");
        //Obtenemos la extension del fichero bien del nombre del fichero o de BBDD
		String fileExt = FolderFileSession.getExtensionFile(fileName,
				bookId.toString(), regId.intValue(), pageId.intValue(),
				useCaseConf.getEntidadId());

		//si el nombre del fichero no lleva la extension se la añadimos
        if(StringUtils.isBlank(FilenameUtils.getExtension(fileName))){
            //comprobamos que la extension no es nula y no sea la extension por defecto ("-")
			if ((StringUtils.isNotBlank(fileExt))
					&& (!Keys.EXTENSION_DEFECTO.equalsIgnoreCase(fileExt))) {
        		fileName = fileName + PUNTO + fileExt;
        	}
        }

        //asignamos la cabecera/contentType correspondiente
        setResponseByFileExtension(response, fileName, fileExt);

        OutputStream output = response.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(output);

        try {
        	// copy(url.openStream(), response.getOutputStream());
			// IOUtils.copy(new BufferedInputStream(new
			// ByteArrayInputStream(bookUseCase.getFile(useCaseConf, bookId,
			// regId, docId, pageId))), output );
        	byte[] file = bookUseCase.getFile(useCaseConf, bookId, regId,
					docId, pageId);
        	IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(file)),
					output);
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            if (fileExt.equals(XADES) || fileExt.equals(XSIG)){
            	removeHeaders(response, fileName);
            }
            showError (RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION), output);
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            if (fileExt.equals(XADES) || fileExt.equals(XSIG)){
            	removeHeaders(response, fileName);
            }
            showError (e.getMessage(), output);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            if (fileExt.equals(XADES) || fileExt.equals(XSIG)){
            	removeHeaders(response, fileName);
            }
            writer.write(Keys.ACTIVATE_TREE_2);
            ResponseUtils.generateJavaScriptErrorSessionExpired(writer, e, idioma, numIdioma);
        } catch (Exception e) {
            _logger.fatal("Error en la descarga de ficheros", e);
            if (fileExt.equals(XADES) || fileExt.equals(XSIG)){
            	removeHeaders(response, fileName);
            }
            showError (RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_DOWNLOAD_OBJ), output);
        }
    }

    /**
     * Metodo que obtiene la configuracion de las extension y en funcion de ello asigna una respuesta del servlet
     *
     * @param response - HttpServletResponse
     * @param fileName - Nombre del fichero
     * @param fileExt - Extension del fichero
     */
    private void setResponseByFileExtension(HttpServletResponse response, String fileName,
            String fileExt) {

    	//Variable que indica si se debe mostrar el cuadro de dialogo de abrir/guardar el fichero
        boolean showDialogSaveOpen = ConfigExtensionFileHelper.getShowDialogSaveOpenFile(fileExt);

        //seteamos la cabecera
        setHeaderContentTypeResponse(response, showDialogSaveOpen , fileName, fileExt);

     }

    /**
     * Metodo que asigna la Cabecera y el ContentType correspondiente a la respuesta del servlet, segun la configuracion indicada
     *
     * @param response - {@link HttpServletResponse}
     * @param showDialogSaveOpen - Indica si se debe mostrar el cuadro de dialogo de abrir/descargar fichero
     * @param fileName - Nombre del fichero
     * @param fileExt - Extension del fichero
     *
     */
    private void setHeaderContentTypeResponse(HttpServletResponse response, boolean showDialogSaveOpen,
            String fileName, String fileExt) {
        //comprobamos por que metodo debemos ir, si mostrar cuadro de dialogo (abrir/descargar) o mostrar fichero en el navegador
        if (showDialogSaveOpen) {
            //Metodo que muestra el cuadro de dialogo de descargar/abrir fichero
            response.setContentType("application/octet-stream; name=" + fileName);
            response.addHeader("Content-type: application/force-download","filename=" + fileName);
            response.addHeader("Content-type: application/download","filename=" + fileName);
            response.addHeader("Content-disposition","attachment;filename=" + fileName);

            if(_logger.isDebugEnabled()){
            	_logger.debug("Asignación de cabecera para forzar cuadro de diálogo de abrir/guardar fichero");
            }

        }else{
            //Metodo que abre los documentos dentro del navegador
        	if(PDF.equalsIgnoreCase(fileExt)){
                response.setContentType("application/pdf; name=" + fileName);
            } else {
                response.setContentType("application/octet-stream; name=" + fileName);
            }
            response.addHeader("content-disposition", "filename=" + fileName);

            if(_logger.isDebugEnabled()){
            	_logger.debug("Asignación de cabecera para insertar fichero en la pagina html");
            }
        }
    }

    /**
     * Metodo que borra la cabecera de la response
     * @param response - HttpServletResponse
     * @param fileName - Nombre del fichero
     */
	private void removeHeaders(HttpServletResponse response, String fileName) {
		response.reset();
		response.setContentType("application/octet-stream; name=" + fileName);
		response.addHeader("content-disposition", "filename=" + fileName);
	}

    /**
     * Metodo que muestra el error que se pasa como parametro
     *
     * @param message - Mensaje de error
     * @param output - Respuesta con el mensaje de error recibido como parametro
     */
	private void showError(String message, OutputStream output) {
		try {
			byte[] buffer = ResponseUtils.generateJavaScriptLogFileDownLoad(
					message).getBytes("UTF-8");
			IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(
					buffer)), output);
		} catch (IOException e) {
			_logger.fatal("Error en ShowError", e);
		}

	}
}