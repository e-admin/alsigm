
package ieci.tecdoc.sbo.idoc.folder.document;


import java.io.Reader;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;
import ieci.tecdoc.sbo.idoc.dao.DaoPageAnnsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoPageAnnsRow;
import ieci.tecdoc.sbo.idoc.dao.DaoPageAnnsRowUpd;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.datetime.DateTimeUtil;

public class FolderMdoDocumentAnn
{
   
   private static final String ANN_CURRENT_VERS = "01.00";

   //Flags
   private static final int ANN_XDI_FLAG_NONE       = 0;
   private static final int ANN_XDI_FLAG_ADD_ANNS   = 1;
   private static final int ANN_XDI_FLAG_PRINT_ANNS = 2;   
   
   private FolderMdoDocumentAnn()
   {      
   }  
   
   public static String retrieveAnnData(int userId, FolderToken fdr, int docId)
                        throws Exception
   {  
      FolderTokenDocument doc   = null;
      int                 annId;
      String              data;
             
      
      doc    = fdr.getDocument(docId);     
      annId  = doc.getAnnId();

      if ( (annId == SboType.NULL_ID)||(annId == SboType.VOID_ID) )
         data = initAnnData(userId);
      else
      {
         data   = DaoPageAnnsTbl.selectData(annId);
      }
      
      return data;     
   }      
   
   // Tiene que estar fuera de una transacción
   public static void deleteAnn(FolderTokenDocument doc)
                      throws Exception
   {  
            
      DaoPageAnnsTbl.deleteRow(doc.getAnnId());
         
   }    
   
   public static void createAnn(int userId, FolderTokenDocument doc, int annId)
                      throws Exception
   {  
      String          data        = null;
      String          pathAnnFile = doc.getPathAnnFile();
      Reader          readerAnnFile = doc.getReaderAnnFile();
      DaoPageAnnsRow  row         = null;
            
      if (pathAnnFile != null)
        data = FileManager.readStringFromFile(pathAnnFile);
      else if (readerAnnFile != null)
        data = FileManager.readStringFromFile(readerAnnFile);
      
      row = fillAnnRow(annId, data, userId);
      
      DaoPageAnnsTbl.insertRow(row);     
         
   }
   
  
   public static void updateAnn(int userId, FolderTokenDocument doc)
                      throws Exception
   {    
      String            data        = null;
      String            pathAnnFile = doc.getPathAnnFile();
      Reader          readerAnnFile = doc.getReaderAnnFile();
      int               annId       = doc.getAnnId();
      DaoPageAnnsRowUpd row         = null;
      
      if (pathAnnFile != null)
        data = FileManager.readStringFromFile(pathAnnFile);
      else if (readerAnnFile != null)
        data = FileManager.readStringFromFile(readerAnnFile);
      
      row = fillAnnRowUpd(data, userId);
      
      DaoPageAnnsTbl.updateData(annId, row);    
      
   }  


   private static String initAnnData(int userId)
                         throws Exception
   {  
      String       data;
      StringBuffer buffer  = new StringBuffer();
      String       vers;
      int          numPages;
      String       aux = "";
      int          flags;

      vers = '"' + ANN_CURRENT_VERS + '"';

      buffer.append(vers);//versión
      buffer.append(",");

      numPages = 0;

      buffer.append(numPages); //número de páginas
      buffer.append("|"); 

      //Información extendida 
      buffer.append(userId);   //UserId
      buffer.append(",");
 
      aux = '"' + aux + '"';
      
      buffer.append(aux); //UserName
      buffer.append(",");

      buffer.append(aux); //IconPath
      buffer.append(",");

      buffer.append(aux); //DefNavIcon
      buffer.append(",");

      flags = ANN_XDI_FLAG_ADD_ANNS | ANN_XDI_FLAG_PRINT_ANNS ;
      buffer.append(flags); //flags

      data = buffer.toString(); 
      
      return data;     
   } 
   
   private static DaoPageAnnsRow fillAnnRow(int annId, String data,
                                            int userId)
                                 throws Exception
   {
   
      DaoPageAnnsRow row = new DaoPageAnnsRow();        
      
      row.m_id        = annId;
      row.m_data      = data; 
      row.m_crtUsrId  = userId;
      row.m_crtTs     = DateTimeUtil.getCurrentDateTime();
      row.m_updUsrId  = SboType.NULL_ID;    
      row.m_updTs     = DbDataType.NULL_DATE_TIME;
            
      return row;
   
   } 
   
   private static DaoPageAnnsRowUpd fillAnnRowUpd(String data,
                                                  int userId)
                                    throws Exception
   {
   
      DaoPageAnnsRowUpd row = new DaoPageAnnsRowUpd();        
      
      row.m_data      = data;      
      row.m_updUsrId  = userId;    
      row.m_updTs     = DateTimeUtil.getCurrentDateTime();
      
      return row;
   
   } 
   
} // class
