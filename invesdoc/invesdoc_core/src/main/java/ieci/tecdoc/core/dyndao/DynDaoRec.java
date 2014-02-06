
package ieci.tecdoc.core.dyndao;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.core.exception.IeciTdException;
import java.util.Date;
import java.util.ArrayList;

public class DynDaoRec implements DbInputRecord, DbOutputRecord
{
   private DbColumnDef[] m_colsDef;
   private ArrayList     m_colVals; 
   
   private DynDaoRec()
   {
      m_colsDef = null;
      m_colVals = new ArrayList();
   }
   
   protected void init(DbColumnDef[] colsDef)
   {
      m_colsDef = colsDef;      
   }
  
   public DynDaoRec(DbColumnDef[] colsDef)
   {
      m_colsDef = colsDef;
      m_colVals = new ArrayList(colsDef.length);
   }
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      int i, dataType;
      
      for (i = 0; i < m_colsDef.length; i++)
      {
         dataType = m_colsDef[i].getDataType();         
         setColValue(stmt, i, dataType);
      }
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      int i, dataType;
      
      for (i = 0; i < m_colsDef.length; i++)
      {
         dataType = m_colsDef[i].getDataType();         
         getColValue(stmt, i, dataType);
      }
   } 
   
   private void setColValue(DbInputStatement stmt, int colIdx,
                            int colDataType) throws Exception
   {
      Object colVal = m_colVals.get(colIdx);
      
      switch(colDataType)
      {
         case DbDataType.SHORT_TEXT:
         {
            String shortText;
            
            if (colVal == null)
               shortText = DbDataType.NULL_SHORT_TEXT;
            else
               shortText = (String)colVal;
            
            stmt.setShortText(colIdx + 1, shortText);
            break;
         }
         case DbDataType.LONG_TEXT:
         {
            String longText;
                 
            if (colVal == null)
            {
               longText = DbDataType.NULL_LONG_TEXT;
            }
            else
               longText = (String)colVal;
               
            stmt.setLongText(colIdx + 1, longText);            
            break;
         }
         case DbDataType.SHORT_INTEGER:
         {
            short  shortInt;
             
            if (colVal == null)
            {
               shortInt = DbDataType.NULL_SHORT_INTEGER;
            }
            else
               shortInt = ((Short)colVal).shortValue();
               
            stmt.setShortInteger(colIdx + 1, shortInt);            
            break;
         }
         case DbDataType.LONG_INTEGER:
         {
            int longInt; 
             
            if (colVal == null)
            {
               longInt = DbDataType.NULL_LONG_INTEGER;
            }
            else
               longInt = ((Integer)colVal).intValue();
               
            stmt.setLongInteger(colIdx + 1, longInt);  
                      
            break;
         }
         case DbDataType.SHORT_DECIMAL:
         {
            float shortDec;
             
            if (colVal == null)
            {
               shortDec = DbDataType.NULL_SHORT_DECIMAL;
            }
            else
               shortDec = ((Float)colVal).floatValue();
               
            stmt.setShortDecimal(colIdx + 1, shortDec); 
            break;
         }
         case DbDataType.LONG_DECIMAL:
         {
            double longDec;  
          
            if (colVal == null)
            {
               longDec = DbDataType.NULL_LONG_DECIMAL;
            }
            else
               longDec = ((Double)colVal).doubleValue();
               
            stmt.setLongDecimal(colIdx + 1, longDec); 
            break;
         }
         case DbDataType.DATE_TIME:
         {
            Date dt;
            
            if (colVal == null)
            {
               dt = DbDataType.NULL_DATE_TIME;
            }
            else
               dt = (Date)colVal;
               
            stmt.setDateTime(colIdx + 1, dt); 
            break;
         }         
      }
      
   }   
   
   private void getColValue(DbOutputStatement stmt, int colIdx,
                            int colDataType) throws Exception
   {
      switch(colDataType)
      {
         case DbDataType.SHORT_TEXT:
         {
            m_colVals.add(colIdx, stmt.getShortText(colIdx + 1));
            break;
         }
         case DbDataType.LONG_TEXT:
         {
            m_colVals.add(colIdx, stmt.getLongText(colIdx + 1));
            break;
         }
         case DbDataType.SHORT_INTEGER:
         {
            Short val = new Short(stmt.getShortInteger(colIdx + 1));
            m_colVals.add(colIdx, val);
            break;
         }
         case DbDataType.LONG_INTEGER:
         {
            Integer val = new Integer(stmt.getLongInteger(colIdx + 1));
            m_colVals.add(colIdx, val);
            break;
         }
         case DbDataType.SHORT_DECIMAL:
         {
            Float val = new Float(stmt.getShortDecimal(colIdx + 1));
            m_colVals.add(colIdx, val);
            break;
         }
         case DbDataType.LONG_DECIMAL:
         {
            Double val = new Double(stmt.getLongDecimal(colIdx + 1));
            m_colVals.add(colIdx, val);
            break;
         }
         case DbDataType.DATE_TIME:
         {
            m_colVals.add(colIdx, stmt.getDateTime(colIdx + 1));
            break;
         }         
      }
      
   } 
   
   private int getColIdx(String colName) throws Exception
   {
      int idx = -1;
      int i;
      
      for (i = 0; i < m_colsDef.length; i++)
      {
         if (m_colsDef[i].getName().equals(colName))
         {
            idx = i;
            break;
         }
      }
      
      return idx;
   }
   
   public Object getColData(String colName) throws Exception
   {
      
      int idx;     
      
      idx = getColIdx(colName);
      
      if (idx == -1)
      {
         throw new IeciTdException(DynDaoError.EC_NOT_FOUND, 
                                   DynDaoError.EM_NOT_FOUND);      

      }
      
      return m_colVals.get(idx);
      
   } 
   
   public String getTextColData(String colName) throws Exception
   {      
      return (String)getColData(colName);        
   } 
   
   public short getShortIntegerColData(String colName) throws Exception
   { 
      return ((Short)getColData(colName)).shortValue();
   } 
   
   public int getLongIntegerColData(String colName) throws Exception
   {
      return ((Integer)getColData(colName)).intValue();      
   } 
   
   public float getShortDecimalColData(String colName) throws Exception
   {
      return ((Float)getColData(colName)).floatValue();      
   }
   
   public double getLongDecimalColData(String colName) throws Exception
   {
      return ((Double)getColData(colName)).doubleValue();      
   }

   public Date getDateColData(String colName) throws Exception
   { 
      return (Date)getColData(colName);      
   } 
   
   //Estos métodos hay que llamarlos en el mismo orden que la definición
   //de las columnas
   
   public void setColData(String colName, Object data) throws Exception
   {
      
      int idx;     
      
      idx = getColIdx(colName);
      
      if (idx == -1)
      {
         throw new IeciTdException(DynDaoError.EC_NOT_FOUND, 
                                   DynDaoError.EM_NOT_FOUND);      

      }
      
      m_colVals.add(idx, data);
      
   } 
   
   public void setTextColData(String colName, String data) throws Exception
   {      
      setColData(colName, data);        
   } 
   
   public void setShortIntegerColData(String colName, short data) throws Exception
   { 
      Short colData = new Short(data);
      
      setColData(colName, colData);
   } 
   
   public void setLongIntegerColData(String colName, int data) throws Exception
   {
      Integer colData = new Integer(data);
      
      setColData(colName, colData);      
   } 
   
   public void setShortDecimalColData(String colName, float data) throws Exception
   {
      Float colData = new Float(data);
      
      setColData(colName, colData);    
   }
   
   public void setLongDecimalColData(String colName, double data) throws Exception
   {
      Double colData = new Double(data);
      
      setColData(colName, colData);      
   }

   public void setDateColData(String colName, Date data) throws Exception
   { 
      setColData(colName, data);       
   }
   
   protected DbColumnDef[] getColumnsDef()
   {
      return m_colsDef;
   }
   
}//class
