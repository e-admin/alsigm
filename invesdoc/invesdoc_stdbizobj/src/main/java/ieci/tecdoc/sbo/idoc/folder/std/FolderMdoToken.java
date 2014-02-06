
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.sbo.idoc.folder.base.*;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.sbo.idoc.folder.field.FolderMdoFldsToken;
import ieci.tecdoc.sbo.idoc.folder.divider.FolderMdoDividerToken;
import ieci.tecdoc.sbo.idoc.folder.document.FolderMdoDocumentToken;
import ieci.tecdoc.sbo.idoc.folder.fdrlink.FolderMdoFdrLinkToken;

public class FolderMdoToken
{
   
   private FolderMdoToken()
   {      
   }   
   
   protected static FolderTokenFlds createFolderTokenFlds(ArchiveToken arch,
                                                          int fdrId)
                                  throws Exception
   {  
      FolderTokenFlds       fldVals  = null; 
      FolderTokenRelFlds    relVals  = null;
      FolderTokenMultFlds   multVals = null;
      FolderTokenExtFlds    extVals  = null; 

      fldVals = new FolderTokenFlds();  
      
      relVals =  FolderMdoFldsToken.createRelFldsToken(arch, fdrId);
      fldVals.setRelFlds(relVals);
      
      multVals =  FolderMdoFldsToken.createMultFldsToken(arch, fdrId);
      fldVals.setMultFlds(multVals);
      
      extVals =  FolderMdoFldsToken.createExtFldsToken(arch, fdrId);
      fldVals.setExtFlds(extVals);    
            
      return fldVals;    
   }
   
   
   public static FolderTokenDocTree createFolderTokenDocTree(ArchiveToken arch,
                                                             int fdrId)
                                    throws Exception
   {  
      FolderTokenDocTree    docTree   = null; 
      FolderTokenDividers   dividers  = null;
      FolderTokenDocuments  documents = null;
      FolderTokenFdrLinks   fdrLinks  = null;  
      
      docTree = new FolderTokenDocTree();  
      
      dividers = FolderMdoDividerToken.createDividersToken(arch.getTblPrefix(),
                                                           fdrId);    
      docTree.setDividers(dividers);
      
      documents = FolderMdoDocumentToken.createDocumentsToken(arch.getTblPrefix(),
                                                              arch.getId(), fdrId);
      docTree.setDocuments(documents);
      
      fdrLinks = FolderMdoFdrLinkToken.createFdrLinksToken(arch.getId(),
                                                           fdrId);
      docTree.setFolderLinks(fdrLinks);       
      
      return docTree;    
   }
   
   public static FolderToken createFolderToken(ArchiveToken arch, int fdrId,
                                               int fdrAcsId, int fdrAcsType)
                             throws Exception
   {  
      FolderToken           token;       
      FolderTokenFlds       flds      = null;      
      FolderTokenDocTree    docTree   = null; 
      
      token = new FolderToken();      
      
      token.setFdrInfo(fdrId, fdrAcsType, fdrAcsId, false);
      
      flds    = createFolderTokenFlds(arch, fdrId);      
      docTree = createFolderTokenDocTree(arch, fdrId); 
      
      token.setFlds(flds);
      token.setDocumentTree(docTree);      
      
      return token;    
   } 
   
   public static FolderToken createFolderToken(ArchiveToken arch, int fdrId,
                                               int fdrAcsId, int fdrAcsType,
                                               FolderTokenFlds flds)
                             throws Exception
   {  
      FolderToken           token;  
      FolderTokenDocTree    docTree   = null; 
      
      token = new FolderToken();
      
      token.setFdrInfo(fdrId, fdrAcsType, fdrAcsId,
                       false);
         
      docTree = createFolderTokenDocTree(arch, fdrId); 
      
      token.setFlds(flds);
      token.setDocumentTree(docTree);      
      
      return token;    
   }
   
   public static FolderToken createFolderToken(ArchiveToken arch)
                             throws Exception
   {  
      FolderToken           token;       
      FolderTokenFlds       flds      = null;      
      FolderTokenDocTree    docTree   = null; 
      boolean               isNew     = true;
      int                   fdrId;
      
      token = new FolderToken(); 
      
      fdrId = FolderMdoXNextId.generateNextFolderId(arch.getId()); 
      
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
