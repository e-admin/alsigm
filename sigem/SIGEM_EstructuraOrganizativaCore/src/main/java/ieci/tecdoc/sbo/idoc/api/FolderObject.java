package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.base.FolderBaseDefs;
import ieci.tecdoc.sbo.idoc.folder.base.FolderToken;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLink;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase modeliza una carpeta Invesdoc. 
 */

public final class FolderObject implements Serializable
{

   /**
    * Contiene toda la información de una carpeta
    */
   protected FolderToken m_fdr;

   /**
    * Contructor
    * 
    * @param fdr referencia a un objeto de tipo FolderToken que contiene toda la
    *           información de una carpeta invesdoc
    */
   protected FolderObject(FolderToken fdr)
   {
      m_fdr = fdr;
   }

   /**
    * Obtiene una referencia a un objeto FolderToken que contiene toda la
    * información de una carpeta invesdoc
    * 
    * @return referencia a un objeto FolderToken
    */
   public FolderToken getFolderToken()
   {
      return m_fdr;
   }

   /**
    * Obtiene el identificador de la carpeta
    * 
    * @return identificador de la carpeta
    */
   public int getId()
   {
      return m_fdr.getId();
   }

   /**
    * Determina si una carpeta es nueva o no
    * 
    * @return true si la carpeta es nueva; false si la carpeta no es nueva
    */
   public boolean isNew()
   {
      return m_fdr.isNew();
   }

   /**
    * Obtiene el valor asociado a un campo de la carpeta
    * @param fldId identificador de la carpeta
    * @return valor asociado al campo de la carpeta
    * @throws Exception si el campo es multivalor.
    */
   // Campos
   public Object getFieldValue(int fldId) throws Exception
   {
      return m_fdr.getFieldValue(fldId);
   }

   /**
    * Obtiene la colección de valores asociados a un campo de la carpeta 
    * @param fldId identificador de la carpeta
    * @return valores asociados al campo de la carpeta
    * @throws Exception si el campo es multivalor.
    */
   public ArrayList getFieldValues(int fldId) throws Exception
   {
      return m_fdr.getFieldValues(fldId);
   }

   /**
    * Establece un nuevo valor a un campo de la carpeta
    * @param fldId identificador de la carpeta
    * @param val valor asociado al campo
    * @throws Exception si el campo es multivalor
    */
   public void setFieldValue(int fldId, Object val) throws Exception
   {
      m_fdr.setFieldValue(fldId, val);
   }

   /**
    * Elimina los valores establecidos a un campo de tipo multivalor
    * @param fldId identificador de la carpeta
    * @throws Exception si el campo no es multivalor 
    */
   public void removeFieldValues(int fldId) throws Exception
   {
      m_fdr.removeFieldValues(fldId);
   }

   /**
    * Añade un nuevo valor a la colección de valores de un campo multivalor
    * @param fldId identificador del campo
    * @param val nuevo valor
    * @throws Exception si el campo no es multivalor
    */
   public void addFieldValue(int fldId, Object val) throws Exception
   {
      m_fdr.addFieldValue(fldId, val);
   }

   //DocumentTree   
   
   /**
    * Obtiene el nombre de la etiqueta asignada al elemento raíz del árbol de 
    * clasificadores y documentos de la carpeta
    * @return nombre de la etiqueta del elemento raíz del árbol de
    * clasificadores y documentos de la carpeta
    */

   public String getDocumentTreeRootName()
   {
      return m_fdr.getDocumentTree().getRootName();
   }

   /**
    * Obtiene un clasificador a partir de un identificador
    * @param divId identificador del clasificador
    * @return  referencia a un objeto de tipo FolderDividerObject que contiene
    * la información de un clasificador
    * @throws Exception si no se encuentra el clasificador
    * @see FolderDividerObject
    */
   public FolderDividerObject getDivider(int divId) throws Exception
   {
      FolderDividerObject div = null;
      FolderTokenDivider divToken = m_fdr.getDocumentTree().getDivider(divId);

      div = new FolderDividerObject(divToken);

      return div;
   }

   /**
    * Obtiene el identificador de un clasificador a partir de su nombre
    * @param name nombre del clasificador
    * @return identificador del clasificador
    * @throws Exception
    */
   public int getDividerId(String name) throws Exception
   {
      return m_fdr.getDocumentTree().getDividerId(name);
   }

   /**
    * Obtiene todos los clasificadores de una carpeta
    * @return referencia a un objeto de tipo FolderDividerObjects que 
    * es una colección de clasificadores
    * @throws Exception
    * @see FolderDividerObjects
    */
   public FolderDividerObjects getAllDividers() throws Exception
   {
      FolderTokenDividers divsToken = m_fdr.getDocumentTree().getAllDividers(
               false);
      FolderDividerObjects divs = new FolderDividerObjects(divsToken);

      return divs;
   }

   /**
    * Obtiene los clasificadores hijos de la raíz del arbol de documentos y clasificadores
    * @return referencia a un objeto de tipo FolderDividerObjects que 
    * es una colección de clasificadores
    * @throws Exception
    * @see FolderDividerObjects 
    */
   public FolderDividerObjects getRootDividerChildren() throws Exception
   {
      FolderTokenDividers divsToken = m_fdr.getDocumentTree().getRootDividerChildren(
               false);
      FolderDividerObjects divs = new FolderDividerObjects(divsToken);

      return divs;
   }

   /**
    * Obtiene los clasificadores hijos de un determinado clasificador
    * @param parentDivId identificador del clasificador
    * @return clasificadores hijos
    * @throws Exception
    */
   public FolderDividerObjects getDividerChildren(int parentDivId)
            throws Exception
   {
      FolderTokenDividers divsToken = m_fdr.getDocumentTree().getDividerChildren(
               parentDivId, false);
      FolderDividerObjects divs = new FolderDividerObjects(divsToken);

      return divs;
   }

   /**
    * Añade a la raíz del árbol de documentos y clasificadores un nuevo clasificador
    * @param name nombre del nuevo clasificador
    * @return identificador del nuevo clasificador
    * @throws Exception si ya existe un clasificador con ese nombre o si el nombre del clasificador
    * excede los 32 caracteres
    */
   public int addRootDivider(String name) throws Exception
   {
      return m_fdr.getDocumentTree().addRootDivider(name);
   }

   /**
    * Añade un nuevo clasificador hijo a un determinado clasificador
    * @param name nombre del nuevo clasificador
    * @param parentDivId identificador del clasificador padre
    * @return identificador del nuevo clasificador
    * @throws Exception si el nombre del nuevo clasificador excede los 32 caracteres o si no existe
    * el clasificador padre
    */
   public int addDivider(String name, int parentDivId) throws Exception
   {
      return m_fdr.getDocumentTree().addDivider(name, parentDivId);
   }

   /**
    * Elimina un clasificador
    * @param divId identificador del clasificador
    * @throws Exception si el clasificador que se quiere eliminar tiene hijos o si no se encuentra
    * el clasificador padre
    */
   public void removeDivider(int divId) throws Exception
   {
      m_fdr.getDocumentTree().removeDivider(divId);
   }

   /**
    * Modifica el nombre de un clasificador
    * @param divId identificador del clasificador
    * @param name nuevo nombre del clasificador
    * @throws Exception si no se encuentra el clasificador o si el nombre del clasificador excede
    * 32 caracteres
    */
   public void renameDivider(int divId, String name) throws Exception
   {
      m_fdr.getDocumentTree().renameDivider(divId, name);
   }

   /**
    * Obtiene todos los documentos de una carpeta
    * @return referencia a un objeto de tipo FolderDocumentObjects que es una colección
    * de documentos
    * @throws Exception
    * @see FolderDocumentObjects
    */
   public FolderDocumentObjects getAllDocuments() throws Exception
   {
      FolderTokenDocuments docsToken = m_fdr.getDocumentTree().getAllDocuments(
               false);
      FolderDocumentObjects docs = new FolderDocumentObjects(docsToken);

      return docs;
   }

   /**
    * Obtiene los documentos hijos de la raíz del árbol de documentos y clasificadores
    * @return documentos hijos de la raíz del árbol de documentos y clasificadores
    * @throws Exception
    * @see FolderDocumentObjects
    */
   public FolderDocumentObjects getRootDocumentChildren() throws Exception
   {
      FolderTokenDocuments docsToken = m_fdr.getDocumentTree().getRootDocumentChildren(
               false);
      FolderDocumentObjects docs = new FolderDocumentObjects(docsToken);

      return docs;
   }

   /**
    * Obtiene los documentos hijos de un determinado clasificador 
    * @param parentDivId identificador del clasificador
    * @return referencia a un objeto de tipo FolderDocumentObjects que contiene una colección
    * de documentos. Si el clasificador no existe se obtiene una colección vacía
    * @throws Exception
    */
   public FolderDocumentObjects getDocumentChildren(int parentDivId)
            throws Exception
   {
      FolderTokenDocuments docsToken = m_fdr.getDocumentTree().getDocumentChildren(
               parentDivId, false);
      FolderDocumentObjects docs = new FolderDocumentObjects(docsToken);

      return docs;
   }

   /**
    * Obtiene un documento con un determinado identificador
    * @param docId identificador del documento
    * @return una referencia a un objeto de tipo FolderDocumentObject que contiene
    * información sobre el documento
    * @throws Exception si no se encuentra el documento
    */
   public FolderDocumentObject getDocument(int docId) throws Exception
   {
      FolderDocumentObject doc = null;
      FolderTokenDocument docToken = m_fdr.getDocumentTree().getDocument(docId);

      doc = new FolderDocumentObject(docToken);

      return doc;
   }

   /**
    * Obtiene el identificador del documento a partir del nombre del documento
    * @param name nombre del documento
    * @return identificador del documento; -1 si no existe el documento
    * @throws Exception
    */
   public int getDocumentId(String name) throws Exception
   {
      return m_fdr.getDocumentTree().getDocumentId(name);
   }

   /**
    * Añade un documento a la raíz del árbol de documentos y clasificadores
    * @param name nombre del documento
    * @param fileExt extensión del documento
    * @param pathDocumentFile path del documento
    * @throws Exception si nombre del documento ya existe o tiene más de 32 caracteres
    */
   public void addRootDocument(String name, String fileExt,
            String pathDocumentFile) throws Exception
   {
      m_fdr.getDocumentTree().addRootDocument(name, fileExt, pathDocumentFile);
   }
   
   /**
    * Añade un documento a la raíz del árbol de documentos y clasificadores
    * @param name nombre del documento
    * @param fileExt extensión del documento
    * @param fileInputStream inputstream del fichero
    * @throws Exception si nombre del documento ya existe o tiene más de 32 caracteres
    */
   public void addRootDocument(String name, String fileExt,
            InputStream fileInputStream) throws Exception
   {
      //m_fdr.getDocumentTree().addRootDocument(name, fileExt, fileInputStream);
     addDocument(name, FolderBaseDefs.CLF_ROOT_ID, fileExt, fileInputStream);
   }

   /**
    * Añade un nuevo documento a un clasificador
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador
    * @param fileExt extensión del documento
    * @param pathDocumentFile path del documento
    * @throws Exception si el nombre del documento ya existe o tiene más de 32 caractares,
    * o si el clasificador no existe
    */
   public void addDocument(String name, int parentDivId, String fileExt,
            String pathDocumentFile) throws Exception
   {
      addDocument(name, parentDivId, fileExt, pathDocumentFile, null);
   }
   
   /**
    * Añade un nuevo documento a un clasificador
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador
    * @param fileExt extensión del documento
    * @param fileInputStream inputstream del fichero
    * @throws Exception si el nombre del documento ya existe o tiene más de 32 caractares,
    * o si el clasificador no existe
    */
   public void addDocument(String name, int parentDivId, String fileExt,
         InputStream fileInputStream) throws Exception
   {
      addDocument(name, parentDivId, fileExt, fileInputStream, null);
   }
   
   /**
    * Añade un nuevo documento a un clasificador
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador
    * @param fileExt extensión del documento
    * @param pathDocumentFile path del documento
    * @param pathDocAnnFile path del fichero de anotaciones
    * @throws Exception si el nombre del documento ya existe o tiene más de 32 caractares,
    * o si el clasificador no existe
    */
   public void addDocument(String name, int parentDivId, String fileExt,
                           String pathDocumentFile, String pathDocAnnFile)
               throws Exception
   {
      m_fdr.getDocumentTree().addDocument(name, parentDivId, fileExt,
                                          pathDocumentFile, pathDocAnnFile);
   }

   /**
    * Añade un nuevo documento a un clasificador
    * @param name nombre del documento
    * @param parentDivId identificador del clasificador
    * @param fileExt extensión del documento
    * @param fileInputStream inputstream del fichero
    * @param annFileInputStream inputstream del fichero de anotaciones
    * @throws Exception si el nombre del documento ya existe o tiene más de 32 caractares,
    * o si el clasificador no existe
    */
   public void addDocument(String name, int parentDivId, String fileExt,
         InputStream fileInputStream, Reader annFileReader)
               throws Exception
   {
      m_fdr.getDocumentTree().addDocument(name, parentDivId, fileExt,
            fileInputStream, annFileReader);
   }
   
   /**
    * Elimina un documento
    * @param docId identificador del documento 
    * @throws Exception si no se encuentra el documento
    */
   public void removeDocument(int docId) throws Exception
   {
      m_fdr.getDocumentTree().removeDocument(docId);
   }

   /**
    * Modifica el nombre de un documento
    * @param docId identificador del documento
    * @param name nuevo nombre del documento
    * @throws Exception si no se encuentra el documento o si el nombre del documento ya existe 
    * o si excede los 32 caracteres
    */
   public void renameDocument(int docId, String name) throws Exception
   {
      m_fdr.getDocumentTree().renameDocument(docId, name);
   }

   /**
    * Obtiene todos los enlaces a otras carpetas
    * @return referencia a un objeto de tipo FolderFdrLinkObjects que es una colección
    * de referencias a carpetas
    * @throws Exception
    * @see FolderFdrLinkObjects
    */
   public FolderFdrLinkObjects getAllFolderLinks() throws Exception
   {
      FolderTokenFdrLinks fdrLinksToken = m_fdr.getDocumentTree().getAllFolderLinks(
               false);
      FolderFdrLinkObjects fdrLinks = new FolderFdrLinkObjects(fdrLinksToken);

      return fdrLinks;
   }

   /**
    * Obtiene todos los enlaces a carpetas hijos de la raíz del árbol
    * @return referencia a un objeto de tipo FolderFdrLinkObjects que es una colección
    * de referencias a carpetas
    * @throws Exception
    * @see FolderFdrLinkObjects 
    */
   public FolderFdrLinkObjects getRootFolderLinkChildren() throws Exception
   {
      FolderTokenFdrLinks fdrLinksToken = m_fdr.getDocumentTree().getRootFolderLinkChildren(
               false);
      FolderFdrLinkObjects fdrLinks = new FolderFdrLinkObjects(fdrLinksToken);

      return fdrLinks;
   }

   /**
    * Obtiene los enlaces a carpetas hijos de un determinado clasificador
    * @param parentDivId identificador del clasificador
    * @return referencia a un objeto de tipo FolderFdrLinkObjects que es una colección
    * de referencias a carpetas
    * @throws Exception
    * @see FolderFdrLinkObjects 
    */
   public FolderFdrLinkObjects getFolderLinkChildren(int parentDivId)
            throws Exception
   {
      FolderTokenFdrLinks fdrLinksToken = m_fdr.getDocumentTree().getFolderLinkChildren(
               parentDivId, false);
      FolderFdrLinkObjects fdrLinks = new FolderFdrLinkObjects(fdrLinksToken);

      return fdrLinks;
   }

   /**
    * Obtiene un enlace a una carpeta dentro de la carpeta
    * @param fdrLinkId identificador del enlace
    * @return referencia a un objeto de tipo FolderFdrLinkObject que contiene la información
    * de un enlace
    * @throws Exception si no encuentra el enlace
    */
   public FolderFdrLinkObject getFolderLink(int fdrLinkId) throws Exception
   {
      FolderFdrLinkObject fdrLink = null;
      FolderTokenFdrLink fdrLinkToken = m_fdr.getDocumentTree().getFolderLink(
               fdrLinkId);

      fdrLink = new FolderFdrLinkObject(fdrLinkToken);

      return fdrLink;
   }

} // class
