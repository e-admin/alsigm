package ieci.tecdoc.sgm.base.dbex;

public final class DbTableFns
{

   private DbTableFns()
   {
   }

   public static void createTable(DbConnection connection, String tblName, DbColumnDef[] colDefs)
         throws Exception
   {

      String stmtText;

      stmtText = getCreateTableStmtText(tblName, colDefs,
                                        connection.getEngine(),
                                        connection.getEngineVersion());

      DbUtil.executeStatement(connection, stmtText);

   }

   public static void createTable(DbConnection connection, String tblName, DbColumnDef[] colDefs,
         DbIndexDef[] indexDefs) throws Exception
   {
      createTable(connection, tblName, colDefs);
      createIndices(connection, tblName, indexDefs);
   }

   public static void dropTable(DbConnection connection, String tblName) throws Exception
   {

      String stmtText;

      stmtText = getDropTableStmtText(tblName);

      DbUtil.executeStatement(connection, stmtText);

   }

   public static void createIndices(DbConnection connection, String tblName, DbIndexDef[] indexDefs)
         throws Exception
   {

      int count, i;

      count = indexDefs.length;

      for (i = 0; i < count; i++)
         createIndex(connection, tblName, indexDefs[i]);

   }

   public static void dropIndices(DbConnection connection, String tblName, DbIndexDef[] indexDefs)
         throws Exception
   {

      int count, i;

      count = indexDefs.length;

      for (i = 0; i < count; i++)
         dropIndex(connection, tblName, indexDefs[i].getName());

   }

   public static void createIndex(DbConnection connection, String tblName, DbIndexDef indexDef)
         throws Exception
   {

      String stmtText;

      stmtText = getCreateIndexStmtText(tblName, indexDef);

      DbUtil.executeStatement(connection, stmtText);

   }

   public static void dropIndex(DbConnection connection, String tblName, String indexName)
         throws Exception
   {

      String stmtText;

      stmtText = getDropIndexStmtText(tblName, indexName, connection.getEngine());

      DbUtil.executeStatement(connection, stmtText);

   }
   
   public static void altertable(DbConnection connection, String tblName, DbColumnDef[] colDefs)
   						throws Exception
   {
      String stmtText;
      
      stmtText = getAlterTableStmtText(tblName,colDefs, connection.getEngine());
      
      DbUtil.executeStatement(connection, stmtText);
   
   }

   // **************************************************************************

   private static String getCreateTableStmtText(String tblName,
                                                DbColumnDef[] colDefs,
                                                int engine, String engineVersion) 
                         throws Exception
   {

      StringBuffer tbdr;
      int count, i;
      DbColumnDef colDef;
      int dataType;

      tbdr = new StringBuffer();

      tbdr.append("CREATE TABLE ").append(tblName).append(" (");

      count = colDefs.length;

      for (i = 0; i < count; i++)
      {

         colDef = colDefs[i];
         dataType = colDef.getDataType();

         if (i > 0) tbdr.append(",");

         tbdr.append(colDef.getName());
         tbdr.append(" ");
         tbdr.append(DbDataType.getNativeDbType(dataType, engine, engineVersion));

         if (dataType == DbDataType.SHORT_TEXT)
               tbdr.append("(").append(colDef.getMaxLen()).append(")");

         if (!colDef.isNullable()) tbdr.append(" NOT NULL");

      }

      tbdr.append(")");

      return tbdr.toString();

   }

   private static String getDropTableStmtText(String tblName)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("DROP TABLE ").append(tblName);

      return tbdr.toString();

   }

   private static String getCreateIndexStmtText(String tblName,
         DbIndexDef indexDef)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      if (indexDef.isUnique())
         tbdr.append("CREATE UNIQUE INDEX ");
      else
         tbdr.append("CREATE INDEX ");

      tbdr.append(indexDef.getName()).append(" ON ").append(tblName);
      tbdr.append(" (").append(indexDef.getColumnNames()).append(")");

      return tbdr.toString();

   }

   private static String getDropIndexStmtText(String tblName, String indexName,
         int engine)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append("DROP INDEX ");      

      if (engine == DbEngine.SQLSERVER)
         tbdr.append(tblName).append(".").append(indexName);
      else
         tbdr.append(indexName);

      return tbdr.toString();

   }
   
   private static String getAlterTableStmtText(String tblName,DbColumnDef[] colDefs,
         int engine)
   {
      StringBuffer tbdr;
      DbColumnDef  colDef = null;
      int          count,i;
      int          dataType;
      
      tbdr = new StringBuffer();
      
      tbdr.append("ALTER TABLE ");
      tbdr.append(tblName);
      tbdr.append(" ADD ");
      
      if (engine == DbEngine.ORACLE)
         tbdr.append("(");
      
      count = colDefs.length;
     
      for (i = 0; i < count; i++)
      {

         colDef = colDefs[i];
         if (i > 0)
            tbdr.append(",");
         
         tbdr.append(colDef.getName());
         tbdr.append(" ");
         dataType = colDef.getDataType();
         
         switch(dataType)
         {
         	case DbDataType.SHORT_TEXT:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("VARCHAR2");
         	   else
         	      tbdr.append("varchar");
         	   tbdr.append("(");
         	   tbdr.append(new Integer(colDef.getMaxLen()).toString());
         	   tbdr.append(")");
         	   break;
         	}
         	case DbDataType.SHORT_INTEGER:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("NUMBER(5)");
         	   else
         	      tbdr.append("smallint");
         	   break;
         	}
         	case DbDataType.LONG_INTEGER:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("NUMBER(10)");
         	   else
         	      tbdr.append("int");
         	   break;
         	}
         	case DbDataType.SHORT_DECIMAL:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("NUMBER(12,5)");
         	   else
         	      tbdr.append("real");
         	   break;
         	}
         	case DbDataType.LONG_DECIMAL:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("NUMBER(20,4)");
         	   else
         	      tbdr.append("float");
         	   break;
         	}
         	case DbDataType.DATE_TIME:
         	{
         	   if (engine == DbEngine.ORACLE)
         	      tbdr.append("DATE");
         	   else
         	      tbdr.append("datetime");
         	   break;
         	}
        }
      }
      if (engine == DbEngine.ORACLE)
         tbdr.append(")");
             
      return tbdr.toString();
      
   }

} // class