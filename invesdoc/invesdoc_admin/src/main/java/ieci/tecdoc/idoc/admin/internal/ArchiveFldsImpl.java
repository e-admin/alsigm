package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;

import java.util.ArrayList;

public class ArchiveFldsImpl implements ArchiveFlds
{
   
   public ArchiveFldsImpl()
   {
      m_al = new ArrayList();      
   }
   
   /**
    * Borra toda la definición de campos.
    *
    */
   public void clear()
   {
      m_al.clear();
   }
   
   /**
    * Se obtiene el número de campos.
    * @return Número de campos
    */
   public int count()
   {
      return m_al.size();     
   }
   
   /**
    * Añade una definición de campo. 
    * 
    * @param fld Definición del campo.
    */
   public void addFld(ArchiveFld fld) throws Exception
   {
      
      ArchiveFldImpl fldDef = null;
      String            colName;
      
      colName = "Fld" + Integer.toString(fld.getId());
      
      if (fld.getName().length() == 0)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_NAME_NO_VALID);
      }
      if (!isValidName(fld.getName()))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_EXISTS);
      }
   	
      fldDef = new ArchiveFldImpl(fld.getId(),fld.getName(),fld.getType(),fld.getLen(),
                                 fld.isNullable(),colName,fld.isDoc(),fld.isMult(),
                                 fld.getRemarks()); 
   	                           
      m_al.add(fldDef);
      
   }
   
   
   /**
    * Añade una definición de campo
    * (el cálculo del identificador es interno, nunca tiene que se
    * establecido).
    * 
    * @param name Nombre del campo
    * @param type Tipo del campo
    * @param len  Longitud del campo en caso de texto y texto largo en
    * otro caso es = 0
    * @param isNullable true /false (posibilidad de tener valores nulos)
    * @param isDoc true / false (documental)
    * @param isMult true /false (multivalor)
    * @param remarks Descripción
    * @throws Exception Errores
    */
   public void add(String name, int type, int len, boolean isNullable,
         			boolean isDoc, boolean isMult, String remarks) throws Exception
	{
      int            fldId;
      String         colName;
      ArchiveFldImpl fld;
      
      if (name.length() == 0)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_NAME_NO_VALID);
      }
      if (!isValidName(name))
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_EXISTS);
      }
      
      fldId = getNextFldId();

      colName = "Fld" + Integer.toString(fldId);
	
      fld = new ArchiveFldImpl(fldId, name, type, len, isNullable, 
	                           colName,isDoc, isMult, remarks);     
     
	
      m_al.add(fld);
	
	}
   
   /**
    * Obtiene el identificador del campo a partir del nombre del campo.
    * 
    * @param name Nombre del campo
    * @return Identificador del campo
    * @throws Exception  Errores (si no existe el campo)
    */
   public int getFldIdByName(String name) throws Exception
   {
      int     fldId = 0;
      boolean find = false;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.getName().equals(name))
         {
            find = true;
            fldId = fldDef.getId();
            break;
         }           
      }
      
      if (!find)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_NO_EXISTS); 

      }
      
      return fldId;
      
   }
   
      
   /**
    * Obtiene la definición de un campo a partir del identificador del campo.
    * 
    * @param fldId Identificador del campo
    * @return  Definición del campo
    * @throws Exception Errores (si el campo no exite)
    */
   public ArchiveFld getFldDefById(int fldId) throws Exception
   {
      ArchiveFldImpl fldDef = null;
      boolean find = false;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.getId() == fldId)
         {
            find = true;            
            break;
         }           
      }
      
      if (!find)
      {
         AdminException.throwException(ArchiveErrorCodes.EC_FLD_NO_EXISTS); 
      }
      
      return fldDef;
      
   }
   
   /**
    * Obtiene la definición del campo a partir del índice en la colección.
    * @param index Indice.
    * @return Definición del campo.
    */
   public ArchiveFld get(int index)
   {
      return (ArchiveFld)m_al.get(index);
   }
   
   
   /**
    * Obtiene si todos las descripciones de los campos son válidas.
    * 
    * @return true /false
    */
   public boolean areValidRemarks()
   {
      boolean exists = false;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         String remarks;
         
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         remarks = fldDef.getRemarks();
         if (remarks.indexOf("\"") >= 0)
         {
            exists = true;
            break;
         }
      }
      
      return exists;
   }
   
   /**
    * Obtiene el número de campos relacionales en la colección.
    * 
    * @return Número de campos relacionales.
    */
   public int getRelFldsCount()
   {
      int count = 0;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.isRel())
         {
            count++;            
         }         
      }
      
      return count;
   }
   
   /**
    * Obtiene el número de campos documentales en la colección.
    * 
    * @return Número de campos documentales
    */
   public int getFtsFldsCount()
   {
      int count = 0;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.isDoc())
         {
            count++;            
         }         
      }
      
      return count;
   }
   
   /**
    * Obtiene una colección con las definiciones de los campos documentales.
    * 
    * @return Colección de campos documentales
    * @throws Exception Errores
    */
   public ArchiveFldsImpl getFtsFlds() throws Exception
   {
      int              i;
      ArchiveFldsImpl multFlds = new ArchiveFldsImpl();
      ArchiveFldImpl  multFld = new ArchiveFldImpl();
      ArchiveFldImpl  fld;      
      
      for(i = 0; i < count(); i++)
      {
         fld = (ArchiveFldImpl)get(i);
         
         if (fld.isDoc())
         {
            multFld = new ArchiveFldImpl(fld.getId(), fld.getName(), fld.getType(),
                        fld.getLen(), fld.isNullable(),
                        fld.getColName(), fld.isDoc(), true, fld.getRemarks());
            
            multFlds.addFld(multFld);

         }
      }
      
      return multFlds;
   }
   
   /**
    * Obtiene el número de campos multivalores en la colección.
    * 
    * @return Número de campos multivalores.
    */
   public int getMultFldsCount()
   {
      int count = 0;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.isMult())
         {
            count++;            
         }         
      }
      
      return count;
   }
   
   /**
    * Obtiene la información de los campos en formato XML.
    *
    * @return La información mencionada.
    */
   public String toXML() 
   {
      return toXML(true);
   }
   
   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Fields";
      ArchiveFldImpl fld;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         fld = (ArchiveFldImpl)get(i);
         bdr.addFragment(fld.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }
   
   
   /**
    * Obtiene si el nombre del campo es válido ó no
    * 
    * @param name - nombre del campo
    * @return true / false
    */
   private boolean isValidName(String name)
   {
      boolean valid = true;
      
      for (int i = 0; i < m_al.size(); i++)
      {
         ArchiveFldImpl fldDef = (ArchiveFldImpl)m_al.get(i);
         if (fldDef.getName().equals(name))
         {
            valid = false;
         }         
      }
      
      return valid;
   }
   
   /**
    * Obtiene el próximo identificador de campo
    * 
    * @return - identificador del campo
    */
   private int getNextFldId()
   {
      int            nextId;
      int            count,i;
      ArchiveFldImpl fldDef = null;

      nextId = 0;
      count = m_al.size();

      for(i=0; i<count; i++)
      {
          fldDef = (ArchiveFldImpl)m_al.get(i);

         if (nextId < fldDef.getId() )
            nextId = fldDef.getId();
      }

      nextId = nextId + 1;

      return(nextId);
   }
   
   private ArrayList m_al;

}

