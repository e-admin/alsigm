package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;


import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicArchive;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicArchives;
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