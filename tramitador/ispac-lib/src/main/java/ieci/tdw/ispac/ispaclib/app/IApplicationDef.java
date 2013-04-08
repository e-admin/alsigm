/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/app/IApplicationDef.java,v $
 * $Revision: 1.3 $
 * $Date: 2007/10/17 11:22:00 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;

/**
 * IApplicationDef
 *
 *
 * @version $Revision: 1.3 $ $Date: 2007/10/17 11:22:00 $
 */
public interface IApplicationDef
{
    /**
     * @return Devuelve el valor de la propiedad className.
     */
    public String getClassName() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad description.
     */
    public String getDescription() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad formatter.
     */
    public String getFormatter() throws ISPACException;

    /**
     * @return Devuelve el valor de la propiedad formatterXML.
     */
    public String getFormatterXML() throws ISPACException;

    /**
     * @return Devuelve el valor de la propiedad id.
     */
    public int getId() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad name.
     */
    public String getName() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad page.
     */
    public String getPage() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad parameters.
     */
    public String getParameters() throws ISPACException;
    
    /**
     * @return Devuelve el valor de la propiedad frmJsp.
     */
	public String getFrmJsp() throws ISPACException;
	
    /**
     * @return Devuelve el valor de la propiedad frmVersion.
     */
	public int getFrmVersion() throws ISPACException;
	
    /**
     * @return Devuelve el valor de la propiedad entPrincipalNombre.
     */
	public String getEntPrincipalNombre() throws ISPACException;
	
}