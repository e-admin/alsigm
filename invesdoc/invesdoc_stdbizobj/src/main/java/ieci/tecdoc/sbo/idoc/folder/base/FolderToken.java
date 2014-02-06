
package ieci.tecdoc.sbo.idoc.folder.base;

import ieci.tecdoc.sbo.acs.base.AcsAccessType;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.sbo.util.types.SboType;

/**
 * Encapsula la información referente a una carpeta de un archivador,
 * tanto la información general de la carpeta como la de los campos y 
 * el árbol de documentos de dicha carpeta.
 * 
 * @author
 * @see FolderTokenFlds 
 * @see FolderTokenDocTree 
 */
public final class FolderToken implements Serializable
{   
   /** identificador de carpeta */
   private int                m_fdrId;
   
   /** tipo de acceso a la carpeta (publico o protegido) */
   private int                m_acsType;
   /**  */
   private int                m_acsId;
   /** flag que indica si la carpeta es nueva */
   private boolean            m_isNew;
   /** lista con la información referente a los campos de la carpeta */
   private FolderTokenFlds    m_flds;
   /** árbol de documentos de la carpeta */
   private FolderTokenDocTree m_docTree;  
   
   /**
    * Constructor
    *
    */
   public FolderToken()
   {
      m_fdrId      = SboType.NULL_ID;  
      m_acsType    = AcsAccessType.ACS_PUBLIC; 
      m_acsId      = SboType.NULL_ID;  
      m_isNew      = false;     
      m_flds       = null;
      m_docTree    = null;
   }
   
   /**
    * Devuelve la lista de campos de la carpeta
    * @return lista de campos de la carpeta
    * @see FolderTokenFlds
    */
   public FolderTokenFlds getFlds()
   {
      return m_flds;
   }
         
   /**
    * Establece la lista de campos de la carpeta
    * 
    * @param flds lista de campos de la carpeta
    * @see FolderTokenFlds
    */
   public void setFlds(FolderTokenFlds flds)
   {      
      m_flds = flds;       
   } 
      
   /**
    * Establece el árbol de documentos de la carpeta
    * 
    * @param docTree árbol de documentos de la carpeta
    * @see FolderTokenDocTree
    */
   public void setDocumentTree(FolderTokenDocTree docTree)
   {      
      m_docTree = docTree;       
   } 
   
   /**
    * Devuelve el árbol de documentos de la carpeta
    * 
    * @return árbol de documentos de la carpeta
    * @see FolderTokenDocTree
    */
   public FolderTokenDocTree getDocumentTree()
   {
      return m_docTree;
   }
      
   /**
    * Establece la información de general de la carpeta
    * 
    * @param fdrId identificador de carpeta
    * @param acsType acceso a la carpeta (publico o protegido)
    * @param acsId
    * @param isNew flag que indica si la carpeta es nueva o no
    * @see AcsAccessType
    */
   public void setFdrInfo(int fdrId, int acsType, int acsId,
                          boolean isNew)
   {      
      m_fdrId      = fdrId;  
      m_acsType    = acsType; 
      m_acsId      = acsId;  
      m_isNew      = isNew;           
   } 
   
   /**
    * Devuelve el id de la carpeta
    * 
    * @return el id de la carpeta
    */
   public int getId()
   {
      return m_fdrId;
   }
   
   /** 
    * Devuelve <tt>true</tt> si la carpeta es nueva
    * @return <tt>true</tt> si la carpeta es nueva
    */
   public boolean isNew()
   {
      return m_isNew;
   }   
   
   /**
    * Devuelve el tipo de acceso a la carpeta
    * @return el tipo de acceso a la carpeta
    * @see AcsAccessType
    */
   public int getAccessType()
   { 
      return m_acsType;
   }
   
   /**
    * 
    * @return
    */
   public int getAccessId()
   { 
      return m_acsId;
   }
   
   /**
    * Devuelve el documento de la carpeta con el id especificado
    * 
    * @param docId identificador del documento
    * @return documento especificado
    * @throws Exception arroja IeciTdException si no se encuentra el
    * documento
    * @see FolderTokenDocument
    */
   public FolderTokenDocument getDocument(int docId)
                              throws Exception
   { 
      return m_docTree.getDocument(docId);
   }
   
   /**
    * Devuelve el valor del campo indicado de la carpeta. Este método 
    * no vale para campos multivalor.
    * 
    * @param fldId identificador del campo
    * @return valor del campo. <tt>null</tt> si no encuentra el campo 
    * @throws Exception arroja <tt>IeciTdException</tt> si el campo 
    * es multivalor
    */
   public Object getFieldValue(int fldId)
                 throws Exception
   {  
      return m_flds.getFieldValue(fldId);
   }
   
   /**
    * Devuelve una lista con los valores del campo especificado
    * por id. Este método es solo para campos multivalor.
    * 
    * @param fldId identificador del campo
    * @return lista con los valores del campo 
    * @throws Exception arroja IeciTdException si el campo no es
    * multivalor
    */
   public ArrayList getFieldValues(int fldId)
                    throws Exception
   { 
      return m_flds.getFieldValues(fldId);
   }
   
   /**
    * Establece el valor para un campo de la carpeta. No vale para
    * campos que sean multivalor
    * 
    * @param fldId identificador del campo
    * @param val valor del campo
    * @throws Exception arroja IeciTdException si el campo es 
    * multivalor 
    */
   public void setFieldValue(int fldId, Object val) 
               throws Exception
   {  
      m_flds.setFieldValue(fldId, val);
   }
   
   /**
    * Elimina los valores para el campo especificado. Solo vale
    * para campos multivalor
    * @param fldId identificador del campo
    * @throws Exception arroja IeciTdException si el campo no es 
    * multivalor 
    */
   public void removeFieldValues(int fldId) 
               throws Exception
   { 
      m_flds.removeFieldValues(fldId);
   }
   
   /**
    * Agrega un nuevo valor a un campo multivalor
    * @param fldId identificador del campo
    * @param val nuevo valor para el campo
    * @throws Exception arroja IeciTdException si el campo no es 
    * multivalor 
    */
   public void addFieldValue(int fldId, Object val) 
               throws Exception
   { 
      m_flds.addFieldValue(fldId, val);
   }
        
   /**
    * @see Object#toString()
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
    
      buffer.append("FolderToken[");
      buffer.append("m_fdrId = ").append(m_fdrId);
      buffer.append(", m_acsType = ").append(m_acsType);
      buffer.append(", m_acsId = ").append(m_acsId);
      buffer.append(", m_isNew = ").append(m_isNew);
      buffer.append(", m_flds = ").append(m_flds);
      buffer.append(", m_docTree = ").append(m_docTree);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
