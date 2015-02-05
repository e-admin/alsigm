package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.GroupsTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Group;

/**
 * Implementación de la clase Groups.  Maneja la lista de grupos invesDoc.
 */
public class GroupsImpl
{
	public GroupsImpl()
   {
      _list = new ArrayList();
   }

   public int count()
   {
      return _list.size();
   }

   public Group get(int index)
   {
      return (Group)_list.get(index);
   }

   public void load(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      GroupsTable table = new GroupsTable();
      int counter;
      GroupImpl group;

      if (_logger.isDebugEnabled())
         _logger.debug("load");

      DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         tableInfo.setTableObject(table);
         tableInfo.setClassName(GroupsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

         rowInfo.setClassName(GroupImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, "", false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            group = (GroupImpl)rowInfo.getRow(counter);
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
         dbConn.close();
      }

   }

   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Groups";
      GroupImpl group;

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

   private GroupImpl getImpl(int index)
   {
      return (GroupImpl)_list.get(index);
   }

   /**
    * Añade un grupo a la lista.
    *
    * @param group El grupo mencionado.
    *
    */

   protected void add(GroupImpl group)
   {
      _list.add(group);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(GroupsImpl.class);
}
