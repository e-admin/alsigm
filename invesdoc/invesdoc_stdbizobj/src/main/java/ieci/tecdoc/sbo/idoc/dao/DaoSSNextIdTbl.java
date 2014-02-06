package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public class DaoSSNextIdTbl
{
// **************************************************************************
   private static final String TN = "IDOCSSNEXTID";
        
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = {CD_ID};  
   
// **************************************************************************
   
   public static void initTblContentsSSNextId() throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( 1 )" ;            
      DbUtil.executeStatement(stmtText);
      
   }
   
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN,ACD);
   } 
   
   public static void dropTable() throws Exception
   {
      DbTableFns.dropTable(TN);
   }
   
}
