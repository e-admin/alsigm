
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.sbo.idoc.archive.base.ArchiveFldType;
import ieci.tecdoc.sbo.util.types.SboType;

public class ArchiveFldImpl implements ArchiveFld
{
   private int     m_id;
   private String  m_name;
   private int     m_type;
   private int     m_len;
   private boolean m_isNullable;
   private String  m_colName;
   private boolean m_isDoc;
   private boolean m_isMult;
   private String  m_remarks;
   
   public ArchiveFldImpl()
   {
      m_id         = SboType.NULL_ID;
      m_name       = null;     
      m_type       = 0;
      m_len        = 0;
      m_isNullable = true;   
      m_colName    = null;
      m_isDoc      = false;
      m_isMult     = false;  
      m_remarks    = null;
   }

   /**
    * Contruye un objeto de tipo ArchiveFldDefImpl.
    * 
    * @param id  Identificador del campo (solo si se ha recuperado de base de datos).
    * @param name  Nombre del campo.
    * @param type  Tipo de campo en base de dato 
    * @param len   Longitud del campo, válido si es texto ó texto largo.
    * @param isNullable Indica si el campo puede ser nulo (true /false).
    * @param colName    Nombre de columna en base de datos.
    * @param isDoc      Indica si el campo es documental.
    * @param isMult     Indica si el campo es multivalor.
    * @param remarks    Descripción del campo.
    */
   protected ArchiveFldImpl(int id, String name, int type, int len, boolean isNullable,
         					String colName, boolean isDoc, boolean isMult, String remarks)
   {
      m_id         = id;
      m_name       = name;
      m_type       = type;
      m_len        = len;
      m_isNullable = isNullable;
      m_colName    = colName;
      m_isDoc      = isDoc;
      m_isMult     = isMult;
      m_remarks    = remarks;
   }
   
   /**
    * Obtiene el identificador del campo.
    * 
    * @return El identificador.
    */
   public int getId()
   {
      return m_id;
   }
   
   /**
    * Obtiene el nombre del campo.
    * 
    * @return El nombre.
    */
   public String getName()
   {
      return m_name;
   }
   
   /**
    * Establece el nombre del campo.
    * 
    * @param name  Nombre del campo.
    */
   public void setName(String name)
   {
      m_name = name;
   }
   
   /**
    * Obtiene la descripción del campo.
    * 
    * @return La descripción.
    */
   public String getRemarks()
   {
      return m_remarks;
   }
   
   /**
    * Establece la descripción del campo
    * 
    * @param remarks descripción
    */
   public void setRemarks(String remarks)
   {
      m_remarks = remarks;
   }
   
   /**
    * Obtiene el nombre de la columna en base de datos
    * referente al campo.
    * 
    * @return Nombre de columna.
    */
   public String getColName()
   {
      return m_colName;
   }
   
   /**
    * Obtiene el tipo de campo en base de datos.
    * 
    * @return Tipo de campo
    */
   public int getType()
   {
      return m_type;
   }
   
   /**
    * Establece el tipo de campo en base de datos
    *  
    * @param type Tipo de campo
    */
   public void setType(int type)
   {
      m_type = type;
   }
   
   /**
    * Obtiene la longitud del campo.
    * 
    * @return La longitud.
    */
   public int getLen()
   {
      return m_len;
   }
   
   /**
    * Establece la longitud del campo.
    * 
    * @param len longitud
    */
   public void setLen(int len)
   {
      m_len = len;
   }
   
   /**
    * Obtiene la nulidad del campo.
    * 
    * @return true - si puede ser null, false - si no puede ser null
    */
   public boolean isNullable()
   {
      return m_isNullable;
   }
   
   /**
    * Establece o no la obligatoriedad del campo
    * 
    * @param nullable true / false
    */
   public void setNullable(boolean nullable)
   {
      m_isNullable = nullable;
   }
   
   /**
    * Obtiene si el campo tiene búsqueda documental.
    * 
    * @return true - tiene búsqueda documental, false - no tiene
    */
   public boolean isDoc()
   {
      return m_isDoc;
   }
   
   /**
    * Establece o no la búsqueda documental de un campo.
    * 
    * @param isDoc  true / false
    */
   public void setDoc(boolean isDoc)
   {
      m_isDoc = isDoc;
   }
   
   /**
    * obtiene si el campo es multivalor.
    * 
    * @return true - es multivalor, false - no es multivalor
    */
   public boolean isMult()
   {
      return m_isMult;
   }
   
   /**
    * Establece o no si el campo es multivalor
    * 
    * @param mult true / false
    */
   public void setMult(boolean mult)
   {
      m_isMult = mult;
   }
   
   /**
    * Obtiene si el campo es relacional.
    * 
    * @return true - es relacional, false - no es relacional
    */
   public boolean isRel()
   {
      boolean isRel = false;
      
      if (!isMult() && 
          (getType() != ArchiveFldType.LONG_TEXT) )
         isRel = true;
      
      return isRel;
   }
   
   /**
    * Obtiene si el campo es extendido.
    * 
    * @return true - es extendido, false - no es extendido
    */
   public boolean isExt()
   {
      boolean isExt = false;
      
      if (getType() == ArchiveFldType.LONG_TEXT )
         isExt = true;
      
      return isExt;
   }
   
   /**
    * Obtiene la información del campo en formato XML.
    * @param header true /false
    * @return
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Field";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("Id", Integer.toString(m_id));
      bdr.addSimpleElement("Name", m_name);
      bdr.addSimpleElement("Type", Integer.toString(m_type));
      bdr.addSimpleElement("Len", Integer.toString(m_len));
      bdr.addSimpleElement("Nullable", Boolean.toString(m_isNullable));
      bdr.addSimpleElement("ColName", m_colName);
      bdr.addSimpleElement("Documental", Boolean.toString(m_isDoc));
      bdr.addSimpleElement("Multival", Boolean.toString(m_isMult));
      bdr.addSimpleElement("Description", m_remarks);      

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   
   /**
    * Obtiene la información del campo en formato XML.
    *
    * @return La información mencionada.
    */
   public String toXML() 
   {
      return toXML(true);
   }
   
   
   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */
   public String toString()
   {
      return toXML(false);
   }
   
}

