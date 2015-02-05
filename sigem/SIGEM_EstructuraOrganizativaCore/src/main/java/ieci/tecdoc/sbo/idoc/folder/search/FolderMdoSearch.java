
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.db.DBSessionManager;

import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenExtFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenMultFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenRelFlds;
import ieci.tecdoc.sbo.idoc.folder.field.FolderMdoFldsToken;
import ieci.tecdoc.sbo.idoc.folder.std.FolderMdoFdr;
import ieci.tecdoc.sbo.idoc.dao.*;
import ieci.tecdoc.sbo.idoc.acs.AcsInfo;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbOutputRecordSet;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;


public final class FolderMdoSearch
{     
  
   public FolderMdoSearch()
   {   
     
   }
   
   public static FolderSearchResult executeQuery(AcsAccessToken accToken,
                                                 ArchiveToken arch,
                                                 FolderSearchQuery query, String entidad)
                                    throws Exception
   {
      FolderSearchResult  rs   = null;  
      String              qual = null;
       
      DbConnection dbConn=new DbConnection(); 
      try{
    	dbConn.open(DBSessionManager.getSession(entidad));
	      qual = query.getSqlQual(dbConn, dbConn.getEngine());
	      
	      rs =  executeQuery(accToken, arch, qual, entidad);
      }catch (Exception e) {
    	  throw e;
	}finally{
		dbConn.close();
	}
      return rs;
   }
   
   public static FolderSearchResult executeQuery(AcsAccessToken accToken,
                                                 ArchiveToken arch,
                                                 String qual, String entidad)
                                    throws Exception
   {
      FolderSearchResult         rs     = null;      
      IeciTdLongIntegerArrayList fdrIds = null; 
      int                        fdrId, i, numFdrs;
      
      rs = new FolderSearchResult();
      
      fdrIds = getVisibleFolderIds(accToken, arch, qual, entidad);
      
      numFdrs = fdrIds.count();
      
      for(i = 0; i < fdrIds.count(); i++)
      {                
         fdrId  = fdrIds.get(i);         
         
         rs.addFolder(fdrId);
      }     
      
      return rs;
   }


   public static FolderTokenFlds getFolderValues(DbConnection dbConn, ArchiveToken arch, FolderSearchResult result,
                                                 int idx, String entidad)
                          throws Exception
   {
      int                 fdrId ;
      FolderTokenFlds     fldVals  = null; 
      FolderTokenRelFlds  relVals  = null;
      FolderTokenMultFlds multVals = null;
      FolderTokenExtFlds  extVals  = null;
      
      fdrId = result.getFolderId(idx);      
      
      fldVals = new FolderTokenFlds();  
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId, entidad);
      fldVals.setRelFlds(relVals);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(dbConn, arch, fdrId);
      fldVals.setMultFlds(multVals);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(dbConn, arch, fdrId);
      fldVals.setExtFlds(extVals);
      
      return fldVals;
   }   
   
   public static FolderTokenRelFlds getRelFldsValues(ArchiveToken arch,
                                                     FolderSearchResult result,
                                                     int idx, String entidad)
                                    throws Exception
   {      
      
      FolderTokenRelFlds  relVals = null;
      int                 fdrId;
      
      fdrId   = result.getFolderId(idx); 
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId, entidad);  
      
      
      return relVals;
   }
   
   public static FolderTokenExtFlds getExtFldsValues(DbConnection dbConn, ArchiveToken arch,
                                                     FolderSearchResult result,
                                                     int idx)
                                    throws Exception
   {      
      FolderTokenExtFlds  extVals = null;
      int                 fdrId;      
      
      fdrId   = result.getFolderId(idx);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(dbConn, arch, fdrId);      
      
      return extVals;
   }
   
   public static FolderTokenMultFlds getMultFldsValues(DbConnection dbConn, ArchiveToken arch,
                                                       FolderSearchResult result,
                                                       int idx)
                                     throws Exception
   {      
      FolderTokenMultFlds  multVals = null;
      int                  fdrId;      
      
      fdrId   = result.getFolderId(idx);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(dbConn, arch, fdrId);      
      
      return multVals;
   }    
   
   private static boolean canViewFolder(DbConnection dbConn, AcsAccessToken accToken, ArchiveToken arch, AcsInfo fdrAcsInfo)
                          throws Exception
   { 
      boolean  can       = false;      
      
      can = FolderMdoFdr.canLoadFolder(dbConn, accToken, arch, fdrAcsInfo); 

      return can;      
   }  

   public static IeciTdLongIntegerArrayList getVisibleFolderIds(AcsAccessToken accToken,
                                                                ArchiveToken arch,
                                                                String qual, String entidad)
                                            throws Exception
   {           
      IeciTdLongIntegerArrayList    fdrIds     = new IeciTdLongIntegerArrayList();  
      boolean                       canViewFdr;
      int                           fdrId;
      int                           numFdrs, i;                                         
      ieci.tecdoc.sgm.base.search.FolderSearchDaoAcsFdrInfoRow  row           = null;
      ieci.tecdoc.sgm.base.search.FolderSearchDaoAcsFdrInfoRows rows          = new ieci.tecdoc.sgm.base.search.FolderSearchDaoAcsFdrInfoRows();
      String                        relTblName    = DaoUtil.getRelFldsTblName(arch.getTblPrefix()); 
      String                        fdrHdrTblName = DaoUtil.getFdrHdrTblName(arch.getTblPrefix());
      String                        fdrIdColName, acsIdColName, acsTypeColName, relFdrIdColName; 
      DaoFdrHdrTbl                  fdrHdrTbl     = new DaoFdrHdrTbl(fdrHdrTblName);
      String                        tblNames,colNames;
      String                        sqlQual, sqlWhere, sqlOrderBy;
      AcsInfo                       fdrAcsInfo = null;
      int                           idx;
      

      fdrIdColName    = fdrHdrTbl.getIdColName(true);
      acsTypeColName  = fdrHdrTbl.getAccessTypeColName(true);
      acsIdColName    = fdrHdrTbl.getAcsIdColName(true);
      relFdrIdColName = relTblName + "." + DaoUtil.getRelFldsTblFdrIdColName();
      
      colNames = relFdrIdColName + "," + acsIdColName + "," + acsTypeColName;
      tblNames = relTblName + "," + fdrHdrTblName;
      
      idx = qual.indexOf("ORDER BY");
      if (idx != -1)
      {
         sqlWhere   = qual.substring(0,idx); 
         sqlOrderBy = qual.substring(idx);
         if(sqlWhere.trim().equals(""))
         {
            sqlQual = " WHERE " + relFdrIdColName + " = " +
            		  fdrIdColName + " " + sqlOrderBy;
         }
         else
         {
            sqlQual = sqlWhere + " AND " + relFdrIdColName + " = " +
			 		  fdrIdColName + " " + sqlOrderBy;
         }
      }
      else
      {
         if(qual.trim().equals(""))
         {
            sqlQual = " WHERE " + relFdrIdColName + " = " + fdrIdColName ;
         }
         else
         {
            sqlQual = qual + " AND " + relFdrIdColName + " = " + fdrIdColName ;
         }
      }     
      DbConnection dbConn=new DbConnection(); 
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));    
	  	DbSelectFns.select(dbConn, tblNames, colNames, sqlQual, false, rows);

      numFdrs = rows.count(); 
      
      for(i = 0; i < numFdrs; i++)
      {
         canViewFdr = true;

         row   = rows.getRecord(i);         
         fdrId = row.getFdrId();
        
         fdrAcsInfo = new AcsInfo(row.getAcsType(), row.getAcsId());
         
         if (accToken != null)
         {
            canViewFdr = canViewFolder(dbConn, accToken, arch, fdrAcsInfo);
         }
         
         if (canViewFdr)
            fdrIds.add(fdrId);
      }     
      }catch (Exception e) {
    	  throw e;
	  }finally{
		  dbConn.close();
	  }     
      return fdrIds;
   }
      
} // class
