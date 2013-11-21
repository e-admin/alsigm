package com.ieci.tecdoc.isicres.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.IdiomaUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * Filtro encargado de validar los datos que llegan en la request (ID de Libro e
 * ID de Registro) y validar que el usuario tiene acceso
 *
 * @author IECISA
 *
 */
public class URLCheckFilter implements Filter{

	private static Logger _logger = Logger.getLogger(URLCheckFilter.class);

	private static String ARCHIVEID = "ArchiveId";
	private static String BOOKID = "BookId";

	private static String FOLDERID = "FolderId";
	private static String REGID = "RegId";

	//variable almacenada en session con valor ID del libro + "_" + ID del registro al que se intenta acceder
	private static String BOOK_REG_VALIDADO = "idBookIdRegValidado";

	//variable almacenada en session con valor ID del libro + "_" + ID del registro a copiar
	private static String BOOK_REG_COPY_VALIDADO = "idBookIdRegCopyValidado";

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

		//ID del libro almacenado en la session
		Integer bookIDSession = (Integer) session.getAttribute(Keys.J_BOOK);
    	//ID del registro almacenado en sesion
    	Integer regIdSession = (Integer) session.getAttribute(Keys.J_REGISTER);

        // identificador del libro que llega por la URL (request).
        Integer bookIDRequest = getBookIDByURL(request);
		//ID del Registro que llega por la URL (request).
        Integer regIdRequest = getIdRegisterByURL(request);

        //ID del Registro que se intenta copiar -- Variable util SOLO en el caso de COPIAR REGISTROS
        Integer copyFdr = RequestUtils.parseRequestParameterAsInteger(request, "CopyFdr");

		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(Keys.J_USECASECONF);
		try {
			//validamos que el usuario tiene session creada
			if(useCaseConf != null){
				//obtenemos la informacion de la url de origen de la peticion
				String urlOrigen = request.getRequestURI();

				//validamos el libro
		        validateBook(urlOrigen,	bookIDSession, bookIDRequest, useCaseConf);

				//obtenemos el id del libro con el que validaremos el registro
				Integer bookIdAux = getBookIDJob(bookIDSession, bookIDRequest);

		        //validamos el registro
		        validateRegister(session, bookIdAux, regIdRequest, regIdSession, useCaseConf);

		        //Validamos si se copia un registro, que el usuario tenga acceso a los datos de dicho registro
		        validateRegIsCopy(session, copyFdr, useCaseConf, bookIdAux);
			}

			//si todo esta correcto continua la ejecucion de la aplicacion
			chain.doFilter(req, res);

		} catch (Exception e) {
			// la validación de datos no ha sido correcta, por tanto no se puede
			// acceder a los datos, se mostrara un alert
			// en pantalla y se cerrara la ventana a la que se intenta acceder
			_logger.error("Se ha producido un error durante la ejecución del filtro URLCheckFilter.",e);
			getMessageError(request, response, session);
		}
	}



	/**
	 * Metodo que valida el proceso de copiado de registros, para que se tenga permisos de acceso en el registro a copiar
	 *
	 * @param copyFdr - ID del registro a copiar
	 * @param useCaseConf - Config. del usuario
	 * @param bookIdAux - ID del libro
	 *
	 * @throws IOException
	 * @throws ServletException
	 * @throws TecDocException
	 */
	private void validateRegIsCopy(HttpSession session, Integer copyFdr, UseCaseConf useCaseConf,
			Integer bookIdAux) throws IOException, ServletException,
			TecDocException {
		//validamos si estamos en un proceso de copiado, si es asi la variable copyFdr viene distinta de 0
		if((copyFdr != null) && (copyFdr!=0)){
			try {
	        	//obtenemos la cadena almacenada en sesion con la informacion del libro y registro validado con anterioridad
			String bookRegCopyValidado = (String) session.getAttribute(BOOK_REG_COPY_VALIDADO);
	    		//componemos una cadena auxiliar con id del libro + "_" + id del registro
	    		String auxBookRegistroCopy = bookIdAux + Keys.GUION_BAJO + copyFdr;

				// comparamos las cadenas de id libro + id registro para validar
				// si la informacion almacenada en sesion es igual a la que
				// llega por la request y sino validar los permisos sobre el registro
	        	if((!auxBookRegistroCopy.equals(bookRegCopyValidado))){
				//validamos si el id del registro que se recibe, el usuario tiene acceso
				validateInfoRegister(useCaseConf, bookIdAux, copyFdr);

					//si la validacion fue correcta almacenamos el valor en session
					session.setAttribute(BOOK_REG_COPY_VALIDADO, auxBookRegistroCopy);
	        	}
			} catch (TecDocException e) {
				if(_logger.isDebugEnabled()){
					_logger.debug("Se ha producido un error al validar el registro a copiar ["
							+ copyFdr
							+ "] en el libro ["
							+ bookIdAux
							+ "] para el usuario [" + useCaseConf.getUserName() + "]");
				}
				//se produce un error al intentar obtener los datos del registro, con lo que se para la operativa
				_logger.error("Se ha producido un error al validar los datos en validateRegIsCopy: ", e);
				throw e;
			}
		}
	}

	/**
	 * Metodo que obtiene el id del libro en el que validaremos los registros,
	 * comprueba si el id del libro llega por la request o trabajamos con el
	 * almacenado en sesion
	 *
	 * @param bookIDSession
	 *            - ID del libro almacenado en session
	 * @param bookIDRequest
	 *            - ID del libro que recibimos en la request
	 *
	 * @return Integer - ID del libro en el que validaremos el registro
	 */
	private Integer getBookIDJob(Integer bookIDSession, Integer bookIDRequest) {
		Integer result = null;

		if(bookIDRequest != null){
			//si el id del libro nos llega por la request trabajaremos con este valor
			result = bookIDRequest;
		}else{
			if(bookIDSession != null){
				//sino trabajaremos con id del libro almacenado en session
				result = bookIDSession;
			}
		}
		return result;
	}

	/**
	 * Metodo que comprueba la informacion del libro y si de esta ha de ser validado
	 *
	 * @param urlOrigen - String con la url de origen de la peticion
	 * @param bookIDSession - ID del libro almacenado en session
	 * @param bookIDRequest - ID del libro que recibimos por la request
	 * @param useCaseConf
	 *
	 * @throws Exception
	 */
	private void validateBook(String urlOrigen,
			Integer bookIDSession, Integer bookIDRequest, UseCaseConf useCaseConf)
			throws Exception {

		if(_logger.isDebugEnabled()){
			_logger.debug("validateBook - urlOrigen: [" + urlOrigen
					+ "] bookIDSession [" + bookIDSession + "] bookIDRequest ["
					+ bookIDRequest + "]");
		}

		//si el archivador nos llega por url y la url es distinta a la pantalla de seleccion de libros
		if((bookIDRequest != null) && (bookIDRequest != 0) && (urlDataRequestValidate(urlOrigen))){
			//validamos el Libro
			validateIDBookIsValid(bookIDSession, bookIDRequest, useCaseConf);
		}
	}

	/**
	 * Metodo que indica si la url pasada como parametro debemos validar los datos de la request
	 *
	 * @param url - URL de origen de la peticion
	 *
	 * @return boolean: TRUE - los datos deberan ser validados; FALSE - los datos de la request no se validan
	 */
	private boolean urlDataRequestValidate(String url){
		boolean result = false;

		//si corresponde a una pantalla diferente a la seleccion de libros los datos deberan ser validados
		if(url.indexOf("/qryfmtmp") == -1){
			result = true;
		}

		return result;
	}

	/**
	 * Metodo que valida el id del Registro sea accesible para el usuario
	 *
	 * @param session
	 * @param bookId - ID del libro
	 * @param regIdRequest - ID del registro recibido por la request
	 * @param regIdSession - ID del registro almacenado en session
	 * @param useCaseConf
	 *
	 * @throws IOException
	 * @throws ServletException
	 * @throws TecDocException
	 */
	private void validateRegister(HttpSession session, Integer bookId, Integer regIdRequest, Integer regIdSession, UseCaseConf useCaseConf)
			throws IOException, ServletException, TecDocException {

		if(_logger.isDebugEnabled()){
			_logger.debug("validateRegister - Bookid [" + bookId
					+ "] regIdRequest [" + regIdRequest + "] regIdSession ["
					+ regIdSession + "]");
		}

        //si el id del registro es distinto de nulo y de -1 comprobamos si el dato es valido
        if((regIdRequest!= null) && (regIdRequest!=-1)){
        	//obtenemos la cadena almacenada en sesion con la informacion del libro y registro validado con anterioridad
		String bookRegValidadoInSession = (String) session.getAttribute(BOOK_REG_VALIDADO);

        	//validamos el registro recibido en la request respecto al almacenado en session
        	validateRegisterRequestWithSession(useCaseConf, bookId, regIdRequest, regIdSession, bookRegValidadoInSession);

    		//si no se produce ningun error, el id del registro es correcto lo almacenamos en session
    		session.setAttribute(Keys.J_REGISTER, regIdRequest);

    		//almacenamos en la session la cadena con la información de libro y del registro: "idBook_idReg"
		session.setAttribute(BOOK_REG_VALIDADO, (bookId + Keys.GUION_BAJO + regIdRequest));
        }
	}

	/**
	 * Metodo que valida el ID del libro recibido tanto por session como por la request
	 *
	 * @param bookIDSession - ID del Libro almacenado en la session
	 * @param bookIDRequest - ID del libro recibio por la request
	 * @param useCaseConf
	 *
	 * @throws Exception
	 */
	private void validateIDBookIsValid(Integer bookIDSession, Integer bookIDRequest, UseCaseConf useCaseConf)
			throws Exception {

		if(_logger.isDebugEnabled()){
			_logger.debug("validateIDBookIsValid");
		}

		//si id de libro de sesion y el id del libro son distintos de nulo
		if((bookIDSession != null) && (bookIDRequest != null)){
			//comparamos los id de los libros de la session y de la request
			compareIdBookSessionAndBookRequest(bookIDSession, bookIDRequest, useCaseConf);
		}else{
			// si el bookID de sesion es nulo y el id del libro que
			// recibimos por la request es distinto de nulo, validamos el id del libro
			if(((bookIDRequest != null) && (bookIDRequest != 0)) && (bookIDSession == null)){
				//validamos el libro respecto a la cache
				validateIDBookInCache(bookIDRequest, useCaseConf);
			}
		}
	}

	/**
	 * Metodo que valida el ID del libro de la request respecto al ID de libro almacenado en la session
	 *
	 * @param bookIDSession - ID del libro almacenado en session
	 * @param bookIDRequest - ID del libro obtenido de la request
	 * @param useCaseConf
	 * @throws Exception
	 */
	private void compareIdBookSessionAndBookRequest(Integer bookIDSession, Integer bookIDRequest, UseCaseConf useCaseConf)
			throws Exception {

		if(_logger.isDebugEnabled()){
			_logger.debug("compareIdBookSessionAndBookRequest");
		}

		//comprobamos si los id de libro son distintos
		if (!bookIDSession.equals(bookIDRequest)) {
			//Si son distintos, validamos el libro respecto a la cache
			validateIDBookInCache(bookIDRequest, useCaseConf);
		}
	}

	/**
	 * Metodo que comprueba si el ID del libro esta cacheado, si el usuario
	 * tiene acceso al libro la ejecucion continua sino
	 * elevamos una excepcion {@link BookException}
	 *
	 * @param bookIDRequest - ID del libro a validar
	 * @param useCaseConf
	 * @throws Exception
	 */
	private void validateIDBookInCache(Integer bookIDRequest, UseCaseConf useCaseConf) throws Exception {
		try {
			if(_logger.isDebugEnabled()){
				_logger.debug("validateIDBookInCache");
			}

			// Recuperamos la cache
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					useCaseConf.getSessionID());
			// Es necesario tener el libro abierto para consultar su

			// si no se encuentra en la cacheBag es que no se ha podido abrir
			if (!cacheBag.containsKey(bookIDRequest)) {
				BookSession.openBook(useCaseConf.getSessionID(), bookIDRequest,
						useCaseConf.getEntidadId());
			}
			
			if (!cacheBag.containsKey(bookIDRequest)) {
				//el libro no esta cacheado para el usuario con lo que no tiene acceso
				throw new BookException(BookException.ERROR_BOOK_NOTFOUND);
			}
			
		} catch (Exception e) {
			// se produce un error al intentar obtener los datos del registro,
			// con lo que se para la operativa
			_logger.error("Se ha producido un error al validar los datos en validateIDBookCache ", e);
			throw e;
		}
	}


	/**
	 * Metodo que valida el id del registro recibido en la request respecto al almacenado en session
	 *
	 * @param useCaseConf
	 * @param bookID - ID del libro
	 * @param regIdRequest - ID del registro recibido por la request
	 * @param regIdSession - ID del registro almacenado en session
	 * @param regValidadoInSession - Cadena con id del libro guion bajo id del registro validado y almacenado en la session
	 *
	 * @throws IOException
	 * @throws ServletException
	 * @throws TecDocException
	 */
	private void validateRegisterRequestWithSession(UseCaseConf useCaseConf,
			Integer bookID, Integer regIdRequest, Integer regIdSession, String bookRegValidadoInSession)
			throws IOException, ServletException, TecDocException {

		//componemos una cadena auxiliar con id del libro + "_" + id del registro
		String auxBookRegistro = bookID + Keys.GUION_BAJO + regIdRequest;

		if(_logger.isDebugEnabled()){
			_logger.debug("validateRegisterRequestWithSession - auxBookRegistro ["
					+ auxBookRegistro
					+ "] bookRegValidadoInSession ["
					+ bookRegValidadoInSession + "]");
		}

		// comprobamos si hay id de registro en session, si este este a cambiado
		// respecto al que recibimos en la request o si la cadena con el
		// idBook_IdReg es diferente a la que debemos tratar
		if((regIdSession== null) || (!regIdSession.equals(regIdRequest)) || (!auxBookRegistro.equals(bookRegValidadoInSession))){
			try {
				//validamos si el id del registro que se recibe el usuario tiene acceso
				validateInfoRegister(useCaseConf, bookID, regIdRequest);

			} catch (TecDocException e) {
				//se produce un error al intentar obtener los datos del registro, con lo que se para la operativa
				_logger.error("Se ha producido un error al validar los datos en validateRegisterRequestWithSession: ", e);
				throw e;
			}
		}
	}

	/**
	 *
	 * Metodo que valida la información del registro
	 *
	 * @param useCaseConf
	 * @param bookID - ID del libro
	 * @param regIdRequest - ID del registro
	 *
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws IOException
	 * @throws ServletException
	 * @throws DistributionException
	 */
	private void validateInfoRegister(UseCaseConf useCaseConf, Integer bookID, Integer regIdRequest)
			throws TecDocException,	IOException, ServletException {

		if(_logger.isDebugEnabled()){
			_logger.debug("validateInfoRegister");
		}

		//consultamos si el usuario tiene acceso al regstro, si se encuentra nos devolvera mayor de 0
		int size = FolderSession.getCountRegisterByIdReg(
				useCaseConf.getSessionID(), useCaseConf.getEntidadId(), bookID,
				regIdRequest);

		//si el contador es menor o igual a 0, el usuario no tiene acceso directo al registro
		if(size<=0){
			// el usuario no tiene acceso directo al registro, con lo que comprobamos si
			// el registro le ha llegado mediante una distribucion
			validateIDRegisterByDistribution(useCaseConf, bookID, regIdRequest);
		}
	}

	/**
	 * Metodo que valida si el registro ha sido distribuido al usuario, de esta
	 * forma se comprueba si tiene acceso al registro
	 *
	 * @param useCaseConf
	 * @param bookID - ID del libro
	 * @param regIdRequest - ID del registro
	 *
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void validateIDRegisterByDistribution(UseCaseConf useCaseConf, Integer bookID, Integer regIdRequest)
			throws TecDocException, IOException, ServletException {

		if(_logger.isDebugEnabled()){
			_logger.debug("validateIDRegisterByDistribution");
		}

		// obtenemos el contador con el numero de distribuciones en las que se
		// ha visto afectado el registro y el usuario logeado
		int size = DistributionSession.getAllDistributionByRegisterAndUser(useCaseConf
				.getSessionID(), bookID, regIdRequest, useCaseConf.getEntidadId());

		// si el contador es menor o igual a 0 se eleva una excepcion, el
		// usuario no tiene acceso al registro
		if(size<=0){
			// el usuario no tiene acceso al registro indicado
			_logger.error("El usuario no tiene permisos para realizar operaciones sobre el registro con FDRID ["
					+ regIdRequest + "] en el libro [" + bookID + "]");

			throw new BookException(BookException.ERROR_ROW_OUTSIDE);
		}
	}

	/**
	 * Metodo que obtiene el id del libro que llega como parametro por la url (request)
	 *
	 * @param request
	 * @return {@link Integer} - ID del libro
	 */
	private Integer getBookIDByURL(HttpServletRequest request) {
		//obtenemos el id del libro si viene denominado como ArchiveID
		Integer result = RequestUtils.parseRequestParameterAsInteger(request, ARCHIVEID);

		if(result == null){
			//sino se encuentra nada por ArchiveID, intentamos obtenerlo como BookID
			result = RequestUtils.parseRequestParameterAsInteger(request, BOOKID);
		}
		return result;
	}

	/**
	 * Metodo que obtiene el id del registro que llega por url (request)
	 * @param request
	 * @return {@link Integer} - ID del Registro
	 */
	private Integer getIdRegisterByURL(HttpServletRequest request) {
		//comprobamos si el id del registro viene denominado por FolderID
        Integer result = RequestUtils.parseRequestParameterAsInteger(request, FOLDERID);

        if(result == null){
        	//comprobamos si el id del registro viene denominado por RegID
		result = RequestUtils.parseRequestParameterAsInteger(request, REGID);
        }
        // Identificador de carpeta.
		return result;
	}

	/**
	 * Metodo que compone el mensaje de error correspondiente cuando los datos que recibimos por url no son correctos
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	private void getMessageError(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {

		if(_logger.isDebugEnabled()){
			_logger.debug("getMessageError");
		}

		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		if(idioma == null){
			idioma = IdiomaUtils.getInstance().getIdioma(request);
		}
		// Numero del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		if(numIdioma == null){
			numIdioma = IdiomaUtils.getInstance().getNumIdioma(request);
		}

		//seteamos los datos de la cabecera de respuesta
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();

		//generamos el error a mostrar por pantalla "Error en la validacion de los datos"
		ResponseUtils
				.generateJavaScriptLogSessionExpiredProvCityDir(writer,
						new ValidationException(
								ValidationException.ERROR_VALIDATION_DATA)
								.getMessage());
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}


}
