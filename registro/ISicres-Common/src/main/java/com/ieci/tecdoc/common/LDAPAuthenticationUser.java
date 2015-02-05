package com.ieci.tecdoc.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * Clase que contiene la informacion de un usuario de LDAP que se loguea en la
 * aplicacion
 * 
 */
public class LDAPAuthenticationUser {

	private static Logger log = Logger.getLogger(LDAPAuthenticationUser.class);

	private Object guid = null;

	private String guidStringFormat = null;

	private String dn = null;

	private String fullName = null;

	private String groups = null;

	private List groupList = new ArrayList();

	public LDAPAuthenticationUser() {
	}

	/**
	 * @return
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return
	 */
	public String getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 */
	public void setGroups(String groups) {
		groupList.add(groups);
		// this.groups = groups;
	}

	/**
	 * @return
	 */
	public Object getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 */
	public void setGuid(Object guid) {
		this.guid = guid;
	}

	/**
	 * @return
	 */
	public List getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList
	 */
	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}

	/**
	 * @return
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * @param dn
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 * @return
	 */
	public String getGuidStringFormat() {
		return guidStringFormat;
	}

	/**
	 * @param guidStringFormat
	 */
	public void setGuidStringFormat(String guidStringFormat) {
		this.guidStringFormat = guidStringFormat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("guid [");
		buffer.append(guid);
		buffer.append("] dn [");
		buffer.append(dn);
		buffer.append("] fullName [");
		buffer.append(fullName);
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * @return
	 */
	public String toXML() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1,
				className.length()).toUpperCase();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<");
		buffer.append(className);
		buffer.append(">");
		try {
			java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
			java.lang.reflect.Field field = null;
			int size = fields.length;
			for (int i = 0; i < size; i++) {
				field = fields[i];
				fullName = field.getName();
				buffer.append("<");
				buffer.append(fullName.toUpperCase());
				buffer.append(">");
				if (field.get(this) != null) {
					buffer.append(field.get(this));
				}
				buffer.append("</");
				buffer.append(fullName.toUpperCase());
				buffer.append(">");
			}
		} catch (Exception e) {
			log.error("Error al obtener el xml", e);
		}
		buffer.append("</");
		buffer.append(className);
		buffer.append(">");
		return buffer.toString();
	}
}
