/*
 * Created on 12-sep-2005
 *
 */
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbOutputRecordSet;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.dyndao.DynDaoRs;


/**
 * @author egonzalez
 */
public class VldDaoHierarchicalVldTbl
{
   
   
   private String TN = null;
   
   private static final DbColumnDef CD_PARENT = new DbColumnDef
   ("IDOCPARENTKEY", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_CHILD = new DbColumnDef
   ("IDOCCHILDKEY", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] FIELDS =
   {CD_PARENT, CD_CHILD};   
   
   private static final String ACN = DbUtil.getColumnNames(FIELDS);
   
   public VldDaoHierarchicalVldTbl (String tn)
   {      
      TN = tn;
   }
   
   private String getDefaultQual(int parent, int child)
   {
      return "WHERE " + CD_PARENT.getName() + "= " + parent + " AND " 
      	+ CD_CHILD.getName() + "= " + child ;
   }
   
   protected String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  TN +  "." + colName;
      
      return colName;
   }   
   
   public String getTblName()
   {      
      return TN;
   }   
   
   public boolean existRow(int parent, int child) throws Exception
   {

      boolean rc = false;
      
      DynDaoRs  rs  = new DynDaoRs(FIELDS);

      DbSelectFns.select(TN, 
                         ACN,
                         getDefaultQual(parent, child), 
                         false, 
                         rs);
      
      return rs.count() > 0;

   }   
}

