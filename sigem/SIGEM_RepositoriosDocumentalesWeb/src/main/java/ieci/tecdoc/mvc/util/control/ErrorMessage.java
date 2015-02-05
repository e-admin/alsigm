package ieci.tecdoc.mvc.util.control;

import java.io.Serializable;


public class ErrorMessage implements Serializable
{

   //~ Instance fields ---------------------------------------------------------

   private String message = null;

   //~ Constructors ------------------------------------------------------------

   public ErrorMessage(String message)
   {

      this.message = message;

   }

   public ErrorMessage()
   {
      super();

   }

   //~ Methods -----------------------------------------------------------------

   public String getMessage()
   {

      return message;

   }

   public void setMessage(String message)
   {

      this.message = message;

   }

}
