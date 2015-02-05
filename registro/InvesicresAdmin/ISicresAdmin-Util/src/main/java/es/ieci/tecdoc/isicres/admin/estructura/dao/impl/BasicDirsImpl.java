package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicDirectories;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicDirectory;

public class BasicDirsImpl implements BasicDirectories
{

   protected BasicDirsImpl()
   {
      _list = new ArrayList();
   }

   public int count()
   {
      return _list.size();
   }

   public BasicDirectory get(int index)
   {
      return (BasicDirectory)_list.get(index);
   }

   protected void add(BasicDirectory dir)
   {
      _list.add(dir);
   }


   private ArrayList _list;

}
