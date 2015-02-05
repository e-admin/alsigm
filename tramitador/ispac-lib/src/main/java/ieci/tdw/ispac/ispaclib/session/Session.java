package ieci.tdw.ispac.ispaclib.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.ClientCtxConstants;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.session.persistence.Persistable;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Session implements Persistable
{

//	private String remoteHost;
//	private String ticket;
//	private String username;
	private ClientContext clctx;

	/**
	 * Constructor vacio
	 */
	public Session()
	{
	}

	/**
	 * Constructor del objeto session
	 */
	public Session(String remoteHost, String ticket, String userName, IDirectoryEntry userEntry)
			throws ISPACException
	{
		clctx = new ClientContext();
		Responsible resp= RespFactory.createResponsible(userEntry);
		clctx.setTicket(ticket);
		//clctx.setUser(RespFactory.createResponsible(userentry));
		//clctx.setDirInfoUser(userInfo);
		clctx.setUser(resp);
		// Establecer el nombre de usuario que se loguea
		// para cuando es un administrador por defecto
		clctx.setUserName(userName);
		clctx.setRemoteHost(remoteHost);
	}

	/**
	 * Devuelve el ticket de la sesion
	 * 
	 * @return ticket de la sesion
	 */
	public String getTicket()
	{
		return (String) clctx.get(ClientCtxConstants.MBR_TICKET);
	}

	/**
	 * Devuelve el nombre de usuario
	 * 
	 * @return nombre de usuario
	 */
	public String getUserName()
	{
		return (String) clctx.get(ClientCtxConstants.MBR_USERNAME);
	}
	
	/**
	 * Devuelve el nombre del usuario,
	 * siendo el nombre largo cuando lo tiene,
	 * en caso contrario, es el mismo nombre de usuario
	 * 
	 * @return nombre de usuario
	 */
	public String getRespName()
	{
		return (String) clctx.get(ClientCtxConstants.MBR_RESPNAME);
	}

	/**
	 * Devuelve el nombre de usuario
	 * 
	 * @return nombre de usuario
	 */
//	public void setUserName(String userName)
//	{
//		this.username = username;
//	}

	/**
	 * Devuelve el contexto del cliente
	 * 
	 * @return contexto del cliente
	 */
	public ClientContext getClientContext()
	{
		return clctx;
	}

	/**
	 * Establece el contexto del cliente
	 * 
	 * @return contexto del cliente
	 */
	public void setClientContext(ClientContext clctx)
	{
		this.clctx = clctx;
	}

	/**
	 * Convierte el objeto en un string en formato xml
	 * 
	 * @return string en formato xml que representa al objeto
	 */
	public String toXmlString()
	{
		String xml = XmlTag.newTag("ticket", getTicket());
		xml += XmlTag.newTag("username", getUserName());
		xml += clctx.toXmlString();
		xml = XmlTag.newTag("session", xml);
		
		//Añadimos la codificacion para que al parsearlo no de problemas, ya que segun el parser, 
		//si no hay codificacion coge por defecto UFT-8, y si hay caracteres como ñ, tildes, etc.. en los datos,
		//daba error en el parseo
		//Origen: Error al validar usuarios contra Active Directory cuando en el CN existen caracteres especiales
		xml = XmlTag.getXmlInstruction("ISO-8859-1")+xml;
		return xml;
	}

	public void loadObject(String xml) throws ISPACException
	{
		Document doc = XMLDocUtil.newDocument(xml);
		String ticket = XPathUtil.get(doc, "/session/ticket/text()");
		String userName = XPathUtil.get(doc, "/session/username/text()");
		Node nClientcontext = XPathUtil.selectNode(doc, "/session/clientcontext");
		ClientContext newClCtx = new ClientContext();
		newClCtx.loadObject(XMLDocUtil.toString(nClientcontext));
		newClCtx.setTicket(ticket);
		newClCtx.setUserName(userName);
		clctx = newClCtx;
	}

//	/**
//	 * Pasa los elementos de una enumaracion a un array de strings
//	 * 
//	 * @param e enumeracion
//	 * @return array
//	 */
//	private String[] toArray(Enumeration e)
//	{
//		String[] array = null;
//		Vector vec = new Vector();
//		while (e.hasMoreElements())
//		{
//			String element = (String) e.nextElement();
//			vec.add(element);
//		}
//		vec.trimToSize();
//		array = new String[vec.size()];
//		array = (String[]) vec.toArray(array);
//		return array;
//	}

	public String toString()
	{
		return toXmlString();
	}
}
