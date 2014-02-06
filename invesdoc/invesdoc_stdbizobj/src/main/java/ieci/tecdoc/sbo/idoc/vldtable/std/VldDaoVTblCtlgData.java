
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.dyndao.DynDaoTbl;
import ieci.tecdoc.core.dyndao.DynDaoRs;
import ieci.tecdoc.core.dyndao.DynDaoRec;
import ieci.tecdoc.core.textutil.UtilX;

import java.text.StringCharacterIterator;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class VldDaoVTblCtlgData extends DynDaoTbl
{
   
   private static Logger logger = Logger.getLogger(VldDaoVTblCtlgData.class);

   // **************************************************************************

   private DbColumnDef[] colsDef ;
   private ArrayList colsDefArray = new ArrayList();
   private String[] colsName ;
   private ArrayList colNameArray = new ArrayList() ;
   private String pkColName;
   private String qual="";
   private String orderBy="";


// **************************************************************************

   public String getDefaultQual()
   {
      return qual;
   }   
   public void setDefaultQual( String pQual )
   {
      qual = pQual ;
   }
   public String getOrderBy()
   {
      return orderBy;
   }
   public void setOrderBy( String pOrderBy )
   {
      orderBy = "";
      if ( !pOrderBy.equals("") )
      {
         orderBy = "ORDER BY " +pOrderBy;
      }
   }

   private String getPkQual(int id)
   {
      String qualValue = generateQual( "WHERE " + pkColName + "= " + id );
      return qualValue;
   }

   private String generateQual(String Qual)
   {
      String qualValue="";
      if ( !qual.equals("") )
      {
         qualValue = Qual  + " AND " + qual;
      }
      else
      {
         qualValue = Qual;
      }
      return qualValue;
   }

   public void setPkColName(String pkName)
   {
      pkColName=pkName;
   }

   public void createColsDef( String info , String tableName ) throws Exception
   {
      StringCharacterIterator iterator  = new StringCharacterIterator(info);
      char                    sep       = '|';
      String vers;
      int numCols;

      //versión
      vers = UtilX.parseString(iterator);
      UtilX.iteratorIncrementIndex(iterator, 1);

      //Número de campos
      numCols = UtilX.parseInteger(iterator , sep) ;
      for(int i = 0; i < numCols; i++)
      {

         createColDef(iterator, sep,i);

      }

      colsName = (String[])colNameArray.toArray(new String[0]);
      colsDef = (DbColumnDef[])colsDefArray.toArray( new DbColumnDef[0] ) ;
      super.init(tableName,colsDef);

   }

   // **************************************************************************
   public void createColDef(StringCharacterIterator iterator, char sep,int index)
   throws Exception
   {
      String dbColName;
      int colType;
      int colLen;
      boolean colNulls;
      UtilX.iteratorIncrementIndex(iterator, 1);
      dbColName = UtilX.parseString(iterator) ;
      colNameArray.add(dbColName);

      UtilX.iteratorIncrementIndex(iterator, 1);
      colType = UtilX.parseInteger(iterator, ',') ;

      UtilX.iteratorIncrementIndex(iterator, 1);
      colLen = UtilX.parseInteger(iterator, ',') ;

      UtilX.iteratorIncrementIndex(iterator, 1);
      if ( UtilX.parseInteger(iterator, sep)==0 ){
         colNulls=true;
      }else{
         colNulls=false;
      }

      addColDef(new DbColumnDef(dbColName,colType ,colLen,colNulls ));
   }

   public void addColDef( DbColumnDef colDef) throws Exception
   {
      colsDefArray.add( colDef );
   }
   public DbColumnDef[] getColDef() throws Exception
   {
      return colsDef;
   }
   public DynDaoRs selectData()
                               throws Exception
   {
      if ( logger.isDebugEnabled() )
         logger.debug("getDefaultQual(): " + getDefaultQual());
      String qual = getDefaultQual();
      if ( !qual.equals("") )
      {
         qual = "WHERE "+getDefaultQual() ;
      }
      qual += " " + orderBy;
      
      DynDaoRs  rs = new DynDaoRs(colsDef);
      rs = super.selectRows(colsDef ,qual );

      return rs;
   }

   public DynDaoRs selectByVal(String Qual)
                               throws Exception
   {

      String qualVal= generateQual( Qual );
      qualVal += " " + orderBy;
      
      if ( logger.isDebugEnabled() )
         logger.debug("qualVal: " + qualVal);
            
      DynDaoRs  rs = new DynDaoRs(colsDef);
      rs = super.selectRows(colsDef ,qualVal );

      return rs;
   }

   public DynDaoRec selectDataByPk(int id)
                               throws Exception
   {
      if ( logger.isDebugEnabled() )
         logger.debug("getPkQual(id): " + getPkQual(id));

      DynDaoRec  rec = new DynDaoRec(this.colsDef);
      
      super.selectRow(getPkQual(id), rec);
      
      return rec;
   }
} // class
