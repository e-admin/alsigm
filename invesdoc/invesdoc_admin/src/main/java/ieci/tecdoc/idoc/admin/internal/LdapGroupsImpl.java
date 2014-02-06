
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.database.LdapGroupsTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;

import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Implementación de la clase LdapGroups.  Maneja la lista de grupos invesDoc 
 * relacionados con un servicio de directorio Ldap.
 */

public class LdapGroupsImpl 
{

   public LdapGroupsImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }

   public LdapGroup get(int index) 
   {
      return (LdapGroup)_list.get(index);
   }
   
   public void load(boolean full) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapGroupsTable table = new LdapGroupsTable();
      int counter;
      LdapGroupImpl group;
   
      if (_logger.isDebugEnabled())
         _logger.debug("loadLite");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapGroupsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdColumnName");
         
         rowInfo.setClassName(LdapGroupImpl.class.getName());
//         rowInfo.setValuesMethod("loadAllValues");
         rowInfo.setValuesMethod("loadIdValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(table.getLoadBaseAllQual(), false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            group = (LdapGroupImpl)rowInfo.getRow(counter);
            group.load(group.getId(), full);
            add(group);
         }
         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         DbConnection.close();
      }
         
   }
   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "LdapGroups";
      LdapGroupImpl group;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         group = getImpl(i);
         bdr.addFragment(group.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve un grupo de la lista.
    * 
    * @param index Indice del grupo que se desea recuperar.
    * 
    * @return El grupo mencionado.
    */
    
   private LdapGroupImpl getImpl(int index)
   {
      return (LdapGroupImpl)_list.get(index);  
   }
 
   /**
    * Añade un grupo a la lista.
    * 
    * @param user El grupo mencionado.
    *  
    */

   protected void add(LdapGroupImpl group) 
   {
      _list.add(group);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(LdapGroupsImpl.class);
}