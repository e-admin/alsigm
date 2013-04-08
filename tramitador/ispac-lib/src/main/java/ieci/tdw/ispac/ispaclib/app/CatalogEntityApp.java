package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;

public class CatalogEntityApp extends SimpleEntityApp
{
	public CatalogEntityApp(ClientContext context)
	{
		super(context);
	}

	public void setItem(IItem item)
	throws ISPACException {
		
		DbCnt cnt = mContext.getConnection();
		
		try
		{
			String tableName = ((CTEntityDAO) item).getTableName().toUpperCase();
			String keyName = ((CTEntityDAO) item).getKeyName();
			CompositeItem composite = new CompositeItem( tableName + ":" + keyName);
			composite.addItem( item, tableName + ":");
			
			this.mitem = composite;
		}
		catch(Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{	
			mContext.releaseConnection(cnt);
		}
	}

	public void initiate() 
	throws ISPACException
	{
		
		DbCnt cnt = mContext.getConnection();
		
		try
		{
            CTApplicationDAO application = new CTApplicationDAO(cnt,mitem.getInt("SPAC_CT_ENTIDADES:FRM_EDIT"));
			
			if (application != null)
			{
				String tableName = application.getTableName().toUpperCase();
				// La aplicación es de solo lectura
				((CompositeItem) mitem).addItem( application, tableName + ":", false);
			}
		}
		catch( Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{	
			mContext.releaseConnection(cnt);
		}
	}
}
