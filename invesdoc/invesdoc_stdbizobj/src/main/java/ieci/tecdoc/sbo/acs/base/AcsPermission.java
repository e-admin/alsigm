
package ieci.tecdoc.sbo.acs.base;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

public final class AcsPermission
{
   
   // **************************************************************************
       
   public static final int NONE       = 0;
   public static final int QUERY      = 1;
   public static final int UPDATE     = 2;
   public static final int CREATION   = 4;
   public static final int DELETION   = 8;
   public static final int PRINTING   = 16;
   public static final int ALL        = 31;
  
   // **************************************************************************
 
   private AcsPermission()
   {
   }
   
   public static IeciTdLongIntegerArrayList getAllPermissions()
   {
      IeciTdLongIntegerArrayList perms = new IeciTdLongIntegerArrayList();
      
      perms.add(QUERY);
      perms.add(UPDATE);
      perms.add(CREATION);
      perms.add(DELETION);
      perms.add(PRINTING);
      
      return perms;
   }
   
} // class
