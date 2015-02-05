
package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.dom.Document;


public class HTTPNetManager
{

	// Conexion con una determinada URL
	private URLConnection m_connection;

	/**
	 * Crea una instancia de esta clase. Gestiona el envio y recepcion de
	 * mensajes XML
	 */
	public HTTPNetManager()
	{
	}

	/**
	 * Envia un mensaje XML a una direccion
	 * 
	 * @param msg mensaje que se envia
	 * @param dst direccion destino a la cual se envia el mensaje
	 * @return XMLNetworkException si hubo un error al enviar el mensaje
	 */
	public void send(Document xmldoc, String dst) throws ISPACException
	{
		OutputStream out = null;
		try
		{
			URL url = new URL(dst);
			m_connection = url.openConnection();
			m_connection.setDoInput(true);
			m_connection.setDoOutput(true);
			m_connection.setUseCaches(false);
			m_connection.setDefaultUseCaches(false);
			out = m_connection.getOutputStream();
			
			XMLDocUtil.serialize(out,xmldoc);
			
			if (out != null)
				out.close();
		} catch (Exception e)
		{
			try
			{
				if (out != null)
					out.close();
			} catch (IOException e1)
			{
				// Ignore error
			}
			throw new ISPACException("No se ha podido establecer conexión con el servidor: "
					+ e.toString());
		}
	}

	/**
	 * Recibe un mensaje XML del servidor
	 */
	public Document receive() throws ISPACException
	{
		InputStream in = null;
		try
		{
			in = m_connection.getInputStream();
			Document xmldoc=XMLDocUtil.newDocument(in);
			
			if (in != null)
				in.close();
			
			return xmldoc;
		} catch (Exception e)
		{
			try
			{
				if (in != null)
					in.close();
			} catch (IOException e1)
			{
				// Ignore error
			}
			throw new ISPACException(e.toString());
		}
	}
}