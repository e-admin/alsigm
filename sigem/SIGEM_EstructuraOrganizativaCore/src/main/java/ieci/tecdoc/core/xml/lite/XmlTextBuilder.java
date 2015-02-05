
package ieci.tecdoc.core.xml.lite;

/** Permite construir texto xml. */   

public final class XmlTextBuilder
{   

   private static final int DEFAULT_CAPACITY = 1024;
   private static final int AUX_CAPACITY     = 128;
   
   private StringBuffer m_textBuf;  
   private StringBuffer m_auxBuf;
   
   /** Construye un objeto de la clase. */ 
   
   public XmlTextBuilder()
   {
      m_textBuf = new StringBuffer(DEFAULT_CAPACITY);
      m_auxBuf  = new StringBuffer(AUX_CAPACITY);      
   }
   
   /** Construye un objeto de la clase.
   * @param initialCapacity Capacidad inicial del StringBuffer subyacente. */ 
   
   public XmlTextBuilder(int initialCapacity)
   {
      m_textBuf = new StringBuffer(initialCapacity);
      m_auxBuf  = new StringBuffer(AUX_CAPACITY);        
   }
   
   /** Establece la cabecera estándar. */
   
   public void setStandardHeader()
   {      
      
      m_auxBuf.setLength(0);
      m_auxBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      
      m_textBuf.insert(0, m_auxBuf);
      
   }
   
   /** Añade un tag de apertura. 
   * @param name Nombre del tag. */
   
   public void addOpeningTag(String name)
   {

      m_auxBuf.setLength(0);
      m_auxBuf.append("<").append(name).append(">");
      
      m_textBuf.append(m_auxBuf);
      
   }
   
   /** Añade un tag de cierre. 
   * @param name Nombre del tag. */
   
   public void addClosingTag(String name)
   {
      
      m_auxBuf.setLength(0);
      m_auxBuf.append("</").append(name).append(">");
      
      m_textBuf.append(m_auxBuf);
      
   }
   
   /** Añade un elemento simple. 
   * @param name Nombre del elemento.
   * @param value Valor del elemento. */
   
   public void addSimpleElement(String name, String value)
   {
      
      m_auxBuf.setLength(0);
      m_auxBuf.append("<").append(name).append(">");
      m_auxBuf.append(value);
      m_auxBuf.append("</").append(name).append(">");
            
      m_textBuf.append(m_auxBuf);
      
   }
   
   /** Añade un fragmento. 
   * @param text Texto del fragmento. */
   
   public void addFragment(String text)
   {
      m_textBuf.append(text);      
   }
   
   /** Devuelve el texto en curso.
   * @return El texto en curso. */  
   
   public String getText()
   {
      return m_textBuf.toString();
   }
   
   /** Reinicia el constructor. */
      
   public void reset()
   {
      m_textBuf.setLength(0);
   }
   
} // class
