
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicUser;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicUsers;

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