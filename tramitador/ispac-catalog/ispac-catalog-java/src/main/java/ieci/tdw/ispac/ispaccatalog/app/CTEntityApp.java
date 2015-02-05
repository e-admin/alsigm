package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;

public class CTEntityApp extends SimpleEntityApp {

	public CTEntityApp(ClientContext context)
	{
		super(context);
	}

	public IItem processItem(IItem item)
	throws ISPACException
	{
	    CompositeItem composite = new CompositeItem( "SPAC_CT_ENTIDADES:ID");
		composite.addItem( item, "SPAC_CT_ENTIDADES:");
		
		return composite;
	}

	/*
	public boolean validate()
	throws ISPACException
	{	
		String sNombre = (String)mitem.get("SPAC_CT_ENTIDADES:NOMBRE");
		if((sNombre != null) && ("-1".equals(sNombre))){
			ValidationError error = new ValidationError("SPAC_CT_ENTIDADES:NOMBRE", "form.entity.property.error.table");
			addError(error);
			return false;
		}
			return true;
	}
	*/

	public void initiate() throws ISPACException
    {
		IItem item = getAsociatedEntity(ICatalogAPI.ENTITY_CT_APP);

		((CompositeItem) mitem).addItem( item, "SPAC_CT_APLICACIONES:");

    }

    /**
     * Obtiene la entidad asociada
     *
     * @param entityId identificador de la entidad asociada
     * @return EntityDAO
     */
    protected IItem getAsociatedEntity(int ctentityId)
    throws ISPACException
    {
        ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();

        // Si no tiene asociada aplicación se devuelve un Item vacío.
        if (getItem().get("SPAC_CT_ENTIDADES:FRM_EDIT") == null)
            return catalogAPI.getCTEntity(ctentityId);

        int appid = getItem().getInt("SPAC_CT_ENTIDADES:FRM_EDIT");

        try
        {
            IItemCollection collection =
            catalogAPI.queryCTEntities(ctentityId, "WHERE ID = " + appid);

			if (!collection.next())
			    return catalogAPI.getCTEntity(ctentityId);
			    //throw new ISPACException(" CTEntityApp: No existe la aplicación");

			return collection.value();
        }
        catch (ISPACException e)
        {
            throw new ISPACException(" CTEntityApp: getAsociatedEntity()", e);
        }
    }    
}
