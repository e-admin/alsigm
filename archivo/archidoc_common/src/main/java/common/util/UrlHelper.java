package common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import common.Constants;

/**
 * Utilidad para gestionar URLs.
 */
public class UrlHelper
{

	/**
	 * Añade un parámetro a una URL.
	 * @param url URL.
	 * @param paramName Nombre del parámetro.
	 * @param paramValue Valor del parámetro.
	 * @return URL modificada.
	 */
	public static String addUrlParameter(String url, String paramName,
	        String paramValue, boolean encoded)
	{
		if ( (url != null) && (url.length() > 0) )
		{
			StringBuffer aux = new StringBuffer(url);

			if (url.indexOf("?") > 0)
				aux.append(encoded ? "&amp;" : "&");
			else
				aux.append("?");

			try
			{
				aux.append(paramName);
				aux.append("=");
				aux.append(URLEncoder.encode(paramValue, Constants.ENCODING_ISO_8859_1));
			}
			catch (UnsupportedEncodingException e)
			{}

			url = aux.toString();
		}

		return url;
	}

	/**
	 * Añade un parámetro a una URL.
	 * @param url URL.
	 * @param paramName Nombre del parámetro.
	 * @param paramValue Valor del parámetro.
	 * @return URL modificada.
	 */
	public static String addUrlParameter(String url, String paramName, String paramValue)
	{
	    return addUrlParameter(url, paramName, paramValue, true);
	}

}
