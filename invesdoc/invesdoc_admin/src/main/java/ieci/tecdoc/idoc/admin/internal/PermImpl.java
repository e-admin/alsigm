
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;

import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.Permission;

import org.apache.log4j.Logger;

/**
 * Implementa el interfaz UserPerm.
 */

public class PermImpl implements Permission
{
   /**
    * Construye un objeto de la clase.
    *  
    */
    
   public PermImpl()
   {
      _id = Defs.NULL_ID;
      _dest = Defs.DESTINATION_USER;
      _product = UserDefs.PRODUCT_IDOC;
      _perm = UserDefs.PERMISSION_NONE;
   }

   /**
    * Construye un objeto de la clase.
    * 
    * @param id Identificador del destinatario de los permisos.
    * @param dest Destinatario de los permisos (usuario, grupo
    * @param product El identificador del producto.
    * @param perm El permiso asociado al producto.
    */
    
   public PermImpl(int id, int dest, int product, int perm)
   {
      _id = id;
      _dest = dest;
      _product = product;
      _perm = perm;
   }

   public int getId()
   {
      return _id;
   }
   
   public int getDestination()
   {
      return _dest;
   }

   public int getProduct()
   {
      return _product;
   }
   
   public int getPermission()
   {
      return _perm;
   }
   
   public void setPermission(int perm) 
   {
      _perm = perm;
   }

	public String toString()
	{
      return toXML(false);
   }

   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Permission";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();
         
      bdr.addOpeningTag(tagName);

//      bdr.addSimpleElement("Id", Integer.toString(_id));
//      bdr.addSimpleElement("Destination", Integer.toString(_dest));
      bdr.addSimpleElement("ProductId", Integer.toString(_product));
      bdr.addSimpleElement("Permission", Integer.toString(_perm));
      
      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Guarda en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer insertValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertValues");

      statement.setLongInteger(index++, _dest);
      statement.setLongInteger(index++, _id);
      statement.setLongInteger(index++, _product);
      statement.setLongInteger(index++, _perm);

      return new Integer(index);
   }

   /**
    * Actualiza en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer updateValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateValues");

      statement.setLongInteger(index++, _perm);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al objeto. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadValues(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("getValues");

      _dest = statement.getShortInteger(index++);
      _product = statement.getShortInteger(index++);
      _perm = statement.getShortInteger(index++);

      return new Integer(index);
   }

   /**
    * Establece el identificador del objeto.
    * 
    * @param id El identificador mencionado.
    */

   protected void setId(int id) 
   {
      _id = id;
   }

   /**
    * Establece el destinatario de los permisos.
    * 
    * @param dest El destinatario mencionado.
    */

   protected void setDestination(int dest) 
   {
      _dest = dest;
   }

   /**
    * Establece el identificador de producto del permiso.
    * 
    * @param product El identificador mencionado.
    */

   protected void setProduct(int product) 
   {
      _product = product;
   }

   private int _id;
   private int _dest;
   private int _product;
   private int _perm;
   private static final Logger _logger = Logger.getLogger(PermImpl.class);

}