package ieci.tecdoc.core.xml.adv;

/** Define las constantes que identifican los códigos y mensajes de error. */

public final class XmlAdvError
{

   private static final String EC_PREFIX           = "IECI_TECDOC_CORE_XML_ADV_";

   // **************************************************

   /** Parser no encontrado. */
   public static final String  EC_PARSER_NOT_FOUND = EC_PREFIX + "PARSER_NOT_FOUND";

   /** Parser no encontrado. */
   public static final String  EM_PARSER_NOT_FOUND = "Parser not found";

   // **************************************************

   private XmlAdvError()
   {
   }

} // class
