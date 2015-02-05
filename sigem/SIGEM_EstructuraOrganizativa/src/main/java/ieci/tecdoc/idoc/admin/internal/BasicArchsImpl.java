package ieci.tecdoc.idoc.admin.internal;


import ieci.tecdoc.idoc.admin.api.archive.BasicArchive;
import ieci.tecdoc.idoc.admin.api.archive.BasicArchives;

import java.util.ArrayList;
/**
 * Implementación de la clase BasicArchives
 * 
 */

public class BasicArchsImpl implements BasicArchives
{

   protected BasicArchsImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }
    
   public BasicArchive get(int index) 
   {
      return (BasicArchive)_list.get(index);
   }

   protected void add(BasicArchive arch) 
   {
      _list.add(arch);
   }


   private ArrayList _list;

}