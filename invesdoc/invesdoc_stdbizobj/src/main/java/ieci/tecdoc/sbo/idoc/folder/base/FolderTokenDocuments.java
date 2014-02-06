
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.collections.IeciTdLiLiArrayList;
import ieci.tecdoc.core.collections.IeciTdLiLi;

/**
 * Representa una lista de nodos clasificadores, {@link FolderTokenDocument}. 
 * Proporciona métodos para gestionar dicha lista.
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode
 * @see ieci.tecdoc.sbo.idoc.api.FolderDocumentObjects
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks
 */
public final class FolderTokenDocuments extends FolderTokenDocTreeNodes implements Serializable
{
 
   /**
    * Constructor
    *
    */
   public FolderTokenDocuments()
   {
      super();     
   }
      
   /**
    * Agrega un documento a la lista
    * 
    * @param id identificador del documento
    * @param name nombre del documento
    * @param parentId identificador del nodo padre 
    * @param parentDocId identificador del clasificador del documento
    * @param fileId identificador del fichero
    * @param fileExt extension del fichero
    * @param sortOrder numero de orden del documento
    * @throws Exception
    */
   public void add(int id, String name, int parentId, int parentDocId, 
                   int fileId, String fileExt, int sortOrder, int annId)
               throws Exception
   {
      
      FolderTokenDocument doc;
      
      doc = new FolderTokenDocument(id, name, parentId, parentDocId, fileId, 
                                    fileExt, sortOrder, annId);
      
      super.add(doc);
      
   }  
   
   /**
    * Agrega un nuevo documento a la lista. Genera el nuevo identificador
    * para dicho documento
    * 
    * @param name nombre del documento
    * @param parentId identificador del nodo padre 
    * @param fileExt extension del fichero
    * @param sortOrder numero de orden del documento
    * @throws Exception
    */
   protected void addNewDocument(String name, int parentId,
                                 String fileExt, int sortOrder,
                                 String pathDocumentFile, String pathDocAnnFile) 
                  throws Exception
   {

      FolderTokenDocument doc;
      int                 id;
      
      id = super.generateNextNewNodeId(); 

      doc = new FolderTokenDocument(id, name, parentId, id, fileExt, sortOrder,
                                    pathDocumentFile, pathDocAnnFile, FolderEditFlag.NEW);

      super.add(doc);
   }  
   
   
   
   
   
   
   /**
    * Agrega un nuevo documento a la lista. Genera el nuevo identificador
    * para dicho documento
    * 
    * @param name nombre del documento
    * @param parentId identificador del nodo padre 
    * @param fileExt extension del fichero
    * @param sortOrder numero de orden del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @throws Exception
    */
   protected void addNewDocument(String name, int parentId,
                                 String fileExt, int sortOrder,
                                 InputStream inputStreamDocumentFile) 
                  throws Exception
   {

     addNewDocument (name, parentId, fileExt, sortOrder, inputStreamDocumentFile, null);
   }  
   
   
   
   /**
    * Agrega un nuevo documento a la lista. Genera el nuevo identificador
    * para dicho documento
    * 
    * @param name nombre del documento
    * @param parentId identificador del nodo padre 
    * @param fileExt extension del fichero
    * @param sortOrder numero de orden del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @param inputStreamAnnFile inputStream del documento de anotaciones
    * @throws Exception
    */
   protected void addNewDocument(String name, int parentId,
                                 String fileExt, int sortOrder,
                                 InputStream inputStreamDocumentFile,
                                 Reader readerAnnFile) 
                  throws Exception
   {

      FolderTokenDocument doc;
      int                 id;
      
      id = super.generateNextNewNodeId(); 

      doc = new FolderTokenDocument(id, name, parentId, id, fileExt, sortOrder,
                                    inputStreamDocumentFile, readerAnnFile, FolderEditFlag.NEW);

      super.add(doc);
   }  
   
   
   /**
    * Devuelve el documento indicado de la lista
    * @param index indice del documento
    * @return el documento buscado
    * @see FolderTokenDocument
    */
   public FolderTokenDocument get(int index)
   {
      return (FolderTokenDocument) super.getNodes().get(index);
   } 
   
   /**
    * Busca un documento en la lista a partir de su id
    * 
    * @param id identificador del documento
    * @return documento buscado
    * @throws Exception devuelve IeciTdException si no existe
    * @see FolderTokenDocument
    */
   public FolderTokenDocument findById(int id) throws Exception
   {
      int                 idx;
      FolderTokenDocument doc = null;
           
      idx = super.findIndexById(id);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      doc = get(idx);
      
      return doc;
   }
   
   /**
    * Busca un documento en la lista a partir de su nombre
    *  
    * @param name nombre del documento
    * @return documento buscado
    * @throws Exception devuelve IeciTdException si no existe
    * @see FolderTokenDocument
    */
   public FolderTokenDocument findByName(String name) throws Exception
   {
      int                 idx;
      FolderTokenDocument doc = null;
           
      idx = super.findIndexByName(name);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      doc = get(idx);
      
      return doc;
   }
   
   /**
    * Devuelve una lista con todos los documentos
    * 
    * @param alsoRemoveDocs Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista con todos los documentos
    * @throws Exception
    * @see FolderTokenDocuments
    */
   protected FolderTokenDocuments getAllDocuments(boolean alsoRemoveDocs) 
                                  throws Exception
   {
      int                  i;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc; 
      boolean              addDoc; 
      
      for(i = 0; i < count(); i++)
      {
         addDoc = true;
         
         doc = get(i);
         
         if (doc.isRemove() && !alsoRemoveDocs)
         {
            addDoc = false;            
         }
         
         if (addDoc)
            docs.add(doc);
      }
      
      return docs;
   }
   
   /**
    * Devuelve todos los documentos hijos del nodo especificado
    * 
    * @param parentId identificador del nodo
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return los documentos hijos del nodo especificado
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getChildren(int parentId, boolean alsoRemoveChildren) 
                               throws Exception
   {
      int                  i;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc;
      boolean              addDoc;      
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         if (doc.getParentId() == parentId)
         {
            addDoc = true;
            
            if (!alsoRemoveChildren && doc.isRemove())
               addDoc = false;
            
            if (addDoc)
               docs.add(doc);   
         }
      }
      
      return docs;
   }

   /**
    * Devuelve el numero de hijos de un nodo
    * 
    * @param parentId Identificador del nodo
    * @param byParentDocId si este flag es <tt>false</tt>, se busca por 
    * clasificador
    * @return numero de hijos
    * @throws Exception
    */
   public int getNumChildren(int parentId, boolean byParentDocId) 
                              throws Exception
   {
      int                  i;      
      FolderTokenDocument  doc;
      int                  numChildren = 0;
      int                  pId;
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         if (byParentDocId)
            pId = doc.getParentDocId();
         else
            pId = doc.getParentId();        
         
         if (parentId == pId)
            numChildren = numChildren + 1;        
        
      }
   
      return numChildren;
   }

   private void refreshParentId(int parentId, int newParentId) 
                throws Exception
   {
      FolderTokenDocuments docs = null;
      int                  i;
      FolderTokenDocument  doc = null;
      
      docs = getChildren(parentId, true);
      
      for(i = 0; i < docs.count(); i++)
      {
         doc = docs.get(i);
         doc.changeParentId(newParentId);
      }       

   }
   
   protected void refreshParentId(IeciTdLiLiArrayList newParents) 
                  throws Exception
   {
      
      int         i;
      IeciTdLiLi  newParent;
      int         parentId;
      int         newParentId;
      
      for(i = 0; i < newParents.count(); i++)
      {
         newParent   = newParents.get(i);
         parentId    = newParent.m_val1;
         newParentId = newParent.m_val2;
         
         refreshParentId(parentId, newParentId);         
      } 
     
   }   
   
   protected FolderTokenDocuments getNewDocuments() 
                                  throws Exception
   {
      int                  i, j;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc; 
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         if (doc.isNew())
         {                                                       
            docs.add(doc);  
         }
         
      }
      
      return docs;
   }
   
   protected void refreshNewDocument(int id, int newDocId, 
                                     int newParentDocId, FolderDocUpdIds docUpdIds) 
                  throws Exception
   {
   
      FolderTokenDocument doc = findById(id);
      
      doc.changeId(newDocId);
      doc.changeParentDocId(newParentDocId);
      doc.changeFileId(docUpdIds.getFileId());
      doc.clearFile();
      doc.changeAnnId(docUpdIds.getAnnId());
      doc.clearAnnFile();      
      doc.clearEditInfo();
      
   } 
   
   protected FolderTokenDocuments getRemoveDocuments() 
                                  throws Exception
   {
      int                  i;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc;   
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         if (doc.isRemove())
         {
           docs.add(doc);
         }         
      }
      
      return docs;
   }
   
   protected FolderTokenDocuments getUpdateDocuments() 
                                  throws Exception
   {
      int                  i, j;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc; 
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         if (doc.isUpdate())
         {                                                       
            docs.add(doc);  
         }
         
      }
      
      return docs;
   }
   
   protected void refreshUpdateDocument(int id, FolderDocUpdIds docUpdIds) 
                  throws Exception
   {
   
      FolderTokenDocument doc = findById(id);      
      
      doc.changeFileId(docUpdIds.getFileId());
      doc.clearFile();
      doc.changeAnnId(docUpdIds.getAnnId());
      doc.clearAnnFile();
      doc.clearEditInfo();
      
   } 

   protected int getNextSortOrder() throws Exception
   {
   
      int                  i;
      FolderTokenDocuments docs = new FolderTokenDocuments();
      FolderTokenDocument  doc; 
      int                  nextSortOrder = 0;
      int                  sortOrder;
      
      for(i = 0; i < count(); i++)
      {
         doc = get(i);
         
         sortOrder = doc.getSortOrder();

         if (sortOrder > nextSortOrder)
            nextSortOrder = sortOrder;         
      }

      nextSortOrder = nextSortOrder + 1;

      return nextSortOrder;
      
   } 

    
} // class
