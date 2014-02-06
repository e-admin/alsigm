
package ieci.tecdoc.core.xml.adv;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.apache.xerces.parsers.DOMParser;
import ieci.tecdoc.core.exception.IeciTdException;

/** Permite chequear una fuente xml contra un esquema.
* <br><br>NOTA. Requiere <b>xercesImpl.jar</b>.  */ 

public final class XmlChecker implements ErrorHandler  
{  
   
   private DOMParser    m_domParser;  
   private boolean      m_onlyCheckValidity;
   private StringBuffer m_infoMsg;
   private boolean      m_valid;  
   
   /** Construye un objeto de la clase. */ 
   
   public XmlChecker()
   {
      m_domParser         = null;
      m_onlyCheckValidity = true;
      m_infoMsg           = null;
      m_valid             = true;      
   }

   /** Crea el chequeador.
   * @param onlyCheckValidity Especifica si el chequeador sólo debe verificar
   * la validez de la fuente xml. En caso afirmativo, un chequeo finalizará
   * al encontrarse el primer error y no se reportará información acerca del
   * mismo. En otro caso, el chequeo continuará hasta donde sea posible y se
   * reportará información extendida de los errores y warnings encontrados.
   * @throws Exception si se produce algún error. */ 
   
   public void create(boolean onlyCheckValidity) throws Exception
   {
      
      String name;
      
      try
      {
         
         m_domParser = new DOMParser();

         m_domParser.setErrorHandler(this);

         name = "http://xml.org/sax/features/validation";
         m_domParser.setFeature(name, true);

         name = "http://apache.org/xml/features/validation/schema";
         m_domParser.setFeature(name, true);

         m_onlyCheckValidity = onlyCheckValidity;

         if (!m_onlyCheckValidity)      
         {

            m_infoMsg = new StringBuffer(512);         

            name = "http://apache.org/xml/features/continue-after-fatal-error";
            m_domParser.setFeature(name, true);        

         }
      
      }
      catch (NoClassDefFoundError e)
      {
         throw new IeciTdException(XmlAdvError.EC_PARSER_NOT_FOUND,
                                   XmlAdvError.EM_PARSER_NOT_FOUND, e);
      }
      
   }

   /** Especifica el esquema contra el que se efectuará el chequeo.
   * @param pathname Ruta de acceso y nombre del fichero con el esquema.
   * @throws Exception si se produce algún error. */ 
   
   public void setSchemaFile(String pathname) throws Exception
   {
      
      String name;
      File   xsdFile;
         
      xsdFile = new File(pathname);

      name = "http://apache.org/xml/properties/schema/" 
             + "external-noNamespaceSchemaLocation";
      m_domParser.setProperty(name, xsdFile.toURI().toString());
         
   }

   /** Efectúa el chequeo de una fuente xml.
   * @param pathname Ruta de acceso y nombre del fichero con la 
   * fuente xml.
   * @return true si el chequeo es satisfactorio; false, en caso contrario.
   * @throws Exception si se produce algún error. */ 
   
   public boolean checkXmlFile(String pathname) throws Exception
   {
      
      try
      {
         
         m_valid = true;
         
         if (!m_onlyCheckValidity)
            m_infoMsg.setLength(0);

         m_domParser.parse(pathname);

         return m_valid;         
         
      }
      catch (SAXParseException e)
      {
         return m_valid;            
      }
      
   }   

   /** Efectúa el chequeo de una fuente xml.
   * @param text Texto, codificado en UTF-8, con la fuente xml.
   * @return true si el chequeo es satisfactorio; false, en caso contrario.
   * @throws Exception si se produce algún error. */ 
   
   public boolean checkXmlUtf8Text(byte[] text) throws Exception
   {

      ByteArrayInputStream is;      
      InputSource          src;
      
      try
      {
         
         m_valid = true;
         
         if (!m_onlyCheckValidity)
            m_infoMsg.setLength(0);
      
         is  = new ByteArrayInputStream(text);
         src = new InputSource(is);
      
         m_domParser.parse(src);

         return m_valid;         
         
      }
      catch (SAXParseException e)
      {
         return m_valid;            
      }
      
   }   

   /** Efectúa el chequeo de una fuente xml.
   * @param text Texto con la fuente xml.
   * @return true si el chequeo es satisfactorio; false, en caso contrario.
   * @throws Exception si se produce algún error. */ 
   
   public boolean checkXmlStringText(String text) throws Exception
   {

      StringReader r;      
      InputSource  src;
      
      try
      {
         
         m_valid = true;
         
         if (!m_onlyCheckValidity)
            m_infoMsg.setLength(0);
      
         r   = new StringReader(text);
         src = new InputSource(r);
      
         m_domParser.parse(src);

         return m_valid;         
         
      }
      catch (SAXParseException e)
      {
         return m_valid;            
      }
      
   }   
      
   /** Devuelve la información obtenida en el chequeo de una fuente xml.
   * @return La información mencionada. */
   
   public String getCheckInfo()
   {      
      
      String msg, msg1, msg2;
      
      if (m_valid)
         msg1 = "NO ERRORS.";
      else
         msg1 = "ERRORS.";
      
      if (m_infoMsg != null)
         msg2 = m_infoMsg.toString();
      else
         msg2 = "";
      
      msg = msg1 + msg2;
      
      return msg;
      
   }

   /** Chequea la validez de una fuente xml contra un esquema.
   * @param xmlFilePathName Ruta de acceso y nombre del fichero con la 
   * fuente xml.
   * @param xsdFilePathName Ruta de acceso y nombre del fichero con el esquema.
   * @return true si la fuente es válida; false, en caso contrario.
   * @throws Exception si se produce algún error. */ 
   
   public static boolean validateXmlFileUsingXsdFile(String xmlFilePathName,
                                                     String xsdFilePathName)
                         throws Exception
   {
      
      boolean    valid;
      XmlChecker checker;
      
      checker = new XmlChecker();
      
      checker.create(true);
      checker.setSchemaFile(xsdFilePathName);      
      valid = checker.checkXmlFile(xmlFilePathName);

      return valid;
      
   }

   /** Chequea la validez de una fuente xml contra un esquema.
   * @param xmlText Texto, codificado en UTF-8, con la fuente xml.
   * @param xsdFilePathName Ruta de acceso y nombre del fichero con el esquema.
   * @return true si la fuente es válida; false, en caso contrario..
   * @throws Exception si se produce algún error. */ 
   
   public static boolean validateXmlUtf8TextUsingXsdFile
                         (byte[] xmlText, String xsdFilePathName)
                         throws Exception
   {
      
      boolean    valid;
      XmlChecker checker;
      
      checker = new XmlChecker();
      
      checker.create(true);
      checker.setSchemaFile(xsdFilePathName);      
      valid = checker.checkXmlUtf8Text(xmlText);

      return valid;
      
   }

   /** Chequea la validez de una fuente xml contra un esquema.
   * @param xmlText Texto con la fuente xml.
   * @param xsdFilePathName Ruta de acceso y nombre del fichero con el esquema.
   * @return true si la fuente es válida; false, en caso contrario.
   * @throws Exception si se produce algún error. */ 
   
   public static boolean validateXmlStringTextUsingXsdFile
                         (String xmlText, String xsdFilePathName)
                         throws Exception
   {
      
      boolean    valid;
      XmlChecker checker;
      
      checker = new XmlChecker();
      
      checker.create(true);
      checker.setSchemaFile(xsdFilePathName);      
      valid = checker.checkXmlStringText(xmlText);

      return valid;
      
   }
   
   /** Método interno.
   * @param exc Ver org.xml.sax.ErrorHandler
   * @throws SAXException Ver org.xml.sax.ErrorHandler */
   
   public void warning(SAXParseException exc)
               throws SAXException
   {
      
      String msg;
   
      if (!m_onlyCheckValidity)
      {
         msg = "Warning at (" + exc.getLineNumber() + ","
               + exc.getColumnNumber() + "): " + exc.getMessage();
         m_infoMsg.append("\r\n\r\n" + msg);
      }
   
   }
   
   /** Método interno.
   * @param exc Ver org.xml.sax.ErrorHandler
   * @throws SAXException Ver org.xml.sax.ErrorHandler */
   
   public void error(SAXParseException exc)
               throws SAXException
   {
      
      String msg;
      
      m_valid = false;
      
      if (m_onlyCheckValidity)
         throw exc;
      else
      {
         msg = "Error at (" + exc.getLineNumber() + "," + exc.getColumnNumber()
               + "): " + exc.getMessage();
         m_infoMsg.append("\r\n\r\n" + msg);
      }
      
   }
   
   /** Método interno.
   * @param exc Ver org.xml.sax.ErrorHandler
   * @throws SAXException Ver org.xml.sax.ErrorHandler */
   
   public void fatalError(SAXParseException exc)
               throws SAXException
   {
      
      String msg;
      
      m_valid = false;
      
      if (m_onlyCheckValidity)
         throw exc;
      else
      {
         msg = "Fatal error at (" + exc.getLineNumber() + ","
               + exc.getColumnNumber() + "): " + exc.getMessage();
         m_infoMsg.append("\r\n\r\n" + msg);
      }
            
   }               

} // class XmlChecker
