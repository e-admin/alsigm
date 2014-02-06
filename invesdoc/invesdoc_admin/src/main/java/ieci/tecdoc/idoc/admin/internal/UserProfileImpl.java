
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;

import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;

import org.apache.log4j.Logger;


public class UserProfileImpl implements UserProfile
{
   /**
    * Construye un objeto de la clase.
    *  
    */
    
   public UserProfileImpl()
   {
      _userId = Defs.NULL_ID;
      _product = UserDefs.PRODUCT_IDOC;
      _profile = UserDefs.PROFILE_NONE;
   }

   /**
    * Construye un objeto de la clase.
    * 
    * @param userId El identificador del usuario.
    * @param product El identificador del producto.
    * @param profile El perfil asociado al producto.
    */
    
   public UserProfileImpl(int userId, int product, int profile)
   {
      _userId = userId;
      _product = product;
      _profile = profile;
   }

   public int getUserId()
   {
      return _userId;
   }
   
   public int getProduct()
   {
      return _product;
   }
   
   public int getProfile()
   {
      return _profile;
   }
   
   public void setProfile(int profile) 
   {
      _profile = profile;
   }

	public String toString()
	{
      return toXML(false);
   }

   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Profile";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();
         
      bdr.addOpeningTag(tagName);

//      bdr.addSimpleElement("UserId", Integer.toString(_userId));
      bdr.addSimpleElement("ProductId", Integer.toString(_product));
      bdr.addSimpleElement("Profile", Integer.toString(_profile));
      
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

      statement.setLongInteger(index++, _userId);
      statement.setLongInteger(index++, _product);
      statement.setLongInteger(index++, _profile);

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

      statement.setLongInteger(index++, _profile);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al usuario. 
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

      _product = statement.getShortInteger(index++);
      _profile = statement.getShortInteger(index++);

      return new Integer(index);
   }

   /**
    * Establece el identificador del usuario.
    * 
    * @param id El identificador mencionado.
    *  
    */

   protected void setId(int id) 
   {
      _userId = id;
   }

   /**
    * Establece el identificador del producto al que se asigna el perfil.
    * 
    * @param product El identificador mencionado.
    *  
    */
    
   public void setProduct(int product) 
   {
      _product = product;
   }


   private int _userId;  
   private int _product;
   private int _profile;
   private static final Logger _logger = Logger.getLogger(UserProfileImpl.class);
}