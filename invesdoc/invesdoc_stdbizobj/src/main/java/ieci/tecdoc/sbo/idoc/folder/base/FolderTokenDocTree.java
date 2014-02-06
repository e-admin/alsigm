
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.collections.IeciTdLiLiArrayList;
import ieci.tecdoc.sbo.util.types.SboType;

/**
 * Esta clase encapsula el árbol de documentos para una carpeta.
 * Esta formado por nodos, que pueden ser clasificadores, documentos
 * o enlaces a otras carpetas.
 *  
 * @author
 * 
 * @see FolderTokenDividers
 * @see FolderTokenDocuments
 * @see FolderTokenFdrLinks
 *
 */
public final class FolderTokenDocTree implements Serializable
{   
   /** Patron para una expresion regular necesaria para el formato de nombres de fichero */
   private static final String SUFIX_REGEX = "_\\([0-9]+\\)";
   
   /** Nombre del nodo Raiz */
   private String               m_rootName;
   /** Lista de nodos clasificadores */
   private FolderTokenDividers  m_dividers;
   /** Lista de documento */
   private FolderTokenDocuments m_documents;
   /** Lista de enlaces a otras carpetas */
   private FolderTokenFdrLinks  m_fdrLinks;
   
   /**
    * Constructor
    *
    */
   public FolderTokenDocTree()
   {
      m_rootName  = FolderBaseDefs.CLF_ROOT_NAME;
      m_dividers  = null;  
      m_documents = null; 
      m_fdrLinks  = null;   
   }
   
   /**
    * Devuelvel el nombre del nodo raiz
    * 
    * @return el nombre del nodo raiz
    */
   public String getRootName()
   {
      return m_rootName;
   }
   
   /**
    * Devuelve la lista de nodos clasificadores
    * 
    * @param alsoRemoveDividers Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista de nodos clasificadores
    * @throws Exception 
    * @see FolderTokenDividers
    */
   public FolderTokenDividers getAllDividers(boolean alsoRemoveDividers)
                              throws Exception
   {
      return m_dividers.getAllDividers(alsoRemoveDividers);
   }
   
   /**
    * Devuelve la lista de nodos hijos del nodo clasificador raiz
    * 
    * @param alsoRemoveDividers Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista de nodos hijos del nodo clasificador raiz
    * @throws Exception 
    * @see FolderTokenDividers
    */
   public FolderTokenDividers getRootDividerChildren(boolean alsoRemoveChildren)
                              throws Exception
   {
      return m_dividers.getChildren(FolderBaseDefs.CLF_ROOT_ID, alsoRemoveChildren);
   }
   
   /**
    * Devuelve la lista de hijos de un clasificador
    * 
    * @param parentDivId identificador del clasificador
    * @param alsoRemoveDividers Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista de hijos de un clasificador
    * @throws Exception
    * @see FolderTokenDividers
    */
   public FolderTokenDividers getDividerChildren(int parentDivId, boolean alsoRemoveChildren)
                              throws Exception
   {
      return m_dividers.getChildren(parentDivId, alsoRemoveChildren);
   }
   
   /**
    * Devuelve el clasificador especificado por id
    * 
    * @param divId identificador del clasificador
    * @return clasificador buscado
    * @throws Exception
    * @see FolderTokenDivider
    */
   public FolderTokenDivider getDivider(int divId)
                             throws Exception
   {      
      return m_dividers.findById(divId);       
   }
   
   /**
    * Devuelve el identificador de un clasificador a partir del
    * nombre de este
    * 
    * @param name nombre del clasificador
    * @return identificador del clasificador 
    * @throws Exception
    */
   public int getDividerId(String name)
              throws Exception
   {      
      return m_dividers.getNodeId(name);      
   }

   /**
    * Cambia el nombre de un clasificador
    * 
    * @param divId identificador del clasificador
    * @param name nuevo nombre del clasificador
    * @throws Exception arroja IeciTdException si el nuevo nombre
    * no es válido
    */
   public void renameDivider(int divId, String name)
               throws Exception
   {   
      FolderTokenDivider div = m_dividers.findById(divId);
  
      checkValidDividerName(div.getParentId(), name);

      div.rename(name);   
   }  
   
   /**
    * Devuelve el numero de clasificadores nuevos que contiene la carpeta
    * 
    * @return numero de clasificadores nuevos que contiene la carpeta
    * @throws Exception
    */
   public int getNumNewDividers() throws Exception
   {      
      return m_dividers.getNumNewNodes();       
   }
   
   /**
    * Devuelve la lista de clasificadores nuevos. Se ordena para que 
    * vengan los padres antes que los hijos
    * 
    * @return lista de clasificadores nuevos
    * @throws Exception
    */
   public FolderTokenDividers getNewDividers() throws Exception
   {
      return m_dividers.getNewDividers();
   } 
   
   /**
    * Devuelve el numero de clasificadores marcados para eliminar
    * 
    * @return numero de clasificadores marcados para eliminar
    * @throws Exception
    */
   public int getNumRemoveDividers() throws Exception
   {      
      return m_dividers.getNumRemoveNodes();       
   } 
   
   /**
    * Devuelve la lista de clasificadores marcados para eliminar
    * 
    * @return lista de clasificadores marcados para eliminar
    * @throws Exception
    * @see FolderTokenDividers
    */
   public FolderTokenDividers getRemoveDividers() throws Exception
   {
      return m_dividers.getRemoveDividers();
   }
   
   /**
    * Devuelve el numero de clasificadores marcados como modificados
    *  
    * @return el numero de clasificadores marcados como modificados
    * @throws Exception
    */
   public int getNumUpdateDividers() throws Exception
   {      
      return m_dividers.getNumUpdateNodes();       
   } 
   
   /**
    * Devuelve la lista de clasificadores marcados como modificados
    *  
    * @return lista de clasificadores marcados como modificados
    * @throws Exception
    * @see FolderTokenDividers
    */
   public FolderTokenDividers getUpdateDividers() throws Exception
   {
      return m_dividers.getUpdateDividers();
   }

   /**
    * Devuelve una la lista con todos los documentos de la carpeta
    * 
    * @param alsoRemoveDocs Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista con todos los documentos de la carpeta
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getAllDocuments(boolean alsoRemoveDocs)
                               throws Exception
   {
      return m_documents.getAllDocuments(alsoRemoveDocs);
   }
   
   /**
    * Devuelve el identificador de un documento a partir de su nombre
    * 
    * @param name nombre del documento
    * @return identificador del documento 
    * @throws Exception
    */
   public int getDocumentId(String name)
              throws Exception
   {      
      return m_documents.getNodeId(name);      
   }
   
   /**
    * Devuelve los documentos del clasificador raiz
    * 
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return los documentos del clasificador raiz
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getRootDocumentChildren(boolean alsoRemoveChildren)
                               throws Exception
   {
      return m_documents.getChildren(FolderBaseDefs.CLF_ROOT_ID, alsoRemoveChildren);
   }
   
   /**
    * Devuelve los documentos hijos de un clasificador
    * 
    * @param parentDivId identificador del clasificador padre
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return los documentos hijos de un clasificador
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getDocumentChildren(int parentDivId, boolean alsoRemoveChildren)
                               throws Exception
   {
      return m_documents.getChildren(parentDivId, alsoRemoveChildren);
   }
   
   /**
    * Devuelve el numero de documentos nuevos del árbol
    * 
    * @return el numero de documentos nuevos del árbol
    * @throws Exception
    */
   public int getNumNewDocuments() throws Exception
   {      
      return m_documents.getNumNewNodes();       
   }
   
   /**
    * Devuelve la lista de documentos nuevos del arbol
    * 
    * @return la lista de documentos nuevos del arbol
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getNewDocuments() throws Exception
   {
      return m_documents.getNewDocuments();
   }
   
   /**
    * Devuelve el numero de documentos marcados como eliminados 
    * en el árbol
    * 
    * @return numero de documentos marcados como eliminados
    * @throws Exception
    */
   public int getNumRemoveDocuments() throws Exception
   {      
      return m_documents.getNumRemoveNodes();       
   }
   
   /**
    * Devuelve la lista de documentos marcados como eliminados en 
    * el arbol
    * 
    * @return la lista de documentos marcados como eliminados
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getRemoveDocuments() throws Exception
   {
      return m_documents.getRemoveDocuments();
   }

   /**
    * Devuelve el numero de documentos marcados como modificados 
    * en el árbol
    * 
    * @return numero de documentos marcados como modificados
    * @throws Exception
    */
   public int getNumUpdateDocuments() throws Exception
   {      
      return m_documents.getNumUpdateNodes();       
   }
   
   /**
    * Devuelve la lista de documentos modificados del arbol
    * 
    * @return lista de documentos modificados del arbol
    * @throws Exception
    * @see FolderTokenDocuments
    */
   public FolderTokenDocuments getUpdateDocuments() throws Exception
   {
      return m_documents.getUpdateDocuments();
   }
   
   /**
    * Devuelve un enlace a partir de su id
    * 
    * @param fdrLinkId identificador del enlace
    * @return enlace buscado
    * @throws Exception
    * @see FolderTokenFdrLink
    */
   public FolderTokenFdrLink getFolderLink(int fdrLinkId)
                            throws Exception
   {      
      return m_fdrLinks.findById(fdrLinkId);       
   }
   
   /**
    * Devuelve los enlaces hijos del clasificador raiz
    * 
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return enlaces hijos del clasificador raiz
    * @throws Exception
    * @see FolderTokenFdrLinks
    */
   public FolderTokenFdrLinks getRootFolderLinkChildren(boolean alsoRemoveChildren)
                              throws Exception
   {
      return m_fdrLinks.getChildren(FolderBaseDefs.CLF_ROOT_ID, alsoRemoveChildren);
   }

   /**
    * Devuelve la lista de enlaces hijos de un clasificador
    * 
    * @param parentDivId identificador del clasificador
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista de enlaces hijos de el clasificador
    * @throws Exception
    * @see FolderTokenFdrLinks
    */
   public FolderTokenFdrLinks getFolderLinkChildren(int parentDivId, boolean alsoRemoveChildren)
                              throws Exception
   {
      return m_fdrLinks.getChildren(parentDivId, alsoRemoveChildren);
   }
         
   /**
    * Establece el árbol de clasificadores de la carpeta
    * 
    * @param dividers árbol de clasificadores de la carpeta
    * @see FolderTokenDividers
    */
   public void setDividers(FolderTokenDividers dividers)
   {      
      m_dividers = dividers;       
   }
      
   /**
    * Establece la lista de documentos de la carpeta
    * 
    * @param documents lista de documentos de la carpeta
    * @see FolderTokenDocuments
    */
   public void setDocuments(FolderTokenDocuments documents)
   {      
      m_documents = documents;       
   } 
   
   /**
    * Devuelve la lista de enlaces de la carpeta
    * 
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @see FolderTokenFdrLinks
    */
   public FolderTokenFdrLinks getAllFolderLinks(boolean alsoRemoveLinks)
                              throws Exception
   {
      return m_fdrLinks.getAllFolderLinks(alsoRemoveLinks);     
   }
   
   /**
    * Establece la lista de enlaces de la carpeta
    * 
    * @param fdrLinks lista de enlaces de la carpeta
    * @see FolderTokenFdrLinks
    */
   public void setFolderLinks(FolderTokenFdrLinks fdrLinks)
   {      
      m_fdrLinks = fdrLinks;       
   }
   
   /**
    * Devuelve el documento especificado de la carpeta
    * 
    * @param docId identificador del documento
    * @return documento especificado
    * @throws Exception
    * @see FolderTokenDocument
    */
   public FolderTokenDocument getDocument(int docId)
                              throws Exception
   {      
      return m_documents.findById(docId);       
   }

   /**
    * Agrega el clasificador raiz
    * @param name Nombre del clasificador
    * @return el identificador del clasificador añadido
    * @throws Exception devuelve IeciTdException si el nombre del clasificador 
    * no es válido
    */
   public int addRootDivider(String name)
               throws Exception
   {    
      int divId = SboType.NULL_ID;
        
      divId = addDivider(name, FolderBaseDefs.CLF_ROOT_ID); 
      
      return divId;  
   }
   
   /**
    * Agrega un clasificador al arbol de documentos
    * 
    * @param name nombre del nuevo clasificador
    * @param parentDivId identificador del clasificador padre
    * @return identificador del clasificador añadido
    * @throws Exception devuelve IeciTdException si el nombre del clasificador 
    * no es válido
    */
   public int addDivider(String name, int parentDivId)
              throws Exception
   { 
      int divId = SboType.NULL_ID;

      checkExistsParent(parentDivId);

      checkValidDividerName(parentDivId, name);

      divId = m_dividers.addNewDivider(name, parentDivId, 
                                       false);
                               
      return divId;
   } 
   
   /**
    * Agrega un documento al clasificador raiz
    * 
    * @param name nombre del documento
    * @param fileExt extension del documento
    * @param pathDocumentFile path del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addRootDocument(String name, String fileExt,
                              String pathDocumentFile)
              throws Exception
   {   
      int sortOrder = m_documents.getNextSortOrder();

      addDocument(name, FolderBaseDefs.CLF_ROOT_ID, fileExt, sortOrder, 
                  pathDocumentFile, null);  
   }
   
   /**
    * Agrega un documento al clasificador raiz
    * 
    * @param name nombre del documento
    * @param fileExt extension del documento
    * @param sortOrder numero de orden del documento
    * @param pathDocumentFile path del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addRootDocument(String name, String fileExt,
                               int sortOrder, String pathDocumentFile)
              throws Exception
   {   
                
      addDocument(name, FolderBaseDefs.CLF_ROOT_ID,
                  fileExt, sortOrder, pathDocumentFile, null);                          
        
   }

   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param pathDocumentFile path del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
            			      String fileExt, String pathDocumentFile)
   				throws Exception
   { 
	  int sortOrder = m_documents.getNextSortOrder();
	
	  addDocument(name, parentDivId, fileExt, sortOrder, pathDocumentFile, null);
   }

   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param pathDocumentFile path del documento
    * @param pathDocAnnFile path del fichero de anotaciones. Null si no tiene anotaciones
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
            			      String fileExt,String pathDocumentFile,
                           String pathDocAnnFile)	throws Exception
   { 
	  int sortOrder = m_documents.getNextSortOrder();
	
	  addDocument(name, parentDivId, fileExt, sortOrder, pathDocumentFile,
                 pathDocAnnFile);
   }
   
   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param sortOrder numero de orden del documento
    * @param pathDocumentFile path del documento
    * @param pathDocAnnFile path del fichero de anotaciones. Null si no tiene anotaciones
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
                           String fileExt, int sortOrder,
                           String pathDocumentFile, String pathDocAnnFile)
               throws Exception
   { 
            
      checkExistsParent(parentDivId);
      
      checkValidDocumentName(parentDivId, name);
      
      m_documents.addNewDocument(name, parentDivId, fileExt,
                                 sortOrder, pathDocumentFile, pathDocAnnFile);                                 
     
   }
   
   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
                           String fileExt, 
                           InputStream inputStreamDocumentFile)
                throws Exception
   { 
      int sortOrder = m_documents.getNextSortOrder();
    
      addDocument(name, parentDivId, fileExt, sortOrder, inputStreamDocumentFile, null);
   }
   
   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param sortOrder numero de orden del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
                           String fileExt, int sortOrder,
                           InputStream inputStreamDocumentFile)
             throws Exception
   {             
     addDocument(name, parentDivId, fileExt, sortOrder, inputStreamDocumentFile, null);
   }
   
   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
                           String fileExt, 
                           InputStream inputStreamDocumentFile, Reader readerAnnFile)
                throws Exception
   { 
      int sortOrder = m_documents.getNextSortOrder();
    
      addDocument(name, parentDivId, fileExt, sortOrder, inputStreamDocumentFile, readerAnnFile);
   }
   
   /**
    * Agrega un nuevo documento a un clasificador
    * 
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador padre
    * @param fileExt extension del documento
    * @param sortOrder numero de orden del documento
    * @param inputStreamDocumentFile inputStream del documento
    * @throws Exception arroja IeciTdException si el clasificador no existe o 
    * si ha sido marcado para eliminar
    */
   public void addDocument(String name, int parentDivId,
                           String fileExt, int sortOrder,
                           InputStream inputStreamDocumentFile, Reader readerAnnFile)
             throws Exception
   { 
            
      checkExistsParent(parentDivId);
      
      checkValidDocumentName(parentDivId, name);
      
      m_documents.addNewDocument(name, parentDivId, fileExt,
                                 sortOrder, inputStreamDocumentFile, readerAnnFile);                                 
     
   }
      
   
   /**
    * Devuelve un nombre valido para un documento, que no supere el numero maximo de 
    * caracteres ni este repetido. 
    * 
    * @param parentDivId Identificador del padre
    * @param docName Nombre de documento
    * @return El nombre del documento obtenido
    * @throws Exception arroja IeciTdException si no es posible encontrar un 
    * nombre válido para el documento
    */
   public String getValidDocumentName(int parentDivId, String docName) throws Exception
   {
      String validName = null;
      String fileExt = "";
      boolean isValid = false;
      
      // primero validamos que el padre exista
      this.checkExistsParent(parentDivId);
      
      // Separamos la extension del nombre del fichero
      int indExt = docName.lastIndexOf("."); 
      if(indExt > 0)
      {
         validName = docName.substring(0, indExt);
         fileExt = docName.substring(indExt);
      }
      else 
      {
         validName = docName;
      }
      
      // le ponemos un limite de 100 iteraciones antes de encontrar un nombre
      // valido, por peligro de entrar en un bucle infinito
      int count = 1;
      while(!isValid)
      {
         try 
         {
            checkValidDocumentName(parentDivId, validName + fileExt);
            isValid = true;
         }
         catch(IeciTdException ite)
         {
            if(FolderBaseError.EC_INVALID_NAME.equals(ite.getErrorCode()))
            {
               validName = this.reduceDocumentName(validName, FolderBaseDefs.DOCTREE_NODE_NAME_LEN - fileExt.length());
            }
            else if(FolderBaseError.EC_NAME_ALREADY_EXISTS.equals(ite.getErrorCode()))
            {
               validName = this.handleDocumentName(validName, count++, FolderBaseDefs.DOCTREE_NODE_NAME_LEN - fileExt.length() - 4);
            }
            else 
            {
               throw ite;
            }
         }
      }
      
      if(isValid)
         return validName + fileExt;
      else
         throw new IeciTdException(FolderBaseError.EC_INVALID_NAME, FolderBaseError.EM_INVALID_NAME);
   }

   /**
    * Agrega al nombre del documento una cadena del tipo _(index) donde index
    * es un indice pasado por parametro. Si este indice ya existe lo modifica<
    * 
    * @param name nombre del documento
    * @param index indice que se va a añadir al nombre del documento
    * @param maxSize tamaño máximo del nombre del documento
    * @return El nombre con la cadena _(index) concatenada
    */
   private String handleDocumentName(String name, int index, int maxSize)
   {
      int idx = 0;
      if(name.length() > maxSize)
      {
         name = name.substring(0, maxSize);
      }
      idx = name.lastIndexOf("_");
      if(idx > 0) 
      {
         String sufix = name.substring(idx);
         if(sufix.length() <= 5 && sufix.matches(SUFIX_REGEX)) 
         {
            name = name.substring(0, idx);
         }
      }
      name += "_(" + index + ")";

      return name;
   }

   /**
    * Reduce el tamaño del nombre del documento al maximo establecido.
    * El nombre debe llegar sin extension
    * 
    * @param name nombre reducido
    * @param maxSize tamaño maximo del nombre
    * @return nombre reducido
    */
   private String reduceDocumentName(String name, int maxSize)
   {
      if(name.length() <= maxSize)
      {
         return name;
      }
      else 
      {
         return name.substring(0, maxSize);
      }
   }

   /**
    * Chequea si existe el clasificador
    * 
    * @param parentDivId identificador del clasificador
    * @throws Exception arroja IeciTdException si no existe
    */
   private void checkExistsParent(int parentDivId)
                throws Exception
   {
      int                idx;
      FolderTokenDivider div = null;
      
      if (parentDivId == FolderBaseDefs.CLF_ROOT_ID) return;

      idx = m_dividers.findIndexById(parentDivId);

      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_PARENT, 
                                   FolderBaseError.EM_INVALID_PARENT); 
      }
      
      div = m_dividers.get(idx);
      
      if (div.isRemove())
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_PARENT, 
                                   FolderBaseError.EM_INVALID_PARENT); 
      }

   }
   
   /**
    * Chequea si existe el nombre del documento es valido
    * 
    * @param parentDivId identificador del clasificador
    * @param docName nombre del documento
    * @throws Exception arroja IeciTdException si el nombre no es 
    * válido o si ya existe
    */
   private void checkValidDocumentName(int parentDivId, 
                                       String docName)
                throws Exception
   {
      FolderTokenDocuments docs = null;      
      
      if ( (docName == null) || docName.equals("") ||
           (docName.length() > FolderBaseDefs.DOCTREE_NODE_NAME_LEN) )
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_NAME, 
                                   FolderBaseError.EM_INVALID_NAME); 
      }
         
      docs = getDocumentChildren(parentDivId, true);
      
      if (docs.nameExists(docName))
      {
         throw new IeciTdException(FolderBaseError.EC_NAME_ALREADY_EXISTS, 
                                   FolderBaseError.EM_NAME_ALREADY_EXISTS); 
      }
      
   }
   
   /**
    * 
    * @param parentDivId
    * @param divName
    * @throws Exception
    */
   private void checkValidDividerName(int parentDivId, 
                                      String divName)
                throws Exception
   {
      FolderTokenDividers divs = null; 
      
      if ( (divName == null) || divName.equals("") ||
           (divName.length() > FolderBaseDefs.DIVIDERTREE_NODE_NAME_LEN) )
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_NAME, 
                                   FolderBaseError.EM_INVALID_NAME); 
      }
      
      divs = getDividerChildren(parentDivId, true);
      
      if (divs.nameExists(divName))
      {
         throw new IeciTdException(FolderBaseError.EC_NAME_ALREADY_EXISTS, 
                                   FolderBaseError.EM_NAME_ALREADY_EXISTS); 
      }

   }
     
   /**
    * 
    * @param newDivIds
    * @throws Exception
    */
   public void refreshNewDividers(IeciTdLiLiArrayList newDivIds) 
               throws Exception
   {      
      m_dividers.refreshNewDividers(newDivIds); 
      m_documents.refreshParentId(newDivIds);
   }
   
   /**
    * Elimina todos los nodos marcados como eliminados
    * 
    * @throws Exception
    */
   public void refreshRemoveDividers() 
               throws Exception
   {      
      m_dividers.refreshRemoveNodes();      
   }
   
   /**
    * Encuentra todos los nodos marcados como modificados y les restea
    * el flag de estado, es decir, les quita la marca de modificados
    *
    */
   public void refreshUpdateDividers() 
               throws Exception
   {      
      m_dividers.refreshUpdateDividers();      
   }
   
   /**
    * Actualiza los valores de un documento nuevo
    * 
    * @param id identificador del documento
    * @param newDocId nuevo identificador del documento
    * @param newParentDocId identificador del clasificador del documento
    * @param newFileId identificador del fichero del documento
    * @throws Exception arroja IeciTdException si no encuentra el documento
    */
   public void  refreshNewDocument(int id, int newDocId, 
                                   int newParentDocId, FolderDocUpdIds docUpdIds)
                throws Exception
   {
      m_documents.refreshNewDocument(id, newDocId, newParentDocId, docUpdIds);
   }
   
   /**
    * Elimina definitivamente de la lista de nodos un nodo que estaba
    * marcado como eliminado. 
    * 
    * @param id identificador del nodo a borrar 
    * @throws Exception arroja IeciTdException si no encuentra el nodo
    * o si dicho nodo no esta marcado como eliminado.
    */
   public void  refreshRemoveDocument(int id)
                throws Exception
   {
      m_documents.refreshRemoveNode(id);
   }
   
   /**
    * Actualiza los valores de un documento modificado
    * 
    * @param id identificador del documento
    * @param docUpdIds identificador asociados al documento que se pueden haber modificado
    * @throws Exception arroja IeciTdException si no encuentra el documento
    */
   public void refreshUpdateDocument(int id, FolderDocUpdIds docUpdIds)
               throws Exception
   {
      m_documents.refreshUpdateDocument(id, docUpdIds);
   }
   
   /**
    * Checkea si un clasificador tiene o no hijos
    *  
    * @param divId identificador del clasificador
    * @throws Exception arroja IeciTdException si el clasificador
    * tiene hijos
    */
   private void checkDividerNoHasChildren(int divId)
                throws Exception
   {
      FolderTokenDividers  divs  = null; 
      FolderTokenDocuments docs  = null;
      FolderTokenFdrLinks  links = null;
      int                  i;
      boolean              alsoRemoveChildren = false;
      
      divs = getDividerChildren(divId, alsoRemoveChildren);
      
      if (divs.count() > 0)
      {
         throw new IeciTdException(FolderBaseError.EC_DIVIDER_HAS_CHILDREN, 
                                   FolderBaseError.EM_DIVIDER_HAS_CHILDREN);
        
      }
      
      docs = getDocumentChildren(divId, alsoRemoveChildren);
      
      if (docs.count() > 0)
      {
         throw new IeciTdException(FolderBaseError.EC_DIVIDER_HAS_CHILDREN, 
                                   FolderBaseError.EM_DIVIDER_HAS_CHILDREN);            
      }
      
      links = getFolderLinkChildren(divId, alsoRemoveChildren);
      
      if (links.count() > 0)
      {
         throw new IeciTdException(FolderBaseError.EC_DIVIDER_HAS_CHILDREN, 
                                   FolderBaseError.EM_DIVIDER_HAS_CHILDREN);         
      }
      
   }
   
   /**
    * Elimina un clasificador (lo marca como eliminado)
    *  
    * @param divId identificador del clasificador
    * @throws Exception arroja IeciTdException si el clasificador
    * tiene hijos o si no se encuentra
    */
   public void removeDivider(int divId)
               throws Exception
   { 

      checkDividerNoHasChildren(divId);
      
      m_dividers.removeNode(divId);

   } 
   
   /**
    * Elimina un documento (lo marca como eliminado)
    * 
    * @param docId identificador del documento
    * @throws Exception arroja IeciTdException si el clasificador
    * tiene hijos o si no se encuentra
    */
   public void removeDocument(int docId)
               throws Exception
   {    
      m_documents.removeNode(docId);   
   }  

   /**
    * Renombra un documento. 
    * 
    * @param docId identificador del documento
    * @param name nuevo nombre
    * @throws Exception arroja IeciTdException si el nuevo nombre
    * no es válido
    */
   public void renameDocument(int docId, String name)
               throws Exception
   {   
      FolderTokenDocument doc = m_documents.findById(docId);
  
      checkValidDocumentName(doc.getParentId(), name);

      doc.rename(name);   
   }   
     
        
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenDocTree[");
      buffer.append("m_rootName = ").append(m_rootName);
      buffer.append(", m_dividers = ").append(m_dividers);
      buffer.append(", m_documents = ").append(m_documents);
      buffer.append(", m_fdrLinks = ").append(m_fdrLinks);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
