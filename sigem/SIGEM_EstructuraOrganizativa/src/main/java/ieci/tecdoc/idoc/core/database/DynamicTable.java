
package ieci.tecdoc.idoc.core.database;

public class DynamicTable 
{
   public DynamicTable()
   {
      _tableObject = null;
      _tablesMethod = null;
      _columnsMethod = null;
      _className = null;
   }

   public Object getTableObject() 
   {
      return _tableObject;
   }
   
   public String getClassName() 
   {
      return _className;
   }
   
   public String getTablesMethod() 
   {
      return _tablesMethod;
   }

   public String getColumnsMethod() 
   {
      return _columnsMethod;
   }

   public void setClassName(String className) 
   {
      this._className = className;
   }

   public void setTableObject(Object obj) 
   {
      this._tableObject = obj;
   }

   public void setTablesMethod(String method) 
   {
      this._tablesMethod = method;
   }

   public void setColumnsMethod(String method) 
   {
      this._columnsMethod = method;
   }

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
      
		buffer.append("DynamicTable[");
		buffer.append("TableObject = ").append(_tableObject);
		buffer.append(", ClassName = ").append(_className);		
		buffer.append(", TablesMethod = ").append(_tablesMethod);		
		buffer.append(", ColumnsMethod = ").append(_columnsMethod);		
		buffer.append("]");
      
		return buffer.toString();
   }


   private String _tablesMethod;
   private String _className;
   private String _columnsMethod;
   private Object _tableObject;
   
}