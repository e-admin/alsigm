package ieci.tecdoc.idoc.core.database;

import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.miscelanea.ClassLoader;
import java.lang.reflect.Method;

public class Invocation 
{
   private Invocation()
   {
   }

   protected static String invokeMethod(DynamicTable tableInfo, String method) 
                              throws Exception 
   {

      String result;
      Method getTableNameMethod;
      Class objectClass = null;

      objectClass = ClassLoader.getClass(tableInfo.getClassName());
//      objectClass = ClassLoader.getClass(tableInfo.getTableObject().class.getName());

      getTableNameMethod = objectClass.getMethod(method, null);
      result = (String)getTableNameMethod.invoke(tableInfo.getTableObject(), null);

      return result;

   }

   protected static int invokeOutputStatementValuesMethod(DynamicRow rowInfo,
                              DbOutputStatement outputStatement, int index) 
                              throws Exception 
   {
      Class[] parameterTypes = new Class[] {DbOutputStatement.class,
                                            Integer.class};
      Method getStatementValuesMethod;
      Object[] arguments = new Object[] {outputStatement, new Integer(index)};
      Class objectClass;
      Integer idx;

      objectClass = ClassLoader.getClass(rowInfo.getClassName());

      getStatementValuesMethod = objectClass.getMethod(
                                 rowInfo.getValuesMethod(), parameterTypes);
      idx = (Integer)getStatementValuesMethod.invoke(
                     rowInfo.getRow(rowInfo.getRowCount() - 1), arguments);

      return idx.intValue();

   }

   protected static int invokeInputStatementValuesMethod(DynamicRow rowInfo,
                                 DbInputStatement inputStatement, int index) 
                                 throws Exception 
   {

      Class[] parameterTypes = new Class[] {DbInputStatement.class,
                                            Integer.class};
      Method getStatementValuesMethod;
      Object[] arguments = new Object[] {inputStatement, new Integer(index)};
      Class objectClass;
      Integer idx;

      objectClass = ClassLoader.getClass(rowInfo.getClassName());

      getStatementValuesMethod = objectClass.getMethod(rowInfo.getValuesMethod(),
                                                       parameterTypes);
      idx = (Integer)getStatementValuesMethod.invoke(
                        rowInfo.getRow(rowInfo.getRowCount() - 1), arguments);

      return idx.intValue();

   }

}