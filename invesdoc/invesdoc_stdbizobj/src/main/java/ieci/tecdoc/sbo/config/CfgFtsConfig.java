
package ieci.tecdoc.sbo.config;

import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;
import ieci.tecdoc.core.textutil.UtilX;
import java.util.StringTokenizer;

public final class CfgFtsConfig 
{
      
   public int                       m_engine;
   public int                       m_platform; 
   public String                    m_root;   
   public IeciTdShortTextArrayList  m_exts = null;

   public CfgFtsConfig()
   {      
   }
   
   protected void restoreFromTokenizer(StringTokenizer tokens) 
                  throws Exception
   {

      int    numExts;
      String extsInfo;
      String ftsInfo;
      
      //Número de extensiones
      numExts = UtilX.parseIntegerNextToken(tokens);
      
      m_exts  = new IeciTdShortTextArrayList();
      if (numExts > 0)
      {
         //Información de extensiones
         extsInfo = UtilX.parseStringNextToken(tokens);
      
         restoreExts(numExts, extsInfo);
      }
      
      ftsInfo = UtilX.parseStringNextToken(tokens);
         
      
      restoreFtsInfo(ftsInfo);
      
   }   
   
   protected String persist() throws Exception
   {
      String misc;
      int    numExts = 0;
      
      if (m_exts != null)
         numExts = m_exts.count();
      
      misc = new Integer(numExts).toString();
      misc = misc + "|";
      
      if (numExts > 0)
      {
         for(int i=0; i<numExts; i++)
         {
            String ext;
            ext = m_exts.get(i);
            if (i == 0)
               misc = misc + "\"" + ext + "\"";
            else
               misc = misc + ",\"" + ext + "\"";
         }
         misc = misc + "|";
      }
      
      misc = misc + new Integer(m_engine).toString() + "," +
             new Integer(m_platform) + ",\"" + m_root + "\"";
      
      return misc;
      
   }
   
   private void restoreFtsInfo(String ftsInfo) throws Exception
   {
   	
      StringTokenizer tokens = new StringTokenizer(ftsInfo, ",");
            
//    Motor
      m_engine = UtilX.parseIntegerNextToken(tokens);      
     
      //Plataforma
      m_platform = UtilX.parseIntegerNextToken(tokens);
      
      //Root      
      m_root = UtilX.parseStringNextToken(tokens, true);   

   }   
   
   
   private void restoreExts(int numExts, String extsInfo) throws Exception
   {
   	
      int             i;
      StringTokenizer tokens = new StringTokenizer(extsInfo, ",");
      String          ext;
            
      for(i = 0; i < numExts; i++)
      {
      	ext = UtilX.parseStringNextToken(tokens, true);
      	m_exts.add(ext);
      } 

   }   
   
   
} // class
