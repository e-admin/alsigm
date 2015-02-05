package ieci.tecdoc.core.db;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.fss.core.FssDaoFileTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.uas.std.UasDaoDeptTbl;
/**@deprecated*/
public final class DbTableFns
{
	
   /*
   private DbTableFns()
   {
   }

   public static void createTable(String tblName, DbColumnDef[] colDefs)
         throws Exception
   {

      String stmtText;

      stmtText = getCreateTableStmtText(tblName, colDefs,
                                        DbConnection.getEngine(),
                                        DbConnection.getEngineVersion());

      DbUtil.executeStatement(stmtText);

   }

   public static void createTable(String tblName, DbColumnDef[] colDefs,
         DbIndexDef[] indexDefs) throws Exception
   {
      createTable(tblName, colDefs);
      createIndices(tblName, indexDefs);
   }

   public static void dropTable(String tblName) throws Exception
   {

      String stmtText;

      stmtText = getDropTableStmtText(tblName);

      DbUtil.executeStatement(stmtText);

   }

   public static void createIndices(String tblName, DbIndexDef[] indexDefs)
         throws Exception
   {

      int count, i;

      count = indexDefs.length;

      for (i = 0; i < count; i++)
         createIndex(tblName, indexDefs[i]);

   }

   public static void dropIndices(String tblName, DbIndexDef[] indexDefs)
         throws Exception
   {

      int count, i;

      count = indexDefs.length;

      for (i = 0; i < count; i++)
         dropIndex(tblName, indexDefs[i].getName());

   }

   public static void createIndex(String tblName, DbIndexDef indexDef)
         throws Exception
   {

      String stmtText;

      stmtText = getCreateIndexStmtText(tblName, indexDef);

      DbUtil.executeStatement(stmtText);

   }

   public static void dropIndex(String tblName, String indexName)
         throws Exception
   {

      String stmtText;

      stmtText = getDropIndexStmtText(tblName, indexName, DbConnection
            .getEngine());

      DbUtil.executeStatement(stmtText);

   }
   
   public static void altertable(String tblName, DbColumnDef[] colDefs)
   						throws Exception
   {
      String stmtText;
      
      stmtText = getAlterTableStmtText(tblName,colDefs, DbConnection.getEngine());
      
      DbUtil.executeStatement(stmtText);
   
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
   
   
   	public static void altertable(int op, String tblName, DbColumnDef[] colDefs) throws Exception
	{
	String stmtText;
	int engine;
	engine = DbConnection.getEngine();
	//engine = DbEngine.ORACLE;
	//engine = DbEngine.POSTGRESQL;
	stmtText = getAlterTableStmtText(op,tblName,colDefs, engine);
	
	DbUtil.executeStatement(stmtText);
	
	}
    
    private static String getAlterTableStmtText(int op, String tblName,DbColumnDef[] colDefs,int engine) throws Exception{
	   int count;
	   int i;
	   StringBuffer buff = new StringBuffer();
	   
	   buff.append("ALTER TABLE ");
	   buff.append(tblName);
	   DbColumnDef  colDef = null;
	   if ( ( op & MODIFY )== MODIFY )
		   buff.append(" MODIFY ");
	   else if ( ( op & ADD )== ADD )
  			buff.append(" ADD ");
	   else
		   throw new Exception ("La operacion no esta permitida");
		   
	   count = colDefs.length;
	   
	   if (engine == DbEngine.ORACLE)
		   buff.append("(");
	   for (i = 0; i < count -1; i++)
	   {
		   colDef = colDefs[i];
		   buff.append(colDef.getName());
		   buff.append(colDef.toString());
		   buff.append(",");
	   }
	   colDef = colDefs[count - 1 ];
	   buff.append(colDef.getName());
	   buff.append(" ");
	   if ( ( op & CHANGE_NULLABILITY )== CHANGE_NULLABILITY ) 
		   buff.append(colDef.toStringValue(true));
	   else
		   buff.append(colDef.toString());	   	   
	   
	   if (engine == DbEngine.ORACLE)
		   buff.append(")");
	   return buff.toString();
   }
   public static final int MODIFY = 1;
   public static final int ADD = 2;
   public static final int CHANGE_NULLABILITY = 4;
   
*/
} // class