package ieci.tecdoc.idoc.admin.internal;

import java.util.ArrayList;

import ieci.tecdoc.idoc.admin.api.archive.BasicDirectories;
import ieci.tecdoc.idoc.admin.api.archive.BasicDirectory;

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
