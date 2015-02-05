package common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils extends org.apache.commons.collections.ListUtils {
	
	public static final Object isAnyElementRepeated(List list, Object object)
	{
		for(int i=0;i<list.size();i++)
		{
			for(int j=0;j<list.size();j++)
			{
				if(i!=j)
				{
					if (list.get(i).equals(list.get(j)) && !list.get(i).equals(object))
						return list.get(i);
				}
			}
		}
		return null;
	}
	
	public static boolean isEmpty(List list)
	{
		return list==null || list.size()==0;
	}
	
	/**
	 * Comprueba si la lista está vacía
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List list)
	{
		return list!=null && list.size()>0;
	}	
	
	/**
	 * Se supone una lista de elementos listaElementos. Esa lista se divide en grupos de
	 * numItemsPerGroup elementos por grupo. Este metodo devuelve los numItemsPerGroup elementos
	 * pertenecientes al grupo group. Todos los grupos excepto el último deben tener numItemsPerGroup
	 * Ejemplo: Supongamos una lista de 40 elementos.Se establece un máximo de 15 elementos por grupo
	 * por lo que tendremos 2 grupos completos (de 15 elementos) y uno de 10 elementos.
	 * Si queremos obtener los elementos del segundo grupo tendremos que llamar al 
	 * metodo getItems(2,15,listaElementos)  
	 * 
	 */
	public static List getItems(int group, int numItemsPerGroup, List listaElementos)
	{

		List list=new ArrayList();
		//Si la lista tiene un elemento
		if(!ListUtils.isEmpty(listaElementos) && listaElementos.size()==1)
		{
			list=listaElementos;
		}
		
		else
		{
			int initPos=getInitPos(group,numItemsPerGroup);
			int endPos=getEndPos(group,numItemsPerGroup,listaElementos.size());
			
			if (initPos != -1 && endPos != -1)
			{
				if(initPos==endPos)
				{
					list.add(listaElementos.get(endPos));
				}
				else
				{
					list=new ArrayList(listaElementos.subList(initPos, endPos));
				
					if(!ListUtils.isEmpty(list) && endPos<listaElementos.size())
					{
						//Añadimos el ultimo elemento de la lista
						list.add(listaElementos.get(endPos));
						
					}
				}
			}
		}
		return list;
	}
	
	private static int getInitPos(int group, int numItemsPerGroup)
	{
		int initPos =numItemsPerGroup*(group-1);
		if(initPos<0){
			initPos=0;
		}
		
		return initPos;
	}
	
	private static int getEndPos(int group, int numItemsPerGroup, int sizeList)
	{
		int endPos=getInitPos(group,numItemsPerGroup)+numItemsPerGroup-1;
		if(endPos>=sizeList){
			endPos=sizeList-1;
		}
		return endPos;
	}
}
