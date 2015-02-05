
package ieci.tecdoc.core.xml.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.StringTokenizer;

/** Representa un elemento de un documento xml. */ 

public final class XmlElement
{
   
   private Element m_domElem;
   
   /** Construye un objeto de la clase.
   * @param domElem Elemento Dom subyacente. */
   
   XmlElement(Element domElem)
   {
      m_domElem = domElem;
   }

   /** Devuelve el nombre del elemento. 
   * @return El nombre del elemento. */
   
   public String getName()
   {
      return m_domElem.getTagName();
   }

   /** Devuelve el valor del elemento. 
   * @return El valor del elemento; null si no existe. */
   
   public String getValue()
   {

      String val = null;
      Node   node;
      
      node = m_domElem.getFirstChild();
      if (node != null)
         val = node.getNodeValue();
      
      return val;
      
   }

   /** Establece el valor del elemento. 
   * @param val El nuevo valor del elemento. */
   
   public void setValue(String val)
   {

      Node     node;
      Document doc;
      
      node = m_domElem.getFirstChild();
      if (node != null)
         node.setNodeValue(val);
      else
      {
         doc  = m_domElem.getOwnerDocument();
         node = doc.createTextNode(val);
         m_domElem.appendChild(node);
      }
      
   }
   
   /** Devuelve el (primer) elemento hijo con el nombre especificado. 
   * @param name Nombre del elemento buscado.
   * @return El elemento buscado; null si no existe. */
   
   public XmlElement getChildElement(String name)
   {
      
      XmlElement elem = null;
      NodeList   nodes;
      int        count, i;
      Node       node;
      String     nodeName;
      
      nodes = m_domElem.getChildNodes();      
      count = nodes.getLength();
      
      for (i = 0; i < count; i++)
      {         
         
         node = nodes.item(i);
         
         if (node.getNodeType() == Node.ELEMENT_NODE)
         {
         
            nodeName = node.getNodeName();
            
            if (nodeName.compareTo(name) == 0)
            {
               elem = new XmlElement((Element) node);
               break;
            }
            
         }
         
      }
      
      return elem;
      
   }
   
   /** Devuelve el (primer) elemento descendiente con el nombre especificado. 
   * @param pathname Ruta de acceso y nombre del elemento buscado.
   * <br> La ruta de acceso debe ser relativa al elemento padre.
   * @return El elemento buscado; null si no existe. */
   
   public XmlElement getDescendantElement(String pathname)
   {
      
      XmlElement      elem = null;
      StringTokenizer strTkr;       
      int             count, i;
      String          name;
      XmlElement      parentElem;
      XmlElement      childElem;
      
      strTkr = new StringTokenizer(pathname, "/");  
      count  = strTkr.countTokens();
      
      if (count < 1)
         elem = getChildElement(pathname);
      else
      {
         
         parentElem = this;
         
         for (i = 0; i < count; i++)
         {

            name      = strTkr.nextToken();
            childElem = parentElem.getChildElement(name);
            
            if (childElem == null)
               break;
            else
            {
               parentElem = childElem;
               if (i == (count - 1)) elem = childElem;
            }
         
         }
         
      }
      
      return elem;
      
   }
   
   /** Devuelve la colección de elementos hijo del elemento.   
   * @return La colección mencionada. */
   
   public XmlElements getChildElements()
   {
      return XmlElement.getChildElements(m_domElem);
   }
   
   /** Devuelve el texto xml del elemento. 
   * @param omitXmlHdr Especifica si omitir la cabecera xml.
   * @return El texto mencionado.
   * @throws Exception si se produce algún error. */ 
   
   public String getStringText(boolean omitXmlHdr) throws Exception
   {
      return XmlTransformer.transformXmlElementToXmlStringText(this, 
                                                               omitXmlHdr);
   }
     
   /** Devuelve el texto xml del elemento codificado en UTF-8. 
   * @param omitXmlHdr Especifica si omitir la cabecera xml.
   * @return El texto mencionado.
   * @throws Exception si se produce algún error. */ 
   
   public byte[] getUtf8Text(boolean omitXmlHdr) throws Exception
   {
      return XmlTransformer.transformXmlElementToXmlUtf8Text(this, omitXmlHdr);
   }

   /** Devuelve el elemento Dom subyacente.
   * @return El elemento mencionado. */
   
   public Element getDomElement()
   {
      return m_domElem;
   }
   
   // **************************************************************************
   
   /** Devuelve la colección de elementos hijo del nodo Dom especificado.
   * @param domNode El nodo Dom mencionado.    
   * @return La colección mencionada. */
   
   private static XmlElements getChildElements(Node domNode)
   {
      
      XmlElements  elems = null;
      NodeList     domNodes;
      int          count, count1, i;
      Node[]       domNodes1;
      Node         domNode1;
      XmlElement[] elems1;
      XmlElement   elem1;      
      
      domNodes  = domNode.getChildNodes();      
      count     = domNodes.getLength();
      domNodes1 = new Node[count];
      count1    = 0;
      
      for (i = 0; i < count; i++)
      {         
         
         domNode1 = domNodes.item(i);
         
         if (domNode1.getNodeType() == Node.ELEMENT_NODE)
         { 
            domNodes1[count1] = domNode1;
            count1++;
         }
         
      }

      elems1 = new XmlElement[count1];
      
      for (i = 0; i < count1; i++)
      { 
         domNode1  = domNodes1[i];
         elem1     = new XmlElement((Element) domNode1);
         elems1[i] = elem1;
      }
      
      elems = new XmlElements(elems1);
      
      return elems;
      
   }
      
} // class XmlElement
