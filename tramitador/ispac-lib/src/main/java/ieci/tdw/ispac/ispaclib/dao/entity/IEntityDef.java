/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/dao/entity/IEntityDef.java,v $
 * $Revision: 1.4 $
 * $Date: 2008/08/28 11:17:24 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.dao.entity;

import java.util.Date;

import ieci.tdw.ispac.api.errors.ISPACException;

/**
 * IEntityDef
 *
 * Proporciona toda la información necesaria para crear una entidad.
 *
 * @version $Revision: 1.4 $ $Date: 2008/08/28 11:17:24 $
 */
public interface IEntityDef {
	
    public int getId() throws ISPACException;
    public String getName() throws ISPACException;
    public String getKeyField() throws ISPACException;
    public String getKeyNumExp() throws ISPACException;
    public String getSequence() throws ISPACException;
    public int getType() throws ISPACException;
    public String getSchemaExpr() throws ISPACException;
    public int getAppId() throws ISPACException;
    public String getAppIdString() throws ISPACException;
    public String getDescription() throws ISPACException;
    public String getDefinition() throws ISPACException;
    public Date getTimestamp() throws ISPACException;

}
