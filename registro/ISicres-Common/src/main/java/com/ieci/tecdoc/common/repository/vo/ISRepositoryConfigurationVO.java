/**
 * 
 */
package com.ieci.tecdoc.common.repository.vo;

/**
 * VO que contiene los datos necesarios para obtener la configuración del gestor
 * documental de la base de datos.
 * 
 * 
 * @author Iecisa
 * 
 */

public class ISRepositoryConfigurationVO {

	/**
	 * Entidad con la que trabajamos
	 */
	protected String entidad;

	/**
	 * Tipo de libro
	 */
	protected Integer bookType;

	/**
	 * 
	 */
	public ISRepositoryConfigurationVO() {
		super();
	}

	/**
	 * 
	 * @param entidad
	 * @param bookType
	 */
	public ISRepositoryConfigurationVO(Integer bookType, String entidad) {
		super();
		this.entidad = entidad;
		this.bookType = bookType;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the bookType
	 */
	public Integer getBookType() {
		return bookType;
	}

	/**
	 * @param bookType
	 *            the bookType to set
	 */
	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

}
