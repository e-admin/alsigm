package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.database.DepartmentsTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Implementación de la clase Departaments.  Maneja la lista de departamentos invesDoc.
 */
public class DepartmentsImpl
{
	public DepartmentsImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }

   public Department get(int index) 
   {
      return (Department)_list.get(index);
   }
   
   public void load(int parentId) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();
      String qual;
      int counter;
      DepartmentImpl department;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load from parentId = " + parentId);
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameParentColumnNames");
         
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameParentValues");
         rowsInfo.add(rowInfo);
         
         qual = table.getParentIdQual(parentId);
         
         DynamicFns.selectMultiple(qual, false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	department = (DepartmentImpl)rowInfo.getRow(counter);
            add(department);
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
   
   public void load() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();
      int counter;
      DepartmentImpl department;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameParentColumnNames");
         
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameParentValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple("", false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	department = (DepartmentImpl)rowInfo.getRow(counter);
            add(department);
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
      String tagName = "Departments";
      DepartmentImpl department;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
      	department = getImpl(i);
         bdr.addFragment(department.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve un departamento de la lista.
    * 
    * @param index Indice del departamento que se desea recuperar.
    * 
    * @return El departamento mencionado.
    */
    
   private DepartmentImpl getImpl(int index)
   {
      return (DepartmentImpl)_list.get(index);  
   }
 
   /**
    * Añade un departamento a la lista.
    * 
    * @param department El departamento mencionado.
    *  
    */

   protected void add(DepartmentImpl department) 
   {
      _list.add(department);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(DepartmentsImpl.class);
}
