/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Este servlet se invoca cuando se pulsa Formulario o el icono de modificar de la lista de registros (Solo si el
 * usuario tiene permiso.
 *
 * @author jcebrien
 *
 */

public class OpenFolder extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(OpenFolder.class);
    private static final String INTERROGACION = "?";
    private static final String IGUAL = "=";
    private static final String AMPERSAN = "&";
    private static final String PUNTO_COMA = ";";
    private static final String BARRA = "|";

    public static long folderPId = 1;

    private BookUseCase bookUseCase = null;
    private TransformerFactory factory = null;

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
        factory = TransformerFactory.newInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doWork(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doWork(request, response);
    }



    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/html; charset=UTF-8");

        // identificador de archivo de usuario.
        Integer archiveId = RequestUtils.parseRequestParameterAsInteger(request, "ArchiveId");
        // Identificador de registro a copiar.
        Integer archivePId = RequestUtils.parseRequestParameterAsInteger(request, "ArchivePId");
        // Identificador de carpeta.
        Integer folderId = RequestUtils.parseRequestParameterAsInteger(request, "FolderId");
        // Identificador de la carpeta de consulta.
        Integer fdrQryPId = RequestUtils.parseRequestParameterAsInteger(request, "FdrQryPId");
        Integer copyFdr = RequestUtils.parseRequestParameterAsInteger(request, "CopyFdr");
        // Identificador de la carpeta de consulta.
        Integer row = RequestUtils.parseRequestParameterAsInteger(request, "Row");
        // Formulario o no.
        Boolean form = RequestUtils.parseRequestParameterAsBoolean(request, "Form");
        //Saber si se abre una carpeta desde la distribución.
        Boolean openFolderDtr = RequestUtils.parseRequestParameterAsBoolean(request,
                "OpenFolderDtr",
                new Boolean(false));

        // para saber si se abre la carpeta desde distribución en modo edición
		Boolean openEditDistr = RequestUtils.parseRequestParameterAsBoolean(
				request, "OpenEditDistr", new Boolean(false));

        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        session.setAttribute(J_OPENFOLDER_FORM, form);
        PrintWriter writer = response.getWriter();

        try {
		//comprobamos la visualización del registro
		openFolderDtr = valiteOpenEditDistri(archiveId, folderId, openFolderDtr, openEditDistr,
					useCaseConf);

            if (form.booleanValue() || openFolderDtr.booleanValue()) {
                // desde boton formulario
                Document xmlDocument = bookUseCase.getBookTree(useCaseConf,
                        archiveId,
                        true,
                        folderPId++,
                        folderId.intValue(),
                        row.intValue(),
                        getBeforeEncondedResponseURL(request, response),
                        openFolderDtr.booleanValue(),
                        useCaseConf.getLocale());

                String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_FRMT_RELATIVE_PATH);
                StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                        new FileInputStream(xslPath))));
                Templates cachedXSLT = factory.newTemplates(s);
                Transformer transformer = cachedXSLT.newTransformer();
                DocumentSource source = new DocumentSource(xmlDocument);

                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);
            } else if (!form.booleanValue() && !openFolderDtr.booleanValue()){
                // desde icono modificar
                boolean bIsOpened = false;
                boolean readOnly = false;
                String archFolder = (String) session.getAttribute(Keys.J_STRCARPETA);

                if(!form.booleanValue() && !openFolderDtr.booleanValue()){
                    bIsOpened = getIsOpened(session, archiveId, folderId, archFolder);

                    if (!bIsOpened) {
                        bIsOpened = !(bookUseCase.lockFolder(useCaseConf, archiveId, folderId.intValue()));
                    }
                }

                readOnly = (form.booleanValue() || bIsOpened || openFolderDtr.booleanValue());

                //debemos comprobar si el registro esta cerrado
        		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
        				archiveId, folderId.intValue(), useCaseConf.getLocale(), useCaseConf
        						.getEntidadId());
        		String estado = axsf.getAttributeValueAsString(AxSf.FLD6_FIELD);
			boolean folderCloseOrCanceled = bookUseCase.validateStateFolderIfClosedOrCancel(estado);

                if (!readOnly && !folderCloseOrCanceled) {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(archiveId.toString());
                    buffer.append(BARRA);
                    buffer.append(folderId.toString());
                    buffer.append(PUNTO_COMA);
                    if (archFolder != null) {
                        buffer.insert(0, archFolder);
                    }
                    session.setAttribute(Keys.J_STRCARPETA, buffer.toString());
                }

                Document xmlDocument = bookUseCase.getBookTree(useCaseConf,
                        archiveId,
                        bIsOpened,
                        folderPId,
                        folderId.intValue(),
                        row.intValue(),
                        getBeforeEncondedResponseURL(request, response),
                        openFolderDtr.booleanValue(),
                        useCaseConf.getLocale());

                String xslPath = ContextUtil.getRealPath(session.getServletContext(), XSL_FRMT_RELATIVE_PATH);
                StreamSource s = new StreamSource(new InputStreamReader(new BufferedInputStream(
                        new FileInputStream(xslPath))));
                Templates cachedXSLT = factory.newTemplates(s);
                Transformer transformer = cachedXSLT.newTransformer();
                DocumentSource source = new DocumentSource(xmlDocument);

                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);

                session.setAttribute(Keys.J_REGISTER, folderId);
                session.setAttribute(Keys.J_BOOK, archiveId);
                session.setAttribute(Keys.J_ROW, row);
            }
        } catch (ValidationException e) {
            _logger.fatal("Error de validacion", e);
            writer.write(Keys.ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (BookException e) {
            _logger.fatal("Error en el libro", e);
            writer.write(Keys.ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptError(writer, e);
        } catch (SessionException e) {
            _logger.fatal("Error en la sesion", e);
            ResponseUtils.generateJavaScriptErrorSessionExpiredOpenFolder(writer, e, idioma, numIdioma, form);
            //ResponseUtils.generateJavaScriptError(response, e);
        } catch (TransformerConfigurationException e) {
            _logger.fatal("Error al abrir el registro", e);
            writer.write(ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (TransformerFactoryConfigurationError e) {
            _logger.fatal("Error al abrir el registro", e);
            writer.write(ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (TransformerException e) {
            _logger.fatal("Error al abrir el registro", e);
            writer.write(ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
        } catch (Exception e) {
            _logger.fatal("Error al abrir el registro", e);
            writer.write(Keys.ACTIVATE_OPEN_FOLDER);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_FDRQRY_OBJ));
        }
    }

	/**
	 * Método que verifica si se esta abriendo el registro desde la bandeja de
	 * distribución de distribuciones aceptadas, si el usuario posee los
	 * permisos el registro se visualiza en modo edición
	 *
	 * @param archiveId - Id del libro
	 * @param folderId - Id del registro
	 * @param openFolderDtr - Variable que indica si se abre desde la bandeja de distribución
	 * @param openEditDistr - Variable que indica si se visualiza desde la bandeja de distribución aceptada
	 * @param useCaseConf - Conf. del usuario
	 *
	 * @return boolean - True - Se abre el registro bloqueado / false - El registro se visualiza en modo edición
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws DistributionException
	 */
	private Boolean valiteOpenEditDistri(Integer archiveId, Integer folderId, Boolean openFolderDtr,
			Boolean openEditDistr, UseCaseConf useCaseConf)
			throws ValidationException, SecurityException, BookException,
			SessionException, DistributionException {

		if (openEditDistr) {
			//obtenemos los permisos del usuario en el libro
			int perms = new BookUseCase().getPermisosLibro(useCaseConf, archiveId);

			// comprobamos si el libro de registro esta abierto antes de seguir
			// con el proceso
			ScrRegstate scrregstate = BookSession.getBook(
					useCaseConf.getSessionID(), archiveId);

			//buscamos el registro por id de registro e id de libro
			int size = FolderSession.getCountRegisterByIdReg(
					useCaseConf.getSessionID(), useCaseConf.getEntidadId(),
					archiveId, folderId);

			//validamos que tenga permisos necesarios para abrirlo en modo edición
			if ((perms >> 2 % 2 != 0)
					&& (scrregstate.getState() != ISicresKeys.BOOK_STATE_CLOSED)
					&& (size != 0)) {
				// se abre en modo lectura no tiene los permisos necesarios
				openFolderDtr = false;
			}
		}

		return openFolderDtr;
	}

    // Este método mete en la cadena archiveId y folderId
    private boolean getIsOpened(HttpSession session, Integer archiveId, Integer folderId, String archFolder) {
        boolean isOpened = false;
        if (archFolder != null && archFolder.length() > 0) {
            StringTokenizer tokens = new StringTokenizer(archFolder, PUNTO_COMA);
            String aux = null;
            String auxArch = null;
            String auxFolder = null;
            while (tokens.hasMoreTokens() && !isOpened) {
                aux = tokens.nextToken();
                auxArch = aux.substring(0, aux.indexOf(BARRA));
                auxFolder = aux.substring(aux.indexOf(BARRA) + 1, aux.length());
                isOpened = (auxArch.equals(archiveId.toString()) && auxFolder.equals(folderId.toString()));
            }
        }

        return isOpened;
    }

    private String getBeforeEncondedResponseURL(HttpServletRequest request, HttpServletResponse response) {
        String query = request.getQueryString();
        query = query.replaceFirst(J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOKTREE_FORM_BUTTON,
                J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOK);
        query = query.replaceFirst(J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOKTREE_UDPATE_BUTTON,
                J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOK);

        String path = request.getServletPath();
        path = path.substring(5, path.length());

        StringBuffer buffer = new StringBuffer();
        buffer.append(path);
        buffer.append(INTERROGACION);
        buffer.append(query);
        buffer.append(AMPERSAN);
        buffer.append(J_OPENFOLDER_TYPE);
        buffer.append(IGUAL);
        buffer.append(J_OPENFOLDER_TYPE_XMLBOOK);
        return response.encodeURL(buffer.toString());
    }
}