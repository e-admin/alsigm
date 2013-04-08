package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LMVICENTE
 * @creationDate 30-abr-2004 11:11:12
 * @version
 * @since
 */
public class RequestUtils {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static String parseRequestParameterAsString(HttpServletRequest request, String parameter) {
        String result = request.getParameter(parameter);
        if (result != null) {
            return result.trim();
        } else {
            return null;
        }
    }

    public static String parseRequestParameterAsStringWithEmpty(HttpServletRequest request, String parameter) {
        String result = parseRequestParameterAsString(request, parameter);
        if (result == null) {
            return "";
        } else {
            return result;
        }
    }
    public static String parseRequestParameterAsStringWithEmptyBis(HttpServletRequest request, String parameter) {
        String result = parseRequestParameterAsString(request, parameter);
        if (result == null) {
            return "1";
        } else {
            return result;
        }
    }

    public static Long parseRequestParameterAsLong(HttpServletRequest request, String parameter) {
        Long result = null;

        try {
            result = new Long(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            // Ignored
        }

        return result;
    }

    public static Integer parseRequestParameterAsInteger(HttpServletRequest request, String parameter) {
        Integer result = null;
        try {
            if (request.getParameter(parameter)!=null) {
                result = new Integer(request.getParameter(parameter).trim());
            } 
        } catch (NumberFormatException e) {
            // Ignored
        }

        return result;
    }
    public static Boolean parseRequestParameterAsBoolean(HttpServletRequest request, String parameter, Boolean defaultValue) {
    	Boolean result = null;

        result = parseRequestParameterAsBoolean(request, parameter);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }
    
    public static Boolean parseRequestParameterAsBoolean(HttpServletRequest request, String parameter) {
        Boolean result = null;

        result = new Boolean(request.getParameter(parameter));

        return result;
    }

    public static Integer parseRequestParameterAsInteger(HttpServletRequest request,
            String parameter,
            Integer defaultValue) {
        Integer result = null;

        result = parseRequestParameterAsInteger(request, parameter);
        if (result == null) {
            result = defaultValue;
        }

        return result;
    }
    public static List parseRequestParametersAsList(HttpServletRequest request, String parameter) {
        List result = new ArrayList();
        String[] ids = request.getParameterValues(parameter);
        for (int i = 0; i < ids.length; i++){
        	result.add(new Integer(ids[i]));
        }
        return result;
    }

    public static int parseRequestParameterAsint(HttpServletRequest request, String parameter) {
        int result = 0;

        try {
            result = Integer.parseInt(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            // Ignored
        }

        return result;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

