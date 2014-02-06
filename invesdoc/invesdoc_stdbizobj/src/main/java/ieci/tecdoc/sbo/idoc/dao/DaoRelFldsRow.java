
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;
import ieci.tecdoc.core.dyndao.DynDaoRec;

public class DaoRelFldsRow extends DynDaoRec
{
   private DaoRelFlds  m_relFlds;  
    
   public DaoRelFldsRow(DaoRelFlds flds, DbColumnDef[] colsDef)
   {
      super(colsDef);      
      m_relFlds = flds;     
   }
   
   public Object getFldData(int fldId) throws Exception
   {
      DaoRelFld fld = m_relFlds.findById(fldId);
      
      return super.getColData(fld.getColName());        
   }   
   
   public Object getFldData(String fldName) throws Exception
   {
      
      DaoRelFld fld = m_relFlds.findByName(fldName);
      
      return super.getColData(fld.getColName()); 
      
   }    
     
   public String getTextFldData(int fldId) throws Exception
   {      
      return (String)getFldData(fldId);        
   } 
   
   public short getShortIntegerFldData(int fldId) throws Exception
   { 
      return ((Short)getFldData(fldId)).shortValue();
   } 
   
   public int getLongIntegerFldData(int fldId) throws Exception
   {
      return ((Integer)getFldData(fldId)).intValue();      
   } 
   
   public float getShortDecimalFldData(int fldId) throws Exception
   {
      return ((Float)getFldData(fldId)).floatValue();      
   }
   
   public double getLongDecimalFldData(int fldId) throws Exception
   {
      return ((Double)getFldData(fldId)).doubleValue();      
   }

   public Date getDateFldData(int fldId) throws Exception
   { 
      return (Date)getFldData(fldId);      
   } 
   
   public void setFldData(int fldId, Object data) throws Exception
   {
      DaoRelFld fld = m_relFlds.findById(fldId);
      
      super.setColData(fld.getColName(), data);        
   }  
   
   public void setFldData(String fldName, Object val) throws Exception
   {
      
      DaoRelFld fld = m_relFlds.findByName(fldName);
      
      super.setColData(fld.getColName(), val); 
      
   }    
     
   public void setTextFldData(int fldId, String data) throws Exception
   {      
      setFldData(fldId, data);        
   } 
   
   public void setShortIntegerFldData(int fldId, short data) throws Exception
   { 
      super.setShortIntegerColData(getFldColName(fldId), data);
   } 
   
   public void setLongIntegerFldData(int fldId, int data) throws Exception
   {
      super.setLongIntegerColData(getFldColName(fldId), data);      
   } 
   
   public void setShortDecimalFldData(int fldId, float data) throws Exception
   {
      super.setShortDecimalColData(getFldColName(fldId), data);      
   }
   
   public void setLongDecimalFldData(int fldId, double data) throws Exception
   {
      super.setLongDecimalColData(getFldColName(fldId), data);    
   }

   public void setDateFldData(int fldId, Date data) throws Exception
   { 
      super.setDateColData(getFldColName(fldId), data);      
   }
   
   public void setFdrId(int fdrId) throws Exception
   { 
      super.setLongIntegerColData(DaoUtil.getRelFldsTblFdrIdColName(),
                                  fdrId);      
   }
   
   public void setTimeStamp(Date ts) throws Exception
   { 
      super.setDateColData(DaoUtil.getRelFldsTblTsColName(),
                           ts);      
   }
   
// ************************************************************************
   
   private String getFldColName(int fldId) throws Exception
   {
      DaoRelFld fld = m_relFlds.findById(fldId);
      
      return fld.getColName();      
   }   
      
}//class
