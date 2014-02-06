
package ieci.tecdoc.core.ldap;

import javax.naming.directory.SearchControls;

public final class LdapScope
{
   
   public static final int BASE     = SearchControls.OBJECT_SCOPE;
   public static final int ONELEVEL = SearchControls.ONELEVEL_SCOPE;
   public static final int SUBTREE  = SearchControls.SUBTREE_SCOPE;

   private LdapScope()
   {
   }
   
} // class
