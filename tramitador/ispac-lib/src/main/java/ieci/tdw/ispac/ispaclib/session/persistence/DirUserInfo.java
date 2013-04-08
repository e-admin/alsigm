/*
 * DirUserInfo.java
 *
 * Created on June 4, 2004, 3:27 PM
 */

package ieci.tdw.ispac.ispaclib.session.persistence;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import org.w3c.dom.Document;

public class DirUserInfo implements Persistable
{

	private String userDN;
	private String userUID;
	private String deptDN;
	private String deptUID;
	private String[] grpDNs;
	private String[] grpUIDs;

	/**
	 * Constructor vacio
	 */
	public DirUserInfo()
	{
	}

	/**
	 * Construye un objeto con la informacion del usuario recogida del
	 * directorio
	 *
	 * @param userDN dn del usuario
	 * @param userUID uid del usuario
	 * @param deptDN dn del departamento
	 * @param deptUID uid del departamento
	 * @param grpDNs dns de los grupos
	 * @param grpUIDs uids de los grupos
	 */
	public DirUserInfo(String userDN, String userUID, String deptDN,
			String deptUID, String[] grpDNs, String[] grpUIDs)
	{
		this.userDN = userDN;
		this.userUID = userUID;
		this.deptDN = deptDN;
		this.deptUID = deptUID;
		this.grpDNs = grpDNs;
		this.grpUIDs = grpUIDs;
	}

	/**
	 * Devuelve el dn de usuario
	 *
	 * @return dn de usuario
	 */
	public String getUserDN()
	{
		return userDN;
	}

	/**
	 * Devuelve el uid de usuario
	 *
	 * @return uid de usuario
	 */
	public String getUserUID()
	{
		return userUID;
	}

	/**
	 * Devuelve el dn del departamento
	 *
	 * @return dn del departamento
	 */
	public String getDeptDN()
	{
		return deptDN;
	}

	/**
	 * Devuelve el uid del departamento
	 *
	 * @return uid del departamento
	 */
	public String getDeptUID()
	{
		return deptUID;
	}

	/**
	 * Devuelve los dns de los grupos a los cuales pertenece el usuario
	 *
	 * @return dns de los grupos
	 */
	public String[] getGroupDNs()
	{
		return grpDNs;
	}

	/**
	 * Devuelve los uids de los grupos a los cuales pertenece el usuario
	 *
	 * @return uids de los grupos
	 */
	public String[] getGroupUIDs()
	{
		return grpUIDs;
	}

	/**
	 * Devuelve la lista de responsables asociada a la sesion
	 *
	 * @param lista de responsables asociada a la sesion
	 */
	public String getRespListAsString()
	{
		String list = "'" + DBUtil.replaceQuotes(this.userUID) + "','" + DBUtil.replaceQuotes(this.deptUID) + "'";
		if (grpUIDs.length > 0)
		{
			for (int i = 0; i < grpUIDs.length; i++)
			{
				list += ",'" + DBUtil.replaceQuotes(grpUIDs[i]) + "'";
			}
		}
		return list;
	}

	/**
	 * Convierte el objeto en un string en formato xml
	 *
	 * @return string en formato xml que representa al objeto
	 */
	public String toXmlString()
	{
		String userInfo = XmlTag.newTag("userdn", userDN);
		userInfo += XmlTag.newTag("useruid", userUID);
		userInfo += XmlTag.newTag("deptdn", deptDN);
		userInfo += XmlTag.newTag("deptuid", deptUID);
		String groupsInfo = "";
		for (int i = 0; i < grpDNs.length && i < grpUIDs.length; i++)
		{
			String groupInfo = XmlTag.newTag("groupdn", grpDNs[i]);
			groupInfo += XmlTag.newTag("groupuid", grpUIDs[i]);
			groupInfo = XmlTag.newTag("group", groupInfo);
			groupsInfo += groupInfo;
		}
		userInfo += XmlTag.newTag("groups", groupsInfo);
		String xml = XmlTag.newTag("diruserinfo", userInfo);
		return xml;
	}

	public void loadObject(String xml) throws ISPACException
	{
		Document doc = XMLDocUtil.newDocument(xml);
		userDN = XPathUtil.get(doc, "/diruserinfo/userdn/text()");
		userUID = XPathUtil.get(doc, "/diruserinfo/useruid/text()");
		deptDN = XPathUtil.get(doc, "/diruserinfo/deptdn/text()");
		deptUID = XPathUtil.get(doc, "/diruserinfo/deptuid/text()");
		int nGrps = XPathUtil.getNodeList(doc,"/diruserinfo/groups/group").getLength();
		grpDNs = new String[nGrps];
		grpUIDs = new String[nGrps];
		for (int i = 0; i < nGrps; i++)
		{
			grpDNs[i] = XPathUtil.get(doc, "/diruserinfo/groups/group[" + (i + 1)	+ "]/groupdn/text()");
			grpUIDs[i] = XPathUtil.get(doc, "/diruserinfo/groups/group[" + (i + 1) + "]/groupuid/text()");
		}

	}
}
