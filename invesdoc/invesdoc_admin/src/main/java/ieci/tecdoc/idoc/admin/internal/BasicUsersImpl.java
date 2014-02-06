
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.idoc.admin.api.user.BasicUser;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import java.util.ArrayList;

public class BasicUsersImpl implements BasicUsers
{

   protected BasicUsersImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }
    
   public BasicUser get(int index) 
   {
      return (BasicUser)_list.get(index);
   }

   protected void add(BasicUser user) 
   {
      _list.add(user);
   }


   private ArrayList _list;

}