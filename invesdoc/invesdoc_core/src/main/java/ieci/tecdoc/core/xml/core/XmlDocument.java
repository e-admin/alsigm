
package ieci.tecdoc.core.xml.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.ByteArrayInputStream;
   
/** Representa un documento xml. */ 

public final class XmlDocument
{
   
   private Document m_domDoc;
   
   /** Construye un objeto de la clase. */ 
   
   public XmlDocument()
   {
      m_domDoc = null;
   }
   
   /** Crea el documento a partir de texto xml. 
   * @param text Texto fuente.
   * @throws Exception si se produce algún error. */ 
     
   public void createFromStringText(String text) throws Exception
   {
      
      DocumentBuilderFactory fry;
      DocumentBuilder        bdr;
      ByteArrayInputStream   src;

      fry = DocumentBuilderFactory.newInstance();    
      bdr = fry.newDocumentBuilder();

      src = new ByteArrayInputStream(text.getBytes("UTF8"));
      
      m_domDoc = bdr.parse(src);
      
   }
   
   /** Crea el documento a partir de texto xml codificado en UTF-8. 
   * @param text Texto fuente.
   * @throws Exception si se produce algún error. */ 
   
   public void createFromUtf8Text(byte[] text) throws Exception
   {
      
      DocumentBuilderFactory fry;
      DocumentBuilder        bdr;
      ByteArrayInputStream   src;

      fry = DocumentBuilderFactory.newInstance();      
      bdr = fry.newDocumentBuilder();

      src = new ByteArrayInputStream(text);
      
      m_domDoc = bdr.parse(src);
      
   }
   
   /** Crea el documento a partir del contenido xml de un fichero. 
   * @param pathname Ruta de acceso y nombre del fichero fuente.
   * @throws Exception si se produce algún error. */ 
   
   public void createFromFile(String pathname) throws Exception
   {
      
      DocumentBuilderFactory fry;
      DocumentBuilder        bdr;
      File                   src;
      
      fry = DocumentBuilderFactory.newInstance();      
      bdr = fry.newDocumentBuilder();
      
      src = new File(pathname);  
      
      m_domDoc = bdr.parse(src);
      
   }
   
   /** Devuelve el texto xml del documento. 
   * @param omitXmlHdr Especifica si omitir la cabecera xml.
   * @return El texto mencionado.
   * @throws Exception si se produce algún error. */ 
   
   public String getStringText(boolean omitXmlHdr) throws Exception
   {
      return XmlTransformer.transformXmlDocumentToXmlStringText(this,
                                                                omitXmlHdr);
   }
   
   /** Devuelve el texto xml del documento codificado en UTF-8. 
   * @param omitXmlHdr Especifica si omitir la cabecera xml.
   * @return El texto mencionado.
   * @throws Exception si se produce algún error. */ 
   
   public byte[] getUtf8Text(boolean omitXmlHdr) throws Exception
   {
      return XmlTransformer.transformXmlDocumentToXmlUtf8Text(this, omitXmlHdr);
   }
     
   /** Escribe el documento en un fichero. 
   * @param pathname Ruta de acceso y nombre del fichero.
   * @throws Exception si se produce algún error. */ 
   
   public void writeToFile(String pathname) throws Exception
   {     
      XmlTransformer.transformXmlDocumentToXmlFile(this, pathname);
   }
     
   /** Devuelve el elemento raíz del documento. 
   * @return El elemento mencionado. */
   
   public XmlElement getRootElement()
   {
      
      XmlElement elem = null;
      Element    domElem;

      domElem = m_domDoc.getDocumentElement();
      elem    = new XmlElement(domElem);
      
      return elem;
      
   }
   
   /** Devuelve el documento Dom subyacente.
   * @return El documento mencionado. */
   
   public Document getDomDocument()
   {
      return m_domDoc;
   }

} // class XmlDocument
