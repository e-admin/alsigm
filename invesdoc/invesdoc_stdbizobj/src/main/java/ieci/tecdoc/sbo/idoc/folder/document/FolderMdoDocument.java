
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.config.CfgFtsConfig;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.dao.DaoDocRow;
import ieci.tecdoc.sbo.idoc.dao.DaoDocTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPageAnnsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPageRow;
import ieci.tecdoc.sbo.idoc.dao.DaoPageTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.folder.base.FolderDocUpdIds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sbo.util.types.SboType;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class FolderMdoDocument
{
   private static final Logger log = Logger.getLogger("xml");

   private static final String NEXT_ID_TBL_NAME         = "IDOCNEXTID";
   private static final int    NEXT_ID_TYPE_PAGE_ANNS   = 9;
   
   private static final int DEFAULT_MEMORY_INPUT_STREAM_UMBRAL = 150 * 1024; // 150 K.
   private static int memoryInputStreamUmbral = DEFAULT_MEMORY_INPUT_STREAM_UMBRAL;
   
   private static File tempDir = null;
   
   // Contendrá los ficheros temporales que no se han podido eliminar.
   // Se intentarán eliminar al finalizar cada comando.
   private static final ArrayList m_nonDeletedTempFiles = new ArrayList();
   // Flag para saber si se están eliminando o no ficheros temporales.
   private static boolean m_deletingFlag = false;
   
   private FolderMdoDocument()
   {      
   } 
   
   public static IeciTdLongIntegerArrayList getFolderFileIds(String tblPrefix, int fdrId)
                                            throws Exception
   {  
      
      String      tblName   = DaoUtil.getPageTblName(tblPrefix);      
      DaoPageTbl  pageTbl   = new DaoPageTbl(tblName);      
      
      return pageTbl.selectFolderFileIds(fdrId); 
      
   }
   
   public static void deleteFolderDocuments(String tblPrefix, int archId, int fdrId)
                      throws Exception
   {  
      String             docTblName  = DaoUtil.getDocumentTblName(tblPrefix);
      String             pageTblName = DaoUtil.getPageTblName(tblPrefix);      
      DaoDocTbl          docTbl      = new DaoDocTbl(docTblName);
      DaoPageTbl         pageTbl     = new DaoPageTbl(pageTblName);
      String             qual        = null;
      
      qual = getDeleteAnnsFolderCondition(fdrId, pageTbl);      
      DaoPageAnnsTbl.deleteRows(qual);
      
      docTbl.deleteFolderRows(archId, fdrId); 
      pageTbl.deleteFolderRows(fdrId);  
            
   } 
   
   public static void removeDocument(ArchiveToken arch,
                                     int fdrId,
                                     FolderTokenDocument doc,
                                     boolean onlyPage)                                    
                      throws Exception
   { 
      int    parentDocId = doc.getParentDocId();
      int    pageId      = doc.getId();
      String tblPrefix   = arch.getTblPrefix();
            
      try
      {
         
         DbConnection.beginTransaction(); 
         
         deletePage(tblPrefix, fdrId, pageId);
         
         if (!onlyPage)      
            deleteDoc(tblPrefix, parentDocId);
      
         DbConnection.endTransaction(true);
      
      }
      catch (Exception e)
      {
      
         DbConnection.ensureEndTransaction(e);
      
      }
      
      try
      {
         FolderMdoDocumentFile.deleteFile(doc);
      }
      catch (Exception e)
      {
         log.warn(e.getMessage());
      }
   }
   
   private static String getDeleteAnnsFolderCondition(int fdrId, DaoPageTbl pageTbl)
                         throws Exception
   {
       /*
        * 1 - EXISTS (SELECT DISTINCT(AxxPAGEH.ANNID) FROM AxxPAGEH WHERE 
        *     AxxPAGEH.ANNID = IDOCPAGEANNS.ANNID AND AxxPAGEH.FDRID = fdrId)          
        */
       StringBuffer  cond = new StringBuffer();       
       String        pageTblAnnIdColName = pageTbl.getAnnIdColName(true);
       String        pageTblFdrIdColName = pageTbl.getFdrIdColName(true);
       String        pageTblName         = pageTbl.getTblName();
      
       cond.append(" WHERE EXISTS( SELECT DISTINCT(").append(pageTblAnnIdColName).append(")");
       cond.append(" FROM ").append(pageTblName).append(" WHERE "); 
       cond.append(pageTblAnnIdColName).append(" = ");
       cond.append(DaoPageAnnsTbl.getIdColName(true)).append(" AND ");
       cond.append(pageTblFdrIdColName).append(" = ").append(fdrId).append(")");
       
       return cond.toString();
   }

   
   private static void deleteDoc(String tblPrefix,int docId)
                      throws Exception
   {  
      
      String       docTblName   = DaoUtil.getDocumentTblName(tblPrefix);      
      DaoDocTbl    docTbl       = new DaoDocTbl(docTblName);      
      
      docTbl.deleteRow(docId);           
   }
   
   private static void deletePage(String tblPrefix, int fdrId, int pageId)
                       throws Exception
   {  
      String       pageTblName  = DaoUtil.getPageTblName(tblPrefix);       
      DaoPageTbl   pageTbl      = new DaoPageTbl(pageTblName);       
      
      pageTbl.deleteRow(pageId, fdrId);      
   }
   
   //Devuelve los identificadores asociado al documento que se han podido modificar
   public static FolderDocUpdIds updateDocument(int userId,
                                                CfgFtsConfig ftsConfig,
                                                ArchiveToken arch,
                                                int fdrId,
                                                FolderTokenDocument doc)                                    
                                  throws Exception
   { 
      int             fileId    = doc.getFileId();
      int             annId     = doc.getAnnId();
      int             listId    = arch.getMisc().getVolListId();
      boolean         createAnn = false;
      boolean         updateAnn = false;
      FolderDocUpdIds docUpdIds = null;
      
      if (doc.isUpdateFile())
      { 
        fileId = storeFile(ftsConfig, arch.getId(), fdrId, doc, listId);
      }  
      
      if (doc.isUpdateAnnFile())
      { 
         if ( (annId == SboType.NULL_ID)||(annId == SboType.VOID_ID) )
         {
            annId =  NextId.generateNextId(NEXT_ID_TBL_NAME,
                                           NEXT_ID_TYPE_PAGE_ANNS);
            createAnn = true;
         }
         else
         {
            updateAnn = true;
         }
      } 
   
           
      try
      {
         
         DbConnection.beginTransaction(); 
         
         if (createAnn)
         { 
            FolderMdoDocumentAnn.createAnn(userId, doc, annId);
         }
         else if (updateAnn)
         {
            FolderMdoDocumentAnn.updateAnn(userId, doc);
         }
      
         updatePage(userId, arch.getTblPrefix(), fdrId, doc, 
                    fileId, annId);
      
         DbConnection.endTransaction(true);
         
         docUpdIds = new FolderDocUpdIds(fileId, annId);
      
      }
      catch (Exception e)
      {
      
         DbConnection.ensureEndTransaction(e);
      
      }
      
      if (doc.isUpdateFile())
      {
         try
         {
            FolderMdoDocumentFile.deleteFile(doc);
         }
         catch (Exception e)
         {
            log.warn(e.getMessage());
         }         
      }  
      
      return docUpdIds;
      
   }
   
   private static void updatePage(int userId, String tblPrefix, int fdrId,
                                  FolderTokenDocument doc, int fileId, int annId)
                       throws Exception
   {  
      String               tblName   = DaoUtil.getPageTblName(tblPrefix);       
      DaoPageTbl           pageTbl   = new DaoPageTbl(tblName); 
      int                  pageId    = doc.getId(); 
      FolderDaoPageRowUpd  pageRow   = null;      
     
      pageRow = fillPageRowUpd(userId, doc.getName(), 
                               fileId, doc.getFileExt(), annId);      
      
      pageTbl.updateRow(pageId, fdrId, pageRow);
      
   } 
   
   private static FolderDaoPageRowUpd fillPageRowUpd(int userId, String name, 
                                                     int fileId, String fileExt,
                                                     int annId)
                                  throws Exception
   {   
      FolderDaoPageRowUpd rec = new FolderDaoPageRowUpd();
      
      rec.m_name      = name; 
      rec.m_fileId    = fileId; 
      rec.m_loc       = fileExt; 
      rec.m_annId     = annId;
      rec.m_updUsrId  = userId;       
      rec.m_updTs     = DateTimeUtil.getCurrentDateTime();      
     
      return rec;      
   }
   
   // Devuelve los identificadores asociado al documento que se han podido modificar
   public static FolderDocUpdIds createDocument(int userId,
                                                CfgFtsConfig ftsConfig,
                                                ArchiveToken arch,
                                                int fdrId,
                                                FolderTokenDocument doc, 
                                                int parentDocId, int docId, 
                                                boolean onlyPage)                                    
                                 throws Exception
   { 
      int             fileId;
      int             listId    = arch.getMisc().getVolListId(); 
      // PARCHE P9.0.2. Permite recuperar el contenido del documento en un File.
      //byte[]      fileData = null;
      String      path     = null;
      InputStream iStream  = null; 
      
      int             annId     = doc.getAnnId();      
      boolean         createAnn = false; 
      FolderDocUpdIds docUpdIds = null;
      
      // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
      if (doc.getInputStreamFile() == null)
      {
        path     = doc.getPathFile();
        //fileData = getFileData(path);
        
        fileId = FolderMdoDocumentFile.storeFile(ftsConfig, arch.getId(), 
                fdrId, parentDocId,
                docId, doc.getFileExt(), 
                new File (path), listId); 
      }
      else
      {
//        iStream  = doc.getInputStreamFile();
//        byte[] fileData = getBytes(iStream);
//        
//        fileId = FolderMdoDocumentFile.storeFile(ftsConfig, arch.getId(), 
//                fdrId, parentDocId,
//                docId, doc.getFileExt(), 
//                fileData, listId); 

          iStream  = doc.getInputStreamFile();
          Object auxObject = getBytesOrFile(iStream);
          
          if (auxObject instanceof File) {
              try {
              fileId = FolderMdoDocumentFile.storeFile(ftsConfig, arch.getId(), 
                      fdrId, parentDocId,
                      docId, doc.getFileExt(), 
                      (File) auxObject, listId);
              } finally {
                  deleteTempFile ((File) auxObject);
              }
          } else {
              fileId = FolderMdoDocumentFile.storeFile(ftsConfig, arch.getId(), 
                      fdrId, parentDocId,
                      docId, doc.getFileExt(), 
                      (byte []) auxObject, listId);
          }
          
          
      }   
      
      // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
//      fileId = FolderMdoDocumentFile.storeFile(ftsConfig, arch.getId(), 
//                                               fdrId, parentDocId,
//                                               docId, doc.getFileExt(), 
//                                               fileData, listId); 
      
      if ((doc.getPathAnnFile() != null && 
          !doc.getPathAnnFile().equals("")) || doc.getReaderAnnFile() != null)
      { 
         annId =  NextId.generateNextId(NEXT_ID_TBL_NAME,
                                        NEXT_ID_TYPE_PAGE_ANNS);
         createAnn = true;         
      }
      
      try
      {
         
         DbConnection.beginTransaction(); 
         
         if (createAnn)
         { 
            FolderMdoDocumentAnn.createAnn(userId, doc, annId);
         }         

         insertPage(userId, arch, fdrId, doc, parentDocId,
                    docId, fileId, annId);

         if (!onlyPage)
         {
            insertDoc(userId, arch, fdrId, doc.getParentId(),
                      parentDocId);         
         }
      
         DbConnection.endTransaction(true);
         
         docUpdIds = new FolderDocUpdIds(fileId, annId);
      
      }
      catch (Exception e)
      {
      
         DbConnection.ensureEndTransaction(e);
      
      } 
      
      return docUpdIds;
      
   }
  
   private static void insertDoc(int userId, ArchiveToken arch, int fdrId,
                                 int parentDivId, int docId)
                      throws Exception
   {  
      DaoDocRow    docRow      = null;     
      int          archId      = arch.getId();
      String       tblPrefix   = arch.getTblPrefix();      
      String       docTblName  = DaoUtil.getDocumentTblName(tblPrefix); 
      DaoDocTbl    docTbl      = new DaoDocTbl(docTblName);     
      
      docRow  = fillDocRow(userId, archId, fdrId, docId, parentDivId);    
      
      docTbl.insertRow(docRow); 
      
   } 

   private static void insertPage(int userId, ArchiveToken arch, int fdrId,
                                  FolderTokenDocument doc, int parentDocId,
                                  int pageId, int fileId, int annId)
                      throws Exception
   {  
      DaoPageRow   pageRow     = null;
      String       tblPrefix   = arch.getTblPrefix(); 
      String       pageTblName = DaoUtil.getPageTblName(tblPrefix);         
      DaoPageTbl   pageTbl     = new DaoPageTbl(pageTblName); 
      
      
      pageRow = fillPageRow(userId, fdrId, pageId, parentDocId,
                            doc.getName(), doc.getSortOrder(),
                            fileId, doc.getFileExt(), annId);
      
      pageTbl.insertRow(pageRow);
      
   } 
   
   private static DaoPageRow fillPageRow(int userId, int fdrId, int id, 
                                         int docParentId, String name, int sortOrder,
                                         int fileId, String fileExt, int annId)
                             throws Exception
   {   
      DaoPageRow rec = new DaoPageRow();
      
      rec.m_id        = id; 
      rec.m_fdrId     = fdrId; 
      rec.m_name      = name; 
      rec.m_sortOrder = sortOrder;
      rec.m_docId     = docParentId;
      rec.m_fileId    = fileId;
      rec.m_volId     = SboType.NULL_ID;  
      rec.m_loc       = fileExt; 
      rec.m_annId     = annId;   
      rec.m_stat      = 0;
      rec.m_refCount  = 0;   
      rec.m_rems      = DbDataType.NULL_SHORT_TEXT; 
      rec.m_acsType   = AcsAccessType.ACS_PUBLIC;
      rec.m_acsId     = SboType.NULL_ID;   
      rec.m_crtUsrId  = userId;
      rec.m_crtTs     = DateTimeUtil.getCurrentDateTime();
      rec.m_updUsrId  = SboType.NULL_ID;    
      rec.m_updTs     = DbDataType.NULL_DATE_TIME;
      rec.m_accUsrId  = userId;    
      rec.m_accTs     = DateTimeUtil.getCurrentDateTime();
      rec.m_accCount  = 1; 
     
      return rec;      
   }
   
   private static DaoDocRow fillDocRow(int userId, int archId, int fdrId, int id, 
                                       int divParentId)
                            throws Exception
   {   
      DaoDocRow rec = new DaoDocRow();
      
      rec.m_id       = id; 
      rec.m_fdrId    = fdrId; 
      rec.m_archId   = archId;      
      rec.m_name     = "Doc" + id;       
      rec.m_clfId    = divParentId;
      rec.m_type     = 1;
      rec.m_title    = DbDataType.NULL_SHORT_TEXT; 
      rec.m_author   = DbDataType.NULL_SHORT_TEXT; 
      rec.m_keywords = DbDataType.NULL_SHORT_TEXT;   
      rec.m_stat     = 0;
      rec.m_refCount = 0;
      rec.m_rems     = DbDataType.NULL_SHORT_TEXT; 
      rec.m_acsType  = AcsAccessType.ACS_PUBLIC;
      rec.m_acsId    = SboType.NULL_ID;   
      rec.m_crtUsrId = userId;
      rec.m_crtTs    = DateTimeUtil.getCurrentDateTime();
      rec.m_updUsrId = SboType.NULL_ID;    
      rec.m_updTs    = DbDataType.NULL_DATE_TIME;
      rec.m_accUsrId = userId;    
      rec.m_accTs    = DateTimeUtil.getCurrentDateTime(); 
      rec.m_accCount = 1; 
      rec.m_ts       = DateTimeUtil.getCurrentDateTime(); 
      
      return rec;      
   }
   
   
// Tiene que estar fuera de una transacción
   public static  int storeFile(CfgFtsConfig ftsConfig, int archId,
                                int  fdrId, FolderTokenDocument doc, 
                                int listId)
                      throws Exception
   {  
      int         fileId;
      // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
      //byte[]      fileData = null;
      String      path     = null;
      InputStream iStream  = null;       
      
      // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
      if (doc.getInputStreamFile() == null)
      {
        path     = doc.getPathFile();
        //fileData = getFileData(path);    
        
        fileId = FolderMdoDocumentFile.storeFile(ftsConfig, archId, fdrId, doc.getParentDocId(), doc.getId(),
                doc.getFileExt(), new File (path), listId);
      }
      else
      {
        iStream = doc.getInputStreamFile();
        Object auxObject = getBytesOrFile(iStream);
        
        if (auxObject instanceof File) {
				try {
					fileId = FolderMdoDocumentFile.storeFile(
					              ftsConfig, 
					              archId, 
					              fdrId, 
					              doc.getParentDocId(), 
					              doc.getId(), 
					              doc.getFileExt(), 
					              (File) auxObject, 
					              listId);
				} finally {
                    deleteTempFile((File) auxObject);
				}
			} else {
				fileId = FolderMdoDocumentFile.storeFile(
				                  ftsConfig, 
				                  archId, 
				                  fdrId, 
				                  doc.getParentDocId(), 
				                  doc.getId(),
								  doc.getFileExt(), 
								  (byte[]) auxObject, 
								  listId);
			}
        
      }
      
      // PARCHE P9.0.2. Permite guardar el contenido del documento en un File.
// fileId = FolderMdoDocumentFile.storeFile(ftsConfig, archId, fdrId, doc.getParentDocId(), doc.getId(),
//                                               doc.getFileExt(), fileData, listId);
      
      
      return fileId;     
   }
      
   /**
    * @return Returns the memoryInputStreamUmbral.
    */
   public static int getMemoryInputStreamUmbral () {
        return memoryInputStreamUmbral;
   }
    
   /**
    * @param memoryInputStreamUmbral The memoryInputStreamUmbral to set.
    */
   public static void setMemoryInputStreamUmbral (int memoryInputStreamUmbral) {
        FolderMdoDocument.memoryInputStreamUmbral = memoryInputStreamUmbral;
   }

   /**
    * Establece el directorio donde se crearán los ficheros temporales
    */
   public static void setTempDir (File pTmpDir) {
       tempDir = pTmpDir;
   }
   
   /**
    * Devuelve un File o un byte [] dependiendo del memoryInputStreamUmbral.
    * Si no se supera el umbral, devolverá un byte [].
    * Si supera un umbral, volcará el contenido en un fichero y lo devolverá.
    * 
    * @param stream
    * @return
    * @throws Exception
    */
   private static Object getBytesOrFile (InputStream stream) throws Exception 
   {

       if (stream.available() > memoryInputStreamUmbral) {
           // Si lo que hay disponible supera el umbral, directamente se guarda en un fichero
           return getFile (stream, null);
       }
       
       
       byte[] buffer;
       BufferedInputStream input = new BufferedInputStream(stream);
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       int count;

       try {
           buffer = new byte[10240];
           boolean toFile = false;
           while (( ! toFile) && ((count = input.read(buffer)) != -1)) {
               output.write(buffer, 0, count);
               
               if ((output.size() + input.available()) > memoryInputStreamUmbral) {
                   toFile = true;
               }
           }
           
           if (toFile) {
               return getFile (stream, output.toByteArray());
           } else {
               return output.toByteArray();
           }
       }
       catch (Exception exc) {
          throw exc;
       }
       finally {
          try {
             output.close();
          }
          catch (Exception exc) {
             throw exc;
          }
       }
   }     
   
   
   /**
    * Devuelve un File.
    */
   private static Object getFile (InputStream stream, byte [] readedBytes) throws Exception 
   {
       File tempFile;
       
       if (tempDir == null) {
           tempFile = File.createTempFile("idoc", ".tmp");
       } else {
           tempFile = File.createTempFile("idoc", ".tmp", tempDir);
       }
       tempFile.deleteOnExit();

       FileOutputStream fos = null;
       
       try {
           fos = new FileOutputStream (tempFile);
           
           if (readedBytes != null) {
               fos.write(readedBytes);
           }
           
           byte[] buffer = new byte[10240]; 
           int count;
        
           while ((count = stream.read(buffer)) != -1) {
               fos.write(buffer, 0, count);
           }
           
           fos.flush();
       } catch (Exception e) {
           if (fos != null) {
               try {
                   fos.close ();
                   fos = null;
               } catch (Throwable t) {
                   fos = null;
               }
           }
           
           // Si se produce un error se intenta eliminar el fichero
           try {
               deleteTempFile(tempFile);
           } catch (Throwable t) {
           }
           
           throw e;
       } finally {
           if (fos != null) {
               try {
                   fos.close ();
               } catch (Throwable t) {
               }
           }
       }

       return tempFile;
   }     
   
   private static void deleteTempFile (File tempFile) {
       try {
           tryToDeleteOlderTempFiles ();
       } catch (Throwable t) {}
       
       if ( ! tempFile.delete()) {
           // Se añade al conjunto de ficheros temporales que no se han podido eliminar
           synchronized (m_nonDeletedTempFiles) {
               m_nonDeletedTempFiles.add(tempFile);
           }
       }
   }
   
   /**
    * Intenta eliminar los ficheros temporales que no han podido eliminarse.
    */
   public static void tryToDeleteOlderTempFiles () {
       boolean delete = false;
       
       if ( ! m_deletingFlag) {
           synchronized (NEXT_ID_TBL_NAME) { 
               if ( ! m_deletingFlag) {
                   m_deletingFlag = true;
                   delete = true;
               }
           }
       }
       
       if (delete) {
           try {
               int currentSize;
               synchronized (m_nonDeletedTempFiles) {
                   currentSize = m_nonDeletedTempFiles.size();
               }
               
               File tempFile;
               for (int i = currentSize - 1; i >= 0; i--) {
                   synchronized (m_nonDeletedTempFiles) {
                       tempFile = (File) m_nonDeletedTempFiles.get(i);
                   }
                   
                   if ((tempFile.exists()) && (! tempFile.delete())) {
                       if (log.isInfoEnabled()) {
                           log.info ("No se ha podido eliminiar el fichero temporal " + tempFile.getAbsolutePath());
                       }
                   } else {
                       synchronized (m_nonDeletedTempFiles) {
                           m_nonDeletedTempFiles.remove(i);
                       }
                       
                       if (log.isDebugEnabled()) {
                           log.debug ("Elimininado el fichero temporal " + tempFile.getAbsolutePath());
                       }
                   }
               }
               
               
           } finally {
               synchronized (NEXT_ID_TBL_NAME) { 
                   m_deletingFlag = false;
               }   
           }
       }
   }
   
} // class
