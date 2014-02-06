
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;


public final class ArchiveTokenFlds implements Serializable
{
   
   private ArrayList m_al;
   
   public ArchiveTokenFlds()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(ArchiveTokenFld item)
   {      
      m_al.add(item);      
   }
   
   public void add(int id, String name, int type, int len, boolean isNullable,
                   String colName, boolean isDoc, boolean isMult, String remarks)
   {
      
      ArchiveTokenFld fld;
      
      fld = new ArchiveTokenFld(id, name, type, len, isNullable, 
                                 colName, isDoc, isMult, remarks);
      
      m_al.add(fld);
      
   }
   
   public ArchiveTokenFld get(int index)
   {
      return (ArchiveTokenFld) m_al.get(index);
   } 
   
   public ArchiveTokenFld findById(int fldId) throws Exception
   {
      int             i;
      ArchiveTokenFld fld = null;
      boolean         find = false;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.getId() == fldId)
         {
            find = true;
            break;            
         }
      }
      
      if (!find)
      {
         throw new IeciTdException(ArchiveBaseError.EC_NOT_FOUND, 
                                   ArchiveBaseError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   public ArchiveTokenFld findByName(String fldName) throws Exception
   {
      int             i;
      ArchiveTokenFld fld = null;
      boolean         find = false;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.getName().equals(fldName))
         {
            find = true;
            break;            
         }
      }
      
      if (!find)
      {
         throw new IeciTdException(ArchiveBaseError.EC_NOT_FOUND, 
                                   ArchiveBaseError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   public Object clone()
   {
      int               i;
      ArchiveTokenFlds  flds = new ArchiveTokenFlds();
      ArchiveTokenFld   fld  = null;
           
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         flds.add(fld.getId(), fld.getName(), fld.getType(), fld.getLen(),
                  fld.isNullable(), fld.getColName(), fld.isDoc(), fld.isMult(),
                  fld.getRemarks());         
      }
      
      return flds;
   }
   
   public ArchiveTokenFlds getFlds(IeciTdLongIntegerArrayList fldIds)
                           throws Exception
   {  
      ArchiveTokenFld   fld  = null;
      ArchiveTokenFlds  flds = new ArchiveTokenFlds(); 
      int               i;
      int               fldId;
      
      for(i = 0; i < fldIds.count(); i++)
      {
         fldId = fldIds.get(i);         
         fld   = findById(fldId);
         
         flds.add(fld.getId(), fld.getName(), fld.getType(), fld.getLen(),
                  fld.isNullable(), fld.getColName(), fld.isDoc(), fld.isMult(),
                  fld.getRemarks());        
         
      }
      
      return flds;      
   }
   
   public ArchiveTokenFlds getFlds(String[] fldNames)
                           throws Exception
   {  
      ArchiveTokenFld   fld  = null;
      ArchiveTokenFlds  flds = new ArchiveTokenFlds(); 
      int               i;
      String            fldName;
      
      for(i = 0; i < fldNames.length; i++)
      {
         fldName = fldNames[i];         
         fld     = findByName(fldName);
         
         flds.add(fld.getId(), fld.getName(), fld.getType(), fld.getLen(),
                  fld.isNullable(), fld.getColName(), fld.isDoc(), fld.isMult(),
                  fld.getRemarks());        
         
      }
      
      return flds;      
   } 
   
   public ArchiveTokenFlds getRelFlds() throws Exception
   {
      int              i;
      ArchiveTokenFlds relFlds = new ArchiveTokenFlds();
      ArchiveTokenFld  fld;      
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.isRel())
         {
            
            relFlds.add(fld.getId(), fld.getName(), fld.getType(),
                        fld.getLen(), fld.isNullable(),
                        fld.getColName(), fld.isDoc(), false, fld.getRemarks());

         }
      }
      
      return relFlds;
   }
   
   public ArchiveTokenFlds getMultFlds() throws Exception
   {
      int              i;
      ArchiveTokenFlds multFlds = new ArchiveTokenFlds();
      ArchiveTokenFld  fld;      
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.isMult())
         {
            
            multFlds.add(fld.getId(), fld.getName(), fld.getType(),
                        fld.getLen(), fld.isNullable(),
                        fld.getColName(), fld.isDoc(), true, fld.getRemarks());

         }
      }
      
      return multFlds;
   }
   
   public ArchiveTokenFlds getExtFlds() throws Exception
   {
      int              i;
      ArchiveTokenFlds extFlds = new ArchiveTokenFlds();
      ArchiveTokenFld  fld;      
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.isExt())
         {
            
            extFlds.add(fld.getId(), fld.getName(), fld.getType(),
                        fld.getLen(), fld.isNullable(),
                        fld.getColName(), fld.isDoc(), false, fld.getRemarks());

         }
      }
      
      return extFlds;
   }
      
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ArchiveTokenFlds[");
      
      for(int i = 0; i < m_al.size(); i++)
      {

          buffer.append(" [Field").append(i+1);
          buffer.append(" = ").append((m_al.get(i)).toString());
          buffer.append("] ");

      }
      
      buffer.append("]");
      return buffer.toString();
   }
} // class
