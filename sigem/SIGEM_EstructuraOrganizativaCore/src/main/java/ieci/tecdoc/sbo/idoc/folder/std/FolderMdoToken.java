
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTree;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenExtFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenMultFlds;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenRelFlds;
import ieci.tecdoc.sbo.idoc.folder.divider.FolderMdoDividerToken;
import ieci.tecdoc.sbo.idoc.folder.document.FolderMdoDocumentToken;
import ieci.tecdoc.sbo.idoc.folder.fdrlink.FolderMdoFdrLinkToken;
import ieci.tecdoc.sbo.idoc.folder.field.FolderMdoFldsToken;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


public class FolderMdoToken
{
   
   private FolderMdoToken()
   {      
   }   
   
   protected static FolderTokenFlds createFolderTokenFlds(DbConnection dbConn, ArchiveToken arch,
                                                          int fdrId, String entidad)
                                  throws Exception
   {  
      FolderTokenFlds       fldVals  = null; 
      FolderTokenRelFlds    relVals  = null;
      FolderTokenMultFlds   multVals = null;
      FolderTokenExtFlds    extVals  = null; 

      fldVals = new FolderTokenFlds();  
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId, entidad);
      fldVals.setRelFlds(relVals);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(dbConn, arch, fdrId);
      fldVals.setMultFlds(multVals);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(dbConn, arch, fdrId);
      fldVals.setExtFlds(extVals);    
            
      return fldVals;    
   }
   
   
   public static FolderTokenDocTree createFolderTokenDocTree(DbConnection dbConn, ArchiveToken arch,
                                                             int fdrId)
                                    throws Exception
   {  
      FolderTokenDocTree    docTree   = null; 
      FolderTokenDividers   dividers  = null;
      FolderTokenDocuments  documents = null;
      FolderTokenFdrLinks   fdrLinks  = null;  
      
      docTree = new FolderTokenDocTree();  
      
      dividers = FolderMdoDividerToken.createDividersToken(dbConn, arch.getTblPrefix(),
                                                           fdrId);    
      docTree.setDividers(dividers);
      
      documents = FolderMdoDocumentToken.createDocumentsToken(dbConn, arch.getTblPrefix(),
                                                              arch.getId(), fdrId);
      docTree.setDocuments(documents);
      
      fdrLinks = FolderMdoFdrLinkToken.createFdrLinksToken(dbConn, arch.getId(),
                                                           fdrId);
      docTree.setFolderLinks(fdrLinks);       
      
      return docTree;    
   }
   
   public static FolderToken createFolderToken(DbConnection dbConn, ArchiveToken arch, int fdrId,
                                               int fdrAcsId, int fdrAcsType, String entidad)
                             throws Exception
   {  
      FolderToken           token;       
      FolderTokenFlds       flds      = null;      
      FolderTokenDocTree    docTree   = null; 
      
      token = new FolderToken();      
      
      token.setFdrInfo(fdrId, fdrAcsType, fdrAcsId, false);
      
      flds    = createFolderTokenFlds(dbConn, arch, fdrId, entidad);      
      docTree = createFolderTokenDocTree(dbConn, arch, fdrId); 
      
      token.setFlds(flds);
      token.setDocumentTree(docTree);      
      
      return token;    
   } 
   
   public static FolderToken createFolderToken(DbConnection dbConn, ArchiveToken arch, int fdrId,
                                               int fdrAcsId, int fdrAcsType,
                                               FolderTokenFlds flds)
                             throws Exception
   {  
      FolderToken           token;  
      FolderTokenDocTree    docTree   = null; 
      
      token = new FolderToken();
      
      token.setFdrInfo(fdrId, fdrAcsType, fdrAcsId,
                       false);
         
      docTree = createFolderTokenDocTree(dbConn, arch, fdrId); 
      
      token.setFlds(flds);
      token.setDocumentTree(docTree);      
      
      return token;    
   }
   
   public static FolderToken createFolderToken(ArchiveToken arch, String entidad)
                             throws Exception
   {  
      FolderToken           token;       
      FolderTokenFlds       flds      = null;      
      FolderTokenDocTree    docTree   = null; 
      boolean               isNew     = true;
      int                   fdrId;
      
      token = new FolderToken(); 
      
      fdrId = FolderMdoXNextId.generateNextFolderId(arch.getId(), entidad); 
      
      token.setFdrInfo(fdrId, AcsAccessType.ACS_PUBLIC, SboType.NULL_ID,
                       isNew);
      
      flds    = createFolderTokenFlds(arch);      
      docTree = createFolderTokenDocTree(arch); 
      
      token.setFlds(flds);
      token.setDocumentTree(docTree);      
      
      return token;    
   } 
   
   //Para carpetas nuevas
   public static FolderTokenFlds createFolderTokenFlds(ArchiveToken arch)
                                 throws Exception
   {  
      FolderTokenFlds       fldVals  = null; 
      FolderTokenRelFlds    relVals  = null;
      FolderTokenMultFlds   multVals = null;
      FolderTokenExtFlds    extVals  = null; 

      fldVals = new FolderTokenFlds();  
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch);
      fldVals.setRelFlds(relVals);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(arch);
      fldVals.setMultFlds(multVals);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(arch);
      fldVals.setExtFlds(extVals);    
            
      return fldVals;    
   }
   
   public static FolderTokenDocTree createFolderTokenDocTree(ArchiveToken arch)
                                    throws Exception
   {  
      FolderTokenDocTree    docTree   = null; 
      FolderTokenDividers   dividers  = null;
      FolderTokenDocuments  documents = new FolderTokenDocuments();
      FolderTokenFdrLinks   fdrLinks  = new FolderTokenFdrLinks();  
      
      docTree = new FolderTokenDocTree();  
      
      dividers = FolderMdoDividerToken.createDividersToken(arch);    
      docTree.setDividers(dividers);
      
      docTree.setDocuments(documents);
      
      docTree.setFolderLinks(fdrLinks);       
      
      return docTree;    
   } 
   
   
} // class
