
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;

import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Implementación de la clase LdapUsers.  Maneja la lista de usuarios invesDoc 
 * relacionados con un servicio de directorio Ldap.
 */

public class LdapUsersImpl 
{

   public LdapUsersImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }

   public LdapUser get(int index) 
   {
      return (LdapUser)_list.get(index);
   }
   
   public void load(boolean full) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      int counter;
      LdapUserImpl user;
   
      if (_logger.isDebugEnabled())
         _logger.debug("loadLite");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdColumnName");
         
         rowInfo.setClassName(LdapUserImpl.class.getName());
//         rowInfo.setValuesMethod("loadAllValues");
         rowInfo.setValuesMethod("loadIdValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(table.getLoadBaseAllQual(), false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (LdapUserImpl)rowInfo.getRow(counter);
            user.load(user.getId(), full);
            add(user);
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
      String tagName = "LdapUsers";
      LdapUserImpl user;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         user = getImpl(i);
         bdr.addFragment(user.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve un usuario de la lista.
    * 
    * @param index Indice del usuario que se desea recuperar.
    * 
    * @return El usuario mencionado.
    */
    
   private LdapUserImpl getImpl(int index)
   {
      return (LdapUserImpl)_list.get(index);  
   }
 
   /**
    * Añade un usuario a la lista.
    * 
    * @param user El usuario mencionado.
    *  
    */

   protected void add(LdapUserImpl user) 
   {
      _list.add(user);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(LdapUsersImpl.class);
}