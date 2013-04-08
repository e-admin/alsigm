
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.file.FileManager;
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
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class FolderMdoDocument
{
   private static final Logger log = Logger.getLogger("xml");

   private static final String NEXT_ID_TBL_NAME         = "IDOCNEXTID";
   private static final int    NEXT_ID_TYPE_PAGE_ANNS   = 9;
   
   private FolderMdoDocument()
   {      
   } 
   
   public static IeciTdLongIntegerArrayList getFolderFileIds(DbConnection dbConn, String tblPrefix, int fdrId)
                                            throws Exception
   {  
      
      String      tblName   = DaoUtil.getPageTblName(tblPrefix);      
      DaoPageTbl  pageTbl   = new DaoPageTbl(tblName);      
      
      return pageTbl.selectFolderFileIds(dbConn, fdrId); 
      
   }
   
   public static void deleteFolderDocuments(DbConnection dbConn, String tblPrefix, int archId, int fdrId)
                      throws Exception
   {  
      String             docTblName  = DaoUtil.getDocumentTblName(tblPrefix);
      String             pageTblName = DaoUtil.getPageTblName(tblPrefix);      
      DaoDocTbl          docTbl      = new DaoDocTbl(docTblName);
      DaoPageTbl         pageTbl     = new DaoPageTbl(pageTblName);
      String             qual        = null;
      
      qual = getDeleteAnnsFolderCondition(fdrId, pageTbl);      
      DaoPageAnnsTbl.deleteRows(dbConn, qual);
      
      docTbl.deleteFolderRows(dbConn, archId, fdrId); 
      pageTbl.deleteFolderRows(dbConn, fdrId);  
            
   } 
   
   public static void removeDocument(ArchiveToken arch,
                                     int fdrId,
                                     FolderTokenDocument doc,
                                     boolean onlyPage, String entidad)                                    
                      throws Exception
   { 
      int    parentDocId = doc.getParentDocId();
      int    pageId      = doc.getId();
      String tblPrefix   = arch.getTblPrefix();
           
      DbConnection dbConn=new DbConnection(); 
      try
      {
         dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction(); 
         
         deletePage(dbConn, tblPrefix, fdrId, pageId);
         
         if (!onlyPage)      
            deleteDoc(dbConn, tblPrefix, parentDocId);
      
         dbConn.endTransaction(true);
      

      
      try
      {
         FolderMdoDocumentFile.deleteFile(dbConn, doc, entidad);
      }
      catch (Exception e)
      {
         log.warn(e.getMessage());
      }
      }
      catch (Exception e)
      {
         throw e;    
      }finally{
    	  dbConn.close();
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

   
   private static void deleteDoc(DbConnection dbConn, String tblPrefix,int docId)
                      throws Exception
   {  
      
      String       docTblName   = DaoUtil.getDocumentTblName(tblPrefix);      
      DaoDocTbl    docTbl       = new DaoDocTbl(docTblName);      
      
      docTbl.deleteRow(dbConn, docId);           
   }
   
   private static void deletePage(DbConnection dbConn, String tblPrefix, int fdrId, int pageId)
                       throws Exception
   {  
      String       pageTblName  = DaoUtil.getPageTblName(tblPrefix);       
      DaoPageTbl   pageTbl      = new DaoPageTbl(pageTblName);       
      
      pageTbl.deleteRow(dbConn, pageId, fdrId);      
   }
   
   //Devuelve los identificadores asociado al documento que se han podido modificar
   public static FolderDocUpdIds updateDocument(int userId,
                                                CfgFtsConfig ftsConfig,
                                                ArchiveToken arch,
                                                int fdrId,
                                                FolderTokenDocument doc, String entidad)                                    
                                  throws Exception
   { 
      int             fileId    = doc.getFileId();
      int             annId     = doc.getAnnId();
      int             listId    = arch.getMisc().getVolListId();
      boolean         createAnn = false;
      boolean         updateAnn = false;
      FolderDocUpdIds docUpdIds = null;
      
      DbConnection dbConn=new DbConnection(); 
      try
      {
         dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction(); 
         
      if (doc.isUpdateFile())
      { 
        fileId = storeFile(dbConn, ftsConfig, arch.getId(), fdrId, doc, listId, entidad);
      }  
      
      if (doc.isUpdateAnnFile())
      { 
         if ( (annId == SboType.NULL_ID)||(annId == SboType.VOID_ID) )
         {
            annId =  NextId.generateNextId(NEXT_ID_TBL_NAME,
                                           NEXT_ID_TYPE_PAGE_ANNS, entidad);
            createAnn = true;
         }
         else
         {
            updateAnn = true;
         }
      } 
   

         
         if (createAnn)
         { 
            FolderMdoDocumentAnn.createAnn(dbConn, userId, doc, annId);
         }
         else if (updateAnn)
         {
            FolderMdoDocumentAnn.updateAnn(dbConn, userId, doc);
         }
      
         updatePage(dbConn, userId, arch.getTblPrefix(), fdrId, doc, 
                    fileId, annId);
      
         dbConn.endTransaction(true);
         
         docUpdIds = new FolderDocUpdIds(fileId, annId);
      

      
      if (doc.isUpdateFile())
      {
         try
         {
            FolderMdoDocumentFile.deleteFile(dbConn, doc, entidad);
         }
         catch (Exception e)
         {
            log.warn(e.getMessage());
         }         
      }  
      }
      catch (Exception e)
      {
    	  throw e;
      }finally{
    	  dbConn.close();
      }      
      return docUpdIds;
      
   }
   
   private static void updatePage(DbConnection dbConn, int userId, String tblPrefix, int fdrId,
                                  FolderTokenDocument doc, int fileId, int annId)
                       throws Exception
   {  
      String               tblName   = DaoUtil.getPageTblName(tblPrefix);       
      DaoPageTbl           pageTbl   = new DaoPageTbl(tblName); 
      int                  pageId    = doc.getId(); 
      FolderDaoPageRowUpd  pageRow   = null;      
     
      pageRow = fillPageRowUpd(userId, doc.getName(), 
                               fileId, doc.getFileExt(), annId);      
      
      pageTbl.updateRow(dbConn, pageId, fdrId, pageRow);
      
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
                                                boolean onlyPage, String entidad)                                    
                                 throws Exception
   { 
      int             fileId;
      int             listId    = arch.getMisc().getVolListId(); 
      byte[]      fileData = null;
      String      path     = null;
      InputStream iStream  = null; 
      
      int             annId     = doc.getAnnId();      
      boolean         createAnn = false; 
      FolderDocUpdIds docUpdIds = null;
      
      if (doc.getInputStreamFile() == null)
      {
        path     = doc.getPathFile();
        fileData = getFileData(path);
      }
      else
      {
        iStream  = doc.getInputStreamFile();
        fileData = getBytes(iStream);
        
      }   
      DbConnection dbConn=new DbConnection(); 
      try
      {
    	dbConn.open(DBSessionManager.getSession(entidad));     
      fileId = FolderMdoDocumentFile.storeFile(dbConn, ftsConfig, arch.getId(), 
                                               fdrId, parentDocId,
                                               docId, doc.getFileExt(), 
                                               fileData, listId, entidad); 
      
      if ((doc.getPathAnnFile() != null && 
          !doc.getPathAnnFile().equals("")) || doc.getReaderAnnFile() != null)
      { 
         annId =  NextId.generateNextId(NEXT_ID_TBL_NAME,
                                        NEXT_ID_TYPE_PAGE_ANNS, entidad);
         createAnn = true;         
      }
      

         dbConn.beginTransaction(); 
         
         if (createAnn)
         { 
            FolderMdoDocumentAnn.createAnn(dbConn, userId, doc, annId);
         }         

         insertPage(dbConn, userId, arch, fdrId, doc, parentDocId,
                    docId, fileId, annId);

         if (!onlyPage)
         {
            insertDoc(dbConn, userId, arch, fdrId, doc.getParentId(),
                      parentDocId);         
         }
      
         dbConn.endTransaction(true);
         
         docUpdIds = new FolderDocUpdIds(fileId, annId);
      
      }
      catch (Exception e)
      {
    	  throw e;
      }finally{
    	  dbConn.close();
      }
      
      return docUpdIds;
      
   }
  
   private static void insertDoc(DbConnection dbConn, int userId, ArchiveToken arch, int fdrId,
                                 int parentDivId, int docId)
                      throws Exception
   {  
      DaoDocRow    docRow      = null;     
      int          archId      = arch.getId();
      String       tblPrefix   = arch.getTblPrefix();      
      String       docTblName  = DaoUtil.getDocumentTblName(tblPrefix); 
      DaoDocTbl    docTbl      = new DaoDocTbl(docTblName);     
      
      docRow  = fillDocRow(userId, archId, fdrId, docId, parentDivId);    
      
      docTbl.insertRow(dbConn, docRow); 
      
   } 

   private static void insertPage(DbConnection dbConn, int userId, ArchiveToken arch, int fdrId,
                                  FolderTokenDocument doc, int parentDocId,
                                  int pageId, int fileId, int annId)
                      throws Exception
   {  
      DaoPageRow   pageRow     = null;
      int          archId      = arch.getId();
      String       tblPrefix   = arch.getTblPrefix(); 
      String       pageTblName = DaoUtil.getPageTblName(tblPrefix);         
      DaoPageTbl   pageTbl     = new DaoPageTbl(pageTblName); 
      
      
      pageRow = fillPageRow(userId, fdrId, pageId, parentDocId,
                            doc.getName(), doc.getSortOrder(),
                            fileId, doc.getFileExt(), annId);
      
      pageTbl.insertRow(dbConn, pageRow);
      
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
   public static  int storeFile(DbConnection dbConn, CfgFtsConfig ftsConfig, int archId,
                                int  fdrId, FolderTokenDocument doc, 
                                int listId, String entidad)
                      throws Exception
   {  
      int         fileId; 
      byte[]      fileData = null;
      String      path     = null;
      InputStream iStream  = null;       
      
      if (doc.getInputStreamFile() == null)
      {
        path     = doc.getPathFile();
        fileData = getFileData(path);    
      }
      else
      {
        iStream = doc.getInputStreamFile();
        fileData = getBytes(iStream);
        
      }
      
      fileId = FolderMdoDocumentFile.storeFile(dbConn, ftsConfig, archId, fdrId, doc.getParentDocId(), doc.getId(),
                                               doc.getFileExt(), fileData, listId, entidad);
      
      
      return fileId;     
   }
   
   private static byte[] getBytes(InputStream stream) throws Exception 
   {

      byte[] bytes = null;
      byte[] buffer;
      BufferedInputStream input = new BufferedInputStream(stream);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      int available, chunkSize, count;

      try {
         chunkSize = 10240;
         buffer = new byte[chunkSize];
         available = input.available();
         while (available > 0) {
            count = input.read(buffer);
            output.write(buffer, 0, count);
            available = input.available();
         }
         bytes = output.toByteArray();
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

      return bytes;
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
