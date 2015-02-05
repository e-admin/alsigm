/*
 * Created on 16-sep-2005
 *
 */
package common.view;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author LUISANVE
 * 
 */
public class CollectionUtils {

	public static String join(Collection collection, String property,
			String token) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (collection == null || collection.size() == 0)
			return null;
		Iterator i = collection.iterator();
		Object anItem = i.next();

		StringBuffer buff = new StringBuffer()
				.append(property != null ? BeanUtils.getProperty(anItem,
						property) : anItem);
		while (i.hasNext()) {
			anItem = i.next();
			buff.append(token).append(
					property != null ? BeanUtils.getProperty(anItem, property)
							: anItem);
		}
		return buff.toString();
	}

	public String getJoinedValues(Collection collection, String property,
			String token) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return join(collection, property, token);
	}
}
