
package ieci.tecdoc.core.collections;

// No es final porque se extiende en DbStSiRec

public class IeciTdStSi
{
   
   public String m_fld1;
   public short  m_fld2;
   
   public IeciTdStSi()
   {
      m_fld1 = null;
      m_fld2 = 0;
   }
   
   public IeciTdStSi(String fld1, short fld2)
   {
      m_fld1 = fld1;
      m_fld2 = fld2;
   }
   
} // class
