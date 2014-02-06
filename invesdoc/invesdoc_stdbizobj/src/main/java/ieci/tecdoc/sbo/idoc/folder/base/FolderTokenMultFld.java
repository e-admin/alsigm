
package ieci.tecdoc.sbo.idoc.folder.base;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Representa un campo multivalor de una carpeta
 *  
 * @author 
 */
public final class FolderTokenMultFld  implements Serializable
{
   /**
    * Identificador del campo
    */
   private int                         m_id;
   
   /**
    * Nombre del campo
    */
   private String                      m_name;
   
   /**
    * Colección de valores asociada al campo
    */
   private ArrayList                   m_vals;
   
   /**
    * Colección de flags que definen el estado de los valores asociados al campo
    * <li> FolderEditFlag.NONE
    * <li> FolderEditFlag.NEW
    * <li> FolderEditFlag.UPDATE
    * @see IeciTdLongIntegerArrayList
    */
   private IeciTdLongIntegerArrayList  m_editFlags;   
      
   /**
    * Constructor
    * @param id identificador del campo
    * @param name nombre del campo
    * @param vals valores del campo
    */
   public FolderTokenMultFld(int id, String name, ArrayList vals)
   {
      m_id          = id;
      m_name        = name;
      
      initVals(vals);     
   }
   
   /**
    * Inicializa los valores de la colección
    * @param vals
    */
   private void initVals(ArrayList vals)
   {
      int i, count;
      
      m_vals      = new ArrayList();
      m_editFlags = new IeciTdLongIntegerArrayList();
      
      count = vals.size();
      
      for(i = 0; i < count; i++)
      {
         m_vals.add(vals.get(i));
         m_editFlags.add(FolderEditFlag.NONE);
      }
            
   }
   
   /**
    * Devuelve el identificador del campo multivalor
    * @return identificador del campo multivalor
    */
   public int getId()
   {
      return m_id;
   }

   /**
    * Devuelve el nombre del campo multivalor
    * @return nombre del campo multivalor
    */   
   public String getName()
   {
      return m_name;
   }
   
   /**
    * Devuelve los valores asociados al campo multivalor
    * @return valores asociados al campo multivalor
    */
   public ArrayList getVals()
   {
      return m_vals;
   }

   /**
    * Devuelve el número de valores asociados al campo multivalor
    * @return
    */
   public int getNumVals()
   {
      return m_vals.size();
   }
   
   /**
    * Añade un valor a la colección de valores asociados al campo multivalor
    * @param val nuevo valor
    * @throws Exception
    */
   public void addVal(Object val) throws Exception
   {
      if (val == null)
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_VALUE, 
                                   FolderBaseError.EM_INVALID_VALUE);
      }
      
      m_vals.add(val);
      m_editFlags.add(FolderEditFlag.NEW);
   }
   
   /**
    * Elimina todos los valores asociados al campo multivalor
    * @throws Exception
    */
   protected void removeValues() throws Exception
   {
      int i, j;
      int flag, count;
      
      count = m_vals.size();
      j = 0;
      
      for(i = 0; i < count; i ++)
      {
         flag = m_editFlags.get(j);

         if ( (flag == FolderEditFlag.NONE) ||
              (flag == FolderEditFlag.UPDATE) )
         {
            flag = FolderEditFlag.REMOVE;
            m_editFlags.set(j,flag);
            j = j + 1;
         }
         else if (flag == FolderEditFlag.NEW)
         {
            m_editFlags.remove(j);
            m_vals.remove(j);
         }

      }
      
   }
   
   /**
    * Indica si el valor i-ésimo asociado al campo multivalor tiene estado NEW
    * @param idx índice
    * @return true si es nuevo; false en caso contrario
    * @throws Exception si el índice esta fuera de rango
    */
   public boolean isValNew(int idx) throws Exception
   {
      boolean is = false;
      int     editFlag;
      
      if ( (idx + 1) > m_vals.size() || (idx < 0) )
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_PARAM, 
                                   FolderBaseError.EM_INVALID_PARAM);
      
      }
      
      editFlag = m_editFlags.get(idx);
      
      if (editFlag == FolderEditFlag.NEW)
         is = true;
      else
         is = false; 
      
      return is;
   }
   
   /**
    * Indica si el valor i-ésimo asociado al campo multivalor tiene estado REMOVE
    * @param idx índice
    * @return true si ha sido eliminado; false en caso contrario
    * @throws Exception si el índice esta fuera de rango
    */
   public boolean isValRemove(int idx) throws Exception
   {
      boolean is = false;
      int     editFlag;
      
      if ( (idx + 1) > m_vals.size() || (idx < 0) )
      {
         throw new IeciTdException(FolderBaseError.EC_INVALID_PARAM, 
                                   FolderBaseError.EM_INVALID_PARAM);
      
      }
      
      editFlag = m_editFlags.get(idx);
      
      if (editFlag == FolderEditFlag.REMOVE)
         is = true;
      else
         is = false; 
      
      return is;
   }
   
   /**
    * Indica si alguno de los valores asociados al campo multivalor tiene estado distinto a NONE
    * @param idx índice
    * @return true si existe algun valor; false en caso contrario
    * @throws Exception si el índice esta fuera de rango
    */   
   public boolean isModified() throws Exception
   {
      boolean is = false;
      int     i, flag, count;
      
      count = m_vals.size();     
      
      for(i = 0; i < count; i ++)
      {
         flag = m_editFlags.get(i);
         
         if ( flag != FolderEditFlag.NONE )
         {
            is = true;
            break;
         }
         
      }
      
      return is;
   }
   
   /**
    * Indica si ha existe algun valor del campo con estado NEW
    * @return true si existe algún valor nuevo; false en caso contrario
    * @throws Exception
    */
   public boolean existsAnyValNoNew() throws Exception
   {
      boolean exists = false;
      int     i, flag, count;
      
      count = m_vals.size();     
      
      for(i = 0; i < count; i ++)
      {
         flag = m_editFlags.get(i);
         
         if ( flag != FolderEditFlag.NEW )
         {
            exists = true;
            break;
         }
         
      }
      
      return exists;
   }
   
   /**
    * Establece todos los valores del campo multivalor con estado NONE
    *
    */
   protected void clearEditInfo()
   {
      int i, j;
      int flag, count;
      
      count = m_vals.size();
      j = 0;
      
      for(i = 0; i < count; i ++)
      {
         flag = m_editFlags.get(j);
         
         if ( (flag == FolderEditFlag.UPDATE) || 
              (flag == FolderEditFlag.NEW) )
         {
            flag = FolderEditFlag.NONE;
            m_editFlags.set(j,flag);
            j = j + 1;
         }
         else if (flag == FolderEditFlag.REMOVE)
         {
            m_editFlags.remove(j);
            m_vals.remove(j);            
         }
         else
            j= j + 1;
      }
   }
   
   
  
   /**
    * Representación textual del objeto, es decir, de un campo multivalor de una carpeta
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenMultFld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      
      for(int i = 0; i < m_vals.size(); i++)
      {

          buffer.append(" [m_vals_").append(i+1);
          buffer.append(" = ").append((m_vals.get(i)).toString());
          buffer.append("] ");

      }
      
      buffer.append(", m_editFlags = ").append(m_editFlags);

		for(int i = 0; i < m_editFlags.count() ; i++)
		{

			 buffer.append(" [m_editFlags _").append(i+1);
			 buffer.append(" = ").append((m_editFlags.get(i)));
			 buffer.append("] ");

		}

      buffer.append("]");
      
      return buffer.toString();
   }
} // class
