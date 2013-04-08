
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.search;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecordSet;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.acs.AcsInfo;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.ArchiveToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.*;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenExtFlds;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenFlds;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenMultFlds;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenRelFlds;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.field.FolderMdoFldsToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.std.FolderMdoFdr;



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
    	dbConn.open(DBSessionManager.getSession());
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
      es.ieci.tecdoc.isicres.admin.base.search.FolderSearchDaoAcsFdrInfoRow  row           = null;
      es.ieci.tecdoc.isicres.admin.base.search.FolderSearchDaoAcsFdrInfoRows rows          = new es.ieci.tecdoc.isicres.admin.base.search.FolderSearchDaoAcsFdrInfoRows();
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
      	dbConn.open(DBSessionManager.getSession());    
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
