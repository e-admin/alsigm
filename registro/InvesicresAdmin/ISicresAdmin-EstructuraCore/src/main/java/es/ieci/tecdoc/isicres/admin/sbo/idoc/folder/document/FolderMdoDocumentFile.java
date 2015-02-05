
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.document;


import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.file.FileManager;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgFtsConfig;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssFileInfo;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssFtsInfo;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssMdoFile;
import es.ieci.tecdoc.isicres.admin.sbo.fss.core.FssMdoUtil;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenDocument;

public class FolderMdoDocumentFile
{
   private static final Logger log = Logger.getLogger(FolderMdoDocumentFile.class);
   
   private FolderMdoDocumentFile()
   {      
   }  
   
   public static  byte[] retrieveFile(DbConnection dbConn, FolderToken fdr, int docId, String entidad)
                         throws Exception
   {  
      FolderTokenDocument doc   = null;
      int                 fileId;
        
      
      doc    = fdr.getDocument(docId);     
      fileId = doc.getFileId();
      
      return FssMdoFile.retrieveFile(dbConn, fileId, entidad);     
   }    
   
   // Tiene que estar fuera de una transacción
   public static void deleteFiles(DbConnection dbConn, IeciTdLongIntegerArrayList fileIds, String entidad)
                      throws Exception
   {  
      int i, fileId;
      
      for (i = 0; i < fileIds.count(); i++)
      {

         try
         {

            fileId = fileIds.get(i);
            
            deleteFile(dbConn, fileId, entidad);            

         }
         catch (Exception e)
         {
            log.error(e.getMessage());
         }
         
      }
         
   }
   
   // Tiene que estar fuera de una transacción
   public static void deleteFile(DbConnection dbConn, FolderTokenDocument doc, String entidad)
                      throws Exception
   {  
            
      deleteFile(dbConn, doc.getFileId(), entidad);
         
   }
   
  // Tiene que estar fuera de una transacción
   protected static void deleteFile(DbConnection dbConn, int fileId, String entidad)
                         throws Exception
   {  
            
      FssMdoFile.deleteFile(dbConn, fileId, entidad);
         
   }
   
   //Tiene que estar fuera de una transacción
   public static  int storeFile(DbConnection dbConn, CfgFtsConfig ftsConfig, int archId,
                                int  fdrId, int parentDocId, int docId,
                                String ext, byte[] fileData, int listId, String entidad)
                      throws Exception
   {  
      int           fileId; 
      FssFileInfo   info    = null;
      FssFtsInfo    ftsInfo = null;
      
      int           i, numExts;
      boolean       isFts;
      String        ftsExt, upperFtsExt, upperExt;
      
      isFts = false;
      
      if (ftsConfig != null)
      {
         upperExt = ext.toUpperCase();
         
         numExts = ftsConfig.m_exts.count();
         
         for(i = 0; i < numExts; i ++)
         {
            ftsExt = ftsConfig.m_exts.get(i);
            upperFtsExt = ftsExt.toUpperCase();
            
            if (upperFtsExt.equals(upperExt))
            {
               isFts = true;
               break;
            }            
         }
      }      
      
      info = createFileInfo(archId, fdrId,
                            parentDocId, docId, ext, isFts);
      
      if (isFts)
         ftsInfo = createFtsInfo(ftsConfig);
      
      fileId = FssMdoFile.storeFileInList(dbConn, listId, info, ftsInfo,
                                          fileData, entidad);
      
      return fileId;     
   }
   
   public static int getFileSize (DbConnection dbConn, FolderToken fdr, int docId)
                         throws Exception
   {  
      FolderTokenDocument doc   = null;
      int                 fileId;
        
      
      doc    = fdr.getDocument(docId);     
      fileId = doc.getFileId();
      
      return FssMdoFile.getFileSize (dbConn, fileId);     
   }   
   
   
   private static  FssFileInfo createFileInfo(int archId, int fdrId,
                                              int parentDocId,
                                              int docId, String ext, boolean isFts)
   
   {  
      FssFileInfo  info = null;
      
      info = new FssFileInfo();
      
      info.m_ext    = ext;
      info.m_extId1 = archId;
      info.m_extId2 = fdrId;
      info.m_extId3 = parentDocId;
      info.m_extId4 = docId;
      
      if (isFts)
         info.m_flags = FssMdoUtil.FILE_FLAG_FTS;
      else
         info.m_flags  = 0;              
      
      return info;     
   }
   
   private static  FssFtsInfo createFtsInfo(CfgFtsConfig ftsConfig)

   {  
      FssFtsInfo  info = null;
      
      info = new FssFtsInfo();      
      
      info.m_ftsPlatform = ftsConfig.m_platform;
      info.m_ftsRoot     = ftsConfig.m_root;                 
      
      return info;     
   }
   
   private static byte[] getFileData(String fileName) throws Exception
   {      
      byte[]  fileData = null;        
         
      if (fileName != null)
      {         
         fileData = FileManager.readBytesFromFile(fileName);        
              
      } 
      
      return fileData;
   }
   
   
   
} // class
