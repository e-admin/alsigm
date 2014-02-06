
package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbSelectFns;
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


public final class FolderMdoSearch
{     
  
   public FolderMdoSearch()
   {   
     
   }
   
   public static FolderSearchResult executeQuery(AcsAccessToken accToken,
                                                 ArchiveToken arch,
                                                 FolderSearchQuery query)
                                    throws Exception
   {
      FolderSearchResult  rs   = null;  
      String              qual = null;
       
      qual = query.getSqlQual(DbConnection.getEngine());
      
      rs =  executeQuery(accToken, arch, qual);
      
      return rs;
   }
   
   public static FolderSearchResult executeQuery(AcsAccessToken accToken,
                                                 ArchiveToken arch,
                                                 String qual)
                                    throws Exception
   {
      FolderSearchResult         rs     = null;      
      IeciTdLongIntegerArrayList fdrIds = null; 
      int                        fdrId, i, numFdrs;
      
      rs = new FolderSearchResult();
      
      fdrIds = getVisibleFolderIds(accToken, arch, qual);
      
      numFdrs = fdrIds.count();
      
      for(i = 0; i < fdrIds.count(); i++)
      {                
         fdrId  = fdrIds.get(i);         
         
         rs.addFolder(fdrId);
      }     
      
      return rs;
   }


   public static FolderTokenFlds getFolderValues(ArchiveToken arch, FolderSearchResult result,
                                                 int idx)
                          throws Exception
   {
      int                 fdrId ;
      FolderTokenFlds     fldVals  = null; 
      FolderTokenRelFlds  relVals  = null;
      FolderTokenMultFlds multVals = null;
      FolderTokenExtFlds  extVals  = null;
      
      fdrId = result.getFolderId(idx);      
      
      fldVals = new FolderTokenFlds();  
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId);
      fldVals.setRelFlds(relVals);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(arch, fdrId);
      fldVals.setMultFlds(multVals);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(arch, fdrId);
      fldVals.setExtFlds(extVals);
      
      return fldVals;
   }   
   
   public static FolderTokenRelFlds getRelFldsValues(ArchiveToken arch,
                                                     FolderSearchResult result,
                                                     int idx)
                                    throws Exception
   {      
      
      FolderTokenRelFlds  relVals = null;
      int                 fdrId;
      
      fdrId   = result.getFolderId(idx); 
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId);  
      
      
      return relVals;
   }
   
   public static FolderTokenExtFlds getExtFldsValues(ArchiveToken arch,
                                                     FolderSearchResult result,
                                                     int idx)
                                    throws Exception
   {      
      FolderTokenExtFlds  extVals = null;
      int                 fdrId;      
      
      fdrId   = result.getFolderId(idx);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(arch, fdrId);      
      
      return extVals;
   }
   
   public static FolderTokenMultFlds getMultFldsValues(ArchiveToken arch,
                                                       FolderSearchResult result,
                                                       int idx)
                                     throws Exception
   {      
      FolderTokenMultFlds  multVals = null;
      int                  fdrId;      
      
      fdrId   = result.getFolderId(idx);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(arch, fdrId);      
      
      return multVals;
   }    
   
   private static boolean canViewFolder(AcsAccessToken accToken, ArchiveToken arch, AcsInfo fdrAcsInfo)
                          throws Exception
   { 
      boolean  can       = false;      
      
      can = FolderMdoFdr.canLoadFolder(accToken, arch, fdrAcsInfo); 

      return can;      
   }  

   public static IeciTdLongIntegerArrayList getVisibleFolderIds(AcsAccessToken accToken,
                                                                ArchiveToken arch,
                                                                String qual)
                                            throws Exception
   {           
      IeciTdLongIntegerArrayList    fdrIds     = new IeciTdLongIntegerArrayList();  
      boolean                       canViewFdr;
      int                           fdrId;
      int                           numFdrs, i;                                         
      FolderSearchDaoAcsFdrInfoRow  row           = null;
		FolderSearchDaoAcsFdrInfoRows rows          = new FolderSearchDaoAcsFdrInfoRows();
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
		         
	  DbSelectFns.select(tblNames, colNames, sqlQual, false, rows);

      numFdrs = rows.count(); 
      
      for(i = 0; i < numFdrs; i++)
      {
         canViewFdr = true;

         row   = rows.getRecord(i);         
         fdrId = row.getFdrId();
        
         fdrAcsInfo = new AcsInfo(row.getAcsType(), row.getAcsId());
         
         if (accToken != null)
         {
            canViewFdr = canViewFolder(accToken, arch, fdrAcsInfo);
         }
         
         if (canViewFdr)
            fdrIds.add(fdrId);
      }     
      
      return fdrIds;
   }
      
} // class
