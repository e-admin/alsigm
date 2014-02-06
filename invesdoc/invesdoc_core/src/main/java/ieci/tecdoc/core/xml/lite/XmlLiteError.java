package ieci.tecdoc.core.xml.lite;

/** Define las constantes que identifican los códigos y mensajes de error. */

public final class XmlLiteError
{

   private static final String EC_PREFIX      = "IECI_TECDOC_CORE_XML_LITE_";

   // **************************************************

   /** Error en TextParser. */
   public static final String  EC_TEXT_PARSER = EC_PREFIX + "TEXT_PARSER";

   /** Error en TextParser. */
   public static final String  EM_TEXT_PARSER = "TextParser error";

   // **************************************************

   private XmlLiteError()
   {
   }

} // class
