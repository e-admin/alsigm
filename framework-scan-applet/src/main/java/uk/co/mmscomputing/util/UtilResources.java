package uk.co.mmscomputing.util;

import java.util.*;

public class UtilResources{

  static private Locale         locale;

         private ResourceBundle messages;
         private String         classname;

  public UtilResources(Class clazz){
    classname = clazz.getName().substring(clazz.getName().lastIndexOf(".")+1);
    messages  = ResourceBundle.getBundle(clazz.getName(),locale);
  }

  public String getString(String id){ 
    try{
      return messages.getString(id);
    }catch(MissingResourceException mre){
      return mre.getMessage();
    }
  }

  public String getString(String id,String arg){ 
    try{
      return messages.getString(id).replaceAll("%0",arg);
    }catch(MissingResourceException mre){
      return mre.getMessage();
    }
  }

  public String getString(String id,String[] args){ 
    try{
      String msg = messages.getString(id);
      for(int i=0;i<args.length;i++){
        msg=msg.replaceAll("%"+i,args[i]);
      }
      return msg;
    }catch(MissingResourceException mre){
      return mre.getMessage();
    }
  }

  /* errno:  native code errno
     errstr: native code strerror(errno)

     if we find a matching error message in resource file return that
     otherwise return operating system supplied error string
  */

  public String getErrorString(int errno,String errstr){
    try{
      return messages.getString(classname+".err."+errno);
    }catch(MissingResourceException mre){
      return "["+errno+"] "+errstr;
    }
  }

  static{
    locale = Locale.getDefault();                        // System.out.println(locale.toString());

//  locale = Locale.GERMANY;
//  locale = new Locale("de","DE");                      // deutsch / Deutschland
//  locale = new Locale("pt","BR");                      // Portuguese PT PORTUGAL / BR Brazil
  }
}