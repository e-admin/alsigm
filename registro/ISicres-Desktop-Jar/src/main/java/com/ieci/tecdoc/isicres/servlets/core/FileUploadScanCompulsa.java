package com.ieci.tecdoc.isicres.servlets.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

public class FileUploadScanCompulsa extends HttpServlet implements Keys {

	private static Logger _logger = Logger
			.getLogger(FileUploadScanCompulsa.class);

	private BookUseCase bookUseCase = null;

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

		// Identificador de libro.
		Integer bookId = RequestUtils.parseRequestParameterAsInteger(request,
				"BookId");
		// Identificador de carpeta.
		Integer folderId = RequestUtils.parseRequestParameterAsInteger(request,
				"RegId");
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession(false);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador de sesión para este usuario en el servidor de
		// aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		Locale locale = new Locale("es", "ES");
		if (useCaseConf.getLocale() != null) {
			locale = useCaseConf.getLocale();
		}

		PrintWriter writer = response.getWriter();

		try {

			// CompulsaVO compulsaVO = new CompulsaVO();
			ISicresCreateCompulsaVO compulsaVO = new ISicresCreateCompulsaVO();
			compulsaVO.setBookId(bookId);
			compulsaVO.setFolderId(folderId);
			compulsaVO.setSessionID(useCaseConf.getSessionID());
			compulsaVO.setEntidad(useCaseConf.getEntidadId());
			compulsaVO.setLocale(locale);

			compulsaVO.setMargen(new Float(RBUtil.getInstance(locale)
					.getProperty(Keys.I18N_PDF_WATER_MARK_POSITION_X))
					.floatValue());
			compulsaVO.setPositionY(new Float(RBUtil.getInstance(locale)
					.getProperty(Keys.I18N_PDF_WATER_MARK_POSITION_Y))
					.floatValue());
			compulsaVO.setFont(RBUtil.getInstance(locale).getProperty(
					Keys.I18N_PDF_WATER_MARK_FONT));
			compulsaVO.setEncoding(RBUtil.getInstance(locale).getProperty(
					Keys.I18N_PDF_WATER_MARK_ENCODING));
			compulsaVO.setFontSize(new Float(RBUtil.getInstance(locale)
					.getProperty(Keys.I18N_PDF_WATER_MARK_SIZE)).floatValue());
			compulsaVO.setBand(new Integer(RBUtil.getInstance(locale)
					.getProperty(Keys.I18N_PDF_WATER_BAND_VH)).intValue());
			compulsaVO.setBandSize(new Integer(RBUtil.getInstance(locale)
					.getProperty(Keys.I18N_PDF_WATER_BAND_SIZE)).intValue());

			compulsaVO.setFileItems(getFileItems(request));

			compulsaVO.setBeginPath(getBeginPath(session));


			if (Configurator.getInstance().getPropertyBoolean(
					ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
				compulsaVO
						.setTempPath(ContextUtil.getRealPath(
								session.getServletContext(),
								Configurator
										.getInstance()
										.getProperty(
												ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)));
			} else {
				compulsaVO.setTempPath(Configurator.getInstance().getProperty(
						ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));
			}

			compulsaVO.setFondoPath(getFondoPath(session));
			compulsaVO.setDatosPath(getDatosPath(session));

			compulsaVO.setDateDataBaseServer(bookUseCase
					.getDBDateServer(useCaseConf));

			AxSf axsf = bookUseCase.getBookFolder(useCaseConf, bookId, folderId
					.intValue());
			compulsaVO.setFolderNumber(axsf.getAttributeValueAsString("fld1"));

			bookUseCase.saveCompulFiles(compulsaVO);
			ResponseUtils.generateJavaScriptCompulsa(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_BOOK_COMPUL_SATISFACTORILY_FILE));

		} catch (FileUploadException e) {
			_logger.fatal("Error compulsando ficheros", e);
			ResponseUtils.generateJavaScriptCompulsa(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_COMPUL_UPLOAD_OBJ));
		} catch (Exception e) {
			_logger.fatal("Error compulsando ficheros", e);
			ResponseUtils.generateJavaScriptCompulsa(writer, RBUtil
					.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_COMPUL_UPLOAD_OBJ));
		}

	}

	private String getBeginPath(HttpSession session) throws Exception {
		String beginPath = null;
		if (Configurator.getInstance().getPropertyBoolean(
				ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
			beginPath = ContextUtil
					.getRealPath(
							session.getServletContext(),
							Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));
		} else {
			beginPath = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME);
		}
		return beginPath;

	}

	private String getFondoPath(HttpSession session) throws Exception {
		String fondoPath = null;
		if (Configurator.getInstance().getPropertyBoolean(
				ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_COMPULSA_FONDO_PATH)) {
			fondoPath = ContextUtil.getRealPath(session.getServletContext(),
					Configurator.getInstance().getProperty(
							ConfigurationKeys.KEY_DESKTOP_COMPULSA_FONDO_PATH));

		} else {
			fondoPath = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_COMPULSA_FONDO_PATH);
		}
		return fondoPath;

	}

	private String getDatosPath(HttpSession session) throws Exception {
		String datosPath = null;
		if (Configurator.getInstance().getPropertyBoolean(
				ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_COMPULSA_DATOS_PATH)) {
			datosPath = ContextUtil.getRealPath(session.getServletContext(),
					Configurator.getInstance().getProperty(
							ConfigurationKeys.KEY_DESKTOP_COMPULSA_DATOS_PATH));
		} else {
			datosPath = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_COMPULSA_DATOS_PATH);
		}
		return datosPath;

	}

	/**
	 * método que genera el localizador del documento compulsado
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @param folderId
	 * @param flushFdrFileC
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	// private String generateLocator(UseCaseConf useCaseConf, Integer bookId,
	// Integer folderId, FlushFdrFile flushFdrFileC)
	// throws ValidationException, BookException, SessionException {
	//
	// // obtenemos la informacion del registro
	// AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
	// bookId, folderId.intValue(), useCaseConf.getLocale(),
	// useCaseConf.getEntidadId());
	//
	// // fecha de la compulsa y la formateamos al formato yyyyMMddHHmmss
	// Date date = bookUseCase.getDBDateServer(useCaseConf);
	// SimpleDateFormat FORMATTER = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss.0");
	// String dateCompul = FORMATTER.format(date);
	//
	// // obtenemos el nombre del fichero compulsado y borramos los guiones
	// // bajos del mismo
	// String nameIMGCompul = flushFdrFileC.getFileNameLog().substring(0,
	// flushFdrFileC.getFileNameLog().lastIndexOf(".pdf"));
	// nameIMGCompul = nameIMGCompul.replaceAll(GUIONBAJO, "");
	//
	// // retornamos el localizador
	// return (axsf.getAttributeValueAsString("fld1") + GUION + bookId + GUION
	// + dateCompul + GUION + useCaseConf.getEntidadId() + GUION +
	// nameIMGCompul);
	// }

	private List getFileItems(HttpServletRequest request)
			throws FileUploadException {
		Long maxUploadFileSize = new Long(Configurator.getInstance()
				.getProperty(ConfigurationKeys.KEY_DESKTOP_MAXUPLOADFILESIZE));

		boolean isMultipart = FileUpload.isMultipartContent(request);
		if (_logger.isDebugEnabled()) {
			_logger.debug("Content Type =" + request.getContentType());
		}

		DiskFileUpload fu = new DiskFileUpload();
		// If file size exceeds, a FileUploadException will be thrown
		fu.setSizeMax(maxUploadFileSize.longValue());
		List fileItems = fu.parseRequest(request);

		return fileItems;
	}

}
