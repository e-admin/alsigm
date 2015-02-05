package ieci.tdw.ispac.ispaclib.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils 
		extends org.apache.commons.collections.CollectionUtils {
	
	public static boolean isEmpty(Collection coll){
		return coll==null || coll.isEmpty();
	}

	public static List createList(Object [] objs) {
		List list = null;
		
		if (objs != null) {
			list = new ArrayList();
			
			for (int i = 0; i < objs.length; i++) {
				list.add(objs[i]);
			}
		}
		
		return list;
	}
	
	public static List createList(Object obj) {
		return createList(new Object [] { obj });
	}
}
