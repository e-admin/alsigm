
package ieci.tecdoc.idoc.core.database;

import java.util.Vector;

public class DynamicRow 
{
   public DynamicRow()
   {
      _className = null;
      _valuesMethod = null;
      _resulSet = new Vector();
   }

   public String getClassName() 
   {
      return _className;
   }

   public String getValuesMethod() 
   {
      return _valuesMethod;
   }

   public int getRowCount() 
   {
      return _resulSet.size();
   }

   public Object getRow(int index) 
   {
      return _resulSet.get(index);
   }

   public void setClassName(String className) 
   {
      _className = className;
   }

   public void setValuesMethod(String method) 
   {
      _valuesMethod = method;
   }

   public void addRow(Object row) 
   {
      _resulSet.add(row);
   }

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
      
		buffer.append("DynamicRow[");
		buffer.append("ClassName = ").append(_className);
		buffer.append(", ValuesMethod = ").append(_valuesMethod);
		buffer.append(", ResultSet = ").append(_resulSet.toString());		
		buffer.append("]");
      
		return buffer.toString();
   }


   private String _className;
   private String _valuesMethod;
   private Vector _resulSet;
   
}