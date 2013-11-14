package com.ieci.tecdoc.common;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clase que contiene los datos del usuario que se loguea
 *
 * @author lmvicente
 * @version
 * @since
 * @creationDate 30-mar-2004
 */

public class AuthenticationUser {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static Logger log = Logger.getLogger(AuthenticationUser.class);

	private Integer id = null;
	private String name = null;
	//Departamento con el que esta autenticado el usuario
	private Integer deptid = null;
	//Departamento al que pertenece originalmente el usuario
	private Integer deptIdOriginal = null;
	//Listado de departamentos del usuario
	private List deptList = null;
	private List groupList = null;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public AuthenticationUser() {
	}

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	/**
	 * @return Returns the deptid.
	 */
	public Integer getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid
	 *            The deptid to set.
	 */
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	/**
	 * @return Returns the id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Integer getDeptIdOriginal() {
		return deptIdOriginal;
	}

	/**
	 * @param deptIdOriginal
	 */
	public void setDeptIdOriginal(Integer deptIdOriginal) {
		this.deptIdOriginal = deptIdOriginal;
	}

	/**
	 * @return
	 */
	public List getDeptList() {
		return deptList;
	}

	/**
	 * @param deptList
	 */
	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}


	/**
	 *
	 * @return
	 */
	public List getGroupList() {
		return groupList;
	}

	/**
	 *
	 * @param groupList
	 */
	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Id [");
		buffer.append(id);
		buffer.append("] Name [");
		buffer.append(name);
		buffer.append("] Deptid [");
		buffer.append(deptid);
		buffer.append("]");
		buffer.append("] DeptidOriginal [");
		buffer.append(deptIdOriginal);
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
				name = field.getName();
				buffer.append("<");
				buffer.append(name.toUpperCase());
				buffer.append(">");
				if (field.get(this) != null) {
					buffer.append(field.get(this));
				}
				buffer.append("</");
				buffer.append(name.toUpperCase());
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

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
