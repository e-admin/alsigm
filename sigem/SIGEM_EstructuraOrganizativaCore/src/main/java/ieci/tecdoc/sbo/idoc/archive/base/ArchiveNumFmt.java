
package ieci.tecdoc.sbo.idoc.archive.base;

public final class ArchiveNumFmt
{      
   private String  m_thousandSep;
   private String  m_decimalSep;
   private int     m_numDecDigits;
      
   public ArchiveNumFmt()
   {
      m_thousandSep  = null;
      m_decimalSep   = null;
      m_numDecDigits = 0;
   }
   
   public ArchiveNumFmt(String thousandSep, String decimalSep, int numDecDigits)
   {
      m_thousandSep  = thousandSep;
      m_decimalSep   = decimalSep;
      m_numDecDigits = numDecDigits;   
   }
   
   public String getThousandSeparator()
   {
      return m_thousandSep;
   }
   
   public String getDecimalSeparator()
   {
      return m_decimalSep;
   }
   
   public int getNumDecDigits()
   {
      return m_numDecDigits;
   }
   
} // class
