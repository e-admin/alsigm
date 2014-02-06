
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.collections.IeciTdLiLiArrayList;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.idoc.acs.AcsInfo;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoArchive;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoFolder;
import ieci.tecdoc.sbo.idoc.acs.AcsTokenArchive;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenArchHdr;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveTokenFlds;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFlag;
import ieci.tecdoc.sbo.idoc.archive.std.ArchiveMdoToken;
import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrHdrRow;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTree;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenExtFld;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenExtFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenMultFld;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenMultFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderDocUpdIds;
import ieci.tecdoc.sbo.idoc.folder.divider.FolderMdoDivider;
import ieci.tecdoc.sbo.idoc.folder.document.FolderMdoDocument;
import ieci.tecdoc.sbo.idoc.folder.document.FolderMdoDocumentFile;
import ieci.tecdoc.sbo.idoc.folder.fdrlink.FolderMdoFdrLink;
import ieci.tecdoc.sbo.idoc.folder.field.FolderMdoFlds;
import ieci.tecdoc.sbo.idoc.folder.document.FolderMdoDocumentAnn;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.config.CfgFtsConfig;

import java.io.File;
import java.util.ArrayList;

public class FolderMdoFdr
{


	

   private FolderMdoFdr()
   {      
   } 
   
   public static boolean canLoadFolder(AcsAccessToken accToken,
                                       int archId,
                                       int fdrId) 
                         throws Exception
   {
      
      boolean  can = false;     
      
      if (accToken == null) return true;
      
      can = AcsMdoFolder.hasAuthView(accToken, archId, fdrId);           
         
      return can;
      
   }
   
   public static boolean canLoadFolder(AcsAccessToken accToken,
                                       ArchiveToken arch,
                                       int fdrId) 
                         throws Exception
   {
      
      boolean             can         = false;
      AcsInfo             fdrAcsInfo  = null;
            
      if (accToken == null) return true; 
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);
      
      can = canLoadFolder(accToken, arch, fdrAcsInfo);
         
      return can;
      
   }
   
   
   
   public static FolderTokenFlds fetchFolderValues(AcsAccessToken accToken,
                                                   ArchiveToken arch,
                                                   int fdrId) 
                         throws Exception
   {
      FolderTokenFlds fldValues = null;
           
      checkCanLoadFolder(accToken, arch, fdrId); 
      
      fldValues = FolderMdoToken.createFolderTokenFlds(arch, fdrId);        
         
      return fldValues;        
   }
   
   public static FolderTokenDocTree fetchFolderDocTree(AcsAccessToken accToken,
                                                       ArchiveToken arch,
                                                       int fdrId) 
                         throws Exception
   {
      FolderTokenDocTree docTree = null;
           
      checkCanLoadFolder(accToken, arch, fdrId); 
      
      docTree = FolderMdoToken.createFolderTokenDocTree(arch, fdrId);        
         
      return docTree;        
   }
   
   public static FolderToken loadFolder(AcsAccessToken accToken, 
                                        int userId,
                                        ArchiveToken arch,
                                        int fdrId)
                             throws Exception
   {
      
      FolderToken token       = null;
      AcsInfo     fdrAcsInfo  = null; 
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);
      
      checkCanLoadFolder(accToken, arch, fdrAcsInfo);          
            
      token =  FolderMdoToken.createFolderToken(arch, fdrId, fdrAcsInfo.getAcsId(),
                                                fdrAcsInfo.getAcsType());     
      
      //Se actualiza el último que accede a la carpeta;  
      
      try
      {

         DbConnection.beginTransaction();
         
         updateAccessData(arch, fdrId, userId);
         
         DbConnection.endTransaction(true); 

      }
      catch (Exception e)
      {

         DbConnection.ensureEndTransaction(e);

      }  
      
      return token;    
      
   }
   
   public static FolderToken loadFolder(AcsAccessToken accToken, 
                                        int userId,
                                        ArchiveToken arch,
                                        int fdrId,
                                        FolderTokenFlds fldsValues)
                             throws Exception
   {
      FolderToken token       = null;
      AcsInfo     fdrAcsInfo  = null; 
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);
      
      checkCanLoadFolder(accToken, arch, fdrAcsInfo);          
            
      token =  FolderMdoToken.createFolderToken(arch, fdrId, fdrAcsInfo.getAcsId(),
                                                fdrAcsInfo.getAcsType(), 
                                                fldsValues);     
      
      //Se actualiza el último que accede a la carpeta;  
      
      try
      {

         DbConnection.beginTransaction();
         
         updateAccessData(arch, fdrId, userId);
         
         DbConnection.endTransaction(true); 

      }
      catch (Exception e)
      {

         DbConnection.ensureEndTransaction(e);

      } 
      
      return token;  
   
   }
   
   public static boolean canRemoveFolder(AcsAccessToken accToken,
                                         int archId,
                                         int fdrId) 
                         throws Exception
   {
      
      boolean  can = false;      
      
      if (accToken == null) return true;
      
      can = AcsMdoFolder.hasAuthDelete(accToken, archId, fdrId);        
         
      return can;
      
   }
   
   public static boolean canRemoveFolder(AcsAccessToken accToken,
                                         ArchiveToken arch,
                                         int fdrId) 
                         throws Exception
   {
      
      boolean   can        = false;
      AcsInfo  fdrAcsInfo  = null;
            
      if (accToken == null) return true; 
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);
      
      can = canRemoveFolder(accToken, arch, fdrAcsInfo);
         
      return can;
      
   }
   
   public static void removeFolder(AcsAccessToken accToken, 
                                   int userId,
                                   ArchiveToken arch,
                                   int fdrId)
                      throws Exception
   {
      
      AcsInfo  fdrAcsInfo  = null;  
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);     
      
      checkCanRemoveFolder(accToken, arch, fdrId, fdrAcsInfo);
      
      removeFolder(userId, arch.getTblPrefix(), arch.getId(),
                   arch.getFlds(), fdrId, fdrAcsInfo);
      
   }
   
   public static void removeFolder(AcsAccessToken accToken, 
                                   int userId,
                                   int archId,
                                   int fdrId)
                      throws Exception
   {
      AcsInfo                    fdrAcsInfo  = null; 
      AcsInfo                    archAcsInfo  = null; 
      String                     tblPrefix;
      ArchiveTokenFlds           fldDefs = null;
      
      archAcsInfo = AcsMdoArchive.getAcsInfo(archId);
      tblPrefix   = DaoArchHdrTbl.selectTblPrefix(archId);   
      fdrAcsInfo  = AcsMdoFolder.getAcsInfo(tblPrefix, fdrId);
      
      checkCanRemoveFolder(accToken, tblPrefix, archAcsInfo, fdrId, fdrAcsInfo);
      
      fldDefs = ArchiveMdoToken.createArchiveTokenFlds(archId);      
      
      removeFolder(userId, tblPrefix, archId, fldDefs, 
                   fdrId, fdrAcsInfo);  
      
   }  
   
   public static boolean canCreateFolder(AcsAccessToken accToken,
                                         int archId) 
                         throws Exception
   {
      
      boolean  can = false;
            
      if (accToken == null) return true;
      
      can = AcsMdoArchive.hasAuthCreate(accToken, archId);        
         
      return can;      
      
   } 
   
   public static boolean canCreateFolder(AcsAccessToken accToken,
                                         ArchiveToken arch) 
                         throws Exception
   {
      
      boolean             can         = false;
      boolean             has         = false;
      AcsInfo             archAcsInfo = null;
      AcsTokenArchive     acsArch     = arch.getAcs();
      ArchiveTokenArchHdr archHdr     = arch.getArchHdr();      
      
      if (accToken == null) return true;
     
      if (acsArch == null)
      {
         archAcsInfo = new AcsInfo(archHdr.getAccessType(), archHdr.getAcsId());
         
         has = AcsMdoArchive.hasAuthCreate(accToken, archAcsInfo);
      }
      else
         has = AcsMdoArchive.hasAuthCreate(accToken, acsArch);
      
      if (has)
         can = true;
      else
         can = false; 
            
      return can;
      
   }
   
   public static FolderToken newFolder(AcsAccessToken accToken,
                                       int archId)
                             throws Exception
   {
      
      FolderToken  token     = null;
      ArchiveToken archToken = null; 
           
      checkCanCreateFolder(accToken, archId);
      
      archToken = ArchiveMdoToken.createArchiveToken(null, archId);
                  
      token = FolderMdoToken.createFolderToken(archToken);            
      
      return token;    
      
   }
   
   public static FolderToken newFolder(AcsAccessToken accToken,
                                       ArchiveToken arch)
                             throws Exception
   {
      
      FolderToken  token = null; 
           
      checkCanCreateFolder(accToken, arch);
                  
      token = FolderMdoToken.createFolderToken(arch);            
      
      return token;    
      
   }
   
    //fdr es un parámetro de entrada, salida 
   public static void createFolderData(int userId,
                                       ArchiveToken arch,
                                       FolderToken fdr)
                      throws Exception
   {
      //FolderToken token = null;      
      
      if (!fdr.isNew())
      {
         throw new IeciTdException(FolderError.EC_NOT_FDR_NEW, 
                                   FolderError.EM_NOT_FDR_NEW);
      }
      
      insertFolderValues(userId, arch, fdr);  
      
   }


   //fdr es un parámetro de entrada, salida   
   public static void createFolder(int userId,
                                   ArchiveToken arch,
                                   FolderToken fdr)
                      throws Exception
   {
           
      if (!fdr.isNew())
      {
         throw new IeciTdException(FolderError.EC_NOT_FDR_NEW, 
                                   FolderError.EM_NOT_FDR_NEW);
      }
      
      insertFolderValues(userId, arch, fdr);
      storeDocumentTree(userId, arch, fdr);           
      
   }
   
   public static boolean canEditFolder(AcsAccessToken accToken,
                                       int archId, int fdrId)
                         throws Exception
   { 
      boolean  can = false;          
      
      if (accToken == null) return true;
      
      can = AcsMdoFolder.hasAuthUpdate(accToken, archId, fdrId);        
              
      return can;
   }
   
   public static boolean canEditFolder(AcsAccessToken accToken,
                                       ArchiveToken arch,
                                       int fdrId)
                         throws Exception
   { 
      boolean             can         = false;
      boolean             has         = false;
      AcsInfo             archAcsInfo = null;
      AcsTokenArchive     acsArch     = arch.getAcs();
      ArchiveTokenArchHdr archHdr     = arch.getArchHdr();      
      AcsInfo             fdrAcsInfo  = null;
            
      if (accToken == null) return true; 
      
      fdrAcsInfo = AcsMdoFolder.getAcsInfo(arch.getTblPrefix(), fdrId);
      
      if (acsArch == null)
      {
         archAcsInfo = new AcsInfo(archHdr.getAccessType(), archHdr.getAcsId());
         
         has = AcsMdoFolder.hasAuthUpdate(accToken, archAcsInfo, fdrAcsInfo);
      }
      else
         has = AcsMdoFolder.hasAuthUpdate(accToken, acsArch, fdrAcsInfo);

		if (has)
			can = true;
		else
		   can = false;

      return can;
   }
   
   public static void editFolder(AcsAccessToken accToken, int userId, int archId,
                                 int fdrId) 
                      throws Exception
   {
      checkCanEditFolder(accToken, archId, fdrId);
      
      FolderMdoLock.lock(archId, fdrId, userId);
   }
   
   public static void editFolder(AcsAccessToken accToken, int userId, 
                                 ArchiveToken arch, int fdrId) 
                      throws Exception
   {
      checkCanEditFolder(accToken, arch, fdrId);
      
      FolderMdoLock.lock(arch.getId(), fdrId, userId);
   }     
   
   public static void terminateEditFolder(int userId, int archId, int fdrId) 
                      throws Exception
   {
      checkLockFolder(userId, archId, fdrId);
      
      FolderMdoLock.unlock(archId, fdrId, userId);
   }
   
    //fdr es un parámetro de entrada, salida 
   public static void storeFolderData(int userId, ArchiveToken arch,
                                      FolderToken fdr)
                      throws Exception
   { 
      //FolderToken    token = null;
      
      if (fdr.isNew())
      {
         throw new IeciTdException(FolderError.EC_FDR_IS_NEW, 
                                   FolderError.EM_FDR_IS_NEW);
      }
      
      //Se debe chequear si cuando se va a hacer el Store esta carpeta sigue
      // bloqueada por el mismo usuario. 

      checkLockFolder(userId, arch.getId(), fdr.getId());
      storeFolderValues(userId, arch, fdr);      
      
   }
   
    //fdr es un parámetro de entrada, salida 
   public static void storeFolder(int userId, ArchiveToken arch,
                                  FolderToken fdr)
                      throws Exception
   { 
            
      if (fdr.isNew())
      {
         throw new IeciTdException(FolderError.EC_FDR_IS_NEW, 
                                   FolderError.EM_FDR_IS_NEW);
      }
      
      //Se debe chequear si cuando se va a hacer el Store esta carpeta sigue
      // bloqueada por el mismo usuario. 

      checkLockFolder(userId, arch.getId(), fdr.getId());
      storeFolderValues(userId, arch, fdr);
      storeDocumentTree(userId, arch, fdr); 
   }
   
   public static  byte[] retrieveFolderDocumentFile(AcsAccessToken accToken,
                                                    ArchiveToken arch,
                                                    FolderToken fdr,
                                                    int docId)
                         throws Exception
   {  
      byte[] fileData = null;
      
      fileData = FolderMdoDocumentFile.retrieveFile(fdr, docId);
      
      return fileData;     
   }  
   
   public static String retrieveFolderDocumentAnnData(AcsAccessToken accToken,
                                                      ArchiveToken arch,
                                                      FolderToken fdr,
                                                      int docId)
                         throws Exception
   {  
      String data   = null;
      int    userId = 0;
      
      if (accToken != null)
         userId = accToken.getUserId();
   
      data = FolderMdoDocumentAnn.retrieveAnnData(userId, fdr, docId);
   
      return data;     
   }  
   
   public static int getFileSize (AcsAccessToken accToken,
										           ArchiveToken arch,
										           FolderToken fdr,
										           int docId)
							throws Exception
	{  
		int size = -1;
		
		size = FolderMdoDocumentFile.getFileSize (fdr, docId);
		
		return size;     
	}  
   
   private static void checkLockFolder(int userId, int archId,
                                       int fdrId)
                       throws Exception
   {
      
      FolderLockInfo lockInfo = null;
   
      lockInfo =  FolderMdoLock.getLockInfo(archId, fdrId);

      if ( !lockInfo.isLocked() ||        
           (userId != lockInfo.getLockerId()) )
      {
      
         throw new IeciTdException(FolderError.EC_FDR_NOT_LOCKED_BY_USER, 
                                   FolderError.EM_FDR_NOT_LOCKED_BY_USER);                
      
      }

   }
   
   public static boolean isFolderLockedByUser (int userId, int archId, int fdrId)  throws Exception
   {
      boolean rc = false;
      FolderLockInfo lockInfo = null;
      
      lockInfo =  FolderMdoLock.getLockInfo(archId, fdrId);

      if (lockInfo.isLocked() && (userId == lockInfo.getLockerId()))
         rc = true;

      return rc;
   }
   
   public static boolean isFolderLocked (int archId, int fdrId)  throws Exception
   {
      boolean rc = false;
      FolderLockInfo lockInfo = null;
      
      lockInfo =  FolderMdoLock.getLockInfo(archId, fdrId);
      
      rc = lockInfo.isLocked();

      return rc;
   }
   
   private static void checkCanLoadFolder(AcsAccessToken accToken,
                                          ArchiveToken arch,int fdrId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canLoadFolder(accToken, arch, fdrId))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_LOAD_FDR, 
                                   FolderError.EM_NOT_AUTH_LOAD_FDR);
      }
      
   }
   
   private static void checkCanLoadFolder(AcsAccessToken accToken,
                                          ArchiveToken arch,
                                          AcsInfo fdrAcsInfo)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canLoadFolder(accToken, arch, fdrAcsInfo))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_LOAD_FDR, 
                                   FolderError.EM_NOT_AUTH_LOAD_FDR);
      }
      
   }
   
   private static void checkCanRemoveFolder(AcsAccessToken accToken,
                                            ArchiveToken arch, int fdrId, 
                                            AcsInfo fdrAcsInfo)
                       throws Exception
   {
      
      if (accToken != null)
      {
            
         if (!canRemoveFolder(accToken, arch, fdrAcsInfo))
         {
            throw new IeciTdException(FolderError.EC_NOT_AUTH_REMOVE_FDR, 
                                      FolderError.EM_NOT_AUTH_REMOVE_FDR);
         }

      }

      if (getFolderRefCount(arch.getTblPrefix(), fdrId) > 0)
      {
          //Alguna carpeta tiene un acceso directo a esta
          throw new IeciTdException(FolderError.EC_FDR_IS_REFERENCED_BY_OTHER, 
                                    FolderError.EM_FDR_IS_REFERENCED_BY_OTHER);
      }
      
   }
   
   private static void checkCanRemoveFolder(AcsAccessToken accToken, String tblPrefix, 
                                            AcsInfo archAcsInfo, int fdrId, 
                                            AcsInfo fdrAcsInfo)
                       throws Exception
   {
       
      if (accToken != null)
      {
            
         if (!canRemoveFolder(accToken, archAcsInfo, fdrAcsInfo))
         {
            throw new IeciTdException(FolderError.EC_NOT_AUTH_REMOVE_FDR, 
                                      FolderError.EM_NOT_AUTH_REMOVE_FDR);
         }

      }

      if (getFolderRefCount(tblPrefix, fdrId) > 0)
      {
          //Alguna carpeta tiene un acceso directo a esta
          throw new IeciTdException(FolderError.EC_FDR_IS_REFERENCED_BY_OTHER, 
                                    FolderError.EM_FDR_IS_REFERENCED_BY_OTHER);
      }
      
   } 

   private static int getFolderRefCount(String tblPrefix, int fdrId)
                      throws Exception
   {
      
      DaoFdrHdrTbl  tbl = null;
      String        tblName;
      int           refCount;
      
      
      tblName = DaoUtil.getFdrHdrTblName(tblPrefix);
      
      tbl = new DaoFdrHdrTbl(tblName);
      
      refCount = tbl.selectRefCount(fdrId); 
      
      return refCount;
      
   } 
   
   private static void checkCanCreateFolder(AcsAccessToken accToken,
                                            int archId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canCreateFolder(accToken, archId))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_CREATE_FDR, 
                                   FolderError.EM_NOT_AUTH_CREATE_FDR);
      }
      
   }  
   
   private static void checkCanCreateFolder(AcsAccessToken accToken,
                                            ArchiveToken arch)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canCreateFolder(accToken, arch))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_CREATE_FDR, 
                                   FolderError.EM_NOT_AUTH_CREATE_FDR);
      }
      
   }  
   
   private static void checkCanEditFolder(AcsAccessToken accToken,
                                          ArchiveToken arch, int fdrId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canEditFolder(accToken, arch, fdrId))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_EDIT_FDR, 
                                   FolderError.EM_NOT_AUTH_EDIT_FDR);
      }
      
   }  
   
   private static void checkCanEditFolder(AcsAccessToken accToken,
                                          int archId, int fdrId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canEditFolder(accToken, archId, fdrId))
      {
         throw new IeciTdException(FolderError.EC_NOT_AUTH_EDIT_FDR, 
                                   FolderError.EM_NOT_AUTH_EDIT_FDR);
      }
      
   }   
   
   
   private static void updateAccessData(ArchiveToken arch, int fdrId, int userId)
                       throws Exception
   {
      
      DaoFdrHdrTbl           tbl = null;
      String                 tblName;
      int                    accCount;
      FolderDaoFdrHdrAccUpd  row;
      
      tblName = DaoUtil.getFdrHdrTblName(arch.getTblPrefix());
      
      tbl = new DaoFdrHdrTbl(tblName);
      
      accCount = tbl.selectAccCount(fdrId);
      
      row = new FolderDaoFdrHdrAccUpd();
      
      row.m_accrId   = userId;
      row.m_accDate  = DateTimeUtil.getCurrentDateTime();
      row.m_accCount = accCount + 1; 
      
      tbl.updateRow(fdrId, row);    
      
   }
   
   public static boolean canLoadFolder(AcsAccessToken accToken, 
                                       ArchiveToken arch,
                                       AcsInfo fdrAcsInfo) 
                         throws Exception
   {
      
      boolean             can         = false;     
      AcsInfo             archAcsInfo = null;
      AcsTokenArchive     acsArch     = arch.getAcs();
      ArchiveTokenArchHdr archHdr     = arch.getArchHdr();
      boolean             has         = false;      
      
      if (accToken == null) return true; 
      
      if (acsArch == null)
      {
         archAcsInfo = new AcsInfo(archHdr.getAccessType(), archHdr.getAcsId());
         
         has = AcsMdoFolder.hasAuthView(accToken, archAcsInfo, fdrAcsInfo);
      }
      else
         has = AcsMdoFolder.hasAuthView(accToken, acsArch, fdrAcsInfo);
      
      if (has)
         can = true;
      else
         can = false; 
         
      return can;
      
   }
   
   private static boolean canRemoveFolder(AcsAccessToken accToken, 
                                          ArchiveToken arch,
                                          AcsInfo fdrAcsInfo) 
                         throws Exception
   {
      
      boolean             can         = false;     
      AcsInfo             archAcsInfo = null;
      AcsTokenArchive     acsArch     = arch.getAcs();
      ArchiveTokenArchHdr archHdr     = arch.getArchHdr();
      boolean             has         = false;      
      
      if (accToken == null) return true; 
      
      if (acsArch == null)
      {
         archAcsInfo = new AcsInfo(archHdr.getAccessType(), archHdr.getAcsId());
         
         has = AcsMdoFolder.hasAuthDelete(accToken, archAcsInfo, fdrAcsInfo);
      }
      else
         has = AcsMdoFolder.hasAuthDelete(accToken, acsArch, fdrAcsInfo);
      
      if (has)
         can = true;
      else
         can = false; 
         
      return can;
      
   }
   
   private static boolean canRemoveFolder(AcsAccessToken accToken, 
                                          AcsInfo archAcsInfo,
                                          AcsInfo fdrAcsInfo) 
                         throws Exception
   {
      
      boolean   can  = false;
      boolean   has  = false;      
      
      if (accToken == null) return true; 
      
      has = AcsMdoFolder.hasAuthDelete(accToken, archAcsInfo, fdrAcsInfo);
      
      if (has)
         can = true;
      else
         can = false; 
         
      return can;
      
   }
   
   private static void removeFolder(int userId, String tblPrefix, int archId,             
                                    ArchiveTokenFlds fldDefs, int fdrId, AcsInfo fdrAcsInfo)
                      throws Exception
   {  
      IeciTdLongIntegerArrayList fileIds = null;      
      
      FolderMdoLock.lock(archId, fdrId, userId); 
      
      try
      {
         fileIds = FolderMdoDocument.getFolderFileIds(tblPrefix, fdrId);
         
         removeDBItems(tblPrefix, archId, fldDefs,
                       fdrId, fdrAcsInfo);          
         
      }
      catch(Exception e) 
      {
         
         FolderMdoLock.unlock(archId, fdrId, userId); 
                 
         throw(e);
                     
      }
      
      FolderMdoLock.unlock(archId, fdrId, userId);
      
      FolderMdoDocumentFile.deleteFiles(fileIds);     
      
   }
   
   private static void removeDBItems(String tblPrefix, int archId,             
                                     ArchiveTokenFlds fldDefs, int fdrId, AcsInfo fdrAcsInfo)
                      throws Exception
   {
      
      try
      {

         DbConnection.beginTransaction(); 
         
         FolderMdoDocument.deleteFolderDocuments(tblPrefix, archId, fdrId);
         FolderMdoDivider.deleteFolderDividers(tblPrefix, fdrId); 
         FolderMdoFdrLink.deleteFolderLinks(archId, fdrId);
         FolderMdoFlds.deleteFolderValues(fldDefs, tblPrefix, fdrId);
         
         deleteFdrHdrInfo(tblPrefix, fdrId);
         
         AcsMdoFolder.deleteAcsFolder(fdrAcsInfo, false);  
         
         FolderMdoXNId.removeFolderXNId(tblPrefix, fdrId);

         DbConnection.endTransaction(true);            
         

      }
      catch (Exception e)
      {

         DbConnection.ensureEndTransaction(e);

      }
      
   } 
   
   private static void deleteFdrHdrInfo(String tblPrefix, int fdrId)                                    
                       throws Exception
   { 
      String             tblName = DaoUtil.getFdrHdrTblName(tblPrefix);
      DaoFdrHdrTbl tbl = null;  
      
      tbl = new DaoFdrHdrTbl(tblName);
      
      tbl.deleteRow(fdrId);      
              
   }
   
    //fdr es un parámetro de entrada, salida 
   private static void insertFolderValues(int userId, ArchiveToken arch,   
                                          FolderToken  fdr)
                       throws Exception
   { 
      FolderTokenFlds    flds    = fdr.getFlds(); 
      //FolderTokenExtFlds extFlds = flds.getExtFlds();
      int                firstExtFldId = -1;
      int                numNewExtFlds;
      
      numNewExtFlds = flds.getNumNewExtFields();
      
      if(numNewExtFlds > 0)
      {
         firstExtFldId = FolderMdoXNextId.generateNextExtFldIds(arch.getId(),
                                                                numNewExtFlds);
      }
      
      try
      {

         DbConnection.beginTransaction();
         
         insertFdrHdrInfo(userId, arch.getTblPrefix(), fdr.getId());

         FolderMdoFlds.insertRelFldsValues(arch, fdr);
         FolderMdoFlds.insertMultFldValues(arch, fdr);         
         storeExtFldsValues(arch, fdr, firstExtFldId);
         
         fdr.setFdrInfo(fdr.getId(), fdr.getAccessType(),
                        fdr.getAccessId(), false);        
         fdr.getFlds().clearEditInfo();
                 
         DbConnection.endTransaction(true);

      }
      catch (Exception e)
      {

         DbConnection.ensureEndTransaction(e);

      }
      
   }
   
   private static void insertFdrHdrInfo(int userId, String tblPrefix,
                                        int fdrId)                                    
                       throws Exception
   { 
      DaoFdrHdrRow row = null;
      DaoFdrHdrTbl tbl = null;
      String       tblName = DaoUtil.getFdrHdrTblName(tblPrefix);
      
      row = fillFdrHdrRow(userId, fdrId);
      
      tbl = new DaoFdrHdrTbl(tblName);
      
      tbl.insertRow(row);      
              
   }
   
   private static DaoFdrHdrRow fillFdrHdrRow(int userId, int fdrId)
                               throws Exception
   {      
      DaoFdrHdrRow rec = new DaoFdrHdrRow();
      
      rec.m_id       = fdrId;  
      rec.m_versStat = FolderDefs.FDR_VERSTAT_CURRENT;  
      rec.m_refCount = 0;
      rec.m_acsType  = AcsAccessType.ACS_PUBLIC;
      rec.m_acsId    = SboType.NULL_ID;   
      rec.m_crtUsrId = userId;
      rec.m_crtTs    = DateTimeUtil.getCurrentDateTime();
      rec.m_updUsrId = SboType.NULL_ID;    
      rec.m_updTs    = DbDataType.NULL_DATE_TIME;
      rec.m_accUsrId = userId;    
      rec.m_accTs    = rec.m_crtTs;
      rec.m_accCount = 1; 
      
      return rec;
      
   }
   
   //fdr es un parámetro de entrada, salida 
   private static void storeFolderValues(int userId, ArchiveToken arch,   
                                         FolderToken  fdr)
                       throws Exception
   { 
      FolderTokenFlds    flds    = fdr.getFlds(); 
      //FolderTokenExtFlds extFlds = flds.getExtFlds();
      int                firstExtFldId = -1;
      int                numNewExtFlds;
      
      numNewExtFlds = flds.getNumNewExtFields();
      
      if(numNewExtFlds > 0)
      {
         firstExtFldId = FolderMdoXNextId.generateNextExtFldIds(arch.getId(),
                                                                numNewExtFlds);
      }
      
      try
      {

         DbConnection.beginTransaction();
         
         updateFdrHdrInfo(userId, arch.getTblPrefix(), fdr.getId());
         FolderMdoFlds.updateRelFldsValues(arch, fdr);
         storeMultFldsValues(arch, fdr);         
         storeExtFldsValues(arch, fdr, firstExtFldId);
                  
         fdr.getFlds().clearEditInfo();  
                 
         DbConnection.endTransaction(true);

      }
      catch (Exception e)
      {

         DbConnection.ensureEndTransaction(e);

      }
      
   }
   
   private static void updateFdrHdrInfo(int userId, String tblPrefix,
                                        int fdrId)                                    
                       throws Exception
   { 
      FolderDaoFdrHdrUpdatorUpd row = null;
      DaoFdrHdrTbl                 tbl = null;
      String                       tblName = DaoUtil.getFdrHdrTblName(tblPrefix);
      
      row = fillFdrHdrUpdatorUpdRow(userId);
      
      tbl = new DaoFdrHdrTbl(tblName);
      
      tbl.updateRow(fdrId, row);      
              
   }
   
   private static FolderDaoFdrHdrUpdatorUpd fillFdrHdrUpdatorUpdRow(int userId)
                                        throws Exception
   {      
      FolderDaoFdrHdrUpdatorUpd rec = new FolderDaoFdrHdrUpdatorUpd();
      
      rec.m_updUsrId = userId;    
      rec.m_updTs    = DateTimeUtil.getCurrentDateTime();
      
      return rec;
      
   }
   
   //Tiene que estar dentro de una transacción
   private static void storeExtFldsValues(ArchiveToken arch, FolderToken  fdr,
                                          int firstNewExtFldId)
                       throws Exception
   { 
      
      int                 i;
      FolderTokenExtFlds  extFlds    = fdr.getFlds().getExtFlds();
      FolderTokenExtFld   extFld     = null;    
      int                 nextId     = firstNewExtFldId;
      int                 numExtFlds = extFlds.count();      
      
      for(i = 0; i < numExtFlds; i++)
      {
         extFld = extFlds.get(i);      
         
         if ( extFld.isNew())       
         {
            FolderMdoFlds.insertExtFldValue(arch, fdr.getId(), extFld, nextId);
            nextId = nextId + 1;
         }
         else if (extFld.isUpdate())
         {
            FolderMdoFlds.updateExtFldValue(arch, fdr.getId(), extFld);
         }
         else if (extFld.isRemove())
         {
            FolderMdoFlds.deleteExtFldValue(arch.getTblPrefix(), fdr.getId(), 
                                            extFld.getId());
         }   
                
      }
      
   }
   
   //Tiene que estar dentro de una transacción
   private static void storeMultFldsValues(ArchiveToken arch, FolderToken  fdr)
                       throws Exception
   { 
      
      int                  i;      
      FolderTokenMultFlds  multFlds    = fdr.getFlds().getMultFlds();
      FolderTokenMultFld   multFld     = null; 
      FolderTokenMultFld   auxMultFld  = null; 
      int                  numMultFlds = multFlds.count(); 
      ArrayList            vals        = null; 
      int                  fldId;
      String               fldName;
      int                  j, numVals;
            
      for(i = 0; i < numMultFlds; i++)
      {
         multFld = multFlds.get(i);      
         
         if ( multFld.isModified())       
         {
            if (multFld.existsAnyValNoNew())
				{	
					FolderMdoFlds.deleteMultFldValue(arch, fdr.getId(), multFld);
				}
               
            vals    = multFld.getVals();
            fldId   = multFld.getId();
            fldName = multFld.getName();
            
            auxMultFld = new FolderTokenMultFld(fldId, fldName, new ArrayList());
            
            numVals = vals.size();
            
            for(j = 0; j < numVals; j ++)
            {
               if (!multFld.isValRemove(j))
               {
                  auxMultFld.addVal(vals.get(j));
               }
            }
            
            FolderMdoFlds.insertMultFldValue(arch, fdr.getId(), auxMultFld);
         }
                 
      }          
      
   }
   
   //fdr es un parámetro de entrada, salida 
   private static void storeDocumentTree(int userId,
                                         ArchiveToken arch,
                                         FolderToken  fdr)                                    
                       throws Exception
   { 
      int                  flags     = arch.getArchHdr().getFlags(); 
      FolderTokenDocTree   docTree   = fdr.getDocumentTree(); 
      CfgFtsConfig         ftsConfig = null;
         
      removeDocumentTreeNodes(arch, fdr);
      
      if ( (flags & ArchiveFlag.FTS_IN_CONTENTS) != 0) 
      {      
         if ( (docTree.getNumNewDocuments() > 0) || 
              (docTree.getNumUpdateDocuments() > 0) )
         {
            ftsConfig = CfgMdoConfig.loadDbFtsCfg();            
         }
      }      
      updateDocumentTreeNodes(userId, ftsConfig, arch, fdr);
      createDocumentTreeNodes(userId, ftsConfig, arch, fdr);      
   }
   
   private static void removeDocumentTreeNodes(ArchiveToken arch,
                                               FolderToken  fdr)                                    
                       throws Exception
   { 
      //El orden es importante
      removeDocuments(arch, fdr);
      removeDividers(arch, fdr);          
   }
   
   private static void removeDocuments(ArchiveToken arch,
                                       FolderToken  fdr)
                       throws Exception
   { 
      FolderTokenDocTree   docTree      = fdr.getDocumentTree(); 
      FolderTokenDocuments allDocs      = docTree.getAllDocuments(true); 
      FolderTokenDocuments removeDocs   = null;      
      FolderTokenDocument  doc          = null; 
      int                  i;   
      boolean              onlyPage;
      IeciTdLiLiArrayList  parentDocIds = new IeciTdLiLiArrayList();
      int                  parentDocId, idx, numChildren;
     
      removeDocs = docTree.getRemoveDocuments();   
      
      for(i = 0; i < removeDocs.count(); i++)     
      {
         doc = removeDocs.get(i);
         
         parentDocId = doc.getParentDocId();
         
         idx = parentDocIds.getIndexByFirstValue(parentDocId);

         if (idx == -1)
         {
            numChildren = allDocs.getNumChildren(parentDocId, true); 
            numChildren = numChildren - 1;
            if (numChildren > 0)
            {
               onlyPage = true;
               parentDocIds.add(parentDocId, numChildren);
            }
            else
               onlyPage = false;
                         
         }
         else
         {
            numChildren = parentDocIds.get(idx).m_val2;
            numChildren = numChildren - 1;
            
            if (numChildren > 0)
            {
               onlyPage = true;
               parentDocIds.get(idx).m_val2 = numChildren;   
            }
            else
               onlyPage = false;
                   
         }  
         
         FolderMdoDocument.removeDocument(arch, fdr.getId(),
                                          doc, onlyPage); 
         
         docTree.refreshRemoveDocument(doc.getId());         
      }           
   }
   
   //fdr es un parámetro de entrada, salida 
   private static void removeDividers(ArchiveToken arch,
                                      FolderToken  fdr)
                       throws Exception
   {  
      FolderTokenDocTree   docTree    = fdr.getDocumentTree();         
      FolderTokenDividers  removeDivs = null; 
      FolderTokenDivider   div        = null; 
      int                  i; 
      String               tblPrefix;     
      
      removeDivs = docTree.getRemoveDividers(); 
      
      if (removeDivs.count() > 0)
      { 
         tblPrefix = arch.getTblPrefix();
      
         try
         {
   
            DbConnection.beginTransaction();         
               
            for(i = 0; i < removeDivs.count(); i++)     
            {
               div = removeDivs.get(i);
         
               FolderMdoDivider.deleteDivider(tblPrefix, fdr.getId(),
                                              div.getId()); 
                                                 
            } 
            
            fdr.getDocumentTree().refreshRemoveDividers();
   
            DbConnection.endTransaction(true);
   
         }
         catch (Exception e)
         {
   
            DbConnection.ensureEndTransaction(e);
   
         }
         
     }  
            
   }
   
   private static void updateDocumentTreeNodes(int userId, CfgFtsConfig ftsConfig,
                                               ArchiveToken arch,
                                               FolderToken  fdr)                                    
                       throws Exception
   { 
      
      updateDividers(userId, arch, fdr);
      updateDocuments(userId, ftsConfig, arch, fdr);          
   }
   
   private static void updateDividers(int userId,
                                      ArchiveToken arch,
                                      FolderToken  fdr)                                    
                       throws Exception
   { 
      FolderTokenDocTree   docTree    = fdr.getDocumentTree();
      FolderTokenDividers  updDivs    = null;
      FolderTokenDivider   div        = null;
      String               tblPrefix;
      int                  i, numUpdDivs;
      
      updDivs = docTree.getUpdateDividers();
      
      numUpdDivs = updDivs.count();     
      
      if (numUpdDivs > 0)
      { 
         tblPrefix = arch.getTblPrefix();
      
         try
         {
   
            DbConnection.beginTransaction();         
               
            for(i = 0; i < numUpdDivs; i++)     
            {
               div = updDivs.get(i);
         
               FolderMdoDivider.updateDivider(userId, tblPrefix, fdr.getId(),
                                              div); 
                                                 
            }
            
            fdr.getDocumentTree().refreshUpdateDividers(); 
   
            DbConnection.endTransaction(true);
   
         }
         catch (Exception e)
         {
   
            DbConnection.ensureEndTransaction(e);
   
         }
         
     }  
      
   }
   
   private static void updateDocuments(int userId, CfgFtsConfig ftsConfig,
                                       ArchiveToken arch,
                                       FolderToken  fdr) 
                       throws Exception
   { 
      FolderTokenDocTree   docTree    = fdr.getDocumentTree();      
      FolderTokenDocuments updDocs    = null; 
      FolderTokenDocument  doc        = null; 
      int                  i, numUpdDocs;
      //int                  fileId;
      FolderDocUpdIds      docUpdIds  = null;
      
      
      updDocs = docTree.getUpdateDocuments();  
      
      numUpdDocs = updDocs.count();
         
      for(i = 0; i < numUpdDocs; i++)
      {
         doc = updDocs.get(i);
            
         docUpdIds = FolderMdoDocument.updateDocument(userId, ftsConfig, 
                                                      arch, fdr.getId(),
                                                      doc); 
           
         fdr.getDocumentTree().refreshUpdateDocument(doc.getId(), docUpdIds);         
      }      
      
   }
   
   private static void createDocumentTreeNodes(int userId,
                                               CfgFtsConfig ftsConfig,
                                               ArchiveToken arch,
                                               FolderToken  fdr)                                    
                       throws Exception
   { 
      //El orden es importante
      createDividers(userId, arch, fdr);
      createDocuments(userId, ftsConfig, arch, fdr);          
   }
   
   private static void createDividers(int userId,
                                      ArchiveToken arch,
                                      FolderToken  fdr)                                    
                       throws Exception
   { 
      FolderTokenDocTree  docTree = fdr.getDocumentTree();
      int                 firstDividerId = -1;
      int                 numNewDividers;
      IeciTdLiLiArrayList divIds  = null;
      FolderTokenDividers newDivs = null;
      
      numNewDividers = docTree.getNumNewDividers();
      
      if(numNewDividers > 0)
      {
         firstDividerId = FolderMdoXNId.generateNextDividerId(arch.getTblPrefix(),
                                                              fdr.getId(),
                                                              numNewDividers);        
         try
         {

            DbConnection.beginTransaction();         
            
            if (numNewDividers > 0)
            {
               
               newDivs = docTree.getNewDividers();
               
               divIds = FolderMdoDivider.insertDividers(userId, arch.getTblPrefix(),
                                                        fdr.getId(), newDivs, firstDividerId);  
            }

            DbConnection.endTransaction(true);
            
            fdr.getDocumentTree().refreshNewDividers(divIds);

         }
         catch (Exception e)
         {

            DbConnection.ensureEndTransaction(e);

         } 
         
      }
      
   }
   
   private static void createDocuments(int userId, 
                                       CfgFtsConfig ftsConfig,
                                       ArchiveToken arch,
                                       FolderToken  fdr) 
                       throws Exception
   { 
      FolderTokenDocTree         docTree         = fdr.getDocumentTree();
      int                        firstiDocPageId = -1;
      int                        firstiDocDocId  = -1;
      int                        numNewDocuments; 
      FolderTokenDocuments       newDocs = null; 
      FolderTokenDocument        newDoc  = null; 
      int                        i; 
      //int                        fileId;
      int                        nextParentDocId, nextId;
      boolean                    createOnlyPage = false;
      IeciTdLiLiArrayList        divDocIds      = new IeciTdLiLiArrayList();     
      int                        parentDocId;      
      FolderDocUpdIds            docUpdIds      = null;
      
      newDocs = docTree.getNewDocuments();  
      
      numNewDocuments = newDocs.count();     
      
      if(numNewDocuments > 0)
      {
         firstiDocPageId = FolderMdoXNId.generateNextiDocPageId(arch.getTblPrefix(),
                                                                fdr.getId(),
                                                                numNewDocuments);

         //cada página tiene un documento asignado.
                  
         firstiDocDocId = FolderMdoXNextId.generateNextiDocDocumentIds(arch.getId(),
                                                                       numNewDocuments);
         
         nextId          = firstiDocPageId;
         nextParentDocId = firstiDocDocId;  
         
         
         for(i = 0; i < numNewDocuments; i++)
         {
            newDoc = newDocs.get(i);
         
            parentDocId     = nextParentDocId;
            divDocIds.add(newDoc.getParentId(), parentDocId);
            createOnlyPage  = false;
            nextParentDocId = nextParentDocId + 1;               
      
            docUpdIds = FolderMdoDocument.createDocument(userId, ftsConfig,
                                                         arch, fdr.getId(),
                                                         newDoc, parentDocId, 
                                                         nextId, createOnlyPage); 
           
            fdr.getDocumentTree().refreshNewDocument(newDoc.getId(), nextId, parentDocId, docUpdIds);      
                      
            nextId = nextId   + 1;
         }  
      }      
      
   } 

   private static int getNumDistinctParentDividers(FolderTokenDocuments docs)
                      throws Exception
   { 
      IeciTdLongIntegerArrayList divsIds = new IeciTdLongIntegerArrayList();
      int                        numDocs = docs.count(); 
      FolderTokenDocument        doc     = null; 
      int                        i; 
      int                        divId;           
         
      for(i = 0; i < numDocs; i++)
      {
         doc = docs.get(i);

         divId = doc.getParentId();

         if ( !divsIds.find(divId))
         {
            divsIds.add(divId);       
         }          
      }

      return divsIds.count();  
     
   }   
   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static File retrieveFolderDocumentFile(AcsAccessToken accToken,
           ArchiveToken arch,
           FolderToken fdr,
           int docId,
           File parentFolder)
    throws Exception
    {  
        File file = null;
        
        file = FolderMdoDocumentFile.retrieveFile(fdr, docId, parentFolder);
        
        return file;     
    }  
   
   
} 
// class
