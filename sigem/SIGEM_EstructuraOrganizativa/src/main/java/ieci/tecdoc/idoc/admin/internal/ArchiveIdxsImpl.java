
package ieci.tecdoc.idoc.admin.internal;

/**
 * Proporciona toda la funcionalidad necesaria para manejar índices. 
 */

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdx;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;

import java.util.ArrayList;

public class ArchiveIdxsImpl implements ArchiveIdxs
{
   
   public ArchiveIdxsImpl()
   {
      m_al = new ArrayList();      
   }
   
   /**
    * Inicializa la estructura de índices
    */
   public void clear()
   {
      m_al.clear();
   }
   
   /**
    * Obtiene el número de índices en la estructura. 
    * 
    * @return Número de índices
    */
   public int count()
   {
      return m_al.size();
   }
   
   /**
    * Añade la definición de un índice.
    * 
    * @param item Definición del índice
    */
   public void addIdx(ArchiveIdx item)
   {      
      ArchiveIdxImpl idx = null;
      ArrayList      idsFld = new ArrayList();
      ArrayList      fldsId = item.getFldsId();
      
      for (int i = 0; i < fldsId.size(); i++)
      {
         Integer id = (Integer)fldsId.get(i);
         idsFld.add(new Integer(id.intValue()));
      }
            
      idx = new ArchiveIdxImpl(item.getId(),item.getName(),item.isUnique(),idsFld);
      
      m_al.add(idx);      
   }
   
   /**
    * Añade la definición de un índice.
    * 
    * @param name Nombre del índice
    * @param isUnique true/false
    * @param idsFld Lista con los identificadores de los campos que
    * componen el índice
    */
   public void add(String name, boolean isUnique,
                   ArrayList idsFld) throws Exception
   {
      
      ArchiveIdxImpl idx;
      int            idxId;
      
      
      if (name.length() == 0)
         AdminException.throwException(ArchiveErrorCodes.EC_IDX_NAME_NO_VALID);
      
      if (!isValidName(name))      
         AdminException.throwException(ArchiveErrorCodes.EC_IDX_EXISTS);
      
      
      idxId  = getNextIdxId();
      idx = new ArchiveIdxImpl(idxId, name, isUnique,idsFld); 
                                       
      m_al.add(idx);
      
   }
   
   /**
    * Obtiene la definición del índice. 
    * 
    * @param index Índice de la estructura
    * @return Errores
    */
   public ArchiveIdx get(int index)
   {
      return (ArchiveIdx) m_al.get(index);
   } 
   
   /**
    * Obtiene el identificador del índice por su nombre.
    * 
    * @param name Nombre del índice
    * @return Identificador del índice
    * @throws Exception Errores
    */
   public int getIdxIdByName(String name) throws Exception
   {
      int            count,i;
      boolean        find = false;
      ArchiveIdxImpl idxDef = null;
      int           idxId = Defs.NULL_ID;

      count = m_al.size();
      
      for(i=0; i<count; i++)
      {
         idxDef = (ArchiveIdxImpl)m_al.get(i);
         if (idxDef.getName().equals(name))
         {
            find = true;
            idxId = idxDef.getId();
            break;
         }           
      }
      
      if (!find)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_IDX_NO_EXISTS); 

      }
      
      return idxId; 
   }
   
   /**
    * Obtiene la información de los índices en formato XML.
    *
    * @return La información mencionada.
    */
   public String toXML() 
   {
      return toXML(true);
   }
   
   /**
    * Obtiene la información e los índices en formato XML.
    * @param header true / false
    * @return La información mencionada.
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Indexs";
      ArchiveIdxImpl idx;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         idx = (ArchiveIdxImpl)get(i);
         bdr.addFragment(idx.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */

	public String toString()
	{
      return toXML(false);
   }
   
   /**
    * Obtiene si el nombre del índice es válido ó no
    * 
    * @param name - nombre del índice
    * @return true / false
    */
   private boolean isValidName(String name)
   {
      boolean valid = true;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveIdxImpl idxDef = (ArchiveIdxImpl)m_al.get(i);
         if (idxDef.getName().equals(name))
         {
            valid = false;
         }         
      }
      
      return valid;
   }
   
   /**
    * Obtiene el identificador del índice
    * @return El identificador
    */
   private int getNextIdxId()
   {
      int            nextId;
      int            count,i;
      ArchiveIdxImpl idxDef = null;

      nextId = 0;
      count = m_al.size();

      for(i=0; i<count; i++)
      {
         idxDef = (ArchiveIdxImpl)m_al.get(i);

         if (nextId < idxDef.getId() )
            nextId = idxDef.getId();
      }

      nextId = nextId + 1;

      return(nextId);
   }
   
   private ArrayList m_al;
}

