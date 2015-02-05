
package ieci.tecdoc.core.xml.lite;

import ieci.tecdoc.core.exception.IeciTdException;

/** Permite . */  

public final class XmlTextParser
{
   
   private String m_text;
   private int    m_pos;
   
   /** Construye un objeto de la clase. */ 
   
   public XmlTextParser()
   {
      m_text = null;
      m_pos  = -1;
   }
   
   public void createFromStringText(String text)
   {
      m_text = text;
      m_pos  = 0;
   }
   
   public void selectRootElement() throws Exception
   {
      
      int pos;
      
      pos = m_text.indexOf('<', 0);
      if (pos >= 0)
      {
         
         if (m_text.charAt(pos + 1) != '?')
            m_pos = pos;
         else
         {
            
            pos = m_text.indexOf('<', pos + 2);
            if (pos >= 0)
               m_pos = pos;
            else
            {
               throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                         XmlLiteError.EM_TEXT_PARSER);
            }
            
         }
         
      }
      else
      {
         throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                   XmlLiteError.EM_TEXT_PARSER);
      }      
      
   }
   
   public void selectElement(String name) throws Exception
   {
      
      int pos, pos1; 
      
      pos1 = m_pos;
      if (pos1 != 0) pos1++;

      pos = m_text.indexOf("<" + name + ">", pos1);
      if (pos >= 0)
         m_pos = pos;
      else
      {
         throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                   XmlLiteError.EM_TEXT_PARSER);
      }
      
   }
   
   public String getElementName() throws Exception
   {
      
      String name;
      int    pos;
      
      pos = m_text.indexOf('>', m_pos + 1);
      if (pos >= 0)
      {
         name = m_text.substring(m_pos + 1, pos);
      }
      else
      {
         throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                   XmlLiteError.EM_TEXT_PARSER);
      }
      
      return name;
      
   }
   
   public String getElementValue() throws Exception
   {
      
      String value;
      int    pos1, pos2;   
      
      pos1 = m_text.indexOf('>', m_pos + 1);
      if (pos1 >= 0)
      {

         pos2 = m_text.indexOf('<', pos1 + 1);
         if (pos2 >= 0)
         {
            value = m_text.substring(pos1 + 1, pos2);
         }
         else
         {
            throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                      XmlLiteError.EM_TEXT_PARSER);
         }
         
      }
      else
      {
         throw new IeciTdException(XmlLiteError.EC_TEXT_PARSER,
                                   XmlLiteError.EM_TEXT_PARSER);
      }
      
      return value;
      
   }
   
} // class
