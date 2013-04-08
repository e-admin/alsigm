/*
 * Created on 26-jul-2004
 *
 */
package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.api.errors.ISPACException;

/**
 *
 */
public interface IResponsible extends IItem {
	
	public static final	String SUPERVISOR 				= "SUPERVISOR";
	public static final	String SUPERVISOR_TOTAL 		= "SUPERVISOR_TOTAL";
	public static final	String SUPERVISOR_MONITORING 	= "SUPERVISOR_MONITORING";
	
	public static final String PROPERTY_UID			= "UID";
	public static final String PROPERTY_NAME		= "NAME";
	public static final String PROPERTY_RESPNAME	= "RESPNAME";
	
	public static final String PROPERTY_GROUP 		= "GROUP";
	public static final String PROPERTY_USER	 	= "USER";
	public static final String PROPERTY_ORGUNIT 	= "ORGUNIT";
	
	/** Devuelve la unidad organizativa o departamento a la que pertenece un
	 *  responsable.
	 * @return la unidad organizativa
	 * @throws ISPACException
	 */
    public IResponsible getRespOrgUnit() throws ISPACException;

    /** Devuelve las unidades organizativas o departamentos que engloba
     * el responsable actual.
     * @return Colección de responsables.
     * @throws ISPACException
     */
    public IItemCollection getRespOrgUnits() throws ISPACException;

    /** Devuelve los usuarios que dependen de este responsable. 
     * Por ejemplo se usa a lo hora de mostrar los usuarios de un grupo en el catalogo desde la opcion de gestion de permisos
     * @return Colección de responsables
     * @throws ISPACException
     */
    public IItemCollection getRespUsers() throws ISPACException;

    /** Devuelve los grupos a los cuales pertenece el responsable.
     * @return
     * @throws ISPACException
     */
    public IItemCollection getUserRespGroups() throws ISPACException;

    /**
     * 
     * @return
     */
    public boolean isUser();
    
    /**
     * 
     * @return
     */
    public boolean isGroup();
    
    /**
     * 
     * @return
     */
    public boolean isOrgUnit();

    /**
     * 
     * @return
     */
    public String getUID();
    
    /**
     * Login del usuario
     * @return
     */
    public String getName();
        
    /**
     * Nombre del usuario
     * @return
     */
    public String getRespName();
    
}
