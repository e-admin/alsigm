package com.ieci.tecdoc.isicres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;


/**
 * Este servlet se invoca cuando se pulsa el botón de cerrar registros desde la ventana de
 * Relaciones de un libro
 */
public class CloseReg extends HttpServlet implements Keys {


	private static Logger _logger = Logger.getLogger(CloseReg.class);

	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm");


	private static final int OPTION_TYPE_RMD = 4; // Relaciones por destino
	private static final String OPTION_TYPE_STRING_RMD = "RMD";

    private static final int OPTION_TYPE_RMO = 5; // Relaciones por origen
    private static final String OPTION_TYPE_STRING_RMO = "RMO";

    private static final String HORA_CERO = " 00:00";
    private static final String HORA_V3 = " 23:59";

    private BookUseCase bookUseCase = null;

    public void init() throws ServletException {
        super.init();

        bookUseCase = new BookUseCase();
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

    	PrintWriter writer = response.getWriter();
    	int nofRegClosed = 0;

    	// opción seleccionada de tipo de informe de relación
        int opcion = 0;
        String opcionStr = RequestUtils.parseRequestParameterAsString(request, "Opcion");
        if (StringUtils.isNotEmpty(opcionStr)) {
        	opcion = mappingOptionToInt(opcionStr);
        }

        String fecha = RequestUtils.parseRequestParameterAsString(request, "Fecha");
        String hora1 = RequestUtils.parseRequestParameterAsString(request, "Hora1");
        String hora2 = RequestUtils.parseRequestParameterAsString(request, "Hora2");
        String unidad = RequestUtils.parseRequestParameterAsString(request, "Unit");
        String fechaDesde = RequestUtils.parseRequestParameterAsString(request, "FechaDesde");
        String fechaHasta = RequestUtils.parseRequestParameterAsString(request, "FechaHasta");

        // Obtenemos la sesión asociada al usuario.
        HttpSession session = request.getSession();

        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);

        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);

        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(J_USECASECONF);
        Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);

        try {
           	Date beginDate = null;
           	Date finalDate = null;

           	if (fechaDesde != null && fechaHasta != null) {
           		// Cierre por rango de fechas
           		beginDate = DATE_FORMATTER.parse(fechaDesde + HORA_CERO);
           		finalDate = DATE_FORMATTER.parse(fechaHasta + HORA_V3);
           	}
           	else {
           		// Cierre diario
           		if (hora1 != null && hora2 != null){
                    beginDate = DATE_FORMATTER.parse(fecha + " " + hora1);
                    finalDate = DATE_FORMATTER.parse(fecha + " " + hora2);
               	} else {
                    beginDate = DATE_FORMATTER.parse(fecha + HORA_CERO);
                    finalDate = DATE_FORMATTER.parse(fecha + HORA_V3);
               	}
           	}
            if (unidad != null) {
            	if (unidad.length() == 0) {
                	unidad = null;
                }
            }

            nofRegClosed = bookUseCase.closeRegisters(useCaseConf, bookID, beginDate, finalDate, unidad, (opcion == OPTION_TYPE_RMD));

            if (nofRegClosed == 0) {
            	ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale())
                        .getProperty(Keys.I18N_EXCEPTION_NO_REGS_TO_CLOSE));
            }

        } catch (BookException e) {
            _logger.fatal("Error cerrando registros.", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                  Keys.I18N_ISICRESSRV_ERR_CLOSING_REGS));
        } catch (EventException e) {
            _logger.fatal("Error cerrando registros.", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                  Keys.I18N_ISICRESSRV_ERR_CLOSING_REGS));
        }catch (SecurityException e){
        	 ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                     Keys.I18N_ISICRESSRV_ERR_CANNOT_OPEN_CLOSE_REGS));

        } catch (Throwable e) {
            _logger.fatal("Error cerrando registros.", e);
            ResponseUtils.generateJavaScriptLog(writer, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
                    Keys.I18N_ISICRESSRV_ERR_CLOSING_REGS));
        }
    }

    private int mappingOptionToInt(String opcion) {
        int result = 0;

        Map optionId = new HashMap();
        optionId.put(OPTION_TYPE_STRING_RMD, new Integer(4));
        optionId.put(OPTION_TYPE_STRING_RMO, new Integer(5));

        result = ((Integer) optionId.get(opcion)).intValue();

        return result;
    }


}