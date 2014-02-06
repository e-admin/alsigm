
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoMultFldRow implements DbInputRecord, DbOutputRecord
{
   private  int    m_fdrId;  
   private  int    m_fldId;
   private  Object m_val;
   private  int    m_sortOrder;      
   private  int    m_fldDbType;
   
   public DaoMultFldRow(int fldDbType)
   {
      m_fldDbType  = fldDbType;       
   }
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_fdrId);
      stmt.setLongInteger(i++, m_fldId);     
      setValue(stmt, i++);         
      stmt.setLongInteger(i++, m_sortOrder);      
      
   } 
   
   private void setValue(DbInputStatement stmt, int idx) throws Exception
   {
          
      switch(m_fldDbType)
      {
         case DbDataType.SHORT_TEXT:
         {
            stmt.setShortText(idx, (String)m_val);
            break;
         }
         case DbDataType.LONG_TEXT:
         {
            stmt.setLongText(idx, (String)m_val);            
            break;
         }
         case DbDataType.SHORT_INTEGER:
         {
            stmt.setShortInteger(idx, ((Short)m_val).shortValue());            
            break;
         }
         case DbDataType.LONG_INTEGER:
         {
            stmt.setLongInteger(idx, ((Integer)m_val).intValue());            
            break;
         }
         case DbDataType.SHORT_DECIMAL:
         {
            stmt.setShortDecimal(idx, ((Float)m_val).floatValue()); 
            break;
         }
         case DbDataType.LONG_DECIMAL:
         {
            stmt.setLongDecimal(idx, ((Double)m_val).doubleValue()); 
            break;
         }
         case DbDataType.DATE_TIME:
         {
            stmt.setDateTime(idx, (Date)m_val); 
            break;
         }
      
      }
   }
      
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
   
      int i = 1;
   
      m_fdrId = stmt.getLongInteger(i++);
      m_fldId = stmt.getLongInteger(i++);     
      getValue(stmt, i++);         
      m_sortOrder = stmt.getLongInteger(i++);      
   
   }
      
   private void getValue(DbOutputStatement stmt, int idx) throws Exception
   {
       
      switch(m_fldDbType)
      {
         case DbDataType.SHORT_TEXT:
         {
            m_val = stmt.getShortText(idx);
            break;
         }
         case DbDataType.LONG_TEXT:
         {
            m_val = stmt.getLongText(idx);            
            break;
         }
         case DbDataType.SHORT_INTEGER:
         {
            m_val = new Short(stmt.getShortInteger(idx));            
            break;
         }
         case DbDataType.LONG_INTEGER:
         {
            m_val = new Integer(stmt.getLongInteger(idx));            
            break;
         }
         case DbDataType.SHORT_DECIMAL:
         {
            m_val = new Float(stmt.getShortDecimal(idx)); 
            break;
         }
         case DbDataType.LONG_DECIMAL:
         {
            m_val = new Double(stmt.getLongDecimal(idx)); 
            break;
         }
         case DbDataType.DATE_TIME:
         {
            m_val = stmt.getDateTime(idx); 
            break;
         }      
      } 
               
   } 
   
   public int getFdrId()
   {
      return m_fdrId;
   }
   
   public int getFldId()
   {
      return m_fldId;
   }
   
   public Object getVal()
   {
      return m_val;
   }
   
   public int getSortOrder()
   {
      return m_sortOrder;
   }
   
   public void setFdrId(int fdrId)
   {
      m_fdrId = fdrId;
   }
   
   public void setFldId(int fldId)
   {
      m_fldId = fldId;
   }
   
   public void setVal(Object val)
   {
      m_val = val;
   }
   
   public void setSortOrder(int sortOrder)
   {
      m_sortOrder = sortOrder;
   }
   
} // class
