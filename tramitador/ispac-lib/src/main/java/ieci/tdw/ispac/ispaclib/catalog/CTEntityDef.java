/*
 * invesflow Java - ISPAC
 *
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/CTEntityDef.java,v $
 * $Revision: 1.4 $
 * $Date: 2008/08/28 11:19:13 $
 * $Author: davidfa $
 */
package ieci.tdw.ispac.ispaclib.catalog;

import java.util.Date;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;

/**
 * @author juanin
 *
 */
public class CTEntityDef implements IEntityDef
{

    int id;
    int type;
    String name;
    String keyField;
    String sequence;
    String keyNumExp;
    String schemaExpr;
    int appId;
    String description;
    String definition;
    Date timestamp;

    public CTEntityDef()
    {
        id=0;
        type=0;
        name="";
        keyField="";
        sequence="";
        keyNumExp="";
        schemaExpr="";
        appId=0;
        description="";
        definition="";
        timestamp = null;
    }


    /**
     * @param appId Cambia el valor de la propiedad appId.
     */
    public void setAppId(int appId)
    {
        this.appId = appId;
    }
    /**
     * @param description Cambia el valor de la propiedad description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    /**
     * @param description Cambia el valor de la propiedad definition.
     */
    public void setDefinition(String definition)
    {
        this.definition = definition;
    }
    /**
     * @param id Cambia el valor de la propiedad id.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    /**
     * @param keyField Cambia el valor de la propiedad keyField.
     */
    public void setKeyField(String keyField)
    {
        this.keyField = keyField;
    }
    /**
     * @param keyNumExp Cambia el valor de la propiedad keyNumExp.
     */
    public void setKeyNumExp(String keyNumExp)
    {
        this.keyNumExp = keyNumExp;
    }
    /**
     * @param name Cambia el valor de la propiedad name.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @param schemaExpr Cambia el valor de la propiedad schemaExpr.
     */
    public void setSchemaExpr(String schemaExpr)
    {
        this.schemaExpr = schemaExpr;
    }
    /**
     * @param sequence Cambia el valor de la propiedad sequence.
     */
    public void setSequence(String sequence)
    {
        this.sequence = sequence;
    }
    /**
     * @param type Cambia el valor de la propiedad type.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
	 * @param timestamp The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getId()
     */
    public int getId() throws ISPACException
    {
        return id;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getName()
     */
    public String getName() throws ISPACException
    {
        return name;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getKeyField()
     */
    public String getKeyField() throws ISPACException
    {
        return keyField;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getKeyNumExp()
     */
    public String getKeyNumExp() throws ISPACException
    {
        return keyNumExp;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getSequence()
     */
    public String getSequence() throws ISPACException
    {
        return sequence;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getType()
     */
    public int getType() throws ISPACException
    {
        return type;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getSchemaExpr()
     */
    public String getSchemaExpr() throws ISPACException
    {
        return schemaExpr;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getAppId()
     */
    public int getAppId() throws ISPACException
    {
        return appId;
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getAppIdString()
     */
    public String getAppIdString() throws ISPACException
    {
        return String.valueOf(appId);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getDescription()
     */
    public String getDescription() throws ISPACException
    {
        return description;
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getDefinition()
     */
    public String getDefinition() throws ISPACException
    {
        return definition;
    }
    
    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef#getTimestamp()
     */
    public Date getTimestamp() throws ISPACException
    {
        return timestamp;
    }

}