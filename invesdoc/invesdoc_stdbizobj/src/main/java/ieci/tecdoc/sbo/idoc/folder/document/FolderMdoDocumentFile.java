
package ieci.tecdoc.sbo.idoc.folder.document;

import java.io.File;
import java.io.InputStream;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sbo.fss.core.FssFileInfo;
import ieci.tecdoc.sbo.fss.core.FssFtsInfo;
import ieci.tecdoc.sbo.fss.core.FssMdoFile;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;
import ieci.tecdoc.sbo.config.CfgFtsConfig;


import org.apache.log4j.Logger;

public class FolderMdoDocumentFile
{
   private static final Logger log = Logger.getLogger(FolderMdoDocumentFile.class);
   
   private FolderMdoDocumentFile()
   {      
   }  
   
   public static  byte[] retrieveFile(FolderToken fdr, int docId)
                         throws Exception
   {  
      FolderTokenDocument doc   = null;
      int                 fileId;
        
      
      doc    = fdr.getDocument(docId);     
      fileId = doc.getFileId();
      
      return FssMdoFile.retrieveFile(fileId);     
   }    
   
   // Tiene que estar fuera de una transacción
   public static void deleteFiles(IeciTdLongIntegerArrayList fileIds)
                      throws Exception
   {  
      int i, fileId;
      
      for (i = 0; i < fileIds.count(); i++)
      {

         try
         {

            fileId = fileIds.get(i);
            
            deleteFile(fileId);            

         }
         catch (Exception e)
         {
            log.error(e.getMessage());
         }
         
      }
         
   }
   
   // Tiene que estar fuera de una transacción
   public static void deleteFile(FolderTokenDocument doc)
                      throws Exception
   {  
            
      deleteFile(doc.getFileId());
         
   }
   
  // Tiene que estar fuera de una transacción
   protected static void deleteFile(int fileId)
                         throws Exception
   {  
            
      FssMdoFile.deleteFile(fileId);
         
   }
   
   //Tiene que estar fuera de una transacción
   public static  int storeFile(CfgFtsConfig ftsConfig, int archId,
                                int  fdrId, int parentDocId, int docId,
                                String ext, byte[] fileData, int listId)
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
      
      fileId = FssMdoFile.storeFileInList(listId, info, ftsInfo,
                                          fileData);
      
      return fileId;     
   }
   
   public static int getFileSize (FolderToken fdr, int docId)
                         throws Exception
   {  
      FolderTokenDocument doc   = null;
      int                 fileId;
        
      
      doc    = fdr.getDocument(docId);     
      fileId = doc.getFileId();
      
      return FssMdoFile.getFileSize (fileId);     
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
   
   
   // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
   public static File retrieveFile(FolderToken fdr, int docId, File parentFolder) throws Exception
   {  
        FolderTokenDocument doc   = null;
        int                 fileId;
        
        
        doc    = fdr.getDocument(docId);     
        fileId = doc.getFileId();
        
        return FssMdoFile.retrieveFile(fileId, parentFolder);     
   }    
   
   // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
   //Tiene que estar fuera de una transacción
   public static  int storeFile(CfgFtsConfig ftsConfig, int archId,
                                int  fdrId, int parentDocId, int docId,
                                String ext, File inputFile, int listId)
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
      
      fileId = FssMdoFile.storeFileInList(listId, info, ftsInfo,
              inputFile);
      
      return fileId;     
   }

   
   
} // class
